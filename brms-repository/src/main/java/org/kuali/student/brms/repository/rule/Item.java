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
package org.kuali.student.brms.repository.rule;

import java.util.Calendar;

public interface Item {
    public String getName();

    public String getDescription();

    public void setCheckinComment(String checkinComment);

    public String getCheckinComment();

    public String getUUID();

    public String getFormat();

    public long getVersionNumber();

    public String getStatus();

    public Calendar getCreatedDate();

    public Calendar getLastModifiedDate();

    public boolean isArchived();

    public void setArchived(boolean archived);

    public boolean isHistorical();

    /**
     * Returns the UUID of the snapshot version. This will return null if this is the current version. Only historical
     * version will return a UUID.
     * 
     * @return UUID of the historical version
     */
    public String getVersionSnapshotUUID();
}
