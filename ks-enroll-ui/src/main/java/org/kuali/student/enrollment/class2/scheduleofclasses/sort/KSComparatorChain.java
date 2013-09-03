/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.sort;

import org.apache.commons.collections.comparators.ComparatorChain;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

/**
 * Generic implementation of {@link ComparatorChain}, which allows sorting a model by multiple {@link java.util.Comparator}
 *
 * @author Kuali Student Team
 */

public class KSComparatorChain implements Serializable{

    private ComparatorChain comparatorChain;

    public KSComparatorChain(){
        comparatorChain = new ComparatorChain();
    }

    /**
     * Creates a new {@link ComparatorChain} with the passed in {@link java.util.Comparator}
     *
     * @param comparators
     */
    public void setComparators(List<ComparatorElement> comparators){
        if (comparators != null){
            BitSet bitSet = new BitSet(comparators.size());
            int index = 0;
            for (ComparatorElement comparatorElement : comparators){
                bitSet.set(index++,comparatorElement.isReverseSearch());
            }
            comparatorChain = new ComparatorChain(comparators,bitSet);
        }
    }

    /**
     * Sorts the model
     *
     * @param comparatorItems models to sort
     */
    public void sort(List<? extends ComparatorModel> comparatorItems){
        Collections.sort(comparatorItems, comparatorChain);
    }
}
