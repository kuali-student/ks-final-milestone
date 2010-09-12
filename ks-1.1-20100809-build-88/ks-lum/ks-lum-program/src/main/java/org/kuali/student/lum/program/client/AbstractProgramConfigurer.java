package org.kuali.student.lum.program.client;

import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;

/**
 * @author Igor
 */
public abstract class AbstractProgramConfigurer extends Configurer {

    public abstract void configure(ProgramController programViewController);

    @Override
    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
        return super.addField(section, fieldKey, messageKey);
    }

    @Override
    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
        return super.addField(section, fieldKey, messageKey, widget);
    }
}
