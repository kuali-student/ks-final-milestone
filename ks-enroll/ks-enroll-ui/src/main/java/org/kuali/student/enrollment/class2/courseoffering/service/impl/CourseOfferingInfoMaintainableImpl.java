package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

/**
 * @deprecated This class is leftover from Core Slice. Delete when no longer needed or un deprecate if needed.
 */
@Deprecated
public class CourseOfferingInfoMaintainableImpl extends MaintainableImpl {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_DOCUMENT_DESC_FOR_CREATING_COURSE_OFFERING =
                                                            "Create a new course offering";
    private static final String DEFAULT_DOCUMENT_DESC_FOR_EDITING_COURSE_OFFERING =
                                                            "Edit an existing course offering";
    private static final String DEFAULT_DOCUMENT_DESC_FOR_COPYING_COURSE_OFFERING =
                                                            "Copy from an existing course offering to create a new one";
    private transient CourseService courseService;
    private transient CourseOfferingService courseOfferingService;

    // TODO - all exception handling in this method needs to 'manually' roll back what has been
    // changed in the database before the exception was caught
    @Override
    public void saveDataObject() {
        CourseOfferingInfo courseOfferingInfo = (CourseOfferingInfo) getDataObject();
//        System.out.println(">>>>> in CourseOfferingInfoMaintainableImpl.saveDataObject method");

        //get termId from the user input through UI
        String termId = courseOfferingInfo.getTermId();
        //get courseId from courseOfferingInfo, which is retrieved based on course Code that the user input through UI
        String courseId = courseOfferingInfo.getCourseId();

        CourseInfo course = null;
        try {
            course = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
        } catch (OperationFailedException ofe) {
            System.out.println("call getCourseService().getCourse(courseId), and get OperationFailedException:  " + ofe.toString());
        } catch (DoesNotExistException dnee) {
            System.out.println("call getCourseService().getCourse(courseId), and get DoesNotExistException:  " + dnee.toString());
        } catch (InvalidParameterException ipe) {
            System.out.println("call getCourseService().getCourse(courseId), and get InvalidParameterException:  " + ipe.toString());
        } catch (PermissionDeniedException pde) {
            System.out.println("call getCourseService().getCourse(courseId), and get PermissionDeniedException:  " + pde.toString());
        } catch (MissingParameterException mpe) {
            System.out.println("call getCourseService().getCourse(courseId), and get MissingParameterException:  " + mpe.toString());
        }
        // TODO - this entire method needs more complete exception handling; then remove this
        if (null == course) return;

        //form the formatIds
        List<String> formatIds = new ArrayList<String>();
        /*
                            List<FormatInfo> formatList = course.getFormats();
                            for (FormatInfo format : formatList){
                                formatIds.add(format.getId());
                            }
            */
        FormatInfo firstFormat = null;
        // only pick the first format based on Larry's suggestion to simplify core slice development
        if (course != null) {
            firstFormat = course.getFormats().get(0);
            formatIds.add(firstFormat.getId());
        }

        CourseOfferingInfo coi = new CourseOfferingInfo ();
        coi.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        coi.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        try {
            //create a CourseOfferingInfo coi
            coi = getCourseOfferingService().createCourseOffering(courseId, 
                    termId, 
                    coi.getTypeKey(), 
                    coi, 
                    Collections.EMPTY_LIST,
                    new ContextInfo());
        } catch (Exception ex) {
          throw new RuntimeException (ex);
        }

        /*
        //If grading options not present in course, set a default one in CO
        if (coi.getGradingOptionId() == null || coi.getGradingOptionId().isEmpty()){
            coi.setGradingOptionId(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        }
        */
        //create a list of instructors
        List<OfferingInstructorInfo> instructors = courseOfferingInfo.getInstructors();

        //set the list of instructors to the CourseOfferingInfo coi
        if (coi != null) {
            coi.setInstructors(instructors);
            coi.setStateKey(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
            coi.setMaximumEnrollment(courseOfferingInfo.getMaximumEnrollment());

            //update the CourseOfferingInfo coi in DB with instructors info
            try {
                getCourseOfferingService().updateCourseOffering(coi.getId(), coi, new ContextInfo());
            } catch (OperationFailedException ofe) {
                System.out.println("call courseOfferingService.updateCourseOffering() method, and get OperationFailedException:  " + ofe.toString());
            } catch (InvalidParameterException ipe) {
                System.out.println("call courseOfferingService.updateCourseOffering() method, and get InvalidParameterException:  " + ipe.toString());
            } catch (DoesNotExistException dnee) {
                System.out.println("call courseOfferingService.updateCourseOffering() method, and get DoesNotExistException:  " + dnee.toString());
            } catch (PermissionDeniedException pde) {
                System.out.println("call courseOfferingService.updateCourseOffering() method, and get PermissionDeniedException:  " + pde.toString());
            } catch (MissingParameterException mpe) {
                System.out.println("call courseOfferingService.updateCourseOffering() method, and get MissingParameterException:  " + mpe.toString());
            } catch (ReadOnlyException roe) {
                System.out.println("call courseOfferingService.updateCourseOffering() method, and get ReadOnlyException:  " + roe.toString());
            } catch (VersionMismatchException vme) {
                System.out.println("call courseOfferingService.updateCourseOffering() method, and get VersionMismatchException:  " + vme.toString());
            } catch (DataValidationErrorException dvee) {
                System.out.println("call courseOfferingService.updateCourseOffering() method, and get DataValidationErrorException:  " + dvee.toString());
            }
        }

        //create a list of ActivityOfferingInfo based on activities defined in the format of the course
        List<String> courseOfferingIds = new ArrayList<String>();
        courseOfferingIds.add(coi.getId());

        List<ActivityOfferingInfo> activityOfferingInfoList = new ArrayList<ActivityOfferingInfo>();
        List<String> activityOfferingIds = new ArrayList<String>();
        if (firstFormat != null) {
            List<ActivityInfo> activities = firstFormat.getActivities();
            for (ActivityInfo activity : activities) {
                ActivityOfferingInfo activityOfferingInfo = new ActivityOfferingInfo();
                activityOfferingInfo.setInstructors(instructors);
                //It looks like termId and activityId are required fields to create an ActivityOfferingInfo data entry
                activityOfferingInfo.setTermId(termId);
                activityOfferingInfo.setActivityId(activity.getId());
                try {
                    List<TypeInfo> activityOfferingTypes = getCourseOfferingService().getActivityOfferingTypesForActivityType(activity.getTypeKey(), new ContextInfo());
                    if (activityOfferingTypes.size() > 1) {
                        System.out.println(">>for core slice, it should be 1-to-1 mapping. so only take the first one -- " + activityOfferingTypes.get(0).getKey());
                    }

                    //for Core Slice -- if the mapping between Canonical Activity to Activity Offering is not 1-to-1,
                    //(see https://wiki.kuali.org/display/STUDENT/Learning+Unit+Instance+Types+and+States#LearningUnitInstanceTypesandStates-Types)
                    //only take the first one.
                    activityOfferingInfo.setTypeKey(activityOfferingTypes.get(0).getKey());
                    activityOfferingInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
                    //TODO remove this fake generation when we are getting real times from the form
                    // TODO: fix this to set the schedule id from the schedule service
                    String scheduleId = null;
                    activityOfferingInfo.setScheduleId(scheduleId);
                    // activityOfferingInfo.setMeetingSchedules(generateFakeMeetingTimes());
                    List<FormatOfferingInfo> formats = this. getCourseOfferingService().getFormatOfferingsByCourseOffering(coi.getId(), new ContextInfo());
                    activityOfferingInfo.setFormatOfferingId(formats.get(0).getId ());
                    activityOfferingInfo = getCourseOfferingService().createActivityOffering
                            (activityOfferingInfo.getFormatOfferingId(),
                            activityOfferingInfo.getActivityId(),
                            activityOfferingInfo.getTypeKey(),
                            activityOfferingInfo, 
                            new ContextInfo());

                    activityOfferingInfoList.add(activityOfferingInfo);
                    activityOfferingIds.add(activityOfferingInfo.getId());

                    //create a RegiistrationGroup after successfully create all activityOfferingInfos
                    RegistrationGroupInfo registrationGroupInfo = new RegistrationGroupInfo();
                    registrationGroupInfo.setCourseOfferingId(coi.getId());
                    registrationGroupInfo.setMaximumEnrollment(courseOfferingInfo.getMaximumEnrollment());
                    registrationGroupInfo.setActivityOfferingIds(activityOfferingIds);
                    registrationGroupInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
                    registrationGroupInfo.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
                    // TODO Change this formatOffering to actual one when implementing
                    String formatOfferingId = null;
                    try {
                        getCourseOfferingService().createRegistrationGroup(formatOfferingId,registrationGroupInfo.getTypeKey(), registrationGroupInfo, new ContextInfo());
                    } catch (OperationFailedException ofe) {
                        System.out.println("call courseOfferingService.createRegistrationGroup() method, and get OperationFailedException:  " + ofe.toString());
                    } catch (InvalidParameterException ipe) {
                        System.out.println("call courseOfferingService.createRegistrationGroup() method, and get InvalidParameterException:  " + ipe.toString());
                    } catch (DoesNotExistException dnee) {
                        System.out.println("call courseOfferingService.createRegistrationGroup() method, and get DoesNotExistException:  " + dnee.toString());
                    } catch (PermissionDeniedException pde) {
                        System.out.println("call courseOfferingService.createRegistrationGroup() method, and get PermissionDeniedException:  " + pde.toString());
                    } catch (MissingParameterException mpe) {
                        System.out.println("call courseOfferingService.createRegistrationGroup() method, and get MissingParameterException:  " + mpe.toString());
                    } catch (DataValidationErrorException dvee) {
                        System.out.println("call courseOfferingService.createRegistrationGroup() method, and get DataValidationErrorException:  " + dvee.toString());
                    } catch (ReadOnlyException roe) {
                        System.out.println("call courseOfferingService.createRegistrationGroup() method, and get ReadOnlyException:  " + roe.toString());
                    }
                } catch (OperationFailedException ofe) {
                    System.out.println("call courseOfferingService.getActivityOfferingTypesForActivityType() or createActivityOffering() method, and get OperationFailedException:  " + ofe.toString());
                } catch (InvalidParameterException ipe) {
                    System.out.println("call courseOfferingService.getActivityOfferingTypesForActivityType() or createActivityOffering() method, and get InvalidParameterException:  " + ipe.toString());
                } catch (PermissionDeniedException pde) {
                    System.out.println("call courseOfferingService.createActivityOffering() method, and get PermissionDeniedException:  " + pde.toString());
                } catch (MissingParameterException mpe) {
                    System.out.println("call courseOfferingService.getActivityOfferingTypesForActivityType() or createActivityOffering() method, and get MissingParameterException:  " + mpe.toString());
                } catch (DataValidationErrorException dvee) {
                    System.out.println("call courseOfferingService.createActivityOffering() method, and get DataValidationErrorException:  " + dvee.toString());
                } catch (DoesNotExistException dnee) {
                    System.out.println("call courseOfferingService.getActivityOfferingTypesForActivityType() method, and get DoesNotExistException:  " + dnee.toString());
                } catch (ReadOnlyException roe) {
                    System.out.println("call courseOfferingService.getActivityOfferingTypesForActivityType() method, and get ReadOnlyException:  " + roe.toString());
                }
            }
        }
    }

    /**
     * @see org.kuali.rice.krad.maintenance.MaintainableImpl#prepareForSave()
     */
    @Override
    public void prepareForSave() {
        if (getMaintenanceAction().equalsIgnoreCase(KRADConstants.MAINTENANCE_NEW_ACTION)) {
//          System.out.println(">>>>> in CourseOfferingInfoMaintainableImpl.prepareForSave method");

            //set state and type value for the courseOfferingInfo
            CourseOfferingInfo newCourseOffering = (CourseOfferingInfo) getDataObject();
            newCourseOffering.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
            newCourseOffering.setStateKey(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);

            //for each instructor, set personId to Id field, state, and type
            List<OfferingInstructorInfo> instructors =  newCourseOffering.getInstructors();
            for(OfferingInstructorInfo instructor: instructors){
                instructor.setId(instructor.getPersonId());
                instructor.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
                instructor.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
            }
        }
        super.prepareForSave();
    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterCopy
     */
    @Override
    public void processAfterCopy(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription(DEFAULT_DOCUMENT_DESC_FOR_COPYING_COURSE_OFFERING);
    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterEdit
     */
    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription(DEFAULT_DOCUMENT_DESC_FOR_EDITING_COURSE_OFFERING);

    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterNew
     */
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription(DEFAULT_DOCUMENT_DESC_FOR_CREATING_COURSE_OFFERING);

    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, "CourseService"));
        }
        return courseService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, "CourseOfferingService"));
        }
        return courseOfferingService;
    }

    //TODO remove this fake generation below when we are getting real times from the form
    private static final String[] days = {"MO", "TU", "WE", "TH", "FR"};
    private static final String[] hours = {"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17"};
    private static final String[] mins = {"00", "15", "30", "45"};

    private List<MeetingScheduleInfo> generateFakeMeetingTimes() {
        List<MeetingScheduleInfo> infos = new ArrayList<MeetingScheduleInfo>();
        Random generator = new Random();

        int randomNum = generator.nextInt(2);
        String daysString = "";
        String daysString2 = "";
        if (randomNum == 0) {
            int day1Index = generator.nextInt(4);
            daysString = days[day1Index];
            daysString2 = days[day1Index + 1];
        } else {
            int day1Index = generator.nextInt(2);
            int day2Index = generator.nextInt(3) + 2;
            daysString = days[day1Index] + "," + days[day2Index];
        }

        String time = daysString + ";" + generateHours();
        MeetingScheduleInfo m1 = new MeetingScheduleInfo();
        m1.setScheduleId(time);
        infos.add(m1);
        if (StringUtils.isNotBlank(daysString2)) {
            String time2 = daysString2 + ";" + generateHours();
            MeetingScheduleInfo m2 = new MeetingScheduleInfo();
            m2.setScheduleId(time2);
            infos.add(m2);
        }
        return infos;


    }

    private String generateHours() {
        Random generator = new Random();
        int randomNum = generator.nextInt(2);

        int hourIndex1 = generator.nextInt(10);
        String hour1 = hours[hourIndex1];
        String hour2 = hours[hourIndex1 + 1];

        int minIndex1 = generator.nextInt(3);
        String min1 = mins[minIndex1];
        String min2 = mins[minIndex1 + randomNum];

        return hour1 + min1 + "," + hour2 + min2;
    }
}
