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

package org.kuali.student.common.util.jpa;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.SharedEntityManagerCreator;

/**
 * This loads a jpa message beans from a list of message bean files. Only beans
 * of entityType listed will be loaded.
 *
 * Example spring config:
 * 
 *  <bean id="messageload" class="org.kuali.student.common.util.jpa.LoadJpaBeanFileListener">
 *    <property name="loadJpaBean">
 *       <bean class="org.kuali.student.common.util.jpa.LoadJpaBean" />
 *    </property>
 *    <property name="entityClass" value="org.kuali.student.common.messages.entity.MessageEntity" />
 *    <property name="persistenceUnit" value="MessageManagement"/>
 *    <property name="preloadMap">
 *       <map>
 *          <entry key="File1" value="classpath:bean-file1.xml" />
 *          <entry key="File2" value="classpath:bean-file2.xml" />
 *       </map>
 *   </property>
 *   </bean>
 *
 * @author Kuali Student Team
 *
 */
public class LoadJpaBeanFileListener implements ApplicationListener,
    ApplicationContextAware { 
    
    private Map<String, String> preloadMap;
    private ApplicationContext applicationContext;
    private LoadJpaBean loadJpaBean;
    private String persistenceUnit;
    private String entityClass;
    
    private boolean loaded = false;

    @SuppressWarnings("unchecked")
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent && !loaded) {
            for (Entry<String, String> entry : preloadMap.entrySet()) {
                String messageFileName = entry.getValue();
                
                Resource res;
                try{
                    if(messageFileName.startsWith("classpath:")){
                        res = new ClassPathResource(messageFileName.substring("classpath:".length()));
                    }else{
                        res = new FileSystemResource(messageFileName);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                
                XmlBeanFactory factory = new XmlBeanFactory(res);

                EntityManagerFactory emf = EntityManagerFactoryUtils
                        .findEntityManagerFactory(applicationContext, persistenceUnit);
                EntityManager em = SharedEntityManagerCreator
                        .createSharedEntityManager(emf);
                try {
                    Class messageClazz = Class.forName(entityClass);
                    Object[] beanArray = factory.getBeansOfType(messageClazz).values().toArray();                    
                    loadJpaBean.loadData(Arrays.asList(beanArray), em);
                } catch (Exception e) {
                    //Log here, need to catch because if errors are thrown then the transaction is left hanging for some reason
                }
            }
            
            loaded=true;
        }        
    }

    /**
     * @return the preloadMap
     */
    public Map<String, String> getPreloadMap() {
        return preloadMap;
    }

    /**
     * @param preloadMap
     *            the preloadMap to set
     */
    public void setPreloadMap(Map<String, String> preloadMap) {
        this.preloadMap = preloadMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * @return the loadJpaBean
     */
    public LoadJpaBean getLoadJpaBean() {
        return loadJpaBean;
    }

    /**
     * @param loadJpaBean
     *            the loadJpaBean to set
     */
    public void setLoadJpaBean(LoadJpaBean loadJpaBean) {
        this.loadJpaBean = loadJpaBean;
    }

    public String getPersistenceUnit() {
        return persistenceUnit;
    }

    public void setPersistenceUnit(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }
    
}
