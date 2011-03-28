package org.kuali.student.krms.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.krms.api.Agenda;
import org.kuali.rice.krms.api.Asset;
import org.kuali.rice.krms.api.Proposition;
import org.kuali.rice.krms.api.Rule;
import org.kuali.rice.krms.framework.engine.AgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.ComparableTermBasedProposition;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.CompoundProposition;
import org.kuali.rice.krms.framework.engine.LogicalOperator;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.service.LrcService;
import org.kuali.student.lum.lu.service.LuService;
import org.springframework.beans.factory.annotation.Autowired;

public class StatementServiceTranslationTest {
    
    private static final String COREQUISITE_STATMENT_TYPE = "fill in with valid data for coreq";
    @Autowired
    StatementService statementService;
    
    @Autowired
    LrcService lrcService;
    
    private static final List<String> validRequirementComponentTypes;
    
    private static final Asset testScoreAsset = new Asset("testScore", "Float");
    private static final Asset permissionAsset = new Asset("permission", "Boolean");
    
    private static final Asset gpaAsset = new Asset("gpa", "Float");
    
    static {
        String[] REQ_COM_TYPE_SEED_DATA = {
            "kuali.reqComponent.type.course.courseset.grade.max",
            "kuali.reqComponent.type.course.permission.org.required",
            "kuali.reqComponent.type.course.permission.instructor.required",
            "kuali.reqComponent.type.course.test.score.min",
            "kuali.reqComponent.type.course.courseset.completed.all",
            "kuali.reqComponent.type.course.courseset.nof.grade.min",
            "kuali.reqComponent.type.course.org.credits.completed.min",
            "kuali.reqComponent.type.course.completed",
            "kuali.reqComponent.type.course.courseset.completed.nof",
            "kuali.reqComponent.type.course.courseset.credits.completed.nof",
            "kuali.reqComponent.type.course.courseset.gpa.min",
            "kuali.reqComponent.type.course.courseset.grade.min"};
        
        validRequirementComponentTypes = Collections.unmodifiableList(Arrays.asList(REQ_COM_TYPE_SEED_DATA));
    }
    
    public Agenda translateStatement(String statmentId) throws DoesNotExistException, 
            InvalidParameterException, MissingParameterException, OperationFailedException {
        
        StatementTreeViewInfo statementTreeView = statementService.getStatementTreeView(statmentId);
        
        Proposition rootProposition = buildPropositionFromComponents(statementTreeView);
        
        Rule rule = new BasicRule(rootProposition, null);
        
        AgendaTree agendaTree = new AgendaTree(Arrays.asList(rule), null, null, null);
        
        Agenda result = new BasicAgenda("statementAgenda", null, agendaTree);
        
        return result;
        
    }

    private Proposition buildPropositionFromComponents(StatementTreeViewInfo statementTreeView) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException {
        if(statementTreeView.getType().equals(COREQUISITE_STATMENT_TYPE)) {
            if(statementTreeView.getStatements().isEmpty()) {
                // if no sub-statements, there are only one or two req components
                return translateReqComponents(statementTreeView.getReqComponents(), statementTreeView.getOperator());
            }
            
            // otherwise, make a compound proposition out of the recursive result for each sub-statement
            List<Proposition> subProps = new ArrayList<Proposition>(statementTreeView.getStatements().size());
            for(StatementTreeViewInfo subStatement : statementTreeView.getStatements()) {
                subProps.add(buildPropositionFromComponents(subStatement));
            }
            
            
            return new CompoundProposition(translateOperator(statementTreeView.getOperator()), subProps);
        }
        
        return null;
    }

    private Proposition translateReqComponents(List<ReqComponentInfo> reqComponents, StatementOperatorTypeKey operator) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException {
        
        ReqComponentInfo req1 = null, req2 = null;
        
        if(reqComponents == null || reqComponents.isEmpty() || reqComponents.size() > 2) {
            throw new InvalidParameterException("reqComponents parameter is invalid");
        }
        
        req1 = reqComponents.get(0);
        Proposition prop1 = buildPropositionForRequirementComponent(req1);
        
        Proposition prop2 = null;
        if(reqComponents.size() == 2) {
            req2 = reqComponents.get(1);
            prop2 = buildPropositionForRequirementComponent(req2);
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

    private Proposition buildPropositionForRequirementComponent(ReqComponentInfo requirementComponent) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException {
        
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
        //  -- kuali.reqComponent.type.course.org.credits.completed.min
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
            List<ReqCompFieldInfo> reqCompFields = requirementComponent.getReqCompFields();
            String testScore = fieldMap.get("kuali.reqComponent.field.type.test.score").getValue();
            String testSetId = fieldMap.get("kuali.reqComponent.field.type.test.cluSet.id").getValue();
            
            // TODO 
            Proposition result = new ComparableTermBasedProposition<Float>(ComparisonOperator.GREATER_THAN_EQUAL, testScoreAsset, null);
            
            return result;
        }
        
        return null;
    }

    private Proposition buildCompletedCountProposition(ReqComponentInfo requirementComponent) {
        Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());
        
        String courseSetId = fieldMap.get("kuali.reqComponent.field.type.course.cluSet.id").getValue();
        
        Proposition result = null;
        
        if(fieldMap.containsKey("kuali.reqComponent.field.type.value.positive.integer")) {
            Integer minToComplete = Integer.parseInt(fieldMap.get("kuali.reqComponent.field.type.value.positive.integer").getValue());
        }
        
        // TODO  where do we put course set id, org id, etc
        
        return result;
    }

    private Map<String, ReqCompFieldInfo> buildFieldMap(List<ReqCompFieldInfo> reqCompFields) {
        Map<String, ReqCompFieldInfo> result = new HashMap<String, ReqCompFieldInfo>(reqCompFields.size());
        
        for(ReqCompFieldInfo field : reqCompFields) {
            result.put(field.getId(), field);
        }
        
        return result;
    }

    private Proposition buildPermissionProposition(ReqComponentInfo requirementComponent) {
        // if the type is permission from an org, get the org id
        String orgId = null;
        if(requirementComponent.getType().equals("kuali.reqComponent.type.course.permission.org.required")) {
            Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());
            orgId = fieldMap.get("kuali.reqComponent.field.type.org.id").getValue();
        }
        
        Proposition result = new ComparableTermBasedProposition<Boolean>(ComparisonOperator.EQUALS, permissionAsset, Boolean.TRUE);
        
        return result;
    }
    
    private Proposition buildCreditCountProposition(ReqComponentInfo requirementComponent) {
        Proposition result = null;
        
        Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());
        
        Integer minimumCredits = Integer.parseInt(fieldMap.get("kuali.reqComponent.field.type.value.positive.integer").getValue());
        Asset creditsAsset = null;
        
        String orgId = null;
        if(fieldMap.containsKey("kuali.reqComponent.field.type.org.id")) {
            orgId = fieldMap.get("kuali.reqComponent.field.type.org.id").getValue();
            // TODO assign creditsAsset somewhere
        }
        
        String courseSetId = null;
        if(fieldMap.containsKey("kuali.reqComponent.field.type.course.cluSet.id")) {
            courseSetId = fieldMap.get("kuali.reqComponent.field.type.course.cluSet.id").getValue();
            // TODO assign creditsAsset somewhere
        }
        
        result = new ComparableTermBasedProposition<Integer>(ComparisonOperator.GREATER_THAN_EQUAL, creditsAsset, minimumCredits);
        
        return result;
    }
    
    private Proposition buildGradeComparisonProposition(ReqComponentInfo requirementComponent) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        Proposition result = null;
        
        Map<String, ReqCompFieldInfo> fieldMap = buildFieldMap(requirementComponent.getReqCompFields());
        
        // TODO WHERE DOES THIS GO??
        String courseSetId = fieldMap.get("kuali.reqComponent.field.type.course.cluSet.id").getValue();
        
        Integer numCourses = -1;
        if(fieldMap.containsKey("kuali.reqComponent.field.type.value.positive.integer")) {
            numCourses = Integer.parseInt(fieldMap.get("kuali.reqComponent.field.type.value.positive.integer").getValue());
        }
        
        if(fieldMap.containsKey("kuali.reqComponent.field.type.gpa")) {
            Float gpa = Float.parseFloat(fieldMap.get("kuali.reqComponent.field.type.gpa").getValue());
            
            if(numCourses == -1) {
                result = new ComparableTermBasedProposition<Float>(ComparisonOperator.GREATER_THAN_EQUAL, gpaAsset, gpa);
            }
        }
        else if(fieldMap.containsKey("kuali.reqComponent.field.type.gradeType.id")) {
            String gradeType = fieldMap.get("kuali.reqComponent.field.type.gradeType.id").getValue();
            String gradeValue = fieldMap.get("kuali.reqComponent.field.type.grade.id").getValue();
            
            GradeInfo grade = lrcService.getGrade(gradeValue);
            
        }
        
        return result;
    }
    
}
