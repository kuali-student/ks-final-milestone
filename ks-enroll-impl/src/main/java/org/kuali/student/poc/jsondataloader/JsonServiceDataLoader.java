package org.kuali.student.poc.jsondataloader;

/**
 * Created by Charles on 8/11/2014.
 */
public interface JsonServiceDataLoader {
    public boolean acceptsType(String typeKey);

    public void clearData();
}
