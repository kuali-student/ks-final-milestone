/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Eswaranm on 2/13/14
 */

package org.kuali.student.cm.common.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.util.KRADPropertyConstants;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.lum.clu.CLUConstants;

/**
 * This class defines constants used in the Curriculum Management UI
 */
public class CurriculumManagementConstants {

    public static class ControllerRequestMappings {
        public final static String START_PROPOSAL = "/start_proposal";
        public final static String START_RETIRE_COURSE = "/start_retire";
        //KSAP uses 'course' as their mapping for Course search. so, please aware of that before changing this mapping.
        public final static String COURSE_MAINTENANCE = "/courses";
        public final static String CM_COMMENT = "/cm_comment";
        public final static String CM_DECISION = "/cm_decision";
        public final static String VIEW_COURSE = "/view_course";
        public final static String COURSE_VERSIONS = "/course_versions";
        public final static String CM_HOME = "/cmHome";
        public final static String CM_RETIRE_COURSE = "/retireCourse";

        public static enum MappingsByProposalType {
            CREATE_COURSE_MAPPING(DocumentTypeNames.CourseProposal.COURSE_CREATE, COURSE_MAINTENANCE),
            CREATE_COURSE_ADMIN_MAPPING(DocumentTypeNames.CourseProposal.COURSE_CREATE_ADMIN, COURSE_MAINTENANCE),
            MODIFY_COURSE_MAPPING(DocumentTypeNames.CourseProposal.COURSE_MODIFY, COURSE_MAINTENANCE),
            MODIFY_COURSE_ADMIN_MAPPING(DocumentTypeNames.CourseProposal.COURSE_MODIFY_ADMIN, COURSE_MAINTENANCE),
            MODIFY_COURSE_ADMIN_NOVERSION_MAPPING(DocumentTypeNames.CourseProposal.COURSE_MODIFY_ADMIN_NOVERSION, COURSE_MAINTENANCE),
            RETIRE_COURSE_MAPPING(DocumentTypeNames.CourseProposal.COURSE_RETIRE, CM_RETIRE_COURSE),
            RETIRE_COURSE_ADMIN_MAPPING(DocumentTypeNames.CourseProposal.COURSE_RETIRE_ADMIN, CM_RETIRE_COURSE);

            private String proposalTypeKey;
            private String controllerMapping;

            MappingsByProposalType(String proposalTypeKey, String controllerMapping) {
                this.proposalTypeKey = proposalTypeKey;
                this.controllerMapping = controllerMapping;
            }

            public String getProposalTypeKey() {
                return proposalTypeKey;
            }

            public String getControllerMapping() {
                return controllerMapping;
            }

            /**
             * Gets a controller mapping given a proposal type key
             *
             * @param proposalTypeKey The proposal type key given
             * @return The corresponding Request Mapping if one matches. Otherwise, null.
             */
            public static String getControllerMapping(String proposalTypeKey) {
                for (MappingsByProposalType mapping : MappingsByProposalType.values()) {
                    if (StringUtils.equalsIgnoreCase(mapping.getProposalTypeKey(), proposalTypeKey)) {
                        return mapping.getControllerMapping();
                    }
                }
                return null;
            }
        }
    }

    /**
     * URL Parameters used in controllers.
     */
    public static class UrlParams {
        public static final String COURSE_ID = "courseId";
        public static final String VERSION_INDEPENDENT_ID = "viId";
        public static final String CLU_ID = "cluId";
        public static final String COMPARE_COURSE_ID = "compareCourseId";
        public static final String VERSION_IND_ID = "VersionIndId";
        public static final String VERSION_COMMENT = "versionComment";
        public static final String IS_MODIFY_NEW_VERSION = "isModifyNewVersion";
        public static final String IS_COURSE_WITH_VERSION = "isCourseWithVersion";

        /**
         * A flag which indicates whether workflow should be used or not.
         */
        public static final String USE_CURRICULUM_REVIEW = "useCurriculumReview";

        public static final String COURSE_SUBJECT_AREA = "subjectArea";
        public static final String COURSE_NUMBER_SUFFIX = "courseNumberSuffix";
        public static final String COURSE_TITLE = "courseTitle";
    }

    public static class DocumentTypeNames {

        public final static String CM_PARENT_DOCUMENT_TYPE = "CluParentDocument";

        public final static String[] ADMIN_DOC_TYPE_NAMES =
                {CourseProposal.COURSE_CREATE_ADMIN, CourseProposal.COURSE_MODIFY_ADMIN, CourseProposal.COURSE_RETIRE_ADMIN};

        public final static String[] COURSE_CREATE_DOC_TYPE_NAMES =
                {CourseProposal.COURSE_CREATE, CourseProposal.COURSE_CREATE_ADMIN};

        public final static String[] COURSE_MODIFY_DOC_TYPE_NAMES =
                {CourseProposal.COURSE_MODIFY, CourseProposal.COURSE_MODIFY_ADMIN};

        public final static String[] COURSE_RETIRE_DOC_TYPE_NAMES =
                {CourseProposal.COURSE_RETIRE, CourseProposal.COURSE_RETIRE_ADMIN};

        public static class CourseProposal {
            public final static String COURSE_CREATE = "kuali.proposal.type.course.create";
            public final static String COURSE_MODIFY = "kuali.proposal.type.course.modify";
            public final static String COURSE_RETIRE = "kuali.proposal.type.course.retire";
            public final static String COURSE_CREATE_ADMIN = "kuali.proposal.type.course.create.admin";
            public final static String COURSE_MODIFY_ADMIN = "kuali.proposal.type.course.modify.admin";
            public final static String COURSE_MODIFY_ADMIN_NOVERSION = "kuali.proposal.type.course.modify.admin.noversion";
            public final static String COURSE_RETIRE_ADMIN = "kuali.proposal.type.course.retire.admin";
        }

        public static class ProgramProposal {
            public final static String MAJOR_DISCIPLINE_CREATE = "kuali.proposal.type.majorDiscipline.create";
            public final static String MAJOR_DISCIPLINE_MODIFY = "kuali.proposal.type.majorDiscipline.modify";
        }
    }

    public static class WorkflowActions {
        public static final String WITHDRAW = "withdraw";
        public static final String RETURN_TO_PREVIOUS = "returnToPreviousNode";
    }

    public static class ActionFlags {
        public final static String KUALI_ACTION_CAN_WITHDRAW = "canWithdraw";
        public final static String KUALI_ACTION_CAN_ADD_COLLABORATOR = "canAddCollaborator";
    }

    public static class ProposalCourseStartOptions {
        public final static String BLANK_PROPOSAL = "startBlankProposal";
        public final static String COPY_APPROVED_COURSE  = "copyApprovedCourse";
        public final static String COPY_PROPOSED_COURSE = "copyProposedCourse";
    }

    public static class ModifyCourseStartOptions {
        public final static String MODIFY_THIS_VERSION = "modifyThisVersion";
        public final static String MODIFY_WITH_A_NEW_VERSION = "modifyNewVersion";
    }

    public static class StartRetireCourseOptions {
        public final static String ADMIN_RETIRE = "startAdministrativeRetire";
        public final static String RETIRE_BY_PROPOSAL = "startRetireByProposal";
    }

    /**
     * The bean ids of the pages within the view.
     */
    public static class CourseViewIds {
        public final static String CREATE_COURSE_VIEW = "CM-Proposal-Course-Create-View";
        public final static String START_RETIRE_VIEW = "startAdminRetireCourseView";
        public final static String START_RETIRE_COURSE_VIEW = "CM-Proposal-Course-Retire-Start-View";
        public final static String RETIRE_COURSE_VIEW = "CM-Proposal-Course-Retire-View";
        public final static String VIEW_COURSE_VIEW = "ViewCourseView";
        public final static String CM_HOME_VIEW = "curriculumHomeView";
    }

    public static class CoursePageIds {
        public final static String REVIEW_COURSE_PROPOSAL_PAGE = "CM-Proposal-Review-Course-Page";
        public final static String CREATE_COURSE_PAGE = "CM-Proposal-Course-Create-Page";
        public final static String RETIRE_COURSE_PAGE = "CM-Proposal-Course-Retire-Page";
        public final static String VIEW_COURSE_PAGE = "CM-ViewCourse-View-Course-Page";
        public final static String REVIEW_RETIRE_COURSE_PROPOSAL_PAGE = "CM-Proposal-Review-RetireCourse-Page";
    }

    public static interface UserInterfaceSections {
        public String getSectionId();
    }

    /**
     * The bean ids of the tab sections.
     */
    public static enum CourseViewSections implements UserInterfaceSections {
        CREATE_COURSE_ENTRY("CM-Proposal-Course-Create-Start-Page"),
        COURSE_INFO("CM-Proposal-Course-CourseInfo-Section"),
        GOVERNANCE("CM-Proposal-Course-Governance-Section"),
        COURSE_LOGISTICS("CM-Proposal-Course-Logistics-Section"),
        LEARNING_OBJECTIVES("CM-Proposal-Course-LearningObjective-Section"),
        COURSE_REQUISITES("CM-Proposal-CourseRequisites-Section"),
        ACTIVE_DATES("CM-Proposal-Course-ActiveDates-Section"),
        FINANCIALS("CM-Proposal-Course-Financial-Section"),
        AUTHORS_AND_COLLABORATORS("CM-Proposal-Course-AuthorsAndCollaborator-Section"),
        SUPPORTING_DOCUMENTS("CM-Proposal-Course-SupportingDocument-Section"),
        REVIEW_COURSE_PROPOSAL(CoursePageIds.REVIEW_COURSE_PROPOSAL_PAGE);

        private String sectionId;

        CourseViewSections(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionId() {
            return this.sectionId;
        }

        /**
         * Gets a CourseViewSections given a sectionId (aka bean name).
         *
         * @param id The sectionId of the CourseViewSections.
         * @return The corresponding CourseViewSections if one matches. Otherwise, null.
         */
        public static CourseViewSections getSection(String id) {
            for (CourseViewSections section : CourseViewSections.values()) {
                if (StringUtils.equalsIgnoreCase(section.getSectionId(), id)) {
                    return section;
                }
            }
            return null;
        }
    }

    public static enum CourseRetireSections implements UserInterfaceSections {
        RETIRE_INFO("CM-Proposal-Course-RetireInfo-Section"),
        AUTHORS_AND_COLLABORATORS("CM-Proposal-Course-AuthorsAndCollaborator-Section"),
        SUPPORTING_DOCUMENTS("CM-Proposal-Course-SupportingDocument-Section"),
        RETIRE_REVIEW_COURSE_PROPOSAL(CoursePageIds.REVIEW_RETIRE_COURSE_PROPOSAL_PAGE);

        private String sectionId;

        CourseRetireSections(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionId() {
            return this.sectionId;
        }

        /**
         * Gets a CourseRetireSections given a sectionId (aka bean name).
         *
         * @param id The sectionId of the CourseRetireSections.
         * @return The corresponding CourseRetireSections if one matches. Otherwise, null.
         */
        public static CourseRetireSections getSection(String id) {
            for (CourseRetireSections section : CourseRetireSections.values()) {
                if (StringUtils.equalsIgnoreCase(section.getSectionId(), id)) {
                    return section;
                }
            }
            return null;
        }
    }

    // message keys
    public static class MessageKeys {
        public final static String ERROR_UNABLE_TO_GET_COLLECTION_GROUP = "unable.to.get.collection.group";
        public final static String ERROR_UNABLE_TO_GET_COLLECTION_PROPERTY = "unable.to.get.collection.property";
        public final static String ERROR_LIST_COLLECTION_IMPLEMENTATIONS_SUPPORTED_FOR_DELETE_INDEX = "list.collection.implementations.supported.for.delete.index";
        public final static String ERROR_GET_INSTRUCTOR_RETURN_MORE_THAN_ONE_RESULT = "get.instructor.return.more.than.one.result";
        public final static String ERROR_LO_CATEGORY_DUPLICATE = "error.learning.objective.category.duplicate";

        public final static String ERROR_COURSE_TITLE_REQUIRED = "error.course.title.required";
        public final static String ERROR_PROPOSAL_TITLE_REQUIRED = "error.proposal.title.required";
        public final static String ERROR_COURSE_DURATION_COUNT_REQUIRED = "error.course.duration.count.required";

        public final static String ERROR_NO_COPY_ID_PROVIDED = "error.cm.course.proposal.noCopyIdProvided";
        public final static String ERROR_COPY_PROPOSAL_FAILED = "error.cm.proposal.generalCopyError";

        public final static String ERROR_COURSE_VERSION_CODE_AND_TITLE_REQUIRED = "error.course.version.code.and.title.required";
        public final static String UNABLE_TO_ADD_LINE = "unable.to.add.line";
        public final static String UNABLE_TO_DELETE_LINE = "unable.to.delete.line";

        public final static String ERROR_CREATE_COMMENT = "error.create.comment";
        public final static String ERROR_NO_RESULTS_FOUND = "error.search.result.notfound";
        public final static String ERROR_DATA_NOT_FOUND = "error.cm.course.data.notfound";
        public final static String ERROR_DATA_MULTIPLE_MATCH_FOUND = "error.cm.course.data.multiplematch";
        public final static String ERROR_OUTCOME_CREDIT_VALUE_REQUIRED = "error.cm.course.data.outcome.creditvalue.required";
        public final static String ERROR_COURSE_LO_DESC_REQUIRED = "error.cm.course.lo.desc.required";

        public final static String ERROR_COMMENT_DELETE = "error.cm.course.comment.delete";
        public final static String SUPPORTING_DOC_MAX_SIZE_LIMIT = "supporting.document.max.size.limit";

        public final static String ERROR_SUPPORTING_DOCUMENTS_FILE_TOO_LARGE = "error.supporting.documents.file.too.large";

        public final static String ERROR_PROPOSAL_COLLABORATORS_CANNOT_ADD_SELF = "error.proposal.collaborators.adding.self";

        public final static String ERROR_KS_LEGACY_VALIDATION = "error.ks.legacy.validation";

        public final static String SUCCESS_PROPOSAL_RETURN_TO_PREVIOUS_NODE = "success.proposal.returnToPreviousNode";

        public final static String SUCCESS_PROPOSAL_WITHDRAW = "success.proposal.withdraw";
    }

    public static class OrganizationMessageKeys {
        public final static String ORG_QUERY_PARAM_OPTIONAL_LONG_NAME = "org.queryParam.orgOptionalLongName";
        public final static String ORG_QUERY_PARAM_OPTIONAL_ID = "org.queryParam.orgOptionalId";
        public final static String ORG_QUERY_PARAM_OPTIONAL_TYPE = "org.queryParam.orgOptionalType";
        public final static String ORG_QUERY_PARAM_OPTIONAL_SHORT_NAME = "org.queryParam.orgOptionalShortName";

        public final static String ORG_RESULT_COLUMN_OPTIONAL_ID = "org.resultColumn.orgOptionalId";
        public final static String ORG_RESULT_COLUMN_OPTIONAL_LONG_NAME = "org.resultColumn.orgOptionalLongName";
        public final static String ORG_RESULT_COLUMN_SHORT_NAME = "org.resultColumn.orgShortName";
        public final static String ORG_RESULT_COLUMN_ID = "org.resultColumn.orgId";
        public final static String ORG_SEARCH_GENERIC = "org.search.generic";
    }

    public static class ProposalConfirmationDialogs {

        public final static String SUBMIT_CONFIRMATION_DIALOG = "Proposal-Review-Course-ConfirmSubmit-Dialog";
        public final static String CANCEL_PROPOSAL_CONFIRMATION_DIALOG = "Proposal-Review-Course-ConfirmCancelProposal-Dialog";
        public final static String RETURN_TO_PREVIOUS_NODE_DIALOG = "Proposal-Review-Course-ConfirmReturnToPreviousNode-Dialog";
        public final static String APPROVE_CONFIRMATION_DIALOG = "Proposal-Review-Course-ConfirmApprove-Dialog";
        public final static String BLANKET_APPROVE_CONFIRMATION_DIALOG = "Proposal-Review-Course-ConfirmBlanketApprove-Dialog";
        public final static String ACKNOWLEDGE_CONFIRMATION_DIALOG = "Proposal-Review-Course-ConfirmAcknowledge-Dialog";
        public final static String WITHDRAW_CONFIRMATION_DIALOG = "Proposal-Review-Course-ConfirmWithdraw-Dialog";
        public final static String REJECT_CONFIRMATION_DIALOG = "Proposal-Review-Course-ConfirmReject-Dialog";
        public final static String RETIRE_CONFIRMATION_DIALOG = "Course-Retire-ConfirmRetire-Dialog";
    }

    //  Learning Objective Repository keys
    public final static String KUALI_LO_REPOSITORY_KEY_SINGLE_USE = "kuali.loRepository.key.singleUse";

    public final static String STATE_KEY_ACTIVE = "Active";

    public final static String L0_MSG_ERROR_NO_LO_IS_FOUND = "error.course.lo.noLoIsFound";

    public final static String CM_LO_CAT_TABLE = "CM-Proposal-Course-LoCategory-Table";

    public final static String CM_MESSAGE_ICON_IMAGE_ID = "[id=CM-IconImage]";

    public final static String SUPPORTING_DOC_MIME_TYPE = "application/octet-stream";

    public final static String REF_DOC_RELATION_PROPOSAL_TYPE = CLUConstants.REF_DOC_RELATION_PROPOSAL_TYPE;
    public final static String REF_DOC_RELATION_TYPE_KEY = "kuali.org.DocRelation.allObjectTypes";
    public final static String DEFAULT_DOC_TYPE_KEY = "documentType.doc";
    public final static String DEFAULT_DOC_CATEGORY_KEY = "documentCategory.proposal";

    public final static String FILE_SIZE_CONSTRAINT = "Maximum File Size - 7.5MB";

    public static final String CM_DATE_FORMAT = "MMMM dd, yyyy, hh:mm aa";

    public static final KSDateTimeFormatter CM_DATE_FORMATTER = new KSDateTimeFormatter(CM_DATE_FORMAT);

    public static final String DATA_OBJECT_PATH = KRADPropertyConstants.DOCUMENT + "."
            + KRADPropertyConstants.NEW_MAINTAINABLE_OBJECT + ".dataObject";

    public static final String PROPOSED_END_TERM = "proposedEndTerm";
    public static final String PROPOSED_LAST_TERM_OFFERED = "proposedLastTermOffered";
    public static final String PROPOSED_LAST_COURSE_CATALOG_YEAR = "proposedLastCourseCatalogYear";
    public static final String PROPOSED_OTHER_COMMENTS = "courseRetireOtherComments";

    public static final String FINAL_EXAM_STATUS = "finalExamStatus";
    public static final String FINAL_EXAM_RATIONALE = "finalExamRationale";
    public static final String COURSE_AUDIT = "audit";
    public static final String COURSE_PASS_FAIL = "passFail";

    public static final String COURSE_ATTRIBUTE_COURSE_AUDIT = "courseAuditAttribute";
    public static final String COURSE_ATTRIBUTE_COURSE_PASS_FAIL = "coursePassFailAttribute";


    /**
     * Following are keys used only to store data in the CourseInfo object
     */
    public static final String COURSE_ATTRIBUTE_RETIREMENT_RATIONALE = "retirementRationale";
    public static final String COURSE_ATTRIBUTE_LAST_TERM_OFFERED = "lastTermOffered";
    public static final String COURSE_ATTRIBUTE_LAST_PUBLICATION_YEAR = "lastPublicationYear";
    public static final String COURSE_ATTRIBUTE_RETIREMENT_COMMENT = "retirementComment";
    /**
     * @deprecated the below property was used in CM 2.x but removed for CM 3.0
     */
    public static final String COURSE_ATTRIBUTE_SPECIAL_CIRCUMSTANCES = "specialCircumstances";

    /**
     * This delimiter to use when rendering collections as a String.
     */
    public static final String COLLECTION_ITEMS_DELIMITER = "; ";
    public static final String COLLECTION_ITEMS_COMMA_DELIMITER = ", ";
    public static final String COLLECTION_ITEMS_NEWLINE_DELIMITER = "\n";
    public static final String COLLECTION_ITEMS_WHITESPACE_DELIMITER = " ";
    public static final String COLLECTION_ITEM_PLURAL_END = "(s)";

    public static final String YES_LABEL = "Yes";
    public static final String NO_LABEL = "No";
    /**
     * LO dynamic attribute keys.
     */
    public static class LoProperties {
        public static final String SEQUENCE = "sequence";
    }

    public enum ViewCourseType {

        COURSE_VIEW("COURSE_VIEW"),
        COURSE_COMPARE_VIEW("COURSE_COMPARE_VIEW");

        private String viewType;

        ViewCourseType(String viewType) {
            this.viewType = viewType;
        }

        public String getViewType() {
            return this.viewType;
        }
    }

    /**
     * Export related constants.
     */
    public static class Export {

        public final static String DOCUMENT_DOWNLOAD_CACHE_CONTROL = "must-revalidate, post-check=0, pre-check=0";

        /**
         * The request params used in print/export controller methods.
         */
        public static class UrlParams {
            public static String EXPORT_TYPE = "export_type";
            public static String RETURN_SAVE_HEADERS = "return_save_headers";
        }

        /**
         * File export related constants.
         */
        public static enum FileType {
            PDF("pdf", "application/pdf"),
            DOC("doc", "application/doc");

            private String fileSuffix;
            private String mimeType;

            FileType(String fileSuffix, String mimeType) {
                this.fileSuffix = fileSuffix;
                this.mimeType = mimeType;
            }

            public String getFileSuffix() {
                return fileSuffix;
            }

            public String getMimeType() {
                return mimeType;
            }
        }
    }

    public static class ProposalViewFieldLabels {

        public static class CourseInformation {

            public static String SECTION_NAME = "Course Information";

            public static String PROPOSAL_TITLE = "Proposal Title";
            public static String COURSE_TITLE    = "Course Title";
            public static String TRANSCRIPT_COURSE_TITLE = "Transcript Course Title";
            public static String SUBJECT_CODE = "Subject Code";
            public static String COURSE_NUMBER = "Course Number";

            public static String CROSS_LISTED_COURSES="Cross Listed Courses";
            public static String JOINTLY_OFFERED_COURSES = "Jointly Offered Courses";
            public static String VERSION_CODES = "Version Codes";

            public static String INSTRUCTOR = "Instructor(s)";
            public static String DESCRIPTION = "Description";
            public static String PROPOSAL_RATIONALE = "Proposal Rationale";
        }

        public static class Governance {

            public static String SECTION_NAME = "Governance";

            public static String CAMPUS_LOCATION = "Campus Locations";
            public static String CURRICULUM_OVERSIGHT = "Curriculum Oversight";
            public static String ADMINISTERING_ORGANIZATIONS = "Administering Organization";
        }

        public static class CourseLogistics {

            public static String SECTION_NAME = "Course Logistics";

            public static String TERM = "Term";
            public static String DURATION = "Duration";
            public static String ASSESSMENT_SCALE = "Assessment Scale";
            public static String AUDIT = "Audit";
            public static String PASS_FAIL_TRANSCRIPT_GRADE = "Pass Fail Transcript Grade";
            public static String FINAL_EXAM_STATUS = "Final Exam Status";
            public static String FINAL_EXAM_RATIONALE = "Final Exam Rationale";
            public static String OUTCOME = "Outcome";
            public static String TYPE = "Type";
            public static String CREDITS = "Credits";
            public static String COURSE_FORMAT = "Course Format ";
            public static String ACTIVITY = "Activity ";
            public static String ACTIVITY_TYPE = "Type";
            public static String CONTACT_HOURS = "Contact Hours";
            public static String CLASS_SIZE = "Class Size";
        }

        public static class LearningObjectives {
            public static String SECTION_NAME = "Learning Objectives";

        }

        public static class CourseRequisites {

            public static String SECTION_NAME = "Course Requisites";

        }

        public static class ActiveDates {
            public static String SECTION_NAME = "Active Dates";
            public static String START_TERM = "Start Term";
            public static String PILOT_COURSE = "Pilot Course";
            public static String END_TERM = "End Term";
        }

        public static class Financials {

            public static String SECTION_NAME = "Financials";
            public static String JUSTIFICATION_OF_FEES = "Justification of Fees";
        }

        public static class AuthorsCollaborators {

            public static String SECTION_NAME = "Authors and Collaborators";
            public static String Name = "Name";
            public static String PERMISSION = "Permissions";
            public static String ACTION_REQUEST = "Action Request";
        }

        public static class SupportingDocument {
            public static String SECTION_NAME = "Supporting Documents";
        }

        public static class RetireProposalInformation {
            public static String SECTION_NAME = "Retire Proposal Information";

            public static String PROPOSAL_TITLE = CourseInformation.PROPOSAL_TITLE;
            public static String COURSE_TITLE = CourseInformation.COURSE_TITLE;
            public static String PROPOSAL_RATIONALE = CourseInformation.PROPOSAL_RATIONALE;
            public static String START_TERM = ActiveDates.START_TERM;
            public static String END_TERM = ActiveDates.END_TERM;
            public static String LAST_TERM = "Last Term Offered";
            public static String PUBLICATION_YEAR= "Last Course Catalog Publication Year";
            public static String OTHER_COMMENTS = "Other Comments";
            public static String COURSE_NUMBER = CourseInformation.COURSE_NUMBER;
            public static String CURRICULUM_OVERSIGHT = Governance.CURRICULUM_OVERSIGHT;
            public static String CROSS_LISTED_JOINTLY_OFFERED_COURSE = "Crosslisted/Jointly Offered Courses";
        }
    }
}
