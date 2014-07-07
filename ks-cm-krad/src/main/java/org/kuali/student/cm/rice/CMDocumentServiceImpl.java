package org.kuali.student.cm.rice;

import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.impl.DocumentServiceImpl;

/**
 * Override for KRAD DocumentServiceImpl which disables notes. This was done to allow CM to work with a
 * remote Rice server. Most of the overrides to get this to work are in
 * {@link org.kuali.student.cm.rice.KSKRADLegacyDataAdapterImpl}, but loadNotes() had to be overridden here.
 * <p/>
 * As it stands this breaks notes for all KS maintenance docs though it might be possible
 * to put in some conditional logic so that notes only not loaded for CM.
 */
public class CMDocumentServiceImpl extends DocumentServiceImpl {

    /**
     * Override which doesn't stacktrace when newMaintaintableImpl is null.
     */
    protected void loadNotes(final Document document) {
        //  Noop. Just needs to not throw.
    }
}
