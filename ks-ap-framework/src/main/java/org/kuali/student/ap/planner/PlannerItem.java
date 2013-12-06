package org.kuali.student.ap.planner;

import org.apache.log4j.Logger;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.course.CreditsFormatter;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.course.infc.Course;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PlannerItem implements
		org.kuali.student.ap.common.infc.HasUniqueId, Serializable {

	private static final long serialVersionUID = -2043370757872665126L;

	private static final Logger LOG = Logger.getLogger(PlannerItem.class);

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

	public PlannerItem() {
	}

	public PlannerItem(StudentCourseRecordInfo completedRecord) {
		uniqueId = UUID.randomUUID().toString();
		termId = completedRecord.getTermName();
		campusCode = completedRecord.getAttributeValue("campusCode");
		courseCode = completedRecord.getCourseCode();
        List<Course> courses = KsapFrameworkServiceLocator.getCourseHelper().getCoursesByCode(courseCode);
        if(courses != null){
            if(courses.size()>0) courseId = courses.get(0).getId();
        }
		activityCode = completedRecord.getActivityCode();
		courseTitle = completedRecord.getCourseTitle();

		String creditsString = completedRecord.getCreditsEarned();
		try {
			minCredits = maxCredits = creditsString == null ? BigDecimal.ZERO
					: new BigDecimal(creditsString);
		} catch (NumberFormatException e) {
			LOG.warn(
					"Invalid credits in course record "
							+ completedRecord.getCreditsEarned(), e);
		}

		String grade = completedRecord.getCalculatedGradeValue();
		if (!"X".equalsIgnoreCase(grade)
				|| KsapFrameworkServiceLocator.getTermHelper().isCompleted(
						termId)) {
			setGrade(grade);
		}

	}

	public PlannerItem(PlanItem planItem, Course course) {
		uniqueId = UUID.randomUUID().toString();
		termId = planItem.getPlanPeriods().get(0);
		learningPlanId = planItem.getLearningPlanId();
		planItemId = planItem.getId();
		courseId = planItem.getRefObjectId();
        setCategory(planItem.getCategory());
        type = planItem.getTypeKey();

		for (Attribute attr : planItem.getAttributes()) {
			String key = attr.getKey();
			if ("campusCode".equals(key))
				campusCode = attr.getValue();
			if ("activityCode".equals(key))
				activityCode = attr.getValue();
		}

		for (Attribute attr : course.getAttributes()) {
			String key = attr.getKey();
			if ("campusCode".equals(key))
				campusCode = attr.getValue();
		}

		BigDecimal credits = planItem.getCredit();
		if (credits == null) {
			CreditsFormatter.Range range = CreditsFormatter.getRange(course);
			if (range != null) {
				minCredits = range.getMin();
				maxCredits = range.getMax();
			}
		} else {
			minCredits = maxCredits = credits;
		}

		courseCode = course.getCode();
		courseTitle = course.getCourseTitle();

		RichText descr = planItem.getDescr();
		if (descr != null)
			courseNote = descr.getPlain();

        menuSuffix = "";
		if (AcademicPlanServiceConstants.ItemCategory.PLANNED
				.equals(planItem.getCategory()) && campusCode != null
					&& KsapFrameworkServiceLocator.getShoppingCartStrategy()
							.isCartAvailable(termId, campusCode)) {
				menuSuffix = "_cartavailable";
        }
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
