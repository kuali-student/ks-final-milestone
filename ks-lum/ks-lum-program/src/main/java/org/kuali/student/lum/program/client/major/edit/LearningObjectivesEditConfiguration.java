package org.kuali.student.lum.program.client.major.edit;

import java.util.List;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.lo.LOBuilder;
import org.kuali.student.lum.common.client.lo.LOBuilderBinding;
import org.kuali.student.lum.common.client.lo.LOPicker;
import org.kuali.student.lum.common.client.lo.OutlineNode;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class LearningObjectivesEditConfiguration extends AbstractSectionConfiguration {

    public LearningObjectivesEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_EDIT, ProgramProperties.get().program_menu_sections_learningObjectives(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        final VerticalSection section = new VerticalSection();
        QueryPath path = QueryPath.concat("", ProgramConstants.LEARNING_OBJECTIVES, "*", "loInfo", "desc");
        Metadata meta = configurer.getModelDefinition().getMetadata(path);
        LOBuilder loBuilder = new LOBuilder("type", "state", "course", "kuali.loRepository.key.singleUse", meta);
        final FieldDescriptor fd = addField(section, ProgramConstants.LEARNING_OBJECTIVES,
                null,
                loBuilder,
                "");
        loBuilder.addValueChangeHandler(new ValueChangeHandler<List<OutlineNode<LOPicker>>>() {
            @Override
            public void onValueChange(ValueChangeEvent<List<OutlineNode<LOPicker>>> event) {
                section.setIsDirty(true);
                fd.setDirty(true);
            }
        });

        // have to do this here, because decision on binding is done in ks-core,
        // and we obviously don't want ks-core referring to LOBuilder
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
