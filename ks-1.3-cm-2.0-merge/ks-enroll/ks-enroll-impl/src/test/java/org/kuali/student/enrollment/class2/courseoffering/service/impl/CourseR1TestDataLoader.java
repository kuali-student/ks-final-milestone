/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;

/**
 *
 * @author nwright
 */
public class CourseR1TestDataLoader {

    private CourseService courseService;
    private static final String principalId = CourseR1TestDataLoader.class.getSimpleName();

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseR1TestDataLoader(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseR1TestDataLoader() {
    }

    public void loadData() {
        loadCourse("COURSE1", "2012FA", "CHEM", "CHEM123", "Chemistry 123", "description 1", "COURSE1-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY);
        loadCourse("COURSE2", "2012SP", "ENG", "ENG101", "Intro English", "description 2", "COURSE2-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, null);
    }

    public void loadCourse(String id,
            String startTermId,
            String subjectArea,
            String code,
            String title,
            String description,
            String formatId,
            String activityTypeKey1,
            String activityTypeKey2) {
        List<String> activityTypeKeys = new ArrayList();
        if (activityTypeKey1 != null) {
            activityTypeKeys.add(activityTypeKey1);
        }
        if (activityTypeKey1 != null) {
            activityTypeKeys.add(activityTypeKey2);
        }
        this.loadCourseInternal(id, startTermId, subjectArea, code, title, description, formatId, activityTypeKeys);
    }

    private void loadCourseInternal(String id,
            String startTermId,
            String subjectArea,
            String code,
            String title,
            String description,
            String formatId,
            List<String> activityTypeKeys) {
        CourseInfo info = new CourseInfo();
        info.setStartTerm(startTermId);
        info.setEffectiveDate(calcEffectiveDateForTerm(startTermId, id));
        info.setId(id);
        info.setSubjectArea(subjectArea);
        info.setCode(code);
        info.setCourseNumberSuffix(code.substring(subjectArea.length()));
        info.setCourseTitle(title);
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain(description);
        info.setDescr(rt);
        info.setType(LuServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        info.setState("Active");
        info.setFormats(new ArrayList<FormatInfo>());
        FormatInfo format = new FormatInfo();
        info.getFormats().add(format);
        format.setId(formatId);
        format.setType(LuServiceConstants.COURSE_FORMAT_TYPE_KEY);
        format.setState("Active");
        format.setActivities(new ArrayList<ActivityInfo>());
        for (String activityTypeKey : activityTypeKeys) {
            ActivityInfo activity = new ActivityInfo();
            format.getActivities().add(activity);
            activity.setId(format.getId() + "-" + activityTypeKey);
            activity.setTypeKey(activityTypeKey);
            activity.setState("Active");
        }
        try {
            CourseInfo newInfo = this.courseService.createCourse(info, ContextUtils.getContextInfo());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Date calcEffectiveDateForTerm(String termId, String context) {
        String year = termId.substring(0, 4);
        String mmdd = "09-01";
        if (termId.endsWith("FA")) {
            mmdd = "09-01";
        } else if (termId.endsWith("WI")) {
            mmdd = "01-01";
        } else if (termId.endsWith("SP")) {
            mmdd = "03-01";
        } else if (termId.endsWith("SU")) {
            mmdd = "06-01";
        }
        return str2Date(year + "-" + mmdd + " 00:00:00.0", context);
    }

    private Date str2Date(String str, String context) {
        if (str == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
        try {
            Date date = df.parse(str);
            return date;
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Bad date " + str + " in " + context);
        }
    }
}
