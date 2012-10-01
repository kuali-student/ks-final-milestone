package org.kuali.student.lum.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.kuali.student.common.dictionary.dto.FieldDefinition;
import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validator.DefaultValidatorImpl;

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
