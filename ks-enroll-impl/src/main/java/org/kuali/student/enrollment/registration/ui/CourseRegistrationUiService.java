package org.kuali.student.enrollment.registration.ui;

import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public interface CourseRegistrationUiService {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    @Path("/registerreggroup/termcode/{termCode}/course/{courseCode}/reggroupname/{regGroupName}")
    public RegistrationResponseInfo RegisterForRegistrationGroupByTermCodeAndCourseCodeAndRegGroupName(@PathParam("termCode") String termCode,
                                                                                                       @PathParam("courseCode") String courseCode,
                                                                                                       @PathParam("regGroupName") String regGroupName) throws Exception;

}
