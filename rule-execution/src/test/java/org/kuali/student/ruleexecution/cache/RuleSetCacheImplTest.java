package org.kuali.student.ruleexecution.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.kuali.student.brms.agenda.entity.AgendaType;
import org.kuali.student.brms.agenda.entity.Anchor;
import org.kuali.student.brms.agenda.entity.AnchorType;
import org.kuali.student.ruleexecution.cache.RuleSetCache;
import org.kuali.student.ruleexecution.cache.RuleSetCacheImpl;

public class RuleSetCacheImplTest {
    
    @Test
    public void testAddAndGetRuleSet() {
        RuleSetCache cache = new RuleSetCacheImpl();
        String key = "key";
        String value = "value";
        String s1 = (String) cache.addObject( key, value );
        assertNull( s1 );
        String s2 = (String) cache.getObject( key );
        assertEquals( value, s2 );
    }

}
