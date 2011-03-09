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
package org.kuali.student.enrollment.lui.infc;

import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.MetaInfc;

import java.util.Date;
import java.util.List;

public interface LuiInfc {

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * Code identifier/name for the LUI. This is typically used
     * human readable form (e.g. ENGL 100 section 123).
     */
    public void setLuiCode(String luiCode);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Code identifier/name for the LUI. This is typically used
     * human readable form (e.g. ENGL 100 section 123).
     */
    public String getLuiCode();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for a Canonical Learning Unit (CLU).
     */
    public void setCluId(String cluId);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for a Canonical Learning Unit (CLU).
     */
    public String getCluId();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for an Academic Time Period (ATP).
     */
    public void setAtpId(String atpId);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for an Academic Time Period (ATP).
     */
    public String getAtpId();

    /**
     * Set ????
     * <p/>
     * Type: Integer
     * <p/>
     * Maximum number of "seats" that the LUI will hold for registration.
     */
    public void setMaxSeats(Integer maxSeats);

    /**
     * Get ????
     * <p/>
     * Type: Integer
     * <p/>
     * Maximum number of "seats" that the LUI will hold for registration.
     */
    public Integer getMaxSeats();

    /**
     * Set ????
     * <p/>
     * Type: Date
     * <p/>
     * Date and time that this LUI became effective. This is a
     * concept to the effective date on enumerated values. When
     * expiration date has been specified, this field must be
     * than or equal to the expiration date.
     */
    public void setEffectiveDate(Date effectiveDate);

    /**
     * Get ????
     * <p/>
     * Type: Date
     * <p/>
     * Date and time that this LUI became effective. This is a
     * concept to the effective date on enumerated values. When
     * expiration date has been specified, this field must be
     * than or equal to the expiration date.
     */
    public Date getEffectiveDate();

    /**
     * Set ????
     * <p/>
     * Type: Date
     * <p/>
     * Date and time that this LUI expires. This is a similar
     * to the expiration date on enumerated values. If specified,
     * should be greater than or equal to the effective date. If
     * field is not specified, then no expiration date has
     * currently defined and should automatically be
     * greater than the effective date.
     */
    public void setExpirationDate(Date expirationDate);

    /**
     * Get ????
     * <p/>
     * Type: Date
     * <p/>
     * Date and time that this LUI expires. This is a similar
     * to the expiration date on enumerated values. If specified,
     * should be greater than or equal to the effective date. If
     * field is not specified, then no expiration date has
     * currently defined and should automatically be
     * greater than the effective date.
     */
    public Date getExpirationDate();

    /**
     * Set ????
     * <p/>
     * Type: List<AttributeInfc>
     * <p/>
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public void setAttributes(List<AttributeInfc> attributes);

    /**
     * Get ????
     * <p/>
     * Type: List<AttributeInfc>
     * <p/>
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public List<AttributeInfc> getAttributes();

    /**
     * Set ????
     * <p/>
     * Type: MetaInfo
     * <p/>
     * Create and last update info for the structure. This is
     * and treated as read only since the data is set by the
     * of the service during maintenance operations.
     */
    public void setMetaInfo(MetaInfc metaInfo);

    /**
     * Get ????
     * <p/>
     * Type: MetaInfo
     * <p/>
     * Create and last update info for the structure. This is
     * and treated as read only since the data is set by the
     * of the service during maintenance operations.
     */
    public MetaInfc getMetaInfo();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * The current status of the LUI. The values for this field
     * constrained to those in the luState enumeration. A
     * setup operation does not exist for retrieval of the meta
     * around this value.
     */
    public void setState(String state);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * The current status of the LUI. The values for this field
     * constrained to those in the luState enumeration. A
     * setup operation does not exist for retrieval of the meta
     * around this value.
     */
    public String getState();

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for a Learning Unit Instance (LUI). This
     * optional, due to the identifier being set at the time
     * creation. Once the LUI has been created, this should be seen
     * required.
     */
    public void setId(String id);

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for a Learning Unit Instance (LUI). This
     * optional, due to the identifier being set at the time
     * creation. Once the LUI has been created, this should be seen
     * required.
     */
    public String getId();
}

