package org.kuali.student.dictionary.service;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.dictionary.dto.EnumeratedValue;
import org.kuali.student.dictionary.dto.ObjectStructure;

@WebService(endpointInterface = "org.kuali.student.dictionary.service.DictionaryService", serviceName = "DictionaryService", portName = "DictionaryService", targetNamespace = "http://org.kuali.student/dictonary")
public class DictionaryServiceImpl implements DictionaryService {

    public List<EnumeratedValue> getEnumeration(String enumerationKey, String enumContextKey, String contextValue) {
        // TODO Garey - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public ObjectStructure getObjectStructure(String objectTypeKey) {
        // TODO Garey - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public List<String> getObjectTypes() {
        // TODO Garey - THIS METHOD NEEDS JAVADOCS
        return null;
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
