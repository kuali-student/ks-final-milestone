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
     * @param newName
     * the newName to set
     */
    public Tag(final String newName) {
        this(newName, null);
    }

    /**
     * @param newName
     * the newName to set
     * @param newClazz
     * the newClazz to set
     */
    public Tag(final String newName, final String newClazz) {
        this(newName, newClazz, null);
    }

    /**
     * @param newName
     * the newName to set
     * @param newClazz
     * the newClazz to set
     * @param newId
     * the newId to set
     */
    public Tag(final String newName, final String newClazz, final String newId) {
        super();
        this.name = newName;
        this.id = newId;
        this.clazz = newClazz;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param newName
     * the newName to set
     */
    public final void setName(final String newName) {
        this.name = newName;
    }

    /**
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /**
     * @param newId
     * the newId to set
     */
    public final void setId(final String newId) {
        this.id = newId;
    }

    /**
     * @return the clazz
     */
    public final String getClazz() {
        return clazz;
    }

    /**
     * @param newClazz
     * the newClazz to set
     */
    public final void setClazz(final String newClazz) {
        this.clazz = newClazz;
    }
}
