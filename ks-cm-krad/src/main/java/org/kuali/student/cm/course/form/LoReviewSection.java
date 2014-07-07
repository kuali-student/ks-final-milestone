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
    protected List<LoReviewSection> loReviewSectionList;

    public LoReviewSection(String description, List<String> lstCategories,List<LoReviewSection> loReviewSectionList) {
        this.description = description;
        this.lstCategories = lstCategories;
        this.loReviewSectionList = loReviewSectionList;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getLstCategories() {
        return lstCategories;
    }

    public List<LoReviewSection> getLoReviewSectionList() {
        return loReviewSectionList;
    }

    public String toString() {
        return description + " (" + StringUtils.join(lstCategories, CurriculumManagementConstants.COLLECTION_ITEMS_COMMA_DELIMITER) + ")";
    }
}
