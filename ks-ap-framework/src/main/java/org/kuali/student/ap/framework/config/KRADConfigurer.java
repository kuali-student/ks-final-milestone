package org.kuali.student.ap.framework.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.kuali.rice.core.api.config.module.RunMode;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.ksb.service.KSBServiceLocator;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;

public class KRADConfigurer extends AbstractKsapModuleConfigurer implements
		SmartApplicationListener {

	private static KRADConfigurer instance;

	private DataSource applicationDataSource;

	private boolean includeKnsSpringBeans;

	private static final String KRAD_BOOTSTRAP_SPRING = "classpath:META-INF/ks-ap/krad-bootstrap.xml";
	private static final String KRAD_SPRING_BEANS_PATH = "classpath:org/kuali/rice/krad/config/KRADSpringBeans.xml";
	private static final String KNS_SPRING_BEANS_PATH = "classpath:org/kuali/rice/kns/config/KNSSpringBeans.xml";
	private static final String KRAD_OVERRIDE_SPRING = "classpath:META-INF/ks-ap/krad-override.xml";

	static KRADConfigurer getActiveInstance() {
		assert instance != null;
		return instance;
	}

	/**
	 * The application may specify additional UI components for the KRAD module
	 * to load by providing this {@link Lifecycle} resource.
	 */
	@Resource
	private Lifecycle additionalComponents;

	public KRADConfigurer() {
		// TODO KSAP-739: Rice Trackback KULRICE-6532
		// Constant value should be "krad"
		super(KRADConstants.KR_MODULE_NAME);
		assert instance == null : "Already created " + instance;
		instance = this;
		setValidRunModes(Arrays.asList(RunMode.LOCAL));
		setIncludeKnsSpringBeans(true);
	}

	@Override
	public void addAdditonalToConfig() {
		configureDataSource();
		assert additionalComponents != null;
	}

	@Override
	public List<String> getPrimarySpringFiles() {
		final List<String> springFileLocations = new ArrayList<String>();
		springFileLocations.add(KRAD_BOOTSTRAP_SPRING);
		LOG.info("KSAP-26 KRAD autowiring bootstrap " + KRAD_BOOTSTRAP_SPRING);
		springFileLocations.add(KRAD_SPRING_BEANS_PATH);

		if (isIncludeKnsSpringBeans()) {
			springFileLocations.add(KNS_SPRING_BEANS_PATH);
		}
		springFileLocations.add(KRAD_OVERRIDE_SPRING);
		return springFileLocations;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		if (applicationEvent instanceof ContextRefreshedEvent) {
			boolean re = additionalComponents.isStarted();
			try {
				if (re)
					additionalComponents.stop();
				additionalComponents.start();
			} catch (Exception e) {
				throw new IllegalStateException("Error " + (re ? "re" : "")
						+ "starting additional components", e);
			}
			loadDataDictionary();
			publishDataDictionaryComponents();
		}
	}

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
		return true;
	}

	@Override
	public boolean supportsSourceType(Class<?> aClass) {
		return true;
	}

	@Override
	public int getOrder() {
		// return a lower value which will give the data dictionary indexing
		// higher precedence since DD indexing should
		// be started as soon as it can be
		return -1000;
	}

	/**
	 * Used to "poke" the Data Dictionary again after the Spring Context is
	 * initialized. This is to allow for modules loaded with KualiModule after
	 * the KNS has already been initialized to work.
	 * 
	 * Also initializes the DateTimeService
	 */
	protected void loadDataDictionary() {
		if (isLoadDataDictionary()) {
			LOG.info("KRAD Configurer - Loading DD");
			DataDictionaryService dds = KRADServiceLocatorWeb
					.getDataDictionaryService();
			if (dds == null) {
				dds = (DataDictionaryService) GlobalResourceLoader
						.getService(KRADServiceLocatorWeb.DATA_DICTIONARY_SERVICE);
			}
			dds.getDataDictionary()
					.parseDataDictionaryConfigurationFiles(false);
		}
	}

	protected void publishDataDictionaryComponents() {
		if (isComponentPublishingEnabled()) {
			long delay = getComponentPublishingDelay();
			LOG.info("Publishing of Data Dictionary components is enabled, scheduling publish after "
					+ delay + " millisecond delay");
			KSBServiceLocator.getScheduledPool().scheduleAtFixedRate(
					new Runnable() {
						@Override
						public void run() {
							long start = System.currentTimeMillis();
							LOG.info("Executing scheduled Data Dictionary component publishing...");
							try {

							} catch (RuntimeException e) {
								LOG.error(
										"Failed to publish data dictionary components.",
										e);
								throw e;
							} finally {
								long end = System.currentTimeMillis();
								LOG.info("... finished scheduled execution of Data Dictionary component publishing.  Took "
										+ (end - start) + " milliseconds");
							}
						}
					}, delay, delay, TimeUnit.MILLISECONDS);
		}
	}

	@Override
	public boolean hasWebInterface() {
		return true;
	}

	/**
	 * Returns true - KNS UI should always be included.
	 * 
	 * @see org.kuali.rice.core.framework.config.module.ModuleConfigurer#shouldRenderWebInterface()
	 */
	@Override
	public boolean shouldRenderWebInterface() {
		return true;
	}

	public boolean isLoadDataDictionary() {
		return ConfigContext.getCurrentContextConfig().getBooleanProperty(
				"load.data.dictionary", true);
	}

	public boolean isValidateDataDictionary() {
		return ConfigContext.getCurrentContextConfig().getBooleanProperty(
				"validate.data.dictionary", false);
	}

	public boolean isValidateDataDictionaryEboReferences() {
		return ConfigContext.getCurrentContextConfig().getBooleanProperty(
				"validate.data.dictionary.ebo.references", false);
	}

	public boolean isComponentPublishingEnabled() {
		return ConfigContext.getCurrentContextConfig().getBooleanProperty(
				KRADConstants.Config.COMPONENT_PUBLISHING_ENABLED, false);
	}

	public long getComponentPublishingDelay() {
		return ConfigContext.getCurrentContextConfig().getNumericProperty(
				KRADConstants.Config.COMPONENT_PUBLISHING_DELAY, 0);
	}

	/**
	 * Used to "poke" the Data Dictionary again after the Spring Context is
	 * initialized. This is to allow for modules loaded with KualiModule after
	 * the KNS has already been initialized to work.
	 * 
	 * Also initializes the DateTimeService
	 */
	protected void configureDataSource() {
		if (getApplicationDataSource() != null) {
			ConfigContext.getCurrentContextConfig().putObject(
					KRADConstants.KRAD_APPLICATION_DATASOURCE,
					getApplicationDataSource());
		}
	}

	public DataSource getApplicationDataSource() {
		return this.applicationDataSource;
	}

	public void setApplicationDataSource(DataSource applicationDataSource) {
		this.applicationDataSource = applicationDataSource;
	}

	/**
	 * Indicates whether the legacy KNS module should be included which will
	 * include the KNS spring beans file
	 * 
	 * @return boolean true if kns should be supported, false if not
	 */
	public boolean isIncludeKnsSpringBeans() {
		return includeKnsSpringBeans;
	}

	/**
	 * Setter for the include kns support indicator
	 * 
	 * @param includeKnsSpringBeans
	 */
	public void setIncludeKnsSpringBeans(boolean includeKnsSpringBeans) {
		this.includeKnsSpringBeans = includeKnsSpringBeans;
	}
}
