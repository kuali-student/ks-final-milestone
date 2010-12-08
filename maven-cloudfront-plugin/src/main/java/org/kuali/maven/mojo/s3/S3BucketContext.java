package org.kuali.maven.mojo.s3;

import java.text.SimpleDateFormat;

import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Holds context information for S3
 */
public class S3BucketContext {
    AmazonS3Client client;
    String bucket;
    String delimiter;
    String fileImage;
    String directoryImage;
    String css;
    String defaultObject;
    SimpleDateFormat lastModifiedDateFormatter;
    String about;

    public AmazonS3Client getClient() {
        return client;
    }

    public void setClient(AmazonS3Client client) {
        this.client = client;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getFileImage() {
        return fileImage;
    }

    public void setFileImage(String fileImage) {
        this.fileImage = fileImage;
    }

    public String getDirectoryImage() {
        return directoryImage;
    }

    public void setDirectoryImage(String directoryImage) {
        this.directoryImage = directoryImage;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getDefaultObject() {
        return defaultObject;
    }

    public void setDefaultObject(String defaultObject) {
        this.defaultObject = defaultObject;
    }

    public SimpleDateFormat getLastModifiedDateFormatter() {
        return lastModifiedDateFormatter;
    }

    public void setLastModifiedDateFormatter(SimpleDateFormat dateFormatter) {
        this.lastModifiedDateFormatter = dateFormatter;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
