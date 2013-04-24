package org.kuali.student.ap.framework.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.datadictionary.DataDictionaryException;
import org.kuali.rice.krad.datadictionary.uif.UifViewPool;
import org.kuali.rice.krad.datadictionary.uif.ViewTypeDictionaryIndex;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifConstants.ViewType;
import org.kuali.rice.krad.uif.service.ViewTypeService;
import org.kuali.rice.krad.uif.util.ViewModelUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.ksb.service.KSBServiceLocator;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Indexes {@code View} bean entries for retrieval
 * 
 * <p>
 * This is used to retrieve a {@code View} instance by its unique id.
 * Furthermore, view of certain types (that have a {@code ViewTypeService} are
 * indexed by their type to support retrieval of views based on parameters.
 * </p>
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 * @deprecated TODO KSAP-26 move to Rice. Modified to use configured thread pool
 *             instead of ad-hoc for index building.
 */
public class UifDictionaryIndex implements Runnable {
	private static final Log LOG = LogFactory.getLog(UifDictionaryIndex.class);

	private ConfigurableListableBeanFactory ddBeans;

	// view entries keyed by view id with value the spring bean name
	private Map<String, String> viewBeanEntriesById;

	// view entries indexed by type
	private Map<String, ViewTypeDictionaryIndex> viewEntriesByType;

	// views that are loaded eagerly
	private Map<String, UifViewPool> viewPools;

	// threadpool size
	private static final int THREADS = 4;
	private boolean poolSizeSet;
	private Integer threadPoolSize;

	public UifDictionaryIndex(ConfigurableListableBeanFactory ddBeans) {
		this.ddBeans = ddBeans;
	}

	@Override
	public void run() {
		LOG.info("Starting View Index Building");
		try {
			Integer size = new Integer(ConfigContext.getCurrentContextConfig()
					.getProperty(KRADConstants.KRAD_DICTIONARY_INDEX_POOL_SIZE));
			threadPoolSize = size;
			poolSizeSet = true;
		} catch (NumberFormatException nfe) {
			// ignore this, instead the pool will be set to DEFAULT_SIZE
		}
		synchronized (KsapDataDictionary.DD_MUTEX) {
			buildViewIndicies();
		}
		LOG.info("View Index Build Processing Started");
	}

	/**
	 * Retrieves the View instance with the given id
	 * 
	 * <p>
	 * First an attempt is made to get a preloaded view (if one exists). If
	 * found it is pulled from the pool and a replacement is built on another
	 * thread. If a preloaded view does not exist, one is built by Spring from
	 * the bean factory
	 * </p>
	 * 
	 * @param viewId
	 *            - the unique id for the view
	 * @return View instance with the given id
	 * @throws org.kuali.rice.krad.datadictionary.DataDictionaryException
	 *             if view doesn't exist for id
	 */
	public View getViewById(final String viewId) {
		boolean poolok;
		try {
			waitForViewBuild();
			poolok = true;
		} catch (Throwable e) {
			LOG.error("Error waiting for prebuild view, defaulting to factory",
					e);
			poolok = false;
		}
		if (poolok && viewPools.containsKey(viewId)) {
			final UifViewPool viewPool = viewPools.get(viewId);
			synchronized (viewPool) {
				if (!viewPool.isEmpty()) {
					View view = viewPool.getViewInstance();
					buildQueue.offer(new ViewBuildTask(viewId, viewPool));
					spawnViewBuildWorkers();
					return view;
				} else {
					LOG.info("Pool size for view with id: "
							+ viewId
							+ " is empty. Considering increasing max pool size.");
				}
			}
		}

		// no pooling, get new instance from factory
		return getViewInstanceFromFactory(viewId);
	}

	/**
	 * Retrieves a view object from the bean factory based on view id
	 * 
	 * @param viewId
	 *            - id of the view to retrieve
	 * @return View instance for view with specified id
	 * @throws org.kuali.rice.krad.datadictionary.DataDictionaryException
	 *             if view doesn't exist for id
	 */
	protected View getViewInstanceFromFactory(String viewId) {
		String beanName = viewBeanEntriesById.get(viewId);
		if (StringUtils.isBlank(beanName)) {
			throw new DataDictionaryException("Unable to find View with id: "
					+ viewId);
		}
		synchronized (ddBeans) {
			return ddBeans.getBean(beanName, View.class);
		}
	}

	/**
	 * Retrieves a {@code View} instance that is of the given type based on the
	 * index key
	 * 
	 * @param viewTypeName
	 *            - type name for the view
	 * @param indexKey
	 *            - Map of index key parameters, these are the parameters the
	 *            indexer used to index the view initially and needs to identify
	 *            an unique view instance
	 * @return View instance that matches the given index or Null if one is not
	 *         found
	 */
	public View getViewByTypeIndex(ViewType viewTypeName,
			Map<String, String> indexKey) {
		String index = buildTypeIndex(indexKey);

		ViewTypeDictionaryIndex typeIndex = getTypeIndex(viewTypeName);

		String viewId = typeIndex.get(index);
		if (StringUtils.isNotBlank(viewId)) {
			return getViewById(viewId);
		}

		return null;
	}

	/**
	 * Indicates whether a {@code View} exists for the given view type and index
	 * information
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
		boolean viewExist = false;

		String index = buildTypeIndex(indexKey);
		ViewTypeDictionaryIndex typeIndex = getTypeIndex(viewTypeName);

		String viewId = typeIndex.get(index);
		if (StringUtils.isNotBlank(viewId)) {
			viewExist = true;
		}

		return viewExist;
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
		String beanName = viewBeanEntriesById.get(viewId);
		if (StringUtils.isBlank(beanName)) {
			BeanDefinition beanDefinition = ddBeans
					.getMergedBeanDefinition(beanName);

			return beanDefinition.getPropertyValues();
		}

		return null;
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
		String index = buildTypeIndex(indexKey);

		ViewTypeDictionaryIndex typeIndex = getTypeIndex(viewTypeName);

		String beanName = typeIndex.get(index);
		if (StringUtils.isNotBlank(beanName)) {
			BeanDefinition beanDefinition = ddBeans
					.getMergedBeanDefinition(beanName);

			return beanDefinition.getPropertyValues();
		}

		return null;
	}

	/**
	 * Gets all {@code View} prototypes configured for the given view type name
	 * 
	 * @param viewTypeName
	 *            - view type name to retrieve
	 * @return List<View> view prototypes with the given type name, or empty
	 *         list
	 */
	public List<View> getViewsForType(ViewType viewTypeName) {
		List<View> typeViews = new ArrayList<View>();

		// get view ids for the type
		if (viewEntriesByType.containsKey(viewTypeName.name())) {
			ViewTypeDictionaryIndex typeIndex = viewEntriesByType
					.get(viewTypeName.name());
			for (Entry<String, String> typeEntry : typeIndex.getViewIndex()
					.entrySet()) {
				View typeView;
				synchronized (KsapDataDictionary.DD_MUTEX) {
					typeView = ddBeans
							.getBean(typeEntry.getValue(), View.class);
				}
				typeViews.add(typeView);
			}
		} else {
			throw new DataDictionaryException(
					"Unable to find view index for type: " + viewTypeName);
		}

		return typeViews;
	}

	private class ViewBuildTask implements Runnable {
		private final String beanName;
		private final UifViewPool viewPool;

		private ViewBuildTask(String beanName, UifViewPool viewPool) {
			this.beanName = beanName;
			this.viewPool = viewPool;
		}

		@Override
		public void run() {
			View view;
			synchronized (KsapDataDictionary.DD_MUTEX) {
				view = (View) ddBeans.getBean(beanName);
			}
			viewPool.addViewInstance(view);
		}

		@Override
		public String toString() {
			return "ViewBuildTask [beanName=" + beanName + ", viewPool="
					+ viewPool + "]";
		}
	}

	private class ViewBuildWorker implements Runnable {

		private ViewBuildWorker() {
			activeWorkerCount++;
		}

		private void discard() {
			activeWorkerCount--;
		}

		@Override
		public void run() {
			try {
				for (;;) {
					ViewBuildTask todo;
					synchronized (buildQueue) {
						if (!buildQueue.isEmpty())
							todo = buildQueue.poll();
						else {
							LOG.info("View Index Build Processing Complete");
							return;
						}
					}
					try {
						todo.run();
						LOG.info("Processed View Index Build " + todo);
					} catch (Throwable t) {
						LOG.fatal("Error building KRAD view " + todo, t);
					}
					synchronized (buildQueue) {
						buildQueue.notifyAll();
					}
				}
			} finally {
				discard();
				try {
					synchronized (buildQueue) {
						buildQueue.notifyAll();
					}
				} catch (Throwable t) {
					LOG.error("Error notifying view build worker shutdown", t);
				}
			}
		}

	}

	private int activeWorkerCount;
	private Queue<ViewBuildTask> buildQueue = new ConcurrentLinkedQueue<ViewBuildTask>();

	/**
	 * Initializes the view index {@code Map} then iterates through all the
	 * beans in the factory that implement {@code View}, adding them to the
	 * index
	 */
	private void buildViewIndicies() {
		viewBeanEntriesById = new HashMap<String, String>();
		viewEntriesByType = new HashMap<String, ViewTypeDictionaryIndex>();
		viewPools = new HashMap<String, UifViewPool>();

		String[] beanNames = ddBeans.getBeanNamesForType(View.class);
		for (final String beanName : beanNames) {
			BeanDefinition beanDefinition = ddBeans
					.getMergedBeanDefinition(beanName);
			PropertyValues propertyValues = beanDefinition.getPropertyValues();

			String id = ViewModelUtils
					.getStringValFromPVs(propertyValues, "id");
			if (StringUtils.isBlank(id)) {
				id = beanName;
			}

			if (viewBeanEntriesById.containsKey(id)) {
				throw new DataDictionaryException(
						"Two views must not share the same id. Found duplicate id: "
								+ id);
			}
			viewBeanEntriesById.put(id, beanName);

			indexViewForType(propertyValues, id);

			// pre-load views if necessary
			String poolSizeStr = ViewModelUtils.getStringValFromPVs(
					propertyValues, "preloadPoolSize");
			if (StringUtils.isNotBlank(poolSizeStr)) {
				int poolSize = Integer.parseInt(poolSizeStr);
				if (poolSize < 1) {
					continue;
				}

				final UifViewPool viewPool = new UifViewPool();
				viewPool.setMaxSize(poolSize);
				for (int j = 0; j < poolSize; j++)
					buildQueue.offer(new ViewBuildTask(beanName, viewPool));
				viewPools.put(id, viewPool);
			}
			spawnViewBuildWorkers();
		}
	}

	private void spawnViewBuildWorkers() {
		ExecutorService executor = KSBServiceLocator.getThreadPool();
		int threads = Math.min(buildQueue.size(), poolSizeSet ? threadPoolSize
				: THREADS);
		for (int i = activeWorkerCount; i < threads; i++) {
			ViewBuildWorker worker = new ViewBuildWorker();
			try {
				executor.execute(worker);
			} catch (Throwable t) {
				worker.discard();
				if (t instanceof RuntimeException)
					throw (RuntimeException) t;
				if (t instanceof Error)
					throw (Error) t;
				throw new IllegalStateException(t);
			}
		}
	}

	void cancelActiveBuilders() {
		buildQueue.clear();
	}

	private void waitForViewBuild() {
		long now = System.currentTimeMillis();
		if (activeWorkerCount < 1 && !buildQueue.isEmpty())
			spawnViewBuildWorkers();
		if (!buildQueue.isEmpty())
			LOG.info("View builders are active, pausing for completion "
					+ buildQueue.size() + " queued, " + activeWorkerCount
					+ " working");
		while (!buildQueue.isEmpty()) {
			if (System.currentTimeMillis() - now > 5000L)
				throw new IllegalStateException(
						"Timed out waiting for DD view build after 5 seconds");
			synchronized (buildQueue) {
				try {
					buildQueue.wait(250L);
				} catch (InterruptedException e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}

	/**
	 * Performs additional indexing based on the view type associated with the
	 * view instance. The {@code ViewTypeService} associated with the view type
	 * name on the instance is invoked to retrieve the parameter key/value pairs
	 * from the configured property values, which are then used to build up an
	 * index used to key the entry
	 * 
	 * @param propertyValues
	 *            - property values configured on the view bean definition
	 * @param id
	 *            - id (or bean name if id was not set) for the view
	 */
	protected void indexViewForType(PropertyValues propertyValues, String id) {
		String viewTypeName = ViewModelUtils.getStringValFromPVs(
				propertyValues, "viewTypeName");
		if (StringUtils.isBlank(viewTypeName)) {
			return;
		}

		UifConstants.ViewType viewType = ViewType.valueOf(viewTypeName);

		ViewTypeService typeService = KRADServiceLocatorWeb.getViewService()
				.getViewTypeService(viewType);
		if (typeService == null) {
			// don't do any further indexing
			return;
		}

		// invoke type service to retrieve it parameter name/value pairs
		Map<String, String> typeParameters = typeService
				.getParametersFromViewConfiguration(propertyValues);

		// build the index string from the parameters
		String index = buildTypeIndex(typeParameters);

		// get the index for the type and add the view entry
		ViewTypeDictionaryIndex typeIndex = getTypeIndex(viewType);

		typeIndex.put(index, id);
	}

	/**
	 * Retrieves the {@code ViewTypeDictionaryIndex} instance for the given view
	 * type name. If one does not exist yet for the given name, a new instance
	 * is created
	 * 
	 * @param viewType
	 *            - name of the view type to retrieve index for
	 * @return ViewTypeDictionaryIndex instance
	 */
	protected ViewTypeDictionaryIndex getTypeIndex(
			UifConstants.ViewType viewType) {
		ViewTypeDictionaryIndex typeIndex = null;

		if (viewEntriesByType.containsKey(viewType.name())) {
			typeIndex = viewEntriesByType.get(viewType.name());
		} else {
			typeIndex = new ViewTypeDictionaryIndex();
			viewEntriesByType.put(viewType.name(), typeIndex);
		}

		return typeIndex;
	}

	/**
	 * Builds up an index string from the given Map of parameters
	 * 
	 * @param typeParameters
	 *            - Map of parameters to use for index
	 * @return String index
	 */
	protected String buildTypeIndex(Map<String, String> typeParameters) {
		String index = "";

		for (String parameterName : typeParameters.keySet()) {
			if (StringUtils.isNotBlank(index)) {
				index += "|||";
			}
			index += parameterName + "^^" + typeParameters.get(parameterName);
		}

		return index;
	}

}
