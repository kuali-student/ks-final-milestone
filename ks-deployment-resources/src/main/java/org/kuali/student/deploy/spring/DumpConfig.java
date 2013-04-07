package org.kuali.student.deploy.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.kuali.common.impex.ImpexContextCloningFactoryBean;
import org.kuali.common.impex.SyncFilesExecutable;
import org.kuali.common.impex.service.ImpexContext;
import org.kuali.common.impex.spring.ImpexContextDumpConfig;
import org.kuali.common.impex.spring.ImpexDumpConfig;
import org.kuali.common.util.service.ScmService;
import org.kuali.common.util.spring.ScmServiceFactoryBean;
import org.kuali.common.util.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
@Import({ ImpexDumpConfig.class })
public class DumpConfig implements ImpexContextDumpConfig {

	private static final String FS = File.separator;

	public static final String KS_IMPEX_RICE_ARTIFACT_ID = "ks-impex-rice-db";
	public static final String KS_IMPEX_APP_ARTIFACT_ID = "ks-impex-app-db";
	public static final String KS_IMPEX_BUNDLED_ARTIFACT_ID = "ks-impex-bundled-db";

	@Autowired
	Environment env;

	@Autowired
	ImpexDumpConfig impexDumpConfig;

	@Bean
	public ImpexContext riceDumpContext() {
		String include = SpringUtils.getProperty(env, "impex.rice.includes");
		return getBaseContext(env, KS_IMPEX_RICE_ARTIFACT_ID, include);
	}

	@Bean
	public ImpexContext appDumpContext() {
		String include = SpringUtils.getProperty(env, "impex.ks.includes");
		return getBaseContext(env, KS_IMPEX_APP_ARTIFACT_ID, include);
	}

	@Bean
	public ImpexContext bundledDumpContext() {
		String riceInclude = SpringUtils.getProperty(env, "impex.rice.includes");
		String ksInclude = SpringUtils.getProperty(env, "impex.ks.includes");
		String include = riceInclude + "," + ksInclude;
		ImpexContext context = getBaseContext(env, KS_IMPEX_BUNDLED_ARTIFACT_ID, include);
		// Bundled is the combination of Rice + App
		// No data files are physically checked into SCM for bundled
		// It just references the data files from Rice + App
		// Thus, there are no data files to copy for bundled
		context.setCopyDataFiles(false);
		return context;
	}

	@Bean
	public ScmService scmService() {
		String url = SpringUtils.getProperty(env, "project.scm.developerConnection");
		ScmServiceFactoryBean factory = new ScmServiceFactoryBean();
		factory.setUrl(url);
		return factory.getObject();
	}

	@Override
	@Bean
	public List<ImpexContext> dumpContexts() {
		List<ImpexContext> contexts = new ArrayList<ImpexContext>();
		contexts.add(riceDumpContext());
		contexts.add(appDumpContext());
		contexts.add(bundledDumpContext());
		return contexts;
	}

	@Bean(initMethod = "execute")
	public SyncFilesExecutable syncExecutable() {
		SyncFilesExecutable sfe = new SyncFilesExecutable();
		sfe.setService(scmService());
		sfe.setContexts(dumpContexts());
		// Skip everything related to sync'ing files
		sfe.setSkip(SpringUtils.getBoolean(env, "impex.sync.skip", false));
		// Skip just the portion that commits changed files to the SCM system
		sfe.setCommitChanges(SpringUtils.getBoolean(env, "impex.scm.commit", false));
		return sfe;
	}

	protected ImpexContext getBaseContext(Environment env, String artifactId, String include) {
		String finalDirName = getFinalDirName(env, artifactId);
		File finalDirectory = new File(finalDirName);
		String schemaFileInclude = "**/" + artifactId + "*";

		ImpexContextCloningFactoryBean factory = new ImpexContextCloningFactoryBean();
		factory.setArtifactId(artifactId);
		factory.setFinalDirectory(finalDirectory);
		factory.setInclude(include);
		factory.setCopyDataFiles(true);
		factory.setSchemaFileInclude(schemaFileInclude);
		factory.setSourceContext(impexDumpConfig.impexSourceContext());
		return factory.getObject();
	}

	protected String getFinalDirName(Environment env, String artifactId) {
		StringBuilder sb = new StringBuilder();
		sb.append(SpringUtils.getProperty(env, "project.basedir"));
		sb.append(FS);
		sb.append(artifactId);
		sb.append(FS);
		sb.append("src");
		sb.append(FS);
		sb.append("main");
		sb.append(FS);
		sb.append("resources");
		return sb.toString();
	}
}