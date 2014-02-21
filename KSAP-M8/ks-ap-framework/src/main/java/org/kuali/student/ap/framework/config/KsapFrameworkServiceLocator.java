package org.kuali.student.ap.framework.config;

import javax.ejb.EJB;

import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.ap.framework.context.EnrollmentStatusHelper;
import org.kuali.student.ap.framework.context.EnumerationHelper;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.ap.framework.context.OrgHelper;
import org.kuali.student.ap.framework.context.PlanHelper;
import org.kuali.student.ap.framework.context.ShoppingCartHelper;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.TextHelper;
import org.kuali.student.ap.framework.context.UserSessionHelper;
import org.kuali.student.ap.framework.course.CourseSearchStrategy;
import org.kuali.student.ap.plannerreview.LearningPlanReviewStrategy;
import org.kuali.student.ap.schedulebuilder.ScheduleBuildStrategy;
import org.kuali.student.ap.schedulebuilder.ShoppingCartStrategy;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.comment.service.CommentService;
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
	 * 
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
		return getInstance().atpService;
	}

	/**
	 * Get the ks-core remote type service.
	 * 
	 * @return The ks-core remote type service.
	 */
	public static TypeService getTypeService() {
		return getInstance().typeService;
	}

	/**
	 * Get the ks-core remote message service.
	 * 
	 * @return The ks-core remote message service.
	 */
	public static MessageService getMessageService() {
		return getInstance().messageService;
	}

	/**
	 * Get the ks-core remote organization service.
	 * 
	 * @return The ks-core remote organization service.
	 */
	public static OrganizationService getOrganizationService() {
		return getInstance().organizationService;
	}

	/**
	 * Get the ks-core remote enumeration service.
	 * 
	 * @return The ks-core remote enumeration service.
	 */
	public static EnumerationManagementService getEnumerationManagementService() {
		return getInstance().enumerationManagementService;
	}

	/**
	 * Get the ks-core remote comment service.
	 *
	 * @return The ks-core remote comment service.
	 */
	public static CommentService getCommentService() {
		return getInstance().commentService;
	}

	/**
	 * Get the ks-enroll remote LUI service.
	 * 
	 * @return The ks-enroll remote LUI service.
	 */
	public static LuiService getLuiService() {
		return getInstance().luiService;
	}

	/**
	 * Get the ks-enroll remote course offering service.
	 * 
	 * @return The ks-enroll remote course offering service.
	 */
	public static CourseOfferingService getCourseOfferingService() {
		return getInstance().courseOfferingService;
	}
    
    public static CourseOfferingSetService getCourseOfferingSetService() {
        return getInstance().courseOfferingSetService;
    }

	/**
	 * Get the ks-enroll remote academic calendar service.
	 * 
	 * @return The ks-enroll remote message service.
	 */
	public static AcademicCalendarService getAcademicCalendarService() {
		return getInstance().academicCalendarService;
	}

	/**
	 * Get the ks-enroll remote academic record service.
	 * 
	 * @return The ks-enroll remote message service.
	 */
	public static AcademicRecordService getAcademicRecordService() {
		return getInstance().academicRecordService;
	}

	/**
	 * Get the ks-lum remote course service.
	 * 
	 * @return The ks-lum remote course service.
	 */
	public static CourseService getCourseService() {
		return getInstance().courseService;
	}

	/**
	 * Get the ks-lum remote program service.
	 * 
	 * @return The ks-lum remote program service.
	 */
	public static ProgramService getProgramService() {
		return getInstance().programService;
	}

	/**
	 * Get the ks-lum remote clu service.
	 * 
	 * @return The ks-lum remote clu service.
	 */
	public static CluService getCluService() {
		return getInstance().cluService;
	}

	/**
	 * Get the ks-lum remote lrc service.
	 * 
	 * @return The ks-lum remote lrc service.
	 */
	public static LRCService getLrcService() {
		return getInstance().lrcService;
	}

    /**
     * Get the KS course registration service
     *
     * @return the KS course registration service
     */
    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
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
	public static TermHelper getTermHelper() {
		return getInstance().ksapTermHelper;
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
	 * Get the course helper.
	 * 
	 * @return The course helper.
	 */
	public static CourseHelper getCourseHelper() {
		return getInstance().ksapCourseHelper;
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

	/**
	 * Get the shopping cart helper implementation.
	 * 
	 * @return The shopping cart helper.
	 */
	public static ShoppingCartHelper getShoppingCartHelper() {
		return getInstance().shoppingCartHelper;
	}

	/**
	 * Get the message text helper.
	 * 
	 * @return The message text helper.
	 */
	public static TextHelper getTextHelper() {
		return getInstance().ksapTextHelper;
	}

    /**
     * Get the plan helper implementation.
     *
     * @return The plan helper.
     */
    public static PlanHelper getPlanHelper() {
        return getInstance().planHelper;
    }

	/**
	 * Get the schedule build strategy.
	 *
	 * @return The schedule build strategy.
	 */
    public static ScheduleBuildStrategy getScheduleBuildStrategy() {
        return getInstance().scheduleBuildStrategy;
    }

    /**
     * Get the shopping cart strategy.
     *
     * @return The shopping cart strategy.
     */
    public static ShoppingCartStrategy getShoppingCartStrategy() {
        return getInstance().shoppingCartStrategy;
    }

    public static LearningPlanReviewStrategy getLearningPlanReviewStrategy() {
        return getInstance().learningPlanReviewStrategy;
    }

    public void setAtpService(AtpService atpService) {
        getInstance().atpService = atpService;
    }

    public void setTypeService(TypeService typeService) {
        getInstance().typeService = typeService;
    }

    public void setMessageService(MessageService messageService) {
        getInstance().messageService = messageService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        getInstance().organizationService = organizationService;
    }

    public void setEnumerationManagementService(EnumerationManagementService enumerationManagementService) {
        getInstance().enumerationManagementService = enumerationManagementService;
    }

    public void setCommentService(CommentService commentService) {
        getInstance().commentService = commentService;
    }

    public void setLuiService(LuiService luiService) {
        getInstance().luiService = luiService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        getInstance().courseOfferingService = courseOfferingService;
    }

    public void setCourseOfferingSetService(CourseOfferingSetService courseOfferingSetService) {
        getInstance().courseOfferingSetService = courseOfferingSetService;
    }

    public void setAcademicCalendarService(AcademicCalendarService academicCalendarService) {
        getInstance().academicCalendarService = academicCalendarService;
    }

    public void setAcademicRecordService(AcademicRecordService academicRecordService) {
        getInstance().academicRecordService = academicRecordService;
    }

    public void setCourseService(CourseService courseService) {
        getInstance().courseService = courseService;
    }

    public void setProgramService(ProgramService programService) {
        getInstance().programService = programService;
    }

    public void setCluService(CluService cluService) {
        getInstance().cluService = cluService;
    }

    public void setLrcService(LRCService lrcService) {
        getInstance().lrcService = lrcService;
    }


    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    public void setKsapContext(KsapContext ksapContext) {
        getInstance().ksapContext = ksapContext;
    }

    public void setKsapUserSessionHelper(UserSessionHelper ksapUserSessionHelper) {
        getInstance().ksapUserSessionHelper = ksapUserSessionHelper;
    }

    public void setKsapTermHelper(TermHelper ksapTermHelper) {
        getInstance().ksapTermHelper = ksapTermHelper;
    }

    public void setKsapEnumerationHelper(EnumerationHelper ksapEnumerationHelper) {
        getInstance().ksapEnumerationHelper = ksapEnumerationHelper;
    }

    public void setKsapOrgHelper(OrgHelper ksapOrgHelper) {
        getInstance().ksapOrgHelper = ksapOrgHelper;
    }

    public void setKsapCourseHelper(CourseHelper ksapCourseHelper) {
        getInstance().ksapCourseHelper = ksapCourseHelper;
    }

    public void setKsapTextHelper(TextHelper ksapTextHelper) {
        getInstance().ksapTextHelper = ksapTextHelper;
    }

    public void setAcademicPlanService(AcademicPlanService academicPlanService) {
        getInstance().academicPlanService = academicPlanService;
    }

    public void setEnrollmentStatusHelper(EnrollmentStatusHelper enrollmentStatusHelper) {
        getInstance().enrollmentStatusHelper = enrollmentStatusHelper;
    }

    public void setShoppingCartHelper(ShoppingCartHelper shoppingCartHelper) {
        getInstance().shoppingCartHelper = shoppingCartHelper;
    }

    public void setPlanHelper(PlanHelper planHelper) {
        getInstance().planHelper = planHelper;
    }

    public void setCourseSearchStrategy(CourseSearchStrategy courseSearchStrategy) {
        getInstance().courseSearchStrategy = courseSearchStrategy;
    }

    public void setLearningPlanReviewStrategy(LearningPlanReviewStrategy learningPlanReviewStrategy) {
        getInstance().learningPlanReviewStrategy = learningPlanReviewStrategy;
    }

    public void setShoppingCartStrategy(ShoppingCartStrategy shoppingCartStrategy) {
        getInstance().shoppingCartStrategy = shoppingCartStrategy;
    }

    public void setScheduleBuildStrategy(ScheduleBuildStrategy scheduleBuildStrategy) {
        getInstance().scheduleBuildStrategy = scheduleBuildStrategy;
    }    

	@EJB
	private transient AtpService atpService;
	@EJB
	private transient TypeService typeService;
	@EJB
	private transient MessageService messageService;
	@EJB
	private transient OrganizationService organizationService;
	@EJB
	private transient EnumerationManagementService enumerationManagementService;
	@EJB
	private transient CommentService commentService;
	@EJB
	private transient LuiService luiService;
	@EJB
    private transient CourseOfferingService courseOfferingService;
    @EJB
    private transient CourseOfferingSetService courseOfferingSetService;
	@EJB
	private transient AcademicCalendarService academicCalendarService;
	@EJB
	private transient AcademicRecordService academicRecordService;
	@EJB
	private transient CourseService courseService;
	@EJB
	private transient ProgramService programService;
	@EJB
	private transient CluService cluService;
	@EJB
	private transient LRCService lrcService;
    @EJB
    private transient CourseRegistrationService courseRegistrationService;
	@EJB
	private transient KsapContext ksapContext;
	@EJB
	private transient UserSessionHelper ksapUserSessionHelper;
	@EJB
	private transient TermHelper ksapTermHelper;
	@EJB
	private transient EnumerationHelper ksapEnumerationHelper;
	@EJB
	private transient OrgHelper ksapOrgHelper;
	@EJB
	private transient CourseHelper ksapCourseHelper;
	@EJB
	private transient TextHelper ksapTextHelper;
	@EJB
	private transient AcademicPlanService academicPlanService;
	@EJB
	private transient EnrollmentStatusHelper enrollmentStatusHelper;
	@EJB
	private transient ShoppingCartHelper shoppingCartHelper;
    @EJB
    private transient PlanHelper planHelper;

	// provided by ks-ap-ui or institution override
	@EJB
    @OptionalResource
    private transient CourseSearchStrategy courseSearchStrategy;

    @OptionalResource
    @EJB
    private transient LearningPlanReviewStrategy learningPlanReviewStrategy;
    @OptionalResource
    @EJB
    private transient ScheduleBuildStrategy scheduleBuildStrategy;


    @OptionalResource
    @EJB
    private transient ShoppingCartStrategy shoppingCartStrategy;

	private KsapFrameworkServiceLocator() {
	}

}
