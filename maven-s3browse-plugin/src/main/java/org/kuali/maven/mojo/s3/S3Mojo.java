package org.kuali.maven.mojo.s3;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jets3t.service.StorageObjectsChunk;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.security.AWSCredentials;

/**
 * @goal browse
 */
public class S3Mojo extends BaseMojo {

    /**
     * 
     * @parameter expression="${accessKeyId}"
     */
    private String accessKeyId;

    /**
     * 
     * @parameter expression="${secretAccessKey}"
     */
    private String secretAccessKey;

    /**
     * 
     * @parameter expression="${bucket}"
     */
    private String bucket;

    @Override
    public void executeMojo() throws MojoExecutionException, MojoFailureException {
        try {
            AWSCredentials credentials = new AWSCredentials(getAccessKeyId(), getSecretAccessKey());
            RestS3Service service = new RestS3Service(credentials);
            S3Bucket bucket = service.getOrCreateBucket(getBucket());
            StorageObjectsChunk chunk = service.listObjectsChunked(bucket.getName(), null, "/", -1, null, false);
            String[] commonPrefixes = chunk.getCommonPrefixes();
            for (String prefix : commonPrefixes) {
                getLog().info("prefix=" + prefix);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Unexpected error: ", e);
        }
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

}
