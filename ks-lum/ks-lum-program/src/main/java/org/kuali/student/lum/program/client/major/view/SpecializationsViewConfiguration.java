package org.kuali.student.lum.program.client.major.view;

import com.google.gwt.user.client.ui.FlexTable;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.variation.VariationsBinding;
import org.kuali.student.lum.program.client.widgets.EditableHeader;

/**
 * @author Igor
 */
public class SpecializationsViewConfiguration extends AbstractSectionConfiguration {


    public static SpecializationsViewConfiguration create() {
        return new SpecializationsViewConfiguration(new VerticalSectionView(ProgramSections.SPECIALIZATIONS_VIEW, ProgramProperties.get().program_menu_sections_specializations(), ProgramConstants.PROGRAM_MODEL_ID));
    }

    public static SpecializationsViewConfiguration createSpecial() {
        String title = ProgramProperties.get().program_menu_sections_specializations();
        return new SpecializationsViewConfiguration(new VerticalSectionView(ProgramSections.SPECIALIZATIONS_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new EditableHeader(title, ProgramSections.SPECIALIZATIONS_EDIT)));
    }

    private SpecializationsViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = new VerticalSection();

        KSCheckBox isVariationRequiredCheckBox = new KSCheckBox(ProgramProperties.get().programSpecialization_instructions());
        isVariationRequiredCheckBox.setEnabled(false);
        //isVariationRequiredCheckBox.setStyleName(style);
        configurer.addReadOnlyField(section, ProgramConstants.IS_VARIATION_REQUIRED, null, isVariationRequiredCheckBox);
        configurer.addReadOnlyField(section, ProgramConstants.VARIATIONS, new MessageKeyInfo(""), new FlexTable()).setWidgetBinding(new VariationsBinding(AppLocations.Locations.VIEW_VARIATION.getLocation(), false));
        rootSection.addSection(section);
    }
}
