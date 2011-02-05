package org.kuali.maven.plugin.ksite.mojo;

import org.apache.maven.project.MavenProject;

public interface SiteContext {
    public MavenProject getTargetProject();

    public String getDownloadSnapshotPrefix();

    public String getDownloadReleasePrefix();

    public String getDownloadPrefix();

    public String getBucket();

    public String getHostname();

    public String getPublicUrlProtocol();

    public String getPublishUrlProtocol();
}
