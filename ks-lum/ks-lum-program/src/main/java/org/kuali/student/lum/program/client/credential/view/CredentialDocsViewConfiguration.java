package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentList;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentListBinding;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialEditableHeader;

/**
 * @author Igor
 */
public class CredentialDocsViewConfiguration extends AbstractSectionConfiguration {

    private CredentialDocsViewConfiguration(Configurer configurer, boolean isSpecial) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_SUPPORTINGDOCUMENTS);
        if (isSpecial){
            this.rootSection = new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_VIEW, title, 
                    ProgramConstants.PROGRAM_MODEL_ID, new CredentialEditableHeader(title,ProgramSections.SUPPORTING_DOCUMENTS_EDIT));
        } else {
            this.rootSection = new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_VIEW, title, 
                    ProgramConstants.PROGRAM_MODEL_ID);
        }
    }

    @Override
    protected void buildLayout() {
       configurer.addReadOnlyField(rootSection, "id", new MessageKeyInfo(""), new DocumentList(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE,false, false)).setWidgetBinding(new DocumentListBinding("id"));
    }

    public static CredentialDocsViewConfiguration createSpecial(Configurer configurer) {
        return new CredentialDocsViewConfiguration(configurer, true);
    }

    public static CredentialDocsViewConfiguration create(Configurer configurer){
        return new CredentialDocsViewConfiguration(configurer, false);
    }
}
