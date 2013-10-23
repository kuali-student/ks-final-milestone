package org.kuali.student.r2.common.krms.type;

import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.AdminOrgPermissionTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.AdmittedProgramAtCourseCampusTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.AdmittedProgramTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.AdmittedProgramWithClassStandingTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CompletedCourseAsOfTermTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CompletedCourseBetweenTermsTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CompletedCourseForTermTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CompletedCoursePriorToTermTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CompletedCourseTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CompletedCoursesTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CourseWithGradeTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CoursesWithGradeTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CreditsEarnedFromCoursesTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CreditsEarnedFromOrganizationTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.CreditsEarnedTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.EnrolledCourseTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.EnrolledCoursesTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.FreeFormTextTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.GPAForCoursesTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.GPAForDurationTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.GPATermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.InstructorPermissionTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.NumberOfCompletedCoursesTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.NumberOfCoursesWithGradeTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.NumberOfEnrolledCoursesTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.PopulationTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.ProgramCoursesOrgDurationTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.ScoreTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.CluIdsInCluSetTermResolver;
import org.kuali.student.enrollment.class2.examoffering.krms.termresolver.MatchingCourseSetTermResolver;
import org.kuali.student.enrollment.class2.examoffering.krms.termresolver.MatchingCourseTermResolver;
import org.kuali.student.enrollment.class2.examoffering.krms.termresolver.MatchingTimeSlotTermResolver;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;

public class KSTermResolverTypeService implements TermResolverTypeService {

	private AcademicRecordService acadRecordService;
    private CourseRegistrationService courseRegistrationService;
    private OrganizationService organizationService;
    private AtpService atpService;
    private CourseOfferingService courseOfferingService;
    private LRCService lrcService;
    private SchedulingService schedulingService;
    private CluService cluService;
    private CourseService courseService;
	
	@Override
	public TermResolver<?> loadTermResolver(
			TermResolverDefinition termResolverDefinition) {

        if(KSKRMSServiceConstants.TERM_RESOLVER_ADMINORGANIZATIONPERMISSIONREQUIRED.equals(termResolverDefinition.getName())) {
            AdminOrgPermissionTermResolver resolver = new AdminOrgPermissionTermResolver();
            resolver.setOrganizationService(organizationService);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_ADMITTEDTOPROGRAMATCOURSECAMPUS.equals(termResolverDefinition.getName())){
            AdmittedProgramAtCourseCampusTermResolver resolver = new AdmittedProgramAtCourseCampusTermResolver();
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_ADMITTEDTOPROGRAM.equals(termResolverDefinition.getName())){
            AdmittedProgramTermResolver resolver = new AdmittedProgramTermResolver();
            resolver.setAcademicRecordService(acadRecordService);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_ADMITTEDTOPROGRAMWITHCLASSSTANDING.equals(termResolverDefinition.getName())){
            AdmittedProgramWithClassStandingTermResolver resolver = new AdmittedProgramWithClassStandingTermResolver();
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEFORTERM.equals(termResolverDefinition.getName())){
            CompletedCourseAsOfTermTermResolver resolver = new CompletedCourseAsOfTermTermResolver();
            resolver.setAcademicRecordService(acadRecordService);
            resolver.setAtpService(atpService);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEBETWEENTERMS.equals(termResolverDefinition.getName())){
            CompletedCourseBetweenTermsTermResolver resolver = new CompletedCourseBetweenTermsTermResolver();
            resolver.setAcademicRecordService(acadRecordService);
            resolver.setAtpService(atpService);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEFORTERM.equals(termResolverDefinition.getName())){
            CompletedCourseForTermTermResolver resolver = new CompletedCourseForTermTermResolver();
            resolver.setAcademicRecordService(acadRecordService);
            resolver.setCourseOfferingService(courseOfferingService);
            resolver.setCluIdsTermResolver(null);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEPRIORTOTERM.equals(termResolverDefinition.getName())){
            CompletedCoursePriorToTermTermResolver resolver = new CompletedCoursePriorToTermTermResolver();
            resolver.setAcademicRecordService(acadRecordService);
            resolver.setAtpService(atpService);
            resolver.setCluIdsTermResolver(null);
            resolver.setAtpForCOIdTermResolver(null);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSES.equals(termResolverDefinition.getName())){
            CompletedCoursesTermResolver resolver = new CompletedCoursesTermResolver();
            resolver.setCluIdsInCluSetTermResolver(null);
            resolver.setCompletedCourseTermResolver(null);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSE.equals(termResolverDefinition.getName())){
            CompletedCourseTermResolver resolver = new CompletedCourseTermResolver();
            resolver.setAcademicRecordService(acadRecordService);
            resolver.setCluIdsTermResolver(null);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_COURSESWITHGRADE.equals(termResolverDefinition.getName())){
            CoursesWithGradeTermResolver resolver = new CoursesWithGradeTermResolver();
            resolver.setCluIdsInCluSetTermResolver(null);
            resolver.setCourseWithGradeTermResolver(null);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_COURSEWITHGRADE.equals(termResolverDefinition.getName())){
            CourseWithGradeTermResolver resolver = new CourseWithGradeTermResolver();
            resolver.setLrcService(lrcService);
            resolver.setCourseRecordsForCourseIdTermResolver(null);
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCREDITSFROMCOMPLETEDCOURSES.equals(termResolverDefinition.getName())){
            CreditsEarnedFromCoursesTermResolver resolver = new CreditsEarnedFromCoursesTermResolver();
            resolver.setCourseRecordsForCourseSetTermResolver(null);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCREDITSFROMORGANIZATION.equals(termResolverDefinition.getName())){
            CreditsEarnedFromOrganizationTermResolver resolver = new CreditsEarnedFromOrganizationTermResolver();
            resolver.setAcademicRecordService(acadRecordService);
            resolver.setCourseOfferingService(courseOfferingService);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCREDITSEARNED.equals(termResolverDefinition.getName())){
            CreditsEarnedTermResolver resolver = new CreditsEarnedTermResolver();
            resolver.setAcademicRecordService(acadRecordService);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_ENROLLEDCOURSES.equals(termResolverDefinition.getName())){
            EnrolledCourseTermResolver resolver = new EnrolledCourseTermResolver();
            resolver.setCourseOfferingService(courseOfferingService);
            resolver.setCourseRegistrationService(courseRegistrationService);
            resolver.setCluIdsTermResolver(null);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_ENROLLEDCOURSE.equals(termResolverDefinition.getName())){
            EnrolledCoursesTermResolver resolver = new EnrolledCoursesTermResolver();
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_SPEC_FREEFORMTEXT.equals(termResolverDefinition.getName())){
            FreeFormTextTermResolver resolver = new FreeFormTextTermResolver();
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_GPAFORCOURSES.equals(termResolverDefinition.getName())){
            GPAForCoursesTermResolver resolver = new GPAForCoursesTermResolver();
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_GPAFORDURATION.equals(termResolverDefinition.getName())){
            GPAForDurationTermResolver resolver = new GPAForDurationTermResolver();
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_GPA.equals(termResolverDefinition.getName())){
            GPATermResolver resolver = new GPATermResolver();
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_INSTRUCTORPERMISSION.equals(termResolverDefinition.getName())){
            InstructorPermissionTermResolver resolver = new InstructorPermissionTermResolver();
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCOMPLETEDCOURSES.equals(termResolverDefinition.getName())){
            NumberOfCompletedCoursesTermResolver resolver = new NumberOfCompletedCoursesTermResolver();
            resolver.setCluIdsInCluSetTermResolver(null);
            resolver.setCompletedCourseTermResolver(null);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_SPEC_NUMBEROFCOURSESWITHGRADE.equals(termResolverDefinition.getName())){
            NumberOfCoursesWithGradeTermResolver resolver = new NumberOfCoursesWithGradeTermResolver();
            resolver.setCluIdsInCluSetTermResolver(null);
            resolver.setCourseWithGradeTermResolver(null);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFENROLLEDCOURSES.equals(termResolverDefinition.getName())){
            NumberOfEnrolledCoursesTermResolver resolver = new NumberOfEnrolledCoursesTermResolver();
            resolver.setCluIdsInCluSetTermResolver(null);
            resolver.setEnrolledCourseTermResolver(null);
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_POPULATION.equals(termResolverDefinition.getName())){
            PopulationTermResolver resolver = new PopulationTermResolver();
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_ADMITTEDTOPROGRAMLIMITCOURSESINORGFORDURATION.equals(termResolverDefinition.getName())){
            ProgramCoursesOrgDurationTermResolver resolver = new ProgramCoursesOrgDurationTermResolver();
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_SCOREONTEST.equals(termResolverDefinition.getName())){
            ScoreTermResolver resolver = new ScoreTermResolver();
			resolver.setAcademicRecordService(acadRecordService);
			return resolver;
		} else if (KSKRMSServiceConstants.TERM_RESOLVER_MATCHINGTIMESLOT.equals(termResolverDefinition.getName())){
            MatchingTimeSlotTermResolver resolver = new MatchingTimeSlotTermResolver();
            resolver.setCourseOfferingService(courseOfferingService);
            resolver.setSchedulingService(schedulingService);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_MATCHINGCOURSE.equals(termResolverDefinition.getName())){
            MatchingCourseTermResolver resolver = new MatchingCourseTermResolver();
            resolver.setCourseOfferingService(courseOfferingService);
            resolver.setCourseService(courseService);
            return resolver;
        } else if (KSKRMSServiceConstants.TERM_RESOLVER_MATCHINGCOURSESET.equals(termResolverDefinition.getName())){
            MatchingCourseSetTermResolver resolver = new MatchingCourseSetTermResolver();
            resolver.setCourseOfferingService(courseOfferingService);
            resolver.setCourseService(courseService);
            resolver.setCluIdsInCluSetTermResolver(getCluIdsInCluSetTermResolver());
            return resolver;
        }

        return null;
	}

    private CluIdsInCluSetTermResolver getCluIdsInCluSetTermResolver() {
        CluIdsInCluSetTermResolver cluIdsInCluSetResolver = new CluIdsInCluSetTermResolver();
        cluIdsInCluSetResolver.setCluService(cluService);
        return cluIdsInCluSetResolver;
    }

    public AcademicRecordService getAcadRecordService() {
		return acadRecordService;
	}

    public void setAcadRecordService(AcademicRecordService acadRecordService) {
        this.acadRecordService = acadRecordService;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public CluService getCluService() {
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
