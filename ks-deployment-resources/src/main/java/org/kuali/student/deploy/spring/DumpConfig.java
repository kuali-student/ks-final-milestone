package org.kuali.student.deploy.spring;

import java.io.File;

import org.kuali.common.impex.ImpexContextCloningFactoryBean;
import org.kuali.common.impex.service.ImpexContext;
import org.kuali.common.util.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DumpConfig {

	private static final String FS = File.separator;

	public static final String KS_IMPEX_RICE_ARTIFACT_ID = "ks-impex-rice-db";
	public static final String KS_IMPEX_APP_ARTIFACT_ID = "ks-impex-app-db";
	public static final String KS_IMPEX_BUNDLED_ARTIFACT_ID = "ks-impex-bundled-db";

	@Autowired
	Environment env;

	@Bean
	public ImpexContext riceDumpContext() {

		String artifactId = KS_IMPEX_RICE_ARTIFACT_ID;
		String include = SpringUtils.getProperty(env, "impex.rice.include");

		String finalDirName = getFinalDirName(env, artifactId);
		File finalDirectory = new File(finalDirName);
		String schemaFileInclude = "**/" + artifactId + "*";

		ImpexContextCloningFactoryBean bean = new ImpexContextCloningFactoryBean();
		bean.setArtifactId(KS_IMPEX_RICE_ARTIFACT_ID);
		bean.setFinalDirectory(finalDirectory);
		bean.setInclude(include);
		bean.setCopyDataFiles(true);
		bean.setSchemaFileInclude(schemaFileInclude);
		return bean.getObject();
	}

	protected String getFinalDirName(Environment env, String artifactId) {
		StringBuilder sb = new StringBuilder();
		sb.append(SpringUtils.getProperty(env, "project.basedir"));
		sb.append(FS);
		sb.append("src");
		sb.append(FS);
		sb.append("main");
		sb.append(FS);
		sb.append("resources");
		return sb.toString();
	}
}