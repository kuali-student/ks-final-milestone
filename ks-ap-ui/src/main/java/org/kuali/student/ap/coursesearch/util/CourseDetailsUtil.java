package org.kuali.student.ap.coursesearch.util;

import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.utils.CourseLinkBuilder;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that is used to look up course requisites for a course or course offering
 */
public class CourseDetailsUtil {

    /**
     * Retrieve and format the list of course requisites to be displayed on the page
     *
     * @param course - Course that is being displayed
     * @return Formatted list of requisites
     */
    public static List<String> getCourseRequisites(Course course){
        List<String> courseRequisites = new ArrayList<String>();
        try {
            TypeInfo typeInfo = KsapFrameworkServiceLocator.getTypeService().getType(
                    course.getTypeKey(), KsapFrameworkServiceLocator.getContext().getContextInfo());
            courseRequisites = getRequisites(course.getId(), typeInfo.getRefObjectUri());
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        }
        return courseRequisites;
    }

    /**
     * Retrieve and format the list of course requisites to be displayed on the page
     * @param courseOffering - course offering that is being displayed
     * @return Formatted list of requisites
     */
    public static List<String> getCourseOfferingRequisites(CourseOffering courseOffering){
        String coType = CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING;
        return getRequisites(courseOffering.getId(), coType);

    }

    /**
     * Retrieve and format the list of course requisites to be displayed on the page
     * @param id - object used in the lookup
     * @param type - object used in the lookup
     * @return Formatted list of requisites
     */
    private static List<String> getRequisites(String id, String type){
        List<String> courseRequisites = new ArrayList<String>();
        RuleManagementService rms = KsapFrameworkServiceLocator.getRuleManagementService();
        // Gather components for natural language translation
        List<ReferenceObjectBinding> referenceObjectBindings = rms.findReferenceObjectBindingsByReferenceObject(
                type, id);
        String language = KsapFrameworkServiceLocator.getContext().getContextInfo().getLocale().getLocaleLanguage();

        // Get requisites as natural language descriptions
        for(ReferenceObjectBinding referenceObjectBinding : referenceObjectBindings){
            NaturalLanguageUsage nlu = rms.getNaturalLanguageUsageByNameAndNamespace(
                    KSKRMSServiceConstants.KRMS_NL_RULE_EDIT, referenceObjectBinding.getNamespace());
            String description = rms.translateNaturalLanguageForObject(
                    nlu.getId(),referenceObjectBinding.getKrmsDiscriminatorType().toLowerCase(),
                    referenceObjectBinding.getKrmsObjectId(),language);
            String formattedDescription = CourseLinkBuilder.makeLinks(description, KsapFrameworkServiceLocator.getContext().getContextInfo());
            courseRequisites.add(formattedDescription);

        }

        return courseRequisites;
    }
}
