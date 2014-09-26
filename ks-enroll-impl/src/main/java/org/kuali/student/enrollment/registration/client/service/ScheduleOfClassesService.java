package org.kuali.student.enrollment.registration.client.service;


import org.kuali.student.enrollment.registration.client.service.dto.CourseOfferingDetailsSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ResultValueGroupCourseOptions;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Note that common parameters used by this object's methods have the following meaning:
 * - termId: the atp.id field; ie: kuali.atp.2012Spring
 * - termCode: the atp.code field; ie: 201201 = Spring 2012
 * - courseCode: the lui.code field; ie: CHEM237
 * - courseOfferingId: the lui.id field; ie: 443be82c-41e0-4e4f-bc09-c1cf086264df
 * - activityOfferingId: ie: 44cb726b-01eb-4d86-9517-b45bef99c120
 * - regGroupName: ie: 1001
 * <p/>
 * In general, methods should only return a single specific entity when it is uniquely-identified by a unique identifier;
 * all other methods should return a list, even if the list contains only a single entity.
 * <p/>
 * In the specific-entity case, the URI should indicate it will return a single entity (ie: /courseoffering) and should
 * require the entity's unique identifier via @PathParam (ie: /courseoffering/{uid}).
 * <p/>
 * In the bulk-entity case, the URI should be pluralized (ie: /courseofferings) and arguments should be optional via
 * the @QueryParam (ie: /courseofferings?termCode=201208&courseCode=ENGL101).  This is true even in the case where a
 * specific combination of arguments will necessarily always return only a single entity in the results
 * (ie: /reggroups?termCode=201208&courseCode=ENGL250&regGroupName=1001)
 * <p/>
 * <p/>
 * It should be noted that this service is too broad in TYPE:
 * It deals with all types we offer (ie: CourseOffering, Activities, etc); it should be refactored into a
 * small set of sub-services ("Resources") where each Resource deals with a specific type
 * (ie: CourseOfferingResource, ActivitiesResource, etc) and collectively combined under a single
 * "Composite Service".
 */

public interface ScheduleOfClassesService {


/** COURSE OFFERINGS **/

    /**
     * Java Helper method.
     *
     * @param termId     required
     * @param courseCode required
     * @return Returns a list of course offerings
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    List<CourseSearchResult> searchForCourseOfferingsByTermIdAndCourse(String termId, String courseCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException;

    /**
     * Java Helper method.
     *
     * @param termCode   required
     * @param courseCode required
     * @return Returns a list of course offerings
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    List<CourseSearchResult> searchForCourseOfferingsByTermCodeAndCourse(String termCode, String courseCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException;

    /**
     * return course offering information if you know the termId and cluId
     * @param termId
     * @param cluId
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    List<CourseSearchResult> searchForCourseOfferingsByTermIdAndCluId(String termId, String cluId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException;

    /**
     * returns a list of course offerings. Both inputs are optional so if they are not specified then all course offerings in the system will be returned.
     * @param luiIds
     * @param atpIds
     * @return
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     */
    List<CourseSearchResult> getCourseOfferings(List<String> luiIds, List<String> atpIds, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /**
     * get the course offering by id
     * @param courseOfferingId
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    CourseSearchResult getCourseOfferingById(String courseOfferingId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException, IOException;


/** REGISTRATION GROUPS **/

    /**
     * Java Helper method.
     *
     * @param termId not required
     * @param termCode     required
     * @param courseCode   required
     * @param regGroupCode required
     * @return Returns a single registration group
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    RegGroupSearchResult searchForRegistrationGroupByTermAndCourseAndRegGroup(String termId, String termCode, String courseCode, String regGroupCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

/** TERMS **/

    /**
     * Java Helper method.
     *
     * @param termCode required
     * @return Returns a term's id
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    String getTermIdByTermCode(String termCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException;

    /**
     * This method will return a map of AO IDs -> List<TimeSlotInfo>.
     * @param aoIds
     * @return  map of AO IDs -> List<TimeSlotInfo>. Null if no records are found
     */
    Map<String, List<TimeSlotInfo>>  getAoTimeSlotMap(List<String> aoIds, ContextInfo contextInfo);

    /**
     * This method takes in the courseOfferingId and will return an object that contains the credit and grading optins of the course.
     *
     * Because credit and grading options almost never change it would be a good idea to cache the returns of this method to increase performance
     * @param courseOfferingId
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public ResultValueGroupCourseOptions getCreditAndGradingOptions(String courseOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Search to get a RegGroupSearchResult.
     *
     * @param courseOfferingId    Optional. If the courseOfferingId is null, then it will return all registration groups
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    public List<RegGroupSearchResult> searchForRegGroups(String courseOfferingId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException;

    /**
     *
     * @param regGroupId registration group id
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    public RegGroupSearchResult getRegGroup(String regGroupId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException;

    /**
     *
     * @param regGroupIds registration group ids
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    public List<RegGroupSearchResult> getRegGroups(List<String> regGroupIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException;

    /**
     * This will search for registration groups by the course offering id and the reg group name. A course offering can have multiple RGs so the name must be unique
     * @param courseOfferingId
     * @param regGroupName       Ex. 1001
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegGroupSearchResult> searchForRegGroupsByCourseAndName(String courseOfferingId, String regGroupName, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * returns course offering details for a particular course offering id
     * @param courseOfferingId
     * @param courseCode
     * @return
     */
    public CourseOfferingDetailsSearchResult searchForCourseOfferingDetails(String courseOfferingId, String courseCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException ;

    /**
     * returns a list of instructors by aoIds
     * @param aoIds
     * @param contextInfo
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<InstructorSearchResult> getInstructorListByAoIds(List<String> aoIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException;

}
