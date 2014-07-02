package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreEditableHeader;

/**
 * @author Igor
 */
public class CoreManagingBodiesViewConfiguration extends AbstractSectionConfiguration {
    
    public static CoreManagingBodiesViewConfiguration create(Configurer configurer) {
        return new CoreManagingBodiesViewConfiguration(configurer, false);
    }

    public static CoreManagingBodiesViewConfiguration createSpecial(Configurer configurer) {
        return new CoreManagingBodiesViewConfiguration(configurer, true);
    }

    private CoreManagingBodiesViewConfiguration(Configurer configurer, boolean isSpecial) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_MANAGINGBODIES);
        if (!isSpecial){
            this.rootSection = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID);
        } else {
            this.rootSection = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, title, 
                    ProgramConstants.PROGRAM_MODEL_ID, new CoreEditableHeader(title, ProgramSections.MANAGE_BODIES_EDIT));
        }
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = createMainSection();
        rootSection.addSection(section);
    }

    private VerticalSection createMainSection() {
        VerticalSection section = new VerticalSection();
        configurer.addReadOnlyField(section, ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_CURRICULUMOVERSIGHTDIVISION));
        configurer.addReadOnlyField(section, ProgramConstants.CURRICULUM_OVERSIGHT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_CURRICULUMOVERSIGHTUNIT));
        configurer.addReadOnlyField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_STUDENTOVERSIGHTDIVISION));
        configurer.addReadOnlyField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_STUDENTOVERSIGHTUNIT));
        return section;
    }
}
