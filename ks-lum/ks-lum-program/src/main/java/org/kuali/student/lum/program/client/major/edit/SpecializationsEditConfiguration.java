package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.ProgramStatus;
import org.kuali.student.lum.program.client.events.AddSpecializationEvent;
import org.kuali.student.lum.program.client.major.MajorManager;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.variation.VariationsBinding;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * @author Igor
 */
public class SpecializationsEditConfiguration extends AbstractSectionConfiguration {

    private final KSButton addSpecializationButton = new KSButton(ProgramProperties.get().variationInformation_button_addSpecialization(), ButtonStyle.SECONDARY);

    public SpecializationsEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SPECIALIZATIONS_EDIT, ProgramProperties.get().program_menu_sections_specializations(), ProgramConstants.PROGRAM_MODEL_ID);
        bind();
    }

    private void bind() {
        addSpecializationButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                MajorManager.getEventBus().fireEvent(new AddSpecializationEvent());
            }
        });
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = new VerticalSection();
        KSCheckBox isVariationRequiredCheckBox = new KSCheckBox(ProgramProperties.get().programSpecialization_instructions());
        configurer.addField(section, ProgramConstants.IS_VARIATION_REQUIRED, null, isVariationRequiredCheckBox);
        configurer.addField(section, ProgramConstants.VARIATIONS, new MessageKeyInfo(""), new FlexTable()).setWidgetBinding(new VariationsBinding(AppLocations.Locations.EDIT_VARIATION.getLocation(), true, this));
        section.addWidget(addSpecializationButton);
        rootSection.addSection(section);
    }

    @Override
    public boolean checkPermission(DataModel model) {
        // return ModelPermissionType.DRAFT_STATUS.check(model);
        ProgramStatus programStatus = ProgramStatus.of((String) model.get(ProgramConstants.STATE));
        return programStatus != ProgramStatus.DRAFT;
    }

    @Override
    public void applyRestrictions() {
        addSpecializationButton.setEnabled(false);
    }
}
