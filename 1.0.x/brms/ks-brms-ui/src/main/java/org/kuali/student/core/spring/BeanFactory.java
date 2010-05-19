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

package org.kuali.student.core.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactory {
    private static BeanFactory _instance;
    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-beans.xml");
    private BeanFactory() {
        
    }
    public static synchronized BeanFactory getInstance() {
        if (_instance == null) {
            _instance = new BeanFactory();
        }
        return _instance;
    }
    public Object getBean(String name) {
        return context.getBean(name);
    }
}
