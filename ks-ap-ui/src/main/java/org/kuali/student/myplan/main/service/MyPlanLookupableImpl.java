package org.kuali.student.myplan.main.service;

import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;

import java.util.Map;
import org.apache.log4j.Logger;

/**
 * MyPlan Lookupable Impl extends the default LookupableImpl and overrides the methods that required postedView. This is to allow MyPlan views to submit search
 * requests without having to first load up the lookup form. In Rice 2.2 M1 postedView is used to validate search criteria and that causes Nullpointer
 * Exception.
 */
public class MyPlanLookupableImpl extends LookupableImpl {

    private final Logger logger = Logger.getLogger(MyPlanLookupableImpl.class);
    /**
     * Override and ignore criteria validation
     * @param form
     * @param searchCriteria
     * @return
     */
    @Override
    public boolean validateSearchParameters(LookupForm form, Map<String, String> searchCriteria) {
        return true;
    }
}
