package org.kuali.maven.mojo.s3;

import com.amazonaws.services.s3.model.ObjectListing;

public class S3PrefixContext {
    boolean isRoot;
    String defaultObjectKey;
    ObjectListing objectListing;
    String prefix;
    String html;
    S3BucketContext bucketContext;
    String browseHtmlKey;

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(final boolean isRoot) {
        this.isRoot = isRoot;
    }

    public String getDefaultObjectKey() {
        return defaultObjectKey;
    }

    public void setDefaultObjectKey(final String defaultObjectKey) {
        this.defaultObjectKey = defaultObjectKey;
    }

    public ObjectListing getObjectListing() {
        return objectListing;
    }

    public void setObjectListing(final ObjectListing objectListing) {
        this.objectListing = objectListing;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(final String html) {
        this.html = html;
    }

    public S3BucketContext getBucketContext() {
        return bucketContext;
    }

    public void setBucketContext(final S3BucketContext context) {
        this.bucketContext = context;
    }

    public String getBrowseHtmlKey() {
        return browseHtmlKey;
    }

    public void setBrowseHtmlKey(final String browseHtmlKey) {
        this.browseHtmlKey = browseHtmlKey;
    }
}
