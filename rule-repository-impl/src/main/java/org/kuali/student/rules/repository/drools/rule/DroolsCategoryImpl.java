package org.kuali.student.rules.repository.drools.rule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.kuali.student.rules.repository.rule.Category;

@XmlAccessorType(XmlAccessType.FIELD)
public class DroolsCategoryImpl implements Category, java.io.Serializable {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;
    
    /** Category name */
	@XmlAttribute
    private String name;
    /** Category path */
	@XmlAttribute
    private String path;

    public DroolsCategoryImpl() {}
    
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
