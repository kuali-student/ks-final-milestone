package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentList;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentListBinding;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.lo.LUConstants;
import org.kuali.student.lum.common.client.lo.TreeStringBinding;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.EditableHeader;

/**
 * @author Igor
 */
public class SupportingDocsViewConfiguration extends AbstractSectionConfiguration {

    private SupportingDocsViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }

    @Override
    protected void buildLayout() {
       configurer.addReadOnlyField(rootSection, "id", new MessageKeyInfo(""), new DocumentList(LUConstants.REF_DOC_RELATION_PROPOSAL_TYPE,false, false)).setWidgetBinding(new DocumentListBinding("id"));
    }

    public static SupportingDocsViewConfiguration createSpecial() {
        String title = ProgramProperties.get().program_menu_sections_supportingDocuments();
        return new SupportingDocsViewConfiguration(new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new EditableHeader(title,ProgramSections.SUPPORTING_DOCUMENTS_EDIT)));
    }

    public static SupportingDocsViewConfiguration create(){
        return new SupportingDocsViewConfiguration(new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_VIEW, ProgramProperties.get().program_menu_sections_supportingDocuments(), ProgramConstants.PROGRAM_MODEL_ID));
    }
}
