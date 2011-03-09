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
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.kuali.student.common.infc.AttributeInfc;

@XmlRootElement
//@XmlJavaTypeAdapter(AnyTypeAdapter.class)
@XmlAccessorType(XmlAccessType.PROPERTY)
public interface LuiPersonRelationStateInfc {

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 public void setName(String name);

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 public String getName();

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 public void setDescr(String descr);

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 public String getDescr();

 /**
  * Set ????
  *
  * Type: Date
  *
  * ???
  */
 public void setEffectiveDate(Date effectiveDate);

 /**
  * Get ????
  *
  * Type: Date
  *
  * ???
  */
 public Date getEffectiveDate();

 /**
  * Set ????
  *
  * Type: Date
  *
  * ???
  */
 public void setExpirationDate(Date expirationDate);

 /**
  * Get ????
  *
  * Type: Date
  *
  * ???
  */
 public Date getExpirationDate();

 /**
  * Set ????
  *
  * Type: List<AttributeInfc>
  *
  * ???
  */
 public void setAttributes(List<AttributeInfc> attributes);

 /**
  * Get ????
  *Map
  * Type: List<AttributeInfc>
  *
  * ???
  */
 public List<AttributeInfc> getAttributes();

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 public void setKey(String key);

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 public String getKey();
}

