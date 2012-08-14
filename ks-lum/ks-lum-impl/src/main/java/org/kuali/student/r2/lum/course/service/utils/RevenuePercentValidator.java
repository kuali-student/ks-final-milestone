package org.kuali.student.r2.lum.course.service.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r1.common.validator.BeanConstraintDataProvider;
import org.kuali.student.r1.common.validator.ConstraintDataProvider;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.kuali.student.r2.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;

public class RevenuePercentValidator extends DefaultValidatorImpl {

    private static final String COURSE_REVENUE_FIELD = "revenues";

    @Override
    public List<ValidationResultInfo> validateObject(Object data, ObjectStructureDefinition objStructure, ContextInfo contextInfo) {
        // Custom validators are required to only override the other validateObject method
        return null;
    }

    /***
     * @see org.kuali.student.common.validator.Validator#validateObject(org.kuali.student.common.dictionary.dto.FieldDefinition,
     *      java.lang.Object, org.kuali.student.common.dictionary.dto.ObjectStructureDefinition, java.util.Stack)
     */
    @Override
    public List<ValidationResultInfo> validateObject(FieldDefinition field, Object data, ObjectStructureDefinition objStructure, Stack<String> elementStack, ContextInfo contextInfo) {

        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

        // Check to see if the validation is called on the right field
        if (!COURSE_REVENUE_FIELD.equalsIgnoreCase(field.getName())) {
            throw new RuntimeException("Custom Validator " + this.getClass().getName() + " was not called on the right field: revenues");
        }

        ConstraintDataProvider dataProvider = new BeanConstraintDataProvider();
        dataProvider.initialize(data);

        // Get revenues data
        Object revenuesObj = dataProvider.getValue(field.getName());

        if (!(revenuesObj instanceof Collection)) {
            throw new RuntimeException("Custom Validator " + this.getClass().getName() + " was not called with right data: CourseRevenueInfo Collection");
        }

        long totalOrgPercent = 0l;
        // Sum all org percents and make sure they add up to 100
        for (Object o : (Collection<?>) revenuesObj) {

            if (!(o instanceof CourseRevenueInfo)) {
                throw new RuntimeException("Custom Validator " + this.getClass().getName() + " was not called with right data: CourseRevenueInfo");
            }

            CourseRevenueInfo courseRevenue = (CourseRevenueInfo) o;

            if (courseRevenue.getAffiliatedOrgs().size() > 0) {
                for (AffiliatedOrgInfo org : courseRevenue.getAffiliatedOrgs()) {
                    totalOrgPercent += org.getPercentage();
                }
            }
        }

        if (((Collection<?>) revenuesObj).size() > 0 && totalOrgPercent != 100l) {
            ValidationResultInfo valRes = new ValidationResultInfo(getElementXpath(elementStack));
            valRes.setElement("/revenues");
            valRes.setError(MessageUtils.interpolate(getMessage("validation.revenueTotal", contextInfo), toMap(field)));
            results.add(valRes);
        }

        return results;
    }
}
