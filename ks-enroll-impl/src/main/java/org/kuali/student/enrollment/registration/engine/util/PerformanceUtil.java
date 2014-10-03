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
package org.kuali.student.enrollment.registration.engine.util;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class records the performance of individual processes
 *
 * @author Kuali Student Team
 */
public class PerformanceUtil {
    private Map<String, Long> TOTAL_TIME = new ConcurrentHashMap<>();
    private Map<String, Long> TOTAL_HITS = new ConcurrentHashMap<>();

    public synchronized void putStatistics(String process, DateTime start, DateTime end) {
        //spin the request off as a thread so that the calling method is not delayed
        Thread thread = new Thread(new StatisticsUpdater(process, start, end));
        thread.start();
    }

    public synchronized Map<String, List<String>> getStatistics() {
        Map<String, List<String>> statistics = new HashMap<>();
        for (Map.Entry<String, Long> entry: TOTAL_TIME.entrySet()) {
            Long totalTime = entry.getValue();
            Long totalHits = TOTAL_HITS.get(entry.getKey());
            if (totalTime != null && totalHits != null) {
                Long averageTime = totalTime / totalHits;
                List<String> stats = new ArrayList<>();
                stats.add("averageRequestTimeMillis="+averageTime);
                stats.add("totalRequestTimeMillis="+totalTime);
                stats.add("requestCount="+totalHits);
                statistics.put(entry.getKey(), stats);
            }
        }
        return statistics;
    }

    public synchronized void clearStatistics() {
        TOTAL_HITS.clear();
        TOTAL_TIME.clear();
    }

    private synchronized void updateStatistics(String process, DateTime start, DateTime end) {
        Long time = end.getMillis() - start.getMillis();
        Long totalTime = TOTAL_TIME.get(process);
        Long totalHits = TOTAL_HITS.get(process);
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
        TOTAL_TIME.put(process, totalTime);
        TOTAL_HITS.put(process, totalHits);
    }

    private class StatisticsUpdater implements Runnable {

        private String process;
        private DateTime start;
        private DateTime end;

        private StatisticsUpdater(String process, DateTime start, DateTime end) {
            this.process = process;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            updateStatistics(process, start, end);
        }
    }
}
