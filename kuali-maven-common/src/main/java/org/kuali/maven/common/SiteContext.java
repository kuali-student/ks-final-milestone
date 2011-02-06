package org.kuali.maven.common;

public interface SiteContext {
    public String getOrganizationGroupId();

    public String getDownloadSnapshotPrefix();

    public String getDownloadReleasePrefix();

    public String getDownloadPrefix();

    public String getBucket();

    public String getHostname();

    public String getPublicUrlProtocol();

    public String getPublishUrlProtocol();
}
