package org.kuali.maven.plugin.ksite.mojo;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

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
     * @parameter expression="${path}"
     */
    private String path;

    /**
     * @parameter expression="${publicUrl}"
     */
    private String publicUrl;

    /**
     * @parameter expression="${publishUrl}"
     */
    private String publishUrl;

    /**
     * @parameter expression="${serverId}" default-value="kuali.site"
     */
    private String serverId;

    /**
     * @parameter expression="${downloadPath}"
     */
    private String downloadPath;

    /**
     * @parameter expression="${downloadUrl}"
     */
    private String downloadUrl;

    /**
     * Convenience reference to System.getProperty("file.separator").
     */
    public static final String FS = System.getProperty("file.separator");

    /**
     * Skip packaging if type is "pom".
     */
    public static final String SKIP_PACKAGING_TYPE = "pom";

    /**
     * When <code>true</code>, skip the execution of this mojo.
     *
     * @parameter default-value="false"
     */
    private boolean skip;

    /**
     * Setting this parameter to <code>true</code> will force the execution of this mojo, even if it would get skipped
     * usually.
     *
     * @parameter expression="${forceMojoExecution}" default-value="false"
     * @required
     */
    private boolean forceMojoExecution;

    /**
     * The encoding to use when reading/writing files. If not specified this defaults to the platform specific encoding
     * of whatever machine the build is running on.
     *
     * @parameter expression="${encoding}" default-value="${project.build.sourceEncoding}"
     */
    private String encoding;

    /**
     * The Maven project this plugin runs in.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter expression="${settings}"
     * @required
     * @since 1.0
     * @readonly
     */
    private Settings settings;

    /**
     * @parameter default-value="${session}"
     * @required
     * @readonly
     */
    private MavenSession mavenSession;

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

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skipMojo()) {
            return;
        }
        String sitePath = getSitePath();
        getLog().info("sitePath=" + sitePath);

        boolean snapshot = isSnapshot();
        getLog().info("snapshot=" + snapshot);
        Properties props = getProject().getProperties();
        String property = props.getProperty("kuali.site.download.url");
        getLog().info("property=" + property);
        DistributionManagement dm = project.getDistributionManagement();
        dm.setDownloadUrl(downloadUrl);

    }

    protected boolean isSnapshot() {
        String version = getProject().getVersion();
        int pos = version.toUpperCase().indexOf("SNAPSHOT");
        boolean isSnapshot = pos != -1;
        return isSnapshot;
    }

    /**
     * <p>
     * Determine if the mojo execution should get skipped.
     * </p>
     * This is the case if:
     * <ul>
     * <li>{@link #skip} is <code>true</code></li>
     * <li>if the mojo gets executed on a project with packaging type 'pom' and {@link #forceMojoExecution} is
     * <code>false</code></li>
     * </ul>
     *
     * @return <code>true</code> if the mojo execution should be skipped.
     */
    protected boolean skipMojo() {
        if (skip) {
            getLog().info("Skipping execution");
            return true;
        }

        if (!forceMojoExecution && project != null && SKIP_PACKAGING_TYPE.equals(project.getPackaging())) {
            getLog().info("Skipping execution for project with packaging type '" + SKIP_PACKAGING_TYPE + "'");
            return true;
        }

        return false;
    }

    /**
     * @return the skip
     */
    public boolean isSkip() {
        return skip;
    }

    /**
     * @param skip
     * the skip to set
     */
    public void setSkip(final boolean skip) {
        this.skip = skip;
    }

    /**
     * @return the forceMojoExecution
     */
    public boolean isForceMojoExecution() {
        return forceMojoExecution;
    }

    /**
     * @param forceMojoExecution
     * the forceMojoExecution to set
     */
    public void setForceMojoExecution(final boolean forceMojoExecution) {
        this.forceMojoExecution = forceMojoExecution;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding
     * the encoding to set
     */
    public void setEncoding(final String encoding) {
        this.encoding = encoding;
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
     * @return the settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * @param settings
     * the settings to set
     */
    public void setSettings(final Settings settings) {
        this.settings = settings;
    }

    /**
     * @return the mavenSession
     */
    public MavenSession getMavenSession() {
        return mavenSession;
    }

    /**
     * @param mavenSession
     * the mavenSession to set
     */
    public void setMavenSession(final MavenSession mavenSession) {
        this.mavenSession = mavenSession;
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
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     * the path to set
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * @return the publicUrl
     */
    public String getPublicUrl() {
        return publicUrl;
    }

    /**
     * @param publicUrl
     * the publicUrl to set
     */
    public void setPublicUrl(final String publicUrl) {
        this.publicUrl = publicUrl;
    }

    /**
     * @return the publishUrl
     */
    public String getPublishUrl() {
        return publishUrl;
    }

    /**
     * @param publishUrl
     * the publishUrl to set
     */
    public void setPublishUrl(final String publishUrl) {
        this.publishUrl = publishUrl;
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
     * @return the downloadPath
     */
    public String getDownloadPath() {
        return downloadPath;
    }

    /**
     * @param downloadPath
     * the downloadPath to set
     */
    public void setDownloadPath(final String downloadPath) {
        this.downloadPath = downloadPath;
    }

    /**
     * @return the downloadUrl
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * @param downloadUrl
     * the downloadUrl to set
     */
    public void setDownloadUrl(final String downloadUrl) {
        this.downloadUrl = downloadUrl;
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
