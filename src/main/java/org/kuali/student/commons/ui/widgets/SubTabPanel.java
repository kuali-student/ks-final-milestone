package org.kuali.student.commons.ui.widgets;

import com.google.gwt.user.client.ui.TabPanel;

/**
 * @author Kuali Student Team
 *         </p>
 *         This class is used to set a different look for sub tab panels.
 *         <h3>CSS Style Examples</h3>
 * 
 * <pre>
 *  .KS-SubTabBar {
 *    background-color: transparent;
 *    background: transparent url(images/newBackground.png) repeat-x 0 -42px;
 *  }
 * 
 *  .KS-SubTabBar .gwt-TabBarFirst {
 *    width: 10px;  // first tab distance from the left 
 *  }
 * 
 *  .KS-SubTabBar .gwt-TabBarItem-wrapper {
 *    padding-left: 20px;  
 *      background: transparent url(images/newBackground.png) repeat-x 0 -42px;
 *    border-left: 0px solid white;  Spacing between tabs 
 *  }
 * 
 *  .KS-SubTabBar .gwt-TabBarItem-selected {
 *    cursor: default;
 *  }
 * 
 *  .KS-SubTabBar .gwt-TabBarItem-wrapper-selected {}
 * 
 *  .KS-SubTabBar .gwt-TabBarItem
 *  {
 *      padding-top: 0px;
 *      padding-bottom: 2px;
 *      padding-left: 0px;
 *      cursor: pointer;
 *      color: #333333;
 *      font-weight: bold;
 *      background-color: white;
 *      background: url(images/newBackground.png) repeat-x 0 -42px;
 *      background-repeat: no-repeat;
 *      background-attachment: scroll;
 *      background-x-position: right;
 *      background-y-position: top;
 *      margin-left: 0px;
 *      padding-right: 0px;
 *      text-align: center;
 *  }
 * </pre>
 */
public class SubTabPanel extends TabPanel {

    /**
     * 
     */
    public SubTabPanel() {
        super();
        addStyleName("KS-SubTabPanel");
        getTabBar().addStyleName("KS-SubTabBar");

    }

}
