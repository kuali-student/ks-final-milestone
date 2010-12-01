package org.kuali.maven.mojo.s3;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;

/**
 * @goal init
 */
public class InitializeBucketMojo extends S3Mojo {

    /**
     * @parameter expression="${internalKey}" default-value="s3Browse"
     */
    private String internalKey;

    @Override
    public void executeMojo() throws MojoExecutionException, MojoFailureException {
        try {
            AWSCredentials credentials = new BasicAWSCredentials(getAccessKeyId(), getSecretAccessKey());
            AmazonS3Client client = new AmazonS3Client(credentials);
            ListObjectsRequest request = new ListObjectsRequest();
            request.setBucketName(getBucket());
            request.setDelimiter(getDelimiter());
            request.setPrefix(getPrefix());
            request.setMaxKeys(getMaxKeys());
            ObjectListing listing = client.listObjects(request);
            for (String prefix : listing.getCommonPrefixes()) {
                getLog().info("prefix=" + prefix);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Unexpected error: ", e);
        }
    }

    public String getInternalKey() {
        return internalKey;
    }

    public void setInternalKey(String internalKey) {
        this.internalKey = internalKey;
    }

}
