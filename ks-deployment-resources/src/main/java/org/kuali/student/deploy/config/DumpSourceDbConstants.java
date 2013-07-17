package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.impex.DumpConfigConstants;
import org.kuali.common.jdbc.config.JdbcConfigConstants;
import org.kuali.common.util.Str;
import org.kuali.student.deploy.spring.DeployProjectConstants;

public class DumpSourceDbConstants {

	// Shorthand for GroupId + ArtifactId
	private static final String GA = Str.getId(DeployProjectConstants.GROUP_ID, DeployProjectConstants.ARTIFACT_ID);

	// Config related to dumping something
	// TODO Move to kuali-impex-export?
	public static final String DUMP = "dump";

	// Context id for config related to dumping a source database
	// TODO Move to kuali-impex-export?
	public static final String SOURCE_DB_CONTEXT_ID = Str.getId(SourceDbConstants.SOURCE_DB, DUMP);

	// Fully qualified config id related to dumping KS_SOURCE_DB
	public static final String SOURCE_DB_CONFIG_ID = Str.getId(GA, SOURCE_DB_CONTEXT_ID);

	// The complete list of configId's needed to dump KS_SOURCE_DB
	public static final List<String> SOURCE_DB_CONFIG_IDS = getSourceDbConfigIds();

	protected static List<String> getSourceDbConfigIds() {
		List<String> ids = new ArrayList<String>();

		// Add whatever kuali-jdbc says it needs
		ids.addAll(JdbcConfigConstants.CONFIG_IDS);

		// Add whatever kuali-impex-export says it needs
		ids.addAll(DumpConfigConstants.CONFIG_IDS);

		// KS launches this via the Maven CLI as a build process
		ids.add(DumpConfigConstants.BUILD_CONFIG_ID);

		// KS specific config for connecting to Amazon RDS
		ids.add(SOURCE_DB_CONFIG_ID);
		return Collections.unmodifiableList(ids);
	}

}
