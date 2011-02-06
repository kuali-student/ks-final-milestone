package org.kuali.maven.plugin.ksite.mojo;

import java.util.Properties;

import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.Site;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.kuali.maven.common.SiteContext;
import org.kuali.maven.common.UrlBuilder;

/**
 * @goal kualisite
 * @phase pre-site
 */
public class KualiSiteMojo extends AbstractMojo implements SiteContext {

    private MavenProject targetProject;

    /**
     * The prefix into the bucket when downloading a snapshot version
     *
     * @parameter expression="${downloadSnapshotPrefix}" default-value="snapshot"
     */
    private String downloadSnapshotPrefix;

    /**
     * The prefix into the bucket when downloading a release version
     *
     * @parameter expression="${downloadReleasePrefix}" default-value="release"
     */
    private String downloadReleasePrefix;

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

    protected MavenProject createTargetProject() {
        MavenProject project = new MavenProject();
        project.setGroupId(getParentGroupId());
        project.setArtifactId(getParentArtifactId());
        project.setPackaging(getParentPackagingType());
        return project;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        setTargetProject(createTargetProject());

        UrlBuilder builder = new UrlBuilder();

        // Generate our urls
        String publicUrl = builder.getPublicUrl(getProject(), this);
        String publishUrl = builder.getPublishUrl(getProject(), this);
        String downloadUrl = builder.getDownloadUrl(getProject(), this);

        // Get a reference to the relevant model objects
        MavenProject project = getProject();
        DistributionManagement dm = project.getDistributionManagement();
        Site site = dm.getSite();

        getLog().info("Public url  - " + publicUrl);
        getLog().info("Publish url  - " + publishUrl);
        getLog().info("Download url - " + downloadUrl);

        // Update the model with our generated urls
        project.setUrl(publicUrl);
        dm.setDownloadUrl(downloadUrl);
        site.setUrl(publishUrl);

        // Update project properties
        Properties properties = getProject().getProperties();
        properties.setProperty("kuali.site.public.url", publicUrl);
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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

    /**
     * @return the downloadSnapshotPrefix
     */
    @Override
    public String getDownloadSnapshotPrefix() {
        return downloadSnapshotPrefix;
    }

    /**
     * @param downloadSnapshotPrefix
     * the downloadSnapshotPrefix to set
     */
    public void setDownloadSnapshotPrefix(final String downloadSnapshotPrefix) {
        this.downloadSnapshotPrefix = downloadSnapshotPrefix;
    }

    /**
     * @return the downloadReleasePrefix
     */
    @Override
    public String getDownloadReleasePrefix() {
        return downloadReleasePrefix;
    }

    /**
     * @param downloadReleasePrefix
     * the downloadReleasePrefix to set
     */
    public void setDownloadReleasePrefix(final String downloadReleasePrefix) {
        this.downloadReleasePrefix = downloadReleasePrefix;
    }

    /**
     * @return the targetProject
     */
    @Override
    public MavenProject getTargetProject() {
        return targetProject;
    }

    /**
     * @param targetProject
     * the targetProject to set
     */
    public void setTargetProject(final MavenProject targetProject) {
        this.targetProject = targetProject;
    }

}
