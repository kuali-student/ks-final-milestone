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
 * Created by vgadiyak on 1/16/13
 */
package org.kuali.student.enroll.kim.permission.type;

import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.krad.kim.ViewPermissionTypeServiceImpl;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KSViewPermissionTypeServiceImpl extends ViewPermissionTypeServiceImpl {

    @Override
    protected List<Permission> performPermissionMatches(Map<String, String> requestedDetails, List<Permission> permissionsList) {
        //Use the superclass to do normal matching
        List<Permission> matchedPermissions = super.performPermissionMatches(requestedDetails, permissionsList);    //To change body of overridden methods use File | Settings | File Templates.

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
