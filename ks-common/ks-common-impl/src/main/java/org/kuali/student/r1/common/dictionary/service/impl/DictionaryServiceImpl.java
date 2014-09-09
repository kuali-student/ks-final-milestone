package org.kuali.student.r1.common.dictionary.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

@Deprecated
public class DictionaryServiceImpl implements DictionaryService{
	private String[] dictionaryContext;
	private Map<String, ObjectStructureDefinition> objectStructures;
    private static final Logger LOG = LoggerFactory.getLogger(DictionaryServiceImpl.class);
    
	public DictionaryServiceImpl() {
		super();
	}

	public DictionaryServiceImpl(String dictionaryContext) {
		super();
        this.dictionaryContext = StringUtils.tokenizeToStringArray(dictionaryContext, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		init();
	}
	
	public DictionaryServiceImpl(String[] dictionaryContext) {
		super();
        this.dictionaryContext = new String[dictionaryContext.length];
        System.arraycopy(dictionaryContext, 0, this.dictionaryContext, 0, dictionaryContext.length);
//		this.dictionaryContext = dictionaryContext;
		init();
	}

	@SuppressWarnings("unchecked")
	public synchronized void init() {
		ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext(dictionaryContext);
		Map<String, ObjectStructureDefinition> beansOfType = ac.getBeansOfType(ObjectStructureDefinition.class);
        ac.close();

		objectStructures = new HashMap<String, ObjectStructureDefinition>();
		for (ObjectStructureDefinition objStr : beansOfType.values()){
			if(objectStructures.containsKey(objStr.getName())){
				LOG.warn("Repeated dictionary structure with the name '{}'.", objStr.getName());
			}
			objectStructures.put(objStr.getName(), objStr);
		}
	}

	@Override
	public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
		return objectStructures.get(objectTypeKey);
	}

	@Override
	public List<String> getObjectTypes() {
		return new ArrayList<String>(objectStructures.keySet());
	}

	public String[] getDictionaryContext() {
		return dictionaryContext;
	}

	public void setDictionaryContext(String[] dictionaryContext) {
        this.dictionaryContext = new String[dictionaryContext.length];
        System.arraycopy(dictionaryContext, 0, this.dictionaryContext, 0, dictionaryContext.length);
//		this.dictionaryContext = dictionaryContext;
	}
}
