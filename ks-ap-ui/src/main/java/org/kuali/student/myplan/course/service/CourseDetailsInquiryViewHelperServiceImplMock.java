package org.kuali.student.myplan.course.service;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;
import org.kuali.student.myplan.academicplan.dto.PlanItemInfo;
import org.kuali.student.myplan.academicplan.infc.LearningPlan;
import org.kuali.student.myplan.academicplan.infc.PlanItem;
import org.kuali.student.myplan.academicplan.service.AcademicPlanService;
import org.kuali.student.myplan.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.myplan.course.dataobject.ActivityOfferingAdditionalInfo;
import org.kuali.student.myplan.course.dataobject.ActivityOfferingItem;
import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.course.dataobject.CourseOfferingDetails;
import org.kuali.student.myplan.course.dataobject.MeetingDetails;
import org.kuali.student.myplan.plan.dataobject.AcademicRecordDataObject;
import org.kuali.student.myplan.plan.dataobject.PlanItemDataObject;
import org.kuali.student.myplan.utils.CourseLinkBuilder;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

@SuppressWarnings("deprecation")
public class CourseDetailsInquiryViewHelperServiceImplMock extends
        KualiInquirableImpl {

    private static final long serialVersionUID = 4933435913745621395L;

    private static final Logger LOG = Logger
            .getLogger(CourseDetailsInquiryViewHelperServiceImpl.class);

    private final static String[] WEEKDAYS_FIRST_LETTER = {"M", "T", "W", "Th", "F", "Sa", "Su"};

    private transient CourseService courseService;

    private transient CourseOfferingService courseOfferingService;

    private transient AcademicCalendarService academicCalendarService;

    private transient AtpService atpService;

    private transient CluService cluService;


    private transient AcademicPlanService academicPlanService;

    private transient AcademicRecordService academicRecordService;

    //TODO: These should be changed to an ehCache spring bean
    private Map<String, List<OrgInfo>> campusLocationCache;
    //private Map<String, String> atpCache;
    private HashMap<String, Map<String, String>> hashMap;
    private transient CourseInfo courseInfo;

    public HashMap<String, Map<String, String>> getHashMap() {
        if (this.hashMap == null) {
            this.hashMap = new HashMap<String, Map<String, String>>();
        }
        return this.hashMap;
    }

    public void setHashMap(HashMap<String, Map<String, String>> hashMap) {
        this.hashMap = hashMap;
    }

    public Map<String, List<OrgInfo>> getCampusLocationCache() {
        if (this.campusLocationCache == null) {
            this.campusLocationCache = new HashMap<String, List<OrgInfo>>();
        }
        return this.campusLocationCache;
    }

    public void setCampusLocationCache(Map<String, List<OrgInfo>> campusLocationCache) {
        this.campusLocationCache = campusLocationCache;
    }

    protected CluService getCluService() {
        if (this.cluService == null) {
            this.cluService = KsapFrameworkServiceLocator.getCluService();
        }
        return this.cluService;
    }

    private transient CourseLinkBuilder courseLinkBuilder;

    // default is to create real links
    private CourseLinkBuilder.LINK_TEMPLATE courseLinkTemplateStyle = CourseLinkBuilder.LINK_TEMPLATE.COURSE_DETAILS;

    public CourseLinkBuilder getCourseLinkBuilder() {
        if (courseLinkBuilder == null) {
            this.courseLinkBuilder = new CourseLinkBuilder();
        }
        return courseLinkBuilder;
    }

    public void setCourseLinkBuilder(CourseLinkBuilder courseLinkBuilder) {
        this.courseLinkBuilder = courseLinkBuilder;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    @Override
    public CourseDetails retrieveDataObject(
            @SuppressWarnings("rawtypes") Map fieldValues) {
        String studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId();
        return retrieveCourseDetails(
                (String) fieldValues.get(PlanConstants.PARAM_COURSE_ID),
                studentId);
    }

    /**
     * Populates course with catalog information (title, id, code, description)
     * and next offering information. Other properties are left empty and a flag
     * is set to indicate only summary view
     *
     * @param courseId
     * @return
     */
    public CourseDetails retrieveCourseSummary(String courseId, String studentId) {
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setSummaryOnly(true);

        CourseInfo course = getCourseInfo();
        try {
            String verifiedCourseId = getVerifiedCourseId(courseId);
            course = getCourseService().getCourse(verifiedCourseId, KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new RuntimeException(String.format("Course [%s] not found.",
                    courseId), e);
        } catch (Exception e) {
            throw new RuntimeException("Query failed.", e);
        }
        courseDetails.setVersionIndependentId(course.getVersion().getVersionIndId());
        courseDetails.setCourseId(course.getId());
        courseDetails.setCode(course.getCode());
        String str = null;
        if (course.getDescr() != null) {
            str = course.getDescr().getFormatted();
        }
        if (str != null && str.contains("Offered:")) {
            str = str.substring(0, str.indexOf("Offered"));
        }
        List<String> prerequisites = new ArrayList<String>();
        prerequisites.add("PRE101");
        if (str != null && str.contains("Prerequisite:")) {
            String req = (CourseLinkBuilder.makeLinks(str.substring(
                    str.indexOf("Prerequisite:"), str.length()),
                    courseLinkTemplateStyle, KsapFrameworkServiceLocator
                    .getContext().getContextInfo()));
            req = req.substring(req.indexOf("Prerequisite:"), req.length());
            req = req.replace("Prerequisite:", "").trim();
            req = req.substring(0, 1).toUpperCase()
                    .concat(req.substring(1, req.length()));
            prerequisites.add(req);

            str = str.substring(0, str.indexOf("Prerequisite:"));
        }
        if (str != null) {
            getCourseLinkBuilder();
            str = CourseLinkBuilder.makeLinks(str, KsapFrameworkServiceLocator
                    .getContext().getContextInfo());
        }

        courseDetails.setRequisites(prerequisites);
        courseDetails.setCourseDescription(str);
        courseDetails.setCredit("3-5");
        courseDetails.setCourseTitle("Course 1: Academic Roadmap");
        courseDetails.setLastEffectiveTerm("Fall 2010");
        courseDetails.setLastOffered("Fall 2010");
        List<String> st = new ArrayList<String>();
        st.add("Winter 2013");
        st.add("Spring 2013");
        courseDetails.setScheduledTerms(st);

        List<String> to = new ArrayList<String>();
        to.add("Winter 2013");
        to.add("Spring 2013");
        to.add("Winter 2014");
        courseDetails.setTermsOffered(to);
        return courseDetails;
    }

    public CourseDetails retrieveCourseDetails(String courseId, String studentId) {
        CourseDetails courseDetails = retrieveCourseSummary(courseId, studentId);
        courseDetails.setSummaryOnly(false);

        ContextInfo context = KsapFrameworkServiceLocator.getContext()
                .getContextInfo();

        CourseInfo course;
        try {
            /*Get version verified course*/
            course = getCourseService().getCourse(getVerifiedCourseId(courseId), context);
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Course lookup failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Course lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Course lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Course lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Course lookup failure", e);
        }

        // Campus Locations
        List<OrgInfo> orgInfoList = KsapFrameworkServiceLocator.getOrgHelper().getOrgInfo(
                CourseSearchConstants.CAMPUS_LOCATION,
                CourseSearchConstants.ORG_QUERY_SEARCH_BY_TYPE_REQUEST,
                CourseSearchConstants.ORG_TYPE_PARAM,
                context);
        getCampusLocationCache().put(CourseSearchConstants.CAMPUS_LOCATION,
                orgInfoList);

        List<String> campusLocations = new ArrayList<String>();

        for (String campus : getCampusLocationsOfferedIn(courseId, context))
            for (OrgInfo orgInfo : orgInfoList)
                if (campus.equalsIgnoreCase(orgInfo.getId()))
                    campusLocations.add(orgInfo.getLongName());
        courseDetails.setCampusLocations(campusLocations);

        // Get only the abbre_val of gen ed requirements
        List<String> abbrGenEdReqs = new ArrayList<String>();
        List<AttributeInfo> abbrAttributes = course.getAttributes();
        for (AttributeInfo entry : abbrAttributes)
            if ("Y".equals(entry.getValue())
                    && entry.getKey().startsWith(
                    CourseSearchConstants.GEN_EDU_REQUIREMENTS_PREFIX))
                abbrGenEdReqs.add(KsapFrameworkServiceLocator.getEnumerationHelper().getEnumAbbrValForCode(entry
                        .getKey()));
        courseDetails.setAbbrGenEdRequirements(abbrGenEdReqs);

        // Get general education requirements.
        List<String> genEdReqs = new ArrayList<String>();
        List<AttributeInfo> attributes = course.getAttributes();
        for (AttributeInfo entry : attributes)
            if ("Y".equals(entry.getValue())
                    && entry.getKey().startsWith(
                    CourseSearchConstants.GEN_EDU_REQUIREMENTS_PREFIX)) {
                EnumeratedValueInfo e = KsapFrameworkServiceLocator.getEnumerationHelper().getGenEdReqEnumInfo(
                        entry.getKey(), context);
                String genEdText = String.format("%s (%s)", e.getValue(),
                        e.getAbbrevValue());
                genEdReqs.add(genEdText);
            }
        courseDetails.setGenEdRequirements(genEdReqs);

        /*
		 * Use the course offering service to see if the course is being offered
		 * in the selected term. Note: In the UW implementation of the Course
		 * Offering service, course id is actually course code.
        */
        try {
            //  Fetch the available terms from the Academic Calendar Service.
            CourseOfferingService cos = getCourseOfferingService();
            Set<String> scheduledTermIds = new java.util.HashSet<String>();
            for (CourseOffering co : cos.getCourseOfferingsByCourse(courseId,
                    context))
                if (scheduledTermIds.contains(co.getTermId()))
                    continue;
                else
                    scheduledTermIds.add(co.getTermId());

            List<String> scheduledTerms = new java.util.LinkedList<String>();
            for (TermInfo ti : getAcademicCalendarService()
                    .searchForTerms(
                            QueryByCriteria.Builder.fromPredicates(equalIgnoreCase(
                                    "atpState", PlanConstants.PUBLISHED)), context))
                if (scheduledTermIds.contains(ti.getId()))
                    scheduledTerms.add(ti.getName());
            courseDetails.setScheduledTerms(scheduledTerms);


            AcademicPlanService academicPlanService = getAcademicPlanService();

            //   Get the first learning plan. There should only be one ...
            String planTypeKey = AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN;
            List<LearningPlanInfo> plans = academicPlanService.getLearningPlansForStudentByType(studentId, planTypeKey, PlanConstants.CONTEXT_INFO);
            if (plans.size() > 0) {
                LearningPlan plan = plans.get(0);

                //  Fetch the plan items which are associated with the plan.
                List<PlanItemInfo> planItemsInPlan = academicPlanService.getPlanItemsInPlan(plan.getId(), PlanConstants.CONTEXT_INFO);
                List<PlanItemDataObject> plannedList = new ArrayList<PlanItemDataObject>();
                List<PlanItemDataObject> backupList = new ArrayList<PlanItemDataObject>();

                // Iterate through the plan items and set flags to indicate
                // whether the item is a planned/backup or saved course.
                for (PlanItem planItemInPlanTemp : planItemsInPlan) {
                    if (planItemInPlanTemp.getRefObjectId().equals(
                            courseDetails.getCourseId())) {
                        // Assuming type is planned or backup if not wishlist.
                        if (planItemInPlanTemp.getTypeKey().equals(
                                PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST)) {
                            courseDetails.setSavedItemId(planItemInPlanTemp
                                    .getId());
                            String dateStr = planItemInPlanTemp.getMeta()
                                    .getCreateTime().toString();
                            dateStr = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(DateFormatters.DEFAULT_DATE_FORMATTER.parse(dateStr.substring(0, 10)));
                            courseDetails.setSavedItemDateCreated(dateStr);
                        } else if (planItemInPlanTemp.getTypeKey().equals(
                                PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED)) {
                            plannedList.add(PlanItemDataObject
                                    .build(planItemInPlanTemp));

                        } else if (planItemInPlanTemp.getTypeKey().equals(
                                PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP)) {
                            backupList.add(PlanItemDataObject
                                    .build(planItemInPlanTemp));

                        }
                    }
                }
                if (plannedList.size() > 0) {
                    courseDetails.setPlannedList(plannedList);
                }
                if (backupList.size() > 0) {
                    courseDetails.setBackupList(backupList);
                }
            }

            List<YearTerm> ytList = new ArrayList<YearTerm>();
            List<String> termList = courseDetails.getScheduledTerms();
            for (String term : termList) {
                YearTerm yt = KsapFrameworkServiceLocator.getAtpHelper().getYearTerm(term);
                ytList.add(yt);
            }
            Collections.sort(ytList, Collections.reverseOrder());

            for (int x = 0;x<1;x++) {
                CourseOfferingDetails courseOfferingDetails = new CourseOfferingDetails();
                courseOfferingDetails.setTerm("Winter 2013");
                String atp = "201301";
                List<ActivityOfferingItem> list = getActivityOfferingItems(courseId, atp, courseDetails.getCode());
                courseOfferingDetails.setActivityOfferingItemList(list);
                courseDetails.getCourseOfferingDetails().add(courseOfferingDetails);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Exception loading course offering for:" + course.getCode(),
                    e);
        }

        // Curriculum
        StringBuilder curtitle = new StringBuilder();
        curtitle.append(getTitle(course.getSubjectArea()));
        String courseCode = courseDetails.getCode();
        if (courseCode != null)
            curtitle.append(" (")
                    .append(courseCode.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[0]
                            .trim()).append(")");
        courseDetails.setCurriculumTitle(curtitle.toString());

        // If course not scheduled for future terms, Check for the last term
        // when course was offered
        if (courseDetails.getScheduledTerms().size() == 0) {
            List<CourseOfferingInfo> courseOfferingInfo = null;
            try {
                courseOfferingInfo = getCourseOfferingService().getCourseOfferingsByCourse(
                        courseId,
                        KsapFrameworkServiceLocator.getContext()
                                .getContextInfo());
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("CO lookup failure", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("CO lookup failure", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("CO lookup failure", e);
            } catch (OperationFailedException e) {
                throw new IllegalStateException("CO lookup failure", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalStateException("CO lookup failure", e);
            }
            if (courseOfferingInfo != null && courseOfferingInfo.size() > 0) {
                TermInfo lo;
                try {
                    lo =getAcademicCalendarService().getTerm(
                            courseOfferingInfo.get(0).getTermId(),
                            KsapFrameworkServiceLocator.getContext()
                                    .getContextInfo());
                } catch (DoesNotExistException e) {
                    throw new IllegalArgumentException("AC lookup failure", e);
                } catch (InvalidParameterException e) {
                    throw new IllegalArgumentException("AC lookup failure", e);
                } catch (MissingParameterException e) {
                    throw new IllegalArgumentException("AC lookup failure", e);
                } catch (OperationFailedException e) {
                    throw new IllegalStateException("AC lookup failure", e);
                } catch (PermissionDeniedException e) {
                    throw new IllegalStateException("AC lookup failure", e);
                }
                courseDetails.setLastOffered(lo.getName());
            }
        }
        /*********
         * Implementation to get the Academic Record Data from the SWS and set
         * that to CourseDetails acedRecordList
         ***************/
        List<AcademicRecordDataObject> academicRecordDataObjectList = new ArrayList<AcademicRecordDataObject>();
        List<StudentCourseRecordInfo> studentCourseRecordInfos = new ArrayList<StudentCourseRecordInfo>();
        try {
            studentCourseRecordInfos = getAcademicRecordService().getCompletedCourseRecords(studentId, PlanConstants.CONTEXT_INFO);
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("AR lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("AR lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("AR lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("AR lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("AR lookup error", e);
        }

        if (studentCourseRecordInfos.size() > 0) {

            for (StudentCourseRecordInfo studentInfo : studentCourseRecordInfos) {
                AcademicRecordDataObject academicRecordDataObject = new AcademicRecordDataObject();
                academicRecordDataObject.setAtpId(studentInfo.getTermName());
                academicRecordDataObject.setPersonId(studentInfo.getPersonId());
                academicRecordDataObject.setCourseCode(studentInfo
                        .getCourseCode());
                academicRecordDataObject.setCourseTitle(studentInfo
                        .getCourseTitle());
                academicRecordDataObject.setCourseId(studentInfo.getId());
                academicRecordDataObject.setCredit(studentInfo
                        .getCreditsEarned());
                academicRecordDataObject.setGrade(studentInfo
                        .getCalculatedGradeValue());
                academicRecordDataObject.setRepeated(studentInfo
                        .getIsRepeated());
                academicRecordDataObjectList.add(academicRecordDataObject);
                if (courseDetails.getCourseId().equalsIgnoreCase(
                        studentInfo.getId())) {
                    YearTerm yearTerm = KsapFrameworkServiceLocator.getAtpHelper().getYearTerm(studentInfo
                            .getTermName());
                    courseDetails.getAcademicTerms().add(yearTerm.toTermName());
                }
            }
            if (academicRecordDataObjectList.size() > 0) {
                courseDetails.setAcadRecList(academicRecordDataObjectList);

            }
        }

        return courseDetails;
    }

    public CourseDetails getCourseSummaryWithSections(String courseId, String studentId, String termId) {
        CourseDetails courseDetails = retrieveCourseSummary(courseId, studentId);
        CourseOfferingDetails courseOfferingDetails = new CourseOfferingDetails();
        courseOfferingDetails.setActivityOfferingItemList(getActivityOfferingItems(courseId, termId, null));
        courseDetails.getCourseOfferingDetails().add(courseOfferingDetails);
        return courseDetails;
    }

    public List<ActivityOfferingItem> getActivityOfferingItems(String courseId, String termId, String courseCode) {
        List<ActivityOfferingItem> activityOfferingItemList = new ArrayList<ActivityOfferingItem>();
        activityOfferingItemList.add(createActivityA());
        activityOfferingItemList.add(createActivityB());
        activityOfferingItemList.add(createActivityA());
        activityOfferingItemList.add(createActivityA());
        return activityOfferingItemList;

    }
    private ActivityOfferingItem createActivityA(){
        ActivityOfferingItem activity = new ActivityOfferingItem();
        ActivityOfferingAdditionalInfo addInfo = new ActivityOfferingAdditionalInfo();
        List<ActivityOfferingAdditionalInfo> addList = new ArrayList<ActivityOfferingAdditionalInfo>();
        activity.setCode("ActivityA");
        activity.setActivityOfferingType("Lecture");

        activity.setCredits("3-5");

        List<MeetingDetails> meetingDetailsList = new ArrayList<MeetingDetails>();

        MeetingDetails meeting1 = new MeetingDetails();
        meeting1.setCampus("CampusA");
        meeting1.setBuilding("CHM");
        meeting1.setRoom("119");
        meeting1.setDays("M");
        meeting1.setTime("8:00AM - 10:15AM");
        meetingDetailsList.add(meeting1);
        MeetingDetails meeting2 = new MeetingDetails();
        meeting2.setCampus("CampusA");
        meeting2.setBuilding("CHM");
        meeting2.setRoom("119");
        meeting2.setDays("MWF");
        meeting2.setTime("11:00AM - 11:50AM");
        meetingDetailsList.add(meeting2);
        MeetingDetails meeting3 = new MeetingDetails();
        meeting3.setCampus("CampusA");
        meeting3.setBuilding("CHM");
        meeting3.setRoom("119");
        meeting3.setDays("MW");
        meeting3.setTime("12:00PM - 1:15AM");
        meetingDetailsList.add(meeting3);

        activity.setMeetingDetailsList(meetingDetailsList);
        activity.setRegistrationCode("00000");

        activity.setEnrollRestriction(false);
        activity.setEnrollOpen(true);
        activity.setEnrollCount("000");
        activity.setEnrollMaximum("999");
        activity.setEnrollEstimate("100");
        addInfo.setInstructor("Dr. Light");
        addInfo.setFeeAmount("0.00");

        addInfo.setGradingOption("Letter");
        addInfo.setServiceLearning(true);
        addInfo.setResearch(true);
        addInfo.setDistanceLearning(true);
        addInfo.setJointOffering(true);
        addInfo.setWritingSection(true);

        addInfo.setHonorsSection(true);
        addInfo.setNewThisYear(false);

        addInfo.setDetails("View section notes and textbook information");
        addList.add(addInfo);
        activity.setAdditionalInfo(addList);

        activity.setPlanned(false);
        activity.setAtpId("201301");
        activity.setQtryr("Win 2013");
        activity.setPrimary(true);
        return activity;
    }
    private ActivityOfferingItem createActivityB(){
        ActivityOfferingItem activity = new ActivityOfferingItem();
        ActivityOfferingAdditionalInfo addInfo = new ActivityOfferingAdditionalInfo();
        List<ActivityOfferingAdditionalInfo> addList = new ArrayList<ActivityOfferingAdditionalInfo>();
        activity.setCode("ActivityB");

        activity.setActivityOfferingType("QUIZ");

        activity.setCredits("1");
        activity.setGradingOption("Letter");
        List<MeetingDetails> meetingDetailsList = new ArrayList<MeetingDetails>();

        MeetingDetails meeting1 = new MeetingDetails();
        meeting1.setCampus("CampusA");
        meeting1.setBuilding("CHM");
        meeting1.setRoom("119");
        meeting1.setDays("M");
        meeting1.setTime("8:00AM - 10:15AM");
        meetingDetailsList.add(meeting1);
        MeetingDetails meeting2 = new MeetingDetails();
        meeting2.setCampus("CampusA");
        meeting2.setBuilding("CHM");
        meeting2.setRoom("119");
        meeting2.setDays("MWF");
        meeting2.setTime("11:00AM - 11:50AM");
        meetingDetailsList.add(meeting2);
        MeetingDetails meeting3 = new MeetingDetails();
        meeting3.setCampus("CampusA");
        meeting3.setBuilding("CHM");
        meeting3.setRoom("119");
        meeting3.setDays("MW");
        meeting3.setTime("12:00PM - 1:15AM");
        meetingDetailsList.add(meeting3);

        activity.setMeetingDetailsList(meetingDetailsList);
        activity.setRegistrationCode("00001");
        activity.setServiceLearning(true);
        activity.setResearch(true);
        activity.setDistanceLearning(true);
        activity.setJointOffering(true);
        activity.setWritingSection(true);
        activity.setIneligibleForFinancialAid(true);
        activity.setInstructor("Dr. Wily");
        activity.setFeeAmount("100.00");

        activity.setHonorsSection(true);
        activity.setNewThisYear(false);

        activity.setDetails("View section notes and textbook information");
        addInfo.setServiceLearning(true);

        activity.setEnrollRestriction(false);
        activity.setEnrollOpen(true);
        activity.setEnrollCount("000");
        activity.setEnrollMaximum("999");
        activity.setEnrollEstimate("20");


        addInfo.setResearch(true);
        addInfo.setDistanceLearning(true);
        addInfo.setJointOffering(true);
        addInfo.setWritingSection(true);
        addInfo.setIneligibleForFinancialAid(true);
        addInfo.setInstructor("Dr. Wily");
        addInfo.setFeeAmount("100.00");

        addInfo.setHonorsSection(true);
        addInfo.setNewThisYear(false);

        addInfo.setDetails("View section notes and textbook information");
        addList.add(addInfo);
        //activity.setAdditionalInfo(addList);


        activity.setPlanned(false);
        activity.setAtpId("201301");

        activity.setQtryr("WIN 2013");
        activity.setPrimary(false);
        return activity;
    }


    /**
     * To get the title for the respective display name
     *
     * @param display
     * @return
     */
    protected String getTitle(String display) {
        return "Activity A";
    }

    private List<String> getCampusLocationsOfferedIn(String courseId,
                                                     ContextInfo context) {
        List<String> campusLocations = new ArrayList<String>();
        SearchRequestInfo searchRequest = new SearchRequestInfo(
                "myplan.course.getCampusLocations");
        searchRequest.addParam("cluId", courseId);
        // TODO: Fix when version issue for course is addressed
        // searchRequest.addParam("currentTerm", KsapFrameworkServiceLocator.getAtpHelper().getCurrentAtpId());
        searchRequest.addParam("lastScheduledTerm",
                KsapFrameworkServiceLocator.getAtpHelper().getLastScheduledAtpId());
        SearchResult searchResult = null;
        try {
            searchResult = KsapFrameworkServiceLocator.getCluService().search(
                    searchRequest, context);
        } catch (MissingParameterException e) {
            LOG.error("Error in CLU search", e);
        } catch (InvalidParameterException e) {
            LOG.error("Error in CLU search", e);
        } catch (OperationFailedException e) {
            LOG.error("Error in CLU search", e);
        } catch (PermissionDeniedException e) {
            LOG.error("Error in CLU search", e);
        }

        if (searchResult != null) {
            for (SearchResultRow row : searchResult.getRows()) {
                campusLocations.add(KsapFrameworkServiceLocator.getOrgHelper().getCellValue(row,
                        "lu.resultColumn.campusVal"));
            }
        }
        return campusLocations;
    }


    /**
     * Validates if the courseId/versionIndependentId is valid or not.
     *
     * @param courseId
     * @return
     */
    public boolean isCourseIdValid(String courseId) {
        return true;
    }

    public boolean isPlanned(String refObjId, String atpId) {
       return true;
    }


    public AcademicRecordService getAcademicRecordService() {
        if (this.academicRecordService == null) {
            //   TODO: Use constants for namespace.
            this.academicRecordService = KsapFrameworkServiceLocator.getAcademicRecordService();
        }
        return this.academicRecordService;
    }

    public void setAcademicRecordService(AcademicRecordService academicRecordService) {
        this.academicRecordService = academicRecordService;
    }

    protected synchronized CourseService getCourseService() {
        if (this.courseService == null) {
            this.courseService = KsapFrameworkServiceLocator.getCourseService();
        }
        return this.courseService;
    }

    public synchronized void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }


    /**
     * Provides an instance of the AtpService client.
     */
    protected AtpService getAtpService() {
        if (atpService == null) {
            // TODO: Namespace should not be hard-coded.
            atpService = KsapFrameworkServiceLocator.getAtpService();
        }
        return this.atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (this.courseOfferingService == null) {
            this.courseOfferingService = KsapFrameworkServiceLocator.getCourseOfferingService();
        }
        return this.courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if (this.academicCalendarService == null) {
            this.academicCalendarService = KsapFrameworkServiceLocator.getAcademicCalendarService();
        }
        return this.academicCalendarService;
    }

    public void setAcademicCalendarService(AcademicCalendarService academicCalendarService) {
        this.academicCalendarService = academicCalendarService;
    }

    public AcademicPlanService getAcademicPlanService() {
        if (academicPlanService == null) {
            academicPlanService = KsapFrameworkServiceLocator.getAcademicPlanService();
        }
        return academicPlanService;
    }

    public void setAcademicPlanService(AcademicPlanService academicPlanService) {
        this.academicPlanService = academicPlanService;
    }


    /**
     * Takes a courseId that can be either a version independent Id or a version dependent Id and
     * returns a version dependent Id. In case of being passed in a version depend
     *
     * @param courseId
     * @return
     */
    private String getVerifiedCourseId(String courseId) {
        return courseId;
    }


    /**
     * Initializes ATP term cache.
     * AtpSeasonalTypes rarely change, so fetch them all and store them in a Map.
     */
    ///No Core 2.0 corresponding functionality
//    private synchronized Map<String, String> initializeAtpTypesCache() {
//        if (null == atpCache) {
//            try {
//                List<AtpTypeInfo> atpTypeInfos = getAtpService().getAtpTypes();
//                atpCache = new HashMap<String, String>();
//                for (AtpTypeInfo ti : atpTypeInfos) {
//                    atpCache.put(ti.getId(), ti.getName().substring(0, 1).toUpperCase() + ti.getName().substring(1));
//                }
//            } catch (OperationFailedException e) {
//                logger.error("ATP types lookup failed.", e);
//            }
//        }
//        return atpCache;
//    }
}