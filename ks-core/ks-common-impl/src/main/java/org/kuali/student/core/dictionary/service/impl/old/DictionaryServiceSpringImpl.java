/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.dictionary.service.impl.old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.kuali.student.core.dictionary.old.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.old.DictionaryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

@WebService(endpointInterface = "org.kuali.student.core.dictionary.service.DictionaryService", serviceName = "DictionaryService", portName = "DictionaryService", targetNamespace = "http://student.kuali.org/wsdl/dictionary")
@Deprecated
public class DictionaryServiceSpringImpl implements DictionaryService {

	private String[] dictionaryContext;
	private Map<String, ObjectStructure> objectStructures;

	public DictionaryServiceSpringImpl() {
		super();
	}

	public DictionaryServiceSpringImpl(String dictionaryContext) {
		super();
		String[] locations = StringUtils.tokenizeToStringArray(dictionaryContext, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		this.dictionaryContext = locations;
		init();
	}
	
	public DictionaryServiceSpringImpl(String[] dictionaryContext) {
		super();
		this.dictionaryContext = dictionaryContext;
		init();
	}

	@SuppressWarnings("unchecked")
	public void init() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(dictionaryContext);

		Map<String, ObjectStructure> beansOfType = (Map<String, ObjectStructure>) ac.getBeansOfType(ObjectStructure.class);
		objectStructures = new HashMap<String, ObjectStructure>();
		for (ObjectStructure objStr : beansOfType.values()){
			//Only add top level structures
			if(objStr.getId()!=null){
				if(objStr.getId().startsWith("object.")){
					if(!objStr.getId().startsWith("object.field.")){
						objectStructures.put(objStr.getKey(), objStr);
					}
				}else{
					objectStructures.put(objStr.getKey(), objStr);
				}
			}else{
				objectStructures.put(objStr.getKey(), objStr);
			}
		}
	}

	@Override
	public ObjectStructure getObjectStructure(String objectTypeKey) {
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
