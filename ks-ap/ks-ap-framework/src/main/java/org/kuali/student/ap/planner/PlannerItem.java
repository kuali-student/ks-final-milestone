package org.kuali.student.ap.planner;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.common.infc.HasUniqueId;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.infc.HasMeta;
import org.kuali.student.r2.common.infc.Meta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PlannerItem implements HasUniqueId, HasMeta, Serializable {

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
	private List<String> registrationGroupCodes = new ArrayList<String>();
	private String courseNote;
	private BigDecimal minCredits;
	private BigDecimal maxCredits;
    private List<BigDecimal> multipleCredits;
	private String grade;
    private AcademicPlanServiceConstants.ItemCategory category;
    private String categoryString;

    private Meta meta;

	private transient String creditString;

    private List<String> statusMessages;

    // PlannerItem types
    public static final String BLANK_ITEM = "BlankItem";
    public static final String COURSE_RECORD_ITEM = "CourseRecord";
    public static final String ADD_ITEM = "AddItem";
    public static final String PLAN_ITEM = "PlanItem";



	public PlannerItem() {
        setType(BLANK_ITEM);
        multipleCredits = new ArrayList<BigDecimal>();
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

    /**
     * Get an XML safe representation of the termId by replacing "." with "-"
     * @return A termId with all occurrences of "." replaced with "-"
     */
    public String getXmlSafeTermId() {
        return termId.replace(".", "-");
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

    public List<String> getRegistrationGroupCodes() {
        return registrationGroupCodes;
    }

    public void setRegistrationGroupCodes(List<String> registrationGroupCodes) {
        this.registrationGroupCodes = registrationGroupCodes;
	}

    public String getRegistrationGroupCodesforUI() {
        RegistrationGroupCodePropertyEditor editor = new RegistrationGroupCodePropertyEditor();
        editor.setValue(registrationGroupCodes);
        return editor.getAsText();
    }

	public String getCourseNote() {
		if(courseNote==null)courseNote="";
        return HtmlUtils.htmlEscape(courseNote);
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

    public List<BigDecimal> getMultipleCredits() {
        return multipleCredits;
    }

    public void setMultipleCredits(List<BigDecimal> multipleCredits) {
        this.multipleCredits = multipleCredits;
        this.creditString = null;
    }

    public String getCreditString() {
		if (creditString == null && minCredits != null && maxCredits != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().trimCredits(minCredits.toString()));
            if (multipleCredits != null && !multipleCredits.isEmpty()){
                if (multipleCredits.size() == 2 ){
                    sb.append(", ");
                    sb.append(KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().trimCredits(multipleCredits.get(1).toString()));
                    creditString = sb.toString();
                    return creditString;
                }
            }
			if (minCredits.compareTo(maxCredits) < 0) {
				sb.append("&ndash;");
				sb.append(KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().trimCredits(maxCredits.toString()));
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

    @Override
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<String> getStatusMessages() {
        return statusMessages;
    }

    public void setStatusMessages(List<String> statusMessages) {
        this.statusMessages = statusMessages;
    }

    public String getStatusMessagesForUI(){
        StringBuilder stringBuilder = new StringBuilder();
        if(statusMessages != null){
            for(String message : statusMessages){
                stringBuilder.append(message+"<br>");
            }
        }

        return stringBuilder.toString();
    }
}
