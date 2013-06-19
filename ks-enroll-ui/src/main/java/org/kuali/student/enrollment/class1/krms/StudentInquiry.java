/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms;

import org.kuali.rice.krad.uif.widget.Inquiry;

/**
 * @author Kuali Student Team
 */
public class StudentInquiry extends Inquiry {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StudentInquiry.class);

    public StudentInquiry() {
        super();
    }

    /**
     * Builds the inquiry link based on the given inquiry class and parameters
     *
     * @param dataObject - parent object that contains the data (used to pull inquiry
     * parameters)
     * @param propertyName - name of the property the inquiry is set on
     * @param inquiryObjectClass - class of the object the inquiry should point to
     * @param inquiryParms - map of key field mappings for the inquiry
     */
    /*public void buildInquiryLink(Object dataObject, String propertyName, Class<?> inquiryObjectClass,
                                 Map<String, String> inquiryParms) {
        Properties urlParameters = new Properties();

        // Add additional parameters to url.
        for (Map.Entry<String, String> urlParameter : this.urlParameters.entrySet()) {
            urlParameters.put(urlParameter.getKey(), urlParameter.getValue());
        }

        urlParameters.put(UifParameters.DATA_OBJECT_CLASS_NAME, inquiryObjectClass.getName());
        urlParameters.put(UifParameters.METHOD_TO_CALL, UifConstants.MethodToCallNames.START);
        
        if(this.viewName != null && this.viewName.length() > 0){
            urlParameters.put(UifParameters.VIEW_NAME, this.viewName);
        }

        for (Map.Entry<String, String> inquiryParameter : inquiryParms.entrySet()) {
            String parameterName = inquiryParameter.getKey();

            Object parameterValue = ObjectPropertyUtils.getPropertyValue(dataObject, parameterName);

            // TODO: need general format util that uses spring
            if (parameterValue == null) {
                parameterValue = "";
            } else if (parameterValue instanceof java.sql.Date) {
                if (Formatter.findFormatter(parameterValue.getClass()) != null) {
                    Formatter formatter = Formatter.getFormatter(parameterValue.getClass());
                    parameterValue = formatter.format(parameterValue);
                }
            } else {
                parameterValue = parameterValue.toString();
            }

            // Encrypt value if it is a field that has restriction that prevents a value from being shown to
            // user, because we don't want the browser history to store the restricted attributes value in the URL
            if (KRADServiceLocatorWeb.getDataObjectAuthorizationService()
                    .attributeValueNeedsToBeEncryptedOnFormsAndLinks(inquiryObjectClass, inquiryParameter.getValue())) {
                try {
                    parameterValue = CoreApiServiceLocator.getEncryptionService().encrypt(parameterValue);
                } catch (GeneralSecurityException e) {
                    LOG.error("Exception while trying to encrypted value for inquiry framework.", e);
                    throw new RuntimeException(e);
                }
            }

            // add inquiry parameter to URL
            urlParameters.put(inquiryParameter.getValue(), parameterValue);
        }

        // build inquiry URL
        String inquiryUrl = "";

        // check for EBOs for an alternate inquiry URL
        ModuleService responsibleModuleService =
                KRADServiceLocatorWeb.getKualiModuleService().getResponsibleModuleService(inquiryObjectClass);
        if (responsibleModuleService != null && responsibleModuleService.isExternalizable(inquiryObjectClass)) {
            inquiryUrl = responsibleModuleService.getExternalizableDataObjectLookupUrl(inquiryObjectClass,
                    urlParameters);
        } else {
            inquiryUrl = UrlFactory.parameterizeUrl(getBaseInquiryUrl(), urlParameters);
        }

        getInquiryLinkField().setHrefText(inquiryUrl);

        // set inquiry title
        String linkTitle = createTitleText(inquiryObjectClass);
        linkTitle = LookupInquiryUtils.getLinkTitleText(linkTitle, inquiryObjectClass, getInquiryParameters());
        getInquiryLinkField().setTitle(linkTitle);

        setRender(true);
    }       */
}
