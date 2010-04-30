package ca.ubc.student.cdm.service.course.client;

import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceFeature;

public interface ICourseService {

  /**
   * 
   * @return
   *     returns Course
   */
  @WebEndpoint(name = "CoursePort")
  public Course getCoursePort();

  /**
   * 
   * @param features
   *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
   * @return
   *     returns Course
   */
  @WebEndpoint(name = "CoursePort")
  public Course getCoursePort(WebServiceFeature... features);

}