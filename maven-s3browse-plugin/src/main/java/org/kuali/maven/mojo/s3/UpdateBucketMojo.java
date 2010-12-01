package org.kuali.maven.mojo.s3;

import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * @goal update
 */
public class UpdateBucketMojo extends S3Mojo {
    @Override
    public void executeMojo() throws MojoExecutionException, MojoFailureException {
        try {
            updateCredentials();
            validateCredentials();
            AWSCredentials credentials = getCredentials();
            AmazonS3Client client = new AmazonS3Client(credentials);
            ListObjectsRequest request = new ListObjectsRequest(getBucket(), null, null, "/", Integer.MAX_VALUE);
            ObjectListing objectListing = client.listObjects(request);
            List<String> commonPrefixes = objectListing.getCommonPrefixes();
            for (String prefix : commonPrefixes) {
                getLog().info("Updating: " + prefix);
                updateDir(client, getBucket(), prefix);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Unexpected error: ", e);
        }
    }

    protected void updateDir(AmazonS3Client client, String bucket, String prefix) throws IOException {
        PutObjectRequest request1 = getPutObjectRequest("/dir.htm", prefix);
        PutObjectRequest request2 = getPutObjectRequest("/dir.htm", prefix + "/");
        client.putObject(request1);
        client.putObject(request2);
    }

}
