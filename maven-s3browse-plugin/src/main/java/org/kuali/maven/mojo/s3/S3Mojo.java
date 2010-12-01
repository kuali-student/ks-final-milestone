package org.kuali.maven.mojo.s3;

public abstract class S3Mojo extends BaseMojo {

    /**
     * @parameter expression="${prefix}"
     */
    private String prefix;

    /**
     * @parameter expression="${delimiter}" default-value="/"
     */
    private String delimiter;

    /**
     * @parameter expression="${maxKeys}"
     */
    private Integer maxKeys;

    /**
     * @parameter expression="${accessKeyId}"
     */
    private String accessKeyId;

    /**
     * @parameter expression="${secretAccessKey}"
     */
    private String secretAccessKey;

    /**
     * @parameter expression="${bucket}"
     */
    private String bucket;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
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

    public Integer getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(Integer maxKeys) {
        this.maxKeys = maxKeys;
    }

}
