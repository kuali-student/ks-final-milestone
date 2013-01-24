package org.kuali.student.enroll.kim.permission.type;

import org.kuali.rice.kim.api.permission.Permission;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 1/24/13
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class KSPermissionDetailsExpressionEvaluator {

    private static ExpressionParser parser = new SpelExpressionParser();
    public static final String PERMISSION_EXPRESSION = "permissionExpression";

    public static List<Permission> performPermissionMatches(Map<String, String> requestedDetails,
                                                        List<Permission> matchedPermissions) {
        //Loop through the matched permissions
        for(Iterator<Permission> iter = matchedPermissions.iterator();iter.hasNext();){

            Permission permission = iter.next();

            //Check if the permission has the 'permissionExpression' attribute. If so, evaluate the expression
            if(permission.getAttributes().containsKey(PERMISSION_EXPRESSION)){

                //Create a context with the permission details as the root
                StandardEvaluationContext ctx = new StandardEvaluationContext(requestedDetails);

                //Add the map details as variables in the context so we can use #var instead of ['var']
                for(Map.Entry<String,String> entry:requestedDetails.entrySet()){
                    ctx.setVariable(entry.getKey(), entry.getValue());
                }

                //Parse the expression
                Expression exp = parser.parseExpression(permission.getAttributes().get(PERMISSION_EXPRESSION));//TODO cache the parsed expressions

                if(!exp.getValue(ctx, Boolean.class)){
                    //If the expression resolves to false then remove from the list of matched permissions
                    iter.remove();
                }
            }
        }

        return matchedPermissions;
    }
}
