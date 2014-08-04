package org.kuali.student.enrollment.registration.client.service;


import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.InvalidParameterException;

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
 *
 * This is a split of the RESTful methods out of ScheduleOfClassesService
 */
@Path("/")
public interface ScheduleOfClassesClientService {


/** COURSE OFFERINGS **/

    /**
     * Returns a list of course offering details.  Must provide either termId or termCode.
     *
     * @param termId     optional; if provided, overrides termCode
     * @param termCode   optional; required if termId not provided
     * @param criteria   required
     * @return Returns a list of course offerings
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search")
    Response searchForCourseOfferings(@QueryParam("termId") String termId,
                                      @QueryParam("termCode") String termCode,
                                      @QueryParam("criteria") String criteria) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException, org.kuali.student.r2.common.exceptions.InvalidParameterException;


/** REGISTRATION GROUPS **/

    /**
     * Returns a list of registration group details.
     * Must provide one of the following combinations:
     * - courseOfferingId
     * - termId, courseCode
     * - termCode, courseCode
     * Can optionally also provide a regGroupName to get a single specific entity
     *
     * @param courseOfferingId optional; if provided, overrides termId, termCode, courseCode
     * @param termId           optional; if provided, overrides termCode
     * @param termCode         optional; required if both courseOfferingId & termId are not provided
     * @param courseCode       optional; required if courseOfferingId not provided
     * @param regGroupName     optional; if provided, will return a single entity
     * @return Returns a list of registration groups
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reggroups")
    Response searchForRegistrationGroups(@QueryParam("courseOfferingId") String courseOfferingId,
                                                  @QueryParam("termId") String termId,
                                                  @QueryParam("termCode") String termCode,
                                                  @QueryParam("courseCode") String courseCode,
                                                  @QueryParam("regGroupName") String regGroupName);

/** ACTIVITY OFFERINGS **/

    /**
     * Returns a list of activity offering details.
     * Must provide one of the following combinations:
     * - courseOfferingId
     * - termId, courseCode
     * - termCode, courseCode
     *
     * @param courseOfferingId optional; if provided, overrides termId, termCode, courseCode
     * @param termId           optional; if provided, overrides termCode
     * @param termCode         optional; required if both courseOfferingId & termId are not provided
     * @param courseCode       optional; required if courseOfferingId not provided
     * @return Returns a list of activity offerings
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/activityofferings")
    Response searchForActivityOfferings(@QueryParam("courseOfferingId") String courseOfferingId,
                                                 @QueryParam("termId") String termId,
                                                 @QueryParam("termCode") String termCode,
                                                 @QueryParam("courseCode") String courseCode);

/** ACTIVITY TYPES **/

    /**
     * Returns a list of activity type details.
     * Must provide one of the following combinations:
     * - courseOfferingId
     * - termCode, courseCode
     *
     * @param courseOfferingId optional; if provided, overrides termCode, courseCode
     * @param termId           optional
     * @param termCode         optional; required if courseOfferingId not provided
     * @param courseCode       optional; required if courseOfferingId not provided
     * @return Returns a list of activity types
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/activitytypes")
    Response searchForActivityTypes(@QueryParam("courseOfferingId") String courseOfferingId,
                                             @QueryParam("termId") String termId,
                                             @QueryParam("termCode") String termCode,
                                             @QueryParam("courseCode") String courseCode);

/** INSTRUCTORS **/

    /**
     * Returns a list of instructor details.
     * Must provide one of the following combinations:
     * - courseOfferingId
     * - activityOfferingId
     * - termId, courseCode
     * - termCode, courseCode
     *
     * @param courseOfferingId   optional; if provided, overrides activityOfferingId, termId, termCode, courseCode
     * @param activityOfferingId optional; if provided, overrides termId, termCode, courseCode
     * @param termId             optional; if provided, overrides termCode
     * @param termCode           optional; required if neither courseOfferingId, activityOfferingId, or termId are provided
     * @param courseCode         optional; required if neither courseOfferingId nor activityOfferingId are provided
     * @return Returns a list of instructors
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/instructors")
    Response searchForInstructors(@QueryParam("courseOfferingId") String courseOfferingId,
                                           @QueryParam("activityOfferingId") String activityOfferingId,
                                           @QueryParam("termId") String termId,
                                           @QueryParam("termCode") String termCode,
                                           @QueryParam("courseCode") String courseCode);

/** TERMS **/

    /**
     * Returns whether or not the student is eligible for the term.
     *
     * @param termId      required
     * @return Returns a list of validation results
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/checkStudentEligibilityForTerm")
    Response checkStudentEligibilityForTerm(@QueryParam("termId") String termId);

    /**
     * Returns a list of term details.
     *
     * @param termCode      optional; if provided, overrides isActiveTerms
     * @param isActiveTerms optional
     * @return Returns a list of terms
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/terms")
    Response searchForTerms(@QueryParam("termCode") String termCode,
                                     @QueryParam("active") boolean isActiveTerms);

    /**
     * Returns a list of course offering details such as main info (code, name, desc, etc.),
     * cross-listed courses, prereqs, and AO info (main info, schedule, instructor, reg groups).
     *
     * @param courseOfferingId required
     * @return Returns info for given CO such as desc, name, AOs, etc.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courseOfferingDetails")
    Response searchForCourseOfferingDetails(@QueryParam("courseOfferingId") String courseOfferingId);

}
