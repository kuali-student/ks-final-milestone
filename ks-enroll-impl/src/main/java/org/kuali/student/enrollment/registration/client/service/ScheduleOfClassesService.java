package org.kuali.student.enrollment.registration.client.service;


import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityTypeSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseAndPrimaryAOSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.TermSearchResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Note that common parameters used by this object's methods have the following meaning:
 *      - termId: the atp.id field; ie: kuali.atp.2012Spring
 *      - termCode: the atp.code field; ie: 201201 = Spring 2012
 *      - courseCode: the lui.code field; ie: CHEM237
 *      - courseOfferingId: the lui.id field; ie: 443be82c-41e0-4e4f-bc09-c1cf086264df
 *      - activityOfferingId: ie: 44cb726b-01eb-4d86-9517-b45bef99c120
 *      - regGroupName: ie: 1001
 *
 * In general, methods should only return a single specific entity when it is uniquely-identified by a unique identifier;
 * all other methods should return a list, even if the list contains only a single entity.
 *
 * In the specific-entity case, the URI should indicate it will return a single entity (ie: /courseoffering) and should
 * require the entity's unique identifier via @PathParam (ie: /courseoffering/{uid}).
 *
 * In the bulk-entity case, the URI should be pluralized (ie: /courseofferings) and arguments should be optional via
 * the @QueryParam (ie: /courseofferings?termCode=201208&courseCode=ENGL101).  This is true even in the case where a
 * specific combination of arguments will necessarily always return only a single entity in the results
 * (ie: /reggroups?termCode=201208&courseCode=ENGL250&regGroupName=1001)
 *
 *
 * It should be noted that this service is too broad in two important ways:
 *      TYPE: it deals with all types we offer (ie: CourseOffering, Activities, etc); it should be refactored into a
 *              small set of sub-services ("Resources") where each Resource deals with a specific type
 *              (ie: CourseOfferingResource, ActivitiesResource, etc) and collectively combined under a single
 *              "Composite Service".
 *      USER: it deals both with REST and Java methods; that is, some are intended for public exposure via REST while
 *              others are intended to be accessed via the KSBus by other KS java objects; it should be refactored
 *              to place the bulk of the code in a common object that Rest- and Java-Services each use to serve up
 *              their particular responses for their intended user.
 *
 */
@Path("/")
public interface ScheduleOfClassesService {


/** COURSE OFFERINGS **/

    /**
     * Returns a list of course offering details.  Must provide either termId or termCode.
     *
     * @param termId optional; if provided, overrides termCode
     * @param termCode optional; required if termId not provided
     * @param courseCode required
     * @return Returns a list of course offerings
     * @throws Exception
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/courseofferings")
    public List<CourseSearchResult> loadCourseOfferings( @QueryParam("termId") String termId,
                                                         @QueryParam("termCode") String termCode,
                                                         @QueryParam("courseCode") String courseCode ) throws Exception;
    /**
     * Java Helper method.
     *
     * @param termId required
     * @param courseCode required
     * @return Returns a list of course offerings
     * @throws Exception
     */
    public List<CourseSearchResult> loadCourseOfferingsByTermIdAndCourse( String termId, String courseCode ) throws Exception;

    /**
     * Java Helper method.
     *
     * @param termCode required
     * @param courseCode required
     * @return Returns a list of course offerings
     * @throws Exception
     */
    public List<CourseSearchResult> loadCourseOfferingsByTermCodeAndCourse( String termCode, String courseCode ) throws Exception;

    /**
     * In order to support the UI we have been asked to provide a course offering search that will return course offering information as well as
     * a list of primary activity offerings. There is a default order of activity offering types. Ex: lecture, lab, discussion.
     * In that case the search will return the course offering information and a list of lectures.
     *
     * @param termCode required
     * @param courseCode required
     * @return Returns a list of objects that each contain a course offering and a list of primary activity offerings
     * @throws Exception
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/courseofferings/primaryactivities")
    public List<CourseAndPrimaryAOSearchResult> loadCourseOfferingsAndPrimaryAosByTermAndCourse( @QueryParam("termCode") String termCode,
                                                                                                 @QueryParam("courseCode") String courseCode ) throws Exception;


/** REGISTRATION GROUPS **/

    /**
     * Returns a list of registration group details.
     * Must provide one of the following combinations:
     *      - courseOfferingId
     *      - termId, courseCode
     *      - termCode, courseCode
     * Can optionally also provide a regGroupName to get a single specific entity
     *
     * @param courseOfferingId optional; if provided, overrides termId, termCode, courseCode
     * @param termId optional; if provided, overrides termCode
     * @param termCode optional; required if both courseOfferingId & termId are not provided
     * @param courseCode optional; required if courseOfferingId not provided
     * @param regGroupName optional; if provided, will return a single entity
     * @return Returns a list of registration groups
     * @throws Exception
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/reggroups")
    public List<RegGroupSearchResult> loadRegistrationGroups( @QueryParam("courseOfferingId") String courseOfferingId,
                                                              @QueryParam("termId") String termId,
                                                              @QueryParam("termCode") String termCode,
                                                              @QueryParam("courseCode") String courseCode,
                                                              @QueryParam("regGroupName") String regGroupName ) throws Exception;

    /**
     * Java Helper method.
     *
     * @param termCode required
     * @param courseCode required
     * @param regGroupName required
     * @return Returns a single registration group
     * @throws Exception
     */
    public RegGroupSearchResult loadRegistrationGroupByTermAndCourseAndRegGroup( String termCode, String courseCode, String regGroupName ) throws Exception;


/** ACTIVITY OFFERINGS **/

    /**
     * Returns a list of activity offering details.
     * Must provide one of the following combinations:
     *      - courseOfferingId
     *      - termId, courseCode
     *      - termCode, courseCode
     *
     * @param courseOfferingId optional; if provided, overrides termId, termCode, courseCode
     * @param termId optional; if provided, overrides termCode
     * @param termCode optional; required if both courseOfferingId & termId are not provided
     * @param courseCode optional; required if courseOfferingId not provided
     * @return Returns a list of activity offerings
     * @throws Exception
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/activityofferings")
    public List<ActivityOfferingSearchResult> loadActivityOfferings( @QueryParam("courseOfferingId") String courseOfferingId,
                                                           @QueryParam("termId") String termId,
                                                           @QueryParam("termCode") String termCode,
                                                           @QueryParam("courseCode") String courseCode ) throws Exception;


/** ACTIVITY TYPES **/

    /**
     * Returns a list of activity type details.
     * Must provide one of the following combinations:
     *      - courseOfferingId
     *      - termCode, courseCode
     *
     * @param courseOfferingId optional; if provided, overrides termCode, courseCode
     * @param termCode optional; required if courseOfferingId not provided
     * @param courseCode optional; required if courseOfferingId not provided
     * @return Returns a list of activity types
     * @throws Exception
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/activitytypes")
    public List<ActivityTypeSearchResult> loadActivityTypes( @QueryParam("courseOfferingId") String courseOfferingId,
                                                             @QueryParam("termCode") String termCode,
                                                             @QueryParam("courseCode") String courseCode ) throws Exception;


/** INSTRUCTORS **/

    /**
     * Returns a list of instructor details.
     * Must provide one of the following combinations:
     *      - courseOfferingId
     *      - activityOfferingId
     *      - termId, courseCode
     *      - termCode, courseCode
     *
     * @param courseOfferingId optional; if provided, overrides activityOfferingId, termId, termCode, courseCode
     * @param activityOfferingId optional; if provided, overrides termId, termCode, courseCode
     * @param termId optional; if provided, overrides termCode
     * @param termCode optional; required if neither courseOfferingId, activityOfferingId, or termId are provided
     * @param courseCode optional; required if neither courseOfferingId nor activityOfferingId are provided
     * @return Returns a list of instructors
     * @throws Exception
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/instructors")
    public List<InstructorSearchResult> loadInstructors( @QueryParam("courseOfferingId") String courseOfferingId,
                                                         @QueryParam("activityOfferingId") String activityOfferingId,
                                                         @QueryParam("termId") String termId,
                                                         @QueryParam("termCode") String termCode,
                                                         @QueryParam("courseCode") String courseCode ) throws Exception;


/** TERMS **/

    /**
     * Returns the term's id as a simple string
     *
     * Note: this method needs to be re-thought; it violates the standards established for this Service; consider
     * instead exporting a List<TermSearchResult> for URI /terms and which would return all terms by default
     * (ie: /terms) or an empty-list if termCode were provided (ie: /terms?termCode=201208) but no match was found
     *
     * Also should probably be combined with method below (loadActiveTerms)
     *
     * @param termCode required
     * @return a string represenation of the term id (ie: kuali.atp.2012Fall)
     * @throws Exception
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/terms/termcode/{termCode}")
    public String getTermIdByTermCode( @PathParam("termCode") String termCode ) throws Exception;

    /**
     * Returns a list of the active terms. Active terms are defined as ATPs with a state of Official
     *
     * Note: this method needs to be re-thought; it violates the standards established for this Service; consider
     * instead exporting a List<TermSearchResult> for URI /terms and would return all terms by default
     * (ie: /terms) or a list of all active-terms if activeTerms were provided (ie: /terms?activeTerms)
     *
     * Also should probably be combined with method above (getAtpIdByAtpCode)
     *
     * @return Returns a list of all active terms
     * @throws Exception
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/terms")
    public List<TermSearchResult> loadActiveTerms() throws Exception;

}
