package org.kuali.student.ap.framework.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.rice.core.api.config.module.RunMode;
import org.kuali.rice.core.framework.config.module.ModuleConfigurer;

/**
 * Provides default ks-core service behavior as a local Rice module, suitable
 * for bundling with ks-ap when another provider is not available in the
 * environment.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ks-ap-0.1.1
 */
public class KSCOREConfigurer extends ModuleConfigurer {

	public KSCOREConfigurer() {
		super(KSAPConstants.KSCORE_MODULE_NAME);
		setValidRunModes(Arrays.asList(RunMode.LOCAL, RunMode.EMBEDDED,
				RunMode.REMOTE));
	}

	@Override
	protected String getDefaultConfigPackagePath() {
		return KSAPConstants.KSAP_PACKAGE_CONFIG_PATH;
	}

	@Override
	public List<String> getPrimarySpringFiles() {
		List<String> springFileLocations = new ArrayList<String>();
		springFileLocations.add(getDefaultConfigPackagePath() + "ks-core-"
				+ getRunMode().name().toLowerCase() + ".xml");
		return springFileLocations;
	}

}
