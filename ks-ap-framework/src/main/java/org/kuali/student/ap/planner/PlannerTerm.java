/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.ap.planner;

import org.kuali.student.ap.common.infc.HasUniqueId;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.r2.core.acal.infc.Term;
import org.springframework.web.util.HtmlUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Data Object class for displaying a term in the planner calendar
 * Used for beans:
 * PlannerUI.xml#
 */
public class PlannerTerm implements HasUniqueId, Serializable, Comparable<PlannerTerm> {

	private static final long serialVersionUID = 5885238483581189013L;

	private String uniqueId;
	private String termId;
	private String termName;

	private boolean planning;
	private boolean official;
	private boolean inProgress;
	private boolean completed;
	private boolean futureTerm;
	private boolean currentTerm;
    private boolean registrationOpen;

	private List<PlannerItem> plannedList;
	private List<PlannerItem> backupList;
	private List<PlannerItem> academicRecord;
	private List<PlannerItem> cartList;
	private List<PlannerItem> registrationList;
	private List<PlannerTermNote> termNoteList;

	private transient String allTermNotes;

    private transient BigDecimal creditLineMinCredits;
    private transient BigDecimal creditLineMaxCredits;
    private transient String creditLineString;

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
        inProgress = termHelper.isInProgress(termId);
        futureTerm = termHelper.isFutureTerm(termId);
        registrationOpen = termHelper.isRegistrationOpen(termId);
        currentTerm = termHelper.isCurrentTerm(termId);
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
	}

	public List<PlannerItem> getCartList() {
		return cartList;
	}

	public void setCartList(List<PlannerItem> cartList) {
		this.cartList = cartList;
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

    public BigDecimal getCreditLineMinCredits() {
        if (creditLineMinCredits == null) {
            creditLineMinCredits = BigDecimal.ZERO;
        }
        return creditLineMinCredits;
    }

    public BigDecimal getCreditLineMaxCredits() {
        if (creditLineMaxCredits == null) {
            creditLineMaxCredits = BigDecimal.ZERO;
        }
        return creditLineMaxCredits;
    }

    public String getCreditLineString() {
        if (creditLineString == null) {
            BigDecimal min = getCreditLineMinCredits();
            BigDecimal max = getCreditLineMaxCredits();
            StringBuilder sb = new StringBuilder();
            sb.append(CreditsFormatter.trimCredits(min.toString()));
            if (min.compareTo(max) < 0) {
                sb.append(" - ");
                sb.append(CreditsFormatter.trimCredits(max.toString()));
            }
            creditLineString = sb.toString();
        }
        return creditLineString;
    }

    public void setCreditLineMinCredits(BigDecimal creditLineMinCredits) {
        this.creditLineMinCredits = creditLineMinCredits;
    }

    public void setCreditLineMaxCredits(BigDecimal creditLineMaxCredits) {
        this.creditLineMaxCredits = creditLineMaxCredits;
    }

    public void setCreditLineString(String creditLineString) {
        this.creditLineString = creditLineString;
    }

    public boolean isFutureTerm() {
        return futureTerm;
    }

    public void setFutureTerm(boolean futureTerm) {
        this.futureTerm = futureTerm;
    }

    public boolean isRegistrationOpen() {
        return registrationOpen;
    }

    public void setRegistrationOpen(boolean registrationOpen) {
        this.registrationOpen = registrationOpen;
    }

    public List<PlannerItem> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(List<PlannerItem> registrationList) {
        this.registrationList = registrationList;
    }

    public boolean isCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(boolean currentTerm) {
        this.currentTerm = currentTerm;
    }

    @Override
    public int compareTo(PlannerTerm o) {
        Term t1 = KsapFrameworkServiceLocator.getTermHelper().getTerm(this.termId);
        Term t2 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o.termId);
        return t1.getStartDate().compareTo(t2.getStartDate());
    }
}
