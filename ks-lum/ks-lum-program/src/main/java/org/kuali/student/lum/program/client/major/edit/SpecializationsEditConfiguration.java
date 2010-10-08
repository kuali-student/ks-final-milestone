package org.kuali.student.lum.program.client.major.edit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramManager;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.VariationRegistry;
import org.kuali.student.lum.program.client.events.AddSpecializationEvent;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.variation.VariationsBinding;

/**
 * @author Igor
 */
public class SpecializationsEditConfiguration extends AbstractSectionConfiguration {

    private KSButton addSpecializationButton = new KSButton(ProgramProperties.get().variationInformation_button_addSpecialization());

    public SpecializationsEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SPECIALIZATIONS, ProgramProperties.get().program_menu_sections_specializations(), ProgramConstants.PROGRAM_MODEL_ID);
        bind();
    }

    private void bind() {
        addSpecializationButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Data newSpecializationData = new Data();
                newSpecializationData.set(ProgramConstants.LONG_TITLE, "New Specialization");
                VariationRegistry.setData(newSpecializationData);
                ProgramManager.getEventBus().fireEvent(new AddSpecializationEvent());
            }
        });
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = new VerticalSection();
        KSCheckBox isVariationRequiredCheckBox = new KSCheckBox(ProgramProperties.get().programSpecialization_instructions());
        configurer.addField(section, ProgramConstants.IS_VARIATION_REQUIRED, null, isVariationRequiredCheckBox);
        configurer.addField(section, ProgramConstants.VARIATIONS, new MessageKeyInfo(""), new VerticalPanel()).setWidgetBinding(new VariationsBinding(ProgramConstants.VARIATION_EDIT_URL));
        section.addWidget(addSpecializationButton);
        rootSection.addSection(section);
    }

}
