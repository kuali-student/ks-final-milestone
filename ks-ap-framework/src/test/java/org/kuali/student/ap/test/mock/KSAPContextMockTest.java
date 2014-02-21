package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class KSAPContextMockTest implements KsapContext {
    /**
     * Get the context info for the active transaction.
     * <p/>
     * <p>
     * NOTE: It is expected that user principal name and principal ID will be
     * populated with the current user's information on the context returned by
     * this method.
     * </p>
     *
     * @return The KS context info for the active transaction.
     */
    @Override
    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        return contextInfo;
    }
}
