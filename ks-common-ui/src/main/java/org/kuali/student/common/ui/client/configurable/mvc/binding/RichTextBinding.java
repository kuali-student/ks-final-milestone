/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.common.ui.client.configurable.mvc.binding;

import org.kuali.student.common.assembly.client.DataModel;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.ui.client.widgets.RichTextEditor;

public class RichTextBinding implements ModelWidgetBinding<RichTextEditor>{
    public static RichTextBinding INSTANCE = new RichTextBinding();
    
    private RichTextBinding(){}

    @Override
    public void setModelValue(RichTextEditor object, DataModel model, String path) {
        String richTextRoot = path + QueryPath.getPathSeparator();
        
        QueryPath qPath = QueryPath.parse(richTextRoot + "plain");
        model.set(qPath, object.getText());
        
        qPath = QueryPath.parse(richTextRoot + "formatted");
        model.set(qPath, object.getHTML());

        //TODO: Should these defaults be set in server assembly defaults?
        qPath = QueryPath.parse(richTextRoot + "type");        
        model.set(qPath, "kuali.not.applicable");
        
        qPath = QueryPath.parse(richTextRoot + "state");
        model.set(qPath, "(n/a)");
    }

    @Override
    public void setWidgetValue(RichTextEditor object, DataModel model, String path) {        
        String richTextRoot = path + QueryPath.getPathSeparator();
        
        QueryPath qPath = QueryPath.parse(richTextRoot + "plain");       
        String formatted = model.get(qPath);
        
        qPath = QueryPath.parse(richTextRoot + "formatted");
        String plain = model.get(qPath);

        if (formatted != null){
            object.setHTML(formatted);
        } else if(plain != null){
            object.setText(plain);
        } else{
            object.setHTML("");
        }       
    }
}
