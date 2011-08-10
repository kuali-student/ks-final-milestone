package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseScheduleViewInfo", propOrder = {"studentId", "courseScheduleEntries", "_futureElements"})

public class CourseScheduleViewInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String studentId;

    @XmlElement
    private List<CourseScheduleEntryViewInfo> courseScheduleEntries;

    @XmlAnyElement
    private List<Element> _futureElements;

    public List<CourseScheduleEntryViewInfo> getCourseScheduleEntries() {
        return courseScheduleEntries;
    }

    public String getStudentId() {
        return studentId;
    }

}
