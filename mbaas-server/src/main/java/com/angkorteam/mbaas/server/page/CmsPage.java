package com.angkorteam.mbaas.server.page;

import com.angkorteam.mbaas.model.entity.Tables;
import com.angkorteam.mbaas.model.entity.tables.PageTable;
import com.angkorteam.mbaas.model.entity.tables.pojos.PagePojo;
import com.angkorteam.mbaas.server.Spring;
import com.angkorteam.mbaas.server.wicket.StringResourceStream;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.DefaultMarkupResourceStreamProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.util.resource.IResourceStream;
import org.jooq.DSLContext;

/**
 * Created by socheatkhauv on 10/25/16.
 */
public abstract class CmsPage extends MBaaSPage implements IMarkupResourceStreamProvider {

    @Override
    public final String getVariation() {
        return getPageUUID();
    }

    @Override
    public final IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
        if (CmsPage.class.isAssignableFrom(containerClass) && containerClass != CmsPage.class) {
            DSLContext context = Spring.getBean(DSLContext.class);
            PageTable pageTable = Tables.PAGE.as("pageTable");
            PagePojo page = context.select(pageTable.fields()).from(pageTable).where(pageTable.PAGE_ID.eq(getPageUUID())).fetchOneInto(PagePojo.class);
            String html = page.getHtml();
            StringResourceStream stream = new StringResourceStream("page_" + page.getPageId(), html);
            return stream;
        } else {
            DefaultMarkupResourceStreamProvider streamProvider = new DefaultMarkupResourceStreamProvider();
            return streamProvider.getMarkupResourceStream(container, containerClass);
        }
    }

    @Override
    public com.angkorteam.mbaas.server.Session getSession() {
        return (com.angkorteam.mbaas.server.Session) super.getSession();
    }
}