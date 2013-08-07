package org.kuali.student.lum.program.client.widgets;

import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.lum.common.client.lu.LUUIConstants;

/**
 * @author Igor
 */
public class ProgramDocumentTool extends DocumentTool{
    public ProgramDocumentTool(Enum<?> viewEnum, String viewName) {
        super(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE,viewEnum, viewName);
    }

    @Override
    protected void isAuthorizedUploadDocuments() {
        processUi(true);
    }
}
