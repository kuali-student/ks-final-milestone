package org.kuali.student.common.dictionary.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

public class DictionaryServiceImpl implements DictionaryService{
	private String[] dictionaryContext;
	private Map<String, ObjectStructureDefinition> objectStructures;

	public DictionaryServiceImpl() {
		super();
	}

	public DictionaryServiceImpl(String dictionaryContext) {
		super();
		String[] locations = StringUtils.tokenizeToStringArray(dictionaryContext, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		this.dictionaryContext = locations;
		init();
	}
	
	public DictionaryServiceImpl(String[] dictionaryContext) {
		super();
		this.dictionaryContext = dictionaryContext;
		init();
	}

	@SuppressWarnings("unchecked")
	public void init() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(dictionaryContext);

		Map<String, ObjectStructureDefinition> beansOfType = (Map<String, ObjectStructureDefinition>) ac.getBeansOfType(ObjectStructureDefinition.class);
		objectStructures = new HashMap<String, ObjectStructureDefinition>();
		for (ObjectStructureDefinition objStr : beansOfType.values()){
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
		this.dictionaryContext = dictionaryContext;
	}
}
