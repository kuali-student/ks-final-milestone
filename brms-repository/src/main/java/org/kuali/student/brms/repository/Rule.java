/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.brms.repository;

import java.util.Calendar;

public interface Rule extends Item {
    public void setBinaryContent(byte[] binaryContent);

    public byte[] getBinaryContent();

    public String getContent();

    public void setContent(String content);

    public String getFormat();

    public void setFormat(String format);

    public void setEffectiveDate(Calendar effectiveDate);

    public Calendar getEffectiveDate();

    public void setExpiryDate(Calendar expiryDate);

    public Calendar getExpiryDate();
}
