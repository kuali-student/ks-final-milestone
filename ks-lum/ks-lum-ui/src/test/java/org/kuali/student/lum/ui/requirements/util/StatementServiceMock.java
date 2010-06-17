/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.ui.requirements.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.statement.dto.NlUsageTypeInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationTypeInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
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
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class StatementServiceMock implements StatementService {

    private Map<String, Object> map = new HashMap<String, Object>();

    public void addObject(String key, Object value) {
        this.map.put(key, value);
    }

    public void addAll(Map<String, Object> map) {
        this.map.putAll(map);
    }

    public void clear() {
        this.map.clear();
    }

    @Override
    public ReqComponentInfo createReqComponent(String reqComponentType, ReqComponentInfo reqComponentInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public StatementInfo createStatement(String statementType, StatementInfo statementInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public StatusInfo deleteReqComponent(String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public StatusInfo deleteStatement(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public String getNaturalLanguageForReqComponent(String reqComponentId, String nlUsageTypeKey, String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public String getNaturalLanguageForStatement(String statementId, String nlUsageTypeKey, String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public NlUsageTypeInfo getNlUsageType(String nlUsageTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<NlUsageTypeInfo> getNlUsageTypes() throws OperationFailedException {
        return null;
    }

    @Override
    public List<String> getRefObjectSubTypes(String objectTypeKey) throws OperationFailedException {
        return null;
    }

    @Override
    public List<String> getRefObjectTypes() throws OperationFailedException {
        return null;
    }

    @Override
    public RefStatementRelationInfo getRefStatementRelation(String refStatementRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public ReqComponentInfo getReqComponent(String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<ReqComponentInfo> getReqComponentsByType(String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public ReqComponentTypeInfo getReqComponentType(String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<ReqComponentTypeInfo> getReqComponentTypes() throws OperationFailedException {
        return null;
    }

    @Override
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public StatementInfo getStatement(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<StatementInfo> getStatementsByType(String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<StatementInfo> getStatementsUsingReqComponent(String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public StatementTreeViewInfo getStatementTreeView(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public StatementTypeInfo getStatementType(String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public ReqComponentInfo updateReqComponent(String reqComponentId, ReqComponentInfo reqComponentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;
    }

    @Override
    public StatementInfo updateStatement(String statementId, StatementInfo statementInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;
    }

    @Override
    public StatementTreeViewInfo updateStatementTreeView(String statementId, StatementTreeViewInfo statementTreeViewInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateReqComponent(String validationType, ReqComponentInfo reqComponentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateStatement(String validationType, StatementInfo statementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public ObjectStructure getObjectStructure(String objectTypeKey) {
        return null;
    }

    @Override
    public List<String> getObjectTypes() {
        return null;
    }

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        return null;
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        return null;
    }

    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        return null;
    }

    @Override
    public RefStatementRelationInfo createRefStatementRelation(RefStatementRelationInfo refStatementRelationInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

	@Override
	public StatusInfo deleteRefStatementRelation(String refStatementRelationId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public String getNaturalLanguageForRefStatementRelation(String refStatementRelationId, String nlUsageTypeKey, String language) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<RefStatementRelationInfo> getRefStatementRelationsByRef(String refObjectTypeKey, String refObjectId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<RefStatementRelationInfo> getRefStatementRelationsByStatement(String statementId) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language)
			throws InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language) 
			throws InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public RefStatementRelationInfo updateRefStatementRelation(String refStatementRelationId, RefStatementRelationInfo refStatementRelationInfo)
			throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
		return null;
	}

	@Override
	public List<StatementTypeInfo> getStatementTypes()
			throws OperationFailedException {
		return null;
	}

	@Override
	public List<String> getStatementTypesForStatementType(String statementTypeKey) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public RefStatementRelationTypeInfo getRefStatementRelationType(String refStatementRelationTypeKey) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<RefStatementRelationTypeInfo> getRefStatementRelationTypes()
			throws OperationFailedException {
		return null;
	}

	@Override
	public List<String> getRefStatementRelationTypesForRefObjectSubType(String refSubTypeKey) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getStatementTypesForRefStatementRelationType(String refStatementRelationTypeKey) 
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<StatementInfo> getStatementsUsingStatement(String statementId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateRefStatementRelation(String validationType, RefStatementRelationInfo refStatementRelationInfo)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}
    

    //from before the service contract change:

    // getLuStatement
    // return (LuStatementInfo) map.get(luStatementId);

    // getLuStatements
    /*
     * List<LuStatementInfo> list = new ArrayList<LuStatementInfo>(); for(String id : luStatementIdList) { LuStatementInfo
     * stmt = (LuStatementInfo) map.get(id); list.add(stmt); } return list;
     */

    // getLuStatementType
    // return (LuStatementTypeInfo) map.get(luStatementTypeKey);
    
    // getReqComponent
    // return (ReqComponentInfo) map.get(reqComponentId);
    
    // getReqComponents
    /*
     * List<ReqComponentInfo> list = new ArrayList<ReqComponentInfo>(); for(String id : reqComponentIdList) {
     * ReqComponentInfo req = (ReqComponentInfo) map.get(id); list.add(req); } return list;
     */

}
