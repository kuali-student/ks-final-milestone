package org.kuali.student.lum.program.client.view;

import org.kuali.student.common.ui.client.configurable.mvc.sections.CollapsableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ManagingBodiesViewConfiguration extends AbstractSectionConfiguration<ProgramViewConfigurer> {

    public ManagingBodiesViewConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, ProgramProperties.get().program_menu_sections_managingBodies(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = createMainSection();
        CollapsableSection collapsableSection = createAdditionalSection();
        rootSection.addSection(section);
        rootSection.addSection(collapsableSection);
    }

    private VerticalSection createMainSection() {
        VerticalSection section = new VerticalSection();
        configurer.addReadOnlyField(section, ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightUnit()));
        configurer.addReadOnlyField(section, ProgramConstants.CURRICULUM_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightDivision()));
        configurer.addReadOnlyField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_studentOversightDivision()));
        configurer.addReadOnlyField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_studentOversightUnit()));
        return section;
    }

    private CollapsableSection createAdditionalSection() {
        CollapsableSection section = new CollapsableSection(ProgramProperties.get().managingBodies_seeAll());
        configurer.addReadOnlyField(section, ProgramConstants.DEPLOYMENT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_deploymentDivision()));
        configurer.addReadOnlyField(section, ProgramConstants.DEPLOYMENT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_deploymentUnit()));
        configurer.addReadOnlyField(section, ProgramConstants.FINANCIAL_RESOURCES_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_financialResourcesDivision()));
        configurer.addReadOnlyField(section, ProgramConstants.FINANCIAL_RESOURCES_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_financialResourcesUnit()));
        configurer.addReadOnlyField(section, ProgramConstants.FINANCIAL_CONTROL_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_financialControlDivision()));
        configurer.addReadOnlyField(section, ProgramConstants.FINANCIAL_CONTROL_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_financialControlUnit()));
        return section;
    }
}
