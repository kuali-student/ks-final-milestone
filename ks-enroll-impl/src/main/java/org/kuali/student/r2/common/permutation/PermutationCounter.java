/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Charles on 10/1/12
 */
package org.kuali.student.r2.common.permutation;

import org.aspectj.weaver.Lint;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Used to generate permutations
 *
 * @author Kuali Student Team
 */
public class PermutationCounter {
    private int [] counter;
    private int [] maxSizes;
    private int totalIterations;

    public PermutationCounter(int numDigits) {
        counter = new int[numDigits];
        maxSizes = new int[numDigits];
        for (int i = 0; i < counter.length; i++) {
            counter[i] = 0;
            maxSizes[i] = -1;
        }
        totalIterations = 0;
    }

    public boolean isValid() {
        for (int i = 0; i < counter.length; i++) {
            if (maxSizes[i] == -1) {
                return false;
            }
        }
        return true;
    }
    /**
     *
     * @param maxSize The max value for this "digit" is maxSize - 1
     * @param index The digit position with 0 as the most significant digit
     */
    public void setMaxSizeAtIndex(int maxSize, int index) {
        maxSizes[index] = maxSize;
        totalIterations = _computerNumIterations();
    }

    public int size() {
        return counter.length;
    }

    public void clear() {
        for (int i = 0; i < counter.length; i++) {
            counter[i] = 0;
            maxSizes[i] = -1;
        }
    }

    public int getMaxSizeAtIndex(int index) {
        return maxSizes[index];
    }

    public int get(int index) {
        return counter[index];
    }

    public void increment() {
        // Although it doesn't matter much, index 0 is being treated as the most significant "digit"
        for (int i = counter.length - 1; i >= 0; i--) {
            counter[i] += 1;
            if (counter[i] >= maxSizes[i]) {
                counter[i] = 0;
            } else {
                break;
            }
        }
    }

    /**
     * Number of iterations to go through the entire counter.
     * @return number of iterations to go through entire counter once
     */
    public int numIterations() {
        return totalIterations;
    }

    private int _computerNumIterations() {
        int prod = 1;
        for (int i = 0; i < maxSizes.length; i++) {
            int max = maxSizes[i];
            if (max == -1){
                max = 0; // Force product to be zero
            }
            prod *= max;
        }
        return prod;
    }

    // ============================== Static method ===============================
    public static Set<Set<String>> computeMissingRegGroupAoIdsInCluster(ActivityOfferingClusterInfo cluster,
                                                                        List<RegistrationGroupInfo> currentRGs)
            throws DataValidationErrorException {

        // Initialize counter
        PermutationCounter counter = new PermutationCounter(cluster.getActivityOfferingSets().size());
        List<ActivityOfferingSetInfo> aoSets = cluster.getActivityOfferingSets();

        for (int i = 0; i < aoSets.size(); i++) {
            ActivityOfferingSetInfo setInfo = aoSets.get(i);
            int aoSetSize = setInfo.getActivityOfferingIds().size();
            counter.setMaxSizeAtIndex(aoSetSize, i);
        }
        // Make sure it's valid before going on
        if (!counter.isValid()) {
            throw new DataValidationErrorException("Counter is invalid: can't generate permutations");
        }
        // Compute every permutation
        Set<Set<String>> regGroupAoSetPermutations = new HashSet<Set<String>>();
        for (int i = 0; i < counter.numIterations(); i++, counter.increment()) {
            // Put each permutation of a reg group AOs into a set called regGroupAoIdSetPermutation
            Set<String> regGroupAoIdSetPermutation = new HashSet<String>();
            for (int aoSetIndex = 0; aoSetIndex < counter.size(); aoSetIndex++) {
                // Picks an AO ID from each AO type
                int aoIdIndex = counter.get(aoSetIndex);
                String aoId = cluster.getActivityOfferingSets().get(aoSetIndex).getActivityOfferingIds().get(aoIdIndex);
                regGroupAoIdSetPermutation.add(aoId);
            }
            // Add the set to a set of sets
            regGroupAoSetPermutations.add(regGroupAoIdSetPermutation);
        }
        // Find reg group AO sets for existing reg groups
        Set<Set<String>> actualRegGroupAoSets = new HashSet<Set<String>>();
        for (RegistrationGroupInfo rgInfo: currentRGs) {
            Set<String> regGroupAoIds = new HashSet<String>();
            regGroupAoIds.addAll(rgInfo.getActivityOfferingIds()); // Set of AO IDs in the reg group
            actualRegGroupAoSets.add(regGroupAoIds);
        }
        // Do a set difference to get remaining sets of AOs to be used to generate reg groups.
        Set<Set<String>> missingRegGroupAoSets = new HashSet<Set<String>>(regGroupAoSetPermutations);
        missingRegGroupAoSets.removeAll(actualRegGroupAoSets);
        return missingRegGroupAoSets;
    }
}
