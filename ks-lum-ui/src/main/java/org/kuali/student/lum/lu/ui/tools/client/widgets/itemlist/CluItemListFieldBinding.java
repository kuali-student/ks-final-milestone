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

package org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluSetHelper;

public class CluItemListFieldBinding extends ItemListFieldBinding<CluItemValue>{

    /**
     * @see ItemListFieldBinding#setItemData(ItemValue, Data)
     */
    @Override
    public void setItemData(CluItemValue val, Data data) {
        CluHelper cluHelper = CluHelper.wrap(data);
        cluHelper.setId(val.getId());
        cluHelper.setName(val.getName());
    }

    /**
     * @see ItemListFieldBinding#getItemList(Data)
     */
    @Override
    public List<CluItemValue> getItemList(Data data) {
        if (data == null) return null;
        CluSetHelper cluSetHelper = CluSetHelper.wrap(data.getParent());
        Data clusData = cluSetHelper.getClus();
        List<CluItemValue> cluItemValues = null;
        for (Data.Property p : clusData) {
            CluHelper cluHelper = CluHelper.wrap((Data)p.getValue());
            CluItemValue cluItemValue = new CluItemValue();
            cluItemValues = (cluItemValues == null)? new ArrayList<CluItemValue>() :
                cluItemValues;
            cluItemValue.setId(cluHelper.getId());
            cluItemValue.setName(cluHelper.getName());
            cluItemValues.add(cluItemValue);
        }
        return cluItemValues;
    }

}
