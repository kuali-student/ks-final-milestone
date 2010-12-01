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

public abstract class S3Mojo extends BaseMojo {
    Mimetypes mimeTypes = Mimetypes.getInstance();

    /**
     * @parameter expression="${serverId}"
     */
    private String serverId;

    /**
     * @parameter expression="${prefix}"
     */
    private String prefix;

    /**
     * @parameter expression="${delimiter}" default-value="/"
     */
    private String delimiter;

    /**
     * @parameter expression="${maxKeys}"
     */
    private Integer maxKeys;

    /**
     * @parameter expression="${accessKeyId}"
     */
    private String accessKeyId;

    /**
     * @parameter expression="${secretAccessKey}"
     */
    private String secretAccessKey;

    /**
     * @parameter expression="${bucket}"
     * @required
     */
    private String bucket;

    protected ObjectMetadata getObjectMetadata(String location, Resource resource) throws IOException {
        ObjectMetadata om = new ObjectMetadata();
        String contentType = mimeTypes.getMimetype(location);
        om.setContentType(contentType);
        om.setContentLength(resource.contentLength());
        return om;
    }

    protected PutObjectRequest getPutObjectRequest(String location, String key) throws IOException {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(location);
        InputStream in = resource.getInputStream();
        ObjectMetadata objectMetadata = getObjectMetadata(location, resource);
        PutObjectRequest request = new PutObjectRequest(getBucket(), key, in, objectMetadata);
        request.setCannedAcl(CannedAccessControlList.PublicRead);
        return request;
    }

    protected PutObjectRequest getPutObjectRequest(String location) throws IOException {
        String key = location.substring(1);
        return getPutObjectRequest(location, key);
    }

    protected String getAuthenticationErrorMessage() {
        StringBuffer sb = new StringBuffer();
        sb.append("Error: accessKeyId and secretAccessKey must be provided\n");
        sb.append("Provide them in the plugin configuration or specify them on the command line. eg:\n");
        sb.append("-DaccessKeyId=XXXX\n");
        sb.append("-DsecretAccessKey=XXXX\n");
        sb.append("\n");
        sb.append("You can also provide them in settings.xml as username and password eg:\n");
        sb.append("<server>\n");
        sb.append("  <id>my.server</id>\n");
        sb.append("  <username>[AWS Access Key ID]</username>\n");
        sb.append("  <password>[AWS Secret Access Key]</password>\n");
        sb.append("</server>\n");
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

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Integer getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(Integer maxKeys) {
        this.maxKeys = maxKeys;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
