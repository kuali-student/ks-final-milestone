package org.kuali.student.cm.course.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.cm.course.form.wrapper.VersionWrapper;

import java.util.List;

/**
 * Helper class for course version history.
 */
public interface CourseVersionsViewHelper extends ViewHelperService {
    /**
     * Gets all versions of a course.
     * @param viCluId The version independent id of the series of courses.
     */
    List<VersionWrapper> getVersions(String viCluId);

    /**
     * Gets the title of the current version of the course.
     */
    String getCourseTitle(String viCluId);
}
