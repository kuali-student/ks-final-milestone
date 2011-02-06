package org.kuali.maven.common;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.project.MavenProject;

public class UrlBuilder {
    private static final Log log = LogFactory.getLog(UrlBuilder.class);

    protected boolean isTargetGroupId(final MavenProject project, final String organizationGroupId) {
        // Return false if we can't do a meaningful comparison
        if (project == null || organizationGroupId == null) {
            return false;
        }

        // Check that it matches our target groupId
        return organizationGroupId.equals(project.getGroupId());
    }

    protected String getTrimmedGroupId(final MavenProject project, final String targetGroupId) {
        String groupId = project.getGroupId();
        if (StringUtils.isEmpty(targetGroupId)) {
            return groupId;
        }
        if (!groupId.startsWith(targetGroupId)) {
            log.warn("Group Id: '" + groupId + "' does not start with '" + targetGroupId + "'");
            return groupId;
        }
        String s = StringUtils.replace(groupId, targetGroupId, "");
        if (s.startsWith(".")) {
            s = s.substring(1);
        }
        s = s.replace(".", "/");
        return s;
    }

    protected boolean isAppendArtifactId(final MavenProject project, final String trimmedGroupId) {
        // Always append the artifactId for single module projects
        if (isEmpty(project.getModules())) {
            return true;
        }

        // Always append the artifactId if it is different than the final portion of the groupId
        String artifactId = project.getArtifactId();
        if (!trimmedGroupId.endsWith(artifactId)) {
            return true;
        }

        /**
         * We have a multi-module build where the artifact id for the top level project is the same as the final portion
         * of the group id.<br>
         * eg org.kuali.rice:rice Return false here so the public url is:<br>
         * http://site.kuali.org/rice/1.0.3<br>
         * instead of<br>
         * http://site.kuali.org/rice/rice/1.0.3<br>
         */
        return false;
    }

    public String getSitePath(final MavenProject project, final String organizationGroupId) {
        String trimmedGroupId = getTrimmedGroupId(project, organizationGroupId);
        StringBuilder sb = new StringBuilder(trimmedGroupId);
        if (sb.length() > 0) {
            sb.append("/");
        }
        if (isAppendArtifactId(project, trimmedGroupId)) {
            sb.append(project.getArtifactId() + "/");
        }
        sb.append(project.getVersion());
        return sb.toString();
    }

    protected boolean isEmpty(final Collection<?> c) {
        if (c == null) {
            return true;
        }
        if (c.isEmpty()) {
            return true;
        }
        return false;
    }

    protected String getBaseUrl(final String protocol, final String hostname, final MavenProject project,
            final SiteContext context) {
        StringBuilder sb = new StringBuilder();
        sb.append(protocol);
        sb.append("://");
        sb.append(hostname);
        sb.append("/");
        sb.append(getSitePath(project, context.getOrganizationGroupId()));
        sb.append("/");
        return sb.toString();
    }

    protected boolean isBaseCase(final MavenProject project, final String organizationGroupId) {
        // If this is the targeted parent pom return true
        // This happens when using Maven to generate a site about the Kuali parent pom itself
        if (isTargetGroupId(project, organizationGroupId)) {
            return true;
        }

        // Otherwise, inspect the parent project
        MavenProject parent = project.getParent();

        // If there is no parent, return true
        if (parent == null) {
            return true;
        }

        // If the parent is the targeted parent pom, return true
        if (isTargetGroupId(parent, organizationGroupId)) {
            return true;
        }

        // False otherwise
        return false;
    }

    public String getPublicUrl(final MavenProject project, final SiteContext context) {
        String organizationGroupId = context.getOrganizationGroupId();
        if (isBaseCase(project, organizationGroupId)) {
            return getBaseUrl(context.getPublicUrlProtocol(), context.getHostname(), project, context);
        } else {
            return getPublicUrl(project.getParent(), context) + project.getArtifactId() + "/";
        }
    }

    public String getPublishUrl(final MavenProject project, final SiteContext context) {
        String organizationGroupId = context.getOrganizationGroupId();
        if (isBaseCase(project, organizationGroupId)) {
            return getBaseUrl(context.getPublishUrlProtocol(), context.getBucket(), project, context);
        } else {
            return getPublishUrl(project.getParent(), context) + project.getArtifactId() + "/";
        }
    }

    public boolean isSnapshot(final MavenProject project) {
        String version = project.getVersion();
        int pos = version.toUpperCase().indexOf("SNAPSHOT");
        boolean isSnapshot = pos != -1;
        return isSnapshot;
    }

    public MavenProject getMavenProject(final String groupId, final String artifactId, final String packaging) {
        MavenProject project = new MavenProject();
        project.setGroupId(groupId);
        project.setArtifactId(artifactId);
        project.setPackaging(packaging);
        return project;
    }

    public String getDownloadUrl(final MavenProject project, final SiteContext context) {
        String prefix = context.getDownloadPrefix();
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        if (!prefix.endsWith("/")) {
            sb.append("/");
        }
        if (isSnapshot(project)) {
            sb.append(context.getDownloadSnapshotPrefix());
        } else {
            sb.append(context.getDownloadReleasePrefix());
        }
        sb.append("/");
        String groupId = project.getGroupId();
        sb.append(groupId.replace('.', '/'));
        sb.append("/");
        sb.append(project.getArtifactId());
        sb.append("/");
        sb.append(project.getVersion());
        sb.append("/");
        return sb.toString();
    }

}
