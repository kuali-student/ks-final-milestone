package org.kuali.maven.plugin.ksite.mojo;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.model.DistributionManagement;
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
     * @parameter expression="${downloadPrefix}"
     * default-value="http://s3browse.springsource.com/browse/maven.kuali.org/"
     */
    private String downloadPrefix;

    /**
     * The name of the AWS bucket the site gets published to
     *
     * @parameter expression="${prefixToTrimFromGroupId}" default-value="org.kuali"
     */
    private String prefixToTrimFromGroupId;

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
     * @parameter expression="${serverId}" default-value="kuali.site"
     */
    private String serverId;

    /**
     * The Maven project this plugin runs in.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    protected String getSitePath() {
        String trimmedGroupId = getTrimmedGroupId();
        StringBuilder sb = new StringBuilder(trimmedGroupId);
        if (sb.length() > 0) {
            sb.append("/");
        }
        sb.append(getProject().getArtifactId());
        sb.append("/");
        sb.append(getProject().getVersion());
        return sb.toString();
    }

    protected String getTrimmedGroupId() {
        String groupId = getProject().getGroupId();
        if (StringUtils.isEmpty(getPrefixToTrimFromGroupId())) {
            return groupId;
        }
        if (!groupId.startsWith(getPrefixToTrimFromGroupId())) {
            getLog().warn("Group Id does not start with " + getPrefixToTrimFromGroupId() + " " + groupId);
            return groupId;
        }
        String s = StringUtils.replace(groupId, getPrefixToTrimFromGroupId(), "");
        if (s.startsWith(".")) {
            s = s.substring(1);
        }
        s = s.replace(".", "/");
        return s;
    }

    protected String generateDownloadUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDownloadPrefix());
        if (!getDownloadPrefix().endsWith("/")) {
            sb.append("/");
        }
        if (isSnapshot()) {
            sb.append("snapshot");
        } else {
            sb.append("release");
        }
        sb.append("/");
        String groupId = getProject().getGroupId();
        sb.append(groupId.replace('.', '/'));
        sb.append("/");
        sb.append(getProject().getArtifactId());
        sb.append("/");
        sb.append(getProject().getVersion());
        sb.append("/");
        return sb.toString();
    }

    protected String generatePublicUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("http://" + getHostname() + "/" + getSitePath());
        return sb.toString();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        DistributionManagement dm = getProject().getDistributionManagement();
        dm.setDownloadUrl(generateDownloadUrl());
        getLog().info("generated Public URL=" + generatePublicUrl());
        getLog().info("publicUrl=" + getProject().getUrl());
        getLog().info("publishUrl=" + dm.getSite().getUrl());
        getLog().info("downloadUrl=" + dm.getDownloadUrl());
    }

    protected boolean isSnapshot() {
        String version = getProject().getVersion();
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
     * @return the serverId
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * @param serverId
     * the serverId to set
     */
    public void setServerId(final String serverId) {
        this.serverId = serverId;
    }

    /**
     * @return the prefixToTrimFromGroupId
     */
    public String getPrefixToTrimFromGroupId() {
        return prefixToTrimFromGroupId;
    }

    /**
     * @param prefixToTrimFromGroupId
     * the prefixToTrimFromGroupId to set
     */
    public void setPrefixToTrimFromGroupId(final String prefixToTrimFromGroupId) {
        this.prefixToTrimFromGroupId = prefixToTrimFromGroupId;
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

}
