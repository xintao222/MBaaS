package com.angkorteam.mbaas.server.factory;

import com.angkorteam.mbaas.server.bean.System;
import com.google.common.base.Strings;
import org.apache.commons.configuration.ConfigurationException;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.File;

/**
 * Created by socheat on 10/23/16.
 */
public class ConfigurationFactoryBean implements FactoryBean<Configuration>, InitializingBean, ServletContextAware {

    private Configuration configuration;

    private ServletContext servletContext;

    private DataSource dataSource;

    @Override
    public Configuration getObject() throws Exception {
        return configuration;
    }

    @Override
    public Class<?> getObjectType() {
        return Configuration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        com.angkorteam.mbaas.server.bean.Configuration xml = null;
        String configurationFile = this.servletContext.getInitParameter("configuration");
        File file;
        if (!Strings.isNullOrEmpty(configurationFile)) {
            file = new File(configurationFile);
        } else {
            File home = new File(java.lang.System.getProperty("user.home"));
            file = new File(home, ".xml/" + com.angkorteam.mbaas.server.bean.Configuration.KEY);
        }
        try {
            xml = new com.angkorteam.mbaas.server.bean.Configuration(file);
        } catch (ConfigurationException e) {
        }

        MappedSchema mappedSchema = new MappedSchema();
        mappedSchema.withInput(xml.getString(com.angkorteam.mbaas.server.bean.Configuration.TEMP_JDBC_DATABASE));
        String itest = java.lang.System.getProperty("itest");
        if (itest == null || "".equals(itest)) {
            mappedSchema.withOutput(xml.getString(com.angkorteam.mbaas.server.bean.Configuration.APP_JDBC_DATABASE));
        } else {
            mappedSchema.withOutput(xml.getString(com.angkorteam.mbaas.server.bean.Configuration.TEST_JDBC_DATABASE));
        }
        RenderMapping renderMapping = new RenderMapping();
        renderMapping.withSchemata(mappedSchema);
        Settings settings = new Settings();
        settings.withRenderMapping(renderMapping);
        settings.withExecuteWithOptimisticLocking(true);
        settings.setUpdatablePrimaryKeys(false);

        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.setSettings(settings);
        configuration.setDataSource(this.dataSource);
        configuration.set(SQLDialect.MYSQL);
//        if ("com.mysql.cj.jdbc.Driver".equals(dataSource.getDriverClassName())) {
//            configuration.set(SQLDialect.MYSQL);
//        } else if ("com.mysql.jdbc.Driver".equals(dataSource.getDriverClassName())) {
//            configuration.set(SQLDialect.MYSQL);
//        } else if ("org.hsqldb.jdbcDriver".equals(dataSource.getDriverClassName())) {
//            configuration.set(SQLDialect.HSQLDB);
//        } else if ("org.mariadb.jdbc.Driver".equals(dataSource.getDriverClassName())) {
//            configuration.set(SQLDialect.MARIADB);
//        }
        this.configuration = configuration;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
