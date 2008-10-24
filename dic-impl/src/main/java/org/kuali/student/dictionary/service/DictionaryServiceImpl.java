package org.kuali.student.dictionary.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

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
	
    public List<EnumeratedValue> getEnumeration(String enumerationKey, String enumContextKey, String contextValue) {
		List<EnumeratedValue> eVals = new ArrayList<EnumeratedValue>();
		try {
			JAXBContext jc = JAXBContext.newInstance("org.kuali.student.dictionary.dto");
			
			if(enumContextKey == null || contextValue == null){
				Unmarshaller u = jc.createUnmarshaller();
				EnumeratedValues enums = (EnumeratedValues)u.unmarshal(DictionaryServiceImpl.class.getResource(enumerationKey + "-enum.xml"));
				
				eVals = enums.getEnumeratedValue();
			}
			else{		
				Unmarshaller u = jc.createUnmarshaller();
				EnumeratedValues enums = (EnumeratedValues)u.unmarshal(DictionaryServiceImpl.class.getResource(enumerationKey + "-enum.xml"));
				for(EnumeratedValue e: enums.getEnumeratedValue()){
					for(Context c: e.getContexts().getContext()){
						if(c.getType().equals(enumContextKey) && c.getValue().equals(contextValue)){
							eVals.add(e);
							break;
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

    public ObjectStructure getObjectStructure(String objectTypeKey) {
        ObjectStructure theStruct = null;
    	try {
            JAXBContext jc = JAXBContext.newInstance("org.kuali.student.dictionary.dto");

            Unmarshaller u = jc.createUnmarshaller();
            System.out.println(super.getClass().getResource("/Dictionary.xml"));
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

    public List<String> getObjectTypes() {
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
