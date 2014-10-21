package org.kuali.student.ap.coursesearch.util;

import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingItem;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 3/2/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlannedSectionsPropertyEditor extends PropertyEditorSupport {

    private final static int MAX_SECTIONS = 4;

    @Override
    public void setValue(Object value) {
        super.setValue(value);
    }

    /**
     * returns the delete Plan with sections alert text
     * if there are more than 4 sections planned then returns "All sections will be deleted as well."
     * otherwise if there are more than one section planned and less than 4 sections
     * returns "Section A, AC, B and C will be deleted as well." text.
     * @return
     */
    @Override
    public String getAsText() {
        List<ActivityOfferingItem> plannedSections = (List<ActivityOfferingItem>) super.getValue();
        String atpId = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter("atpId");
        StringBuilder sb = new StringBuilder();
        List<String> sections = new ArrayList<String>();
        for (ActivityOfferingItem activity : plannedSections) {
            if (activity.getPlanItemId() != null && activity.getAtpId().equalsIgnoreCase(atpId)) {
                sections.add(activity.getCode());
            }

        }
        int counter = 0;
        if (sections.size() >= MAX_SECTIONS) {
            sb.append("All sections ");
        } else {
            for (String section : sections) {
                if (counter == 0) {
                    sb.append(String.format("Section %s", section));
                } else if (counter == sections.size() - 1) {
                    sb.append(String.format(" and %s ", section));
                } else {
                    sb.append(String.format(", %s", section));
                }
                counter++;
            }
        }
        if (sb.length() > 0) {
            sb.append("will be deleted as well.");
        }
        return sb.toString();
    }
}
