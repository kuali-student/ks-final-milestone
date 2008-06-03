package org.kuali.student.ruleexecution.cache;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.brms.agenda.entity.AgendaType;
import org.kuali.student.brms.agenda.entity.Anchor;

public class RuleSetCacheImpl implements RuleSetCache {

    private final Map<Object, Object> cache = new HashMap<Object, Object>();

    public RuleSetCacheImpl() {
    }

    public Object addObject( Object key, Object object ) {
        return cache.put( key, object );
    }

    public boolean containsObject( Object key ) {
        return cache.containsKey( key );
    }
    
    public Object getObject( Object key ) {
        return cache.get( key );
    }
    
}
