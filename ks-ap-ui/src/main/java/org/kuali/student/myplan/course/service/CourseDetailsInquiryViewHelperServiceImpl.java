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
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;
import org.kuali.student.myplan.academicplan.dto.PlanItemInfo;
import org.kuali.student.myplan.academicplan.infc.LearningPlan;
import org.kuali.student.myplan.academicplan.infc.PlanItem;
import org.kuali.student.myplan.academicplan.service.AcademicPlanService;
import org.kuali.student.myplan.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.myplan.course.dataobject.ActivityOfferingItem;
import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.course.dataobject.CourseOfferingDetails;
import org.kuali.student.myplan.course.dataobject.MeetingDetails;
import org.kuali.student.myplan.course.util.CreditsFormatter;
import org.kuali.student.myplan.plan.controller.PlanController;
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
import org.kuali.student.r2.common.infc.TimeOfDay;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.room.infc.Building;
import org.kuali.student.r2.core.room.infc.Room;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

@SuppressWarnings("deprecation")
public class CourseDetailsInquiryViewHelperServiceImpl extends
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
        courseDetails.setCredit(CreditsFormatter.formatCredits(course));
        courseDetails.setCourseTitle(course.getCourseTitle());

        String et = course.getEndTerm();
        YearTerm tn;
        if (et != null
                && !((tn = KsapFrameworkServiceLocator.getAtpHelper().getYearTerm(et)).getYear()==9999))
            courseDetails.setLastEffectiveTerm(tn.toTermName());

        List<String> cto = course.getTermsOffered();
        if (cto != null) {
            List<String> to = new java.util.ArrayList<String>();
            try {
                for (TypeInfo ti : KsapFrameworkServiceLocator.getTypeService()
                        .getTypesByKeys(
                                cto,
                                KsapFrameworkServiceLocator.getContext()
                                        .getContextInfo()))
                    to.add(ti.getName());
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("Type lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("Type lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("Type lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalStateException("Type lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalStateException("Type lookup error", e);
            }
            courseDetails.setTermsOffered(to);
        }

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
                KsapFrameworkServiceLocator.getContext().getContextInfo());
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

            for (YearTerm yt : ytList) {
                CourseOfferingDetails courseOfferingDetails = new CourseOfferingDetails();
                courseOfferingDetails.setTerm(yt.toTermName());
                String atp = yt.toATP();
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
        // Get version verified course
        courseId = getVerifiedCourseId(courseId);

        if (courseCode == null) {
            try {
                CourseInfo course = getCourseService().getCourse(courseId, KsapFrameworkServiceLocator.getContext().getContextInfo());
                courseCode = course.getCode();
            } catch (DoesNotExistException e) {
                throw new RuntimeException(String.format("Course [%s] not found.", courseId), e);
            } catch (Exception e) {
                throw new RuntimeException("Query failed.", e);
            }
        }

        List<ActivityOfferingItem> activityOfferingItemList = new ArrayList<ActivityOfferingItem>();
        try {
            CourseOfferingService cos = getCourseOfferingService();

            List<CourseOfferingInfo> courseOfferingInfoList = cos.getCourseOfferingsByCourseAndTerm(courseId, termId, CourseSearchConstants.CONTEXT_INFO);

            List<CourseOfferingDetails> courseOfferingDetailsList = new ArrayList<CourseOfferingDetails>();

            CourseOfferingDetails courseOfferingDetails = new CourseOfferingDetails();
            courseOfferingDetailsList.add(courseOfferingDetails);

            activityOfferingItemList = courseOfferingDetails.getActivityOfferingItemList();

            for (CourseOfferingInfo courseInfo : courseOfferingInfoList) {
                // Activity offerings come back as a list, the first item is primary, the remaining are secondary

                String courseOfferingID = courseInfo.getId();
                List<ActivityOfferingDisplayInfo> aodiList = cos.getActivityOfferingDisplaysForCourseOffering(courseOfferingID, CourseSearchConstants.CONTEXT_INFO);
                boolean primary = true;

                String courseCredits="";
                if(courseInfo.getCreditOptionId()!=null && courseInfo.getCreditOptionId().startsWith("kuali.creditType.credit.degree.")){
                    courseCredits = courseInfo.getCreditOptionId().replace("kuali.creditType.credit.degree.","");
                }

                for (ActivityOfferingDisplayInfo aodi : aodiList) {
                    ActivityOfferingItem activity = new ActivityOfferingItem();
                    activity.setCode(aodi.getActivityOfferingCode());

                    String typeName = aodi.getTypeName();
                    activity.setActivityOfferingType(typeName);

                    activity.setCredits(courseCredits);
                    activity.setGradingOption(courseInfo.getGradingOptionName());
                    List<MeetingDetails> meetingDetailsList = activity.getMeetingDetailsList();
                    {
                        ScheduleDisplayInfo sdi = aodi.getScheduleDisplay();
                        for (ScheduleComponentDisplay scdi : sdi.getScheduleComponentDisplays()) {
                            MeetingDetails meeting = new MeetingDetails();

                            Building building = scdi.getBuilding();
                            if (building != null) {
                                meeting.setCampus(building.getCampusKey());
                                meeting.setBuilding(building.getBuildingCode());
                            }

                            Room roomInfo = scdi.getRoom();
                            if (roomInfo != null) {
                                meeting.setRoom(roomInfo.getRoomCode());
                            }

                            for (TimeSlot timeSlot : scdi.getTimeSlots()) {

                                String days = "";
                                for (int weekday : timeSlot.getWeekdays()) {
                                    if (weekday > -1 && weekday < 7) {
                                        String letter = WEEKDAYS_FIRST_LETTER[weekday];
                                        days += letter;
                                    }
                                }
                                if (!"".equals(days)) {
                                    meeting.setDays(days);
                                }

                                TimeOfDay startInfo = timeSlot.getStartTime();
                                TimeOfDay endInfo = timeSlot.getEndTime();
                                if (startInfo != null && endInfo != null) {
                                	// TODO: commit TimeStringMillisConverter
//                                    long startTimeMillis = startInfo.getMilliSeconds();
//                                    String startTime = TimeStringMillisConverter.millisToStandardTime(startTimeMillis);
//
//                                    long endTimeMillis = endInfo.getMilliSeconds();
//                                    String endTime = TimeStringMillisConverter.millisToStandardTime(endTimeMillis);
//
//                                    String time = startTime + " - " + endTime;
//
//                                    meeting.setTime(time);
                                }
                                meetingDetailsList.add(meeting);
                            }
                        }
                    }
                    if(aodi.getAttributes()!=null){
                        for (AttributeInfo attrib : aodi.getAttributes()) {
                            String key = attrib.getKey();
                            String value = attrib.getValue();
                            if ("SLN".equalsIgnoreCase(key)) {
                                activity.setRegistrationCode(value);
                                continue;
                            }
                            Boolean flag = Boolean.valueOf(value);
                            if ("ServiceLearning".equalsIgnoreCase(key)) {
                                activity.setServiceLearning(flag);
                            } else if ("ResearchCredit".equalsIgnoreCase(key)) {
                                activity.setResearch(flag);
                            } else if ("DistanceLearning".equalsIgnoreCase(key)) {
                                activity.setDistanceLearning(flag);
                            } else if ("JointSections".equalsIgnoreCase(key)) {
                                activity.setJointOffering(flag);
                            } else if ("Writing".equalsIgnoreCase(key)) {
                                activity.setWritingSection(flag);
                            } else if ("FinancialAidEligible".equalsIgnoreCase(key)) {
                                activity.setIneligibleForFinancialAid(flag);
                            }

                        }
                    }
                    activity.setEnrollRestriction(true);
                    activity.setEnrollOpen(true);
                    activity.setEnrollCount("000");
                    activity.setEnrollMaximum("999");
                    activity.setInstructor(aodi.getInstructorName());

                    activity.setHonorsSection(aodi.getIsHonorsOffering());
                    activity.setNewThisYear(false);

                    activity.setDetails("View section notes and textbook information");

                    // Added this flag to know if the activityoffering is planned/backup
                    boolean planned = isPlanned(courseCode + " " + aodi.getActivityOfferingCode(), termId);
                    activity.setPlanned(planned);
                    activity.setAtpId(termId);
                    YearTerm yt = KsapFrameworkServiceLocator.getAtpHelper().getYearTerm(termId);
                    activity.setQtryr(yt.toQTRYRParam());

                    activity.setPrimary(primary);
                    primary = false;
                    activityOfferingItemList.add(activity);

                }

            }
        } catch (Exception e) {
            LOG.error("Could not load the Section Details");
        }
        return activityOfferingItemList;

    }


    /**
     * To get the title for the respective display name
     *
     * @param display
     * @return
     */
    protected String getTitle(String display) {
        String titleValue = null;
        Map<String, String> subjects = new HashMap<String, String>();
        if (!this.getHashMap().containsKey(CourseSearchConstants.SUBJECT_AREA)) {
            subjects = KsapFrameworkServiceLocator.getOrgHelper().getTrimmedSubjectAreas();
            getHashMap().put(CourseSearchConstants.SUBJECT_AREA, subjects);

        } else {
            subjects = getHashMap().get(CourseSearchConstants.SUBJECT_AREA);
        }

        if (subjects != null && subjects.size() > 0) {
            titleValue = subjects.get(display.trim());
        }

        return titleValue;
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
        boolean isCourseIdValid = false;

        CourseInfo course = null;
        try {
            /*Get version verified course*/
            course = getCourseService().getCourse(getVerifiedCourseId(courseId), KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new RuntimeException(String.format("Course [%s] not found.", courseId), e);
        } catch (Exception e) {
            throw new RuntimeException("Query failed.", e);
        }
        if (course != null) {
            isCourseIdValid = true;
        }
        return isCourseIdValid;
    }

    public boolean isPlanned(String refObjId, String atpId) {
        boolean isPlanned = false;
        PlanItemInfo planItem = null;
        PlanController planController = new PlanController();
        planItem = planController.getPlannedOrBackupPlanItem(refObjId, atpId);
        if (planItem != null) {
            isPlanned = true;
        }
        return isPlanned;
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
        /*String verifiedCourseId = null;
        try {
            SearchRequestInfo req = new SearchRequestInfo("myplan.course.version.id");
            req.addParam("courseId", courseId);
            req.addParam("courseId", courseId);
            req.addParam("lastScheduledTerm", KsapFrameworkServiceLocator.getAtpHelper().getLastScheduledAtpId());
            SearchResult result = getCluService().search(req, KsapFrameworkServiceLocator.getContext().getContextInfo());
            for (SearchResultRow row : result.getRows()) {
                for (SearchResultCell cell : row.getCells()) {
                    if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                        verifiedCourseId = cell.getValue();
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("version verified Id retrieval failed", e);
        }
        return verifiedCourseId;*/
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