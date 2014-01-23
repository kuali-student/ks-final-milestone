/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.common.uif.service.impl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.student.common.uif.service.KSViewHelperService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for the KS View helper service.
 *
 * @author Kuali Student Team
 */
public class KSViewHelperServiceImpl extends ViewHelperServiceImpl implements KSViewHelperService {
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(KSViewHelperServiceImpl.class);

    private transient StateService stateService;
    private transient TypeService typeService;

    private transient Map<String,Class> helperClasses;
    private transient Map<String,Object> helpers;

    public KSViewHelperServiceImpl(){
        super();
        helperClasses = new HashMap<String, Class>();
        helpers = new HashMap<String, Object>();
    }

    /**
     * This method creates a new context, which is needed for the service call.  This is to create
     * <code>ContextInfo</code> with the following information - current logged in user, current local
     * date and locale information
     *
     * @return a new ContextInfo
     */
    public ContextInfo createContextInfo(){
        return ContextUtils.createDefaultContextInfo();
    }

    /**
     * This method gets the <code>StateInfo</code> for a given state key
     *
     * @param stateKey
     * @return StateInfo for the given state key
     */
    public StateInfo getStateInfo(String stateKey){
        try {
            return getStateService().getState(stateKey, createContextInfo());
        }catch (Exception e){
            throw convertServiceExceptionsToUI(e);
        }

    }

    /**
     * This method gets the <code>TypeInfo</code> for a given type key
     *
     * @param typeKey
     * @return TypeInfo for the given type key
     */
    public TypeInfo getTypeInfo(String typeKey){
        try {
            return getTypeService().getType(typeKey, createContextInfo());
        }catch (Exception e){
            throw convertServiceExceptionsToUI(e);
        }
    }

    /**
     * This is a helper method which handles all the services exceptions. This can be the central point to handle all
     * the services exceptions. For now, we're converting all the exceptions to Runtime exceptions.
     *
     * Once we implement authz and when services fully implemented the decorators/exceptions, we need to
     * refactor to handle some of the exceptions with HandlerExceptionResolver (Note, underlying exception is passed to
     * the runtime exception as cause... So, this can be accessed at the exception resolver to determine the action)
     *
     * @param ex service exception to be handled
     * @return service exception wrapped in a RuntimeException instance
     */
    public RuntimeException convertServiceExceptionsToUI(Exception ex){

        if (LOG.isEnabledFor(Level.ERROR)){
            LOG.error(ex);
        }
         //Commented for now... once service throws MPE/IPE(KSENROLL-3446), needs to rework.
        /*StringBuilder refinedErrorMessage = new StringBuilder();
        refinedErrorMessage.append(ex.getMessage());
        refinedErrorMessage.append("[Caused at ");
        refinedErrorMessage.append(ex.getStackTrace()[0].getClass().getSimpleName() + ".");
        refinedErrorMessage.append(ex.getStackTrace()[0].getMethodName() + "()");
        refinedErrorMessage.append(ex.getStackTrace()[0].getLineNumber() + "]");*/

        if (ex instanceof DoesNotExistException){
            return new RuntimeException("Does Not Exists - " + ex.getMessage(),ex);
        } else if (ex instanceof InvalidParameterException){
            return new RuntimeException("Invalid parameter - " + ex.getMessage(),ex);
        } else if (ex instanceof MissingParameterException){
            return new RuntimeException("Missing parameter - " + ex.getMessage(),ex);
        } else if (ex instanceof OperationFailedException){
            return new RuntimeException("Operation Failed - " + ex.getMessage(),ex);
        } else if (ex instanceof PermissionDeniedException){
            return new RuntimeException("Permission Denied - " + ex.getMessage(),ex);
        } else {
            return new RuntimeException(ex.getMessage(),ex);
        }
    }

    /**
     * This is a map which holds all the helper classes by name. This can be used from the view xml file like
     *
     * <code>
     *     <property name="viewHelperService.helperClasses">
     *          <map>
     *			<entry key="publishHelper">
     *              <value  type="java.lang.Class">org.kuali.student.enrollment.class2.courseofferingset.service.impl.CourseOfferingSetPublishingHelper</value>
     *              </entry>
     * 		    </map>
     *      </property>
     * </code>
     *
     * And from the helper class, we can call like
     *
     * <code>
     *     CourseOfferingSetPublishingHelper mpeHelper = (CourseOfferingSetPublishingHelper)getHelper("publishHelper");
     * </code>
     *
     * @param helperClasses map of helper classes with name as keys and classes as values
     */
    public void setHelperClasses(Map<String, Class> helperClasses) {
        this.helperClasses = helperClasses;
    }

    /**
     * This creates an instance of the helper class if it's already not there.
     *
     * @param helperName
     * @return
     */
    public Object getHelper(String helperName) {
        Object helper = helpers.get(helperName);
        if (helper == null){
            Class clazz = helperClasses.get(helperName);
            if (clazz != null){
                helper = ObjectUtils.newInstance(clazz);
                helpers.put(helperName,helper);
            }
        }
        return helper;
    }

    protected StateService getStateService(){
        if (stateService == null){
            stateService = GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }

    protected TypeService getTypeService(){
        if (typeService == null){
            typeService = GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }
}
