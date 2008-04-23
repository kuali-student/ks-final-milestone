/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.brms.repository.rule;

import java.util.List;

public interface RuleSet extends Item {

    public void addRule(Rule rule);

    public void setRules(List<Rule> rules);
    public List<Rule> getRules();

    public void setCompiledRuleSet(byte[] compiledRuleSet);
    public byte[] getCompiledRuleSet();

    public void setCompiledRuleSetObject(Object compiledRuleSetObject);
    public Object getCompiledRuleSetObject();

    public void addHeader(String header);

    public String getHeader();

    public void setHeaderList(List<String> header);

    public List<String> getHeaderList();
    
    public String getContent();
    
    public void setSnapshot(boolean snapshot);
    public boolean isSnapshot();

    public String getSnapshotName();

    public void setSnapshotName(String snapshotName);
}
