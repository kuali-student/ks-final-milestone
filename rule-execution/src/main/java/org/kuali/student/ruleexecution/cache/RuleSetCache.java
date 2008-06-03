package org.kuali.student.ruleexecution.cache;

public interface RuleSetCache {
    
    public Object addObject( Object key, Object object );

    public boolean containsObject( Object key );

    public Object getObject( Object key );

}
