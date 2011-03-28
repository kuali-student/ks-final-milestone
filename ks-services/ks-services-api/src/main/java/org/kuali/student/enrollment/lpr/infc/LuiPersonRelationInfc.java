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
package org.kuali.student.enrollment.lpr.infc;

//import com.sun.xml.internal.bind.AnyTypeAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.kuali.student.common.dto.HasAttributesAndMetaInfc;
import org.kuali.student.common.infc.HasEffectiveDates;
import org.kuali.student.common.infc.HasId;
import org.kuali.student.common.infc.HasState;
import org.kuali.student.common.infc.HasType;

@XmlRootElement
//@XmlJavaTypeAdapter(AnyTypeAdapter.class)
@XmlAccessorType(XmlAccessType.PROPERTY)
public interface LuiPersonRelationInfc
        extends HasAttributesAndMetaInfc,
        HasId,
        HasType,
        HasState,
        HasEffectiveDates {

 /**
  * Get ????
  * <p/>
  * Type: String
  * <p/>
  * Name: LUI
  * Unique identifier for a Learning Unit Instance (LUI).
  */
 public String getLuiId();

 /**
  * Get ????
  * <p/>
  * Type: String
  * <p/>
  * Name: Person
  * Unique identifier for a person record.
  */
 public String getPersonId();
}

