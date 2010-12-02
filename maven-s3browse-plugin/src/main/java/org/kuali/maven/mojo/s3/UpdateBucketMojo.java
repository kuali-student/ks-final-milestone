package org.kuali.maven.mojo.s3;

import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * @goal update
 */
public class UpdateBucketMojo extends S3Mojo {

    /**
     * @parameter expression="${prefix}"
     */
    private String prefix;

    /**
     * @parameter expression="${welcomeFile}" default-value="index.html";
     */
    private String welcomeFile;

    @Override
    public void executeMojo() throws MojoExecutionException, MojoFailureException {
        try {
            updateCredentials();
            validateCredentials();
            AWSCredentials credentials = getCredentials();
            AmazonS3Client client = new AmazonS3Client(credentials);
            recurse(client, getPrefix());
        } catch (Exception e) {
            throw new MojoExecutionException("Unexpected error: ", e);
        }
    }

    protected void recurse(AmazonS3Client client, String prefix) throws IOException {
        ListObjectsRequest request = new ListObjectsRequest(getBucket(), prefix, null, "/", Integer.MAX_VALUE);
        ObjectListing objectListing = client.listObjects(request);
        List<String> commonPrefixes = objectListing.getCommonPrefixes();
        for (String commonPrefix : commonPrefixes) {
            getLog().info("Updating: " + commonPrefix);
            updateDir(client, getBucket(), commonPrefix);
            recurse(client, commonPrefix);
        }
    }

    protected boolean isExistingKey(AmazonS3Client client, String bucket, String prefix, String key) {
        ListObjectsRequest request = new ListObjectsRequest(bucket, prefix, null, "/", Integer.MAX_VALUE);
        ObjectListing objectListing = client.listObjects(request);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (S3ObjectSummary objectSummary : objectSummaries) {
            String objectKey = objectSummary.getKey();
            if (key.equals(objectKey)) {
                return true;
            }
        }
        return false;
    }

    protected CopyObjectRequest getCopyObjectRequest(String bucket, String sourceKey, String destKey) {
        CopyObjectRequest request = new CopyObjectRequest(bucket, sourceKey, bucket, destKey);
        request.setCannedAccessControlList(CannedAccessControlList.PublicRead);
        return request;
    }

    protected void updateDir(AmazonS3Client client, String bucket, String prefix) throws IOException {
        String defaultFileKey = prefix + getWelcomeFile();
        if (isExistingKey(client, bucket, prefix, defaultFileKey)) {
            getLog().info("###################");
            getLog().info("###################");
            getLog().info("###################");
            getLog().info("Copying " + defaultFileKey + " to " + prefix);
            getLog().info("###################");
            getLog().info("###################");
            getLog().info("###################");
            client.copyObject(getCopyObjectRequest(bucket, defaultFileKey, prefix));
            // client.copyObject(getCopyObjectRequest(bucket, defaultFileKey,
            // prefix.substring(0, prefix.length() - 1)));
        } else {
            PutObjectRequest request1 = getPutObjectRequest("/dir.htm", prefix);
            client.putObject(request1);
        }
        PutObjectRequest request2 = getPutObjectRequest("/dir.htm", prefix.substring(0, prefix.length() - 1));
        client.putObject(request2);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getWelcomeFile() {
        return welcomeFile;
    }

    public void setWelcomeFile(String welcomeFile) {
        this.welcomeFile = welcomeFile;
    }

}
