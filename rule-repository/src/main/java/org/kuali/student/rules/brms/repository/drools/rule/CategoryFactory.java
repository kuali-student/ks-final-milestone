package org.kuali.student.rules.brms.repository.drools.rule;

import org.kuali.student.rules.brms.repository.rule.Category;

public class CategoryFactory {
    /** Category factory instance */
    private static CategoryFactory factory;

    /**
     * Private constructor
     */
    private CategoryFactory() { }

    /**
     * Gets an instance of this class.
     * 
     * @return A factory
     */
    public static CategoryFactory getInstance() {
        if ( factory == null ) {
            factory = new CategoryFactory();
        }
        return factory;
    }

    /**
     * Creates a new category.
     * 
     * @param name Category name
     * @param description Category description
     * @return A new category
     */
    public Category createDroolsCategory(final String name, final String path) {
        return new DroolsCategoryImpl(name, path);
    }
}
