package org.kuali.maven.mojo.s3;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

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
 * @goal updateorigin
 */
public class UpdateOriginBucketMojo extends S3Mojo {
    SimpleDateFormat dateFormatter;

    /**
     * @parameter expression="${css}" default-value="http://s3browse.ks.kuali.org/css/style.css"
     */
    private String css;

    /**
     * @parameter expression="${fileImage}" default-value="http://s3browse.ks.kuali.org/images/page_white.png"
     */
    private String fileImage;

    /**
     * @parameter expression="${directoryImage}" default-value="http://s3browse.ks.kuali.org/images/folder.png"
     */
    private String directoryImage;

    /**
     * @parameter expression="${timezone}" default-value="GMT"
     */
    private String timezone;

    /**
     * @parameter expression="${dateFormat}" default-value="EEE, dd MMM yyyy HH:mm:ss z"
     */
    private String dateFormat;

    /**
     * @parameter expression="${delimiter}" default-value="/"
     */
    private String delimiter;

    /**
     * @parameter expression="${prefix}"
     */
    private String prefix;

    /**
     * @parameter expression="${defaultObject}" default-value="index.html";
     */
    private String defaultObject;

    protected SimpleDateFormat getSimpleDateFormatInstance() {
        SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
        sdf.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        return sdf;
    }

    @Override
    public void executeMojo() throws MojoExecutionException, MojoFailureException {
        dateFormatter = getSimpleDateFormatInstance();
        try {
            updateCredentials();
            validateCredentials();
            AWSCredentials credentials = getCredentials();
            AmazonS3Client client = new AmazonS3Client(credentials);
            recurse(client, getPrefix(), getDelimiter());
        } catch (Exception e) {
            throw new MojoExecutionException("Unexpected error: ", e);
        }
    }

    protected String getIndexHtml(ObjectListing objectListing) {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

    protected String getHtmlHref(String dest, String show) {
        return "<a href=\"" + dest + "\">" + show + "</a>";
    }

    protected String getHtmlImage(String image) {
        return "<img src=\"" + image + "\">";
    }

    protected String getS3ObjectSummariesHtml(List<S3ObjectSummary> summaries) {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

    /**
     * Trim the prefix off of the text we display for this object.<br>
     * Display "style.css" instead of "css/style.css"
     */
    protected String getShow(String key, String prefix) {
        if (prefix == null) {
            return key;
        }
        int index = prefix.length();
        String s = key.substring(index);
        return s;
    }

    protected DisplayRow getS3ObjectDisplayRow(S3ObjectSummary summary, String prefix, String delimiter) {
        String key = summary.getKey();
        if (key.equals(prefix)) {
            return null;
        }
        if ((key + delimiter).equals(prefix)) {
            return null;
        }

        String image = getHtmlImage("http://s3browse.ks.kuali.org/images/page_white.png");
        String show = getShow(key, prefix);
        String dest = delimiter + key;
        String ahref = getHtmlHref(dest, show);
        String date = dateFormatter.format(summary.getLastModified());

        DisplayRow displayRow = new DisplayRow();
        displayRow.setImage(image);
        displayRow.setAhref(ahref);
        displayRow.setDate(date);
        return displayRow;
    }

    protected void recurse(AmazonS3Client client, String prefix, String delimiter) throws IOException {
        ListObjectsRequest request = new ListObjectsRequest(getBucket(), prefix, null, delimiter, Integer.MAX_VALUE);
        ObjectListing objectListing = client.listObjects(request);
        List<String> commonPrefixes = objectListing.getCommonPrefixes();
        for (String commonPrefix : commonPrefixes) {
            getLog().info("Updating: " + commonPrefix);
            updateDir(client, getBucket(), commonPrefix, delimiter);
            recurse(client, commonPrefix, delimiter);
        }
    }

    protected boolean isExistingKey(AmazonS3Client client, String bucket, String prefix, String key, String delimiter) {
        ListObjectsRequest request = new ListObjectsRequest(bucket, prefix, null, delimiter, Integer.MAX_VALUE);
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

    protected void updateDir(AmazonS3Client client, String bucket, String prefix, String delimiter) throws IOException {
        String defaultFileKey = prefix + getDefaultObject();
        if (isExistingKey(client, bucket, prefix, defaultFileKey, delimiter)) {
            getLog().info("###################");
            getLog().info("###################");
            getLog().info("###################");
            getLog().info("Copying " + defaultFileKey + " to " + prefix);
            getLog().info("###################");
            getLog().info("###################");
            getLog().info("###################");
            client.copyObject(getCopyObjectRequest(bucket, defaultFileKey, prefix));
            // PutObjectRequest request2 = getPutObjectRequest("/redirect.htm",
            // prefix.substring(0, prefix.length() - 1));
            // client.putObject(request2);
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

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDefaultObject() {
        return defaultObject;
    }

    public void setDefaultObject(String defaultCloudFrontObject) {
        this.defaultObject = defaultCloudFrontObject;
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

}
