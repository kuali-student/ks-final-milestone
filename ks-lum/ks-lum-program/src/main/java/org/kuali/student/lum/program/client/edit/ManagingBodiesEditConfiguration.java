package org.kuali.student.lum.program.client.edit;

import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ManagingBodiesEditConfiguration extends AbstractConfiguration<ProgramEditConfigurer> {

    private VerticalSectionView mainView;

    @Override
    public View getView() {
        createShowView();
        return mainView;
    }

    private void createShowView() {
        mainView = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, ProgramProperties.get().program_menu_sections_managingBodies(), ProgramConstants.PROGRAM_MODEL_ID);
        HorizontalSection horizontalSection = new HorizontalSection();
        horizontalSection.addSection(createLeftSection());
        horizontalSection.addSection(createRightSection());
        mainView.addSection(horizontalSection);
    }

    private VerticalSection createLeftSection() {
        VerticalSection section = new VerticalSection();
        configurer.addField(section, ProgramConstants.CURRICULUM_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightDivision()));
        configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_studentOversightUnit()));
        configurer.addField(section, ProgramConstants.DEPLOYMENT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_deploymentUnit()));
        configurer.addField(section, ProgramConstants.FINANCIAL_RESOURCES_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_financialResourcesUnit()));
        configurer.addField(section, ProgramConstants.FINANCIAL_CONTROL_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_financialControlUnit()));
        return section;
    }

    private VerticalSection createRightSection() {
        VerticalSection section = new VerticalSection();
        configurer.addField(section, ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightUnit()));
        configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_studentOversightDivision()));
        configurer.addField(section, ProgramConstants.DEPLOYMENT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_deploymentDivision()));
        configurer.addField(section, ProgramConstants.FINANCIAL_RESOURCES_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_financialResourcesDivision()));
        configurer.addField(section, ProgramConstants.FINANCIAL_CONTROL_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_financialControlDivision()));
        return section;
    }
}
