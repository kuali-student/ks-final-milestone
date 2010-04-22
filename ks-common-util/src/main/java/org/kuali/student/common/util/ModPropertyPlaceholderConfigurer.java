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

package org.kuali.student.common.util;

import java.util.HashSet;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StringValueResolver;

public class ModPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer implements InitializingBean  {
	
	private String customConfigSystemProperty;
	private String customConfigFileLocation;
	private Resource[] locations;	
	
	private String beanName;
	private BeanFactory beanFactory;
	private String nullValue;
	
	
	public String getCustomConfigSystemProperty() {
		return customConfigSystemProperty;
	}

	public void setCustomConfigSystemProperty(String customConfigSystemProperty) {
		this.customConfigSystemProperty = customConfigSystemProperty;
	}

	@Override
	public void setLocations(Resource[] locations) {
		this.locations=locations;
		super.setLocations(locations);
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(customConfigSystemProperty!=null){
			String customConfigLocation = System.getProperty(customConfigSystemProperty);
			try{
				customConfigLocation = this.parseStringValue(customConfigLocation, System.getProperties(), new HashSet<String>());

				Resource customConfigResource = new DefaultResourceLoader().getResource(customConfigLocation);
				if(customConfigResource.exists()){
					Resource[] finalLocations = new Resource[locations.length+1];
					int i=0;
					for(Resource resource:locations){
						finalLocations[i]=resource;
						i++;
					}
					finalLocations[i]=customConfigResource;
					
					super.setLocations(finalLocations);
				}else{
					logger.warn("File does not exist:"+customConfigLocation);
				}
			}catch(Exception e){
				logger.warn("Could not load custom properties from property:"+customConfigSystemProperty+" location:"+customConfigLocation);
			}
		}
		if(customConfigFileLocation!=null){
			String location = this.parseStringValue(customConfigFileLocation, System.getProperties(), new HashSet<String>());
			try{
				Resource customConfigResource = new DefaultResourceLoader().getResource(location);
		
				if(customConfigResource.exists()){
					Resource[] finalLocations = new Resource[locations.length+1];
					int i=0;
					for(Resource resource:locations){
						finalLocations[i] = resource;
						i++;
					}
					finalLocations[i] = customConfigResource;
					
					super.setLocations(finalLocations);
				}else{
					logger.warn("File does not exist:"+location);
				}
			}catch(Exception e){
				logger.warn("Could not load custom properties from file:"+location);
			}
		}
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
		super.setBeanName(beanName);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
		super.setBeanFactory(beanFactory);
	}
	
	@Override
	public void setNullValue(String nullValue) {
		this.nullValue = nullValue;
		super.setNullValue(nullValue);
	}
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {

		StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(props);
		ModBeanDefinitionVisitor visitor = new ModBeanDefinitionVisitor(valueResolver);
		
		String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
		for (int i = 0; i < beanNames.length; i++) {
			// Check that we're not parsing our own bean definition,
			// to avoid failing on unresolvable placeholders in properties file locations.
			if (!(beanNames[i].equals(this.beanName) && beanFactoryToProcess.equals(this.beanFactory))) {
				BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(beanNames[i]);
				try {
					visitor.visitBeanDefinition(bd);
				}
				catch (BeanDefinitionStoreException ex) {
					throw new BeanDefinitionStoreException(bd.getResourceDescription(), beanNames[i], ex.getMessage());
				}
			}
		}
		
		// New in Spring 2.5: resolve placeholders in alias target names and aliases as well.
		beanFactoryToProcess.resolveAliases(valueResolver);
	}
	
	/**
	 * BeanDefinitionVisitor that resolves placeholders in String values,
	 * delegating to the <code>parseStringValue</code> method of the
	 * containing class.
	 */
	public class PlaceholderResolvingStringValueResolver implements StringValueResolver {

		private final Properties props;

		public PlaceholderResolvingStringValueResolver(Properties props) {
			this.props = props;
		}

		public String resolveStringValue(String strVal) throws BeansException {
			String value = parseStringValue(strVal, this.props, new HashSet<String>());
			return (value.equals(nullValue) ? null : value);
		}
		
		public Properties resolvePropertyValue(String strVal){
			Properties prefixedProps = new Properties();
			
			for(Object key:props.keySet()){
				String keyStr = (String)key; 
				if(keyStr.startsWith(strVal)){
					String newKeyStr = keyStr.substring(strVal.length()+1);
					prefixedProps.put(newKeyStr, resolveStringValue((String)props.get(key)));
				}
			}
			
			return prefixedProps;
		}
		
	}

	public String getCustomConfigFileLocation() {
		return customConfigFileLocation;
	}

	public void setCustomConfigFileLocation(String customConfigFileLocation) {
		this.customConfigFileLocation = customConfigFileLocation;
	}
}
