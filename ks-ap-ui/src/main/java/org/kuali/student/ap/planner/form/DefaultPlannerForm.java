package org.kuali.student.ap.planner.form;

import org.kuali.rice.krad.web.bind.RequestAccessible;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.coursesearch.CreditsFormatter.Range;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.planner.PlannerForm;
import org.kuali.student.ap.planner.PlannerTerm;
import org.kuali.student.common.collection.KSCollectionUtils;
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
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

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

    @RequestAccessible
    private String courseCd;
    @RequestAccessible
    private BigDecimal courseCredit;
    @RequestAccessible
    private String courseNote;
    @RequestAccessible
    private String termNote;
    @RequestAccessible
    private boolean backup;
    //used to indicated if the course has been bookmarked in KSAP
    //Note: because when bookmarked is true, we need to display
    //      the checkbox of keepBookmarked and leave it as unchecked by default,
    //      we have to define two separate fields
    @RequestAccessible
    private boolean bookmarked;
    //used to indicated if the course has been planned already in KSAP
    //Note: because when it is planned already, we need to display
    //      a msg indicating it on the bookmark add to plan dialog
    @RequestAccessible
    private String plannedMessage;

    //used to hold the checkbox value in "Add to Plan" popover form
    @RequestAccessible
    private boolean keepBookmarked;

    @RequestAccessible
    private AcademicPlanServiceConstants.ItemCategory expectedPlanItemCategory;
    @RequestAccessible
    private AcademicPlanServiceConstants.ItemCategory targetPlanItemCategory;

    @RequestAccessible
    private String targetTermId;

    @RequestAccessible
    private int focusTermIndex = -1;

    @RequestAccessible
    private transient boolean termNoteInitialized;
    @RequestAccessible
    private transient BigDecimal creditsForPlanItem;
    private transient List<PlannerTerm> terms;
    @RequestAccessible
    private transient String creditString;
    @RequestAccessible
    private List<String> plannedTermIds;

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

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public boolean isKeepBookmarked() {
        return keepBookmarked;
    }

    public void setKeepBookmarked(boolean keepBookmarked) {
        this.keepBookmarked = keepBookmarked;
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
            ResultValuesGroupInfo rci;
            try{
                rci = KSCollectionUtils.getRequiredZeroElement(course.getCreditOptions());
            }catch (OperationFailedException e){
                throw new RuntimeException("Invalid Credit Options", e);
            }
            String type = rci.getTypeKey();
            if (type.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                boolean useAttributes = rci.getResultValueKeys().isEmpty();
                if (!useAttributes)
                    try {
                        ResultValueInfo rv = KsapFrameworkServiceLocator
                                .getLrcService().getResultValue(
                                        KSCollectionUtils.getRequiredZeroElement(rci.getResultValueKeys()),
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
            } else if (type.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
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
                commentInfos = commentService.getCommentsByRefObject(
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
        if(focusTermIndex<0){
            focusTermIndex=0;
            String focusTermId = KsapFrameworkServiceLocator.getPlanHelper().getPlannerFirstTermId();
            for(int i =0; i<getTerms().size();i++){
                PlannerTerm term = getTerms().get(i);
                if(term.getTermId().equals(focusTermId)){
                    focusTermIndex=i;
                    break;
                }
            }

        }
        return focusTermIndex;
    }

    public void setFocusTermIndex(int focusTermIndex) {
        this.focusTermIndex = focusTermIndex;
    }

    public String getPlannedMessage() {
        return this.plannedMessage;
    }

    public void setPlannedMessage(String plannedMessage) {
        this.plannedMessage = plannedMessage;
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

        setCourseCredit(planItem.getCredits());
    }

    public List<PlannerTerm> getTerms() {
        if (terms == null) {
            terms = KsapFrameworkServiceLocator.getPlanHelper().getPlannerTerms(getLearningPlan());
        }

        return terms;
    }

    public boolean isVariableCredit() {
        Range range = CreditsFormatter.getRange(getCourse());
        if(range.getMultiple()!=null && !range.getMultiple().isEmpty()) return true;
        return !range.getMax().equals(range.getMin());
    }

    @Override
    public List<String> getPlannedTermIds() {
        return plannedTermIds;
    }

    @Override
    public void setPlannedTermIds(List<String> plannedTermIds) {
        this.plannedTermIds = plannedTermIds;
    }
}
