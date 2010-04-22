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

package org.kuali.student.common.ui.theme.custom.client;

import org.kuali.student.common.ui.client.theme.CommonCss;

import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;

public class CommonCssImpl implements CommonCss {

	@Override
	public String getCssString() {
       String injectString = "";
        for(ResourcePrototype r: KSClientBundle.INSTANCE.getResources()){
            if(r instanceof CssResource){
                if(((CssResource)r).getText() != null){
                    injectString = injectString + "\n" + (((CssResource)r).getText());
                }
            }
        }
        return injectString;
	}

	@Override
	public String getResetCssString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInitializeCssString() {
		// TODO Auto-generated method stub
		return null;
	}

}
