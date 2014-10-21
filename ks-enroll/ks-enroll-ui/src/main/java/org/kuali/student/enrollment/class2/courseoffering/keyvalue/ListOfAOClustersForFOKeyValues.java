/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Decision for M6 - Garey wants to have logic on the Move To dropdown such that the user would not be shown a cluster
 * that has a different FO than the one in which the AO currently sits.
 * This can always be revisited in the future if the POs think it should be addressed in another manner.
 *
 */
public class ListOfAOClustersForFOKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The cluster drop-down list field would display:
     * 1)If a CO has no FO, show "No Format Offering and No Clusters" with an empty (invalid) key
     * 2)If a CO has only one FO, display a list of clusters for that FO
     * 3)If a CO has more than one FO, go through all AOs to check – if AO is selected, check the AO belongs to which FO,
     *   put the FO with selected AO(s) in a HashSet. at the end,
     *    3a) if the FO set is empty, it indicates that no AO has been selected. show "No Activity has been selected"
     *        with an empty (invalid) key.
     *    3b) if the FO set has one element, it indicates that all selected AOs belong to one FO. Populate all clusters
     *        only for that FO and display the list.
     *    3c) if the FO set has more than one element, it indicates that selected AOs belong to more than one FO. And
     *        we want to prevent users to move AOs across FOs, therefore show "Cannot move between format offerings"
     *        with an empty (invalid) key.
     *
     * @param model  -- the Form
     * @return a list of key-value pair for the display of the drop-down list
     */
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        CourseOfferingManagementForm theForm = (CourseOfferingManagementForm) model;

        //reset values:
        theForm.setSelectedFOIDForAOMove("");
        theForm.setSelectedFONameForAOMove("");

        List<KeyValue> keyValues = new ArrayList<KeyValue>();


        List<FormatOfferingInfo> foInfos = new ArrayList<FormatOfferingInfo>(theForm.getFoId2aoTypeMap().values());
        if(foInfos.size() < 1) {
            keyValues.add(new ConcreteKeyValue("", "No Format Offering and No Clusters"));
            theForm.setDisableMoveButtonForMoveAOCPopOver(true);
        }
        else if(foInfos.size() == 1){
            keyValues = populateClusterList(foInfos.get(0), theForm);
        }
        else if (foInfos.size() >= 1){
            Set<String> foSet = new HashSet<String>(foInfos.size());
            //check if selected AOs belong to different FOs
            List<ActivityOfferingClusterWrapper> clusterResultList = theForm.getClusterResultList();
            for(ActivityOfferingClusterWrapper clusterWrapper: clusterResultList) {
                List<ActivityOfferingWrapper> aoWrapperList = clusterWrapper.getAoWrapperList();
                for(ActivityOfferingWrapper aoWrapper: aoWrapperList){
                    if (aoWrapper.getIsCheckedByCluster()){
                        String selectedFoID = clusterWrapper.getFormatOfferingId();
                        if(foSet.isEmpty() || !foSet.contains(selectedFoID)) {
                            foSet.add(selectedFoID);
                        }
                        break;
                    }
                }
            }
            if(foSet.isEmpty()){
                //no AO has been selected
                keyValues.add(new ConcreteKeyValue("", "No Activity has been selected"));
                theForm.setDisableMoveButtonForMoveAOCPopOver(true);
            }
            else if(foSet.size()==1){
                //all selected AOs belong to one FO
                keyValues = populateClusterList(theForm.getFoId2aoTypeMap().get(foSet.iterator().next()), theForm);

            }
            else if (foSet.size()>1){
                //all selected AOs belong to more than one FO
                keyValues.add(new ConcreteKeyValue("", "Cannot move between format offerings"));
                theForm.setDisableMoveButtonForMoveAOCPopOver(true);
            }
        }

        return keyValues;
    }
    
    private List<KeyValue> populateClusterList(FormatOfferingInfo fo, CourseOfferingManagementForm theForm) {
        //set values for create New under Move To...
        theForm.setSelectedFOIDForAOMove(fo.getId());

        theForm.setSelectedFONameForAOMove(fo.getName());
        theForm.setDisableMoveButtonForMoveAOCPopOver(false);

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        for (ActivityOfferingClusterWrapper clusterWrapper : theForm.getClusterResultList()) {
            if(clusterWrapper.getFormatOfferingId()!=null && clusterWrapper.getFormatOfferingId().equals(fo.getId())){
                keyValues.add(new ConcreteKeyValue(clusterWrapper.getAoCluster().getId(), clusterWrapper.getAoCluster().getPrivateName()));
            }
        }
        return keyValues;
    }
}
