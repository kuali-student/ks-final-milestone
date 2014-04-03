/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by venkat on 4/2/14
 */
package org.kuali.student.cm.course.form;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Kuali Student Team
 */
public class CourseCreateUnitsContentOwner {

    protected String orgId;

    protected RenderHelper renderHelper;

    public CourseCreateUnitsContentOwner(){
        this(StringUtils.EMPTY,StringUtils.EMPTY);
    }

    public CourseCreateUnitsContentOwner(String orgId,String orgLongName){
        this.orgId = orgId;
        renderHelper = new RenderHelper();
        renderHelper.orgLongName = orgLongName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }


    public RenderHelper getRenderHelper() {
        return renderHelper;
    }

    public void setRenderHelper(RenderHelper renderHelper) {
        this.renderHelper = renderHelper;
    }

    public class RenderHelper {

        boolean newRow;
        String orgLongName;

        public RenderHelper(){

        }

        public boolean isNewRow() {
            return newRow;
        }

        public void setNewRow(boolean newRow) {
            this.newRow = newRow;
        }

        public String getOrgLongName() {
            return orgLongName;
        }

        public void setOrgLongName(String orgLongName) {
            this.orgLongName = orgLongName;
        }

    }




}
