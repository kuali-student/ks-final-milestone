/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.RichTextEditor;

/**
 * Sets to and from model data that is marked as rich text (it has plain and formatted text in the model)
 * 
 * @author Kuali Student Team
 *
 */
public class RichTextBinding extends ModelWidgetBindingSupport<RichTextEditor> {
    public static RichTextBinding INSTANCE = new RichTextBinding();

    private RichTextBinding() {}

    @Override
    public void setModelValue(RichTextEditor object, DataModel model, String path) {
        String richTextRoot = path + QueryPath.getPathSeparator();

        QueryPath qPath = QueryPath.parse(richTextRoot + "plain");

        String oldValue = model.get(qPath);
        String newValue = object.getText();

        if (!nullsafeEquals(oldValue, newValue)) {
            model.set(qPath, newValue);
            setDirtyFlag(model, qPath);
        }

        qPath = QueryPath.parse(richTextRoot + "formatted");

        oldValue = model.get(qPath);
        newValue = object.getHTML();

        if (!nullsafeEquals(oldValue, newValue)) {
            model.set(qPath, newValue);
            setDirtyFlag(model, qPath);
        }

        // TODO: Should these defaults be set in server assembly defaults?
        // Commenting type and state as it is not required for rich text
        // qPath = QueryPath.parse(richTextRoot + "type");
        // model.set(qPath, "kuali.not.applicable");
        //        
        // qPath = QueryPath.parse(richTextRoot + "state");
        // model.set(qPath, "(n/a)");
    }

    @Override
    public void setWidgetValue(RichTextEditor object, DataModel model, String path) {
        String richTextRoot = path + QueryPath.getPathSeparator();

        QueryPath qPath = QueryPath.parse(richTextRoot + "plain");
        String formatted = model.get(qPath);

        qPath = QueryPath.parse(richTextRoot + "formatted");
        String plain = model.get(qPath);

        if (formatted != null) {
            object.setHTML(formatted);
        } else if (plain != null) {
            object.setText(plain);
        } else {
            object.setHTML("");
        }
    }

}
