package org.kuali.student.r2.core.dictionary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.validation.dto.ValidationResultInfo;
import org.kuali.student.r1.common.validator.DefaultValidatorImpl;

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
