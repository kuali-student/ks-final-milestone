package org.kuali.rice.kim.permission.mock;

import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.krad.kim.ViewPermissionTypeServiceImpl;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import sun.java2d.pipe.LoopPipe;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 1/14/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class KsViewPermissionTypeService extends ViewPermissionTypeServiceImpl{

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
