package com.angkorteam.mbaas.server.template;

import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.mbaas.server.wicket.JooqUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import java.util.Map;

/**
 * Created by socheat on 3/7/16.
 */
public class StringPanel extends Panel {

    private final String name;

    private final Map<String, Object> fields;

    public StringPanel(String id, String name, Map<String, Object> modelObject) {
        super(id);
        this.name = name;
        this.fields = modelObject;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Label label = new Label("label", this.name);
        this.add(label);
        TextField<String> field = new TextField<>("field", new PropertyModel<>(this.fields, this.name));
        field.setLabel(JooqUtils.lookup(this.name, getPage()));
        field.add(StringValidator.maximumLength(255));
        TextFeedbackPanel feedback = new TextFeedbackPanel("feedback", field);
        this.add(field);
        this.add(feedback);
    }
}