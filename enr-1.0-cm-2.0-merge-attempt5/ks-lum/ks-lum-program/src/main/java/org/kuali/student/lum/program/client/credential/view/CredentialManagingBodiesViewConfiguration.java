package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialEditableHeader;

/**
 * @author Igor
 */
public class CredentialManagingBodiesViewConfiguration extends AbstractSectionConfiguration {

    public static CredentialManagingBodiesViewConfiguration create(Configurer configurer) {
        return new CredentialManagingBodiesViewConfiguration(configurer, false);
    }

    public static CredentialManagingBodiesViewConfiguration createSpecial(Configurer configurer) {
        return new CredentialManagingBodiesViewConfiguration(configurer, true);
    }

    private CredentialManagingBodiesViewConfiguration(Configurer configurer, boolean isSpecial) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_MANAGINGBODIES);
        if (!isSpecial){
            this.rootSection = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, title, 
                ProgramConstants.PROGRAM_MODEL_ID, new CredentialEditableHeader(title, ProgramSections.MANAGE_BODIES_EDIT));
        } else {
            this.rootSection = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, title, 
                    ProgramConstants.PROGRAM_MODEL_ID);
        }
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = createMainSection();
        rootSection.addSection(section);
    }

    private VerticalSection createMainSection() {
        VerticalSection section = new VerticalSection();
        configurer.addReadOnlyField(section, ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.MANAGINGBODIES_CURRICULUMOVERSIGHTDIVISION));
        configurer.addReadOnlyField(section, ProgramConstants.CURRICULUM_OVERSIGHT_UNIT,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.MANAGINGBODIES_CURRICULUMOVERSIGHTUNIT));
        configurer.addReadOnlyField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.MANAGINGBODIES_STUDENTOVERSIGHTDIVISION));
        configurer.addReadOnlyField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.MANAGINGBODIES_STUDENTOVERSIGHTUNIT));
        return section;
    }
}
