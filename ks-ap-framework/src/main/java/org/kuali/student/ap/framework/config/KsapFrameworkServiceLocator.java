package org.kuali.student.ap.framework.config;

import javax.ejb.EJB;

import org.kuali.student.ap.framework.context.AtpHelper;
import org.kuali.student.ap.framework.context.EnrollmentStatusHelper;
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
 * @version 0.4.5
 * @since 0.1.1
 */
public final class KsapFrameworkServiceLocator {

	/**
	 * Internally managed singleton instance.
	 */
	private static KsapFrameworkServiceLocator instance;

	/**
	 * Get a singleton instance.
	 * <p>
	 * This method should be indicated as the factory method by at least one
	 * bean in an auto-wiring container in order to populate {@link EJB}
	 * instances.
	 * </p>
	 * <pre>
	 * &lt;bean class=&quot;org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator&quot;
	 * 	factory-method=&quot;getInstance&quot; /&gt;
	 * </pre>
	 */
	public static KsapFrameworkServiceLocator getInstance() {
		return instance == null ? instance = new KsapFrameworkServiceLocator()
				: instance;
	}

	/**
	 * Get the ks-core remote ATP service.
	 * 
	 * @return The ks-core remote ATP service.
	 */
	public static AtpService getAtpService() {
		return getInstance().ksCoreAtpService;
	}

	/**
	 * Get the ks-core remote type service.
	 * 
	 * @return The ks-core remote type service.
	 */
	public static TypeService getTypeService() {
		return getInstance().ksCoreTypeService;
	}

	/**
	 * Get the ks-core remote message service.
	 * 
	 * @return The ks-core remote message service.
	 */
	public static MessageService getMessageService() {
		return getInstance().ksCoreMessageService;
	}

	/**
	 * Get the ks-core remote organization service.
	 * 
	 * @return The ks-core remote organization service.
	 */
	public static OrganizationService getOrganizationService() {
		return getInstance().ksCoreOrganizationService;
	}

	/**
	 * Get the ks-core remote enumeration service.
	 * 
	 * @return The ks-core remote enumeration service.
	 */
	public static EnumerationManagementService getEnumerationManagementService() {
		return getInstance().ksCoreEnumerationManagementService;
	}

	/**
	 * Get the ks-enroll remote course offering service.
	 * 
	 * @return The ks-enroll remote course offering service.
	 */
	public static CourseOfferingService getCourseOfferingService() {
		return getInstance().ksEnrollCourseOfferingService;
	}

	/**
	 * Get the ks-enroll remote academic calendar service.
	 * 
	 * @return The ks-enroll remote message service.
	 */
	public static AcademicCalendarService getAcademicCalendarService() {
		return getInstance().ksEnrollAcalService;
	}

	/**
	 * Get the ks-enroll remote academic record service.
	 * 
	 * @return The ks-enroll remote message service.
	 */
	public static AcademicRecordService getAcademicRecordService() {
		return getInstance().ksEnrollAcademicRecordService;
	}

	/**
	 * Get the ks-lum remote course service.
	 * 
	 * @return The ks-lum remote course service.
	 */
	public static CourseService getCourseService() {
		return getInstance().ksLumCourseService;
	}

	/**
	 * Get the program service.
	 * 
	 * @return The program service.
	 */
	public static ProgramService getProgramService() {
		return getInstance().ksLumProgramService;
	}

	/**
	 * Get the LRC service.
	 * 
	 * @return The LRC service.
	 */
	public static LRCService getLRCService() {
		return getInstance().ksLumLrcService;
	}

	/**
	 * Get the ks-lum remote clu service.
	 * 
	 * @return The ks-lum remote clu service.
	 */
	public static CluService getCluService() {
		return getInstance().ksLumCluService;
	}

	/**
	 * Get the ks-lum remote lrc service.
	 * 
	 * @return The ks-lum remote lrc service.
	 */
	public static LRCService getLrcService() {
		return getInstance().ksLumLrcService;
	}

	/**
	 * Get the KSAP context provider.
	 * 
	 * @return The KSAP context provider.
	 */
	public static KsapContext getContext() {
		return getInstance().ksapContext;
	}

	/**
	 * Get the user session helper.
	 * 
	 * @return The user session helper.
	 */
	public static UserSessionHelper getUserSessionHelper() {
		return getInstance().ksapUserSessionHelper;
	}

	/**
	 * Get the ATP helper.
	 * 
	 * @return The ATP help.
	 */
	public static AtpHelper getAtpHelper() {
		return getInstance().ksapAtpHelper;
	}

	/**
	 * Get the Enumeration helper.
	 * 
	 * @return The Enumeration help.
	 */
	public static EnumerationHelper getEnumerationHelper() {
		return getInstance().ksapEnumerationHelper;
	}

	/**
	 * Get the Org helper.
	 * 
	 * @return The Org help.
	 */
	public static OrgHelper getOrgHelper() {
		return getInstance().ksapOrgHelper;
	}

	/**
	 * Get the academic plan service.
	 * 
	 * @return The academic plan service.
	 */
	public static AcademicPlanService getAcademicPlanService() {
		return getInstance().academicPlanService;
	}

	/**
	 * Get the course search strategy.
	 * 
	 * @return The course search strategy.
	 */
	public static CourseSearchStrategy getCourseSearchStrategy() {
		return getInstance().courseSearchStrategy;
	}

    /**
     * Get the course search strategy.
     *
     * @return The course search strategy.
     */
    public static EnrollmentStatusHelper getEnrollmentStatusHelper() {
        return getInstance().enrollmentStatusHelper;
    }

	@EJB
	private transient AtpService ksCoreAtpService;
	@EJB
	private transient TypeService ksCoreTypeService;
	@EJB
	private transient MessageService ksCoreMessageService;
	@EJB
	private transient OrganizationService ksCoreOrganizationService;
	@EJB
	private transient EnumerationManagementService ksCoreEnumerationManagementService;
	@EJB
	private transient CourseOfferingService ksEnrollCourseOfferingService;
	@EJB
	private transient AcademicCalendarService ksEnrollAcalService;
	@EJB
	private transient AcademicRecordService ksEnrollAcademicRecordService;
	@EJB
	private transient CourseService ksLumCourseService;
	@EJB
	private transient ProgramService ksLumProgramService;
	@EJB
	private transient CluService ksLumCluService;
	@EJB
	private transient LRCService ksLumLrcService;
	@EJB
	private transient KsapContext ksapContext;
	@EJB
	private transient UserSessionHelper ksapUserSessionHelper;
	@EJB
	private transient AtpHelper ksapAtpHelper;
	@EJB
	private transient EnumerationHelper ksapEnumerationHelper;
	@EJB
	private transient OrgHelper ksapOrgHelper;
	@EJB
	private transient AcademicPlanService academicPlanService;
    @EJB
    private transient EnrollmentStatusHelper enrollmentStatusHelper;
	@EJB
	@OptionalResource
	// provided by ks-ap-ui or institution override
	private transient CourseSearchStrategy courseSearchStrategy;

	private KsapFrameworkServiceLocator() {
	}

}
