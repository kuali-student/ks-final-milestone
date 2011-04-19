package org.kuali.student.krms.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.statement.dto.NlUsageTypeInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationTypeInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class DummyStatementService implements StatementService {
    
    private Map<String, StatementTreeViewInfo> statementTreeMap;

    @Override
    public StatementTreeViewInfo getStatementTreeView(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // Build our static StatementTree objects if not already done so
        if(statementTreeMap == null) {
            buildStatementTreeMap();
        }
        
        if(statementId == null) {
            throw new MissingParameterException("statementId parameter is missing but required.");
        }
        
        if(statementTreeMap.containsKey(statementId)) {
            return statementTreeMap.get(statementId);
        }
        else {
            throw new DoesNotExistException("No StatementTreeViewInfo found for id: " + statementId);
        }
        
    }

    private void buildStatementTreeMap() {
        statementTreeMap = new HashMap<String, StatementTreeViewInfo>();
        
        StatementTreeViewInfo tree1 = buildTree1();
        statementTreeMap.put("1", tree1);
        
        StatementTreeViewInfo tree2 = buildTree2();
        statementTreeMap.put("2", tree2);
        
        StatementTreeViewInfo tree3 = buildTree3();
        statementTreeMap.put("3", tree3);
    }

    private StatementTreeViewInfo buildTree1() {
        StatementTreeViewInfo statement = new StatementTreeViewInfo();
        
        statement.setId("1");
        statement.setType("kuali.statement.type.course.academicReadiness.studentEligibilityPrereq");
        statement.setOperator(StatementOperatorTypeKey.OR);
        
        // first requirement component: Must have successfully completed all courses from (course set with id 1)
        ReqComponentInfo req1 = buildReqComponentInfo("kuali.reqComponent.type.course.courseset.completed.all");
        req1.getReqCompFields().add(buildReqCompFieldInfo("kuali.reqComponent.field.type.course.cluSet.id", "1"));
        
        // second requirement component: Must have earned a minimum GPA of 3.0 in (course set with id 2)
        ReqComponentInfo req2 = buildReqComponentInfo("kuali.reqComponent.type.course.courseset.gpa.min");
        req2.getReqCompFields().add(buildReqCompFieldInfo("kuali.reqComponent.field.type.gpa", "3.0"));
        req2.getReqCompFields().add(buildReqCompFieldInfo("kuali.reqComponent.field.type.course.cluSet.id", "2"));
        
        statement.getReqComponents().add(req1);
        statement.getReqComponents().add(req2);
        
        return statement;
    }
    
    private StatementTreeViewInfo buildTree2() {
        StatementTreeViewInfo statement = new StatementTreeViewInfo();
        
        statement.setId("2");
        statement.setType("kuali.statement.type.course.academicReadiness.studentEligibilityPrereq");
        statement.setOperator(StatementOperatorTypeKey.OR);
        
        // Sub statement 1: one requirement component, Permission of (org with id 1) required
        StatementTreeViewInfo subStatement1 = new StatementTreeViewInfo();
        subStatement1.setId("2-1");
        subStatement1.setType("kuali.statement.type.course.academicReadiness.studentEligibilityPrereq");
        subStatement1.setOperator(StatementOperatorTypeKey.OR);
        
        ReqComponentInfo subReq1 = buildReqComponentInfo("kuali.reqComponent.type.course.permission.org.required");
        subReq1.getReqCompFields().add(buildReqCompFieldInfo("kuali.reqComponent.field.type.org.id", "1"));
        
        subStatement1.getReqComponents().add(subReq1);
        
        // Sub statement 2: two requirement components: Must have completed (course with id 1) **AND** Permission of instructor required
        StatementTreeViewInfo subStatement2 = new StatementTreeViewInfo();
        subStatement2.setId("2-2");
        subStatement2.setType("kuali.statement.type.course.academicReadiness.studentEligibilityPrereq");
        subStatement2.setOperator(StatementOperatorTypeKey.AND);
        
        ReqComponentInfo subReq2 = buildReqComponentInfo("kuali.reqComponent.type.course.completed");
        subReq2.getReqCompFields().add(buildReqCompFieldInfo("kuali.reqComponent.field.type.course.clu.id", "1"));
        
        ReqComponentInfo subReq3 = buildReqComponentInfo("kuali.reqComponent.type.course.permission.instructor.required");
        
        subStatement2.getReqComponents().add(subReq2);
        subStatement2.getReqComponents().add(subReq3);
        
        statement.getStatements().add(subStatement1);
        statement.getStatements().add(subStatement2);
        
        return statement;
    }
    
    private StatementTreeViewInfo buildTree3() {
        StatementTreeViewInfo statement = new StatementTreeViewInfo();
        
        statement.setId("3");
        statement.setType("kuali.statement.type.course.academicReadiness.studentEligibilityPrereq");
        statement.setOperator(StatementOperatorTypeKey.AND);
        
        // first requirement component: Completed 5 credits from (course set with id 1)
        ReqComponentInfo req1 = buildReqComponentInfo("kuali.reqComponent.type.course.courseset.credits.completed.nof");
        req1.getReqCompFields().add(buildReqCompFieldInfo("kuali.reqComponent.field.type.value.positive.integer", "5"));
        req1.getReqCompFields().add(buildReqCompFieldInfo("kuali.reqComponent.field.type.course.cluSet.id", "1"));
        
        // second requirement component: Must have earned a minimum score of 80 on (test set with id 1)
        ReqComponentInfo req2 = buildReqComponentInfo("kuali.reqComponent.type.course.test.score.min");
        req2.getReqCompFields().add(buildReqCompFieldInfo("kuali.reqComponent.field.type.test.cluSet.id", "1"));
        req2.getReqCompFields().add(buildReqCompFieldInfo("kuali.reqComponent.field.type.test.score", "80"));
        
        statement.getReqComponents().add(req1);
        statement.getReqComponents().add(req2);
        
        return statement;
    }
    

    private ReqCompFieldInfo buildReqCompFieldInfo(String type, String value) {
        ReqCompFieldInfo result = new ReqCompFieldInfo();
        result.setType(type);
        result.setValue(value);
        
        return result;
    }

    private ReqComponentInfo buildReqComponentInfo(String type) {
        ReqComponentInfo result = new ReqComponentInfo();
        result.setType(type);
        
        return result;
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String arg0) {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getObjectTypes() {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchTypeInfo getSearchType(String arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchResult search(SearchRequest arg0) throws MissingParameterException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getRefObjectTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getRefObjectSubTypes(String objectTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<NlUsageTypeInfo> getNlUsageTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public NlUsageTypeInfo getNlUsageType(String nlUsageTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RefStatementRelationInfo createRefStatementRelation(RefStatementRelationInfo refStatementRelationInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RefStatementRelationInfo updateRefStatementRelation(String refStatementRelationId, RefStatementRelationInfo refStatementRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteRefStatementRelation(String refStatementRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateRefStatementRelation(String validationType, RefStatementRelationInfo refStatementRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RefStatementRelationInfo getRefStatementRelation(String refStatementRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<RefStatementRelationInfo> getRefStatementRelationsByRef(String refObjectTypeKey, String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<RefStatementRelationInfo> getRefStatementRelationsByStatement(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getNaturalLanguageForStatement(String statementId, String nlUsageTypeKey, String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getNaturalLanguageForRefStatementRelation(String refStatementRelationId, String nlUsageTypeKey, String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getNaturalLanguageForReqComponent(String reqComponentId, String nlUsageTypeKey, String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateReqComponent(String validationType, ReqComponentInfo reqComponentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateStatement(String validationType, StatementInfo statementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatementInfo getStatement(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StatementInfo> getStatementsByType(String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public ReqComponentInfo getReqComponent(String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ReqComponentInfo> getReqComponentsByType(String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StatementInfo> getStatementsUsingReqComponent(String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StatementInfo> getStatementsUsingStatement(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public ReqComponentInfo createReqComponent(String reqComponentType, ReqComponentInfo reqComponentInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteReqComponent(String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatementInfo createStatement(String statementType, StatementInfo statementInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatementInfo updateStatement(String statementId, StatementInfo statementInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteStatement(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatementTypeInfo getStatementType(String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StatementTypeInfo> getStatementTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getStatementTypesForStatementType(String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ReqComponentTypeInfo> getReqComponentTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public ReqComponentTypeInfo getReqComponentType(String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<RefStatementRelationTypeInfo> getRefStatementRelationTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RefStatementRelationTypeInfo getRefStatementRelationType(String refStatementRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getStatementTypesForRefStatementRelationType(String refStatementRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getRefStatementRelationTypesForRefObjectSubType(String refSubTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public ReqComponentInfo updateReqComponent(String reqComponentId, ReqComponentInfo reqComponentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatementTreeViewInfo getStatementTreeViewForNlUsageType(String statementId, String nlUsageTypeKey, String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatementTreeViewInfo updateStatementTreeView(String statementId, StatementTreeViewInfo statementTreeViewInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatementTreeViewInfo createStatementTreeView(StatementTreeViewInfo statementTreeViewInfo) throws CircularReferenceException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteStatementTreeView(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
