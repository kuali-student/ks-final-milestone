package org.kuali.maven.mojo.s3;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * @goal init
 */
public class InitializeBucketMojo extends S3Mojo {
    Mimetypes mimeTypes = Mimetypes.getInstance();

    @Override
    public void executeMojo() throws MojoExecutionException, MojoFailureException {
        try {
            executeUploadInternals();
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

    protected Resource getResource(String location) {
        ResourceLoader loader = new DefaultResourceLoader();
        return loader.getResource(location);
    }

    protected ObjectMetadata getObjectMetadata(String location, Resource resource) throws IOException {
        ObjectMetadata om = new ObjectMetadata();
        String contentType = this.mimeTypes.getMimetype(location);
        om.setContentType(contentType);
        om.setContentLength(resource.contentLength());
        return om;
    }

    protected PutObjectRequest getPutObjectRequest(String location) throws IOException {
        Resource resource = getResource(location);
        InputStream in = resource.getInputStream();
        ObjectMetadata objectMetadata = getObjectMetadata(location, resource);
        String key = location.substring(1);
        PutObjectRequest request = new PutObjectRequest(getBucket(), key, in, objectMetadata);
        request.setCannedAcl(CannedAccessControlList.PublicRead);
        return request;
    }

    protected void executeUploadInternals() throws Exception {
        List<PutObjectRequest> requests = getInternalUploadRequests();
        AWSCredentials credentials = new BasicAWSCredentials(getAccessKeyId(), getSecretAccessKey());
        AmazonS3Client client = new AmazonS3Client(credentials);
        for (PutObjectRequest request : requests) {
            client.putObject(request);
        }
    }

    protected List<PutObjectRequest> getInternalUploadRequests() throws IOException {
        List<PutObjectRequest> requests = new ArrayList<PutObjectRequest>();
        requests.add(getPutObjectRequest("/s3browse/css/style.css"));
        requests.add(getPutObjectRequest("/s3browse/js/jquery-1.4.4.js"));
        requests.add(getPutObjectRequest("/s3browse/js/jQuery.url.js"));
        requests.add(getPutObjectRequest("/s3browse/js/jQuery.xml2json.js"));
        requests.add(getPutObjectRequest("/s3browse/js/s3Dir.js"));
        return requests;
    }

}
