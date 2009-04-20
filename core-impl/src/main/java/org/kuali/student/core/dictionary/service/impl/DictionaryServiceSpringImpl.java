package org.kuali.student.core.dictionary.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

@WebService(endpointInterface = "org.kuali.student.core.dictionary.service.DictionaryService", serviceName = "DictionaryService", portName = "DictionaryService", targetNamespace = "http://org.kuali.student/core/dictonary")
public class DictionaryServiceSpringImpl implements DictionaryService {
    
    private Map<String, ObjectStructure> objectStructures;

    @SuppressWarnings("unchecked")
    public DictionaryServiceSpringImpl() {
        ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:organization-dictionary-config.xml");
        Map<String,ObjectStructure> beansOfType = (Map<String,ObjectStructure>) ac.getBeansOfType(ObjectStructure.class);
        objectStructures = new HashMap<String, ObjectStructure>();
        for(ObjectStructure objStr : beansOfType.values())
            objectStructures.put(objStr.getObjectTypeKey(), objStr);
    }

    @Override
    public ObjectStructure getObjectStructure(String objectTypeKey) {
        return objectStructures.get(objectTypeKey);
    }

    @Override
    public List<String> getObjectTypes() {
        return new ArrayList<String>(objectStructures.keySet());
    }

    @Override
    public boolean validateObject(String objectTypeKey, String stateKey, String info) {
        // TODO ddean - THIS METHOD NEEDS JAVADOCS
        return false;
    }

    @Override
    public boolean validateStructureData(String objectTypeKey, String stateKey, String info) {
        // TODO ddean - THIS METHOD NEEDS JAVADOCS
        return false;
    }

}
