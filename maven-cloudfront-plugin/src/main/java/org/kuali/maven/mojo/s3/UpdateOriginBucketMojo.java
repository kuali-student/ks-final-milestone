package org.kuali.maven.mojo.s3;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
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
    NumberFormat nf = getNumberFormatInstance();
    SimpleDateFormat dateFormatter;
    HtmlUtils html = new HtmlUtils();

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
     * @parameter expression="${defaultObject}" default-value="index.html";
     */
    private String defaultObject;

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

    protected NumberFormat getNumberFormatInstance() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        nf.setGroupingUsed(false);
        return nf;
    }

    protected SimpleDateFormat getSimpleDateFormatInstance() {
        SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
        sdf.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        return sdf;
    }

    protected String getIndexHtml(ObjectListing objectListing) {
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

    protected String getUpOneDirectoryPrefix(String prefix, String delimiter) {
        if (prefix.endsWith(delimiter)) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        int pos = prefix.lastIndexOf(delimiter);
        if (pos == -1) {
            return delimiter;
        } else {
            return prefix.substring(0, pos + 1);
        }
    }

    protected List<String[]> getData(ObjectListing objectListing, String prefix, String delimiter) {
        DisplayRow upOneDirectory = getUpOneDirectoryDisplayRow(prefix, delimiter);
        List<DisplayRow> objectDisplayRows = getObjectDisplayRows(objectListing, prefix, delimiter);
        List<DisplayRow> directoryDisplayRows = getDirectoryDisplayRows(objectListing, prefix, delimiter);
        List<String[]> data = new ArrayList<String[]>();
        addDisplayRow(upOneDirectory, data);
        addDisplayRows(objectDisplayRows, data);
        addDisplayRows(directoryDisplayRows, data);
        return data;
    }

    protected boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    protected String getHtmlTable(List<String[]> data, List<ColumnDecorator> columnDecorators) {
        if (isEmpty(data)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Tag table = new Tag("table", "mainTable");
        Tag thead = new Tag("thead", 1);
        Tag tr = new Tag("tr", 2);
        Tag tbody = new Tag("tbody", 1);
        sb.append(html.getOpenTag(table));
        sb.append(html.getOpenTag(thead));
        sb.append(html.getOpenTag(tr));
        sb.append(html.getCloseTag(tr));
        sb.append(html.getCloseTag(thead));
        sb.append(html.getOpenTag(tbody));
        sb.append(html.getCloseTag(tbody));
        sb.append(html.getCloseTag(table));
        return sb.toString();
    }

    protected Tag getTableRowTag(int row) {
        if ((row % 2) == 0) {
            return new Tag("tr", 2);
        } else {
            return new Tag("tr", "table-tr-odd", 2);
        }
    }

    protected String getTableCell(String content, ColumnDecorator decorator) {
        Tag td = new Tag("td", decorator.getTableDataClass(), 3);
        return html.getTag(td, content);
    }

    protected String getTableRow(int row, String[] data, List<ColumnDecorator> columnDecorators) {
        StringBuffer sb = new StringBuffer();
        Tag tr = getTableRowTag(row);
        sb.append(html.getOpenTag(tr));
        for (int i = 0; i < data.length; i++) {
            sb.append(getTableCell(data[i], columnDecorators.get(i)));
        }
        sb.append(html.getCloseTag(tr));
        return sb.toString();
    }

    protected String getTableRows(List<String[]> data, List<ColumnDecorator> columnDecorators) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            sb.append(getTableRow(i, data.get(i), columnDecorators));
        }
        return sb.toString();
    }

    protected String getTableHeaders(List<ColumnDecorator> columnDecorators) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < columnDecorators.size(); i++) {
            ColumnDecorator decorator = columnDecorators.get(i);
            sb.append(html.getOpenTag(new Tag("th", decorator.getTableDataClass(), 3)));
            sb.append(html.getTag(new Tag("span", decorator.getSpanClass(), 4), decorator.getColumnTitle()));
            sb.append(html.getCloseTag(new Tag("th", 3)));
        }
        return sb.toString();
    }

    protected List<ColumnDecorator> getColumnDecorators() {
        List<ColumnDecorator> columnDecorators = new ArrayList<ColumnDecorator>();
        columnDecorators.add(new ColumnDecorator("image-column", "sort-header", ""));
        columnDecorators.add(new ColumnDecorator("name-column", "sort-header", "Name"));
        columnDecorators.add(new ColumnDecorator("last-modified-column", "sort-header", "Last Modified"));
        columnDecorators.add(new ColumnDecorator("size-column", "sort-header", "Size"));
        return columnDecorators;
    }

    protected void addDisplayRows(List<DisplayRow> displayRows, List<String[]> data) {
        for (DisplayRow displayRow : displayRows) {
            addDisplayRow(displayRow, data);
        }
    }

    protected void addDisplayRow(DisplayRow displayRow, List<String[]> data) {
        if (displayRow == null) {
            return;
        }
        String[] row = new String[4];
        row[0] = displayRow.getImage();
        row[1] = displayRow.getAhref();
        row[2] = displayRow.getLastModified();
        row[3] = displayRow.getSize();
        data.add(row);
    }

    /**
     * Convert a commonPrefix into a DisplayRow object for the UI
     */
    protected DisplayRow getUpOneDirectoryDisplayRow(String prefix, String delimiter) {
        if (StringUtils.isEmpty(prefix)) {
            return null;
        }

        // Create some UI friendly strings
        String image = "";
        String show = ".." + delimiter;
        String dest = getUpOneDirectoryPrefix(prefix, delimiter);
        String ahref = html.getHref(dest, show);
        String date = "";
        String size = "";

        // Store them in an object
        DisplayRow displayRow = new DisplayRow();
        displayRow.setImage(image);
        displayRow.setAhref(ahref);
        displayRow.setLastModified(date);
        displayRow.setSize(size);
        return displayRow;
    }

    /**
     * Convert a commonPrefix into a DisplayRow object for the UI
     */
    protected DisplayRow getDisplayRow(String commonPrefix, String prefix, String delimiter) {

        // Create some UI friendly strings
        String image = html.getImage(getDirectoryImage());
        String show = getShow(commonPrefix, prefix);
        String dest = delimiter + commonPrefix;
        String ahref = html.getHref(dest, show);
        String date = "-";
        String size = "-";

        // Store them in an object
        DisplayRow displayRow = new DisplayRow();
        displayRow.setImage(image);
        displayRow.setAhref(ahref);
        displayRow.setLastModified(date);
        displayRow.setSize(size);
        return displayRow;
    }

    protected List<DisplayRow> getDirectoryDisplayRows(ObjectListing objectListing, String prefix, String delimiter) {
        List<DisplayRow> displayRows = new ArrayList<DisplayRow>();
        for (String commonPrefix : objectListing.getCommonPrefixes()) {
            DisplayRow displayRow = getDisplayRow(commonPrefix, prefix, delimiter);
            if (displayRow == null) {
                continue;
            }
            displayRows.add(displayRow);
        }
        return displayRows;
    }

    protected boolean isDirectory(S3ObjectSummary summary, List<String> commonPrefixes, String delimiter) {
        String key = summary.getKey();
        for (String commonPrefix : commonPrefixes) {
            if ((delimiter + key).equals(commonPrefix)) {
                return true;
            }
        }
        return false;
    }

    protected List<DisplayRow> getObjectDisplayRows(ObjectListing objectListing, String prefix, String delimiter) {
        List<DisplayRow> displayRows = new ArrayList<DisplayRow>();
        for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
            if (isDirectory(summary, objectListing.getCommonPrefixes(), delimiter)) {
                continue;
            }
            DisplayRow displayRow = getDisplayRow(summary, prefix, delimiter);
            if (displayRow == null) {
                continue;
            }
            displayRows.add(displayRow);
        }
        return displayRows;
    }

    /**
     * Convert an S3ObjectSummary into a DisplayRow object for the UI
     */
    protected DisplayRow getDisplayRow(S3ObjectSummary summary, String prefix, String delimiter) {
        // Skip displaying "css" as a file if we are in the css directory
        String key = summary.getKey();
        if (key.equals(prefix)) {
            return null;
        }

        // Skip displaying "css/" as a file also
        if ((key + delimiter).equals(prefix)) {
            return null;
        }

        // Create some UI friendly strings
        String image = html.getImage(getFileImage());
        String show = getShow(key, prefix);
        String dest = delimiter + key;
        String ahref = html.getHref(dest, show);
        String date = dateFormatter.format(summary.getLastModified());
        String size = nf.format((summary.getSize() / 1024D)) + " KiB";

        // Store them in an object
        DisplayRow displayRow = new DisplayRow();
        displayRow.setImage(image);
        displayRow.setAhref(ahref);
        displayRow.setLastModified(date);
        displayRow.setSize(size);
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
