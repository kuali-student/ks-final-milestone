package org.kuali.maven.common;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.project.MavenProject;

public class UrlBuilder {
    private static final Log log = LogFactory.getLog(UrlBuilder.class);

    protected boolean isTargetProject(final MavenProject project, final MavenProject targetProject) {
        // Return false if we can't do a meaningful comparison
        if (project == null || targetProject == null) {
            return false;
        }

        // Extract the data we will be inspecting
        String groupId = project.getGroupId();
        String artifactId = project.getArtifactId();
        String packaging = project.getPackaging();

        // Check that all 3 match our target data
        boolean isTargetGroupId = targetProject.getGroupId().equals(groupId);
        boolean isTargetArtifactId = targetProject.getArtifactId().equals(artifactId);
        boolean isTargetPackaging = targetProject.getPackaging().equals(packaging);

        // Only return true if all 3 match
        return isTargetGroupId && isTargetArtifactId && isTargetPackaging;
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

    protected boolean isAppendArtifactId(final MavenProject project, final MavenProject targetProject,
            final String trimmedGroupId) {
        if (isTargetProject(project, targetProject)) {
            return true;
        }
        List<String> modules = project.getModules();
        boolean singleProject = isEmpty(modules);
        if (singleProject) {
            return true;
        }
        String artifactId = project.getArtifactId();
        if (!trimmedGroupId.startsWith(artifactId)) {
            return true;
        }
        return false;
    }

    public String getSitePath(final MavenProject project, final MavenProject targetProject) {
        String trimmedGroupId = getTrimmedGroupId(project, targetProject.getGroupId());
        StringBuilder sb = new StringBuilder(trimmedGroupId);
        if (sb.length() > 0) {
            sb.append("/");
        }

        // Minor hack alert
        // It would be better to do something else here
        if (isAppendArtifactId(project, targetProject, trimmedGroupId)) {
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
        sb.append(getSitePath(project, context.getTargetProject()));
        sb.append("/");
        return sb.toString();
    }

    protected boolean isBaseCase(final MavenProject project, final MavenProject targetProject) {
        // If this is the targeted parent pom return true
        // This happens when using Maven to generate a site about the Kuali parent pom itself
        if (isTargetProject(project, targetProject)) {
            return true;
        }

        // Otherwise, inspect the parent project
        MavenProject parent = project.getParent();

        // If there is no parent, return true
        if (parent == null) {
            return true;
        }

        // If the parent is the targeted parent pom, return true
        if (isTargetProject(parent, targetProject)) {
            return true;
        }

        // False otherwise
        return false;
    }

    public String getPublicUrl(final MavenProject project, final SiteContext context) {
        MavenProject targetProject = context.getTargetProject();
        if (isBaseCase(project, targetProject)) {
            return getBaseUrl(context.getPublicUrlProtocol(), context.getHostname(), project, context);
        } else {
            return getPublicUrl(project.getParent(), context) + project.getArtifactId() + "/";
        }
    }

    public String getPublishUrl(final MavenProject project, final SiteContext context) {
        MavenProject targetProject = context.getTargetProject();
        if (isBaseCase(project, targetProject)) {
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
