package org.kuali.student.core.krms.proposition;

import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.type.PropositionTypeService;

import java.util.Map;

/**
 * Created by SW Genis on 2014/06/14.
 */
public class CustomPropositionTypeService implements PropositionTypeService {

    private Map<String, Proposition> propositionMap;

    @Override
    public Proposition loadProposition(PropositionDefinition propositionDefinition) {
        return propositionMap.get(propositionDefinition.getId());
    }

    public Map<String, Proposition> getPropositionMap() {
        return propositionMap;
    }

    public void setPropositionMap(Map<String, Proposition> propositionMap) {
        this.propositionMap = propositionMap;
    }

}
