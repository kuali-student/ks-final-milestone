
package ca.ubc.student.cdm.service.course.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ca.ubc.student.cdm.service.course.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InsertNewCourseResponse_QNAME = new QName("http://course.server.services.cdm.student.ubc/", "insertNewCourseResponse");
    private final static QName _CourseException_QNAME = new QName("http://course.server.services.cdm.student.ubc/", "CourseException");
    private final static QName _InsertNewCourse_QNAME = new QName("http://course.server.services.cdm.student.ubc/", "insertNewCourse");
    private final static QName _JustChecking_QNAME = new QName("http://course.server.services.cdm.student.ubc/", "justChecking");
    private final static QName _JustCheckingResponse_QNAME = new QName("http://course.server.services.cdm.student.ubc/", "justCheckingResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ca.ubc.student.cdm.service.course.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CourseResponse }
     * 
     */
    public CourseResponse createCourseResponse() {
        return new CourseResponse();
    }

    /**
     * Create an instance of {@link InsertNewCourseResponse }
     * 
     */
    public InsertNewCourseResponse createInsertNewCourseResponse() {
        return new InsertNewCourseResponse();
    }

    /**
     * Create an instance of {@link InsertNewCourse }
     * 
     */
    public InsertNewCourse createInsertNewCourse() {
        return new InsertNewCourse();
    }

    /**
     * Create an instance of {@link JustCheckingResponse }
     * 
     */
    public JustCheckingResponse createJustCheckingResponse() {
        return new JustCheckingResponse();
    }

    /**
     * Create an instance of {@link CourseException }
     * 
     */
    public CourseException createCourseException() {
        return new CourseException();
    }

    /**
     * Create an instance of {@link CourseRequest }
     * 
     */
    public CourseRequest createCourseRequest() {
        return new CourseRequest();
    }

    /**
     * Create an instance of {@link JustChecking }
     * 
     */
    public JustChecking createJustChecking() {
        return new JustChecking();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertNewCourseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://course.server.services.cdm.student.ubc/", name = "insertNewCourseResponse")
    public JAXBElement<InsertNewCourseResponse> createInsertNewCourseResponse(InsertNewCourseResponse value) {
        return new JAXBElement<InsertNewCourseResponse>(_InsertNewCourseResponse_QNAME, InsertNewCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CourseException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://course.server.services.cdm.student.ubc/", name = "CourseException")
    public JAXBElement<CourseException> createCourseException(CourseException value) {
        return new JAXBElement<CourseException>(_CourseException_QNAME, CourseException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertNewCourse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://course.server.services.cdm.student.ubc/", name = "insertNewCourse")
    public JAXBElement<InsertNewCourse> createInsertNewCourse(InsertNewCourse value) {
        return new JAXBElement<InsertNewCourse>(_InsertNewCourse_QNAME, InsertNewCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JustChecking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://course.server.services.cdm.student.ubc/", name = "justChecking")
    public JAXBElement<JustChecking> createJustChecking(JustChecking value) {
        return new JAXBElement<JustChecking>(_JustChecking_QNAME, JustChecking.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JustCheckingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://course.server.services.cdm.student.ubc/", name = "justCheckingResponse")
    public JAXBElement<JustCheckingResponse> createJustCheckingResponse(JustCheckingResponse value) {
        return new JAXBElement<JustCheckingResponse>(_JustCheckingResponse_QNAME, JustCheckingResponse.class, null, value);
    }

}
