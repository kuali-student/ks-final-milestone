package org.kuali.student.myplan.course.util;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.myplan.plan.util.AtpHelper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 5/3/12
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 *
 *
 * Pretty sure this one isn't being used.
 *
 */
@Deprecated
public class AcademicTermMessageEditor extends CollectionListPropertyEditor {

    private final static Logger logger = Logger.getLogger(AcademicTermMessageEditor.class);

    @Override
    protected String makeHtmlList(Collection c) {
        Iterator<Object> i = c.iterator();
        StringBuffer sb = new StringBuffer();

        while (i.hasNext()) {
            String term = (String) i.next();
            String[] splitStr = term.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            String atpId = AtpHelper.getAtpIdFromTermAndYear(splitStr[0].trim(), splitStr[1].trim());
            List<TermInfo> scheduledTerms = null;
            String currentTerm = AtpHelper.getCurrentAtpId();
            if (atpId.compareToIgnoreCase(currentTerm) >= 0) {
                sb = sb.append("<dd>").append("You're currently enrolled in this course for ")
                        .append("<a href=plan?methodToCall=start&viewId=PlannedCourses-FormView&focusAtpId=")
                        .append(atpId).append(">")
                        .append(term).append("</a>");
            } else {
                sb = sb.append("<dd>").append("You took this course in ")
                        .append("<a href=plan?methodToCall=start&viewId=PlannedCourses-FormView&focusAtpId=")
                        .append(atpId).append(">")
                        .append(term).append("</a>");
            }
        }
        return sb.toString();
    }
}
