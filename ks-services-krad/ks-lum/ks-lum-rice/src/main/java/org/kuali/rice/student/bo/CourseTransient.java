package org.kuali.rice.student.bo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.bo.TransientBusinessObjectBase;
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.lum.course.dto.CourseExpenditureInfo;
import org.kuali.student.lum.course.dto.CourseFeeInfo;
import org.kuali.student.lum.course.dto.CourseJointInfo;
import org.kuali.student.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.lum.course.dto.CourseVariationInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;

public class CourseTransient extends TransientBusinessObjectBase {

    private static final long serialVersionUID = 6385381349983978326L;
    
    private String courseId; 
    private String courseTitle;
    private String courseCode;
    private String subjectArea;  
    
    public CourseTransient() {
        super();
    }

    @Override
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap m = new LinkedHashMap();
        m.put("courseId", courseId);
        m.put("courseTitle", courseTitle);
        m.put("courseCode", courseCode);
        m.put("subjectArea", subjectArea);
        return m;
    }

    /**
     * Gets the courseId
     * 
     * @return courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Sets the courseId
     * 
     * @param courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the courseTitle
     * 
     * @return courseTitle
     */
    public String getCourseTitle() {
         return courseTitle;
    }

    /**
     * Sets the courseTitle
     * 
     * @param courseTitle
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
    
    /**
     * @return the courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    /**
     * Gets the subjectArea
     * 
     * @return subjectArea
     */
    public String getSubjectArea() {
        return subjectArea;
    }

    /**
     * Sets the subjectArea
     * 
     * @param subjectArea
     */
    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }
}
