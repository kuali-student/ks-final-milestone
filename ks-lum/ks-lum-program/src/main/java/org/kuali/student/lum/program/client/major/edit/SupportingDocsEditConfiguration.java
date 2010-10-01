package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.ProgramDocumentTool;

/**
 * @author Igor
 */
public class SupportingDocsEditConfiguration extends AbstractSectionConfiguration {

    public SupportingDocsEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_EDIT, ProgramProperties.get().program_menu_sections_supportingDocuments(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        ProgramDocumentTool documentTool = new ProgramDocumentTool(ProgramSections.SUPPORTING_DOCUMENTS_EDIT, "Documents");
        documentTool.setModelDefinition((DataModelDefinition) configurer.getModelDefinition());
        documentTool.setController(((AbstractProgramConfigurer) configurer).getViewController());
        rootSection.addView(documentTool);
    }
}
