/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.common.messages.dto;

import org.kuali.student.r2.common.messages.infc.LocaleKeys;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocaleKeysInfo", propOrder = {"locales", "_futureElements"})
public class LocaleKeysInfo implements LocaleKeys, java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement
	protected List<String> locales;

    @XmlAnyElement
    private List<Element> _futureElements;

    public LocaleKeysInfo() {
    }

    public LocaleKeysInfo(LocaleKeys locales) {
        if (null != locales) {
            this.locales = (null != locales.getLocales()) ? new ArrayList<String>(locales.getLocales()) : null;
        }
    }

	@Override
    public List<String> getLocales() {
		if (this.locales == null) {
			this.locales = new ArrayList<String>();
		}
		return this.locales;
	}

	public void setLocales(List<String> locales) {
		this.locales = locales;

	}
}
