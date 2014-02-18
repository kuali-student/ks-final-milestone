package org.kuali.student.common.uif.service.impl;

import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.element.Action;

/**
 * Base class for the KS lookupable implementation.
 *
 * @author Kuali Student Team
 */
public class KSLookupableImpl extends LookupableImpl {

    /**
     * Override this method to set the title that is used as a tooltip on the select action as null as the user
     * does not need to see the ids.
     *
     * @param returnLink
     * @param model
     */
    @Override
    public void getReturnUrlForResults(Action returnLink, Object model) {
        super.getReturnUrlForResults(returnLink, model);
        returnLink.setTitle(null);
    }

}
