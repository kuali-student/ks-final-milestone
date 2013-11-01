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
 * Created by Charles on 4/25/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.Calendar;

/**
 * This class provides a basic timing stopwatch
 *
 * @author Kuali Student Team
 */
public class Stopwatch {
    private long startMillis;
    private long accumulatedMillis= 0;

    public Stopwatch() {
        startMillis = -1;
    }

    public Stopwatch(Stopwatch other) {
        this.startMillis = other.startMillis;
    }

    public void reset() {
        startMillis = Calendar.getInstance().getTimeInMillis();
    }

    public long diffInMillis() {
        if (startMillis == -1) {
            return -1;
        }
        return (Calendar.getInstance().getTimeInMillis() - startMillis);
    }

    public String compute() {
        if (startMillis == -1) {
            return "INVALID";
        }
        long nowMillis = Calendar.getInstance().getTimeInMillis();
        long diff = nowMillis - startMillis;
        return compute((int) diff);
    }

    public String computeAndAccumulate() {
        if (startMillis == -1) {
            return "INVALID";
        }
        long nowMillis = Calendar.getInstance().getTimeInMillis();
        long diff = nowMillis - startMillis;
        accumulatedMillis += diff;
        return compute(diff);
    }

    public String getAccumulated() {
        return compute((int) accumulatedMillis);
    }

    public void clearAccumulated() {
        accumulatedMillis = 0;
    }

    public static String compute(long intDiff) {
        double diff = intDiff;
        diff = diff / 1000; // Change to seconds
        String s = String.format("%.4f", diff);
        String result = s + "s";
        return result;
    }

}
