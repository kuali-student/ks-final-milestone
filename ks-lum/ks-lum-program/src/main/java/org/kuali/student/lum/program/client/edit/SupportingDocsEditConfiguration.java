package org.kuali.student.lum.program.client.edit;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class SupportingDocsEditConfiguration extends AbstractSectionConfiguration {

    public SupportingDocsEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_EDIT, ProgramProperties.get().program_menu_sections_supportingDocuments(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        DocumentTool documentTool = new DocumentTool(ProgramSections.SUPPORTING_DOCUMENTS_EDIT, "Documents");
        documentTool.setModelDefinition((DataModelDefinition) configurer.getModelDefinition());
        rootSection.addWidget(documentTool);
        documentTool.setVisible(true);
    }
}
