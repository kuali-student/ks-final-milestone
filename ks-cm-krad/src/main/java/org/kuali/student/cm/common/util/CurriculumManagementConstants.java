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

/**
 * This class defines constants used in the Curriculum Management UI
 */
public class CurriculumManagementConstants {

    public static class ControllerRequestMappings {
        public final static String CREATE_COURSE_INITIAL = "/createcourse_initial";
        public final static String CREATE_COURSE = "/courses";
    }

    public static class DocumentTypeNames {

        public final static String[] ADMIN_DOC_TYPE_NAMES =
                {CourseProposal.COURSE_CREATE_ADMIN, CourseProposal.COURSE_MODIFY_ADMIN};

        public static class CourseProposal {
            public final static String COURSE_CREATE = "kuali.proposal.type.course.create";
            public final static String COURSE_MODIFY = "kuali.proposal.type.course.modify";
            public final static String COURSE_RETIRE = "kuali.proposal.type.course.retire";
            public final static String COURSE_CREATE_ADMIN = "kuali.proposal.type.course.create.admin";
            public final static String COURSE_MODIFY_ADMIN = "kuali.proposal.type.course.modify.admin";
        }

        public static class ProgramProposal {
            public final static String MAJOR_DISCIPLINE_CREATE = "kuali.proposal.type.majorDiscipline.create";
            public final static String MAJOR_DISCIPLINE_MODIFY = "kuali.proposal.type.majorDiscipline.modify";
        }
    }

    /**
     * The bean ids of the pages within the view.
     */
    public static class CourseViewPageIds {
        public final static String REVIEW_PROPOSAL = "KS-CourseView-ReviewProposalPage";
        public final static String CREATE_COURSE = "KS-CourseView-CoursePage";
    }

    /**
     * The bean ids of the tab sections.
     */
    public enum CourseViewSections {
        CREATE_COURSE_ENTRY("KS-CourseView-createCourseInitialPage"),
        COURSE_INFO("KS-CourseView-CourseInfo-Section"),
        GOVERNANCE("KS-CourseView-Governance-Section"),
        COURSE_LOGISTICS("KS-CourseView-Logistics-Section"),
        LEARNING_OBJECTIVES("KS-CourseView-LearningObjectives-Section"),
        COURSE_REQUISITES("KS-CourseView-CourseRequisites-Section"),
        ACTIVE_DATES("KS-CourseView-ActiveDates-Section"),
        FINANCIALS("KS-CourseView-Financials-Section"),
        AUTHORS_AND_COLLABORATORS("KS-CourseView-AuthorsAndCollaborators-Section"),
        SUPPORTING_DOCUMENTS("KS-CourseView-SupportingDocuments-Section"),
        REVIEW_PROPOSAL("KS-CourseView-ReviewProposalPage");

        private String sectionId;

        CourseViewSections(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionId() {
            return this.sectionId;
        }

        /**
         * Gets a CourseViewSections given a sectionId (aka bean name).
         * @param id The sectionId of the CourseViewSections.
         * @return The corresponding CourseViewSections if one matches. Otherwise, null.
         */
        public static CourseViewSections getSection(String id) {
            for(CourseViewSections section : CourseViewSections.values()) {
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
        public final static String ERROR_COURSE_VERSION_CODE_AND_TITLE_REQUIRED = "error.course.version.code.and.title.required";

        public final static String UNABLE_TO_ADD_LINE = "unable.to.add.line";
        public final static String UNABLE_TO_DELETE_LINE = "unable.to.delete.line";
        public final static String ERROR_CREATE_COMMENT = "error.create.comment";

        public final static String ERROR_NO_RESULTS_FOUND = "error.search.result.notfound";
        public final static String ERROR_DATA_NOT_FOUND = "error.cm.course.data.notfound";
        public final static String ERROR_DATA_MULTIPLE_MATCH_FOUND = "error.cm.course.data.multiplematch";

    }

    public static class OrganizationMessageKeys{
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

    //  Learning Objective Repository keys
    public final static String KUALI_LO_REPOSITORY_KEY_SINGLE_USE = "kuali.loRepository.key.singleUse";

    public final static String KS_LO_CAT_TABLE = "KS-LoCatTable";

    /**
     * This delimiter to use when rendering collections as a String.
     */
    public static String COLLECTION_ITEMS_DELIMITER = "; ";
    public static String COLLECTION_ITEMS_NEWLINE_DELIMITER = "\n";
    public static String COLLECTION_ITEMS_WHITESPACE_DELIMITER = " ";
    public static String COLLECTION_ITEM_PLURAL_END = "(s)";
}
