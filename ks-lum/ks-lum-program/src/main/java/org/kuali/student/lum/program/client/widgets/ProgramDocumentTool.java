package org.kuali.student.lum.program.client.widgets;

import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.lum.common.client.lo.LUConstants;

/**
 * @author Igor
 */
public class ProgramDocumentTool extends DocumentTool{
    public ProgramDocumentTool(Enum<?> viewEnum, String viewName) {
        super(LUConstants.REF_DOC_RELATION_PROPOSAL_TYPE,viewEnum, viewName);
    }

    @Override
    protected void isAuthorizedUploadDocuments() {
        processUi(true);
    }
}
