/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by pauldanielrichardson on 9/25/14
 */
package org.kuali.student.enrollment.registration.engine.util;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class records the performance of individual nodes
 *
 * @author Kuali Student Team
 */
public class RegEnginePerformanceUtil {

    public static String TERMS = "Term Resolvers";
    public static String NODES = "Nodes";
    public static String OTHER = "Other";

    private RegEnginePerformanceUtil() {}

    private static final Map<String, PerformanceUtil> utilMap = new ConcurrentHashMap<>();

    public static void putStatistics(String type, String node, DateTime start, DateTime end) {
        PerformanceUtil performanceUtil = getPerformanceUtil(type);
        performanceUtil.putStatistics(node, start, end);
    }

    public static Map<String, List<String>> getStatistics(String type) {
        PerformanceUtil performanceUtil = getPerformanceUtil(type);
        return performanceUtil.getStatistics();
    }

    public static void clearStatistics() {
        utilMap.clear();
    }

    public static Set<String> getTypes() {
        return Collections.unmodifiableSet(utilMap.keySet());
    }

    private static PerformanceUtil getPerformanceUtil(String type) {
        PerformanceUtil performanceUtil = utilMap.get(type);
        if (performanceUtil == null) {
            performanceUtil = new PerformanceUtil();
            utilMap.put(type, performanceUtil);
        }
        return performanceUtil;
    }

}
