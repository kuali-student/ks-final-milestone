package org.kuali.student.ap.planner.support;

import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.course.CreditsFormatter;
import org.kuali.student.ap.framework.course.CreditsFormatter.Range;
import org.kuali.student.ap.planner.PlannerForm;
import org.kuali.student.ap.planner.PlannerItem;
import org.kuali.student.ap.planner.PlannerTerm;
import org.kuali.student.ap.planner.PlannerTermNote;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.lum.course.infc.Course;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

/**
 * KSAP planner form.
 * 
 * <p>
 * Refactored from original PlanController and PlanForm, for improved stability,
 * performance, and maintainability.
 * </p>
 * 
 * @author Mark Fyffe
 * @version 0.7
 */
public class DefaultPlannerForm extends AbstractPlanItemForm implements
		PlannerForm {

	private static final long serialVersionUID = -7205838511702504159L;

	private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

	private String courseCd;
	private BigDecimal courseCredit;
	private String courseNote;
	private String termNote;
	private boolean backup;

	private AcademicPlanServiceConstants.ItemCategory expectedPlanItemCategory;
	private AcademicPlanServiceConstants.ItemCategory targetPlanItemCategory;

	private String targetTermId;

	private int focusTermIndex;

	private transient boolean termNoteInitialized;
	private transient BigDecimal creditsForPlanItem;
	private transient List<PlannerTerm> terms;
	private transient String creditString;

	@Override
	public void setLearningPlanId(String learningPlanId) {
		super.setLearningPlanId(learningPlanId);
		this.creditsForPlanItem = null;
	}

	@Override
	public void setPlanItemId(String planItemId) {
		super.setPlanItemId(planItemId);
		this.creditsForPlanItem = null;
	}

	public boolean isBackup() {
		return backup;
	}

	public void setBackup(boolean backup) {
		this.backup = backup;
	}

	@Override
	public void setCourseId(String courseId) {
		super.setCourseId(courseId);
		this.creditString = null;
	}

	public String getCourseCd() {
		return courseCd;
	}

	public void setCourseCd(String courseCd) {
		this.courseCd = courseCd;
	}

	public BigDecimal getCourseCredit() {
		return courseCredit;
	}

	public void setCourseCredit(BigDecimal courseCredit) {
		this.courseCredit = courseCredit;
		this.creditsForPlanItem = null;
	}

	public String getCreditString() {
		return creditString == null ? creditString = courseCredit == null ? CreditsFormatter
				.formatCredits(getCourse()) : CreditsFormatter
				.trimCredits(courseCredit.toString())
				: creditString;
	}

	public BigDecimal getCreditsForPlanItem() {
		return getCreditsForPlanItem(getCourse());
	}
    public BigDecimal getCreditsForPlanItem(Course course) {
        if (creditsForPlanItem == null && courseCredit != null) {
            BigDecimal minCredit = BigDecimal.ZERO;
            BigDecimal maxCredit = ONE_HUNDRED;
            ResultValuesGroupInfo rci = course.getCreditOptions().get(0);
            String type = rci.getTypeKey();
            if (type.equals("kuali.result.values.group.type.fixed")) {
                boolean useAttributes = rci.getResultValueKeys().isEmpty();
                if (!useAttributes)
                    try {
                        ResultValueInfo rv = KsapFrameworkServiceLocator
                                .getLrcService().getResultValue(
                                        rci.getResultValueKeys().get(0),
                                        KsapFrameworkServiceLocator
                                                .getContext().getContextInfo());
                        if (rv == null)
                            useAttributes = true;
                        else
                            minCredit = maxCredit = new BigDecimal(
                                    rv.getValue());
                    } catch (DoesNotExistException e) {
                        throw new IllegalArgumentException("LRC lookup error",
                                e);
                    } catch (InvalidParameterException e) {
                        throw new IllegalArgumentException("LRC lookup error",
                                e);
                    } catch (MissingParameterException e) {
                        throw new IllegalArgumentException("LRC lookup error",
                                e);
                    } catch (OperationFailedException e) {
                        throw new IllegalStateException("LRC lookup error", e);
                    } catch (PermissionDeniedException e) {
                        throw new IllegalStateException("LRC lookup error", e);
                    }
                if (useAttributes)
                    minCredit = maxCredit = new BigDecimal(
                            rci.getAttributeValue("fixedCreditValue"));
            } else if (type.equals("kuali.result.values.group.type.range")) {
                ResultValueRangeInfo rvr = rci.getResultValueRange();
                if (rvr != null) {
                    minCredit = new BigDecimal(rvr.getMinValue());
                    maxCredit = new BigDecimal(rvr.getMaxValue());
                } else {
                    minCredit = new BigDecimal(
                            rci.getAttributeValue("minCreditValue"));
                    maxCredit = new BigDecimal(
                            rci.getAttributeValue("maxCreditValue"));
                }
            }

            if (courseCredit.compareTo(maxCredit) > 0)
                creditsForPlanItem = maxCredit;
            else if (courseCredit.compareTo(minCredit) < 0)
                creditsForPlanItem = minCredit;
            else
                creditsForPlanItem = courseCredit;

        }
        return creditsForPlanItem;
    }

	public String getCourseNote() {
		return courseNote;
	}

	public void setCourseNote(String courseNote) {
		this.courseNote = courseNote;
	}

	public String getTermNote() {
		String termId;
		if (termNote == null && !termNoteInitialized
				&& (termId = getTermId()) != null) {
			CommentService commentService = KsapFrameworkServiceLocator
					.getCommentService();
			List<CommentInfo> commentInfos;
			try {
				commentInfos = commentService.getCommentsByReferenceAndType(
						getLearningPlan().getId(),
						PlanConstants.TERM_NOTE_COMMENT_TYPE,
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("Comment lookup failure", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("Comment lookup failure", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("Comment lookup failure", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("Comment lookup failure", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("Comment lookup failure", e);
			}

			for (CommentInfo comment : commentInfos) {
				String commentAtpId = comment
						.getAttributeValue(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
				if (termId.equals(commentAtpId)) {
					RichTextInfo text = comment.getCommentText();
					if (text != null)
						termNote = text.getPlain();
				}
			}

			termNoteInitialized = true;
		}

		return termNote;
	}

	public void setTermNote(String termNote) {
		this.termNote = termNote;
		termNoteInitialized = true;
	}

	public AcademicPlanServiceConstants.ItemCategory getExpectedPlanItemCategory() {
		return expectedPlanItemCategory;
	}

	public void setExpectedPlanItemCategory(AcademicPlanServiceConstants.ItemCategory category) {
		this.expectedPlanItemCategory = category;
	}

	public AcademicPlanServiceConstants.ItemCategory getTargetPlanItemCategory() {
		return targetPlanItemCategory;
	}

	public void setTargetPlanItemCategory(AcademicPlanServiceConstants.ItemCategory targetPlanItemCategory) {
		this.targetPlanItemCategory = targetPlanItemCategory;
	}

	public String getTargetTermId() {
		return targetTermId;
	}

	public void setTargetTermId(String targetTermId) {
		this.targetTermId = targetTermId;
	}

	public int getFocusTermIndex() {
		return focusTermIndex;
	}

	public void setFocusTermIndex(int focusTermIndex) {
		this.focusTermIndex = focusTermIndex;
	}

	@Override
	public void populateFromPlanItem() {
		PlanItem planItem = getPlanItem();
		setCourseId(planItem.getRefObjectId());

		Course course = getCourse();
		assert course != null;

		setCourseCd(course.getCode());
		setBackup(planItem.getCategory().equals(
                AcademicPlanServiceConstants.ItemCategory.BACKUP));

		RichText descr = planItem.getDescr();
		setCourseNote(descr != null && StringUtils.hasText(descr.getPlain()) ? descr
				.getPlain() : null);

		setCourseCredit(planItem.getCredit());
	}

	public List<PlannerTerm> getTerms() {
		if (terms == null) {

			LearningPlan learningPlan = getLearningPlan();

			Map<String, List<PlannerItem>> planned = new HashMap<String, List<PlannerItem>>();
			Map<String, List<PlannerItem>> backup = new HashMap<String, List<PlannerItem>>();
			Map<String, List<PlannerItem>> cart = new HashMap<String, List<PlannerItem>>();
			Map<String, List<PlannerItem>> completed = new HashMap<String, List<PlannerItem>>();
			Map<String, List<PlannerTermNote>> termNotes = new HashMap<String, List<PlannerTermNote>>();

			List<StudentCourseRecordInfo> completedRecords;
			try {
				completedRecords = KsapFrameworkServiceLocator
						.getAcademicRecordService().getCompletedCourseRecords(
								learningPlan.getStudentId(),
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("AR lookup failure", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("AR lookup failure", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("AR lookup failure", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("AR lookup failure", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("AR lookup failure", e);
			}

			List<PlanItem> planItems;
			try {
				planItems = new ArrayList<PlanItem>(KsapFrameworkServiceLocator
						.getAcademicPlanService().getPlanItemsInPlan(
								learningPlan.getId(),
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo()));
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("LP lookup failure", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("LP lookup failure", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("LP lookup failure", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("LP lookup failure", e);
			}

			List<String> courseIds = new ArrayList<String>(
					completedRecords.size() + planItems.size());
			SortedSet<String> termIds = new TreeSet<String>();

			Iterator<PlanItem> planItemIterator = planItems.iterator();
			while (planItemIterator.hasNext()) {
				PlanItem planItem = planItemIterator.next();

				AcademicPlanServiceConstants.ItemCategory category = planItem.getCategory();
				String refTypeKey = planItem.getRefObjectType();
				List<String> planPeriods = planItem.getPlanPeriods();
				if (!PlanConstants.COURSE_TYPE.equals(refTypeKey)
						|| (!AcademicPlanServiceConstants.ItemCategory.CART
								.equals(category)
								&& !AcademicPlanServiceConstants.ItemCategory.PLANNED
										.equals(category) && !AcademicPlanServiceConstants.ItemCategory.BACKUP
									.equals(category)) || planPeriods == null
						|| planPeriods.isEmpty()) {
					planItemIterator.remove();
					continue;
				}

				termIds.addAll(planPeriods);
				courseIds.add(planItem.getRefObjectId());
			}

			if (completedRecords != null)
				for (StudentCourseRecordInfo completedRecord : completedRecords) {
					String termId =completedRecord.getTermId();
					termIds.add(termId);
					List<PlannerItem> itemList = completed.get(termId);
					if (itemList == null)
						completed.put(termId,
								itemList = new LinkedList<PlannerItem>());
					itemList.add(new PlannerItem(completedRecord));
				}

			CourseHelper courseHelper = KsapFrameworkServiceLocator
					.getCourseHelper();
			courseHelper.frontLoad(courseIds);

			for (PlanItem planItem : planItems) {
				AcademicPlanServiceConstants.ItemCategory category = planItem.getCategory();
				Course course = courseHelper.getCourseInfo(planItem
						.getRefObjectId());

				for (String termId : planItem.getPlanPeriods()) {
					Map<String, List<PlannerItem>> itemMap;
					if (AcademicPlanServiceConstants.ItemCategory.CART
							.equals(category))
						itemMap = cart;
					else if (AcademicPlanServiceConstants.ItemCategory.PLANNED
							.equals(category))
						itemMap = planned;
					else if (AcademicPlanServiceConstants.ItemCategory.BACKUP
							.equals(category))
						itemMap = backup;
					else
						throw new UnsupportedOperationException(
								"Plan item category " + category);

					List<PlannerItem> itemList = itemMap.get(termId);
					if (itemList == null)
						itemMap.put(termId,
								itemList = new LinkedList<PlannerItem>());

					itemList.add(new PlannerItem(planItem, course));
				}
			}

			TermHelper termHelper = KsapFrameworkServiceLocator.getTermHelper();
            List<Term> tempTerms = new ArrayList<Term>();
            for(String tempId : termIds){
                tempTerms.add(termHelper.getTerm(tempId));
            }
            String firstTermId = termIds.first();
            Term firstTerm;
            if(tempTerms.size()>0){
                tempTerms = termHelper.sortTermsByStartDate(tempTerms,true);
                firstTerm = tempTerms.get(0);
                firstTermId = tempTerms.get(0).getId();
            }else{
                firstTerm = termHelper.getTerm(firstTermId);
            }
			termHelper.frontLoadForPlanner(firstTermId);

			List<Term> calendarTerms = KsapFrameworkServiceLocator.getPlanHelper().getCalendarTerms(firstTerm);
			String focusTermId = KsapFrameworkServiceLocator.getPlanHelper().getStartTermId();

			Calendar cal = Calendar.getInstance();
			cal.setTime(firstTerm.getStartDate());

			CommentService commentService = KsapFrameworkServiceLocator
					.getCommentService();
			List<CommentInfo> commentInfos;
			try {
				commentInfos = commentService.getCommentsByReferenceAndType(
						learningPlan.getId(),
						PlanConstants.TERM_NOTE_COMMENT_TYPE,
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("Comment lookup failure", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("Comment lookup failure", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("Comment lookup failure", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("Comment lookup failure", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("Comment lookup failure", e);
			}

			for (CommentInfo comment : commentInfos) {
				PlannerTermNote newTermNote = new PlannerTermNote();
				String termId = comment
						.getAttributeValue(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
				newTermNote.setUniqueId(termId.replace('.', '-'));
				newTermNote.setTermId(termId);
				newTermNote.setDate(comment.getEffectiveDate());
				newTermNote
						.setTermNote(comment.getCommentText().getFormatted());

				List<PlannerTermNote> termNoteList = termNotes.get(termId);
				if (termNoteList == null)
					termNotes.put(termId,
							termNoteList = new LinkedList<PlannerTermNote>());

				termNoteList.add(newTermNote);
			}

			List<PlannerTerm> pterms = new ArrayList<PlannerTerm>(
					termIds.size());
            for(Term t : calendarTerms){
			    String termId = t.getId();
				PlannerTerm pterm = new PlannerTerm(termId);
				pterm.setUniqueId(UUID.randomUUID().toString());

				List<PlannerItem> completedList = completed.get(termId);
				pterm.setAcademicRecord(completedList == null ? completedList = new ArrayList<PlannerItem>()
						: completedList);
				for (PlannerItem item : completedList)
					item.setParentUniqueId(pterm.getUniqueId());

				List<PlannerItem> cartList = cart.get(termId);
				pterm.setCartList(cartList == null ? cartList = new ArrayList<PlannerItem>()
						: cartList);
				for (PlannerItem item : cartList)
					item.setParentUniqueId(pterm.getUniqueId());

				List<PlannerItem> plannedList = planned.get(termId);
				pterm.setPlannedList(plannedList == null ? plannedList = new ArrayList<PlannerItem>()
						: plannedList);
				for (PlannerItem item : plannedList)
					item.setParentUniqueId(pterm.getUniqueId());

				List<PlannerItem> backupList = backup.get(termId);
				pterm.setBackupList(backupList == null ? backupList = new ArrayList<PlannerItem>()
						: backupList);
				for (PlannerItem item : backupList)
					item.setParentUniqueId(pterm.getUniqueId());

				List<PlannerTermNote> termNotesList = termNotes.get(termId);
				pterm.setTermNoteList(termNotesList == null ? termNotesList = new ArrayList<PlannerTermNote>()
						: termNotesList);
				for (PlannerTermNote termNote : termNotesList)
					termNote.setParentUniqueId(pterm.getUniqueId());

				if (termId.equals(focusTermId)) {
					focusTermIndex = pterms.size();
				}

				pterms.add(pterm);
			}

			terms = pterms;
            Collections.sort(terms);
		}

		return terms;
	}

	public boolean isVariableCredit() {
		Range range = CreditsFormatter.getRange(getCourse());
		return !range.getMax().equals(range.getMin());
	}

}
