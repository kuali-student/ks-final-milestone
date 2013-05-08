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

    @Override
    public int compare(PropositionDefinition o1, PropositionDefinition o2) {
        String seq1 = o1.getId ();
        if (seq1 == null) {
            seq1 = "";
        }
        String seq2 = o2.getId ();
        if (seq2 == null) {
            seq2 = "";
        }
        return seq1.compareTo(seq2);
    }
}
