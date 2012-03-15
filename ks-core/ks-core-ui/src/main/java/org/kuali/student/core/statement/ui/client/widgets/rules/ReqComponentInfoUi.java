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

package org.kuali.student.core.statement.ui.client.widgets.rules;

import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;

/**
 * @author glindhol
 *
 */
public class ReqComponentInfoUi extends ReqComponentInfo {
	private static final long serialVersionUID = 1L;

	private String previewNaturalLanguageTranslation;

	public ReqComponentInfoUi() {
	}
	
	public String getPreviewNaturalLanguageTranslation() {
		return previewNaturalLanguageTranslation;
	}

	public void setPreviewNaturalLanguageTranslation(
			String previewNaturalLanguageTranslation) {
		this.previewNaturalLanguageTranslation = previewNaturalLanguageTranslation;
	}

//	@Deprecated
//	public void setMetaInfo(MetaInfo metaInfo) {
//		this.setMeta(metaInfo);
//		
//	}

}
