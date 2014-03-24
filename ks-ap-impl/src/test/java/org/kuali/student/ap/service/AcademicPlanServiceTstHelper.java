package org.kuali.student.ap.service;

import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.Date;

/**
 * Utility class to support junit tests of the AcademicPlanService
 */
public class AcademicPlanServiceTstHelper {
    public AcademicPlanServiceTstHelper() {
    }

    public void createKsapTypes() throws Exception {
        createType(AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN, "Learning Plan", "Student learning plan type.", "http://student.kuali.org/wsdl/acadplan/LearningPlanInfo");
        createType(AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN_TEMPLATE, "Learning Plan Template", "Student learning plan template type.", "http://student.kuali.org/wsdl/acadplan/LearningPlanInfo");
        createType(AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN_REVIEW, "Learning Plan Review", "Student learning plan review type.", "http://student.kuali.org/wsdl/acadplan/LearningPlanInfo");
        createType(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, "Academic Plan Item", "Academic plan item type.", "http://student.kuali.org/wsdl/acadplan/PlanItemInfo");
    }
    void createType(String typeKey, String typeName, String typeDescription, String refObjectUri) throws Exception {
        TypeInfo type = new TypeInfo();
        type.setKey(typeKey);
        type.setName(typeName);
        type.setDescr(new RichTextHelper().fromPlain(typeDescription));
        type.setRefObjectUri(refObjectUri);
        type.setEffectiveDate(new Date());
        KsapFrameworkServiceLocator.getTypeService().createType(type.getKey(), type,
                KsapFrameworkServiceLocator.getContext().getContextInfo());
    }

}
