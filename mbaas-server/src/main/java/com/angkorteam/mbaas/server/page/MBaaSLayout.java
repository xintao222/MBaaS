package com.angkorteam.mbaas.server.page;

import com.angkorteam.mbaas.model.entity.Tables;
import com.angkorteam.mbaas.model.entity.tables.PageTable;
import com.angkorteam.mbaas.model.entity.tables.SectionTable;
import com.angkorteam.mbaas.model.entity.tables.UserTable;
import com.angkorteam.mbaas.model.entity.tables.pojos.PagePojo;
import com.angkorteam.mbaas.model.entity.tables.pojos.SectionPojo;
import com.angkorteam.mbaas.model.entity.tables.pojos.UserPojo;
import com.angkorteam.mbaas.server.Spring;
import com.angkorteam.mbaas.server.ui.SectionWidget;
import com.google.common.base.Strings;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Map;

/**
 * Created by socheat on 11/3/16.
 */
public class MBaaSLayout extends Border implements UUIDLayout {

    private String welcomeName;

    public MBaaSLayout(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        WebMarkupContainer headerContainer = new WebMarkupContainer("headerContainer");
        addToBorder(headerContainer);
        String htmlPageTitle = getHtmlPageTitle();
        String htmlPageDescription = getHtmlPageDescription();
        Label headerTitle = new Label("headerTitle", new PropertyModel<>(this, "htmlPageTitle"));
        headerContainer.add(headerTitle);
        Label headerDescription = new Label("headerDescription", new PropertyModel<>(this, "htmlPageDescription"));
        headerContainer.add(headerDescription);
        headerContainer.setVisible(!(Strings.isNullOrEmpty(htmlPageTitle) && Strings.isNullOrEmpty(htmlPageDescription)));

        DSLContext context = Spring.getBean(DSLContext.class);

        SectionTable sectionTable = Tables.SECTION.as("sectionTable");
        List<SectionPojo> sectionPojos = context.select(sectionTable.fields()).from(sectionTable).orderBy(sectionTable.ORDER.asc()).fetchInto(SectionPojo.class);

        RepeatingView sectionWidgets = new RepeatingView("sectionWidgets");
        if (getSession().getRoles().hasRole("administrator")) {
            for (SectionPojo sectionPojo : sectionPojos) {
                SectionWidget sectionWidget = new SectionWidget(sectionWidgets.newChildId(), sectionPojo.getSectionId());
                sectionWidgets.add(sectionWidget);
            }
        } else {
            for (SectionPojo sectionPojo : sectionPojos) {
                SectionWidget sectionWidget = new SectionWidget(sectionWidgets.newChildId(), sectionPojo.getSectionId());
                sectionWidgets.add(sectionWidget);
            }
        }

        addToBorder(sectionWidgets);

        BookmarkablePageLink<Void> logoutPage = new BookmarkablePageLink<>("logoutPage", LogoutPage.class);
        addToBorder(logoutPage);

        UserPojo userPojo = context.select(Tables.USER.fields()).from(Tables.USER).where(Tables.USER.USER_ID.eq(getSession().getUserId())).fetchOneInto(UserPojo.class);
        this.welcomeName = Strings.isNullOrEmpty(userPojo.getFullName()) ? userPojo.getLogin() : userPojo.getFullName();
        Label welcomeNameLabel = new Label("welcomeNameLabel", new PropertyModel<>(this, "welcomeName"));
        addToBorder(welcomeNameLabel);
    }

    public String getHtmlPageTitle() {
        String pageId = ((MBaaSPage) getPage()).getPageUUID();
        DSLContext context = Spring.getBean(DSLContext.class);
        PageTable pageTable = Tables.PAGE.as("pageTable");
        PagePojo pagePojo = context.select(pageTable.fields()).from(pageTable).where(pageTable.PAGE_ID.eq(pageId)).fetchOneInto(PagePojo.class);
        return pagePojo != null ? pagePojo.getTitle() : "";
    }

    public String getHtmlPageDescription() {
        String pageId = ((MBaaSPage) getPage()).getPageUUID();
        DSLContext context = Spring.getBean(DSLContext.class);
        PageTable pageTable = Tables.PAGE.as("pageTable");
        PagePojo pagePojo = context.select(pageTable.fields()).from(pageTable).where(pageTable.PAGE_ID.eq(pageId)).fetchOneInto(PagePojo.class);
        return pagePojo != null ? pagePojo.getDescription() : "";
    }

    @Override
    public final com.angkorteam.mbaas.server.Session getSession() {
        return (com.angkorteam.mbaas.server.Session) super.getSession();
    }

    @Override
    public String getLayoutUUID() {
        return MBaaSLayout.class.getName();
    }
}
