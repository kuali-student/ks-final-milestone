package org.kuali.student.embedded.service;

import org.kuali.student.embedded.beans.CourseParams;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCreateInfo;

public interface CourseClientService {
    public String createCourse(CluCreateInfo clu, String userName, String docType);
    public CourseParams loadCourse(String userName, String documentId);
    public boolean approveCourse(String userName, String documentId);
    public boolean acknowledgeCourse(String userName, String documentId);
    public boolean disapproveCourse(String userName, String documentId);
}
