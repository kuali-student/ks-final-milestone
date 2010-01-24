package org.kuali.student.commons.ui.mvc.client;

import java.util.Set;

/**
 * Interface used with MetadataGenerator rebind generator. Used to implement some basic reflection capabilities for event
 * types.
 */
public interface HasMetadata {
    /**
     * Returns the names for all inherited classes and interfaces
     * 
     * @return the names for all inherited classes and interfaces
     */
    public Set<String> getTypeHierarchy();

    /**
     * Used for comparing type hierarchies
     * 
     * @param o
     *            HasMetadata instance to compare against
     * @return true if "this" is assignable from the base type of "o"
     */
    public boolean isAssignableFrom(HasMetadata o);

    /**
     * Used for comparing type hierarchies
     * 
     * @param o
     *            HasMetadata instance to compare against
     * @return true if "o" is assignable from the base type of "this"
     */
    public boolean isAssignableTo(HasMetadata o);

    /**
     * Creates a new instance.
     * 
     * @return new instance
     */
    public HasMetadata newInstance();

    /**
     * Returns the class of the type defined by the developer, rather than the generated implementation class
     * 
     * @return the class of the type defined by the developer, rather than the generated implementation class
     */
    public Class<? extends HasMetadata> getDefinitionClass();

    /**
     * Returns the formatted definition class name. Used for type hierarchy comparisons. Creates a normalized format
     * delimited by "." in the event of "$" and other tokens returned for nested classes.
     * 
     * @return the formatted definition class name
     */
    public String getFormattedDefinitionClassname();
}
