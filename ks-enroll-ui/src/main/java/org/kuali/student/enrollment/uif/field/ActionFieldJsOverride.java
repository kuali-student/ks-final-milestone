package org.kuali.student.enrollment.uif.field;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.field.ActionField;

/**
 * This is somewhat of a hack, but is actually a valid implementation.
 * The purpose of this class prevents the setting of ClientSideJs by the CollectionGroupBuilder if
 * it already has a value set by the dictionary.
 */
public class ActionFieldJsOverride extends ActionField{

    @Override
    public void setActionScript(String js){
        if(StringUtils.isEmpty(this.getActionScript())){
            super.setActionScript(js);
        }
    }

    public void setJumpToIdAfterSubmit(){
        //do nothing, dont want this behavior
    }
}
