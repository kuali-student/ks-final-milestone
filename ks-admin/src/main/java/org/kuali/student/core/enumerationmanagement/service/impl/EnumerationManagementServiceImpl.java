package org.kuali.student.core.enumerationmanagement.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.beanutils.BeanUtils;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.enumerationmanagement.bo.EnumeratedValue;
import org.kuali.student.core.enumerationmanagement.bo.Enumeration;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.core.enumerationmanagement.service.EnumerationManagementService;
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
import org.kuali.student.core.search.service.SearchManager;

@WebService(endpointInterface = "org.kuali.student.core.enumerationmanagement.service.EnumerationManagementService", serviceName = "EnumerationManagementService", portName = "EnumerationManagementService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationManagementService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class EnumerationManagementServiceImpl implements EnumerationManagementService{
    private SearchManager searchManager;
    private SearchableDao searchDao;
    private BusinessObjectService businessObjectService;
    
	@Override
	public List<String> getObjectTypes() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	@Override
	public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
		throw new UnsupportedOperationException("Unimplemented");
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
		return searchManager.search(searchRequest, searchDao);
	}
	

	@Override
	public List<EnumerationInfo> getEnumerations()
			throws OperationFailedException {
		List<Enumeration> bos = (List<Enumeration>) getBusinessObjectService().findAll(Enumeration.class);
		
		return toEnumerationMetaList(bos);
	}

	@Override
	public EnumerationInfo getEnumeration(String enumerationKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		Enumeration bo = (Enumeration) getBusinessObjectService().findBySinglePrimaryKey(Enumeration.class, enumerationKey);
		EnumerationInfo dto = new EnumerationInfo();
		toEnumerationInfo(bo, dto);
		return dto;
	}

	@Override
	public List<EnumeratedValueInfo> getEnumeratedValues(String enumerationKey,
			String contextKey, String contextValue, Date contextDate)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		Map<String,String> criteria = new HashMap<String,String>();
		criteria.put("enumerationId", enumerationKey);
		
		List<EnumeratedValue> bos = (List<EnumeratedValue>) getBusinessObjectService().findMatching(EnumeratedValue.class, criteria);
		
		return toEnumeratedValueList(bos);
	}

	@Override
	public EnumeratedValueInfo addEnumeratedValue(String enumerationKey,
			EnumeratedValueInfo enumeratedValue) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		//Copy properties
		EnumeratedValue bo = new EnumeratedValue();
		toEnumeratedValueEntity(enumeratedValue, bo);
		
		//Lookup the enumeration and set it
		Enumeration enumeration = (Enumeration) getBusinessObjectService().findBySinglePrimaryKey(Enumeration.class, enumerationKey);
		if(enumeration==null){
			throw new InvalidParameterException("Enumeration not found for key: " + enumerationKey);
		}
		bo.setEnumeration(enumeration);
		
		//Save the bo
		getBusinessObjectService().save(bo);
		
		//Transform back to a dto and return
		EnumeratedValueInfo dto = new EnumeratedValueInfo();
		toEnumeratedValueInfo(bo, dto);
		return dto;
	}

	@Override
	public EnumeratedValueInfo updateEnumeratedValue(String enumerationKey,
			String code, EnumeratedValueInfo enumeratedValue)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		//Lookup the enumerated value
		Map<String,String> criteria = new HashMap<String,String>();
		criteria.put("enumerationId", enumerationKey);
		criteria.put("code", code);
		Collection<EnumeratedValue> results = getBusinessObjectService().findMatching(EnumeratedValue.class, criteria);
		
		EnumeratedValue bo = null;
		if(results!=null){
			if(results.size()==1){
				bo = results.iterator().next();
			}
		}
	    if(bo==null){
			throw new DoesNotExistException("Single EnumeratedValue could not be found for key: " + enumerationKey + " and code: " + code);
		}
		//Copy properties
		toEnumeratedValueEntity(enumeratedValue, bo);
		
		//Lookup the enumeration and set it
		Enumeration enumeration = (Enumeration) getBusinessObjectService().findBySinglePrimaryKey(Enumeration.class, enumeratedValue.getEnumerationKey());
		if(enumeration==null){
			throw new InvalidParameterException("Enumeration not found for key: " + enumerationKey);
		}
		bo.setEnumeration(enumeration);
		
		//Save the bo
		getBusinessObjectService().save(bo);
		
		//Transform back to a dto and return
		EnumeratedValueInfo dto = new EnumeratedValueInfo();
		toEnumeratedValueInfo(bo, dto);
		return dto;
	}

	@Override
	public StatusInfo removeEnumeratedValue(String enumerationKey, String code)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		Map<String,String> criteria = new HashMap<String,String>();
		criteria.put("enumerationId", enumerationKey);
		criteria.put("code", code);
		getBusinessObjectService().deleteMatching(EnumeratedValue.class, criteria);
		return new StatusInfo();
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
    public void toEnumeratedValueEntity(EnumeratedValueInfo enumeratedValue, EnumeratedValue enumeratedValueEntity) {
        try {
			BeanUtils.copyProperties(enumeratedValueEntity, enumeratedValue);
			enumeratedValueEntity.setActiveFromDate(new Timestamp(enumeratedValue.getEffectiveDate().getTime()));
			enumeratedValueEntity.setActiveToDate(new Timestamp(enumeratedValue.getExpirationDate().getTime()));
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error copying properties",e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Error copying properties",e);
		}
    }

    public void toEnumeratedValueInfo(EnumeratedValue enumeratedValueEntity, EnumeratedValueInfo enumeratedValue) {
        try {
            BeanUtils.copyProperties(enumeratedValue, enumeratedValueEntity);
            enumeratedValue.setEnumerationKey(enumeratedValueEntity.getEnumeration().getId());
            enumeratedValue.setEffectiveDate(enumeratedValueEntity.getActiveFromDate());
            enumeratedValue.setExpirationDate(enumeratedValueEntity.getActiveToDate());
        } catch (IllegalAccessException e) {
        	throw new RuntimeException("Error copying properties",e);
        } catch (InvocationTargetException e) {
        	throw new RuntimeException("Error copying properties",e);
        }

    }

    public List<EnumeratedValueInfo> toEnumeratedValueList(List<EnumeratedValue> enumeratedValueEntityList) {
        List<EnumeratedValueInfo> enumeratedValueList = new ArrayList<EnumeratedValueInfo>();
        if(enumeratedValueEntityList!=null){
	        for (EnumeratedValue enumeratedValueEntity : enumeratedValueEntityList) {
	        	EnumeratedValueInfo eValue = new EnumeratedValueInfo();
	            toEnumeratedValueInfo(enumeratedValueEntity, eValue);
	            enumeratedValueList.add(eValue);
	        }
        }
        return enumeratedValueList;
    }
    
    public static List<EnumerationInfo> toEnumerationMetaList(List<Enumeration>enumerationMetaEntityList){
        List<EnumerationInfo> enumerationMetaList = new ArrayList<EnumerationInfo>();
        if(enumerationMetaEntityList!=null){ 
        	for(Enumeration enumerationMetaEntity :enumerationMetaEntityList ){
	        	EnumerationInfo eMeta = new EnumerationInfo();
	            toEnumerationInfo(enumerationMetaEntity,eMeta);
	            enumerationMetaList.add(eMeta);
	        }
        }
        return enumerationMetaList;
    }
        

    public static void toEnumerationInfo(Enumeration enumerationEntity, EnumerationInfo enumerationMeta) {
        try {
            BeanUtils.copyProperties(enumerationMeta, enumerationEntity);
            enumerationMeta.setId(enumerationEntity.getId());
            enumerationMeta.setEffectiveDate(enumerationEntity.getActiveFromDate());
            enumerationMeta.setExpirationDate(enumerationEntity.getActiveToDate());
        } catch (IllegalAccessException e) {
        	throw new RuntimeException("Error copying properties",e);
        } catch (InvocationTargetException e) {
        	throw new RuntimeException("Error copying properties",e);
        }

    }

    public static void toEnumeration(EnumerationInfo enumerationMeta, Enumeration enumerationEntity) {
        try {
            BeanUtils.copyProperties(enumerationEntity, enumerationMeta);
            enumerationEntity.setId(enumerationMeta.getId());
            enumerationEntity.setActiveFromDate(new Timestamp(enumerationMeta.getEffectiveDate().getTime()));
            enumerationEntity.setActiveToDate(new Timestamp(enumerationMeta.getExpirationDate().getTime()));
        } catch (IllegalAccessException e) {
        	throw new RuntimeException("Error copying properties",e);
        } catch (InvocationTargetException e) {
        	throw new RuntimeException("Error copying properties",e);
        }

    }
	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchDao(SearchableDao searchDao) {
		this.searchDao = searchDao;
	}

	public SearchableDao getSearchDao() {
		return searchDao;
	}

	public BusinessObjectService getBusinessObjectService() {
        if (businessObjectService == null) {
            businessObjectService = KNSServiceLocator.getBusinessObjectService();
        }
        return businessObjectService;
	}

}
