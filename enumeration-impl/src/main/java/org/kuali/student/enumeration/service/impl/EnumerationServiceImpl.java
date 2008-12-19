package org.kuali.student.enumeration.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enumeration.EnumerationException;
import org.kuali.student.enumeration.dao.EnumerationManagementDAO;
import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueField;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.entity.EnumeratedValueEntity;
import org.kuali.student.enumeration.entity.EnumeratedValueFieldEntity;
import org.kuali.student.enumeration.entity.EnumerationMetaEntity;
import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.enumeration.service.impl.util.POJOConverter;
import org.kuali.student.enumeration.validator.ValidationResult;
import org.kuali.student.enumeration.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.enumeration.service.EnumerationService", serviceName = "EnumerationService", portName = "EnumerationService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationService")
@Transactional
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class EnumerationServiceImpl implements EnumerationService{
    
	final static Logger logger = LoggerFactory.getLogger(EnumerationServiceImpl.class);
	
	private EnumerationManagementDAO enumDAO;
    
    public EnumerationManagementDAO getEnumDAO() {
        return enumDAO;
    }

    public void setEnumDAO(EnumerationManagementDAO enumDAO) {
        this.enumDAO = enumDAO;
    }

    public EnumerationMetaList findEnumerationMetas() {
        List<EnumerationMetaEntity> listDAOEntity =  this.enumDAO.findEnumerationMetas();
        
        EnumerationMetaList metaList = new EnumerationMetaList();
        List<EnumerationMeta> listDTOEntity =  POJOConverter.mapList(listDAOEntity, EnumerationMeta.class);
        metaList.setEnumerationMeta(listDTOEntity);
        
        return metaList;
    }
    
    private ValidationResult validateEnumeratedValue(EnumerationMeta meta, EnumeratedValue value){
    	
    	ValidationResult result = new ValidationResult();
    	List<EnumeratedValueField> fields = meta.getEnumeratedValueFields().getEnumeratedValueField();
        for(EnumeratedValueField field: fields){
        	if(field.getKey().equalsIgnoreCase("code")){
        		result = Validator.validate(value.getCode(), field.getFieldDescriptor().toMap());
        	}
        	else if(field.getKey().equalsIgnoreCase("abbrevValue")){
        		result = Validator.validate(value.getAbbrevValue(), field.getFieldDescriptor().toMap());
        	}
        	else if(field.getKey().equalsIgnoreCase("value")){
        		result = Validator.validate(value.getValue(), field.getFieldDescriptor().toMap());
        	}
        	else if(field.getKey().equalsIgnoreCase("effectiveDate")){
        		result = Validator.validate(value.getEffectiveDate(), field.getFieldDescriptor().toMap());
        	}
        	else if(field.getKey().equalsIgnoreCase("expirationDate")){
        		result = Validator.validate(value.getExpirationDate(), field.getFieldDescriptor().toMap());
        	}
        	else if(field.getKey().equalsIgnoreCase("sortKey")){
        		result = Validator.validate(value.getSortKey(), field.getFieldDescriptor().toMap());
        	}
        	
        	if(result.getErrorLevel() == ValidationResult.ErrorLevel.ERROR){
        		break;
        	}
        }
        return result;
    }
     
    public EnumeratedValue addEnumeratedValue(String enumerationKey, EnumeratedValue valueDTO) {
		ValidationResult result = new ValidationResult();
    	EnumerationMeta meta = this.fetchEnumerationMeta(enumerationKey);
    	if(meta != null){
	        result = this.validateEnumeratedValue(meta, valueDTO);
    	}
    	
    	if(result.getErrorLevel() != ValidationResult.ErrorLevel.ERROR){
    		EnumeratedValueEntity valueEntity = new EnumeratedValueEntity();
        	POJOConverter.map(valueDTO, valueEntity);
        	enumDAO.addEnumeratedValue(enumerationKey, valueEntity);
    	}
    	else{
    		throw new EnumerationException("addEnumeratedValue failed because the EnumeratdValue failed to pass validation against its EnumerationMeta - With Messages: " + result.getMessages());
    	}

        return valueDTO;
    }

    public EnumeratedValueList fetchEnumeration(String enumerationKey, String enumContextKey, String contextValue, Date contextDate) {
        EnumeratedValueList enumeratedValueListDTO = new EnumeratedValueList();
        List<EnumeratedValueEntity> enumeratedValueEntityList = new ArrayList();
        if(enumerationKey != null && enumContextKey != null && contextValue != null && contextDate != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumerationWithContextAndDate(enumerationKey, enumContextKey, contextValue, contextDate);
        }
        else if(enumerationKey != null && enumContextKey != null && contextValue != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumerationWithContext(enumerationKey, enumContextKey, contextValue);
        }
        else if(enumerationKey != null && contextDate != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumerationWithDate(enumerationKey, contextDate);
        }
        else if(enumerationKey != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumeration(enumerationKey);
        }
        
        List<EnumeratedValue> enumeratedValueList = POJOConverter.mapList(enumeratedValueEntityList, EnumeratedValue.class);
        
        enumeratedValueListDTO.setEnumeratedValue(enumeratedValueList);
        return enumeratedValueListDTO;
    }

    public EnumerationMeta fetchEnumerationMeta(String enumerationKey) {
        
        EnumerationMetaEntity enumerationMetaEntity = enumDAO.fetchEnumerationMeta(enumerationKey);
        EnumerationMeta enumerationMeta = null;
        if(enumerationMetaEntity != null){
        	enumerationMeta = new EnumerationMeta();
	        POJOConverter.map(enumerationMetaEntity, enumerationMeta);
        }
        return enumerationMeta;
    }



    public boolean removeEnumeratedValue(String enumerationKey, String code) {
        return enumDAO.removeEnumeratedValue(enumerationKey, code);
    }

    public EnumeratedValue updateEnumeratedValue(String enumerationKey, String code, EnumeratedValue value) {
        
		ValidationResult result = new ValidationResult();
    	EnumerationMeta meta = this.fetchEnumerationMeta(enumerationKey);
    	if(meta != null){
	        result = this.validateEnumeratedValue(meta, value);
    	}
    	
    	if(result.getErrorLevel() != ValidationResult.ErrorLevel.ERROR){
		    EnumeratedValueEntity enumeratedValueEntity = new EnumeratedValueEntity();    
		    POJOConverter.map(value, enumeratedValueEntity);
		    enumeratedValueEntity =  enumDAO.updateEnumeratedValue(enumerationKey, code, enumeratedValueEntity);
		    POJOConverter.map(enumeratedValueEntity,value);
    	}
    	else{
    		throw new EnumerationException("updateEnumeratedValue failed because the EnumeratdValue failed to pass validation against its EnumerationMeta - With Messages: " + result.getMessages());
    	}
        
        return value;
    }

	public EnumerationMeta addEnumerationMeta(EnumerationMeta meta) {
		EnumerationMetaEntity metaEntity = new EnumerationMetaEntity();
		
		POJOConverter.map(meta, metaEntity);
		enumDAO.addEnumerationMeta(metaEntity);
		return meta;
	}

	public boolean removeEnumerationMeta(String enumerationKey) {
		return enumDAO.removeEnumerationMeta(enumerationKey);
	}
}
