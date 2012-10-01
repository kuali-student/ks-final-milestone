package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kim.impl.identity.PersonImpl;
import org.kuali.rice.kim.impl.identity.PersonLookupableImpl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 7/3/12
 * Time: 3:14 PM
 * This class was created to prevent Blank KS Person Lookups from bringing down the server.
 */
public class KSPersonLookupableImpl extends PersonLookupableImpl {

    @Override
    protected List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {

        if(GlobalVariables.getMessageMap().hasErrors()){
            return new ArrayList<PersonImpl>();
        }  else{
            return super.getSearchResults(form, searchCriteria, unbounded);
        }
    }

    @Override
    public boolean validateSearchParameters(LookupForm form, Map<String, String> searchCriteria) {

        boolean valid = super.validateSearchParameters(form, searchCriteria);    //To change body of overridden methods use File | Settings | File Templates.

        Map<String, InputField> criteriaFields = getCriteriaFieldsForValidation((LookupView) form.getPostedView(),
                form);

        // validate something exist
        if(this.areAllCriteriaFieldsBlank(form,searchCriteria)){
            for (Map.Entry<String, String> searchKeyValue : searchCriteria.entrySet()) {
                String searchPropertyName = searchKeyValue.getKey();
                String searchPropertyValue = searchKeyValue.getValue();

                LookupView lookupView = (LookupView) form.getPostedView();
                InputField inputField = criteriaFields.get(searchPropertyName);
                if (inputField != null) {
                    if (StringUtils.isBlank(searchPropertyValue)) {
                        GlobalVariables.getMessageMap().putError(inputField.getPropertyName(),
                                RiceKeyConstants.ERROR_REQUIRED, inputField.getLabel());
                    }
                } else {
                    throw new RuntimeException("Invalid search field sent for property name: " + searchPropertyName);
                }
            }

            if (GlobalVariables.getMessageMap().hasErrors()) {
                valid = false;
            }
        }

        return valid;
    }

    private boolean areAllCriteriaFieldsBlank(LookupForm form, Map<String, String> searchCriteria){
        Map<String, InputField> criteriaFields = getCriteriaFieldsForValidation((LookupView) form.getPostedView(),
                form);

        // validate required
        // TODO: this will be done by the uif validation service at some point
        int blankCount =0;
        for (Map.Entry<String, String> searchKeyValue : searchCriteria.entrySet()) {
            String searchPropertyValue = searchKeyValue.getValue();

            if(searchPropertyValue == null || "".equals(searchPropertyValue)){
                blankCount++;
            }

        }
        return (blankCount == searchCriteria.size());
    }

}
