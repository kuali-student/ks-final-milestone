package org.kuali.student.r2.lum.course.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;

/**
 * Detailed information about a single course.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface Format extends IdNamelessEntity {
    public List<ActivityInfo> getActivities();

    public List<String> getTermsOffered();

    public TimeAmount getDuration();
}
