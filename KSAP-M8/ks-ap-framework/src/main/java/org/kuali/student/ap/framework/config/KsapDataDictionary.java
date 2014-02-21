package org.kuali.student.ap.framework.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.datadictionary.BeanOverride;
import org.kuali.rice.krad.datadictionary.BusinessObjectEntry;
import org.kuali.rice.krad.datadictionary.DataDictionary;
import org.kuali.rice.krad.datadictionary.DataDictionaryEntry;
import org.kuali.rice.krad.datadictionary.DataDictionaryException;
import org.kuali.rice.krad.datadictionary.DataObjectEntry;
import org.kuali.rice.krad.datadictionary.DictionaryBeanFactoryPostProcessor;
import org.kuali.rice.krad.datadictionary.DocumentEntry;
import org.kuali.rice.krad.datadictionary.InactivationBlockingMetadata;
import org.kuali.rice.krad.datadictionary.MaintenanceDocumentEntry;
import org.kuali.rice.krad.datadictionary.parse.StringListConverter;
import org.kuali.rice.krad.datadictionary.parse.StringMapConverter;
import org.kuali.rice.krad.uif.UifConstants.ViewType;
import org.kuali.rice.krad.uif.util.ComponentBeanPostProcessor;
import org.kuali.rice.krad.uif.util.UifBeanFactoryPostProcessor;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.ksb.messaging.threadpool.KSBThreadPool;
import org.kuali.rice.ksb.service.KSBServiceLocator;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.convert.support.GenericConversionService;

/**
 * @deprecated TODO KSAP-26 Move mods to KRAD DataDictionary implementation.
 */
public class KsapDataDictionary extends DataDictionary implements
		InitializingBean {

	private static final Logger LOG = Logger
			.getLogger(KsapDataDictionary.class);

	static final Object DD_MUTEX = new Object();

	private ConfigurableListableBeanFactory ddBeans;
	private DataDictionaryIndex ddIndex;
	private UifDictionaryIndex uifIndex;
	private final DataDictionaryMapper ddMapper = new DataDictionaryIndexMapper();
	private DataDictionaryLoadTask loader;
	private boolean loaderError;

	public KsapDataDictionary() {
		ddBeans = null;
		xmlReader = null;
		ddIndex = null;
		uifIndex = null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		assert ddBeans == null && xmlReader == null && ddIndex == null
				&& uifIndex == null && loader == null : "Already initialized";
	}

	private static final ThreadLocal<DataDictionaryLoadTask> TL_LOADER = new ThreadLocal<DataDictionaryLoadTask>();

	private class DataDictionaryLoadTask implements Runnable {

		private final boolean allowConcurrentValidation;
		private RiceBeanFactory loadingDdBeans;
		private DataDictionaryIndex loadingDdIndex;
		private UifDictionaryIndex loadingUifIndex;
		private ArrayList<String> loadingBeanValidationFiles;

		private DataDictionaryLoadTask(boolean allowConcurrentValidation) {
			this.allowConcurrentValidation = allowConcurrentValidation;
		}

		@Override
		public void run() {
			if (loader != this) {
				LOG.warn("Canceling DD refresh, a more recent loader has been has been started "
						+ loader);
				return;
			}
			try {
				loadingDdBeans = KRADConfigurer.getActiveInstance()
						.createPrivateBeanFactory();
				loadingDdIndex = new DataDictionaryIndex(loadingDdBeans);
				loadingUifIndex = new UifDictionaryIndex(loadingDdBeans);
				loadingBeanValidationFiles = new ArrayList<String>();

				// UIF post processor that sets component ids
				BeanPostProcessor idPostProcessor = new ComponentBeanPostProcessor();
				loadingDdBeans.addBeanPostProcessor(idPostProcessor);
				loadingDdBeans
						.setBeanExpressionResolver(new StandardBeanExpressionResolver());

				// special converters for shorthand map and list property syntax
				GenericConversionService conversionService = new GenericConversionService();
				conversionService.addConverter(new StringMapConverter());
				conversionService.addConverter(new StringListConverter());
				loadingDdBeans.setConversionService(conversionService);

				// expand configuration locations into files
				LOG.info("Starting DD XML File Load");
				XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(
						loadingDdBeans);

				Set<String> allBeanNames = new java.util.HashSet<String>();
				for (String namespaceCode : moduleLoadOrder)
					try {
						List<String> xmls = moduleDictionaryFiles
								.get(namespaceCode);
						LOG.info("DD XML File Load for namespace "
								+ namespaceCode + " " + xmls);
						loadingBeanValidationFiles.addAll(xmls);
						xmlReader.loadBeanDefinitions(xmls
								.toArray(new String[xmls.size()]));
						List<String> added = new java.util.LinkedList<String>();
						for (String bn : loadingDdBeans
								.getBeanDefinitionNames())
							if (allBeanNames.add(bn))
								added.add(bn);
						loadingDdIndex.addBeanNamesToNamespace(namespaceCode,
								added);
						LOG.info("DD XML File Load for namespace "
								+ namespaceCode + " complete.  Added beans "
								+ added);
					} catch (Exception e) {
						throw new DataDictionaryException(
								"Error loading bean definitions", e);
					}

				LOG.info("Completed DD XML File Load");

				assert TL_LOADER.get() == null : TL_LOADER.get();
				try {
					TL_LOADER.set(this);
					// invoke post processing of the dictionary bean definitions
					new DictionaryBeanFactoryPostProcessor(
							KsapDataDictionary.this, loadingDdBeans)
							.postProcessBeanFactory();

					// post processes UIF beans for pulling out expressions
					// within
					// property values
					new UifBeanFactoryPostProcessor()
							.postProcessBeanFactory(loadingDdBeans);

					if (allowConcurrentValidation) {
						KSBThreadPool tp = KSBServiceLocator.getThreadPool();
						tp.execute(loadingDdIndex);
						tp.execute(loadingUifIndex);
					} else {
						loadingDdIndex.run();
						loadingUifIndex.run();
					}

					LOG.info("KRAD Configurer - Validating DD");
					validateDD(true);

					// KULRICE-4513 After the Data Dictionary is loaded and
					// validated,
					// perform Data Dictionary bean overrides.
					performBeanOverrides();
				} finally {
					TL_LOADER.remove();
				}

				if (loader == this || ddBeans == null) {
					ddBeans = loadingDdBeans;
					ddIndex = loadingDdIndex;
					uifIndex = loadingUifIndex;
					beanValidationFiles = loadingBeanValidationFiles;
				}
				loaderError = false;
			} catch (Throwable t) {
				LOG.fatal("Error loading DD from " + moduleLoadOrder + "\n"
						+ moduleDictionaryFiles, t);
				loaderError = true;
			} finally {
				synchronized (this) {
					this.notifyAll();
				}
				if (loader == this) {
					loadingDdBeans = null;
					loadingDdIndex = null;
					loadingUifIndex = null;
					loader = null;
				}
			}
		}
	}

	private synchronized void waitForInit() {
		if (ddBeans == null || ddIndex == null || uifIndex == null
				|| loaderError) {
			if (loader == null)
				KSBServiceLocator.getThreadPool().execute(
						loader = new DataDictionaryLoadTask(false));
			DataDictionaryLoadTask tl;
			long now = System.currentTimeMillis();
			while ((ddBeans == null || ddIndex == null || uifIndex == null || loaderError)
					&& (tl = loader) != null)
				synchronized (tl) {
					try {
						if (System.currentTimeMillis() - now > 120000L)
							throw new IllegalStateException(
									"Timed out waiting for DD view build after 120 seconds");
						tl.wait(1000L);
					} catch (InterruptedException e) {
						throw new IllegalStateException("Interrupted", e);
					}
				}
		}
	}

	@Override
	public void addConfigFileLocation(String namespaceCode, String location)
			throws IOException {
		List<String> cf = moduleDictionaryFiles.get(namespaceCode);
		if (cf != null && cf.contains(location))
			return;
		else
			super.addConfigFileLocation(namespaceCode, location);
	}

	private ConfigurableListableBeanFactory getDataDictionaryBeans() {
		DataDictionaryLoadTask tl = TL_LOADER.get();
		if (tl != null)
			// allow the loader to "pre-publish" within the same thread
			return tl.loadingDdBeans;
		waitForInit();
		return ddBeans;
	}

	private DataDictionaryIndex getDataDictionaryIndex() {
		DataDictionaryLoadTask tl = TL_LOADER.get();
		if (tl != null)
			// allow the loader to "pre-publish" within the same thread
			return tl.loadingDdIndex;
		waitForInit();
		return ddIndex;
	}

	private UifDictionaryIndex getUifIndex() {
		DataDictionaryLoadTask tl = TL_LOADER.get();
		if (tl != null)
			// allow the loader to "pre-publish" within the same thread
			return tl.loadingUifIndex;
		waitForInit();
		return uifIndex;
	}

	/**
	 * Populates and processes the dictionary bean factory based on the
	 * configured files and performs indexing
	 * 
	 * @param allowConcurrentValidation
	 *            - indicates whether the indexing should occur on a different
	 *            thread or the same thread
	 * @deprecated
	 */
	public void parseDataDictionaryConfigurationFiles(
			boolean allowConcurrentValidation) {
		KSBServiceLocator.getThreadPool().execute(
				loader = new DataDictionaryLoadTask(false));
	}

	/**
	 * Populates and processes the dictionary bean factory based on the
	 * configured files
	 * 
	 * @param beans
	 *            - The bean factory for the dictionary bean
	 * @param moduleDictionaryFiles
	 *            - List of bean xml files
	 * @param index
	 *            - Index of the data dictionary beans
	 * @param validationFiles
	 *            - The List of bean xml files loaded into the bean file
	 * @deprecated
	 */
	public void loadDictionaryBeans(ConfigurableListableBeanFactory beans,
			Map<String, List<String>> moduleDictionaryFiles,
			DataDictionaryIndex index, ArrayList<String> validationFiles) {
		throw new UnsupportedOperationException("KSAP-26 REMOVE THIS METHOD");
	}

	/**
	 * Invokes post processors and builds indexes for the beans contained in the
	 * dictionary
	 * 
	 * @param allowConcurrentValidation
	 *            - indicates whether the indexing should occur on a different
	 *            thread or the same thread
	 * @deprecated
	 */
	public void performDictionaryPostProcessing(
			boolean allowConcurrentValidation) {
		throw new UnsupportedOperationException("KSAP-26 REMOVE THIS METHOD");
	}

	public void validateDD(boolean validateEbos) {
		DataDictionary.validateEBOs = validateEbos;

		/*
		 * ValidationController validator = new ValidationController(); String
		 * files[] = new String[beanValidationFiles.size()]; files =
		 * beanValidationFiles.toArray(files); validator.validate(files,
		 * xmlReader.getResourceLoader(), ddBeans, LOG, false);
		 */

		Map<String, DataObjectEntry> doBeans = getDataDictionaryBeans()
				.getBeansOfType(DataObjectEntry.class);
		for (DataObjectEntry entry : doBeans.values()) {
			entry.completeValidation();
		}

		Map<String, DocumentEntry> docBeans = getDataDictionaryBeans()
				.getBeansOfType(DocumentEntry.class);
		for (DocumentEntry entry : docBeans.values()) {
			entry.completeValidation();
		}
	}

	/**
	 * Returns an object from the dictionary by its spring bean name
	 * 
	 * @param beanName
	 *            - id or name for the bean definition
	 * @return Object object instance created or the singleton being maintained
	 */
	public Object getDictionaryObject(String beanName) {
		return getDataDictionaryBeans().getBean(beanName);
	}

	/**
	 * Indicates whether the data dictionary contains a bean with the given id
	 * 
	 * @param id
	 *            - id of the bean to check for
	 * @return boolean true if dictionary contains bean, false otherwise
	 */
	public boolean containsDictionaryObject(String id) {
		return getDataDictionaryBeans().containsBean(id);
	}

	/**
	 * @param className
	 * @return BusinessObjectEntry for the named class, or null if none exists
	 */
	@Deprecated
	public BusinessObjectEntry getBusinessObjectEntry(String className) {
		return ddMapper.getBusinessObjectEntry(getDataDictionaryIndex(),
				className);
	}

	/**
	 * @param className
	 * @return BusinessObjectEntry for the named class, or null if none exists
	 */
	public DataObjectEntry getDataObjectEntry(String className) {
		return ddMapper.getDataObjectEntry(getDataDictionaryIndex(), className);
	}

	/**
	 * This method gets the business object entry for a concrete class
	 * 
	 * @param className
	 * @return
	 */
	public BusinessObjectEntry getBusinessObjectEntryForConcreteClass(
			String className) {
		return ddMapper.getBusinessObjectEntryForConcreteClass(
				getDataDictionaryIndex(), className);
	}

	/**
	 * @return List of businessObject classnames
	 */
	public List<String> getBusinessObjectClassNames() {
		return ddMapper.getBusinessObjectClassNames(getDataDictionaryIndex());
	}

	/**
	 * @return Map of (classname, BusinessObjectEntry) pairs
	 */
	public Map<String, BusinessObjectEntry> getBusinessObjectEntries() {
		return ddMapper.getBusinessObjectEntries(getDataDictionaryIndex());
	}

	/**
	 * @param className
	 * @return DataDictionaryEntryBase for the named class, or null if none
	 *         exists
	 */
	public DataDictionaryEntry getDictionaryObjectEntry(String className) {
		return ddMapper.getDictionaryObjectEntry(getDataDictionaryIndex(),
				className);
	}

	/**
	 * Returns the KNS document entry for the given lookup key. The
	 * documentTypeDDKey is interpreted successively in the following ways until
	 * a mapping is found (or none if found):
	 * <ol>
	 * <li>KEW/workflow document type</li>
	 * <li>business object class name</li>
	 * <li>maintainable class name</li>
	 * </ol>
	 * This mapping is compiled when DataDictionary files are parsed on startup
	 * (or demand). Currently this means the mapping is static, and one-to-one
	 * (one KNS document maps directly to one and only one key).
	 * 
	 * @param documentTypeDDKey
	 *            the KEW/workflow document type name
	 * @return the KNS DocumentEntry if it exists
	 */
	public DocumentEntry getDocumentEntry(String documentTypeDDKey) {
		return ddMapper.getDocumentEntry(getDataDictionaryIndex(),
				documentTypeDDKey);
	}

	/**
	 * Note: only MaintenanceDocuments are indexed by businessObject Class
	 * 
	 * This is a special case that is referenced in one location. Do we need
	 * another map for this stuff??
	 * 
	 * @param businessObjectClass
	 * @return DocumentEntry associated with the given Class, or null if there
	 *         is none
	 */
	public MaintenanceDocumentEntry getMaintenanceDocumentEntryForBusinessObjectClass(
			Class<?> businessObjectClass) {
		return ddMapper.getMaintenanceDocumentEntryForBusinessObjectClass(
				getDataDictionaryIndex(), businessObjectClass);
	}

	public Map<String, DocumentEntry> getDocumentEntries() {
		return ddMapper.getDocumentEntries(getDataDictionaryIndex());
	}

	/**
	 * Returns the View entry identified by the given id
	 * 
	 * @param viewId
	 *            - unique id for view
	 * @return View instance associated with the id
	 */
	public View getViewById(String viewId) {
		return ddMapper.getViewById(getUifIndex(), viewId);
	}

	/**
	 * Returns View instance identified by the view type name and index
	 * 
	 * @param viewTypeName
	 *            - type name for the view
	 * @param indexKey
	 *            - Map of index key parameters, these are the parameters the
	 *            indexer used to index the view initially and needs to identify
	 *            an unique view instance
	 * @return View instance that matches the given index
	 */
	public View getViewByTypeIndex(ViewType viewTypeName,
			Map<String, String> indexKey) {
		return ddMapper.getViewByTypeIndex(getUifIndex(), viewTypeName,
				indexKey);
	}

	/**
	 * Indicates whether a <code>View</code> exists for the given view type and
	 * index information
	 * 
	 * @param viewTypeName
	 *            - type name for the view
	 * @param indexKey
	 *            - Map of index key parameters, these are the parameters the
	 *            indexer used to index the view initially and needs to identify
	 *            an unique view instance
	 * @return boolean true if view exists, false if not
	 */
	public boolean viewByTypeExist(ViewType viewTypeName,
			Map<String, String> indexKey) {
		return ddMapper.viewByTypeExist(getUifIndex(), viewTypeName, indexKey);
	}

	/**
	 * Gets all <code>View</code> prototypes configured for the given view type
	 * name
	 * 
	 * @param viewTypeName
	 *            - view type name to retrieve
	 * @return List<View> view prototypes with the given type name, or empty
	 *         list
	 */
	public List<View> getViewsForType(ViewType viewTypeName) {
		return ddMapper.getViewsForType(getUifIndex(), viewTypeName);
	}

	/**
	 * Retrieves the configured property values for the view bean definition
	 * associated with the given id
	 * 
	 * <p>
	 * Since constructing the View object can be expensive, when metadata only
	 * is needed this method can be used to retrieve the configured property
	 * values. Note this looks at the merged bean definition
	 * </p>
	 * 
	 * @param viewId
	 *            - id for the view to retrieve
	 * @return PropertyValues configured on the view bean definition, or null if
	 *         view is not found
	 */
	public PropertyValues getViewPropertiesById(String viewId) {
		return ddMapper.getViewPropertiesById(getUifIndex(), viewId);
	}

	/**
	 * Retrieves the configured property values for the view bean definition
	 * associated with the given type and index
	 * 
	 * <p>
	 * Since constructing the View object can be expensive, when metadata only
	 * is needed this method can be used to retrieve the configured property
	 * values. Note this looks at the merged bean definition
	 * </p>
	 * 
	 * @param viewTypeName
	 *            - type name for the view
	 * @param indexKey
	 *            - Map of index key parameters, these are the parameters the
	 *            indexer used to index the view initially and needs to identify
	 *            an unique view instance
	 * @return PropertyValues configured on the view bean definition, or null if
	 *         view is not found
	 */
	public PropertyValues getViewPropertiesByType(ViewType viewTypeName,
			Map<String, String> indexKey) {
		return ddMapper.getViewPropertiesByType(getUifIndex(), viewTypeName,
				indexKey);
	}

	/**
	 * Retrieves the list of dictionary bean names that are associated with the
	 * given namespace code
	 * 
	 * @param namespaceCode
	 *            - namespace code to retrieve associated bean names for
	 * @return List<String> bean names associated with the namespace
	 */
	public List<String> getBeanNamesForNamespace(String namespaceCode) {
		List<String> namespaceBeans = new ArrayList<String>();

		Map<String, List<String>> dictionaryBeansByNamespace = getDataDictionaryIndex()
				.getDictionaryBeansByNamespace();
		if (dictionaryBeansByNamespace.containsKey(namespaceCode)) {
			namespaceBeans = dictionaryBeansByNamespace.get(namespaceCode);
		}

		return namespaceBeans;
	}

	/**
	 * Retrieves the namespace code the given bean name is associated with
	 * 
	 * @param beanName
	 *            - name of the dictionary bean to find namespace code for
	 * @return String namespace code the bean is associated with, or null if a
	 *         namespace was not found
	 */
	public String getNamespaceForBeanDefinition(String beanName) {
		String beanNamespace = null;

		Map<String, List<String>> dictionaryBeansByNamespace = getDataDictionaryIndex()
				.getDictionaryBeansByNamespace();
		for (Map.Entry<String, List<String>> moduleDefinitions : dictionaryBeansByNamespace
				.entrySet()) {
			List<String> namespaceBeans = moduleDefinitions.getValue();
			if (namespaceBeans.contains(beanName)) {
				beanNamespace = moduleDefinitions.getKey();
				break;
			}
		}

		return beanNamespace;
	}

	@SuppressWarnings("rawtypes")
	public Set<InactivationBlockingMetadata> getAllInactivationBlockingMetadatas(
			Class blockedClass) {
		return ddMapper.getAllInactivationBlockingMetadatas(
				getDataDictionaryIndex(), blockedClass);
	}

	/**
	 * This method gathers beans of type BeanOverride and invokes each one's
	 * performOverride() method.
	 */
	// KULRICE-4513
	public void performBeanOverrides() {
		Collection<BeanOverride> beanOverrides = getDataDictionaryBeans()
				.getBeansOfType(BeanOverride.class).values();

		if (beanOverrides.isEmpty()) {
			LOG.info("DataDictionary.performOverrides(): No beans to override");
		}
		for (BeanOverride beanOverride : beanOverrides) {

			Object bean = getDataDictionaryBeans().getBean(
					beanOverride.getBeanName());
			beanOverride.performOverride(bean);
			LOG.info("DataDictionary.performOverrides(): Performing override on bean: "
					+ bean.toString());
		}
	}

}
