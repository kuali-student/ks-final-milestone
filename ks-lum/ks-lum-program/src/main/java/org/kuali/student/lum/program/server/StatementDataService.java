package org.kuali.student.lum.program.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.common.search.dto.SearchTypeInfo;
import org.kuali.student.common.util.ContextUtils;
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
    
    
    private StatementService statementService;
    private LuService luService;
    
    private static final long serialVersionUID = 822326113643828855L;
    @Override
    @Transactional(readOnly=true)
    public List<StatementTypeInfo> getStatementTypesForStatementTypeForCourse(String statementTypeKey,ContextInfo contextInfo) throws Exception {
    
        List<StatementTypeInfo> allStatementTypes = new ArrayList<StatementTypeInfo>();
        //TODO KSCM List Types does not match, I commented this out and did  initialize the List as ArrayList
        //      List<String> topStatementTypes = statementService.getStatementTypesForStatementType(statementTypeKey,ContextUtils.getContextInfo());
        List<String> topStatementTypes = new ArrayList<String>() ;
        
        // loop through top statement types like enrollment eligibility and credit constraints
        for (String topStatementType : topStatementTypes) {           
            allStatementTypes.add(statementService.getStatementType(topStatementType));
            //TODO KSCM List<> types differ, I did not initialized the string 
            //List<String> subStatementTypeNames = statementService.getStatementTypesForStatementType(topStatementType,ContextUtils.getContextInfo());
            List<String> subStatementTypeNames = new ArrayList<String>();
            // loop through statement types belonging to the top statement types
            for (String subStatementTypeName : subStatementTypeNames) {
                allStatementTypes.add(statementService.getStatementType(subStatementTypeName));
            }
        }
        
        return allStatementTypes;
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<StatementTypeInfo> getStatementTypesForStatementType(String statementTypeKey,ContextInfo contextInfo) throws Exception {
    	
    	//TODO KSCM : Need to rewire this logic to fit the new List types
//        List<String> statementTypeNames = statementService.getStatementTypesForStatementType(statementTypeKey,ContextUtils.getContextInfo());
//        List<StatementTypeInfo> statementTypes = new ArrayList<StatementTypeInfo>();
//        for (String statementTypeName : statementTypeNames) {
//            statementTypes.add(statementService.getStatementType(statementTypeName));
//        }
//        return statementTypes;
    	return null;
    }
    @Override
    @Transactional(readOnly=true)
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String luStatementTypeKey,ContextInfo contextInfo) throws Exception {

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
    public String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language,ContextInfo contextInfo) throws Exception {
        return statementService.translateStatementTreeViewToNL(statementTreeViewInfo, nlUsageTypeKey, language,contextInfo);
    }

    @Override
    @Transactional(readOnly=true)
    public String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language,ContextInfo contextInfo) throws Exception {
        return statementService.translateReqComponentToNL(reqComponentInfo, nlUsageTypeKey, language,contextInfo);
    }

    @Override
    @Transactional(readOnly=true)
    public List<String> translateReqComponentToNLs(ReqComponentInfoUi reqComponentInfo, String[] nlUsageTypeKeys, String language,ContextInfo contextInfo) throws Exception {
    	List<String> nls = new ArrayList<String>(nlUsageTypeKeys.length);
    	for (String typeKey : nlUsageTypeKeys) {
    		nls.add(statementService.translateReqComponentToNL(reqComponentInfo, typeKey, language,contextInfo));
    	}
    	return nls;
    }

    @Override
    @Transactional(readOnly=true)
    public CluInfo getClu(String cluId,ContextInfo contextInfo) throws Exception {
        return luService.getClu(cluId,ContextUtils.getContextInfo());
    }

    @Override
    @Transactional(readOnly=true)
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId,ContextInfo contextInfo) throws Exception {
        return luService.getCurrentVersion(refObjectTypeURI, refObjectId,ContextUtils.getContextInfo());
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
	public List<SearchTypeInfo> getSearchTypes(ContextInfo contextInfo) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey,ContextInfo contextInfo) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey,ContextInfo contextInfo) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey,ContextInfo contextInfo) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes(ContextInfo contextInfo) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey,ContextInfo contextInfo) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes(ContextInfo contextInfo) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey,ContextInfo contextInfo) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchResult search(SearchRequest searchRequest,ContextInfo contextInfo) {
		throw new UnsupportedOperationException();
	}
}
