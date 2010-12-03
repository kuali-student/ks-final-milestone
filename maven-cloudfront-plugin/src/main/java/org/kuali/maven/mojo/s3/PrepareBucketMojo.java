package org.kuali.maven.mojo.s3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * 
 */
public class PrepareBucketMojo extends S3Mojo {

    @Override
    public void executeMojo() throws MojoExecutionException, MojoFailureException {
        try {
            updateCredentials();
            validateCredentials();
            AWSCredentials credentials = getCredentials();
            List<PutObjectRequest> requests = getInternalUploadRequests();
            AmazonS3Client client = new AmazonS3Client(credentials);
            for (PutObjectRequest request : requests) {
                client.putObject(request);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Unexpected error: ", e);
        }
    }

    protected void addDir(List<PutObjectRequest> requests, String dir) throws IOException {
        PutObjectRequest dirWithSlash = getPutObjectRequest("/dir.htm");
        PutObjectRequest dirWithOutSlash = getPutObjectRequest("/dir.htm");
        dirWithSlash.setKey(dir + "/");
        dirWithOutSlash.setKey(dir);
        requests.add(dirWithSlash);
        requests.add(dirWithOutSlash);
    }

    protected List<PutObjectRequest> getInternalUploadRequests() throws IOException {
        List<PutObjectRequest> requests = new ArrayList<PutObjectRequest>();
        requests.add(getPutObjectRequest("/s3browse/css/style.css"));
        requests.add(getPutObjectRequest("/s3browse/js/jquery-1.4.4.js"));
        requests.add(getPutObjectRequest("/s3browse/js/jquery.url.js"));
        requests.add(getPutObjectRequest("/s3browse/js/jquery.xml2json.js"));
        requests.add(getPutObjectRequest("/s3browse/js/s3dir.js"));
        requests.add(getPutObjectRequest("/s3browse/images/folder.png"));
        requests.add(getPutObjectRequest("/s3browse/images/page_white.png"));
        addDir(requests, "s3browse");
        addDir(requests, "s3browse/css");
        addDir(requests, "s3browse/js");
        addDir(requests, "s3browse/images");
        PutObjectRequest rootBrowse = getPutObjectRequest("/dir.htm");
        rootBrowse.setKey("s3browse.html");
        requests.add(rootBrowse);
        return requests;
    }

}
