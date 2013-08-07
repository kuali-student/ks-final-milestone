/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.lum.clu.infc;

import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.HasType;
import org.kuali.student.r2.common.infc.RichText;

/**
 * Detailed information about an LU Code.
 * 
 * Each code is intended as a way to classify a learning unit.
 * Examples include: CIP 2000 and 2010 codes (classification of instructional programs) 
 * from the US government  but also include a university specific classification codes
 */
public interface LuCode extends HasAttributesAndMeta, HasType, HasId {

    /**
     * The Lu code's value
     *
     * @name Value
     */
    public String getValue();
    
    /**
     * An optional description describing the code's value
     *
     * @name Description
     */
    public RichText getDescr();
}
