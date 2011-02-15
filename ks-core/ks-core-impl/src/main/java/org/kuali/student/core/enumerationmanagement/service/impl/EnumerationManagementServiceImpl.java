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

package org.kuali.student.core.enumerationmanagement.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.common.search.service.SearchManager;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.enumerationmanagement.EnumerationException;
import org.kuali.student.core.enumerationmanagement.dao.EnumerationManagementDAO;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValue;
import org.kuali.student.core.enumerationmanagement.entity.Enumeration;
import org.kuali.student.core.enumerationmanagement.service.EnumerationManagementService;
import org.kuali.student.core.enumerationmanagement.service.impl.util.EnumerationAssembler;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.enumerationmanagement.service.EnumerationManagementService", serviceName = "EnumerationManagementService", portName = "EnumerationManagementService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationManagementService")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class EnumerationManagementServiceImpl implements EnumerationManagementService{
    
	final static Logger logger = LoggerFactory.getLogger(EnumerationManagementServiceImpl.class);
	
    private SearchManager searchManager;

    private EnumerationManagementDAO enumDAO;
	
	public EnumerationManagementServiceImpl() {}
    
    private List<ValidationResultInfo> validateEnumeratedValue(EnumeratedValueInfo value){
    	return null; //FIXME need real validation
    }
     
	@Override
	public EnumerationInfo getEnumeration(String enumerationKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        
        Enumeration enumerationMetaEntity = enumDAO.fetch(Enumeration.class, enumerationKey);
        EnumerationInfo enumerationMeta = null;
        if(enumerationMetaEntity != null){
        	enumerationMeta = new EnumerationInfo();
	        EnumerationAssembler.toEnumerationInfo(enumerationMetaEntity, enumerationMeta);
        }
        return enumerationMeta;
	}

	@Override
	public List<EnumerationInfo> getEnumerations()
			throws OperationFailedException {
        List<Enumeration> entities =  this.enumDAO.findEnumerations();
        
        List<EnumerationInfo> dtos = EnumerationAssembler.toEnumerationMetaList(entities);
       
        return dtos;
	}
	
	@Override
	@Transactional(readOnly=false)
	public EnumeratedValueInfo addEnumeratedValue(String enumerationKey,
			EnumeratedValueInfo enumeratedValue) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
    	Enumeration meta;
		try {		    
			meta = enumDAO.fetch(Enumeration.class, enumeratedValue.getEnumerationKey());			
		} catch (DoesNotExistException e) {
			throw new InvalidParameterException("Enumeration does not exist for key:"+enumerationKey);
		}
		
    	if(meta != null){
    		List<ValidationResultInfo> results = this.validateEnumeratedValue(enumeratedValue);
    	
    		if(null != results) {
    		    for(ValidationResultInfo result:results){
    		        if(result !=null && ValidationResultInfo.ErrorLevel.ERROR.equals(result.getErrorLevel())){
    		            throw new EnumerationException("addEnumeratedValue failed because the EnumeratdValue failed to pass validation against its EnumerationMeta - With Messages: " + result.toString());//FIXME need to get messages here
    		        }
    		    }
    		}
    	}
    	
    	EnumeratedValue valueEntity = new EnumeratedValue();
    	EnumerationAssembler.toEnumeratedValueEntity(enumeratedValue, valueEntity);
    	valueEntity.setEnumeration(meta);
    	
        enumDAO.addEnumeratedValue(enumerationKey, valueEntity);
        

        return enumeratedValue;
	}

	@Override
	public List<EnumeratedValueInfo> getEnumeratedValues(String enumerationKey,
			String contextType, String contextValue, Date contextDate)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

        List<EnumeratedValue> enumeratedValueEntityList = new ArrayList<EnumeratedValue>();
        if(enumerationKey != null && contextType != null && contextValue != null && contextDate != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumeratedValuesWithContextAndDate(enumerationKey, contextType, contextValue, contextDate);
        }
        else if(enumerationKey != null && contextType != null && contextValue != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumeratedValuesWithContext(enumerationKey, contextType, contextValue);
        }
        else if(enumerationKey != null && contextDate != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumeratedValuesWithDate(enumerationKey, contextDate);
        }
        else if(enumerationKey != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumeratedValues(enumerationKey);
        }
        
        List<EnumeratedValueInfo> enumeratedValueList = EnumerationAssembler.toEnumeratedValueList(enumeratedValueEntityList);
        return enumeratedValueList;
	}


	@Override
	@Transactional(readOnly=false)
	public EnumeratedValueInfo updateEnumeratedValue(String enumerationKey,
			String code, EnumeratedValueInfo enumeratedValue)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        
        Enumeration meta;
        try {           
            meta = enumDAO.fetch(Enumeration.class, enumeratedValue.getEnumerationKey());           
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("Enumeration does not exist for key:"+enumerationKey);
        }
    	
    	if(meta != null){
	        List<ValidationResultInfo> results = this.validateEnumeratedValue(enumeratedValue);

	        if(null != results) {
	            for(ValidationResultInfo result:results){
	                if(result !=null && ValidationResultInfo.ErrorLevel.ERROR.equals(result.getErrorLevel())){
	                    throw new EnumerationException("addEnumeratedValue failed because the EnumeratdValue failed to pass validation against its EnumerationMeta - With Messages: " + result.toString());//FIXME need to get messages here
	                }
	            }
	        }
    	}

	    EnumeratedValue enumeratedValueEntity = new EnumeratedValue();    
	    EnumerationAssembler.toEnumeratedValueEntity(enumeratedValue, enumeratedValueEntity);
	    enumeratedValueEntity =  enumDAO.updateEnumeratedValue(meta, code, enumeratedValueEntity);
	    EnumerationAssembler.toEnumeratedValueInfo(enumeratedValueEntity, enumeratedValue);
        
        return enumeratedValue;
	}
	
	@Override
    @Transactional(readOnly=false)
	public StatusInfo removeEnumeratedValue(String enumerationKey, String code) {
        enumDAO.removeEnumeratedValue(enumerationKey, code);
        return new StatusInfo();
    }
	
	
	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		return searchManager.getSearchCriteriaTypes();
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
		return searchManager.getSearchResultType(searchResultTypeKey);
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		return searchManager.getSearchResultTypes();
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(searchTypeKey, "searchTypeKey");
		return searchManager.getSearchType(searchTypeKey);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		return searchManager.getSearchTypes();
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
		return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
		return searchManager.getSearchTypesByResult(searchResultTypeKey);
	}

	@Override
	public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
		return searchManager.search(searchRequest, enumDAO);
	}
	
	/**
	 * Check for missing parameter and throw localized exception if missing
	 *
	 * @param param
	 * @param parameter name
	 * @throws MissingParameterException
	 */
	private void checkForMissingParameter(Object param, String paramName)
			throws MissingParameterException {
		if (param == null) {
			throw new MissingParameterException(paramName + " can not be null");
		}
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	public void setEnumDAO(EnumerationManagementDAO enumDAO) {
		this.enumDAO = enumDAO;
	}

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getObjectTypes() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
}
