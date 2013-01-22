/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by vgadiyak on 1/15/13
 */
package org.kuali.student.enroll.kim.permission.type;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.impl.permission.PermissionBo;
import org.kuali.rice.krad.kim.ViewActionPermissionTypeServiceImpl;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KSViewActionPermissionTypeServiceImpl extends ViewActionPermissionTypeServiceImpl {

    @Override
    protected List<Permission> performPermissionMatches(Map<String, String> requestedDetails,
                                                        List<Permission> permissionsList) {

        String requestedFieldId = null;
        if (requestedDetails.containsKey(KimConstants.AttributeConstants.FIELD_ID)) {
            requestedFieldId = requestedDetails.get(KimConstants.AttributeConstants.FIELD_ID);
        }

        String requestedActionEvent = null;
        if (requestedDetails.containsKey(KimConstants.AttributeConstants.ACTION_EVENT)) {
            requestedActionEvent = requestedDetails.get(KimConstants.AttributeConstants.ACTION_EVENT);
        }

        List<Permission> matchingPermissions = new ArrayList<Permission>();
        for (Permission permission : permissionsList) {
            PermissionBo bo = PermissionBo.from(permission);

            String permissionFieldId = null;
            if (bo.getDetails().containsKey(KimConstants.AttributeConstants.FIELD_ID)) {
                permissionFieldId = bo.getDetails().get(KimConstants.AttributeConstants.FIELD_ID);
            }

            String permissionActionEvent = null;
            if (bo.getDetails().containsKey(KimConstants.AttributeConstants.ACTION_EVENT)) {
                permissionActionEvent = bo.getDetails().get(KimConstants.AttributeConstants.ACTION_EVENT);
            }

            if ((requestedFieldId != null) && (permissionFieldId != null) && StringUtils.equals(requestedFieldId,
                    permissionFieldId)) {
                matchingPermissions.add(permission);
            } else if ((requestedActionEvent != null) && (permissionActionEvent != null) && StringUtils.equals(
                    requestedActionEvent, permissionActionEvent)) {
                matchingPermissions.add(permission);
            }
        }

        List<Permission> matchedPermissions = super.performPermissionMatches(requestedDetails, matchingPermissions);

        //Loop through the matched permissions
        for(Iterator<Permission> iter = matchedPermissions.iterator();iter.hasNext();){
            Permission permission = iter.next();

            //Check if the permission has the 'permissionExpression' attribute. If so, evaluate the expression
            if(permission.getAttributes().containsKey("permissionExpression")){//TODO change the name of the attribute
                ExpressionParser parser = new SpelExpressionParser();
                Expression exp = parser.parseExpression(permission.getAttributes().get("permissionExpression"));//TODO cache the expressions

                if(!exp.getValue(requestedDetails, Boolean.class)){
                    //If the expression resolves to false then remove from the list of matched permissions
                    iter.remove();
                }
            }
        }

        return matchedPermissions;
    }
}
