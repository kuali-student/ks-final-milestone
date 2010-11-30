package org.kuali.student.lum.program.client;

import java.util.Arrays;
import java.util.List;


/**
 * @author Igor
 */
public class ProgramConstants {
    public static final String PROGRAM_MODEL_ID = "programModelId";
    public static final String CREDENTIAL_PROGRAM_ID = "credentialProgramId";
    public static final String CREDENTIAL_PROGRAM_TYPE = "credentialProgramType";
    public static final String ID = "id";
    public static final String STATE = "state";
    public static final String TYPE = "type";
    public static final String MAJOR_LU_TYPE_ID = "kuali.lu.type.MajorDiscipline";
    public static final String CORE_LU_TYPE_ID = "kuali.lu.type.CoreProgram";
    public static final String CRED_LU_TYPE_ID = "kuali.lu.type.CoreProgram";
    public static final String VARIATION_TYPE_KEY = "kuali.lu.type.Variation";
    public static final String MAJOR_REFERENCE_TYPE_ID = "referenceType.clu";
    public static final String RUNTIME_DATA = "_runtimeData";
    public static final String ID_TRANSLATION = "id-translation";
    public static final String HAS_CORE_PROGRAM = "kuali.lu.lu.relation.type.hasCoreProgram";

    //Identifying Details
    public static final String CODE = "code";
    public static final String PROGRAM_LEVEL = "programLevel";
    public static final String CREDENTIAL_PROGRAM = "credentialProgramInfo";
    public static final String CREDENTIAL_PROGRAMS = "credentialPrograms";
    public static final String PROGRAM_CLASSIFICATION = "universityClassification";
    public static final String DEGREE_TYPE = "resultOptions";

    //Dates
    public static final String START_TERM = "startTerm";
    public static final String END_INSTITUTIONAL_ADMIT_TERM = "endInstAdmitTerm";
    public static final String END_PROGRAM_ENTRY_TERM = "endProgramEntryTerm";
    public static final String END_PROGRAM_ENROLL_TERM = "endTerm";
    public static final String PROGRAM_APPROVAL_DATE = "approvalDate";

    //Program Title
    public static final String LONG_TITLE = "longTitle";
    public static final String SHORT_TITLE = "shortTitle";
    public static final String TRANSCRIPT = "transcriptTitle";
    public static final String DIPLOMA = "diplomaTitle";

    //Other Information
    public static final String LOCATION = "campusLocations";
    public static final String ACCREDITING_AGENCY = "accreditingAgencies";
    public static final String CIP_2000 = "cip2000Code";
    public static final String CIP_2010 = "cip2010Code";
    public static final String HEGIS_CODE = "hegisCode";
    public static final String INSTITUTION = "institution";
    public static final String ORG_ID = "orgId";

    //Managing Bodies
    public static final String CURRICULUM_OVERSIGHT_DIVISION = "divisionsContentOwner";
    public static final String CURRICULUM_OVERSIGHT_UNIT = "unitsContentOwner";
    public static final String STUDENT_OVERSIGHT_DIVISION = "divisionsStudentOversight";
    public static final String STUDENT_OVERSIGHT_UNIT = "unitsStudentOversight";
    public static final String DEPLOYMENT_DIVISION = "divisionsDeployment";
    public static final String DEPLOYMENT_UNIT = "unitsDeployment";
    public static final String FINANCIAL_RESOURCES_DIVISION = "divisionsFinancialResources";
    public static final String FINANCIAL_RESOURCES_UNIT = "unitsFinancialResources";
    public static final String FINANCIAL_CONTROL_DIVISION = "divisionsFinancialControl";
    public static final String FINANCIAL_CONTROL_UNIT = "unitsFinancialControl";

    //Program Description and Catalog Details
    public static final String DESCRIPTION = "descr";
    public static final String CATALOG_DESCRIPTION = "catalogDescr";
    public static final String PLAIN_TEXT = "plain";
    public static final String CORE_FACULTY_MEMBERS = "coreFaculties";
    public static final String PUBLICATION_TARGETS = "catalogPublicationTargets";
    public static final String FULL_PART_TIME = "intensity";
    public static final String DURATION = "stdDuration";
    public static final String MORE_INFORMATION = "referenceURL";

    //Learning Objectives
    public static final String LEARNING_OBJECTIVES = "learningObjectives";

    //Program Specializations
    public static final String VARIATIONS = "variations";
    public static final String PROGRAM_REQUIREMENTS = "programRequirements";
    public static final String VARIATION_ID = "variationId";

    //Dynamic attributes
    public static final String IS_VARIATION_REQUIRED = "isVariationRequired";
    public static final String LAST_REVIEW_DATE = "lastReviewDate";
    public static final String DURATION_NOTES = "durationNotes";

    //History
    public static final String SCHEDULED_REVIEW_DATE = "nextReviewPeriod";
    public static final String LAST_UPDATED_DATE = "metaInfo/updateTime";

    //Versioning
    public static final String VERSION = "versionInfo/sequenceNumber";
    public static final String VERSION_IND_ID = "versionInfo/versionIndId";
    public static final String VERSION_FROM_ID = "versionInfo/versionedFromId";
    public static final String VERSION_SEQUENCE_NUMBER = "versionInfo/sequenceNumber";

    public static final String PREV_END_PROGRAM_ENTRY_TERM = "previousVersionInfo/endProgramEntryTerm";
    public static final String PREV_END_PROGRAM_ENROLL_TERM = "previousVersionInfo/endTerm";
    public static final String PREV_STATE = "previousVersionInfo/state";

    //Compound constants
    public static final String CREDENTIAL_PROGRAM_INSTITUTION_ID = CREDENTIAL_PROGRAM + "/" + INSTITUTION + "/" + RUNTIME_DATA + "/" + ORG_ID + "/" + ID_TRANSLATION;
    public static final String CREDENTIAL_PROGRAM_TYPE_NAME = CREDENTIAL_PROGRAM + "/" + RUNTIME_DATA + "/" + CREDENTIAL_PROGRAM_TYPE + "/" + ID_TRANSLATION;
    public static final String CREDENTIAL_PROGRAM_LEVEL = CREDENTIAL_PROGRAM + "/" + PROGRAM_LEVEL;
    public static final String ACCREDITING_AGENCY_ORG_ID_TRANSLATION = RUNTIME_DATA + "/" + ORG_ID + "/" + ID_TRANSLATION;

    public static final List<String> RICH_TEXT_KEYS = Arrays.asList("/" + DESCRIPTION, "/" + CATALOG_DESCRIPTION);
}
