package org.kuali.maven.mojo.s3;

import java.io.IOException;
import java.io.InputStream;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.settings.Server;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Abstract mojo containing S3 mojo essentials.
 */
public abstract class S3Mojo extends BaseMojo {
    /**
     * Helper class for determining the mime types of objects.
     */
    Mimetypes mimeTypes = Mimetypes.getInstance();

    /**
     * This is the id of the server from settings.xml containing Amazon AWS
     * credentials.
     *
     * @parameter expression="${serverId}"
     */
    private String serverId;

    /**
     * Only update the bucket hierarchy underneath this prefix.
     *
     * @parameter expression="${prefix}"
     */
    private String prefix;

    /**
     * The delimiter used to organize keys into a hierarchy.
     *
     * @parameter expression="${delimiter}" default-value="/"
     */
    private String delimiter;

    /**
     * Maximum number of keys to return per query.
     *
     * @parameter expression="${maxKeys}"
     */
    private Integer maxKeys;

    /**
     * Amazon AWS Access Key Id. See also <code>serverId</code>.
     *
     * @parameter expression="${accessKeyId}"
     */
    private String accessKeyId;

    /**
     * Amazon AWS Secret Access Key. See also <code>serverId</code>.
     *
     * @parameter expression="${secretAccessKey}"
     */
    private String secretAccessKey;

    /**
     * The name of the bucket to update.
     *
     * @parameter expression="${bucket}"
     * @required
     */
    private String bucket;

    protected ObjectMetadata getObjectMetadata(final String location,
            final Resource resource) throws IOException {
        ObjectMetadata om = new ObjectMetadata();
        String contentType = mimeTypes.getMimetype(location);
        om.setContentType(contentType);
        om.setContentLength(resource.contentLength());
        return om;
    }

    protected PutObjectRequest getPutObjectRequest(final String location, final String key)
            throws IOException {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(location);
        InputStream in = resource.getInputStream();
        ObjectMetadata objectMetadata = getObjectMetadata(location, resource);
        PutObjectRequest request = new PutObjectRequest(getBucket(), key, in,
                objectMetadata);
        request.setCannedAcl(CannedAccessControlList.PublicRead);
        return request;
    }

    protected PutObjectRequest getPutObjectRequest(final String location)
            throws IOException {
        String key = location.substring(1);
        return getPutObjectRequest(location, key);
    }

    protected String getAuthenticationErrorMessage() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n\nError: accessKeyId and secretAccessKey must be provided.\n");
        sb.append("Provide them in the plugin configuration or specify them on the command line:\n\n");
        sb.append("-DaccessKeyId=XXXX\n");
        sb.append("-DsecretAccessKey=XXXX\n");
        sb.append("\n");
        sb.append("You can also provide them in settings.xml as username and password:\n\n");
        sb.append("<server>\n");
        sb.append("  <id>[serverId]</id>\n");
        sb.append("  <username>[AWS Access Key ID]</username>\n");
        sb.append("  <password>[AWS Secret Access Key]</password>\n");
        sb.append("</server>\n\n.\n");
        return sb.toString();
    }

    protected void updateCredentials() {
        if (getServerId() == null) {
            return;
        }
        Server server = getSettings().getServer(getServerId());
        if (getAccessKeyId() == null) {
            setAccessKeyId(server.getUsername());
        }
        if (getSecretAccessKey() == null) {
            setSecretAccessKey(server.getPassword());
        }
    }

    protected void validateCredentials() throws MojoExecutionException {
        if (getAccessKeyId() == null || getSecretAccessKey() == null) {
            throw new MojoExecutionException(getAuthenticationErrorMessage());
        }
    }

    protected AWSCredentials getCredentials() throws MojoExecutionException {
        return new BasicAWSCredentials(getAccessKeyId(), getSecretAccessKey());
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(final String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(final String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(final String bucket) {
        this.bucket = bucket;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
    }

    public Integer getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(final Integer maxKeys) {
        this.maxKeys = maxKeys;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(final String serverId) {
        this.serverId = serverId;
    }
}
