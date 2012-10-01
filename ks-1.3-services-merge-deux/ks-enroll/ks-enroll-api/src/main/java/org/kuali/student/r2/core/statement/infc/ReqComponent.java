/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.core.statement.infc;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.List;
import org.kuali.student.r2.common.dto.RichTextInfo;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface ReqComponent extends IdNamelessEntity, HasEffectiveDates {

    /**
     * Detailed information about a requirement component field value.
     *
     * @name Req Comp Fields
     * @required
     */
    List<? extends ReqCompField> getReqCompFields();

    /**
     * <code>naturalLanguageTranslation</code> attribute is generated on-the-fly
     * and should not be persisted.
     *
     * @name Natural Language Translation
     * @readOnly
     * @required
     */
    String getNaturalLanguageTranslation();

    /**
     * Narrative description of this requirement component
     * 
     * @name Description
     */
    public RichTextInfo getDescr();
}
