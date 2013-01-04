package org.kuali.student.lum.program.client.credential.edit;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.widgets.ProgramDocumentTool;

/**
 * @author Igor
 */
public class CredentialDocsEditConfiguration extends AbstractSectionConfiguration {

    public CredentialDocsEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_EDIT, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_SUPPORTINGDOCUMENTS), 
                ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        ProgramDocumentTool documentTool = new ProgramDocumentTool(ProgramSections.SUPPORTING_DOCUMENTS_EDIT, "Documents");
        documentTool.setModelDefinition((DataModelDefinition) configurer.getModelDefinition());
        documentTool.setController(((AbstractProgramConfigurer) configurer).getProgramController());
        rootSection.addView(documentTool);
    }
}
