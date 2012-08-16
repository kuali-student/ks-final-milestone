/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.core.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

/**
 * This class holds the constants used by the Hold service
 *
 * @author nwright
 */
public class HoldServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "hold";
    public static final String SERVICE_NAME_LOCAL_PART = "HoldService";
    public static final String REF_OBJECT_URI_HOLD = NAMESPACE + "/" + AppliedHoldInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ISSUE = NAMESPACE + "/" + HoldIssueInfo.class.getSimpleName();
    public static final String REF_OBJECT_TYPE_URI_ISSUE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "type" + "/" + HoldIssueInfo.class.getSimpleName();
    /**
     * Hold types
     */
    public static final String STUDENT_HOLD_TYPE_KEY = "kuali.hold.type.student";
    public static final String INTRUCTOR_HOLD_TYPE_KEY = "kuali.hold.type.instructor";
    /**
     * ISSUE TYPES
     */
    public static final String OVERDUE_LIBRARY_MATERIALS_ISSUE_TYPE_KEY = "kuali.hold.issue.type.overdue.library.materials";
    public static final String FINANCIAL_ISSUE_TYPE_KEY = "kuali.hold.issue.type.financial";
    public static final String MEDICAL_IMMUNIZATION_ISSUE_TYPE_KEY = "kuali.hold.issue.type.medical.immunization";
    public static final String MEDICAL_INSURANCE_ISSUE_TYPE_KEY = "kuali.hold.issue.type.medical.insurance";
    public static final String INTERNATIONAL_STUDENT_ISSUE_TYPE_KEY = "kuali.hold.issue.type.international.students";
    public static final String DISCIPLINE_ISSUE_TYPE_KEY = "kuali.hold.issue.type.discipline";
    public static final String ACADEMIC_PROGRESS_ISSUE_TYPE_KEY = "kuali.hold.issue.type.academic.progress";
    public static final String RESIDENCE_ISSUE_TYPE_KEY = "kuali.hold.issue.type.residence";
    public static final String UNPAID_FEES_ISSUE_TYPE_KEY = "kuali.hold.issue.type.unpaid.fee";
    public static final String FINANCIAL_AID_ISSUE_TYPE_KEY = "kuali.hold.issue.type.financial.aid";
    public static final String UNRETURNED_EQUIPMENT_ISSUE_TYPE_KEY = "kuali.hold.issue.type.unreturned.equipment";
    public static final String ADMISSIONSS_ISSUE_TYPE_KEY = "kuali.hold.issue.type.admissions";
    public static final String IDENTITY_ISSUE_TYPE_KEY = "kuali.hold.issue.type.identity";
    public static final String PRIOR_TRANSCRIPT_ISSUE_TYPE_KEY = "kuali.hold.issue.type.prior.transcript";
    public static final String INCOMPLETE_BIO_ISSUE_TYPE_KEY = "kuali.hold.issue.type.incomplete.bio";
    public static final String RESIDENCY_ISSUE_TYPE_KEY = "kuali.hold.issue.type.residency";
    /**
     * known issue keys
     */
    public static final String ISSUE_KEY_BOOK_OVERDUE = "kuali.hold.issue.library.book.overdue";
    public static final String ISSUE_KEY_UNPAID_FINE = "kuali.hold.issue.library.unpaid.fine";
    public static final String ISSUE_KEY_UNPAID_TUITION_PRIOR_TERM = "kuali.hold.issue.financial.unpaid.tuition.prior.term";
    public static final String ISSUE_KEY_UNPAID_TUITION_CURRENT_TERM = "kuali.hold.issue.financial.unpaid.tuition.current.term";
    public static final String ISSUE_KEY_FULL_PAYMENT_REQUIRED = "kuali.hold.issue.financial.full.payment.required";
    public static final String ISSUE_KEY_TUITION_EXCHANGE = "kuali.hold.issue.financial.tuition.exchange";
    public static final String ISSUE_KEY_TUITION_SPONSOR = "kuali.hold.issue.financial.tuition.sponsor";
    public static final String ISSUE_KEY_COLLECTIONS = "kuali.hold.issue.financial.collections";
    public static final String ISSUE_KEY_BOOK_STORE_DEBT = "kuali.hold.issue.financial.book.store.debt";
    public static final String ISSUE_KEY_IMMUNIZATION = "kuali.hold.issue.medical.immunization";
    public static final String ISSUE_KEY_HEALTH_INSURANCE = "kuali.hold.issue.medical.health.insurance";
    public static final String ISSUE_KEY_VISA_CERTIFICATION = "kuali.hold.issue.international.visa.certification";
    public static final String ISSUE_KEY_VISA_VERIFICATION = "kuali.hold.issue.international.visa.verification";
    public static final String ISSUE_KEY_ENGLISH_LANGUAGE_REMEDIATION = "kuali.hold.issue.international.english.language.remediation";
    public static final String ISSUE_KEY_DISCIPLINARY_INVESTIGATION = "kuali.hold.issue.disciplinary.investigation";
    public static final String ISSUE_KEY_DISCIPLINARY_WARNING = "kuali.hold.issue.disciplinary.warning";
    public static final String ISSUE_KEY_DISCIPLINARY_SUSPENSION = "kuali.hold.issue.disciplinary.suspension";
    public static final String ISSUE_KEY_DISCIPLINARY_EXPULSION = "kuali.hold.issue.disciplinary.expulsion";
    public static final String ISSUE_KEY_ACADEMIC_PROBATION = "kuali.hold.issue.progress.academic.probation";
    public static final String ISSUE_KEY_ACADEMIC_RESTRICTION = "kuali.hold.issue.progress.academic.restriction";
    public static final String ISSUE_KEY_ACADEMICALLY_INELIGIBLE = "kuali.hold.issue.progress.academically.ineligible";
    public static final String ISSUE_KEY_HOUSING_CONTRACT_CANCELLED = "kuali.hold.issue.residence.housing.contract.cancelled";
    public static final String ISSUE_KEY_DORMATORY_DAMAGE = "kuali.hold.issue.residence.dormatory.damage";
    public static final String ISSUE_KEY_RETURNED_CHECK = "kuali.hold.issue.unpaid.fee.returned.check";
    public static final String ISSUE_KEY_TRANSCRIPT_FEE_PENDING = "kuali.hold.issue.unpaid.fee.transcript.fee.pending";
    public static final String ISSUE_KEY_PARKING_TICKET = "kuali.hold.issue.unpaid.fee.parking.ticket";
    public static final String ISSUE_KEY_OVER_AWARD = "kuali.hold.issue.financial.aid.over.award";
    public static final String ISSUE_KEY_FINANCIAL_AID_PAPERWORK_COMPLIANCE = "kuali.hold.issue.financial.aid.paperwork.compliance";
    public static final String ISSUE_KEY_LOAN_DEFAULT = "kuali.hold.issue.financial.aid.loan.default";
    public static final String ISSUE_KEY_OTHER_REFUND_HOLD = "kuali.hold.issue.financial.aid.other.refund.hold";
    public static final String ISSUE_KEY_ATHLETICS_PROPERTY_NOT_RETURNED = "kuali.hold.issue.athletics.property.not.returned";
    public static final String ISSUE_KEY_ARMY_PROPERTY_NOT_RETURNED = "kuali.hold.issue.army.property.not.returned";
    public static final String ISSUE_KEY_UNPAID_APPLICATION_FEE = "kuali.hold.issue.admissions.unpaid.application.fee";
    public static final String ISSUE_KEY_APPLICATION_INCOMPLETE = "kuali.hold.issue.admissions.application.incomplete";
    public static final String ISSUE_KEY_NOT_ADMITTED = "kuali.hold.issue.admissions.not.admitted";
    public static final String ISSUE_KEY_UNPAID_BINDING_FEE = "kuali.hold.issue.admissions.unpaid.binding.fee";
    public static final String ISSUE_KEY_CONDITIONAL_ADMIT_NOT_VERIFIED = "kuali.hold.issue.admissions.conditional.admit.not.verified";
    public static final String ISSUE_KEY_RESTRICTED_ADMISSION = "kuali.hold.issue.admissions.restricted.admission";
    public static final String ISSUE_KEY_IDENTITY_RESOLUTION = "kuali.hold.issue.identity.resolution";
    public static final String ISSUE_KEY_IDENTITY_INFORMATION = "kuali.hold.issue.identity.information";
    public static final String ISSUE_KEY_PRIOR_SCHOOL = "kuali.hold.issue.incomplete.record.prior.school";
    public static final String ISSUE_KEY_RESIDENCY_QUESTION = "kuali.hold.issue.residency.question";
    public static final String ISSUE_KEY_MANDATORY_ADVISING = "kuali.hold.issue.advising.mandatory.advising";
    public static final String ISSUE_KEY_EXCESSIVE_WITHDRAWALS_MEETING = "kuali.hold.issue.advising.excessive.withdrawals.meeting";
    public static final String ISSUE_KEY_LEAVE_OF_ABSENCE = "kuali.hold.issue.leave.of.absence";
    public static final String ISSUE_KEY_LOAN_CANCELLATION = "kuali.hold.issue.loan.loan.cancellation";
    public static final String ISSUE_KEY_LOAN_EXIT_INTERVIEW = "kuali.hold.issue.loan.loan.exit.interview";
    public static final String ISSUE_KEY_LOAN_PAPERWORK_COMPLIANCE = "kuali.hold.issue.loan.paperwork.compliance";
    public static final String ISSUE_KEY_LOAN_PAYMENTS_PAST_DUE = "kuali.hold.issue.loan.payments.past.due";
    public static final String ISSUE_KEY_ATHLETE_LOAD = "kuali.hold.issue.review.athlete.load";
    public static final String ISSUE_KEY_ATHLETE_PROGRESS = "kuali.hold.issue.review.athlete.progress";
    public static final String ISSUE_KEY_ROTC_LOAD = "kuali.hold.issue.review.rotc.load";
    public static final String ISSUE_KEY_NON_MAJOR_CREDITS = "kuali.hold.issue.review.non-major.credits";
    public static final String ISSUE_KEY_PRIVACY_REQUEST = "kuali.hold.issue.annotation.privacy.request";
    public static final String ISSUE_KEY_TRANSCRIPT_NOTATION_REQUIRED = "kuali.hold.issue.annotation.transcript.notation.required";
    public static final String ISSUE_KEY_WILL_NOT_CHANGE_RESTRICTIONS = "kuali.hold.issue.annotation.will.not.change.restrictions";
    /**
     * RESTRICTION TYPES
     */
//    public static final String REGISTERATION_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.registration";
//    public static final String ADD_DROP_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.add.drop.class";
//    public static final String REQUEST_TRANSCRIPT_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.request.transcript";
//    public static final String AWARD_DEGREE_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.award.degree";
//    public static final String RECEIVE_DIPLOMA_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.receive.diploma";
//    public static final String ADD_CHARGES_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.add.charges";
//    public static final String LIBRARY_PRIVILEGES_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.library.privileges";
//    public static final String DORM_ACCESS_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.dorm.access";
//    public static final String UPDATE_PROGRAM_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.update.program";
//    public static final String ACCESS_GRADES_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.access.grades";
//    public static final String BURSAR_REFUND_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.bursar.refund";
//    public static final String VERIFICATION_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.verification";
    /**
     * STATES FOR holds
     */
    public static final String STUDENT_HOLD_PROCESS_KEY = "kuali.hold.process.student";
    public static final String HOLD_ACTIVE_STATE_KEY = "kuali.hold.state.active";
    public static final String HOLD_RELEASED_STATE_KEY = "kuali.hold.state.released";
    public static final String HOLD_CANCELED_STATE_KEY = "kuali.hold.state.canceled";
    public static final String[] STUDENT_HOLD_PROCESS_KEYS = {HOLD_ACTIVE_STATE_KEY,
        HOLD_RELEASED_STATE_KEY,
        HOLD_CANCELED_STATE_KEY};
    /**
     * STATES FOR hold ISSUES
     */
    public static final String ISSUE_PROCESS_KEY = "kuali.hold.issue.process";
    public static final String ISSUE_ACTIVE_STATE_KEY = "kuali.hold.issue.state.active";
    public static final String ISSUE_INACTIVE_STATE_KEY = "kuali.hold.issue.state.inactive";
    public static final String[] ISSUE_PROCESS_KEYS = {ISSUE_ACTIVE_STATE_KEY,
        ISSUE_INACTIVE_STATE_KEY};
//    /**
//     * STATES FOR hold RESTRICTIONS
//     */
//    public static final String RESTRICTION_PROCESS_KEY = "kuali.hold.restriction.process";
//    public static final String RESTRICTION_ACIVE_STATE_KEY = "kuali.hold.restriction.state.active";
//    public static final String RESTRICTION_INACTIVE_STATE_KEY = "kuali.hold.restriction.state.inactive";
//    public static final String[] RESTRICTION_PROCESS_KEYS = {RESTRICTION_ACIVE_STATE_KEY,
//        RESTRICTION_INACTIVE_STATE_KEY};
}
