package org.kuali.maven.plugin.ksite.mojo;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.Site;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * @goal kualisite
 * @phase pre-site
 */
public class KualiSiteMojo extends AbstractMojo {

    /**
     * The protocol used when publishing the web site
     *
     * @parameter expression="${publishUrlProtocol}" default-value="s3"
     */
    private String publishUrlProtocol;

    /**
     * The protocol for the public facing website
     *
     * @parameter expression="${publicUrlProtocol}" default-value="http"
     */
    private String publicUrlProtocol;

    /**
     * The prefix for the location that artifacts can be downloaded from
     *
     * @parameter expression="${downloadPrefix}"
     * default-value="http://s3browse.springsource.com/browse/maven.kuali.org/"
     */
    private String downloadPrefix;

    /**
     * The groupId of the top level parent pom projects inherit from
     *
     * @parameter expression="${parentGroupId}" default-value="org.kuali"
     */
    private String parentGroupId;

    /**
     * The artifactId of the top level parent pom projects inherit from
     *
     * @parameter expression="${parentArtifactId}" default-value="kuali"
     */
    private String parentArtifactId;

    /**
     * The packaging type of the top level parent pom projects inherit from
     *
     * @parameter expression="${parentPackagingType}" default-value="pom"
     */
    private String parentPackagingType;

    /**
     * The name of the AWS bucket the site gets published to
     *
     * @parameter expression="${bucket}" default-value="site.origin.kuali.org"
     * @required
     */
    private String bucket;

    /**
     * The public DNS name for the site
     *
     * @parameter expression="${hostname}" default-value="site.kuali.org"
     * @required
     */
    private String hostname;

    /**
     * The Maven project this plugin runs in.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    protected String getSitePath(final MavenProject project) {
        String trimmedGroupId = getTrimmedGroupId(project, getParentGroupId());
        StringBuilder sb = new StringBuilder(trimmedGroupId);
        if (sb.length() > 0) {
            sb.append("/");
        }
        // Minor hack alert
        // It would be better to do something else here
        if (isTargetParentPom(project)) {
            sb.append(project.getArtifactId() + "/");
        }
        sb.append(project.getVersion());
        return sb.toString();
    }

    protected String getTrimmedGroupId(final MavenProject project, final String targetGroupId) {
        String groupId = project.getGroupId();
        if (StringUtils.isEmpty(targetGroupId)) {
            return groupId;
        }
        if (!groupId.startsWith(targetGroupId)) {
            getLog().warn("Group Id does not start with " + targetGroupId + " " + groupId);
            return groupId;
        }
        String s = StringUtils.replace(groupId, targetGroupId, "");
        if (s.startsWith(".")) {
            s = s.substring(1);
        }
        s = s.replace(".", "/");
        return s;
    }

    protected String buildDownloadUrl(final MavenProject project) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDownloadPrefix());
        if (!getDownloadPrefix().endsWith("/")) {
            sb.append("/");
        }
        if (isSnapshot(project)) {
            sb.append("snapshot");
        } else {
            sb.append("release");
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

    protected boolean isTargetParentPom(final MavenProject project) {
        // Return false if the project passed in is null
        if (project == null) {
            return false;
        }

        // Extract the data we will be inspecting
        String groupId = project.getGroupId();
        String artifactId = project.getArtifactId();
        String packaging = project.getPackaging();

        // Check that all 3 match our target data
        boolean isParentGroupId = "org.kuali".equals(groupId);
        boolean isParentArtifactId = "kuali".equals(artifactId);
        boolean isPomPackaging = "pom".equals(packaging);

        // Only return true if all 3 match
        return isParentGroupId && isParentArtifactId && isPomPackaging;
    }

    protected boolean isBaseCase(final MavenProject project) {
        // If this is the targeted parent pom return true
        // This happens if when using Maven to generate a site about the Kuali parent pom itself
        if (isTargetParentPom(project)) {
            return true;
        }

        // Otherwise, inspect the parent project
        MavenProject parent = project.getParent();

        // If there is no parent, return true
        if (parent == null) {
            return true;
        }

        // If the parent is the targeted parent pom, return true
        if (isTargetParentPom(parent)) {
            return true;
        }

        // False otherwise
        return false;
    }

    protected String buildPublicUrl(final MavenProject project, final String protocol) {
        if (isBaseCase(project)) {
            return protocol + "://" + getHostname() + "/" + getSitePath(project) + "/";
        } else {
            return buildPublicUrl(project.getParent(), protocol) + project.getArtifactId() + "/";
        }
    }

    protected String buildPublishUrl(final MavenProject project, final String protocol) {
        if (isBaseCase(project)) {
            return protocol + "://" + getBucket() + "/" + getSitePath(project) + "/";
        } else {
            return buildPublishUrl(project.getParent(), protocol) + project.getArtifactId() + "/";
        }
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // Generate our urls
        String publicUrl = buildPublicUrl(getProject(), getPublicUrlProtocol());
        String publishUrl = buildPublishUrl(getProject(), getPublishUrlProtocol());
        String downloadUrl = buildDownloadUrl(getProject());

        // Get a reference to the relevant model objects
        MavenProject project = getProject();
        DistributionManagement dm = project.getDistributionManagement();
        Site site = dm.getSite();

        // Update the model with our generated urls
        project.setUrl(publicUrl);
        dm.setDownloadUrl(downloadUrl);
        site.setUrl(publishUrl);

        getLog().info("  Public URL=" + publicUrl);
        getLog().info(" Publish URL=" + publishUrl);
        getLog().info("Download URL=" + downloadUrl);
    }

    protected boolean isSnapshot(final MavenProject project) {
        String version = project.getVersion();
        int pos = version.toUpperCase().indexOf("SNAPSHOT");
        boolean isSnapshot = pos != -1;
        return isSnapshot;
    }

    /**
     * @return the project
     */
    public MavenProject getProject() {
        return project;
    }

    /**
     * @param project
     * the project to set
     */
    public void setProject(final MavenProject project) {
        this.project = project;
    }

    /**
     * @return the bucket
     */
    public String getBucket() {
        return bucket;
    }

    /**
     * @param bucket
     * the bucket to set
     */
    public void setBucket(final String bucket) {
        this.bucket = bucket;
    }

    /**
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @param hostname
     * the hostname to set
     */
    public void setHostname(final String hostname) {
        this.hostname = hostname;
    }

    /**
     * @return the downloadPrefix
     */
    public String getDownloadPrefix() {
        return downloadPrefix;
    }

    /**
     * @param downloadPrefix
     * the downloadPrefix to set
     */
    public void setDownloadPrefix(final String downloadPrefix) {
        this.downloadPrefix = downloadPrefix;
    }

    /**
     * @return the parentGroupId
     */
    public String getParentGroupId() {
        return parentGroupId;
    }

    /**
     * @param parentGroupId
     * the parentGroupId to set
     */
    public void setParentGroupId(final String parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    /**
     * @return the parentArtifactId
     */
    public String getParentArtifactId() {
        return parentArtifactId;
    }

    /**
     * @param parentArtifactId
     * the parentArtifactId to set
     */
    public void setParentArtifactId(final String parentArtifactId) {
        this.parentArtifactId = parentArtifactId;
    }

    /**
     * @return the parentPackagingType
     */
    public String getParentPackagingType() {
        return parentPackagingType;
    }

    /**
     * @param parentPackagingType
     * the parentPackagingType to set
     */
    public void setParentPackagingType(final String parentPackagingType) {
        this.parentPackagingType = parentPackagingType;
    }

    /**
     * @return the publishUrlProtocol
     */
    public String getPublishUrlProtocol() {
        return publishUrlProtocol;
    }

    /**
     * @param publishUrlProtocol
     * the publishUrlProtocol to set
     */
    public void setPublishUrlProtocol(final String publishUrlProtocol) {
        this.publishUrlProtocol = publishUrlProtocol;
    }

    /**
     * @return the publicUrlProtocol
     */
    public String getPublicUrlProtocol() {
        return publicUrlProtocol;
    }

    /**
     * @param publicUrlProtocol
     * the publicUrlProtocol to set
     */
    public void setPublicUrlProtocol(final String publicUrlProtocol) {
        this.publicUrlProtocol = publicUrlProtocol;
    }

}
