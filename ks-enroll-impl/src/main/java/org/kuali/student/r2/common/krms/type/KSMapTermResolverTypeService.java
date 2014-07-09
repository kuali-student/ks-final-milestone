package org.kuali.student.r2.common.krms.type;

import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;

import java.util.Map;

/**
 * Created by Daniel on 7/7/14.
 */
public class KSMapTermResolverTypeService implements TermResolverTypeService {
    private Map<String, TermResolver<?>> termResolverMap;

    @Override
    public TermResolver<?> loadTermResolver(TermResolverDefinition termResolverDefinition) {
        if (!termResolverMap.containsKey(termResolverDefinition.getName())) {
            throw new RuntimeException("No term configured for key " + termResolverDefinition.getName());
        }
        return termResolverMap.get(termResolverDefinition.getName());
    }

    public void setTermResolverMap(Map<String, TermResolver<?>> termResolverMap) {
        this.termResolverMap = termResolverMap;
    }

    public Map<String, TermResolver<?>> getTermResolverMap() {
        return termResolverMap;
    }
}
