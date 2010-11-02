package org.kuali.student.lum.course.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.common.validator.BeanConstraintDataProvider;
import org.kuali.student.common.validator.ConstraintDataProvider;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.lum.lu.dto.AffiliatedOrgInfo;

public class RevenuePercentValidation extends DefaultValidatorImpl {

    private static final String COURSE_REVENUE_FIELD = "revenues";

    @Override
    public List<ValidationResultInfo> validateObject(Object data, ObjectStructureDefinition objStructure) {
        // Custom validators are required to only override the other validateObject method
        return null;
    }

    /***
     * @see org.kuali.student.common.validator.Validator#validateObject(org.kuali.student.core.dictionary.dto.FieldDefinition,
     *      java.lang.Object, org.kuali.student.core.dictionary.dto.ObjectStructureDefinition, java.util.Stack)
     */
    @Override
    public List<ValidationResultInfo> validateObject(FieldDefinition field, Object data, ObjectStructureDefinition objStructure, Stack<String> elementStack) {

        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

        // Check to see if the validation is called on the right field
        if (!COURSE_REVENUE_FIELD.equalsIgnoreCase(field.getName())) {
            throw new RuntimeException("Custom Validator " + this.getClass().getName() + " was not called on the right field: revenues");
        }

        ConstraintDataProvider dataProvider = new BeanConstraintDataProvider();
        dataProvider.initialize(data);

        // Get revenues data
        Object revenueObj = dataProvider.getValue(field.getName());

        if (!(revenueObj instanceof CourseRevenueInfo)) {
            throw new RuntimeException("Custom Validator " + this.getClass().getName() + " was not called with right data: CourseRevenueInfo");
        }

        CourseRevenueInfo courseRevenue = (CourseRevenueInfo) revenueObj;

        // Sum all org percents and make sure they add up to 100
        long totalOrgPercent = 0l;

        if (courseRevenue.getAffiliatedOrgs().size() > 0) {
            for (AffiliatedOrgInfo org : courseRevenue.getAffiliatedOrgs()) {
                totalOrgPercent += org.getPercentage();
            }

            if (totalOrgPercent != 100l) {
                ValidationResultInfo valRes = new ValidationResultInfo(getElementXpath(elementStack));
                valRes.setError(MessageUtils.interpolate(getMessage("validation.minOccurs"), toMap(field)));
                results.add(valRes);
            }
        }
        
        return results;
    }
}
