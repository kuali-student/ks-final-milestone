package org.kuali.student.rules.repository.drools.rule;

import org.kuali.student.rules.repository.rule.Category;

public class DroolsCategoryImpl implements Category, java.io.Serializable {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;
    
    /** Category name */
    private String name;
    /** Category path */
    private String path;

    /**
     * Constructs a new category.
     * 
     * @param name Category name
     * @param path Category path
     */
    public DroolsCategoryImpl(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * Gets the category name.
     * 
     * @see org.kuali.student.rules.repository.rule.Category#getName()
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the category path.
     * 
     * @see org.kuali.student.rules.repository.rule.Category#getPath()
     */
    public String getPath() {
        return this.path;
    }

}
