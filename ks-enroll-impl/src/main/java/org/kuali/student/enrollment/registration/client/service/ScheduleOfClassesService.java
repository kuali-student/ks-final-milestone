package org.kuali.student.enrollment.registration.client.service;


import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityTypeSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public interface ScheduleOfClassesService {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/term/{termId}/course/{courseCode}")
    public List<CourseSearchResult> loadCourseOfferingsByTermAndCourseCode(@PathParam("termId") String termId,
                                                                           @PathParam("courseCode") String courseCode) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/courseofferings/termcode/{termCode}/course/{courseCode}")
    public List<CourseSearchResult> loadCourseOfferingsByTermCodeAndCourseCode(@PathParam("termCode") String termCode,
                                                                           @PathParam("courseCode") String courseCode) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/reggroups/courseOfferingId/{courseOfferingId}/")
    public List<RegGroupSearchResult> loadRegistrationGroupsByCourseOfferingId(@PathParam("courseOfferingId") String courseOfferingId) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/reggroups/termid/{termId}/course/{courseCode}/")
    public List<RegGroupSearchResult> loadRegistrationGroupsByTermIdAndCourseCode(@PathParam("termId") String termId,
                                                                                @PathParam("courseCode") String courseCode) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/reggroups/termcode/{termCode}/course/{courseCode}/")
    public List<RegGroupSearchResult> loadRegistrationGroupsByTermCodeAndCourseCode(@PathParam("termCode") String termCode,
                                                                                @PathParam("courseCode") String courseCode) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/reggroups/termcode/{termCode}/course/{courseCode}/reggroupname/{regGroupName}")
    public RegGroupSearchResult loadRegistrationGroupByTermCodeAndCourseCodeAndRegGroupName(@PathParam("termCode") String termCode,
                                                                                    @PathParam("courseCode") String courseCode,
                                                                                    @PathParam("regGroupName") String regGroupName) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/activityofferings/courseOfferingId/{courseOfferingId}/")
    public List<ActivityOfferingSearchResult> loadActivityOfferingsByCourseOfferingId(@PathParam("courseOfferingId") String courseOfferingId) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/activityofferings/termid/{termId}/course/{courseCode}/")
    public List<ActivityOfferingSearchResult> loadActivityOfferingsByTermIdAndCourseCode(@PathParam("termId") String termId,
                                                                                @PathParam("courseCode") String courseCode) throws Exception;
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/activityofferings/termcode/{termCode}/course/{courseCode}/")
    public List<ActivityOfferingSearchResult> loadActivityOfferingsByTermCodeAndCourseCode(@PathParam("termCode") String termCode,
                                                                                @PathParam("courseCode") String courseCode) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("instructors/termid/{termId}/course/{courseCode}")
    public List<InstructorSearchResult> loadInstructorsByTermIdAndCourseCode(@PathParam("termId") String termId,
                                                                             @PathParam("courseCode") String courseCode) throws Exception;
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("instructors/termcode/{termCode}/course/{courseCode}")
    public List<InstructorSearchResult> loadInstructorsByTermCodeAndCourseCode(@PathParam("termCode") String termCode,
                                                                             @PathParam("courseCode") String courseCode) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/instructors/courseOfferingId/{courseOfferingId}/")
    public List<InstructorSearchResult> loadInstructorsByCourseOfferingId(@PathParam("courseOfferingId") String courseOfferingId) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/instructors/activityOfferingId/{activityOfferingId}/")
    public List<InstructorSearchResult> loadInstructorsByActivityOfferingId(@PathParam("activityOfferingId") String activityOfferingId) throws Exception;


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/atp/termcode/{termCode}/")
    public String getAtpIdByAtpCode(@PathParam("termCode") String termCode) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/activities/courseOfferingId/{courseOfferingId}/")
    public List<ActivityTypeSearchResult> loadActivitiesByCourseOfferingId(@PathParam("courseOfferingId") String courseOfferingId) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/activities/termcode/{termCode}/course/{courseCode}/")
    public List<ActivityTypeSearchResult> loadActivitiesByTermCodeAndCourseCode(@PathParam("termCode") String termCode,
                                                                                           @PathParam("courseCode") String courseCode) throws Exception;

}
