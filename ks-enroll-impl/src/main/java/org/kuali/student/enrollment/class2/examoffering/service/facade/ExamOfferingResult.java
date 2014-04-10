package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.kuali.student.r2.common.dto.BulkStatusInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helium on 2014/04/09.
 */
public class ExamOfferingResult {

    private List<BulkStatusInfo> examOfferingsCreated; //status for whether or not exam offerings are generated
    private List<BulkStatusInfo> examOfferingsUpdated; //status for whether or not exam offerings are generated
    private List<BulkStatusInfo> examOfferingsSlotted; //status for whether or not exam offerings are generated
    private BulkStatusInfo examPeriodStatus; //status for whether or not exam period exists


    public ExamOfferingResult(boolean examPeriodExist) {
        examOfferingsCreated = new ArrayList<BulkStatusInfo>();
        examOfferingsUpdated = new ArrayList<BulkStatusInfo>();
        examOfferingsSlotted = new ArrayList<BulkStatusInfo>();
        examPeriodStatus = new BulkStatusInfo();
        examPeriodStatus.setSuccess(examPeriodExist);
    }

    public List<BulkStatusInfo> getExamOfferingsCreated() {
        return examOfferingsCreated;
    }

    public void setExamOfferingsCreated(List<BulkStatusInfo> examOfferingsCreated) {
        this.examOfferingsCreated = examOfferingsCreated;
    }

    public List<BulkStatusInfo> getExamOfferingsUpdated() {
        return examOfferingsUpdated;
    }

    public void setExamOfferingsUpdated(List<BulkStatusInfo> examOfferingsUpdated) {
        this.examOfferingsUpdated = examOfferingsUpdated;
    }

    public List<BulkStatusInfo> getExamOfferingsSlotted() {
        return examOfferingsSlotted;
    }

    public void setExamOfferingsSlotted(List<BulkStatusInfo> examOfferingsSlotted) {
        this.examOfferingsSlotted = examOfferingsSlotted;
    }

    public BulkStatusInfo getExamPeriodStatus() {
        return examPeriodStatus;
    }

    public void setExamPeriodStatus(BulkStatusInfo examPeriodStatus) {
        this.examPeriodStatus = examPeriodStatus;
    }
}
