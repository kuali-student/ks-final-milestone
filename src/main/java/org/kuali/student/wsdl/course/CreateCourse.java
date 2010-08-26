
package org.kuali.student.wsdl.course;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createCourse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createCourse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="courseInfo" type="{http://student.kuali.org/wsdl/course}courseInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createCourse", propOrder = {
    "courseInfo"
})
public class CreateCourse {

    protected CourseInfo courseInfo;

    /**
     * Gets the value of the courseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CourseInfo }
     *     
     */
    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    /**
     * Sets the value of the courseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CourseInfo }
     *     
     */
    public void setCourseInfo(CourseInfo value) {
        this.courseInfo = value;
    }

}
