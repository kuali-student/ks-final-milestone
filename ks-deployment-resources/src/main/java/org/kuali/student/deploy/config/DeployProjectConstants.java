package org.kuali.student.deploy.config;

import org.kuali.common.util.project.KualiProjectConstants;
import org.kuali.common.util.project.model.ProjectIdentifier;

public abstract class DeployProjectConstants {

	// These 2 must always exactly match the pom
	private static final String GID = KualiProjectConstants.STUDENT_GROUP_ID;
	private static final String AID = "ks-deployment-resources";

	public static final ProjectIdentifier PROJECT_ID = new ProjectIdentifier(GID, AID);

	@Deprecated
	public static final String GROUP_ID = GID;

	@Deprecated
	public static final String ARTIFACT_ID = AID;

}
