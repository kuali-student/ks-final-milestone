package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.core.workflow.ui.client.views.CollaboratorSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Will
 */
public class CollaboratorsEditConfiguration extends AbstractSectionConfiguration {

    public CollaboratorsEditConfiguration() {
    	rootSection = new CollaboratorSectionView(ProgramSections.COLLABORATORS_EDIT, ProgramProperties.get().program_menu_sections_collaborators(),ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {

    }
}
