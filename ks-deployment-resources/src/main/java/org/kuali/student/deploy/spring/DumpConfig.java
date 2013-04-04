package org.kuali.student.deploy.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DumpConfig {

	public static final String KS_IMPEX_RICE_ARTIFACT_ID = "ks-impex-rice-db";
	public static final String KS_IMPEX_APP_ARTIFACT_ID = "ks-impex-app-db";
	public static final String KS_IMPEX_BUNDLED_ARTIFACT_ID = "ks-impex-bundled-db";

	@Bean
	public void whatever() {

	}

}
