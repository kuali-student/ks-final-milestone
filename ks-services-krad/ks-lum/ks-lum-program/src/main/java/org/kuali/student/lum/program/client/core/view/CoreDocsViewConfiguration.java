package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentList;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentListBinding;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialEditableHeader;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class CoreDocsViewConfiguration extends AbstractSectionConfiguration {

    private CoreDocsViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }

    @Override
    protected void buildLayout() {
       configurer.addReadOnlyField(rootSection, "id", new MessageKeyInfo(""), new DocumentList(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE,false, false)).setWidgetBinding(new DocumentListBinding("id"));
    }

    public static CoreDocsViewConfiguration createSpecial() {
        String title = ProgramProperties.get().program_menu_sections_supportingDocuments();
        return new CoreDocsViewConfiguration(new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new CredentialEditableHeader(title,ProgramSections.SUPPORTING_DOCUMENTS_EDIT)));
    }

    public static CoreDocsViewConfiguration create(){
        return new CoreDocsViewConfiguration(new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_VIEW, ProgramProperties.get().program_menu_sections_supportingDocuments(), ProgramConstants.PROGRAM_MODEL_ID));
    }
}

