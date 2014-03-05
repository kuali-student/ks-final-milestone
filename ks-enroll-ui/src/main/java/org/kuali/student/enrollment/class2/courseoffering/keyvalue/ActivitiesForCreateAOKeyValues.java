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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class retrieves Activities based on the selection of a Format, and returns a key-value pair list of
 * the Activity Type name and the Activity Id
 *
 * @author andrewlubbers
 *
 */
public class ActivitiesForCreateAOKeyValues extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        CourseOfferingManagementForm coForm = (CourseOfferingManagementForm) model;
        FormatsForCreateAOKeyValues formatsForCreateAOKeyValues = new FormatsForCreateAOKeyValues();
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        String formatOfferingId = coForm.getFormatOfferingIdForNewAO();
        if (formatOfferingId==null || formatOfferingId.equals("")) {
            //  Just return if the CO has no formats (e.g. it was just created).
            if (coForm.getFoId2aoTypeMap().isEmpty()) {
                return keyValues;
            }
            formatOfferingId = formatsForCreateAOKeyValues.getFirstKey(model);
        }

        String courseId = coForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo().getCourseId();

        if(!StringUtils.isEmpty(formatOfferingId)) {
            try {
                FormatOfferingInfo foInfo = coForm.getFoId2aoTypeMap().get(formatOfferingId);

                if(foInfo == null) {
                    throw new RuntimeException("No FormatInfo found with id " + foInfo.getFormatId() + " in course " + courseId);
                }

                SearchRequestInfo request = new SearchRequestInfo("lu.search.relatedTypes");
                request.addParam("lu.queryParam.cluId", foInfo.getFormatId());
                request.addParam("lu.queryParam.luOptionalRelationType", "luLuRelationType.contains");
                SearchResultInfo result = CourseOfferingManagementUtil.getCluService().search(request, ContextUtils.createDefaultContextInfo());
                Map<String,String> activityIdToTypeMapKeys = new HashMap<String,String>();
                for (SearchResultRowInfo row: result.getRows()) {
                    String activityId = null;
                    String activityTypeKey = null;
                    for (SearchResultCellInfo cell: row.getCells()) {
                        if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                            activityId = cell.getValue();
                        }
                        if ("lu.resultColumn.cluType".equals(cell.getKey())) {
                            activityTypeKey = cell.getValue();
                        }
                    }
                    activityIdToTypeMapKeys.put(activityId, activityTypeKey);
                }

                //map AO types to ActivityInfos based on typeTypeRelation
                if (foInfo.getActivityOfferingTypeKeys() != null && foInfo.getActivityOfferingTypeKeys().size() > 0) {
                    for (String aoTypeKey : foInfo.getActivityOfferingTypeKeys()) {
                        List<TypeTypeRelationInfo> typeTypeRelationInfos = CourseOfferingManagementUtil.getTypeService().getTypeTypeRelationsByRelatedTypeAndType(aoTypeKey, TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, ContextUtils.getContextInfo());
                        
                        //Even though the possibility of many-to-many relationship between AO type and Activity type does exist, the relationship,
                        //most likely, is many-to-one. The following codes map an AO type to an ActivityInfo based on type key and use the mapped
                        //ActivityInfo to construct the key/value pair
                        if (typeTypeRelationInfos != null && typeTypeRelationInfos.size() > 0) {
                            for (Map.Entry<String, String> entry : activityIdToTypeMapKeys.entrySet()) {
                                for(TypeTypeRelationInfo typeTypeRelationInfo : typeTypeRelationInfos){
                                    if (entry.getValue().equals(typeTypeRelationInfo.getOwnerTypeKey())) {
                                        TypeInfo activityType = CourseOfferingManagementUtil.getTypeService().getType(entry.getValue(), ContextUtils.getContextInfo());
                                        keyValues.add(new ConcreteKeyValue(entry.getKey(), activityType.getName()));
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return keyValues;
    }
}
