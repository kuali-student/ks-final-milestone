package org.kuali.maven.mojo.s3;

/**
 * Pojo that represents one row in the directory listing of the contents of a directory in an S3 bucket
 */
public class DisplayRow {
    String image;
    String ahref;
    String lastModified;
    String size;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAhref() {
        return ahref;
    }

    public void setAhref(String ahref) {
        this.ahref = ahref;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String date) {
        this.lastModified = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
