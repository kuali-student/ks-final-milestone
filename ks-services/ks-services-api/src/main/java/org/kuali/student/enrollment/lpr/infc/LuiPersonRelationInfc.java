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
import org.kuali.student.common.infc.HasAttributesInfc;
import org.kuali.student.common.infc.HasEffectiveDatesInfc;
import org.kuali.student.common.infc.HasIdInfc;
import org.kuali.student.common.infc.HasMetaInfc;
import org.kuali.student.common.infc.HasStateInfc;
import org.kuali.student.common.infc.HasTypeInfc;

@XmlRootElement
//@XmlJavaTypeAdapter(AnyTypeAdapter.class)
@XmlAccessorType(XmlAccessType.PROPERTY)
public interface LuiPersonRelationInfc
        extends HasAttributesInfc,
        HasMetaInfc,
        HasIdInfc,
        HasTypeInfc,
        HasStateInfc,
        HasEffectiveDatesInfc {

 /**
  * Set ????
  * <p/>
  * Type: String
  * <p/>
  * Name: LUI
  * Unique identifier for a Learning Unit Instance (LUI).
  */
 public void setLuiId(String luiId);

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
  * Set ????
  * <p/>
  * Type: String
  * <p/>
  * Name: Person
  * Unique identifier for a person record.
  */
 public void setPersonId(String personId);

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

