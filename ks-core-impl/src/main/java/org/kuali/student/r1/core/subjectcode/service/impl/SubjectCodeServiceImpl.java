package org.kuali.student.r1.core.subjectcode.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.LookupService;
import org.kuali.student.common.util.DateFormatThread;
import org.kuali.student.r1.core.subjectcode.dao.SubjectCodeDao;
import org.kuali.student.r1.core.subjectcode.dao.SubjectCodeJoinOrgDao;
import org.kuali.student.r1.core.subjectcode.model.SubjectCode;
import org.kuali.student.r1.core.subjectcode.model.SubjectCodeJoinOrg;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.springframework.beans.factory.InitializingBean;

import javax.xml.namespace.QName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SubjectCodeServiceImpl 
    implements SubjectCodeService, InitializingBean {

    private static OrganizationService organizationService;
    private static LookupService lookupService;
    private SearchManager searchManager;
    private SubjectCodeDao subjectCodeDao;
    private SubjectCodeJoinOrgDao subjectCodeJoinOrgDao;
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
    protected boolean cachingEnabled = false;
    protected int searchCacheMaxSize = 20;
    protected int searchCacheMaxAgeSeconds = 90;
    protected Cache<String, SearchResultInfo> searchCache;
    
	@Override
	public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws OperationFailedException, InvalidParameterException, MissingParameterException {
		return searchManager.getSearchTypes(contextInfo);
	}

	@Override
	public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
		return searchManager.getSearchType(searchTypeKey, contextInfo);
	}


	@Override
	public SearchResultInfo search(SearchRequestInfo searchRequest, ContextInfo contextInfo)
            throws MissingParameterException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
		String searchKey = searchRequest.getSearchKey();
		//Check Params
		if(searchKey==null||searchKey.isEmpty()){
			throw new MissingParameterException("searchRequest parameter is required.");
		}
		
		//Get easy access to params
		Map<String,Object> paramMap = new HashMap<String,Object>();
		for(SearchParamInfo param:searchRequest.getParams()){
			paramMap.put(param.getKey(), param.getValues().get(0));
		}
		
		SearchResultInfo searchResult = null;
		
    	if(cachingEnabled){
    		//Get From Cache
            try {
                return searchCache.get(searchRequest.toString());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
		}
		
		//Do searches
    	if("subjectCode.search.subjectCodeGeneric".equals(searchKey)){
			searchResult = doSubjectCodeGenericSearch(paramMap);
		}else if("subjectCode.search.orgsForSubjectCode".equals(searchKey)){
			searchResult = doOrgsForSubjectCodeSearch(paramMap, contextInfo);
		}

		//Store to Cache
    	if(cachingEnabled){
    		//Store to cache
    		searchCache.put(searchRequest.toString(), searchResult );
    	}
    	
		return searchResult;
	}
	
    private SearchResultInfo doOrgsForSubjectCodeSearch(Map<String, Object> paramMap, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
		SearchResultInfo searchResult = new SearchResultInfo();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		String codeParam = (String) paramMap.get("subjectCode.queryParam.code");
		if(codeParam!=null){ 
			queryMap.put("subjectCode.code", codeParam);
		}
		String orgIdParam = (String) paramMap.get("subjectCode.queryParam.optionalOrgId");
		if(orgIdParam!=null){ 
			queryMap.put("orgId", orgIdParam);
		}
		
		//Use the BO service to perform the query
		List<SubjectCodeJoinOrg> subjectCodeJoinOrgs = subjectCodeJoinOrgDao.getBySubjectCodeAndOrgId(codeParam, orgIdParam);
        Map<String,List<SearchResultRowInfo>> orgIdToRowMapping = new HashMap<String,List<SearchResultRowInfo>> ();
        
        //Create search results from the results of the BO search
		for(SubjectCodeJoinOrg subjectCodeJoinOrg:subjectCodeJoinOrgs){
			//Only include active orgs if the search is not looking by org id
			if(orgIdParam!=null || subjectCodeJoinOrg.isActive()){
	        	SearchResultRowInfo row = new SearchResultRowInfo();
	        	row.addCell("subjectCode.resultColumn.code", subjectCodeJoinOrg.getSubjectCode().getCode());
	        	row.addCell("subjectCode.resultColumn.type", subjectCodeJoinOrg.getSubjectCode().getType().getId());
	        	row.addCell("subjectCode.resultColumn.activeFrom", subjectCodeJoinOrg.getActiveFromDate()==null?null: DateFormatThread.format(new Date(subjectCodeJoinOrg.getActiveFromDate().getTime())));
	        	row.addCell("subjectCode.resultColumn.activeTo", subjectCodeJoinOrg.getActiveToDate()==null?null:format.format(new Date(subjectCodeJoinOrg.getActiveToDate().getTime())));
	        	row.addCell("subjectCode.resultColumn.orgId", subjectCodeJoinOrg.getOrgId());
	        	//Get a mapping of the org id to this row so we can find it later and do all the org id searches in one call
	        	List<SearchResultRowInfo> rowList = orgIdToRowMapping.get(subjectCodeJoinOrg.getOrgId());
	        	if(rowList==null){
	        		rowList = new ArrayList<SearchResultRowInfo>();
		        	orgIdToRowMapping.put(subjectCodeJoinOrg.getOrgId(), rowList);
	        	}
	        	rowList.add(row);
	
	        	searchResult.getRows().add(row);
			}
        }
		
		//Translate Org Id to name.  Make a searchRequest for mapping org ids to org longname/shortname
		//Use the org search to Translate the orgIds into Org names and update the holder cells
		if(!orgIdToRowMapping.isEmpty()){
			//Perform the Org search
			SearchRequestInfo orgIdTranslationSearchRequest = new SearchRequestInfo("org.search.generic");
			orgIdTranslationSearchRequest.addParam("org.queryParam.orgOptionalIds", new ArrayList<String>(orgIdToRowMapping.keySet()));
			SearchResultInfo orgIdTranslationSearchResult = getOrganizationService().search(orgIdTranslationSearchRequest, contextInfo);
            			
			//For each translation, update the result cell with the translated org name
			for(SearchResultRowInfo row:orgIdTranslationSearchResult.getRows()){
				//Get Params
				String orgId = null;
				String orgShortName = null;
				String orgLongName = null;
				String orgType = null; 
				for(SearchResultCellInfo cell:row.getCells()){
					if("org.resultColumn.orgId".equals(cell.getKey())){
						orgId = cell.getValue();
					}else if("org.resultColumn.orgShortName".equals(cell.getKey())){
						orgShortName = cell.getValue();
					}else if("org.resultColumn.orgOptionalLongName".equals(cell.getKey())){
						orgLongName = cell.getValue();
					}else if("org.resultColumn.orgType".equals(cell.getKey())){
						orgType = cell.getValue();
					}
				}
				//Update the rows with the org translation
				for(SearchResultRowInfo rowToUpdate:orgIdToRowMapping.get(orgId)){
					rowToUpdate.addCell("subjectCode.resultColumn.orgShortName", orgShortName);
					rowToUpdate.addCell("subjectCode.resultColumn.orgLongName", orgLongName);
					rowToUpdate.addCell("subjectCode.resultColumn.orgType", orgType);
				}
			}
		}
		return searchResult;
	}

	private SearchResultInfo doSubjectCodeGenericSearch(Map<String, Object> paramMap) {
		SearchResultInfo searchResult = new SearchResultInfo();
		Map<String,String> queryMap = new HashMap<String,String>();
		String code = (String) paramMap.get("subjectCode.queryParam.code");
		if(code!=null){ 
			code = "%" + code.toUpperCase() + "%";
		}

		//Use built in KNS lookup to perform the search
		List<SubjectCode> subjectCodes = subjectCodeDao.getBySubjectCode(code);

		//Default sort by code
		Collections.sort(subjectCodes,new Comparator<SubjectCode>(){
			@Override
			public int compare(SubjectCode o1, SubjectCode o2) {
				if(o1==null && o2==null){
					return 0;
				}
				return o1.getCode().compareTo(o2.getCode());
		}});
		
		//Convert to a KS search result
        for(SubjectCode subjectCode:subjectCodes){
        	SearchResultRowInfo row = new SearchResultRowInfo();
        	row.addCell("subjectCode.resultColumn.id", subjectCode.getId());
        	row.addCell("subjectCode.resultColumn.code", subjectCode.getCode());
        	row.addCell("subjectCode.resultColumn.name", subjectCode.getName());
        	row.addCell("subjectCode.resultColumn.type", subjectCode.getType().getId());
        	searchResult.getRows().add(row);
        }
        return searchResult;
	}

	protected LookupService getLookupService() {
        if (lookupService == null) {
        	lookupService = KRADServiceLocatorWeb.getLookupService();
        }
        return lookupService;
    }
    
	protected static OrganizationService getOrganizationService() {
		if (organizationService == null) {
			organizationService = (OrganizationService) GlobalResourceLoader
					.getService(new QName(
                            "http://student.kuali.org/wsdl/organization",
                            "OrganizationService"));
		}
		return organizationService;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(cachingEnabled){
            searchCache = CacheBuilder.newBuilder()
                    .expireAfterAccess(searchCacheMaxAgeSeconds, TimeUnit.SECONDS)
                    .maximumSize(searchCacheMaxSize)
                    .softValues()
                    .build();
		}
	}
	
	public void setCachingEnabled(boolean cachingEnabled) {
		this.cachingEnabled = cachingEnabled;
	}

	public void setSearchCacheMaxSize(int searchCacheMaxSize) {
		this.searchCacheMaxSize = searchCacheMaxSize;
	}

	public void setSearchCacheMaxAgeSeconds(int searchCacheMaxAgeSeconds) {
		this.searchCacheMaxAgeSeconds = searchCacheMaxAgeSeconds;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

    public SubjectCodeJoinOrgDao getSubjectCodeJoinOrgDao() {
        return subjectCodeJoinOrgDao;
    }

    public void setSubjectCodeJoinOrgDao(SubjectCodeJoinOrgDao subjectCodeJoinOrgDao) {
        this.subjectCodeJoinOrgDao = subjectCodeJoinOrgDao;
    }

    public SubjectCodeDao getSubjectCodeDao() {
        return subjectCodeDao;
    }

    public void setSubjectCodeDao(SubjectCodeDao subjectCodeDao) {
        this.subjectCodeDao = subjectCodeDao;
    }
}
