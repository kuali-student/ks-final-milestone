package org.kuali.student.ap.framework.config;

/**
 * Provides default ks-enroll service behavior as a local Rice module, suitable
 * for bundling with ks-ap when another provider is not available in the
 * environment.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ks-ap-0.1.1
 */
public class KSENROLLConfigurer extends AbstractKsapModuleConfigurer {

	public KSENROLLConfigurer() {
		super(KSAPConstants.KSENROLL_MODULE_NAME);
	}

}
