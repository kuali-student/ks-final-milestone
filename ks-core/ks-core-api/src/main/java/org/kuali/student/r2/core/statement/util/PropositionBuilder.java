package org.kuali.student.r2.core.statement.util;

import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTree;
import org.kuali.rice.krms.framework.engine.AgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.expression.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.CompoundProposition;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.student.common.util.krms.proposition.CourseSetCompletionProposition;
import org.kuali.student.common.util.krms.proposition.CourseSetEnrollmentProposition;
import org.kuali.student.common.util.krms.proposition.MaxCourseCompletionProposition;
import org.kuali.student.common.util.krms.proposition.SingleCourseCompletionProposition;
import org.kuali.student.common.util.krms.proposition.SingleCourseEnrollmentProposition;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.core.constants.StatementServiceConstants;
import org.kuali.student.r2.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.core.statement.dto.StatementOperator;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alubbers
 */
public class PropositionBuilder {

    public static final Collection<String> TRANSLATABLE_STATEMENT_TYPES = Collections.singleton(StatementServiceConstants.PREREQUISITE_STATEMENT_TYPE);

    //private LrcService lrcService;

    //private ApplicationContext appContext;

    private static final List<String> validRequirementComponentTypes;

    static {
        String[] REQ_COM_TYPE_SEED_DATA = {
                StatementServiceConstants.ENROLLED_COURSE_REQ_COM_TYPE,
                StatementServiceConstants.N_OF_REQUIRED_COURSES_ENROLLED_REQ_COM_TYPE,
                StatementServiceConstants.ALL_OF_REQUIRED_COURSES_ENROLLED_REQ_COM_TYPE,
                //"kuali.reqComponent.type.course.courseset.grade.max",
                //"kuali.reqComponent.type.course.permission.org.required",
                //"kuali.reqComponent.type.course.permission.instructor.required",
                //"kuali.reqComponent.type.course.test.score.min",
                //"kuali.reqComponent.type.course.courseset.credits.completed.nof",
                //"kuali.reqComponent.type.course.courseset.gpa.min",
                //"kuali.reqComponent.type.course.courseset.grade.min"
                //"kuali.reqComponent.type.course.courseset.nof.grade.min",
                StatementServiceConstants.ALL_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE,
                StatementServiceConstants.COMPLETED_COURSE_REQ_COM_TYPE,
                StatementServiceConstants.N_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE,
                StatementServiceConstants.MAX_N_OF_COURSES_COMPLETED_REQ_COM_TYPE,
                StatementServiceConstants.NONE_OF_COURSES_COMPLETED_REQ_COM_TYPE,
                StatementServiceConstants.NOT_COMPLETED_COURSE_REQ_COM_TYPE
        };

        validRequirementComponentTypes = Collections.unmodifiableList(Arrays.asList(REQ_COM_TYPE_SEED_DATA));
    }

    public static class TranslationResults {
        public Agenda agenda;

        public Map<Proposition, ReqComponentInfo> reqComponentPropositionMap = new HashMap<Proposition, ReqComponentInfo>();
    }

    public TranslationResults translateStatement(StatementTreeViewInfo statementTreeView, Map<String, String> qualifierMap) throws InvalidParameterException {

        TranslationResults results = new TranslationResults();

        Proposition rootProposition = buildPropositionFromComponents(statementTreeView, results.reqComponentPropositionMap);

        Rule rule = new BasicRule(rootProposition, null);

        List<AgendaTreeEntry> treeEntries = new ArrayList<AgendaTreeEntry>();
        treeEntries.add(new BasicAgendaTreeEntry(rule));

        AgendaTree agendaTree = new BasicAgendaTree(treeEntries);

        if (qualifierMap == null) {
            qualifierMap = Collections.emptyMap();
        }

        results.agenda = new BasicAgenda(qualifierMap, agendaTree);

        return results;

    }

    private Proposition buildPropositionFromComponents(StatementTreeViewInfo statementTreeView, Map<Proposition, ReqComponentInfo> reqComponentPropositionMap) throws InvalidParameterException {
        if (statementTreeView.getTypeKey().equals(StatementServiceConstants.PREREQUISITE_STATEMENT_TYPE)) {
            if (statementTreeView.getStatements().isEmpty()) {
                // if no sub-statements, there are only one or two req components

                Proposition proposition = translateReqComponents(statementTreeView.getReqComponents(), statementTreeView.getOperator(), reqComponentPropositionMap);

                return proposition;
            }

            // otherwise, make a compound proposition out of the recursive result for each sub-statement
            List<Proposition> subProps = new ArrayList<Proposition>(statementTreeView.getStatements().size());

            for (StatementTreeViewInfo subStatement : statementTreeView.getStatements()) {

                Proposition proposition = buildPropositionFromComponents(subStatement, reqComponentPropositionMap);

                subProps.add(buildPropositionFromComponents(subStatement, reqComponentPropositionMap));


            }

            CompoundProposition compoundProposition = new CompoundProposition(translateOperator(statementTreeView.getOperator()), subProps);

            return compoundProposition;
        }

        return null;
    }


    private Proposition translateReqComponents(List<ReqComponentInfo> reqComponents, StatementOperator operator, Map<Proposition, ReqComponentInfo> reqComponentPropositionMap) throws InvalidParameterException {

        ReqComponentInfo req1 = null, req2 = null;

        if (reqComponents == null || reqComponents.isEmpty() || reqComponents.size() > 2) {
            throw new InvalidParameterException("reqComponents parameter is invalid");
        }

        req1 = reqComponents.get(0);
        Proposition prop1 = buildPropositionForRequirementComponent(req1);

        reqComponentPropositionMap.put(prop1, req1);


        Proposition prop2 = null;
        if (reqComponents.size() == 2) {
            req2 = reqComponents.get(1);
            prop2 = buildPropositionForRequirementComponent(req2);

            reqComponentPropositionMap.put(prop2, req2);

        }

        if (prop2 == null) {
            return prop1;
        } else {
            LogicalOperator logicalOperator = translateOperator(operator);
            return new CompoundProposition(logicalOperator, Arrays.asList(prop1, prop2));
        }
    }

    private LogicalOperator translateOperator(StatementOperator statementOperator) throws InvalidParameterException {

        if (statementOperator == null) {
            return null;
        }

        switch (statementOperator) {
            case AND: {
                return LogicalOperator.AND;
            }
            case OR: {
                return LogicalOperator.OR;
            }
            default: {
                throw new InvalidParameterException("StatementOperator is an unrecognized value: " + statementOperator.toString());
            }
        }

    }

    private Proposition buildPropositionForRequirementComponent(ReqComponentInfo requirementComponent) throws InvalidParameterException {

        String componentType = requirementComponent.getTypeKey();

        if (!validRequirementComponentTypes.contains(componentType)) {
            throw new InvalidParameterException("Requirement component type is not handled");
        }

        // requirement component types that should match this condition:
        //  -- kuali.reqComponent.type.course.permission.org.required
        //  -- kuali.reqComponent.type.course.permission.instructor.required

        if (componentType.contains("permission")) {
            return buildPermissionProposition(requirementComponent);
        }

        // requirement component types that should match this condition:
        //  -- kuali.reqComponent.type.course.courseset.credits.completed.nof

        if (componentType.contains("credits")) {
            return buildCreditCountProposition(requirementComponent);
        }

        if (componentType.equals(StatementServiceConstants.ENROLLED_COURSE_REQ_COM_TYPE) ||
                componentType.equals(StatementServiceConstants.N_OF_REQUIRED_COURSES_ENROLLED_REQ_COM_TYPE) ||
                componentType.equals(StatementServiceConstants.ALL_OF_REQUIRED_COURSES_ENROLLED_REQ_COM_TYPE)) {
            return buildEnrolledCountProposition(requirementComponent);
        }

        if (componentType.equals(StatementServiceConstants.COMPLETED_COURSE_REQ_COM_TYPE) ||
                componentType.equals(StatementServiceConstants.N_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE) ||
                componentType.equals(StatementServiceConstants.ALL_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE)) {
            return buildCompletedCountProposition(requirementComponent);
        }

        if (componentType.equals(StatementServiceConstants.NOT_COMPLETED_COURSE_REQ_COM_TYPE) ||
                componentType.equals(StatementServiceConstants.MAX_N_OF_COURSES_COMPLETED_REQ_COM_TYPE) ||
                componentType.equals(StatementServiceConstants.NONE_OF_COURSES_COMPLETED_REQ_COM_TYPE)) {
            return buildMaxCompletedCountProposition(requirementComponent);
        }


        // requirement component types that should match this condition:
        //  -- kuali.reqComponent.type.course.courseset.gpa.min
        //  -- kuali.reqComponent.type.course.courseset.grade.min
        //  -- kuali.reqComponent.type.course.courseset.grade.max
        //  -- kuali.reqComponent.type.course.courseset.nof.grade.min

        if (componentType.endsWith("grade.min") || componentType.endsWith("grade.max") || componentType.endsWith("gpa.min")) {
            return buildGradeComparisonProposition(requirementComponent);
        }

        if (componentType.equals("kuali.reqComponent.type.course.test.score.min")) {
            // build the test score proposition here

            // get the expected test score and the tests
            Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());
            Float testScore = Float.parseFloat(fieldMap.get("kuali.reqComponent.field.type.test.score").getValue());
            String testSetId = fieldMap.get("kuali.reqComponent.field.type.test.cluSet.id").getValue();

            // TODO
            //Proposition result = new TestScoreCompareProposition(ComparisonOperator.GREATER_THAN_EQUAL, testSetId, testScore);

            //return result;
        }

        return null;
    }

    private Proposition buildMaxCompletedCountProposition(ReqComponentInfo requirementComponent) {
        Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());

        Proposition result = null;
        if (requirementComponent.getTypeKey().equals(StatementServiceConstants.NOT_COMPLETED_COURSE_REQ_COM_TYPE)) {
            // Single course to check for non-completion
            String courseId = fieldMap.get(StatementServiceConstants.COURSE_ID_REQ_COM_FIELD_TYPE).getValue();

            result = new MaxCourseCompletionProposition(courseId);

            return result;
        }

        // for the other two types of max completion propositions handled here, a course set field will be provided
        String courseSetId = fieldMap.get(StatementServiceConstants.COURSE_SET_ID_REQ_COM_FIELD_TYPE).getValue();

        if (fieldMap.containsKey(StatementServiceConstants.INTEGER_REQ_COM_FIELD_TYPE)) {
            // if the field map contains an integer use that value for the maximum allowed courses to be completed in the set
            // otherwise, the maximum is 0 (no courses in the set can be completed)

            Integer maxToComplete = Integer.parseInt(fieldMap.get(StatementServiceConstants.INTEGER_REQ_COM_FIELD_TYPE).getValue());
            result = new MaxCourseCompletionProposition(courseSetId, maxToComplete);
        } else {
            result = new MaxCourseCompletionProposition(courseSetId, 0);
        }

        return result;
    }

    private Proposition buildEnrolledCountProposition(ReqComponentInfo requirementComponent) {
        Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());

        Proposition result = null;
        if (requirementComponent.getTypeKey().equals(StatementServiceConstants.ENROLLED_COURSE_REQ_COM_TYPE)) {
            // only checking one course
            String courseId = fieldMap.get(StatementServiceConstants.COURSE_ID_REQ_COM_FIELD_TYPE).getValue();

            result = new SingleCourseEnrollmentProposition(courseId);

            return result;
        }

        // for the other two types of enrollment propositions handled here, a course set field will be provided
        String courseSetId = fieldMap.get(StatementServiceConstants.COURSE_SET_ID_REQ_COM_FIELD_TYPE).getValue();

        if (fieldMap.containsKey(StatementServiceConstants.INTEGER_REQ_COM_FIELD_TYPE)) {
            // if the field map contains an integer use that value for the minimum number of courses to be enrolled in the set
            // otherwise, the minimum is not set, and the proposition checks for all courses for enrollment

            Integer minToComplete = Integer.parseInt(fieldMap.get(StatementServiceConstants.INTEGER_REQ_COM_FIELD_TYPE).getValue());
            result = new CourseSetEnrollmentProposition(courseSetId, minToComplete);
        } else {
            result = new CourseSetEnrollmentProposition(courseSetId);
        }

        return result;
    }

    private Proposition buildCompletedCountProposition(ReqComponentInfo requirementComponent) {
        Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());

        Proposition result = null;

        if (requirementComponent.getTypeKey().equals(StatementServiceConstants.COMPLETED_COURSE_REQ_COM_TYPE)) {
            // only checking one course
            String courseId = fieldMap.get(StatementServiceConstants.COURSE_ID_REQ_COM_FIELD_TYPE).getValue();

            result = new SingleCourseCompletionProposition(courseId);

            return result;
        }

        // for the other two types of completion propositions handled here, a course set field will be provided
        String courseSetId = fieldMap.get(StatementServiceConstants.COURSE_SET_ID_REQ_COM_FIELD_TYPE).getValue();

        if (fieldMap.containsKey(StatementServiceConstants.INTEGER_REQ_COM_FIELD_TYPE)) {
            // if the field map contains an integer use that value for the minimum number of courses to be completed in the set
            // otherwise, the minimum is not set, and the proposition checks for all courses for completion

            Integer minToComplete = Integer.parseInt(fieldMap.get(StatementServiceConstants.INTEGER_REQ_COM_FIELD_TYPE).getValue());
            result = new CourseSetCompletionProposition(courseSetId, minToComplete);
        } else {
            result = new CourseSetCompletionProposition(courseSetId);
        }

        return result;
    }

    private Map<String, ReqCompFieldInfo> buildFieldMap(List<ReqCompFieldInfo> reqCompFields) {
        Map<String, ReqCompFieldInfo> result = new HashMap<String, ReqCompFieldInfo>(reqCompFields.size());

        for (ReqCompFieldInfo field : reqCompFields) {
            result.put(field.getTypeKey(), field);
        }

        return result;
    }

    private Proposition buildPermissionProposition(ReqComponentInfo requirementComponent) {
        Proposition result = null;

        if (requirementComponent.getTypeKey().equals("kuali.reqComponent.type.course.permission.org.required")) {
            // if the type is permission from an org, get the org id
            Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());
            String orgId = fieldMap.get("kuali.reqComponent.field.type.org.id").getValue();

            // TODO
            // result = new OrgPermissionProposition(orgId);
        } else {
            // result = new InstructorPermissionProposition();
        }

        return result;
    }

    private Proposition buildCreditCountProposition(ReqComponentInfo requirementComponent) {
        Proposition result = null;

        Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());

        Integer minimumCredits = Integer.parseInt(fieldMap.get(StatementServiceConstants.INTEGER_REQ_COM_FIELD_TYPE).getValue());

        String courseSetId = fieldMap.get(StatementServiceConstants.COURSE_SET_ID_REQ_COM_FIELD_TYPE).getValue();

        // TODO
        // result = new CourseSetCreditsProposition(courseSetId, ComparisonOperator.GREATER_THAN_EQUAL, minimumCredits);

        return result;
    }

    //  -- kuali.reqComponent.type.course.courseset.gpa.min
    //  -- kuali.reqComponent.type.course.courseset.grade.min
    //  -- kuali.reqComponent.type.course.courseset.grade.max
    //  -- kuali.reqComponent.type.course.courseset.nof.grade.min

    private Proposition buildGradeComparisonProposition(ReqComponentInfo requirementComponent) {
        Proposition result = null;

        Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());

        String courseSetId = fieldMap.get(StatementServiceConstants.COURSE_SET_ID_REQ_COM_FIELD_TYPE).getValue();

        ComparisonOperator operator = null;
        if (requirementComponent.getTypeKey().endsWith("min")) {
            operator = ComparisonOperator.GREATER_THAN_EQUAL;
        } else {
            operator = ComparisonOperator.LESS_THAN;
        }

        boolean hasNumCourses = false;
        Integer numCourses = -1;
        if (fieldMap.containsKey(StatementServiceConstants.INTEGER_REQ_COM_FIELD_TYPE)) {
            numCourses = Integer.parseInt(fieldMap.get(StatementServiceConstants.INTEGER_REQ_COM_FIELD_TYPE).getValue());
            hasNumCourses = true;
        }

        if (fieldMap.containsKey("kuali.reqComponent.field.type.gpa")) {
            Float gpa = Float.parseFloat(fieldMap.get("kuali.reqComponent.field.type.gpa").getValue());

            // TODO
            // result = new CourseSetGPAProposition(courseSetId, gpa, operator);
        } else if (fieldMap.containsKey("kuali.reqComponent.field.type.gradeType.id")) {
            String gradeType = fieldMap.get("kuali.reqComponent.field.type.gradeType.id").getValue();
            String gradeValue = fieldMap.get("kuali.reqComponent.field.type.grade.id").getValue();

            // TODO
            /*
            GradeInfo gradeInfo = null;
            
            gradeInfo = lrcService.getGrade(gradeValue);
            
            if(hasNumCourses) {
                result = new CourseSetGradeProposition(courseSetId, gradeInfo, numCourses, operator);
            }
            else {
                result = new CourseSetGradeProposition(courseSetId, gradeInfo, operator);
            }*/
        }

        return result;
    }

}
