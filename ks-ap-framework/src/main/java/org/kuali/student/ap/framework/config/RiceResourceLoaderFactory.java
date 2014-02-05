/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.ap.framework.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.namespace.QName;

import org.kuali.rice.core.api.config.CoreConfigHelper;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.resourceloader.ResourceLoader;
import org.kuali.rice.core.framework.resourceloader.BaseResourceLoader;
import org.kuali.rice.core.framework.resourceloader.SimpleServiceLocator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AutowireCandidateResolver;

/**
 * Short-term alternate for
 * org.kuali.rice.core.framework.resourceloader.RiceResourceLoaderFactory
 * 
 * @deprecated
 */
public class RiceResourceLoaderFactory {

	private static final String RICE_ROOT_RESOURCE_LOADER_NAME = "RICE_ROOT_RESOURCE_LOADER";
	private static final String RICE_SPRING_RESOURCE_LOADER_NAME = "RICE_SPRING_RESOURCE_LOADER_NAME";

	static ResourceLoader createRootRiceResourceLoader(ServletContext context,
			List<String> springFileLocations, String prefix,
			AutowireCandidateResolver autowireCandidateResolver,
			BeanFactory delegatedBeanFactory) {
		// FIXME: RICE MODULARITY
		// hack to not break the hack in
		// ResourceLoaderContainer.moveKSBLoadersDownHack();
		if ("KSB".equals(prefix.toUpperCase())) {
			prefix = "K~S~B";
		}

		String applicationId = CoreConfigHelper.getApplicationId();
		final QName root = new QName(applicationId, prefix.toUpperCase() + "_"
				+ RICE_ROOT_RESOURCE_LOADER_NAME);

		if (getRootResourceLoaderNames() == null
				|| !getRootResourceLoaderNames().contains(root)) {
			addRootResourceLoaderName(root);
		}

		final QName spring = new QName(applicationId, prefix.toUpperCase()
				+ "_" + RICE_SPRING_RESOURCE_LOADER_NAME);
		if (getSpringResourceLoaderNames() == null
				|| !getSpringResourceLoaderNames().contains(root)) {
			addSpringResourceLoaderName(spring);
		}

		final ResourceLoader rootResourceLoader = new BaseResourceLoader(root,
				new SimpleServiceLocator());

		// KSAP MOD: Use KSAP resource loader for auto-wiring support
		// this class.
		final ResourceLoader springResourceLoader = new SpringResourceLoader(
				spring, springFileLocations, context,
				autowireCandidateResolver, delegatedBeanFactory);
		rootResourceLoader.addResourceLoaderFirst(springResourceLoader);

		return rootResourceLoader;
	}

	// UNMODIFIED BELOW THIS LINE
	@SuppressWarnings("unchecked")
	private static Collection<QName> getRootResourceLoaderNames() {
		return (Collection<QName>) ConfigContext.getCurrentContextConfig()
				.getObject(RICE_ROOT_RESOURCE_LOADER_NAME);
	}

	private static void addRootResourceLoaderName(QName name) {
		@SuppressWarnings("unchecked")
		Collection<QName> names = (Collection<QName>) ConfigContext
				.getCurrentContextConfig().getObject(
						RICE_ROOT_RESOURCE_LOADER_NAME);
		if (names == null) {
			names = new ArrayList<QName>();
		}
		names.add(name);

		ConfigContext.getCurrentContextConfig().putObject(
				RICE_ROOT_RESOURCE_LOADER_NAME, names);
	}

	@SuppressWarnings("unchecked")
	private static Collection<QName> getSpringResourceLoaderNames() {
		return (Collection<QName>) ConfigContext.getCurrentContextConfig()
				.getObject(RICE_SPRING_RESOURCE_LOADER_NAME);
	}

	private static void addSpringResourceLoaderName(QName name) {
		@SuppressWarnings("unchecked")
		Collection<QName> names = (Collection<QName>) ConfigContext
				.getCurrentContextConfig().getObject(
						RICE_SPRING_RESOURCE_LOADER_NAME);
		if (names == null) {
			names = new ArrayList<QName>();
		}
		names.add(name);

		ConfigContext.getCurrentContextConfig().putObject(
				RICE_SPRING_RESOURCE_LOADER_NAME, names);
	}

}
