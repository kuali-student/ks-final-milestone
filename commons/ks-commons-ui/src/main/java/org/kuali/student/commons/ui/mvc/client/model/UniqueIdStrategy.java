package org.kuali.student.commons.ui.mvc.client.model;

public interface UniqueIdStrategy<T extends Object> {
    public String getUniqueId(T object);
}
