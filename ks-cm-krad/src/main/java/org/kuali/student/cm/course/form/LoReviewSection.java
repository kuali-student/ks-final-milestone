package org.kuali.student.cm.course.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;

import java.util.List;

/**
 * @author Kuali Student Team
 */
public class LoReviewSection {

    protected String description;
    protected List<String> lstCategories;

    public LoReviewSection(String description, List<String> lstCategories) {
        this.description = description;
        this.lstCategories = lstCategories;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getLstCategories() {
        return lstCategories;
    }

    public String toString() {
        return description + "(" + StringUtils.join(lstCategories, CurriculumManagementConstants.COLLECTION_ITEMS_COMMA_DELIMITER) + ")";
    }
}
