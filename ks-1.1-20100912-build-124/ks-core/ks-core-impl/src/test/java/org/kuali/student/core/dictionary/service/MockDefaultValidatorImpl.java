package org.kuali.student.core.dictionary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class MockDefaultValidatorImpl extends DefaultValidatorImpl
{

 @Override
 public List<ValidationResultInfo> validateObject (Object o,
                                                   ObjectStructureDefinition objStructure)
 {
  return new ArrayList ();
 }

 @Override
 public List<ValidationResultInfo> validateObject (FieldDefinition field,
                                                   Object o,
                                                   ObjectStructureDefinition objStructure,
                                                   Stack<String> elementStack)
 {
  return new ArrayList ();
 }
}
