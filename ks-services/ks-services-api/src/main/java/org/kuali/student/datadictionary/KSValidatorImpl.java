/*
 * Copyright 2005-2007 The Kuali Foundation
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
package org.kuali.student.datadictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.datadictionary.DataDictionaryEntry;
import org.kuali.rice.kns.datadictionary.exception.AttributeValidationException;
import org.kuali.rice.kns.datadictionary.validation.AttributeValueReader;
import org.kuali.rice.kns.datadictionary.validation.DataType;
import org.kuali.rice.kns.datadictionary.validation.DictionaryObjectAttributeValueReader;
import org.kuali.rice.kns.datadictionary.validation.ValidationUtils;
import org.kuali.rice.kns.datadictionary.validation.capability.Constrainable;
import org.kuali.rice.kns.datadictionary.validation.capability.HierarchicallyConstrainable;
import org.kuali.rice.kns.datadictionary.validation.constraint.Constraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.DataTypeConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.provider.AttributeDefinitionConstraintProvider;
import org.kuali.rice.kns.datadictionary.validation.constraint.provider.ConstraintProvider;
import org.kuali.rice.kns.datadictionary.validation.processor.CollectionConstraintProcessor;
import org.kuali.rice.kns.datadictionary.validation.processor.CollectionSizeConstraintProcessor;
import org.kuali.rice.kns.datadictionary.validation.processor.ConstraintProcessor;
import org.kuali.rice.kns.datadictionary.validation.processor.ExistenceConstraintProcessor;
import org.kuali.rice.kns.datadictionary.validation.processor.LengthConstraintProcessor;
import org.kuali.rice.kns.datadictionary.validation.processor.ValidCharactersConstraintProcessor;
import org.kuali.rice.kns.datadictionary.validation.result.ConstraintValidationResult;
import org.kuali.rice.kns.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.kns.datadictionary.validation.result.ProcessorResult;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.infc.ValidationResultInfc;

/**
 * this is an attempt to write cut and paste the validation logic to do what I need it to do and just that.

 * @author nwright
 */
public class KSValidatorImpl implements ValidatorInfc {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KSValidatorImpl.class);
    private DictionaryServiceInfc dictionaryService;

    @Override
    public List<ValidationResultInfc> validate(String validationType, Object info) {
        String entryName = info.getClass().getName();
        DataDictionaryEntry dictEntry = this.dictionaryService.getDataDictionaryEntry(entryName);

        AttributeValueReader reader = new DictionaryObjectAttributeValueReader(info, entryName, dictEntry);
        reader.setAttributeName(null);
        Stack<String> parents = new Stack<String>();

        DictionaryValidationResult result = new DictionaryValidationResult();
        validateObject(result, reader, parents, false);
        List<ValidationResultInfc> vris = transform(result);
        return vris;
    }

    private List<ValidationResultInfc> transform(DictionaryValidationResult dvr) {
        List<ValidationResultInfc> vrs = new ArrayList<ValidationResultInfc>();
        Iterator<ConstraintValidationResult> it = dvr.iterator();
        while (it.hasNext()) {
            ValidationResultInfo vr = null;
            ConstraintValidationResult cvr = it.next();
            switch (cvr.getStatus()) {
                case OK:
                    continue;
                case NOCONSTRAINT:
                    continue;
                case INAPPLICABLE:
                    continue;
                case ERROR:
                    vr = new ValidationResultInfo();
                    vr.setElement(cvr.getAttributeName());
                    vr.setError(cvr.getErrorKey());
                    vrs.add(vr);
                case WARN:
                    vr = new ValidationResultInfo();
                    vr.setElement(cvr.getAttributeName());
                    vr.setWarning(cvr.getErrorKey());
                    vrs.add(vr);
            }
        }

        return vrs;
    }

    private void validateObject(DictionaryValidationResult result, AttributeValueReader attributeValueReader, Stack<String> elementStack, boolean doOptionalProcessing) throws AttributeValidationException {

        // This method can be called recursively on objects that are themselves attributes of other objects, in which case a path
        // will be maintained in the attribute value reader, using the standard bean notation with "." delimiters
        // for example on a Car object the business object volume control knob might be indicated by "dashboard.radio.volumeControl"
        String attributePath = attributeValueReader.getPath();

        // If this attribute path is something other than "" when trimmed of whitespace, then we can safely assume we're dealing with an attribute object
        boolean isObjectAnAttribute = StringUtils.isNotBlank(attributePath);

        // The KS code kept track of the recursion using a stack - presumably for error checking - but it is not currently being used by this code and should probably be
        // eliminated or made use of in some way.
        if (isObjectAnAttribute) {
            elementStack.push(attributePath);
        }

        // If the entry itself is constrainable then the attribute value reader will return it here and we'll need to check if it has any constraints
        Constrainable entry = attributeValueReader.getEntry();

        if (entry != null) {
            validateConstrainableObject(result, attributeValueReader.getObject(), entry, attributeValueReader, elementStack, doOptionalProcessing);
        }

        List<Constrainable> definitions = attributeValueReader.getDefinitions();

        // Exit if the attribute value reader has no child definitions
        if (null == definitions) {
            return;
        }

        // Otherwise, iterate through those definitions and
        for (Constrainable definition : definitions) {
            if (definition == null) {
                continue;
            }

            String attributeName = definition.getName();
            attributeValueReader.setAttributeName(attributeName);

            validateAttribute(result, definition, attributeValueReader, elementStack, doOptionalProcessing);

            // FIXME: Need to check that it's okay that custom validators are no longer possible - alternative is to inject custom processors
        }

        if (isObjectAnAttribute) {
            elementStack.pop();
        }

    }

    private void processObject(DictionaryValidationResult result, Constrainable definition, Object value, AttributeValueReader passedAttributeValueReader, Stack<String> elementStack, boolean checkIfRequired, boolean isComplex) {
        if (isComplex) {
            // In the case of complex objects, need to build a new attribute value reader for this object and its dictionary entry
            AttributeValueReader attributeValueReader = resolveAttributeValueReader(definition, value, passedAttributeValueReader, elementStack, isComplex);
            validateObject(result, attributeValueReader, elementStack, checkIfRequired);
        } else {
            processElementConstraints(result, value, definition, passedAttributeValueReader, elementStack, checkIfRequired);
        }
    }

    private void processCollection(DictionaryValidationResult result, Constrainable definition, Collection<?> collection, AttributeValueReader passedAttributeValueReader, Stack<String> elementStack, boolean checkIfRequired, boolean isComplex) {
        int i = 0;
        for (Object element : collection) {
            elementStack.push(Integer.toString(i));
            if (isComplex) {
                // In the case of complex objects, have to resolve a new attribute value reader based on the child entry name and this particular element (as the business object)
                AttributeValueReader attributeValueReader = resolveAttributeValueReader(definition, element, passedAttributeValueReader, elementStack, isComplex);
                validateObject(result, attributeValueReader, elementStack, checkIfRequired);
            } else {
                processElementConstraints(result, element, definition, passedAttributeValueReader, elementStack, checkIfRequired);
            }

            elementStack.pop();
            i++;
        }

        processCollectionConstraints(result, collection, definition, passedAttributeValueReader, elementStack, checkIfRequired);
    }

    private void processElementConstraints(DictionaryValidationResult result, Object value, Constrainable definition, AttributeValueReader attributeValueReader, Stack<String> elementStack, boolean doOptionalProcessing) {
        List <ConstraintProcessor> ecp = new ArrayList ();
        ecp.add(new ExistenceConstraintProcessor ());
        ecp.add(new LengthConstraintProcessor ());
        ecp.add(new ValidCharactersConstraintProcessor ());
        processConstraints(result, ecp, value, definition, attributeValueReader, elementStack, doOptionalProcessing);
    }

    private void processCollectionConstraints(DictionaryValidationResult result, Collection<?> collection, Constrainable definition, AttributeValueReader attributeValueReader, Stack<String> elementStack, boolean doOptionalProcessing) {
        List<CollectionConstraintProcessor> list = new ArrayList ();
        CollectionSizeConstraintProcessor cscp = new CollectionSizeConstraintProcessor ();
        list.add (cscp);
        processConstraints(result, list, collection, definition, attributeValueReader, elementStack, doOptionalProcessing);
    }

    @SuppressWarnings("rawtypes")
    private void processConstraints(DictionaryValidationResult result, List<? extends ConstraintProcessor> constraintProcessors, Object object, Constrainable definition, AttributeValueReader attributeValueReader, Stack<String> elementStack, boolean doOptionalProcessing) {
        List<ConstraintProvider> providers = new ArrayList<ConstraintProvider> ();
        AttributeDefinitionConstraintProvider adcp = new AttributeDefinitionConstraintProvider ();
        adcp.init();
        providers.add (adcp);
        if (constraintProcessors != null) {
            Constrainable selectedDefinition = definition;
            AttributeValueReader selectedAttributeValueReader = attributeValueReader;


            // First - take the constrainable definition and get its constraints

            Queue<Constraint> constraintQueue = new LinkedList<Constraint>();

            // Using a for loop to iterate through constraint processors because ordering is important
            for (ConstraintProcessor<Object, Constraint> processor : constraintProcessors) {

                // Let the calling method opt out of any optional processing
                if (!doOptionalProcessing && processor.isOptional()) {
                    result.addSkipped(attributeValueReader, processor.getName());
                    continue;
                }

                Class<? extends Constraint> constraintType = processor.getConstraintType();

                // Add all of the constraints for this constraint type for all providers to the queue
                for (ConstraintProvider constraintProvider : providers) {
                    if (constraintProvider.isSupported(selectedDefinition)) {
                        Collection<Constraint> constraintList = constraintProvider.getConstraints(selectedDefinition, constraintType);
                        if (constraintList != null) {
                            constraintQueue.addAll(constraintList);
                        }
                    }
                }

                // If there are no constraints provided for this definition, then just skip it
                if (constraintQueue.isEmpty()) {
                    result.addSkipped(attributeValueReader, processor.getName());
                    continue;
                }

                Collection<Constraint> additionalConstraints = new LinkedList<Constraint>();

                // This loop is functionally identical to a for loop, but it has the advantage of letting us keep the queue around
                // and populate it with any new constraints contributed by the processor
                while (!constraintQueue.isEmpty()) {

                    Constraint constraint = constraintQueue.poll();

                    // If this constraint is not one that this process handles, then skip and add to the queue for the next processor;
                    // obviously this would be redundant (we're only looking at constraints that this processor can process) except that
                    // the previous processor might have stuck a new constraint (or constraints) on the queue
                    if (!constraintType.isInstance(constraint)) {
                        result.addSkipped(attributeValueReader, processor.getName());
                        additionalConstraints.add(constraint);
                        continue;
                    }

                    ProcessorResult processorResult = processor.process(result, object, constraint, selectedAttributeValueReader);

                    Collection<Constraint> processorResultContraints = processorResult.getConstraints();
                    if (processorResultContraints != null && processorResultContraints.size() > 0) {
                        additionalConstraints.addAll(processorResultContraints);
                    }

                    // Change the selected definition to whatever was returned from the processor
                    if (processorResult.isDefinitionProvided()) {
                        selectedDefinition = processorResult.getDefinition();
                    }
                    // Change the selected attribute value reader to whatever was returned from the processor
                    if (processorResult.isAttributeValueReaderProvided()) {
                        selectedAttributeValueReader = processorResult.getAttributeValueReader();
                    }
                }

                // After iterating through all the constraints for this processor, add additional constraints for following processors
                constraintQueue.addAll(additionalConstraints);
            }
        }
    }

    private AttributeValueReader resolveAttributeValueReader(Constrainable definition, Object value, AttributeValueReader passedAttributeValueReader, Stack<String> elementStack, boolean isComplex) {
        if (isComplex && definition instanceof HierarchicallyConstrainable) {

            // The idea here is that a 'HierarchicallyConstrained' definition provides the business object name
            String childEntryName = ((HierarchicallyConstrainable) definition).getChildEntryName();

            DataDictionaryEntry childEntry = childEntryName != null ? this.dictionaryService.getDataDictionaryEntry(childEntryName) : null;

            if (childEntry == null) {
                throw new AttributeValidationException("No valid child entry of the name " + childEntryName + " can be found in the data dictionary");
            }

            elementStack.push(childEntryName);

            // Create a new attribute value reader using the current attribute value as its 'object', along with the correct dictionary metadata
            // that's available using the child entry name
            return new DictionaryObjectAttributeValueReader(value, childEntryName, childEntry, passedAttributeValueReader.getPath());
        }
        return passedAttributeValueReader;
    }

    private void validateAttribute(DictionaryValidationResult result, AttributeValueReader attributeValueReader, Stack<String> elementStack, boolean checkIfRequired) throws AttributeValidationException {
        Constrainable definition = attributeValueReader.getDefinition(attributeValueReader.getAttributeName());
        validateAttribute(result, definition, attributeValueReader, elementStack, checkIfRequired);
    }

    private void validateAttribute(DictionaryValidationResult result, Constrainable definition, AttributeValueReader attributeValueReader, Stack<String> elementStack, boolean checkIfRequired) throws AttributeValidationException {

        if (definition == null) {
            throw new AttributeValidationException("Unable to validate constraints for attribute \"" + attributeValueReader.getAttributeName() + "\" on entry \"" + attributeValueReader.getEntryName() + "\" because no attribute definition can be found.");
        }

        Object value = attributeValueReader.getValue();

        validateConstrainableObject(result, value, definition, attributeValueReader, elementStack, checkIfRequired);

    }

    private void validateConstrainableObject(DictionaryValidationResult result, Object value, Constrainable definition, AttributeValueReader attributeValueReader, Stack<String> elementStack, boolean checkIfRequired) throws AttributeValidationException {
        DataType dataType = definition instanceof DataTypeConstraint ? ((DataTypeConstraint) definition).getDataType() : null;
        boolean isComplex = dataType != null && dataType.equals(DataType.COMPLEX);

        if (ValidationUtils.isNullOrEmpty(value)) {
            processElementConstraints(result, value, definition, attributeValueReader, elementStack, checkIfRequired);
        }

        if (value instanceof Collection) {
            // Obviously, it's not the child entry's attribute definition being passed here, but that's okay, if isComplex=true, definition is ignored
            processCollection(result, definition, (Collection<?>) value, attributeValueReader, elementStack, checkIfRequired, isComplex);
        } else {
            processObject(result, definition, value, attributeValueReader, elementStack, checkIfRequired, isComplex);
        }
    }

    /**
     * @return Returns the dataDictionaryService.
     */
    public DictionaryServiceInfc getDictionaryService() {
        return dictionaryService;
    }

    /**
     * @param dictionaryService The dataDictionaryService to set.
     */
    public void setDictionaryService(DictionaryServiceInfc dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
}
