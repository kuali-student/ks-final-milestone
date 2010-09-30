package org.kuali.student.lum.program.client.major.edit;

import com.google.gwt.user.client.ui.VerticalPanel;

import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.variation.VariationsBinding;

/**
 * @author Igor
 */
public class SpecializationsEditConfiguration extends AbstractSectionConfiguration {

    public SpecializationsEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SPECIALIZATIONS, ProgramProperties.get().program_menu_sections_specializations(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
    	VerticalSection section = new VerticalSection();
    	
    	KSCheckBox isVariationRequiredCheckBox = new KSCheckBox(ProgramProperties.get().programSpecialization_instructions());
    	//isVariationRequiredCheckBox.setStyleName(style);
    	configurer.addField(section, ProgramConstants.ISVARIATIONREQUIRED, null, isVariationRequiredCheckBox);
        configurer.addField(section, ProgramConstants.VARIATIONS, new MessageKeyInfo(""), new VerticalPanel()).setWidgetBinding(new VariationsBinding("/HOME/CURRICULUM_HOME/VARIATION_EDIT"));
        rootSection.addSection(section);
    }
   
}
