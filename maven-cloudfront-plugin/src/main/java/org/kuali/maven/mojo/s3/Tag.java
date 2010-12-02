package org.kuali.maven.mojo.s3;

public class Tag {

    String name;
    String id;
    String clazz;

    public Tag() {
        this(null);
    }

    public Tag(String name) {
        this(name, null);
    }

    public Tag(String name, String clazz) {
        this(name, clazz, null);
    }

    public Tag(String name, String clazz, String id) {
        super();
        this.name = name;
        this.id = id;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

}
