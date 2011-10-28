package org.kuali.student.core.statement.util;

import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTree;
import org.kuali.rice.krms.framework.engine.AgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.CompoundProposition;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.CourseSetCompletionProposition;
import org.kuali.student.common.util.krms.proposition.SingleCourseCompletionProposition;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author alubbers
 *
 */
public class PropositionBuilder {
    
    private static final String PREREQUISITE_STATEMENT_TYPE = "kuali.statement.type.course.academicReadiness.studentEligibilityPrereq";

    public static final Collection<String> TRANSLATABLE_STATEMENT_TYPES = Collections.singleton(PREREQUISITE_STATEMENT_TYPE);
    
    //private LrcService lrcService;
    
    //private ApplicationContext appContext;
    
    private static final List<String> validRequirementComponentTypes;

    static {
        String[] REQ_COM_TYPE_SEED_DATA = {
            //"kuali.reqComponent.type.course.courseset.grade.max",
            //"kuali.reqComponent.type.course.permission.org.required",
            //"kuali.reqComponent.type.course.permission.instructor.required",
            //"kuali.reqComponent.type.course.test.score.min",
            "kuali.reqComponent.type.course.courseset.completed.all",
            //"kuali.reqComponent.type.course.courseset.nof.grade.min",
            "kuali.reqComponent.type.course.completed",
            "kuali.reqComponent.type.course.courseset.completed.nof",
            //"kuali.reqComponent.type.course.courseset.credits.completed.nof",
            //"kuali.reqComponent.type.course.courseset.gpa.min",
            //"kuali.reqComponent.type.course.courseset.grade.min"
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

        results.agenda = new BasicAgenda(RulesExecutionConstants.STATEMENT_EVENT_NAME, qualifierMap, agendaTree);

        return results;
        
    }

    private Proposition buildPropositionFromComponents(StatementTreeViewInfo statementTreeView, Map<Proposition, ReqComponentInfo> reqComponentPropositionMap) throws InvalidParameterException {
        if(statementTreeView.getType().equals(PREREQUISITE_STATEMENT_TYPE)) {
            if(statementTreeView.getStatements().isEmpty()) {
                // if no sub-statements, there are only one or two req components
            	
            	Proposition proposition = translateReqComponents(statementTreeView.getReqComponents(), statementTreeView.getOperator(), reqComponentPropositionMap);
            	
                return proposition;
            }
            
            // otherwise, make a compound proposition out of the recursive result for each sub-statement
            List<Proposition> subProps = new ArrayList<Proposition>(statementTreeView.getStatements().size());
            
            for(StatementTreeViewInfo subStatement : statementTreeView.getStatements()) {
            
            	Proposition proposition = buildPropositionFromComponents(subStatement, reqComponentPropositionMap);
            	            	
                subProps.add(buildPropositionFromComponents(subStatement, reqComponentPropositionMap));
                                
                
            }
            
            CompoundProposition compoundProposition = new CompoundProposition(translateOperator(statementTreeView.getOperator()), subProps);
            
            return compoundProposition;
        }
        
        return null;
    }
    
    
    private Proposition translateReqComponents(List<ReqComponentInfo> reqComponents, StatementOperatorTypeKey operator, Map<Proposition, ReqComponentInfo> reqComponentPropositionMap) throws InvalidParameterException {
        
        ReqComponentInfo req1 = null, req2 = null;
        
        if(reqComponents == null || reqComponents.isEmpty() || reqComponents.size() > 2) {
            throw new InvalidParameterException("reqComponents parameter is invalid");
        }
        
        req1 = reqComponents.get(0);
        Proposition prop1 = buildPropositionForRequirementComponent(req1);
        
        reqComponentPropositionMap.put(prop1, req1);

        
        Proposition prop2 = null;
        if(reqComponents.size() == 2) {
            req2 = reqComponents.get(1);
            prop2 = buildPropositionForRequirementComponent(req2);
            
            reqComponentPropositionMap.put(prop2, req2);

        }
        
        if(prop2 == null) {
            return prop1;
        }
        else {
            LogicalOperator logicalOperator = translateOperator(operator);
            return new CompoundProposition(logicalOperator , Arrays.asList(prop1, prop2));
        }
    }

    private LogicalOperator translateOperator(StatementOperatorTypeKey statementOperator) throws InvalidParameterException {
        
        if(statementOperator == null) {
            return null;
        }
        
        switch(statementOperator) {
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
        
        String componentType = requirementComponent.getType();
        
        if(!validRequirementComponentTypes.contains(componentType)) {
            throw new InvalidParameterException("Requirement component type is not handled");
        }
        
        // requirement component types that should match this condition:
        //  -- kuali.reqComponent.type.course.permission.org.required
        //  -- kuali.reqComponent.type.course.permission.instructor.required
        
        if(componentType.contains("permission")) {
            return buildPermissionProposition(requirementComponent);
        }

        // requirement component types that should match this condition:
        //  -- kuali.reqComponent.type.course.courseset.credits.completed.nof
        
        if(componentType.contains("credits")) {
            return buildCreditCountProposition(requirementComponent);
        }
        
        if(componentType.equals("kuali.reqComponent.type.course.completed") ||
                componentType.equals("kuali.reqComponent.type.course.courseset.completed.nof") ||
                componentType.equals("kuali.reqComponent.type.course.courseset.completed.all")) {
            return buildCompletedCountProposition(requirementComponent);
        }
        
        
        // requirement component types that should match this condition:
        //  -- kuali.reqComponent.type.course.courseset.gpa.min
        //  -- kuali.reqComponent.type.course.courseset.grade.min
        //  -- kuali.reqComponent.type.course.courseset.grade.max
        //  -- kuali.reqComponent.type.course.courseset.nof.grade.min
        
        if(componentType.endsWith("grade.min") || componentType.endsWith("grade.max") || componentType.endsWith("gpa.min")) {
            return buildGradeComparisonProposition(requirementComponent);
        }
        
        if(componentType.equals("kuali.reqComponent.type.course.test.score.min")) {
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

    private Proposition buildCompletedCountProposition(ReqComponentInfo requirementComponent) {
        Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());
        
        Proposition result = null;
        
        if(requirementComponent.getType().equals("kuali.reqComponent.type.course.completed")) {
            // only checking one course
            String courseId = fieldMap.get("kuali.reqComponent.field.type.course.clu.id").getValue();
            
            result = new SingleCourseCompletionProposition(courseId);
            
            return result;
        }
        
        String courseSetId = fieldMap.get("kuali.reqComponent.field.type.course.cluSet.id").getValue();
        
        if(fieldMap.containsKey("kuali.reqComponent.field.type.value.positive.integer")) {
            Integer minToComplete = Integer.parseInt(fieldMap.get("kuali.reqComponent.field.type.value.positive.integer").getValue());
            result = new CourseSetCompletionProposition(courseSetId, minToComplete);
        }
        else {
            result = new CourseSetCompletionProposition(courseSetId);
        }
        
        return result;
    }

    private Map<String, ReqCompFieldInfo> buildFieldMap(List<ReqCompFieldInfo> reqCompFields) {
        Map<String, ReqCompFieldInfo> result = new HashMap<String, ReqCompFieldInfo>(reqCompFields.size());
        
        for(ReqCompFieldInfo field : reqCompFields) {
            result.put(field.getType(), field);
        }
        
        return result;
    }

    private Proposition buildPermissionProposition(ReqComponentInfo requirementComponent) {
        Proposition result = null;
        
        if(requirementComponent.getType().equals("kuali.reqComponent.type.course.permission.org.required")) {
            // if the type is permission from an org, get the org id
            Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());
            String orgId = fieldMap.get("kuali.reqComponent.field.type.org.id").getValue();

            // TODO
            // result = new OrgPermissionProposition(orgId);
        }
        else {
            // result = new InstructorPermissionProposition();
        }
        
        return result;
    }
    
    private Proposition buildCreditCountProposition(ReqComponentInfo requirementComponent) {
        Proposition result = null;
        
        Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());
        
        Integer minimumCredits = Integer.parseInt(fieldMap.get("kuali.reqComponent.field.type.value.positive.integer").getValue());
        
        String courseSetId = fieldMap.get("kuali.reqComponent.field.type.course.cluSet.id").getValue();

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
        
        String courseSetId = fieldMap.get("kuali.reqComponent.field.type.course.cluSet.id").getValue();
        
        ComparisonOperator operator = null;
        if(requirementComponent.getType().endsWith("min")) {
            operator = ComparisonOperator.GREATER_THAN_EQUAL;
        }
        else {
            operator = ComparisonOperator.LESS_THAN;
        }
        
        boolean hasNumCourses = false;
        Integer numCourses = -1;
        if(fieldMap.containsKey("kuali.reqComponent.field.type.value.positive.integer")) {
            numCourses = Integer.parseInt(fieldMap.get("kuali.reqComponent.field.type.value.positive.integer").getValue());
            hasNumCourses = true;
        }
        
        if(fieldMap.containsKey("kuali.reqComponent.field.type.gpa")) {
            Float gpa = Float.parseFloat(fieldMap.get("kuali.reqComponent.field.type.gpa").getValue());

            // TODO
            // result = new CourseSetGPAProposition(courseSetId, gpa, operator);
        }
        else if(fieldMap.containsKey("kuali.reqComponent.field.type.gradeType.id")) {
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
