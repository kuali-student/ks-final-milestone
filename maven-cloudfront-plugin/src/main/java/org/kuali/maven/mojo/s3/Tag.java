package org.kuali.maven.mojo.s3;

public class Tag {

    int indent;
    String name;
    String id;
    String clazz;

    public Tag() {
        this(null, 0);
    }

    public Tag(String name) {
        this(name, 0);
    }

    public Tag(String name, int indent) {
        this(name, null, indent);
    }

    public Tag(String name, String clazz) {
        this(name, null, clazz, 0);
    }

    public Tag(String name, String clazz, int indent) {
        this(name, clazz, null, indent);
    }

    public Tag(String name, String clazz, String id, int indent) {
        super();
        this.indent = indent;
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

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }
}
