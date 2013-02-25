/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.permutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ocleirig
 *
 */
public final class PermutationUtils {

    /**
     *
     */
    private PermutationUtils() {
    }

    /**
     * Generates a list of permutations.  A permutation is a combination of one element from each data category.
     * <p/>
     * Each category corresponds to an index in the resultant permutation. e.g.
     * <p/>
     * "LEC" -> "A", "B"
     * <p/>
     * "LAB" -> "Z"
     * <p/>
     * Permutations:
     * "A", "Z"
     * "B", "Z"
     * <p/>
     * The index 0 is for type "LEC" and index 1 for type "LAB"
     * <p/>
     * <p/>
     * This method calls itself recursively to descend the list of available categories and build out each permutation.
     *
     * @param keyList               The ordered list of categories available.
     * @param prefix                The built up prefix
     * @param dataMap               The category to list of elements for that category.
     * @param generatedPermutations The list of generatedPermutations to this point.
     */
    public static void generatePermutations(List<String> keyList,
                                            List<String> prefix, Map<String, List<String>> dataMap,
                                            List<List<String>> generatedPermutations) {

        if (keyList.isEmpty()) {
            generatedPermutations.add(prefix);
        } else {
            String type = keyList.get(0);
            List<String> typeOptionsList = dataMap.get(type);

            if (typeOptionsList == null) {
                // This is needed to prevent a null pointer exception.  This will also cause
                // generatedPermutations will be empty--which it should be.
                return;
            }

            for (String option : typeOptionsList) {
                List<String> nextPrefix = new ArrayList<String>();
                nextPrefix.addAll(prefix);
                nextPrefix.add(option);
                Map<String, List<String>> nextMap = new HashMap<String, List<String>>();
                nextMap.putAll(dataMap);
                nextMap.remove(type);
                List<String> nextKeyList = new ArrayList<String>(keyList);
                nextKeyList.remove(type);
                generatePermutations(nextKeyList, nextPrefix, nextMap,
                        generatedPermutations);

            }
        }
    }

}
