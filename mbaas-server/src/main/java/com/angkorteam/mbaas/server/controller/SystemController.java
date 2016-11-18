package com.angkorteam.mbaas.server.controller;

import com.angkorteam.mbaas.plain.response.RestResponse;
import com.angkorteam.mbaas.server.Application;
import com.angkorteam.mbaas.server.bean.GroovyClassLoader;
import com.angkorteam.mbaas.server.gson.Page;
import com.angkorteam.mbaas.server.gson.Rest;
import com.angkorteam.mbaas.server.gson.Sync;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import groovy.lang.GroovyCodeSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by socheat on 11/3/16.
 */
@Controller
public class SystemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private ServletContext servletContext;

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DSLContext context;

    @Autowired
    private GroovyClassLoader classLoader;

    @Autowired
    private Sql2o sql2o;

    @RequestMapping(path = "/system/monitor")
    public ResponseEntity<RestResponse> monitor(Authentication authentication, HttpServletRequest request) throws Throwable {
        Map<String, Object> monitor = new HashMap<>();
        monitor.put("time", DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
        RestResponse response = new RestResponse();
        response.setResultCode(HttpStatus.OK.value());
        response.setResultMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(monitor);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "/system/sync")
    public ResponseEntity<RestResponse> sync(Authentication authentication, HttpServletRequest request) throws Throwable {
        String json = org.apache.commons.io.IOUtils.toString(request.getInputStream(), "UTF-8");
        Sync sync = this.gson.fromJson(json, Sync.class);
        if (sync == null) {
            sync = new Sync();
        }
        List<String> pageIds = new ArrayList<>();
        List<String> restIds = new ArrayList<>();
        try (Connection connection = sql2o.open()) {
            if (sync.getPages() != null && !sync.getPages().isEmpty()) {
                for (Page clientPage : sync.getPages()) {
                    Query query = connection.createQuery("select page.path as mountPath, groovy.groovy_id as groovyId, page.page_id as pageId, html as serverHtml, html_crc32 as serverHtmlCrc32, groovy.script as serverGroovy, groovy.script_crc32 as serverGroovyCrc32 from page inner join groovy on page.groovy_id = groovy.groovy_id where pageId = :pageId");
                    query.addParameter("pageId", clientPage.getPageId());
                    Page serverPage = query.executeAndFetchFirst(Page.class);
                    boolean groovyConflicted = !clientPage.getServerGroovyCrc32().equals(serverPage.getServerGroovyCrc32());
                    boolean htmlConflicted = !clientPage.getServerHtmlCrc32().equals(serverPage.getServerHtmlCrc32());
                    pageIds.add(clientPage.getPageId());
                    clientPage.setGroovyConflicted(groovyConflicted);
                    clientPage.setHtmlConflicted(htmlConflicted);
                    if (!groovyConflicted && !htmlConflicted && Strings.isNullOrEmpty(clientPage.getClientGroovyCrc32()) && Strings.isNullOrEmpty(clientPage.getClientHtmlCrc32())) {
                        String path = StringUtils.replaceChars(serverPage.getJavaClass(), '.', '/');
                        clientPage.setHtmlPath(path + ".html");
                        clientPage.setGroovyPath(path + ".groovy");
                        clientPage.setServerGroovyCrc32(null);
                        clientPage.setServerGroovy(null);
                        clientPage.setServerHtmlCrc32(null);
                        clientPage.setServerHtml(null);
                        classLoader.removeSourceCache(serverPage.getGroovyId());
                        classLoader.removeClassCache(serverPage.getJavaClass());
                        connection.createQuery("delete from page where page_id = :page_id").addParameter("page_id", serverPage.getPageId()).executeUpdate();
                        connection.createQuery("delete from groovy where groovy_id = :groovy_id").addParameter("groovy_id", serverPage.getGroovyId()).executeUpdate();
                        Application.get().unmount(serverPage.getMountPath());
                        Application.get().getMarkupSettings().getMarkupFactory().getMarkupCache().clear();
                    } else {
                        if (!groovyConflicted) {
                            classLoader.removeSourceCache(serverPage.getGroovyId());
                            classLoader.removeClassCache(serverPage.getJavaClass());
                            GroovyCodeSource source = new GroovyCodeSource(clientPage.getClientGroovy(), serverPage.getGroovyId(), "/groovy/script");
                            source.setCachable(true);
                            Class<?> pageClass = classLoader.parseClass(source, true);
                            Application.get().mountPage(serverPage.getMountPath(), (Class<? extends org.apache.wicket.Page>) pageClass);
                            connection.createQuery("update groovy set script = :script, script_crc32 = :script_crc32, java_class = :java_class where groovy_id = :groovy_id")
                                    .addParameter("script", clientPage.getClientGroovy())
                                    .addParameter("script_crc32", clientPage.getClientGroovyCrc32())
                                    .addParameter("java_class", pageClass.getName())
                                    .addParameter("groovy_id", serverPage.getGroovyId())
                                    .executeUpdate();
                            clientPage.setServerGroovyCrc32(clientPage.getClientGroovyCrc32());
                            clientPage.setServerGroovy(clientPage.getClientGroovy());
                        } else {
                            clientPage.setServerGroovyCrc32(serverPage.getServerGroovyCrc32());
                            clientPage.setServerGroovy(serverPage.getServerGroovy());
                        }

                        if (!htmlConflicted) {
                            connection.createQuery("update page set html = :html, html_crc32 = :html_crc32 where page_id = :page_id")
                                    .addParameter("html", clientPage.getClientHtml())
                                    .addParameter("html_crc32", clientPage.getClientHtmlCrc32())
                                    .addParameter("page_id", clientPage.getPageId())
                                    .executeUpdate();
                            Application.get().getMarkupSettings().getMarkupFactory().getMarkupCache().clear();
                            clientPage.setServerHtmlCrc32(serverPage.getClientHtmlCrc32());
                            clientPage.setServerHtml(serverPage.getClientHtml());
                        } else {
                            clientPage.setServerHtmlCrc32(serverPage.getServerHtmlCrc32());
                            clientPage.setServerHtml(serverPage.getServerHtml());
                        }
                    }
                }
            }
            if (sync.getRests() != null && !sync.getRests().isEmpty()) {
                for (Rest clientRest : sync.getRests()) {
                    restIds.add(clientRest.getRestId());
                    Query query = connection.createQuery("select groovy.groovy_id as groovyId, rest.rest_id as restId, groovy.script as serverGroovy, groovy.script_crc32 as serverGroovyCrc32 from rest inner join groovy on rest.groovy_id = groovy.groovy_id where restId = :restId");
                    query.addParameter("restId", clientRest.getRestId());
                    Rest serverRest = query.executeAndFetchFirst(Rest.class);
                    boolean groovyConflicted = !clientRest.getServerGroovyCrc32().equals(serverRest.getServerGroovyCrc32());
                    clientRest.setGroovyConflicted(groovyConflicted);
                    if (!groovyConflicted && Strings.isNullOrEmpty(clientRest.getClientGroovyCrc32())) {
                        String path = StringUtils.replaceChars(serverRest.getJavaClass(), '.', '/');
                        clientRest.setGroovyPath(path + ".groovy");
                        clientRest.setServerGroovy(null);
                        clientRest.setServerGroovyCrc32(null);
                        classLoader.removeSourceCache(serverRest.getGroovyId());
                        classLoader.removeClassCache(serverRest.getJavaClass());
                        connection.createQuery("delete from rest where rest_id = :rest_id").addParameter("rest_id", serverRest.getRestId()).executeUpdate();
                        connection.createQuery("delete from groovy where groovy_id = :groovy_id").addParameter("groovy_id", serverRest.getGroovyId()).executeUpdate();
                    } else {
                        if (!groovyConflicted) {
                            GroovyCodeSource source = new GroovyCodeSource(clientRest.getClientGroovy(), serverRest.getGroovyId(), "/groovy/script");
                            source.setCachable(true);
                            Class<?> serviceClass = classLoader.parseClass(source, true);
                            connection.createQuery("update groovy set script = :script, script_crc32 = :script_crc32, java_class = :java_class where groovy_id = :groovy_id")
                                    .addParameter("script", clientRest.getClientGroovy())
                                    .addParameter("script_crc32", clientRest.getClientGroovyCrc32())
                                    .addParameter("java_class", serviceClass.getName())
                                    .addParameter("groovy_id", clientRest.getGroovyId())
                                    .executeUpdate();
                            clientRest.setServerGroovyCrc32(clientRest.getClientGroovyCrc32());
                            clientRest.setServerGroovy(clientRest.getClientGroovy());
                        } else {
                            clientRest.setServerGroovyCrc32(serverRest.getServerGroovyCrc32());
                            clientRest.setServerGroovy(serverRest.getServerGroovy());
                        }
                    }
                }
            }

            List<Page> serverPages;
            if (!pageIds.isEmpty()) {
                Query query = connection.createQuery("select groovy.java_class as javaClass, page.page_id as pageId, html as serverHtml, html_crc32 as serverHtmlCrc32, groovy.script as serverGroovy, groovy.script_crc32 as serverGroovyCrc32 from page inner join groovy on page.groovy_id = groovy.groovy_id where page.system = false and pageId not in (:pageId)");
                query.addParameter("pageId", pageIds);
                serverPages = query.executeAndFetch(Page.class);
            } else {
                Query query = connection.createQuery("select groovy.java_class as javaClass, page.page_id as pageId, html as serverHtml, html_crc32 as serverHtmlCrc32, groovy.script as serverGroovy, groovy.script_crc32 as serverGroovyCrc32 from page inner join groovy on page.groovy_id = groovy.groovy_id where page.system = false");
                serverPages = query.executeAndFetch(Page.class);
            }
            if (serverPages != null && !serverPages.isEmpty()) {
                for (Page serverPage : serverPages) {
                    String path = StringUtils.replaceChars(serverPage.getJavaClass(), '.', '/');
                    serverPage.setHtmlPath(path + ".html");
                    serverPage.setGroovyPath(path + ".groovy");
                    serverPage.setGroovyConflicted(false);
                    serverPage.setHtmlConflicted(false);
                    sync.addPage(serverPage);
                }
            }

            List<Rest> serverRests;
            if (!restIds.isEmpty()) {
                Query query = connection.createQuery("select groovy.java_class as javaClass, rest.rest_id as restId, groovy.script as serverGroovy, groovy.script_crc32 as serverGroovyCrc32 from rest inner join groovy on rest.groovy_id = groovy.groovy_id where rest.system = false and restId not in (:restId)");
                query.addParameter("restId", restIds);
                serverRests = query.executeAndFetch(Rest.class);
            } else {
                Query query = connection.createQuery("select groovy.java_class as javaClass, rest.rest_id as restId, groovy.script as serverGroovy, groovy.script_crc32 as serverGroovyCrc32 from rest inner join groovy on rest.groovy_id = groovy.groovy_id where rest.system = false");
                serverRests = query.executeAndFetch(Rest.class);
            }
            if (serverRests != null && !serverRests.isEmpty()) {
                for (Rest restPage : serverRests) {
                    String path = StringUtils.replaceChars(restPage.getJavaClass(), '.', '/');
                    restPage.setGroovyPath(path + ".groovy");
                    restPage.setGroovyConflicted(false);
                    sync.addRest(restPage);
                }
            }
        }
        RestResponse response = new RestResponse();
        response.setData(sync);
        response.setResultCode(HttpStatus.OK.value());
        response.setResultMessage(HttpStatus.OK.getReasonPhrase());
        return ResponseEntity.ok(response);
    }

}
