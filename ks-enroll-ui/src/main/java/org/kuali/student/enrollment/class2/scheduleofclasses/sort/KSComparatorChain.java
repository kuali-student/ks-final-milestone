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
 * Generic implementation of {@link ComparatorChain}, which allows sorting a model by multiple {@link KSComparator}
 *
 * @author Kuali Student Team
 */

public class KSComparatorChain implements Serializable{

    private ComparatorChain comparatorChain;
    private List<KSComparator> comparators;

    public KSComparatorChain(){
        comparatorChain = new ComparatorChain();
    }

    /**
     * Creates a new {@link ComparatorChain} with the passed in {@link KSComparator} list
     *
     * @param comparators
     */
    public void setComparators(List<KSComparator> comparators){
        if (comparators != null){
            this.comparators = comparators;
            initializeComparatorChain();
        }
    }

    /**
     * Adds a comparator to the chain at the given index.
     *
     * Note: Avoid this method to add multiple comparators. Use setComparators() in that case
     *
     * @param index
     * @param comparator
     */
    public void addComparator(int index, KSComparator comparator){
        if (comparator != null){
            comparators.add(index,comparator);
            initializeComparatorChain();
        }
    }


    protected void initializeComparatorChain(){
        BitSet bitSet = new BitSet(comparators.size());
        int index = 0;
        for (KSComparator comparator : comparators){
            bitSet.set(index++, comparator.isReverseSort());
        }
        comparatorChain = new ComparatorChain(comparators,bitSet);
    }

    /**
     * Sorts the model
     *
     * @param comparatorItems models to sort
     */
    public void sort(List<? extends ComparatorModel> comparatorItems){
        Collections.sort(comparatorItems, comparatorChain);
    }

    /**
     * Returns the comparator chain.
     *
     * @return comparatorChain
     */
    public ComparatorChain getComparatorChain() {
        return comparatorChain;
    }
}
