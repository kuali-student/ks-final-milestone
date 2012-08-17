/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by bobhurt on 8/7/12
 */
package org.kuali.student.enrollment.kitchensink;

import org.kuali.rice.krad.web.form.UifFormBase;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KitchenSinkForm extends UifFormBase {
    private static final long serialVersionUID = 4678031668930436995L;

    private String stringField1;
    private String stringField2;
    private List<KitchenSinkFormCollection1> collection;

    public KitchenSinkForm() {
        super();
    }

    @Override
    public void postBind(HttpServletRequest request) {
        super.postBind(request);
    }

    public String getStringField1() {
        return stringField1;
    }

    public void setStringField1(String stringField1) {
        this.stringField1 = stringField1;
    }

    public String getStringField2() {
        return stringField2;
    }

    public void setStringField2(String stringField2) {
        this.stringField2 = stringField2;
    }

    public List<KitchenSinkFormCollection1> getCollection() {
        return collection;
    }

    public void setCollection(List<KitchenSinkFormCollection1> collection) {
        this.collection = collection;
    }
}