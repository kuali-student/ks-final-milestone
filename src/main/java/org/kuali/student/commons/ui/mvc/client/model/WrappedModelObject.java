package org.kuali.student.commons.ui.mvc.client.model;

public class WrappedModelObject<T extends Object> implements ModelObject {
    private static final long serialVersionUID = 1L;
    
    T wrappedObject;
    UniqueIdStrategy<T> uniqueIdStrategy;
    
    public WrappedModelObject(T wrappedObject, UniqueIdStrategy<T> uniqueIdStrategy) {
        super();
        this.wrappedObject = wrappedObject;
        this.uniqueIdStrategy = uniqueIdStrategy;
    }

    public String getUniqueId() {
        return uniqueIdStrategy.getUniqueId(wrappedObject);
    }

    public T getWrappedObject() {
        return wrappedObject;
    }

    public UniqueIdStrategy<T> getUniqueIdStrategy() {
        return uniqueIdStrategy;
    }
}
