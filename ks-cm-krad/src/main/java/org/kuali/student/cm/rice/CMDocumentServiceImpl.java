package org.kuali.student.cm.rice;

import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.impl.DocumentServiceImpl;

/**
 * Override for KRAD DocumentServiceImpl
 */
public class CMDocumentServiceImpl extends DocumentServiceImpl {

    /**
     * Override which doesn't stacktrace when newMaintaintableImpl is null.
     */
    protected void loadNotes(final Document document) {
        //  Noop. Just needs to not throw.
    }
}
