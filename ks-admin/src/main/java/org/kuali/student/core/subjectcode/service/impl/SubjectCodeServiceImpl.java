package org.kuali.student.core.subjectcode.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.util.MaxAgeSoftReference;
import org.kuali.rice.core.util.MaxSizeMap;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.search.dto.SearchParam;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultCell;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.common.search.dto.SearchTypeInfo;
import org.kuali.student.common.search.service.SearchManager;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.subjectcode.bo.SubjectCode;
import org.kuali.student.core.subjectcode.bo.SubjectCodeJoinOrg;
import org.kuali.student.core.subjectcode.service.SubjectCodeService;
import org.springframework.beans.factory.InitializingBean;

@WebService(endpointInterface = "org.kuali.student.core.subjectcode.service.SubjectCodeService", serviceName = "SubjectCodeService", portName = "SubjectCodeService", targetNamespace = "http://student.kuali.org/wsdl/subjectCode")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SubjectCodeServiceImpl implements SubjectCodeService, InitializingBean{

	private static OrganizationService organizationService;
	private BusinessObjectService businessObjectService;
	private SearchManager searchManager;
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	protected boolean cachingEnabled = false;
	protected int searchCacheMaxSize = 20;
	protected int searchCacheMaxAgeSeconds = 90;
	protected Map<String,MaxAgeSoftReference<SearchResult>> searchCache;

	@Override
	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		return searchManager.getSearchTypes();
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return searchManager.getSearchType(searchTypeKey);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return searchManager.getSearchTypesByResult(searchResultTypeKey);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		return searchManager.getSearchResultTypes();
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return searchManager.getSearchResultType(searchResultTypeKey);
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		return searchManager.getSearchCriteriaTypes();
	}

	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
	}

	@Override
	public SearchResult search(SearchRequest searchRequest)
			throws MissingParameterException {
		String searchKey = searchRequest.getSearchKey();
		//Check Params
		if(searchKey==null||searchKey.isEmpty()){
			throw new MissingParameterException("searchRequest parameter is required.");
		}
		
		//Get easy access to params
		Map<String,Object> paramMap = new HashMap<String,Object>();
		for(SearchParam param:searchRequest.getParams()){
			paramMap.put(param.getKey(), param.getValue());
		}
		
		SearchResult searchResult = null;
		
    	if(cachingEnabled){
    		//Get From Cache
    		MaxAgeSoftReference<SearchResult> ref = searchCache.get(searchRequest.toString());
    		if ( ref != null ) {
    			searchResult = ref.get();
    			if(searchResult != null){
    				return searchResult;
    			}
    		}
		}
		
		//Do searches
    	if("subjectCode.search.subjectCodeGeneric".equals(searchKey)){
			searchResult = doSubjectCodeGenericSearch(paramMap);
		}else if("subjectCode.search.orgsForSubjectCode".equals(searchKey)){
			searchResult = doOrgsForSubjectCodeSearch(paramMap);
		}

		//Store to Cache
    	if(cachingEnabled){
    		//Store to cache
    		searchCache.put(searchRequest.toString(), new MaxAgeSoftReference<SearchResult>( searchCacheMaxAgeSeconds, searchResult) );
    	}
    	
		return searchResult;
	}
	
    private SearchResult doOrgsForSubjectCodeSearch(Map<String, Object> paramMap) throws MissingParameterException {
		SearchResult searchResult = new SearchResult();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		String code = (String) paramMap.get("subjectCode.queryParam.code");
		if(code!=null){ 
			queryMap.put("subjectCode.code", paramMap.get("subjectCode.queryParam.code"));
		}
		//Use the BO service to perform the query
		List<SubjectCodeJoinOrg> subjectCodeJoinOrgs = (List<SubjectCodeJoinOrg>) getBusinessObjectService().findMatching(SubjectCodeJoinOrg.class, queryMap);
        Map<String,List<SearchResultRow>> orgIdToRowMapping = new HashMap<String,List<SearchResultRow>> ();
        
        //Create search results from the results of the BO search
		for(SubjectCodeJoinOrg subjectCodeJoinOrg:subjectCodeJoinOrgs){
        	SearchResultRow row = new SearchResultRow();
        	row.addCell("subjectCode.resultColumn.code", subjectCodeJoinOrg.getSubjectCode().getCode());
        	row.addCell("subjectCode.resultColumn.type", subjectCodeJoinOrg.getSubjectCode().getTypeId());
        	row.addCell("subjectCode.resultColumn.activeFrom", subjectCodeJoinOrg.getActiveFromDate()==null?null:format.format(new Date(subjectCodeJoinOrg.getActiveFromDate().getTime())));
        	row.addCell("subjectCode.resultColumn.activeTo", subjectCodeJoinOrg.getActiveToDate()==null?null:format.format(new Date(subjectCodeJoinOrg.getActiveToDate().getTime())));
        	row.addCell("subjectCode.resultColumn.orgId", subjectCodeJoinOrg.getOrgId());
        	//Get a mapping of the org id to this row so we can find it later and do all the org id searches in one call
        	List<SearchResultRow> rowList = orgIdToRowMapping.get(subjectCodeJoinOrg.getOrgId());
        	if(rowList==null){
        		rowList = new ArrayList<SearchResultRow>();
	        	orgIdToRowMapping.put(subjectCodeJoinOrg.getOrgId(), rowList);
        	}
        	rowList.add(row);

        	searchResult.getRows().add(row);
        }
		
		//Translate Org Id to name.  Make a searchRequest for mapping org ids to org longname/shortname
		//Use the org search to Translate the orgIds into Org names and update the holder cells
		if(!orgIdToRowMapping.isEmpty()){
			//Perform the Org search
			SearchRequest orgIdTranslationSearchRequest = new SearchRequest("org.search.generic");
			orgIdTranslationSearchRequest.addParam("org.queryParam.orgOptionalIds", new ArrayList<String>(orgIdToRowMapping.keySet()));
			SearchResult orgIdTranslationSearchResult = getOrganizationService().search(orgIdTranslationSearchRequest);
			
			//For each translation, update the result cell with the translated org name
			for(SearchResultRow row:orgIdTranslationSearchResult.getRows()){
				//Get Params
				String orgId = null;
				String orgShortName = null;
				String orgLongName = null;
				String orgType = null; 
				for(SearchResultCell cell:row.getCells()){
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
				for(SearchResultRow rowToUpdate:orgIdToRowMapping.get(orgId)){
					rowToUpdate.addCell("subjectCode.resultColumn.orgShortName", orgShortName);
					rowToUpdate.addCell("subjectCode.resultColumn.orgLongName", orgLongName);
					rowToUpdate.addCell("subjectCode.resultColumn.orgType", orgType);
				}
			}
		}
		return searchResult;
	}

	private SearchResult doSubjectCodeGenericSearch(Map<String, Object> paramMap) {
		SearchResult searchResult = new SearchResult();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		String code = (String) paramMap.get("subjectCode.queryParam.code");
		if(code!=null){ 
			queryMap.put("code", paramMap.get("subjectCode.queryParam.code"));
		}

		List<SubjectCode> subjectCodes = (List<SubjectCode>) getBusinessObjectService().findMatching(SubjectCode.class, queryMap);
        
        for(SubjectCode subjectCode:subjectCodes){
        	SearchResultRow row = new SearchResultRow();
        	row.addCell("subjectCode.resultColumn.id", subjectCode.getId());
        	row.addCell("subjectCode.resultColumn.code", subjectCode.getCode());
        	row.addCell("subjectCode.resultColumn.name", subjectCode.getName());
        	row.addCell("subjectCode.resultColumn.type", subjectCode.getTypeId());
        	searchResult.getRows().add(row);
        }
        return searchResult;
	}

	protected BusinessObjectService getBusinessObjectService() {
        if (businessObjectService == null) {
            businessObjectService = KNSServiceLocator.getBusinessObjectService();
        }
        return  businessObjectService;
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
			searchCache = Collections.synchronizedMap( new MaxSizeMap<String,MaxAgeSoftReference<SearchResult>>( searchCacheMaxSize ) );
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
}
