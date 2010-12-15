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

    public void setClient(final AmazonS3Client client) {
        this.client = client;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(final String bucket) {
        this.bucket = bucket;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
    }

    public String getFileImage() {
        return fileImage;
    }

    public void setFileImage(final String fileImage) {
        this.fileImage = fileImage;
    }

    public String getDirectoryImage() {
        return directoryImage;
    }

    public void setDirectoryImage(final String directoryImage) {
        this.directoryImage = directoryImage;
    }

    public String getCss() {
        return css;
    }

    public void setCss(final String css) {
        this.css = css;
    }

    public String getDefaultObject() {
        return defaultObject;
    }

    public void setDefaultObject(final String defaultObject) {
        this.defaultObject = defaultObject;
    }

    public SimpleDateFormat getLastModifiedDateFormatter() {
        return lastModifiedDateFormatter;
    }

    public void setLastModifiedDateFormatter(final SimpleDateFormat dateFormatter) {
        this.lastModifiedDateFormatter = dateFormatter;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(final String about) {
        this.about = about;
    }
}
