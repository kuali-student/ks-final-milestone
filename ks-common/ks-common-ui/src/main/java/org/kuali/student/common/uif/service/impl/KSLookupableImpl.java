package org.kuali.student.common.uif.service.impl;

import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.element.Link;

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
    public void buildReturnUrlForResult(Link returnLink, Object model) {
        super.buildReturnUrlForResult(returnLink, model);
        returnLink.setTitle(null);
    }

}
