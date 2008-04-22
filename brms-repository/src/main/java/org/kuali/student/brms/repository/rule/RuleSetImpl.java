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

import java.util.ArrayList;
import java.util.List;

public class RuleSetImpl extends ItemImpl implements RuleSet {
    private List<Rule> rules = new ArrayList<Rule>();
    private byte[] compiledRulerSet = null;
    private Object compiledRuleSetObject = null;
    private String header = null;

    private boolean snapshot = false;

    public RuleSetImpl(String name) {
        super(name);
    }

    public RuleSetImpl(String uuid, String name) {
        super(uuid, name);
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    public List<Rule> getRules() {
        return this.rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public byte[] getCompiledRuleSet() {
        return this.compiledRulerSet;
    }

    public void setCompiledRuleSet(byte[] compiledRuleSet) {
        this.compiledRulerSet = compiledRuleSet;
    }

    public Object getCompiledRuleSetObject() {
        return this.compiledRuleSetObject;
    }

    public void setCompiledRuleSetObject(Object compiledRuleSetObject) {
        this.compiledRuleSetObject = compiledRuleSetObject;
    }

    public boolean isSnapshot() {
        return snapshot;
    }

    public void setSnapshot(boolean snapshot) {
        this.snapshot = snapshot;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
