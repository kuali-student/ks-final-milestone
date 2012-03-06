package org.kuali.student.lum.common.client.lo;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author Igor
 */
public class LoDisplayInfoSortedSet extends TreeSet<LoDisplayInfoHelper> {
    private static final long serialVersionUID = 1L;

    public LoDisplayInfoSortedSet() {
        super(new Comparator<LoDisplayInfoHelper>() {
            @Override
            public int compare(LoDisplayInfoHelper o1, LoDisplayInfoHelper o2) {
                LoInfoHelper o1InfoHelper = new LoInfoHelper(o1.getLoInfo());
                LoInfoHelper o2InfoHelper = new LoInfoHelper(o2.getLoInfo());
                int seq1 = -1;
                int seq2 = -1;

                seq1 = (o1InfoHelper.getSequence() == null) ?
                        0 : Integer.valueOf(o1InfoHelper.getSequence());
                seq2 = (o2InfoHelper.getSequence() == null) ?
                        0 : Integer.valueOf(o2InfoHelper.getSequence());
                return Integer.valueOf(seq1).compareTo(Integer.valueOf(seq2));
            }
        });
    }
}