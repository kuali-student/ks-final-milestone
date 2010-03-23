package org.kuali.student.core.organization.ui.client.theme;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.TreeImages;

public interface OrgTreeImages extends TreeImages{
    @Resource("org/kuali/student/core/organization/ui/theme/standard/public/images/treeOpen.gif")
    AbstractImagePrototype treeOpen();
    
    @Resource("org/kuali/student/core/organization/ui/theme/standard/public/images/treeClosed.gif")
    AbstractImagePrototype treeClosed();

}

