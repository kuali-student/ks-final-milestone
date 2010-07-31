package org.kuali.student.lum.program.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.SampCustomValidator;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.impl.ComplexSubstructuresHelper;
import org.kuali.student.lum.course.service.impl.CourseDataGenerator;
import org.kuali.student.lum.course.service.impl.Dictionary2BeanComparer;
import org.kuali.student.lum.course.service.impl.DictionaryFormatter;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.service.assembler.MajorDisciplineDataGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestProgramInfoDictionary
{

 @Test
 public void testLoadProgramInfoDictionary ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-programInfo-dictionary-context.xml");
//  for (String beanName: ac.getBeanDefinitionNames ())
//  {
//   System.out.println ("beanName=" + beanName);
//  }
  List<String> errors = new ArrayList ();
  for (Class<?> clazz : getComplexStructures (MajorDisciplineInfo.class))
  {
   errors.addAll (validate (clazz, ac));
  }
  if (errors.size () > 0)
  {
   System.out.println (formatAsString (errors));
//   fail (formatAsString (errors));
   return;
  }

 }

 private Set<Class<?>> getComplexStructures (Class<?> clazz)
 {
  return new ComplexSubstructuresHelper ().getComplexStructures (clazz);
 }

 
 public List<String> validate (Class<?> clazz, ApplicationContext ac)
 {
  ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
    clazz.getName ());
  os.getAttributes ();
  System.out.println (new DictionaryFormatter (os, "|").format ());
  return validate (clazz, os);
 }

 private List<String> validate (Class<?> clazz, ObjectStructureDefinition os)
 {
  Dictionary2BeanComparer validator = new Dictionary2BeanComparer (clazz, os);
  List<String> errors = validator.validate ();
  if (errors.size () > 0)
  {
   errors.add (0, errors.size () + " errors in " + clazz.getSimpleName ());
  }
  return errors;
 }

 private String formatAsString (List<String> errors)
 {
  int i = 0;
  StringBuilder builder = new StringBuilder ();
  for (String error : errors)
  {
   i ++;
   builder.append (i + ". " + error + "\n");
  }
  return builder.toString ();
 }

 @Test
 public void testMajorDisciplineInfoValidation ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-programInfo-dictionary-context.xml");
  DefaultValidatorImpl val = new DefaultValidatorImpl ();
  val.setValidatorFactory (new ValidatorFactory(new SampCustomValidator ()));
  val.setDateParser (new ServerDateParser ());
  MajorDisciplineInfo info = new MajorDisciplineInfo ();
  ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
    info.getClass ().getName ());
  List<ValidationResultInfo> errors = val.validateObject (info, os);
  System.out.println ("errors with just a blank");
  for (ValidationResultInfo error : errors)
  {
   System.out.println (error.getElement () + " " + error.getMessage ());
  }
  assertEquals (1, errors.size ());


  try
  {
   info =
   new MajorDisciplineDataGenerator ().getMajorDisciplineInfoTestData ();
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  errors = val.validateObject (info, os);
  System.out.println ("errors with generated data");
  for (ValidationResultInfo error : errors)
  {
   System.out.println (error.getElement () + " " + error.getMessage ());
  }
  assertEquals (0, errors.size ());
 }

}
