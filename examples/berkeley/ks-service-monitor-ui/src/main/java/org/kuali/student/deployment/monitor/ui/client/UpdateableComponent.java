/**
 * Dec 1, 2008
 */
package org.kuali.student.deployment.monitor.ui.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;


/**
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 * @author Kuali Student
 * @see GWT In Action
 */
public abstract class UpdateableComponent extends Composite {

    //~ Instance fields --------------------------------------------------------

    private Timer timer;

    //~ Methods ----------------------------------------------------------------

    public abstract void update();

    public void startUpdateTimer(int seconds) {
        stopUpdateTimer();
        timer = new Timer() {
                public void run() {
                    update();
                }
            };
        timer.scheduleRepeating(seconds * 1000);
    }

    public void stopUpdateTimer() {

        if (timer == null)
            return;

        timer.cancel();
        timer = null;

    }

}
