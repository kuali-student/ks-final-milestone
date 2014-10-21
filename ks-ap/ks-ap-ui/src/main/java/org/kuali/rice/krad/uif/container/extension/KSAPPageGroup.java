package org.kuali.rice.krad.uif.container.extension;

import org.kuali.rice.krad.uif.container.PageGroupBase;

/**
 * Fix under KSAP-265
 * Added innerViewWrapperId data storage.
 */
public class KSAPPageGroup extends PageGroupBase {
    /**
     * Wrapper ID for pages intended for use as an inner view.
     * @see KULRICE-8862
     */
    private String innerViewWrapperId;

    /**
     * Get the inner view wrapper ID for this page, if this page is intended for use as an inner
     * view.
     *
     * @return The inner view wrapper ID for this page, null if the page is not used as an inner
     *         view.
     */
    public String getInnerViewWrapperId() {
        return this.innerViewWrapperId;
    }

    /**
     * Set the inner view wrapper ID for this page, if this page is intended for use as an inner
     * view.
     *
     * @param innerViewWrapperId The inner view wrapper ID for this page, null if the page is not
     *        used as an inner view.
     */
    public void setInnerViewWrapperId(String innerViewWrapperId) {
        this.innerViewWrapperId = innerViewWrapperId;
    }
}
