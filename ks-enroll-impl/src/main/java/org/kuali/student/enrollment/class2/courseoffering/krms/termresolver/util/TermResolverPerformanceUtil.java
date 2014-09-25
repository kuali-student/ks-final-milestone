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
 * Created by pauldanielrichardson on 9/24/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class records the performance of individual term resolvers
 *
 * @author Kuali Student Team
 */
public class TermResolverPerformanceUtil {
    private static Map<String, Long> TOTAL_TIME = new ConcurrentHashMap<>();
    private static Map<String, Long> TOTAL_HITS = new ConcurrentHashMap<>();

    public static synchronized void putStatistics(String termResolver, DateTime start, DateTime end) {
        //spin the request off as a thread so that the calling method is not delayed
        Thread thread = new Thread(new StatisticsUpdater(termResolver, start, end));
        thread.start();
    }

    public static synchronized Map<String, List<String>> getStatistics() {
        Map<String, List<String>> statistics = new HashMap<>();
        for (Map.Entry<String, Long> entry: TOTAL_TIME.entrySet()) {
            Long totalTime = entry.getValue();
            Long totalHits = TOTAL_HITS.get(entry.getKey());
            if (totalTime != null && totalHits != null) {
                Long averageTime = totalTime / totalHits;
                List<String> stats = new ArrayList<>();
                stats.add("averageRequestTimeMillis="+averageTime);
                stats.add("totalRequestTimeMillies="+totalTime);
                stats.add("requestCount="+totalHits);
                statistics.put(entry.getKey(), stats);
            }
        }
        return statistics;
    }

    public static synchronized void clearStatistics() {
        TOTAL_HITS.clear();
        TOTAL_TIME.clear();
    }

    private static synchronized void updateStatistics(String termResolver, DateTime start, DateTime end) {
        Long time = end.getMillis() - start.getMillis();
        Long totalTime = TOTAL_TIME.get(termResolver);
        Long totalHits = TOTAL_HITS.get(termResolver);
        if (totalTime == null) {
            totalTime = time;
        } else {
            totalTime += time;
        }
        if (totalHits == null) {
            totalHits = 1l;
        } else {
            totalHits++;
        }
        TOTAL_TIME.put(termResolver, totalTime);
        TOTAL_HITS.put(termResolver, totalHits);
    }

    private static class StatisticsUpdater implements Runnable {

        private String termResolver;
        private DateTime start;
        private DateTime end;

        private StatisticsUpdater(String termResolver, DateTime start, DateTime end) {
            this.termResolver = termResolver;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            updateStatistics(termResolver, start, end);
        }
    }
}
