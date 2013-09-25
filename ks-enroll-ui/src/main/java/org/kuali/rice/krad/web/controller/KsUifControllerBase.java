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
 * Created by bobhurt on 5/1/13
 */
package org.kuali.rice.krad.web.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.field.AttributeQueryResult;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * This class overrides one or more methods from Rice's UifControllerBase class
 *
 * @author Kuali Student Team
 */
public abstract class KsUifControllerBase extends UifControllerBase {


    /**
     * Invoked to provide the options for a suggest widget. The valid options are retrieved by the associated
     * <code>AttributeQuery</code> for the field containing the suggest widget. The controller method picks
     * out the query parameters from the request and calls <code>AttributeQueryService</code> to perform the
     * suggest query and prepare the result object that will be exposed with JSON
     */
    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=performFieldSuggest")
    public
    @ResponseBody
    AttributeQueryResult performFieldSuggest(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) {

        // retrieve query fields from request
        Map<String, String> queryParameters = new HashMap<String, String>();
        for (Object parameterName : request.getParameterMap().keySet()) {
            if (parameterName.toString().startsWith(UifParameters.QUERY_PARAMETER + ".")) {
                String fieldName = StringUtils.substringAfter(parameterName.toString(),
                        UifParameters.QUERY_PARAMETER + ".");
                String fieldValue = request.getParameter(parameterName.toString());
                queryParameters.put(fieldName, fieldValue);
            }
        }

        // retrieve id for field to perform query for
        String queryFieldId = request.getParameter(UifParameters.QUERY_FIELD_ID);
        if (StringUtils.isBlank(queryFieldId)) {
            throw new RuntimeException("Unable to find id for field to perform query on under request parameter name: "
                    + UifParameters.QUERY_FIELD_ID);
        }

        // get the field term to match
        String queryTerm = request.getParameter(UifParameters.QUERY_TERM);
        Boolean isBlankQueryTermAllowed =
                "true".equalsIgnoreCase(request.getParameter("blankQueryTermAllowed"));
        if ( ! isBlankQueryTermAllowed) {
            if (StringUtils.isBlank(queryTerm)) {
                throw new RuntimeException(
                        "Unable to find id for query term value for attribute query on under request parameter name: "
                                + UifParameters.QUERY_TERM);
            }
        }

        // invoke attribute query service to perform the query
        AttributeQueryResult queryResult = KRADServiceLocatorWeb.getAttributeQueryService().performFieldSuggestQuery(
                form.getPostedView(), queryFieldId, queryTerm, queryParameters);

        return queryResult;
    }

}
