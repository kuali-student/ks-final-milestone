package org.kuali.student.lum.nlt.naturallanguage.context;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.nlt.naturallanguage.util.LuStatementAnchor;

public class HeaderContextImpl extends AbstractContext<LuStatementAnchor> {
    /**
     * Creates the context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(LuStatementAnchor statementAnchor) throws DoesNotExistException {
        Clu clu = getClu(statementAnchor.getCluAnchorId());
    	Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put(CLU_TOKEN, clu);
        return contextMap;
    }
}
