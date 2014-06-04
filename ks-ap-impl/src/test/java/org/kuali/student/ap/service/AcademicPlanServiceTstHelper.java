package org.kuali.student.ap.service;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r1.lum.course.service.CourseServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.jws.WebParam;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        createType(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, "Learning Plan Item","Learning plan item type.", "http://student.kuali.org/wsdl/acadplan/PlanItemInfo");
        createType(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY, "Credit Course", "Credit Course type.",
                "http://student.kuali.org/wsdl/lum/CourseInfo");
        createType(TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY,"Type-Type relationships Allowed",
                "Type-Type relationships Allowed", "http://student.kuali.org/wsdl/type/TypeTypeRelationInfo");
        createTypeType(TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY,AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE,
                CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
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
    void createTypeType(String typeTypeRelationTypeKey, String ownerTypeKey, String relatedTypeKey) throws Exception {
        TypeTypeRelationInfo typeType = new TypeTypeRelationInfo();
        typeType.setTypeKey(typeTypeRelationTypeKey);
        typeType.setOwnerTypeKey(ownerTypeKey);
        typeType.setRelatedTypeKey(relatedTypeKey);
        typeType.setEffectiveDate(new Date());
        KsapFrameworkServiceLocator.getTypeService().createTypeTypeRelation(typeTypeRelationTypeKey, ownerTypeKey,
                relatedTypeKey, typeType, KsapFrameworkServiceLocator.getContext().getContextInfo());
    }
    public void createMockCourses() throws Exception {
        CourseInfo course1 = this._createCourse("ENGL101", "ENGL", "101", "English 101", "3.00",
                KsapFrameworkServiceLocator.getContext().getContextInfo());
        course1 = this._createCourse("CHEM131", "CHEM", "131", "English 131", "4.00",
                KsapFrameworkServiceLocator.getContext().getContextInfo());
        course1 = this._createCourse("BSCI121", "BSCI", "121", "Biology 121", "5.00",
                KsapFrameworkServiceLocator.getContext().getContextInfo());
        course1 = this._createCourse("BSCI122", "BSCI", "122", "Biology 122", "5.00",
                KsapFrameworkServiceLocator.getContext().getContextInfo());
        course1 = this._createCourse("BSCI123", "BSCI", "123", "Biology 123", "5.00",
                KsapFrameworkServiceLocator.getContext().getContextInfo());
        course1 = this._createCourse("ENGL205", "ENGL", "205", "English 205", "3.00",
                KsapFrameworkServiceLocator.getContext().getContextInfo());
        course1 = this._createCourse("ENGL206", "ENGL", "206", "English 206", "3.00",
                KsapFrameworkServiceLocator.getContext().getContextInfo());
    }
    private CourseInfo _createCourse(String code, String subjArea, String courseNumb, String courseTitle, String credits, ContextInfo contextInfo) {
        CourseInfo info = new CourseInfo();
        info.setId(code);
        info.setCode(code);
        info.setSubjectArea(subjArea);
        info.setCourseNumberSuffix(courseNumb);
        info.setTypeKey(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        info.setStateKey("Active");
        info.setCourseTitle(courseTitle);
        info.setName(courseTitle);
        List<ResultValuesGroupInfo> creditOptions = Arrays.asList(this._createCreditOptions(credits, contextInfo));
        info.setCreditOptions(creditOptions);
        try {
            info = KsapFrameworkServiceLocator.getCourseService().createCourse(info, contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException("error creating course", ex);
        }
        return info;
    }

    private ResultValuesGroupInfo _createCreditOptions(String credits, ContextInfo context) {
        ResultValuesGroupInfo info = new ResultValuesGroupInfo();
        info.setKey("kuali.creditType.credit.degree." + credits);
        info.setName(credits);
        info.setResultValueKeys(Arrays.asList(credits));
        return info;
    }


}
