package com.angkorteam.mbaas.server.validator;

import com.angkorteam.mbaas.model.entity.Tables;
import com.angkorteam.mbaas.model.entity.tables.GroovyTable;
import com.angkorteam.mbaas.server.Spring;
import com.google.common.base.Strings;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.codehaus.groovy.control.CompilationFailedException;
import org.jooq.DSLContext;

/**
 * Created by socheat on 11/4/16.
 */
public class GroovyScriptValidator implements IValidator<String> {

    private String documentId;

    public GroovyScriptValidator() {
    }

    public GroovyScriptValidator(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String script = validatable.getValue();
        if (!Strings.isNullOrEmpty(script)) {
            GroovyClassLoader classLoader = new GroovyClassLoader();
            GroovyCodeSource source = new GroovyCodeSource(script, System.currentTimeMillis() + "", "/groovy/script");
            source.setCachable(false);
            Class<?> groovyClass = null;
            try {
                groovyClass = classLoader.parseClass(source, false);
            } catch (CompilationFailedException e) {
                validatable.error(new ValidationError(this, "error"));
                return;
            }
            if (!groovyClass.getName().startsWith("com.angkorteam.mbaas.server.groovy.")) {
                validatable.error(new ValidationError(this, "invalid"));
                return;
            }
            int count = 0;
            DSLContext context = Spring.getBean(DSLContext.class);
            GroovyTable table = Tables.GROOVY.as("table");
            if (Strings.isNullOrEmpty(this.documentId)) {
                count = context.selectCount().from(table).where(table.JAVA_CLASS.eq(groovyClass.getName())).fetchOneInto(int.class);
            } else {
                count = context.selectCount().from(table).where(table.JAVA_CLASS.eq(groovyClass.getName())).and(table.GROOVY_ID.notEqual(this.documentId)).fetchOneInto(int.class);
            }
            if (count > 0) {
                validatable.error(new ValidationError(this, "duplicated"));
            }
        }
    }
}
