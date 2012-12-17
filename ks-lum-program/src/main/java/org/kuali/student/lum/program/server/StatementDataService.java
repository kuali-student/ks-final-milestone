package org.kuali.student.lum.program.server;

import org.apache.log4j.Logger;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.r1.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.dto.StatementTypeInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class StatementDataService implements StatementRpcService{
	final static Logger LOG = Logger.getLogger(StatementDataService.class);
    
    protected StatementService statementService;
	protected CluService cluService;
    
    private static final long serialVersionUID = 822326113643828855L;
    @Override
    @Transactional(readOnly=true)
    public List<StatementTypeInfo> getStatementTypesForStatementTypeForCourse(String statementTypeKey) throws Exception  {
    
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
        return cluService.getClu(cluId, ContextUtils.getContextInfo());
    }

    @Override
    @Transactional(readOnly=true)
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId) throws Exception {

    	return cluService.getCurrentVersion(refObjectTypeURI, refObjectId, ContextUtils.getContextInfo());

    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
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
	public List<TypeInfo> getSearchTypes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TypeInfo getSearchType(String searchTypeKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TypeInfo> getSearchResultTypes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TypeInfo> getSearchCriteriaTypes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchResultInfo search(SearchRequestInfo searchRequest) {
		throw new UnsupportedOperationException();
	}
}
