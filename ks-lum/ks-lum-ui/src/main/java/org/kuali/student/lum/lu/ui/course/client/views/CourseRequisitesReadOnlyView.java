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

package org.kuali.student.lum.lu.ui.course.client.views;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CourseRequisitesReadOnlyView extends Composite {
    DataModel dataModel=null;

    Controller myController;
    private VerticalPanel rootPanel = new VerticalPanel();

    public CourseRequisitesReadOnlyView(){
        super();
        super.initWidget(rootPanel);
    }
    /* (non-Javadoc)
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    @Override
    protected void onLoad() {
        super.onLoad();
        myController = Controller.findController(this);

        if(null==dataModel){
            //Get the Model from the controller and register a model change handler when the workflow model is updated
            myController.requestModel(LuData.class, new ModelRequestCallback<DataModel>(){

                @Override
                public void onRequestFail(Throwable cause) {
                    Window.alert("Model Request Failed. "+cause.getMessage());
                }

                @Override
                public void onModelReady(DataModel model) {
                    //After we get the model update immediately
                    dataModel = model;
                    updateWorkflow(dataModel);

                }
            });
        }else{

            //If the model has been set don't waste time finding it again and don't register
            //another change listener, just update
            updateWorkflow(dataModel);
        }
    }
    private void updateWorkflow(DataModel dataModel) {
        LuData data = (LuData)dataModel.getRoot();
        rootPanel.clear();
        if(data.getRuleInfos()!=null){
            for(RuleInfo ruleInfo:data.getRuleInfos()){
                KSLabel label = new KSLabel(ruleInfo.getNaturalLanguageForRuleEdit());
                rootPanel.add(label);
            }
        }
    }
}
