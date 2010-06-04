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

package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;

import java.util.Date;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoHelper;

public class CluSetHelper {

    private static final long serialVersionUID = 1;

    public enum Properties implements PropertyEnum
    {
        ID ("id"),
        ORGANIZATION ("organization"),
        DESCRIPTION ("description"),
        EFFECTIVE_DATE ("effectiveDate"),
        EXPIRATION_DATE ("expirationDate"),
        APPROVED_CLUS ("approvedClus"),
        PROPOSED_CLUS ("proposedClus"),
        // This is the union of APPROVED_CLUS, and PROPOSED_CLUS
        ALL_CLUS ("allClus"),
        CLUSETS ("clusets"),
        CLU_SET_RANGE ("clusetRange"),
        CLU_SET_RANGE_VIEW_DETAILS ("cluSetRangeViewDetails"),
        META_INFO ("metaInfo"),
        NAME ("name"),
        STATE ("state"),
        TYPE ("type"),
        REUSABLE ("reusable");

        private final String key;

        private Properties (final String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }
    private Data data;
    
    public CluSetHelper(Data data) {
        this.data = data;
    }
    
    public static CluSetHelper wrap(Data data) {
        if (data == null) {
            return null;
        }
        return new CluSetHelper(data);
    }
    
    public Data getData() {
        return this.data;
    }
    
    public void setId(String value) {
        data.set(Properties.ID.getKey(), value);
    }
    
    public String getId() {
        return (String) data.get(Properties.ID.getKey());
    }
    
    public void setOrganization(String value) {
        data.set(Properties.ORGANIZATION.getKey(), value);
    }
    public String getOrganization() {
        return (String) data.get(Properties.ORGANIZATION.getKey());
    }

    public void setDescription(String value) {
        data.set(Properties.DESCRIPTION.getKey(), value);
    }
    public String getDescription() {
        return (String) data.get (Properties.DESCRIPTION.getKey ());
    }

    public void setEffectiveDate(Date value) {
        data.set(Properties.EFFECTIVE_DATE.getKey(), value);
    }
    public Date getEffectiveDate() {
        return (Date) data.get(Properties.EFFECTIVE_DATE.getKey());
    }

    public void setExpirationDate(Date value) {
        data.set(Properties.EXPIRATION_DATE.getKey(), value);
    }
    public Date getExpirationDate() {
        return (Date) data.get(Properties.EXPIRATION_DATE.getKey());
    }
    
    public void setMetaInfo (MetaInfoHelper value) {
        data.set (Properties.META_INFO.getKey (), (value == null) ? null : value.getData ());
    }
    public MetaInfoHelper getMetaInfo () {
        return MetaInfoHelper.wrap ((Data) data.get (Properties.META_INFO.getKey ()));
    }

    public void setName(String name) {
        data.set(Properties.NAME.getKey(), name);
    }
    public String getName() {
        return (String) data.get(Properties.NAME.getKey());
    }

    public void setState(String state) {
        data.set(Properties.STATE.getKey(), state);
    }
    public String getState() {
        return (String) data.get(Properties.STATE.getKey());
    }

    public void setType(String type) {
        data.set(Properties.TYPE.getKey(), type);
    }
    public String getType() {
        return (String) data.get(Properties.TYPE.getKey());
    }

    public void setReusable(Boolean reusable) {
        Boolean newValue = reusable;
        if (reusable == null) {
            newValue = new Boolean(false);
        }
        data.set(Properties.REUSABLE.getKey(), newValue);
    }
    public Boolean getReusable() {
        Boolean reUsable = (Boolean) data.get(Properties.REUSABLE.getKey());
        if (reUsable == null) {
            reUsable = new Boolean(false);
        }
        return reUsable;
    }

    public void setApprovedClus(Data value) {
        data.set(Properties.APPROVED_CLUS.getKey(), value);
    }
    public Data getApprovedClus() {
        Data approvedClusData = data.get(Properties.APPROVED_CLUS.getKey());
        if (approvedClusData == null) {
            approvedClusData = new Data();
            setApprovedClus(approvedClusData);
        }
        return approvedClusData;
    }
    public void setProposedClus(Data value) {
        data.set(Properties.PROPOSED_CLUS.getKey(), value);
    }
    public Data getProposedClus() {
        Data proposedClusData = data.get(Properties.PROPOSED_CLUS.getKey());
        if (proposedClusData == null) {
            proposedClusData = new Data();
            setProposedClus(proposedClusData);
        }
        return proposedClusData;
    }
    public void setAllClus(Data value) {
        data.set(Properties.ALL_CLUS.getKey(), value);
    }
    public Data getAllClus() {
        Data allClusData = data.get(Properties.ALL_CLUS.getKey());
        if (allClusData == null) {
            allClusData = new Data();
            setAllClus(allClusData);
        }
        return allClusData;
    }
    public void setCluSets(Data value) {
        data.set(Properties.CLUSETS.getKey(), value);
    }
    public Data getCluSets() {
        Data cluSetData = data.get(Properties.CLUSETS.getKey());
        if (cluSetData == null) {
            cluSetData = new Data();
            setCluSets(cluSetData);
        }
        return cluSetData;
    }
    public void setCluRangeParams(Data value) {
        data.set(Properties.CLU_SET_RANGE.getKey(), value);
    }
    public CluSetRangeHelper getCluRangeParams() {
        Data cluRangeParamsData = data.get(Properties.CLU_SET_RANGE.getKey());
        if (cluRangeParamsData == null) {
            cluRangeParamsData = new Data();
            setCluRangeParams(cluRangeParamsData);
        }
        return new CluSetRangeHelper(cluRangeParamsData);
    }
    public void setCluRangeViewDetails(Data value) {
        data.set(Properties.CLU_SET_RANGE_VIEW_DETAILS.getKey(), value);
    }
    public Data getCluRangeViewDetails() {
        Data cluSetRangeViewDetailsData = data.get(Properties.CLU_SET_RANGE_VIEW_DETAILS.getKey());
        if (cluSetRangeViewDetailsData == null) {
            cluSetRangeViewDetailsData = new Data();
            setCluRangeViewDetails(cluSetRangeViewDetailsData);
        }
        return cluSetRangeViewDetailsData;
    }

}