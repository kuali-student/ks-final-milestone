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

package org.kuali.student.r2.common.class1.search;

import org.apache.log4j.Logger;
import org.kuali.student.r2.common.dao.impl.SearchableCrudDaoImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.CrossSearchTypeInfo;
import org.kuali.student.r2.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.r2.core.search.dto.SearchTypeInfo;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.jws.WebParam;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Loads all search info for a service into memory
 *
 */
public class SearchManagerImpl implements SearchManager {

	final Logger logger = Logger.getLogger(SearchManagerImpl.class);

    private SearchableCrudDaoImpl dao;

	private String searchContextFile;
	private Map<String, SearchTypeInfo> searchInfoTypeMap;
	private Map<String, SearchCriteriaTypeInfo> searchCriteriaTypeMap;
	private Map<String, SearchResultTypeInfo> searchResultTypeInfoMap;
	private Map<String, String> queryMap;

	private CrossSearchManager crossSearchManager;

	@SuppressWarnings("unchecked")
	private void init() {
		ApplicationContext ac = new FileSystemXmlApplicationContext(searchContextFile);
		searchInfoTypeMap = ac.getBeansOfType(SearchTypeInfo.class);
		searchCriteriaTypeMap = ac.getBeansOfType(SearchCriteriaTypeInfo.class);
		searchResultTypeInfoMap = ac.getBeansOfType(SearchResultTypeInfo.class);
		queryMap = (Map<String, String>) ac.getBean("queryMap");
	}

	public SearchManagerImpl(String searchContextFile) {
		super();
		this.searchContextFile = searchContextFile;
		init();
	}

    /**
     * This Constructor should only be used in tests.
     * @param searchContextFile
     * @param em
     */
    public SearchManagerImpl(String searchContextFile, EntityManager em) {
        this(searchContextFile);
        dao = new SearchableCrudDaoImpl();
        dao.setEm(em);
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return toTypeInfo(searchInfoTypeMap.get(searchTypeKey));
	}

    @Override
    public List<TypeInfo> getSearchTypes( ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<TypeInfo> typeInfos = new ArrayList<TypeInfo>();
        for(SearchTypeInfo searchTypeInfo : searchInfoTypeMap.values()){
            typeInfos.add(toTypeInfo(searchTypeInfo));
        }
        return typeInfos;
	}

    private TypeInfo toTypeInfo(SearchTypeInfo searchTypeInfo){

        if (searchTypeInfo == null){
            return null;
        }

        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setKey(searchTypeInfo.getKey());
        typeInfo.setName(searchTypeInfo.getName());
        RichTextInfo textInfo = new RichTextInfo();
        textInfo.setPlain(searchTypeInfo.getDesc());
        textInfo.setFormatted(searchTypeInfo.getDesc());
        typeInfo.setDescr(textInfo);
        return typeInfo;
    }

	public String getSearchContextFile() {
		return searchContextFile;
	}

	public void setSearchContext(String searchContextFile) {
		this.searchContextFile = searchContextFile;
	}

	@Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException, InvalidParameterException {
    	if(searchRequestInfo == null){
			throw new MissingParameterException("Search Request can not be null.");
		}

		String searchKey = searchRequestInfo.getSearchKey();

		SearchTypeInfo searchType = searchInfoTypeMap.get(searchKey);

		//If no type was found for the key, try to dispatch the search
		if(searchType == null){
			if(crossSearchManager!=null && crossSearchManager.getSearchDispatcher()!=null){
				logger.info("Search type '"+searchKey+"' is not known to this service's search manager, attempting to dispatch search.");
				return crossSearchManager.getSearchDispatcher().search(searchRequestInfo, contextInfo);
			}else{
				logger.error("Search type '"+searchKey+"' is not known to this service's search manager.");
				throw new RuntimeException("Search type '"+searchKey+"' is not known to this service's search manager.");
			}
		}

		//Check if the search is a cross search
		if(searchType instanceof CrossSearchTypeInfo){
			if(crossSearchManager == null){
				//FIXME should we change these to Operation Failed Exceptions? also we need to handle invalid parameters.
				throw new RuntimeException("Requested cross service search:"+searchKey+", but no cross service search manager was defined.");
			}
			return crossSearchManager.doCrossSearch(searchRequestInfo, (CrossSearchTypeInfo) searchType, contextInfo);
		}


		try{
			return dao.search(searchRequestInfo, queryMap, searchType);
		}catch (Exception e){
			logger.error("Search Failed for searchKey:"+searchKey,e);
			throw new RuntimeException("Search Failed for searchKey:"+searchKey, e);
		}
	}

	public void setCrossSearchManager(CrossSearchManager crossSearchManager) {
		this.crossSearchManager = crossSearchManager;
	}

    public SearchableCrudDaoImpl getDao() {
        return dao;
    }

    public void setDao(SearchableCrudDaoImpl dao) {
        this.dao = dao;
    }
}
