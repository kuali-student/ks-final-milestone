package org.kuali.student.enrollment.ui.registration;

import org.kuali.student.enrollment.ui.registration.dto.CourseSearchResult;

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

}
