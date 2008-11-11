package org.kuali.student.enumeration.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enumeration.dao.EnumerationManagementDAO;
import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.entity.EnumeratedValueEntity;
import org.kuali.student.enumeration.entity.EnumerationMetaEntity;
import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.enumeration.service.impl.util.POJOConverter;

@WebService(name = "EnumerationService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class EnumerationServiceImpl implements EnumerationService{
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
        List<EnumerationMeta> listDTOEntity = null;
        POJOConverter.map(listDAOEntity, listDTOEntity);
        metaList.setEnumerationMeta(listDTOEntity);
        
        return metaList;
    }    
    
    
    public EnumeratedValue addEnumeratedValue(String enumerationKey, EnumeratedValue valueDTO) {
        EnumeratedValueEntity valueEntity = null;
        POJOConverter.map(valueDTO, valueEntity);
        enumDAO.addEnumeratedValue(enumerationKey, valueEntity);
        return null;
    }

    public EnumeratedValueList fetchEnumeration(String enumerationKey, String enumContextKey, String contextValue, Date contextDate) {
        EnumeratedValueList enumeratedValueListDTO = new EnumeratedValueList();
        
        
        List<EnumeratedValueEntity> enumeratedValueEntityList = enumDAO.fetchEnumeration(enumerationKey, enumContextKey, contextValue, contextDate);
        
        List<EnumeratedValue> enumeratedValueList = null; 
        
        POJOConverter.mapList(enumeratedValueEntityList, EnumeratedValue.class);
        
        enumeratedValueListDTO.setEnumeratedValue(enumeratedValueList);
        return enumeratedValueListDTO;
    }

    public EnumerationMeta fetchEnumerationMeta(String enumerationKey) {
        
        EnumerationMetaEntity enumerationMetaEntity = enumDAO.fetchEnumerationMeta(enumerationKey);
        
        EnumerationMeta enumerationMeta = new EnumerationMeta();  
        POJOConverter.map(enumerationMetaEntity, enumerationMeta);
        return enumerationMeta;
    }



    public boolean removeEnumeratedValue(String enumerationKey, String code) {
        enumDAO.removeEnumeratedValue(enumerationKey, code);
        
        return true;
    }

    public EnumeratedValue updateEnumeratedValue(String enumerationKey, String code, EnumeratedValue value) {
        
        EnumeratedValueEntity enumeratedValueEntity = new EnumeratedValueEntity();
        
        POJOConverter.map(value, enumeratedValueEntity);
        enumeratedValueEntity =  enumDAO.updateEnumeratedValue(enumerationKey, code, enumeratedValueEntity);
        
        POJOConverter.map(enumeratedValueEntity,value);
        return value;
    }
}
