/**
 * Dec 5, 2008
 */
package org.kuali.student.deployment.monitor.graphics;

import java.text.DateFormat;

import java.util.Date;


/**
 * Maintain memory usage data
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 * @author Kuali Student
 */
public class MemoryMonitorModel {

    //~ Static fields/initializers ---------------------------------------------

    private static final int TO_MB_DIVISOR = 1024 * 1024;

    //~ Instance fields --------------------------------------------------------

    private float freeMemory;
    private float totalMemory;
    private float initalMemory;
    private float maxMemory;
    private float peakUsage;
    private float currentUsage;
    private float currentUsagePercentage;


    private double[] samples;
    private int currentSample = 0;
    private int sampleCount;

    private Date peakUsageDate;
    private Runtime rt = Runtime.getRuntime();
    private DateFormat dateFmt = DateFormat.getDateTimeInstance(
            DateFormat.SHORT, DateFormat.SHORT);

    //~ Constructors -----------------------------------------------------------

    public MemoryMonitorModel(int sampleCount) {
        this.sampleCount = sampleCount;
        maxMemory = rt.maxMemory();
        initalMemory = rt.totalMemory();
        peakUsage = initalMemory;
        peakUsageDate = new Date();
        samples = new double[sampleCount];
        currentSample = 0;

    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Update current memory usage statistics.
     */
    public void update() {
        freeMemory = rt.freeMemory();
        totalMemory = rt.totalMemory();
        currentUsage = totalMemory - freeMemory;

        if (currentUsage > peakUsage) {
            peakUsage = currentUsage;
            peakUsageDate = new Date();
        }

        currentUsagePercentage = freeMemory / totalMemory;

        updateSamples();

    }

    /**
     * @return the currentUsagePercentage
     */
    public double getCurrentUsagePercentage() {
        return currentUsagePercentage;
    }

    /**
    * Produce a renderable version of the memory history
    * @param yOffset  bottom margin of the graph
    * @param height   height of the graph
    * @return the snapshot
    */
    Snapshot renderableSnapshot(int yOffset, int height) {
        Snapshot rval = new Snapshot();

        int[] tmp = new int[sampleCount];

        synchronized (this) {
            rval.count = currentSample;
            rval.allocationReport = reportAllocation();
            rval.peakUsageReport = reportPeakUsage();


            for (int i = 0; i < sampleCount; i++) {

                //System.out.println("sample: " + samples[i]);
                tmp[i] = (int) (yOffset + (height * samples[i]));
                //System .out.println("xform: " + tmp[i]);
            }
        }

        rval.samples = tmp;

        return rval;
    }


    /**
    * Set sample count (width of graph).
    * @param count
    */
    public void setSampleCount(int count) {
        sampleCount = count;
        handleResize();
    }


    /**
    * @return allocation info
    */
    public String reportAllocation() {
        StringBuilder buf = new StringBuilder(String.format("%.2fM allocated",
                    toMegaBytes(totalMemory)));

        if (totalMemory != initalMemory) {
            buf.append(String.format(" [ %.2fM ]", toMegaBytes(initalMemory)));
        }

        buf.append(String.format(" Max:  %.2fM", toMegaBytes(maxMemory)));

        return buf.toString();
    }

    /**
     * @return peak usage info
     */
    public String reportPeakUsage() {
        return String.format("%.2fM peak used at %s", toMegaBytes(peakUsage),
                dateFmt.format(peakUsageDate));

    }

    public String reportCurrentUsage() {
        return String.format("%.2fM in use %s", toMegaBytes(currentUsage),
                dateFmt.format(new Date()));
    }

    private double toMegaBytes(float val) {
        return val / TO_MB_DIVISOR;
    }

    private void updateSamples() {
        samples[currentSample] = (freeMemory / totalMemory);


        if ((currentSample + 2) == samples.length) {

            // throw out oldest point
            for (int j = 1; j < currentSample; j++) {
                samples[j - 1] = samples[j];
            }

            --currentSample;
        } else {
            currentSample++;
        }
    }

    private void handleResize() {

        if (samples.length != sampleCount) {
            double[] tmp = null;

            if (currentSample < sampleCount) {
                tmp = new double[currentSample];
                System.arraycopy(samples, 0, tmp, 0, tmp.length);
            } else {
                tmp = new double[sampleCount];
                System.arraycopy(samples, samples.length - tmp.length, tmp, 0,
                    tmp.length);
                currentSample = tmp.length - 2;
            }

            samples = new double[sampleCount];
            System.arraycopy(tmp, 0, samples, 0, tmp.length);
        }

    }

    //~ Inner Classes ----------------------------------------------------------

    static class Snapshot {

        //~ Instance fields ----------------------------------------------------

        public int count;
        public int[] samples;
        public String peakUsageReport;
        public String allocationReport;
    }
}
