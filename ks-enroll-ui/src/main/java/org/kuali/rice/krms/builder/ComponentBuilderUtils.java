package org.kuali.rice.krms.builder;

import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/05/23
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComponentBuilderUtils {

    public static void updateTermParameter(TermEditor term, String name, String value){
        //Update the termparameter value
        boolean updated = false;
        for(TermParameterEditor parm : term.getEditorParameters()){
            if(parm.getName().equals(name)){
                parm.setValue(value);
                updated = true;
            }
        }

        if(!updated){
            TermParameterEditor parm = new TermParameterEditor(name, value);
            term.getEditorParameters().add(parm);
        }
    }
}
