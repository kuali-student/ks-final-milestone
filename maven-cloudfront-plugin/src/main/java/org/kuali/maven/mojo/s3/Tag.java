package org.kuali.maven.mojo.s3;

/**
 * Lightweight pojo for helping generate html.
 */
public class Tag {

    /**
     * The html tag name.
     */
    private String name;

    /**
     * The html id.
     */
    private String id;

    /**
     * The html class.
     */
    private String clazz;

    /**
     * No args constructor.
     */
    public Tag() {
        this(null);
    }

    /**
     * @param name
     * the name to set
     */
    public Tag(final String name) {
        this(name, null);
    }

    /**
     * @param name
     * the name to set
     * @param clazz
     * the clazz to set
     */
    public Tag(final String name, final String clazz) {
        this(name, clazz, null);
    }

    /**
     * @param name
     * the name to set
     * @param clazz
     * the clazz to set
     * @param id
     * the id to set
     */
    public Tag(final String name, final String clazz, final String id) {
        super();
        this.name = name;
        this.id = id;
        this.clazz = clazz;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     * the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     * the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return the clazz
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * @param clazz
     * the clazz to set
     */
    public void setClazz(final String clazz) {
        this.clazz = clazz;
    }
}
