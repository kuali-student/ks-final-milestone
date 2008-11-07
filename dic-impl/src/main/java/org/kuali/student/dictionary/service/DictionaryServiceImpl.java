	package org.kuali.student.dictionary.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.kuali.student.dictionary.dto.*;

@WebService(endpointInterface = "org.kuali.student.dictionary.service.DictionaryService", serviceName = "DictionaryService", portName = "DictionaryService", targetNamespace = "http://org.kuali.student/dictonary")
public class DictionaryServiceImpl implements DictionaryService {

	private String xmlFile = "/Dictionary.xml";
	public void setXMLFile(String file){
		xmlFile = file;
	}
	
    public List<EnumeratedValue> fetchEnumeration(String enumerationKey, String enumContextKey, String contextValue, Date contextDate) {
		List<EnumeratedValue> eVals = new ArrayList<EnumeratedValue>();
		try {
			JAXBContext jc = JAXBContext.newInstance("org.kuali.student.dictionary.dto");
			Unmarshaller u = jc.createUnmarshaller();
			if(enumerationKey != null){
				EnumeratedValues enums = (EnumeratedValues)u.unmarshal(DictionaryServiceImpl.class.getResource("/" + enumerationKey + "-enum.xml"));
				if(enumContextKey == null || contextValue == null){	
					for(EnumeratedValue e: enums.getEnumeratedValue()){	
						if(enumValidForDate(e, contextDate)){
							eVals.add(e);
						}
					}
				}
				/*else if(enumContextKey != null && contextValue == null){
					for(EnumeratedValue e: enums.getEnumeratedValue()){	
						if(enumValidForDate(e, contextDate)){
							for(Context c: e.getContexts().getContext()){
								//check context type and value match
								if(c.getType().equals(enumContextKey)){
									eVals.add(e);
									break;
								}
							}
						}
					}
				}*/
				else{
					for(EnumeratedValue e: enums.getEnumeratedValue()){					
						if(enumValidForDate(e, contextDate)){
							for(Context c: e.getContexts().getContext()){
								//check context type and value match
								if(c.getType().equals(enumContextKey) && c.getValue().equals(contextValue)){
									eVals.add(e);
									break;
								}
							}
						}
					}
				}
			}
		}
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eVals;
    }
    
    private boolean enumValidForDate(EnumeratedValue e, Date contextDate){
    	boolean passesDateCheck = false;
		if(contextDate == null){
			passesDateCheck = true;
		}
		else{
			boolean afterEffective = false;
			if(e.getEffectiveDate() != null){
				afterEffective = contextDate.before(e.getExpirationDate());
			}
			else{
				afterEffective = true;
			}
			
			boolean beforeExpiration = false;
			if(e.getExpirationDate() != null){
				beforeExpiration = contextDate.before(e.getExpirationDate());
			}
			else{
				beforeExpiration = true;
			}
			
			if(afterEffective && beforeExpiration){
				passesDateCheck = true;
			}
		}
		return passesDateCheck;
    }

    public ObjectStructure fetchObjectStructure(String objectTypeKey) {
        ObjectStructure theStruct = null;
    	try {
            JAXBContext jc = JAXBContext.newInstance("org.kuali.student.dictionary.dto");

            Unmarshaller u = jc.createUnmarshaller();
            Dictionary dict = (Dictionary)u.unmarshal(DictionaryServiceImpl.class.getResource(xmlFile));
            
            for(ObjectStructure struc: dict.getObjectStructure()){
                if(objectTypeKey.equals(struc.getObjectTypeKey())){
                	theStruct = struc;
                }
            }
    	}
    	catch (JAXBException e) {
            e.printStackTrace();
        }
    	
        return theStruct;
    }

    public List<String> findObjectTypes() {
        List<String> types = new ArrayList<String>();
    	try {
            JAXBContext jc = JAXBContext.newInstance("org.kuali.student.dictionary.dto");

            Unmarshaller u = jc.createUnmarshaller();
           Dictionary dict = (Dictionary)u.unmarshal(DictionaryServiceImpl.class.getResource(xmlFile));
           // Dictionary dict = (Dictionary)u.unmarshal(new FileInputStream("C:\\Users\\bsmith\\KualiStudent_10-16-08\\dictionary\\dic-api\\src\\main\\resources\\Dictionary.xml"));
            
            for(ObjectStructure struc: dict.getObjectStructure()){
                types.add(struc.getObjectTypeKey());
            }
    	}
    	catch (Exception e) {
           throw new RuntimeException(e);
        }
        return types;
    }

    public boolean validateObject(String objectTypeKey, String stateKey, String info) {
        // TODO Garey - THIS METHOD NEEDS JAVADOCS
        return false;
    }

    public boolean validateStructureData(String objectTypeKey, String stateKey, String info) {
        // TODO Garey - THIS METHOD NEEDS JAVADOCS
        return false;
    }

}
