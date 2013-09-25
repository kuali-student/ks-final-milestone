/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.spring;

import java.io.Serializable;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;


/**
 * This class exists soley to test the WebServiceAwareSpringBeanPostProcessor class.
 * 
 * @author Kuali Student Team
 */
public class FakeDictionaryServiceDecoratorImpl extends AbstractFakeService implements DictionaryService, Serializable {

	

	@Override
    public List<String> getObjectTypes() {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String toString() {
		return getClass().getName();
    }
	
	
}
