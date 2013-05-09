/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.repository;

import java.util.Comparator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;

/**
 *
 * @author nwright
 */
public class CompoundPropositionComparator implements Comparator<PropositionDefinition> {

    public static final String DESCRIPTION_SORT_BY_PREFIX = "SORT BY: ";
    
    @Override
    public int compare(PropositionDefinition o1, PropositionDefinition o2) {
        String seq1 = buildKey (o1);
        String seq2 = buildKey (o2);
        return seq1.compareTo(seq2);
    }
    
    private String buildKey (PropositionDefinition prop) {
        if (prop.getDescription() != null) {
            if (prop.getDescription().startsWith(DESCRIPTION_SORT_BY_PREFIX)) {
                return prop.getDescription().substring(DESCRIPTION_SORT_BY_PREFIX.length ());
            }
        }
        if (prop.getId() != null) {
            return prop.getId ();
        }
        return "";
    }
}
