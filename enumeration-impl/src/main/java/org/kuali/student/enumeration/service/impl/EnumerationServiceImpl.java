package org.kuali.student.enumeration.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;

import org.kuali.student.enumeration.dao.impl.EnumerationManagementDAOImpl;
import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.dto.EnumerationMeta;

import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;

@WebService(name = "EnumerationService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class EnumerationServiceImpl implements EnumerationService{
    private EnumerationManagementDAOImpl enumDAO;
    
    public EnumerationManagementDAOImpl getEnumDAO() {
        return enumDAO;
    }

    public void setEnumDAO(EnumerationManagementDAOImpl enumDAO) {
        this.enumDAO = enumDAO;
    }
    public EnumeratedValue addEnumeratedValue(String enumerationKey, EnumeratedValue value) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public EnumeratedValueList fetchEnumeration(String enumerationKey, String enumContextKey, String contextValue, Date contextDate) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public EnumerationMeta fetchEnumerationMeta(String enumerationKey) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public EnumeratedValueList findEnumerationMetas() {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public boolean removeEnumeratedValue(String enumerationKey, String code) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        return false;
    }

    public EnumeratedValue updateEnumeratedValue(String enumerationKey, String code, EnumeratedValue value) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
}
/*	


	@WebMethod
    public List<Enumerations> getEnumerations(){
    	
    	return null;
    }

    @WebMethod
    public List<EnumeratedValue> getEnumeration(@WebParam(name = "enumerationKey") String key) throws OperationFailedException{
        List<org.kuali.student.enumeration.entity.EnumeratedValue> enumValueListDAO = enumDAO.getEnumeratedValues(key);
        
        List<EnumeratedValue> enumValueList = new ArrayList<EnumeratedValue>();
        
        for(org.kuali.student.enumeration.entity.EnumeratedValue e: enumValueListDAO){
        	EnumeratedValue newEnumValue = this.convertDAOtoDTOEnumeratedValue(e);
        	
			//add the converted EnumeratedValue to the list
        	enumValueList.add(newEnumValue);
        }
        return enumValueList;
    }

    @WebMethod
    public EnumeratedValue addEnumeratedValue(@WebParam(name = "enumeratedValue")EnumeratedValue value){
    	//Convert DTO
    	org.kuali.student.enumeration.entity.EnumeratedValue newEnumValueDAO = this.convertDTOtoDAOEnumeratedValue(value);
    	//create EnumeratedValue
    	org.kuali.student.enumeration.entity.EnumeratedValue returnValue = enumDAO.createEnumeratedValue(newEnumValueDAO);
    	//convert return value
    	EnumeratedValue convertedReturn = this.convertDAOtoDTOEnumeratedValue(returnValue);
    	return convertedReturn;
    }

    @WebMethod
    public EnumeratedValue updateEnumeratedValue(@WebParam(name = "enumeratedValueNew")EnumeratedValue newValue){
    	
    	//what is the old value used for?  Ignoring oldValue parameter
    	org.kuali.student.enumeration.entity.EnumeratedValue newEnumValueDAO = this.convertDTOtoDAOEnumeratedValue(newValue);
    	org.kuali.student.enumeration.entity.EnumeratedValue returnValue = enumDAO.updateEnumeratedValue(newEnumValueDAO);
    	EnumeratedValue convertedReturn = this.convertDAOtoDTOEnumeratedValue(returnValue);
    	return convertedReturn;
    }

 //   public Status removeEnumeratedValue(String key);

    private EnumeratedValue convertDAOtoDTOEnumeratedValue(org.kuali.student.enumeration.entity.EnumeratedValue value){
    	EnumeratedValue newEnumValue = new EnumeratedValue();
    	newEnumValue.setValue(value.getValue());
    	newEnumValue.setId(value.getId());
    	newEnumValue.setCode(value.getCode());
    	newEnumValue.setAbbrevValue(value.getAbbrevValue());
    	newEnumValue.setEnumerationId(value.getEnumerationId());
    	newEnumValue.setSortKey(new BigInteger(Integer.toString(value.getSortKey())));
    	try {
    		DatatypeFactory df = DatatypeFactory.newInstance();
			
    		//effective date
			GregorianCalendar gCal = new GregorianCalendar();
			gCal.setTime(value.getEffectiveDate());
			XMLGregorianCalendar XMLcal = df.newXMLGregorianCalendar(gCal);
			newEnumValue.setEffectiveDate(XMLcal);
			
			//expiration date
			gCal.setTime(value.getExpirationDate());
			XMLcal = df.newXMLGregorianCalendar(gCal);
			newEnumValue.setExpirationDate(XMLcal);
			
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return newEnumValue;
    }
    
    private org.kuali.student.enumeration.entity.EnumeratedValue convertDTOtoDAOEnumeratedValue(EnumeratedValue value){
    	org.kuali.student.enumeration.entity.EnumeratedValue newEnumValueDAO = new org.kuali.student.enumeration.entity.EnumeratedValue();
    	newEnumValueDAO.setValue(value.getValue());
    	newEnumValueDAO.setId(value.getId());
    	newEnumValueDAO.setCode(value.getCode());
    	newEnumValueDAO.setAbbrevValue(value.getAbbrevValue());
    	newEnumValueDAO.setEnumerationId(value.getEnumerationId());
    	newEnumValueDAO.setEffectiveDate(value.getEffectiveDate().toGregorianCalendar().getTime());
    	newEnumValueDAO.setExpirationDate(value.getExpirationDate().toGregorianCalendar().getTime());
    	newEnumValueDAO.setSortKey(value.getSortKey().intValue());
    	return newEnumValueDAO;
    }
}
*/
