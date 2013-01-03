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
 * Created by mahtabme on 6/29/12
 */
package org.kuali.student.enrollment.test.util;

import org.kuali.student.r2.common.dto.*;

import static org.junit.Assert.*;

/**
 * This class is a helper class for simplifying how CRUD tests can be written.
 *
 * @author Kuali Student Team
 */
public class CrudInfoTester {

    ///////////////////////
    // DATA VARIABLES
    ///////////////////////

    /**
     * The context associated with the tester.
     */
    private ContextInfo contextInfo;

    /**
     * The principal id associated with the tester.
     */
    private String principalId;

    /**
     * The principal id 2 associated with the tester.
     */
    private String principalId2;

    /**
     * The Attribute tester.
     */
    private AttributeTester attributeTester;

    /**
     * The Meta tester.
     */
    private MetaTester metaTester;

    /**
     * The List of String tester.
     */
    private ListOfStringTester listOfStringTester;

    /**
     * The EntityInfoTester tester.
     */
    private EntityInfoTester entityInfoTester;

    /**
     * The IdEntityTester tester.
     */
    private IdEntityTester idEntityTester;

    /**
     * The RichTextTester tester.
     */
    private RichTextTester richTextTester;

    ///////////////////////
    // CONSTRUCTORS
    ///////////////////////

    public CrudInfoTester (String principalId, String principalId2, ContextInfo contextInfo) {
        this.principalId = principalId;
        this.principalId2 = principalId2;
        this.contextInfo = contextInfo;
        contextInfo.setPrincipalId(principalId);
        attributeTester = new AttributeTester();
        metaTester = new MetaTester();
        listOfStringTester = new ListOfStringTester();
        entityInfoTester = new EntityInfoTester();
        idEntityTester = new IdEntityTester();
        richTextTester = new RichTextTester();
    }

    public CrudInfoTester () {
        this ("123", "321", new ContextInfo());
    }

    ///////////////////////
    // GETTERS AND SETTERS
    ///////////////////////

    public ContextInfo getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(ContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getPrincipalId2() {
        return principalId2;
    }

    public void setPrincipalId2(String principalId2) {
        this.principalId2 = principalId2;
    }

    public AttributeTester getAttributeTester() {
        return attributeTester;
    }

    public void setAttributeTester(AttributeTester attributeTester) {
        this.attributeTester = attributeTester;
    }

    public MetaTester getMetaTester() {
        return metaTester;
    }

    public void setMetaTester(MetaTester metaTester) {
        this.metaTester = metaTester;
    }

    public ListOfStringTester getListOfStringTester() {
        return listOfStringTester;
    }

    public void setListOfStringTester(ListOfStringTester listOfStringTester) {
        this.listOfStringTester = listOfStringTester;
    }

    public EntityInfoTester getEntityInfoTester() {
        return entityInfoTester;
    }

    public void setEntityInfoTester(EntityInfoTester entityInfoTester) {
        this.entityInfoTester = entityInfoTester;
    }

    public IdEntityTester getIdEntityTester() {
        return idEntityTester;
    }

    public void setIdEntityTester(IdEntityTester idEntityTester) {
        this.idEntityTester = idEntityTester;
    }

    public RichTextTester getRichTextTester() {
        return richTextTester;
    }

    public void setRichTextTester(RichTextTester richTextTester) {
        this.richTextTester = richTextTester;
    }

    ///////////////////////
    // FUNCTIONALS
    ///////////////////////

    public void initializeInfoForTestCreate (EntityInfo expected, String typeKey, String stateKey) throws Exception {
        expected.setTypeKey(typeKey);
        expected.setStateKey(stateKey);
        expected.setName("Name1");
        expected.setDescr(new RichTextInfo("plain1", "formatted1"));
        getAttributeTester().add2ForCreate(expected.getAttributes());
    }

    public void testCreate (EntityInfo expected, EntityInfo actual) throws Exception {
        doCommonTests(expected, actual);
        getMetaTester().checkAfterCreate(actual.getMeta());
    }

    public void initializeInfoForTestRead (EntityInfo expected) throws Exception {
        clearAttributeIds(expected);
    }

    public void testRead (EntityInfo expected, EntityInfo actual) throws Exception {
        doCommonTests(expected, actual);
        getMetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
    }

    public void initializeInfoForTestUpdate (EntityInfo expected, String newStateKey) throws Exception {
        clearAttributeIds(expected);
        expected.setStateKey(newStateKey);
        expected.setName("Name2");
        expected.setDescr(new RichTextInfo("plain2", "formatted2"));
        getAttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
    }

    public void testUpdate (EntityInfo expected, EntityInfo actual) throws Exception {
        doCommonTests(expected, actual);
        assertEquals(expected.getName(), "Name2");
        getRichTextTester().check(expected.getDescr(), new RichTextInfo("plain2", "formatted2"));
        getMetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
    }

    public void testDelete (EntityInfo expected, EntityInfo actual, StatusInfo deleteStatus) {
        assertNotNull(deleteStatus);
        assertTrue(deleteStatus.getIsSuccess());
    }

    ///////////////////////
    // HELPERS
    ///////////////////////

    private void doCommonTests (EntityInfo expected, EntityInfo actual) throws Exception {
        if (actual instanceof IdEntityInfo) {
            IdEntityInfo expected_IEO = (IdEntityInfo) expected;
            IdEntityInfo actual_IEO = (IdEntityInfo) actual;
            assertNotNull(actual_IEO.getId());
            if (expected_IEO.getId()!=null) {
                assertEquals(expected_IEO.getId(), actual_IEO.getId());
            }
        }
        else if (actual instanceof KeyEntityInfo) {
            KeyEntityInfo expected_KEO = (KeyEntityInfo) expected;
            KeyEntityInfo actual_KEO = (KeyEntityInfo) actual;
            assertNotNull(actual_KEO.getKey());
            if (expected_KEO.getKey()!=null) {
                assertEquals(expected_KEO.getKey(), actual_KEO.getKey());
            }
        }
        getEntityInfoTester().check(expected, actual);
        getAttributeTester().check(expected.getAttributes(), actual.getAttributes());
        getRichTextTester().check(expected.getDescr(), actual.getDescr());
    }

    private void clearAttributeIds(EntityInfo expected) throws Exception {
        for (AttributeInfo itemInfo : expected.getAttributes()) {
            // clear out any id's set during the persistence
            // to let the checks work properly
            itemInfo.setId(null);
        }
    }
}