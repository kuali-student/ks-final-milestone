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

import org.kuali.student.common.infc.AttributeInfc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement
//@XmlJavaTypeAdapter(AnyTypeAdapter.class)
@XmlAccessorType(XmlAccessType.PROPERTY)
public interface LuiPersonRelationStateInfc {

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public void setName(String name);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getName();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public void setDescr(String descr);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getDescr();

    /**
     * Set ????
     * <p/>
     * Type: Date
     * <p/>
     * ???
     */
    public void setEffectiveDate(Date effectiveDate);

    /**
     * Get ????
     * <p/>
     * Type: Date
     * <p/>
     * ???
     */
    public Date getEffectiveDate();

    /**
     * Set ????
     * <p/>
     * Type: Date
     * <p/>
     * ???
     */
    public void setExpirationDate(Date expirationDate);

    /**
     * Get ????
     * <p/>
     * Type: Date
     * <p/>
     * ???
     */
    public Date getExpirationDate();

    /**
     * Set ????
     * <p/>
     * Type: List<AttributeInfc>
     * <p/>
     * ???
     */
    public void setAttributes(List<AttributeInfc> attributes);

    /**
     * Get ????
     * Map
     * Type: List<AttributeInfc>
     * <p/>
     * ???
     */
    public List<AttributeInfc> getAttributes();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public void setKey(String key);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getKey();
}

