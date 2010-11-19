package org.kuali.student.lum.program.client.credential.edit;

import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.lo.LOBuilder;
import org.kuali.student.lum.common.client.lo.LOBuilderBinding;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class CredentialLearningObjectivesEditConfiguration extends AbstractSectionConfiguration {

    public CredentialLearningObjectivesEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_EDIT, ProgramProperties.get().program_menu_sections_learningObjectives(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    protected void buildLayout() {
        VerticalSection section = new VerticalSection();
        QueryPath path = QueryPath.concat("", ProgramConstants.LEARNING_OBJECTIVES, "*", "loInfo", "desc");
        Metadata meta = configurer.getModelDefinition().getMetadata(path);
        FieldDescriptor fd = addField(section, ProgramConstants.LEARNING_OBJECTIVES,
                null,
                new LOBuilder("type", "state", "course", "kuali.loRepository.key.singleUse", meta),
                "");
        fd.setWidgetBinding(LOBuilderBinding.INSTANCE);
        section.addStyleName("KS-LUM-Section-Divider");
        rootSection.addSection(section);
    }

    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = configurer.getModelDefinition().getMetadata(path);

        FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
        return fd;
    }

}
