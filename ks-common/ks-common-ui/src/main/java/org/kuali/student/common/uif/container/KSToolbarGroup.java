package org.kuali.student.common.uif.container;

import org.apache.commons.beanutils.PropertyUtils;
import org.kuali.rice.krad.uif.container.GroupBase;

/**
 * Created with IntelliJ IDEA.
 * User: aliabad4
 * Date: 5/2/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class KSToolbarGroup extends GroupBase {

    private static final long serialVersionUID = 7230145606607506418L;

    private boolean noLeftBorder;

    public boolean isNoLeftBorder() {
        return noLeftBorder;
    }

    public void setNoLeftBorder(boolean noLeftBorder) {
        this.noLeftBorder = noLeftBorder;
    }

}
