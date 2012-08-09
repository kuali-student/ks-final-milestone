package org.kuali.student.lum.program.client.major.edit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
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
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.events.AddSpecializationEvent;
import org.kuali.student.lum.program.client.major.MajorManager;
import org.kuali.student.lum.program.client.permissions.ModelPermissionType;
import org.kuali.student.lum.program.client.variation.VariationsBinding;

/**
 * @author Igor
 */
public class SpecializationsEditConfiguration extends AbstractSectionConfiguration {

    private final KSButton addSpecializationButton;

    public SpecializationsEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        addSpecializationButton = new KSButton(getLabel(ProgramMsgConstants.VARIATIONINFORMATION_BUTTON_ADDSPECIALIZATION), ButtonStyle.SECONDARY);
        rootSection = new VerticalSectionView(ProgramSections.SPECIALIZATIONS_EDIT, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_SPECIALIZATIONS), ProgramConstants.PROGRAM_MODEL_ID);
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
        KSCheckBox isVariationRequiredCheckBox = new KSCheckBox(getLabel(ProgramMsgConstants.PROGRAMSPECIALIZATION_INSTRUCTIONS));
        configurer.addField(section, ProgramConstants.IS_VARIATION_REQUIRED, null, isVariationRequiredCheckBox);
        configurer.addField(section, ProgramConstants.VARIATIONS, new MessageKeyInfo(""), new FlexTable()).setWidgetBinding(new VariationsBinding(AppLocations.Locations.EDIT_VARIATION.getLocation(), true, this));
        section.addWidget(addSpecializationButton);
        rootSection.addSection(section);
    }

    @Override
    public boolean checkPermission(DataModel model) {
        return ModelPermissionType.DRAFT_STATUS.check(model);
    }

    @Override
    public void applyRestrictions() {
        addSpecializationButton.setEnabled(false);
    }

    @Override
    public void removeRestrictions() {
        addSpecializationButton.setEnabled(true);
    }
}
