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
package org.kuali.rice.krms.api.repository;

import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.krms.api.KrmsConstants;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Collection;

/**
 * Concrete model object implementation of a natural language tree immutable.
 * Instances of natural language tree can be (un)marshalled to and from XML.
 *
 * @see NaturalLanguageContract
 */
@XmlRootElement(name = NaturalLanguage.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = NaturalLanguage.Constants.TYPE_NAME, propOrder = {
        NaturalLanguage.Elements.KRMS_OBJECT_ID,
        NaturalLanguage.Elements.NATURAL_LANGUAGE,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public class NaturalLanguage extends AbstractDataTransferObject implements NaturalLanguageContract {

    private static final long serialVersionUID = 6112385261827152719L;

    @XmlElement(name = NaturalLanguage.Elements.KRMS_OBJECT_ID, required = false)
    private String krmsObjectId;
    @XmlElement(name = NaturalLanguage.Elements.NATURAL_LANGUAGE, required = false)
    private String naturalLanguage;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * This constructor should never be called. It is only present for use
     * during JAXB unmarshalling.
     */
    public NaturalLanguage() {
        this.krmsObjectId = null;
        this.naturalLanguage = null;
    }

    @Override
    public String getKrmsObjectId() {
        return krmsObjectId;
    }

    @Override
    public String getNaturalLanguage() {
        return naturalLanguage;
    }

    /**
     * Constructs a KRMS Repository Agenda object from the given builder. This
     * constructor is private and should only ever be invoked from the builder.
     *
     * @param builder the Builder from which to construct the Agenda
     */
    private NaturalLanguage(Builder builder) {
        this.krmsObjectId = builder.getKrmsObjectId();
        this.naturalLanguage = builder.getNaturalLanguage();
    }

    /**
     * This builder is used to construct instances of KRMS Repository Agenda. It
     * enforces the constraints of the {@link NaturalLanguageContract}.
     */
    public static class Builder implements NaturalLanguageContract, ModelBuilder, Serializable {

        private static final long serialVersionUID = -2354545388959879306L;

        private String krmsObjectId;
        private String naturalLanguage;

        /**
         * Private constructor for creating a builder with all of it's required
         * attributes.
         */
        private Builder() {
        }

        /**
         * Private constructor for creating a builder with all of it's required
         * attributes.
         */
        private Builder(String naturalLanguage, String krmsObjectId) {
            setNaturalLanguage(naturalLanguage);
            setKrmsObjectId(krmsObjectId);
        }

        @Override
        public String getKrmsObjectId() {
            return krmsObjectId;
        }

        public void setKrmsObjectId(String krmsObjectId) {
            this.krmsObjectId = krmsObjectId;
        }

        @Override
        public String getNaturalLanguage() {
            return naturalLanguage;
        }

        public void setNaturalLanguage(String naturalLanguage) {
            this.naturalLanguage = naturalLanguage;
        }

        /**
         * Creates a builder by populating it with data from the given
         * {@link NaturalLanguageContract}.
         *
         * @return an instance of the builder populated with data from the
         * contract
         * @throws IllegalArgumentException if the contract is null
         */
        public static NaturalLanguage.Builder create() {
            NaturalLanguage.Builder builder = new NaturalLanguage.Builder();
            return builder;
        }
        /**
         * Creates a builder by populating it with data from the given
         * {@link NaturalLanguageContract}.
         *
         * @param contract the contract from which to populate this builder
         * @return an instance of the builder populated with data from the
         * contract
         * @throws IllegalArgumentException if the contract is null
         */
        public static NaturalLanguage.Builder create(NaturalLanguageContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract is null");
            }
            NaturalLanguage.Builder builder = new NaturalLanguage.Builder(contract.getNaturalLanguage(), contract.getKrmsObjectId());

            return builder;
        }

        /**
         * Builds an instance of a Natural Language Tree based on the current state of the
         * builder.
         *
         * @return the fully-constructed Agenda
         */
        @Override
        public NaturalLanguage build() {
            return new NaturalLanguage(this);
        }
    }

    /**
     * Defines some constants used on this class.
     */
    public static class Constants {

        final static String ROOT_ELEMENT_NAME = "naturalLanguage";
        final static String TYPE_NAME = "NaturalLanguageType";
        final static String[] HASH_CODE_EQUALS_EXCLUDE = {"_futureElements"};
        public final static String EVENT = "Event";   // key for event attribute
    }

    /**
     * A private class which exposes constants which define the XML element
     * names to use when this object is marshalled to XML.
     */
    public static class Elements {

        final static String KRMS_OBJECT_ID = "krmsObjectId";
        final static String NATURAL_LANGUAGE = "naturalLanguage";
    }

    public static class Cache {

        public static final String NAME = KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0 + "/" + NaturalLanguage.Constants.TYPE_NAME;
    }
}
