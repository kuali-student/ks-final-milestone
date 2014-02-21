package org.kuali.student.ap.framework.config;

/**
 * Framework level constants for the KSAP module.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ksap-0.1.1
 */
public final class KSAPConstants {

	public static final String KS_NAMESPACE_PREFIX = "http://student.kuali.org/wsdl";
	public static final String KSAP_PACKAGE_CONFIG_PATH = "classpath:/META-INF/ks-ap/";

	public static final String KSAP_MODULE_NAME = "ksap";
	public static final String KSAP_NAMESPACE_PREFIX = KS_NAMESPACE_PREFIX
			+ "/" + KSAP_MODULE_NAME;

	public static final String KSCORE_MODULE_NAME = "kscore";
	public static final String KSENROLL_MODULE_NAME = "ksenroll";
	public static final String KSLUM_MODULE_NAME = "kslum";

}
