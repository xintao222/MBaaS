package com.angkorteam.mbaas.server.nashorn.wicket.markup.html.form;

import com.angkorteam.mbaas.server.nashorn.wicket.validation.NashornValidator;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.ValidatorAdapter;

/**
 * Created by socheat on 6/2/16.
 */
public class NashornRangeTextField<T extends Number & Comparable<T>> extends RangeTextField<T> {

    public NashornRangeTextField(String id, IModel<T> model, Class<T> type) {
        super(id, model, type);
    }

    @Override
    public Component add(Behavior... behaviors) {
        for (Behavior behavior : behaviors) {
            if (behavior instanceof ValidatorAdapter) {
                if (((ValidatorAdapter) behavior).getValidator() instanceof NashornValidator) {
                    ((NashornValidator) ((ValidatorAdapter) behavior).getValidator()).setId(getId());
                }
            }
        }
        return super.add(behaviors);
    }

    @Override
    protected String[] getInputTypes() {
        return new String[]{"range", "text"};
    }
}
