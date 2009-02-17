package org.kuali.student.core.dictionary.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.kuali.student.core.dictionary.DictionaryException;
import org.kuali.student.core.dictionary.dto.Context;
import org.kuali.student.core.dictionary.dto.Dictionary;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.enumerable.dto.EnumeratedValue;
import org.kuali.student.core.enumerable.dto.EnumeratedValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(endpointInterface = "org.kuali.student.core.dictionary.service.DictionaryService", serviceName = "DictionaryService", portName = "DictionaryService", targetNamespace = "http://org.kuali.student/core/dictonary")

public class DictionaryServiceImpl implements DictionaryService {

	private String xmlFile = "/Dictionary.xml";
	private static final String CONTEXT_NAME = "org.kuali.student.core.dictionary.dto";
	private JAXBContext context;
	private Unmarshaller unmarshaller;

	private HashMap<String, EnumeratedValues> enumerationCache = new HashMap<String, EnumeratedValues>();

	final static Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);

	private Dictionary dict;

	public DictionaryServiceImpl(){
		try {
			context = JAXBContext.newInstance(CONTEXT_NAME);
			unmarshaller = context.createUnmarshaller();
			dict = (Dictionary)unmarshaller.unmarshal(DictionaryServiceImpl.class.getResource(xmlFile));
		}
		catch (JAXBException e) {
				logger.error("DictionaryServiceImpl instantiation failed.", e);
				throw new DictionaryException("DictionaryServiceImpl instantiation failed.", e);
		}
	}

	public void setXMLFile(String file){
		xmlFile = file;
	}

    public List<EnumeratedValue> getEnumeration(String enumerationKey, String enumContextKey, String contextValue, Date contextDate) {
		List<EnumeratedValue> eVals = new ArrayList<EnumeratedValue>();
		try {
			//JAXBContext jc = JAXBContext.newInstance("org.kuali.student.dictionary.dto");
			//Unmarshaller u = jc.createUnmarshaller();

			if(enumerationKey != null){
				EnumeratedValues enums;
				if(enumerationCache.containsKey(enumerationKey)){
					enums = enumerationCache.get(enumerationKey);
				}
				else{
					enums = (EnumeratedValues)unmarshaller.unmarshal(DictionaryServiceImpl.class.getResource("/enum/" + enumerationKey + ".xml"));
					enumerationCache.put(enumerationKey, enums);
				}

				if(enumContextKey == null || contextValue == null){
					for(EnumeratedValue e: enums.getEnumeratedValues()){
						if(enumValidForDate(e, contextDate)){
							eVals.add(e);
						}
					}
				}
				else{
					for(EnumeratedValue e: enums.getEnumeratedValues()){
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
			logger.error("fetchEnumeration failed - possibly an enum xml file is missing", e);
			throw new DictionaryException("fetchEnumeration failed - possibly an enum xml file is missing", e);
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

    public ObjectStructure getObjectStructure(String objectTypeKey) {
        ObjectStructure theStruct = null;

        for(ObjectStructure struc: dict.getObjectStructure()){
            if(objectTypeKey.equals(struc.getObjectTypeKey())){
            	theStruct = struc;
            }
        }
        return theStruct;
    }

    public List<String> getObjectTypes() {
        List<String> types = new ArrayList<String>();

        for(ObjectStructure struc: dict.getObjectStructure()){
            types.add(struc.getObjectTypeKey());
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
