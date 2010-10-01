package org.kuali.student.lum.program.client.widgets;

import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;

/**
 * @author Igor
 */
public class ProgramDocumentTool extends DocumentTool{
    public ProgramDocumentTool(Enum<?> viewEnum, String viewName) {
        super(viewEnum, viewName);
    }

    @Override
    protected void isAuthorizedUploadDocuments() {
        processUi(true);
    }
}
