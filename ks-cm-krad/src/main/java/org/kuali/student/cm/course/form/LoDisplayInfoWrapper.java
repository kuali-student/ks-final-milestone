package org.kuali.student.cm.course.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LoDisplayInfoWrapper extends LoDisplayInfo {

    private static final long serialVersionUID = 8232176748014317444L;

    private static final Logger LOG = LoggerFactory.getLogger(LoDisplayInfoWrapper.class);

    private String searchBy;

    private String code;

    private String title;

    private String typeName;

    private String orgName;

    private String orgType;

    private String courseNumber;

    private int indentLevel = 0;

    private int sequence;

    private boolean selected;

    protected boolean indentable;
    protected boolean outdentable;
    protected boolean moveUpable;
    protected boolean moveDownable;

    public LoDisplayInfoWrapper() {
    }

    public LoDisplayInfoWrapper(LoDisplayInfo info) {
        super(info);

        //  Read the sequence from the LO dynamic attributes.
        int sequence = 0;
        String s = null;
        try {
            s = info.getLoInfo().getAttributeValue(CurriculumManagementConstants.LoProperties.SEQUENCE);
            if (s != null) {
                sequence = Integer.valueOf(s);
                this.setSequence(sequence);
            } else {
                LOG.warn("Learning Objective {} does not have a '{}' attribute set.", info.getLoInfo().getId(), CurriculumManagementConstants.LoProperties.SEQUENCE);
            }
        } catch(NumberFormatException nfe) {
            LOG.error("The sequence attribute of Learning Objective {} was not a number: {}", info.getLoInfo().getId(), s);
        }

        setLoCategoryInfoList(info.getLoCategoryInfoList());
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void indent() {
        indentLevel++;
    }

    public void outdent() {
        indentLevel--;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setIndentable(boolean indentable) {
        this.indentable = indentable;
    }

    public void setOutdentable(boolean outdentable) {
        this.outdentable = outdentable;
    }

    public void setMoveUpable(boolean moveUpable) {
        this.moveUpable = moveUpable;
    }

    public void setMoveDownable(boolean moveDownable) {
        this.moveDownable = moveDownable;
    }

    /**
     * The sequence of the LO in relation to its siblings. This is stored as a dynamic attribute on LoInfo.
     */
    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    /**
     * Used by the UI to determine if the indent widget should be active.
     * @return True if the widget should be active. Otherwise, false.
     */
    public boolean isIndentable() {
        return indentable;
    }

    /**
     * Used by the UI to determine if the unindent widget should be active.
     * @return True if the widget should be active. Otherwise, false.
     */
    public boolean isOutdentable() {
        return outdentable;
    }

    /**
     * Used by the UI to determine if the "move up" widget should be active.
     * @return True if the widget should be active. Otherwise, false.
     */
    public boolean isMoveUpable() {
        return moveUpable;
    }

    /**
     * Used by the UI to determine if the "move down" widget should be active.
     * @return True if the widget should be active. Otherwise, false.
     */
    public boolean isMoveDownable() {
        return moveDownable;
    }

    /**
     * Creates a formatted String representation of the LO with the categories flattened.
     * Used for display on the Review Course Proposal page.
     */
    public String getTitleAndCategoriesAsString() {
        StringBuilder out = new StringBuilder(getLoInfo().getDescr().getPlain());
        if (! getLoCategoryInfoList().isEmpty()) {
            List<String> categoryNames = new ArrayList<>();
            for (LoCategoryInfo categoryInfo : getLoCategoryInfoList()) {
                categoryNames.add(categoryInfo.getName());
            }
            out.append(" (")
                .append(StringUtils.join(categoryNames, CurriculumManagementConstants.COLLECTION_ITEMS_COMMA_DELIMITER))
                .append(")");
        }
        return out.toString();
    }
}
