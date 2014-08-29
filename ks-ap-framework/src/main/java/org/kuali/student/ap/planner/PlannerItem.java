package org.kuali.student.ap.planner;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.course.infc.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlannerItem implements
		org.kuali.student.ap.common.infc.HasUniqueId, Serializable {

	private static final long serialVersionUID = -2043370757872665126L;

	private static final Logger LOG = LoggerFactory.getLogger(PlannerItem.class);

	private String parentUniqueId;
	private String uniqueId;
	private String type;
	private String menuSuffix;

	private String learningPlanId;
	private String courseId;
	private String termId;
	private String planItemId;
	private String campusCode;
	private String courseCode;
	private String courseTitle;
	private String activityCode;
	private String courseNote;
	private BigDecimal minCredits;
	private BigDecimal maxCredits;
	private String grade;
    private AcademicPlanServiceConstants.ItemCategory category;
    private String categoryString;

	private transient String creditString;

    // PlannerItem types
    public static final String BLANK_ITEM = "Blank";
    public static final String COURSE_RECORD_ITEM = "CourseRecord";
    public static final String ADD_ITEM = "AddItem";
    public static final String PLAN_ITEM = "PlanItem";



	public PlannerItem() {
	}

	public String getParentUniqueId() {
		return parentUniqueId;
	}

	public void setParentUniqueId(String parentUniqueId) {
		this.parentUniqueId = parentUniqueId;
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getLearningPlanId() {
		return learningPlanId;
	}

	public void setLearningPlanId(String learningPlanId) {
		this.learningPlanId = learningPlanId;
	}

	public String getPlanItemId() {
		return planItemId;
	}

	public void setPlanItemId(String planItemId) {
		this.planItemId = planItemId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getCampusCode() {
		return campusCode;
	}

	public void setCampusCode(String campusCode) {
		this.campusCode = campusCode;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getCourseNote() {
		if(courseNote==null)courseNote="";
        return courseNote;
	}

	public void setCourseNote(String courseNote) {
		this.courseNote = courseNote;
	}

	public BigDecimal getMinCredits() {
		return minCredits;
	}

	public void setMinCredits(BigDecimal minCredits) {
		this.minCredits = minCredits;
		this.creditString = null;
	}

	public BigDecimal getMaxCredits() {
		return maxCredits;
	}

	public void setMaxCredits(BigDecimal maxCredits) {
		this.maxCredits = maxCredits;
		this.creditString = null;
	}

	public String getCreditString() {
		if (creditString == null && minCredits != null && maxCredits != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(CreditsFormatter.trimCredits(minCredits.toString()));
			if (minCredits.compareTo(maxCredits) < 0) {
				sb.append(" - ");
				sb.append(CreditsFormatter.trimCredits(maxCredits.toString()));
			}
			creditString = sb.toString();
		}
		return creditString;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMenuSuffix() {
		return menuSuffix;
	}

	public void setMenuSuffix(String menuSuffix) {
		this.menuSuffix = menuSuffix;
	}

    public AcademicPlanServiceConstants.ItemCategory getCategory() {
        return category;
    }

    public void setCategory(AcademicPlanServiceConstants.ItemCategory category) {
        this.category = category;
        setCategoryString(category.toString().toLowerCase());
    }

    public String getCategoryString() {
        return categoryString;
    }
    public void setCategoryString(String categoryString) {
        this.categoryString=categoryString;
    }
}
