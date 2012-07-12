package org.kuali.student.lum.program.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.common.search.dto.SearchTypeInfo;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.springframework.transaction.annotation.Transactional;

public class StatementDataService implements StatementRpcService{
	final static Logger LOG = Logger.getLogger(StatementDataService.class);
    
    protected StatementService statementService;
    protected LuService luService;
    
    private static final long serialVersionUID = 822326113643828855L;
    @Override
    @Transactional(readOnly=true)
    public List<StatementTypeInfo> getStatementTypesForStatementTypeForCourse(String statementTypeKey) throws Exception {
    
        List<StatementTypeInfo> allStatementTypes = new ArrayList<StatementTypeInfo>();

        List<String> topStatementTypes = statementService.getStatementTypesForStatementType(statementTypeKey);

        // loop through top statement types like enrollment eligibility and credit constraints
        for (String topStatementType : topStatementTypes) {           
            allStatementTypes.add(statementService.getStatementType(topStatementType));
            List<String> subStatementTypeNames = statementService.getStatementTypesForStatementType(topStatementType);

            // loop through statement types belonging to the top statement types
            for (String subStatementTypeName : subStatementTypeNames) {
                allStatementTypes.add(statementService.getStatementType(subStatementTypeName));
            }
        }
        
        return allStatementTypes;
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<StatementTypeInfo> getStatementTypesForStatementType(String statementTypeKey) throws Exception {
        List<String> statementTypeNames = statementService.getStatementTypesForStatementType(statementTypeKey);
        List<StatementTypeInfo> statementTypes = new ArrayList<StatementTypeInfo>();
        for (String statementTypeName : statementTypeNames) {
            statementTypes.add(statementService.getStatementType(statementTypeName));
        }
        return statementTypes;
    }
    @Override
    @Transactional(readOnly=true)
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String luStatementTypeKey) throws Exception {

        List<ReqComponentTypeInfo> reqComponentTypeInfoList;
        try { 
            reqComponentTypeInfoList = statementService.getReqComponentTypesForStatementType(luStatementTypeKey);
        } catch (Exception ex) {
            LOG.error(ex);
            throw new Exception("Unable to find Requirement Component Types based on LU Statement Type Key:" + luStatementTypeKey, ex);
        }
        
        return reqComponentTypeInfoList;
    }

    @Override
    @Transactional(readOnly=true)
    public String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language) throws Exception {
        return statementService.translateStatementTreeViewToNL(statementTreeViewInfo, nlUsageTypeKey, language);
    }

    @Override
    @Transactional(readOnly=true)
    public String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language) throws Exception {
        return statementService.translateReqComponentToNL(reqComponentInfo, nlUsageTypeKey, language);
    }

    @Override
    @Transactional(readOnly=true)
    public List<String> translateReqComponentToNLs(ReqComponentInfoUi reqComponentInfo, String[] nlUsageTypeKeys, String language) throws Exception {
    	List<String> nls = new ArrayList<String>(nlUsageTypeKeys.length);
    	for (String typeKey : nlUsageTypeKeys) {
    		nls.add(statementService.translateReqComponentToNL(reqComponentInfo, typeKey, language));
    	}
    	return nls;
    }

    @Override
    @Transactional(readOnly=true)
    public CluInfo getClu(String cluId) throws Exception {
        return luService.getClu(cluId);
    }

    @Override
    @Transactional(readOnly=true)
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId) throws Exception {
        return luService.getCurrentVersion(refObjectTypeURI, refObjectId);
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

	@Override
	public List<String> getObjectTypes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectStructure getObjectStructure(String objectTypeKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SearchTypeInfo> getSearchTypes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchResult search(SearchRequest searchRequest) {
		throw new UnsupportedOperationException();
	}
}
