package org.kuali.student.ap.planner;

import org.kuali.student.ap.common.infc.HasUniqueId;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.course.CreditsFormatter;
import org.kuali.student.r2.core.acal.infc.Term;
import org.springframework.web.util.HtmlUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PlannerTerm implements HasUniqueId, Serializable, Comparable<PlannerTerm> {

	private static final long serialVersionUID = 5885238483581189013L;

	private String uniqueId;
	private String termId;
	private String termName;

	private boolean planning;
	private boolean official;
	private boolean inProgress;
	private boolean cartAvailable;
	private boolean completed;

	private List<PlannerItem> plannedList;
	private List<PlannerItem> backupList;
	private List<PlannerItem> academicRecord;
	private List<PlannerItem> cartList;
	private List<PlannerTermNote> termNoteList;

	private transient String allTermNotes;

	private transient BigDecimal totalCompletedMinCredits;
	private transient BigDecimal totalCompletedMaxCredits;
	private transient String completedCreditString;

	private transient BigDecimal totalCartMinCredits;
	private transient BigDecimal totalCartMaxCredits;
	private transient String cartCreditString;

	private transient BigDecimal totalPlannedMinCredits;
	private transient BigDecimal totalPlannedMaxCredits;
	private transient String plannedCreditString;

	public PlannerTerm() {
	}

	public PlannerTerm(String termId) {
		this.termId = termId;

		TermHelper termHelper = KsapFrameworkServiceLocator.getTermHelper();
		Term term = termHelper.getTerm(termId);
		termName = term.getName();
		planning = termHelper.isPlanning(termId);
		official = termHelper.isOfficial(termId);
		completed = termHelper.isCompleted(termId);
		cartAvailable = KsapFrameworkServiceLocator.getShoppingCartStrategy()
				.isCartAvailable(termId, null);

		Date now = new Date();
		inProgress = !now.before(term.getStartDate())
				&& !now.after(term.getEndDate());
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public boolean isPlanning() {
		return planning;
	}

	public void setPlanning(boolean planning) {
		this.planning = planning;
	}

	public boolean isOfficial() {
		return official;
	}

	public void setOfficial(boolean official) {
		this.official = official;
	}

	public boolean isInProgress() {
		return inProgress;
	}

	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isCartAvailable() {
		return cartAvailable;
	}

	public void setCartAvailable(boolean cartAvailable) {
		this.cartAvailable = cartAvailable;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public List<PlannerItem> getPlannedList() {
		return plannedList;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public void setPlannedList(List<PlannerItem> plannedList) {
		this.plannedList = plannedList;
		this.totalPlannedMinCredits = null;
		this.totalPlannedMaxCredits = null;
		this.plannedCreditString = null;
	}

	public List<PlannerItem> getBackupList() {
		return backupList;
	}

	public void setBackupList(List<PlannerItem> backupList) {
		this.backupList = backupList;
	}

	public List<PlannerItem> getAcademicRecord() {
		return academicRecord;
	}

	public void setAcademicRecord(List<PlannerItem> academicRecord) {
		this.academicRecord = academicRecord;
		this.totalCompletedMinCredits = null;
		this.totalCompletedMaxCredits = null;
		this.completedCreditString = null;
	}

	public List<PlannerItem> getCartList() {
		return cartList;
	}

	public void setCartList(List<PlannerItem> cartList) {
		this.cartList = cartList;
		this.totalCartMinCredits = null;
		this.totalCartMaxCredits = null;
		this.cartCreditString = null;
	}

	public List<PlannerTermNote> getTermNoteList() {
		return termNoteList;
	}

	public void setTermNoteList(List<PlannerTermNote> termNoteList) {
		this.termNoteList = termNoteList;
	}

	public String getAllTermNotesHtml() {
		if (allTermNotes == null && termNoteList != null
				&& !termNoteList.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (PlannerTermNote termNote : termNoteList) {
				if (sb.length() > 0)
					sb.append("<br /><br />");
				// sb.append(StringEscapeUtils.escapeHtml(termNote.getTermNote()));
				sb.append(HtmlUtils.htmlEscape(termNote.getTermNote()));
			}
			allTermNotes = sb.toString();
		}
		return allTermNotes;
	}

	public BigDecimal getTotalCompletedMinCredits() {
		if (totalCompletedMinCredits == null) {
			BigDecimal credits = BigDecimal.ZERO;
			if (academicRecord != null)
				for (PlannerItem item : academicRecord)
					if (item.getMinCredits() != null)
						credits = credits.add(item.getMinCredits());
			totalCompletedMinCredits = credits;
		}
		return totalCompletedMinCredits;
	}

	public BigDecimal getTotalCompletedMaxCredits() {
		if (totalCompletedMaxCredits == null) {
			BigDecimal credits = BigDecimal.ZERO;
			if (academicRecord != null)
				for (PlannerItem item : academicRecord)
					if (item.getMaxCredits() != null)
						credits = credits.add(item.getMaxCredits());
			totalCompletedMaxCredits = credits;
		}
		return totalCompletedMaxCredits;
	}

	public String getCompletedCreditString() {
		if (completedCreditString == null) {
			BigDecimal min = getTotalCompletedMinCredits();
			BigDecimal max = getTotalCompletedMaxCredits();
			StringBuilder sb = new StringBuilder();
			sb.append(CreditsFormatter.trimCredits(min.toString()));
			if (min.compareTo(max) < 0) {
				sb.append(" - ");
				sb.append(CreditsFormatter.trimCredits(max.toString()));
			}
			completedCreditString = sb.toString();
		}
		return completedCreditString;
	}

	public BigDecimal getTotalCartMinCredits() {
		if (totalCartMinCredits == null) {
			BigDecimal credits = BigDecimal.ZERO;
			if (cartList != null)
				for (PlannerItem item : cartList)
					if (item.getMinCredits() != null)
						credits = credits.add(item.getMinCredits());
			totalCartMinCredits = credits;
		}
		return totalCartMinCredits;
	}

	public BigDecimal getTotalCartMaxCredits() {
		if (totalCartMaxCredits == null) {
			BigDecimal credits = BigDecimal.ZERO;
			if (cartList != null)
				for (PlannerItem item : cartList)
					if (item.getMaxCredits() != null)
						credits = credits.add(item.getMaxCredits());
			totalCartMaxCredits = credits;
		}
		return totalCartMaxCredits;
	}

	public String getCartCreditString() {
		if (cartCreditString == null) {
			BigDecimal min = getTotalCartMinCredits();
			BigDecimal max = getTotalCartMaxCredits();
			StringBuilder sb = new StringBuilder();
			sb.append(CreditsFormatter.trimCredits(min.toString()));
			if (min.compareTo(max) < 0) {
				sb.append(" - ");
				sb.append(CreditsFormatter.trimCredits(max.toString()));
			}
			cartCreditString = sb.toString();
		}
		return cartCreditString;
	}

	public BigDecimal getTotalPlannedMinCredits() {
		if (totalPlannedMinCredits == null) {
			BigDecimal credits = BigDecimal.ZERO;
			if (plannedList != null)
				for (PlannerItem item : plannedList)
					if (item.getMinCredits() != null)
						credits = credits.add(item.getMinCredits());
			totalPlannedMinCredits = credits;
		}
		return totalPlannedMinCredits;
	}

	public BigDecimal getTotalPlannedMaxCredits() {
		if (totalPlannedMaxCredits == null) {
			BigDecimal credits = BigDecimal.ZERO;
			if (plannedList != null)
				for (PlannerItem item : plannedList)
					if (item.getMaxCredits() != null)
						credits = credits.add(item.getMaxCredits());
			totalPlannedMaxCredits = credits;
		}
		return totalPlannedMaxCredits;
	}

	public String getPlannedCreditString() {
		if (plannedCreditString == null) {
			BigDecimal min = getTotalPlannedMinCredits();
			BigDecimal max = getTotalPlannedMaxCredits();
			StringBuilder sb = new StringBuilder();
			sb.append(CreditsFormatter.trimCredits(min.toString()));
			if (min.compareTo(max) < 0) {
				sb.append(" - ");
				sb.append(CreditsFormatter.trimCredits(max.toString()));
			}
			plannedCreditString = sb.toString();
		}
		return plannedCreditString;
	}

    @Override
    public int compareTo(PlannerTerm o) {
        Term t1 = KsapFrameworkServiceLocator.getTermHelper().getTerm(this.termId);
        Term t2 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o.termId);
        return t1.getStartDate().compareTo(t2.getStartDate());
    }
}
