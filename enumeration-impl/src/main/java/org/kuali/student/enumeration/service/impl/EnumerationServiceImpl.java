package org.kuali.student.enumeration.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
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
import org.kuali.student.enumeration.dto.Enumerations;
import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;

@WebService(name = "EnumerationService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class EnumerationServiceImpl implements EnumerationService{

    @WebMethod
    public List<Enumerations> getEnumerations(){
    	EnumerationManagementDAOImpl enumDAO = new EnumerationManagementDAOImpl();
    	enumDAO.
    	return null;
    }

    @WebMethod
    public List<EnumeratedValue> getEnumeration(@WebParam(name = "enumerationKey") String key) throws OperationFailedException{
        EnumerationManagementDAOImpl enumDAO = new EnumerationManagementDAOImpl();
        List<org.kuali.student.enumeration.entity.EnumeratedValue> enumValueListDAO = enumDAO.getEnumeratedValues(key);
        
        List<EnumeratedValue> enumValueList = new ArrayList<EnumeratedValue>();
        
        for(org.kuali.student.enumeration.entity.EnumeratedValue e: enumValueListDAO){
        	EnumeratedValue newEnumValue = new EnumeratedValue();
        	newEnumValue.setValue(e.getValue());
        	newEnumValue.setId(e.getId());
        	newEnumValue.setCode(e.getCode());
        	newEnumValue.setAbbrevValue(e.getAbbrevValue());
        	newEnumValue.setEnumerationId(e.getEnumerationId());
        	newEnumValue.setSortKey(new BigInteger(Integer.toString(e.getSortKey())));
        	try {
        		DatatypeFactory df = DatatypeFactory.newInstance();
				
        		//effective date
				GregorianCalendar gCal = new GregorianCalendar();
				gCal.setTime(e.getEffectiveDate());
				XMLGregorianCalendar XMLcal = df.newXMLGregorianCalendar(gCal);
				newEnumValue.setEffectiveDate(XMLcal);
				
				//expiration date
				gCal.setTime(e.getExpirationDate());
				XMLcal = df.newXMLGregorianCalendar(gCal);
				newEnumValue.setExpirationDate(XMLcal);
				
			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
			//add the converted EnumeratedValue to the list
        	enumValueList.add(newEnumValue);
        }
        return enumValueList;
    }

    @WebMethod
    public EnumeratedValue addEnumeratedValue(@WebParam(name = "enumeratedValue")EnumeratedValue value){
    	EnumerationManagementDAOImpl enumDAO = new EnumerationManagementDAOImpl();
    	org.kuali.student.enumeration.entity.EnumeratedValue newEnumValueDAO = new org.kuali.student.enumeration.entity.EnumeratedValue();
    	newEnumValueDAO.setValue(value.getValue());
    	newEnumValueDAO.setId(value.getId());
    	newEnumValueDAO.setCode(value.getCode());
    	newEnumValueDAO.setAbbrevValue(value.getAbbrevValue());
    	newEnumValueDAO.setEnumerationId(value.getEnumerationId());
    	newEnumValueDAO.setEffectiveDate(value.getEffectiveDate().toGregorianCalendar().getTime());
    	newEnumValueDAO.setExpirationDate(value.getExpirationDate().toGregorianCalendar().getTime());
    	newEnumValueDAO.setSortKey(value.getSortKey().intValue());
    	enumDAO.createEnumeratedValue(newEnumValueDAO);
    	//what do I return?
    	return null;
    }

    @WebMethod
    public EnumeratedValue updateEnumeratedValue(@WebParam(name = "enumeratedValueOld")EnumeratedValue oldValue, @WebParam(name = "enumeratedValueNew")EnumeratedValue newValue){
    	
    	//what is the old value used for?  used to check to see if we need an update?
    	EnumerationManagementDAOImpl enumDAO = new EnumerationManagementDAOImpl();
    	org.kuali.student.enumeration.entity.EnumeratedValue newEnumValueDAO = new org.kuali.student.enumeration.entity.EnumeratedValue();
    	newEnumValueDAO.setValue(newValue.getValue());
    	newEnumValueDAO.setId(newValue.getId());
    	newEnumValueDAO.setCode(newValue.getCode());
    	newEnumValueDAO.setAbbrevValue(newValue.getAbbrevValue());
    	newEnumValueDAO.setEnumerationId(newValue.getEnumerationId());
    	newEnumValueDAO.setEffectiveDate(newValue.getEffectiveDate().toGregorianCalendar().getTime());
    	newEnumValueDAO.setExpirationDate(newValue.getExpirationDate().toGregorianCalendar().getTime());
    	newEnumValueDAO.setSortKey(newValue.getSortKey().intValue());
    	enumDAO.updateEnumeratedValue(newEnumValueDAO);
    	//what do I return?
    	return null;
    }

 //   public Status removeEnumeratedValue(String key);

}
