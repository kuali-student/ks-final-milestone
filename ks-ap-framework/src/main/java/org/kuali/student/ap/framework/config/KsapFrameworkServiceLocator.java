package org.kuali.student.ap.framework.config;

import java.util.Hashtable;
import java.util.Map;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.ap.framework.context.AtpHelper;
import org.kuali.student.ap.framework.context.EnumerationHelper;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.ap.framework.context.OrgHelper;
import org.kuali.student.ap.framework.context.UserSessionHelper;
import org.kuali.student.ap.framework.course.CourseSearchStrategy;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.myplan.academicplan.service.AcademicPlanService;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.program.service.ProgramService;

/**
 * Convenience factory for acquiring KSAP provided service.
 * 
 * <p>
 * Remote services commonly used by ks-ap are also provided by this locator.
 * </p>
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ksap-0.1.1
 */
public final class KsapFrameworkServiceLocator {

	/**
	 * Private constructor.
	 */
	private KsapFrameworkServiceLocator() {
	}

	/**
	 * Service cache, for minimizing calls to GlobalResourceLoader.
	 */
	private static final Map<String, Object> SERVICE_CACHE = new Hashtable<String, Object>(
			16);

	/**
	 * Get a local service.
	 * 
	 * @param serviceName
	 *            The local service name.
	 * @return The local service.
	 * @throws AssertionError
	 *             If assertions are enabled and the service doesn't exist.
	 */
	private static <T> T getLocalService(String serviceName) {
		@SuppressWarnings("unchecked")
		T rv = (T) SERVICE_CACHE.get(serviceName);
		if (rv == null) {
			rv = GlobalResourceLoader.getService(serviceName);
			if (rv != null) {
				SERVICE_CACHE.put(serviceName, rv);
			}
		}
		assert rv != null : serviceName + " not defined in Rice";
		return rv;
	}

	/**
	 * Get the ks-core remote ATP service.
	 * 
	 * @return The ks-core remote ATP service.
	 */
	public static AtpService getAtpService() {
		return getLocalService("ksCoreAtpService");
	}

	/**
	 * Get the ks-core remote type service.
	 * 
	 * @return The ks-core remote type service.
	 */
	public static TypeService getTypeService() {
		return getLocalService("ksCoreTypeService");
	}

	/**
	 * Get the ks-core remote message service.
	 * 
	 * @return The ks-core remote message service.
	 */
	public static MessageService getMessageService() {
		return getLocalService("ksCoreMessageService");
	}

	/**
	 * Get the ks-core remote organization service.
	 * 
	 * @return The ks-core remote organization service.
	 */
	public static OrganizationService getOrganizationService() {
		return getLocalService("ksCoreOrganizationService");
	}

	/**
	 * Get the ks-core remote enumeration service.
	 * 
	 * @return The ks-core remote enumeration service.
	 */
	public static EnumerationManagementService getEnumerationManagementService() {
		return getLocalService("ksCoreEnumerationManagementService");
	}

	/**
	 * Get the ks-enroll remote course offering service.
	 * 
	 * @return The ks-enroll remote course offering service.
	 */
	public static CourseOfferingService getCourseOfferingService() {
		return getLocalService("ksEnrollCourseOfferingService");
	}

	/**
	 * Get the ks-enroll remote academic calendar service.
	 * 
	 * @return The ks-enroll remote message service.
	 */
	public static AcademicCalendarService getAcademicCalendarService() {
		return getLocalService("ksEnrollAcalService");
	}

	/**
	 * Get the ks-enroll remote academic record service.
	 * 
	 * @return The ks-enroll remote message service.
	 */
	public static AcademicRecordService getAcademicRecordService() {
		return getLocalService("ksEnrollAcademicRecordService");
	}

	/**
	 * Get the ks-lum remote course service.
	 * 
	 * @return The ks-lum remote course service.
	 */
	public static CourseService getCourseService() {
		return getLocalService("ksLumCourseService");
	}

	/**
	 * Get the program service.
	 * 
	 * @return The program service.
	 */
	public static ProgramService getProgramService() {
		return getLocalService("ksLumProgramService");
	}

	/**
	 * Get the LRC service.
	 * 
	 * @return The LRC service.
	 */
	public static LRCService getLRCService() {
		return getLocalService("ksLumLRCService");
	}

	/**
	 * Get the ks-lum remote clu service.
	 * 
	 * @return The ks-lum remote clu service.
	 */
	public static CluService getCluService() {
		return getLocalService("ksLumCluService");
	}

    /**
     * Get the ks-lum remote lrc service.
     *
     * @return The ks-lum remote lrc service.
     */
    public static LRCService getLrcService(){
        return getLocalService("ksLumLrcService");
    }

	/**
	 * Get the KSAP context provider.
	 * 
	 * @return The KSAP context provider.
	 */
	public static KsapContext getContext() {
		return getLocalService("ksapContext");
	}

	/**
	 * Get the user session helper.
	 * 
	 * @return The user session helper.
	 */
	public static UserSessionHelper getUserSessionHelper() {
		return getLocalService("ksapUserSessionHelper");
	}

	/**
	 * Get the ATP helper.
	 * 
	 * @return The ATP help.
	 */
	public static AtpHelper getAtpHelper() {
		return getLocalService("ksapAtpHelper");
	}

	/**
	 * Get the Enumeration helper.
	 * 
	 * @return The Enumeration help.
	 */
	public static EnumerationHelper getEnumerationHelper() {
		return getLocalService("ksapEnumerationHelper");
	}

	/**
	 * Get the Org helper.
	 * 
	 * @return The Org help.
	 */
	public static OrgHelper getOrgHelper() {
		return getLocalService("ksapOrgHelper");
	}

	/**
	 * Get the academic plan service.
	 * 
	 * @return The academic plan service.
	 */
	public static AcademicPlanService getAcademicPlanService() {
		return getLocalService("academicPlanService");
	}

	/**
	 * Get the course search strategy.
	 * 
	 * @return The course search strategy.
	 */
	public static CourseSearchStrategy getCourseSearchStrategy() {
		return getLocalService("courseSearchStrategy");
	}

}
