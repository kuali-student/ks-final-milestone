/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r1.core.document.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.kuali.student.r1.common.dto.TypeInfo;


/**
 * Detailed information about a document type.
 *
 * @Author KSContractMojo
 * @Author tom
 * @Since Wed Aug 18 12:10:26 EDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/documentTypeInfo+Structure">DocumentTypeInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentTypeInfo extends TypeInfo {
    private static final long serialVersionUID = 1L;
}
