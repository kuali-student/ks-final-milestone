package org.kuali.student.lum.course.service.impl;

import java.io.PrintStream;
import java.util.List;
import org.junit.Test;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestDictionaryLoad
{

 @Test
 public void testLoadCluInfoDictionary ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-cluInfo-dictionary-context.xml");
//  for (String beanName: ac.getBeanDefinitionNames ())
//  {
//   System.out.println ("beanName=" + beanName);
//  }
  testLoadDictionary (RichTextInfo.class, ac);
  testLoadDictionary (AmountInfo.class, ac);
  testLoadDictionary (TimeAmountInfo.class, ac);
  testLoadDictionary (AdminOrgInfo.class, ac);
  testLoadDictionary (CluInfo.class, ac);
 }


 @Test
 public void testLoadCourseInfoDictionary ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-courseInfo-dictionary-context.xml");
  testLoadDictionary (CourseInfo.class, ac);
 }

  @Test
 public void testLoadProgramInfoDictionary ()
 {
  ApplicationContext ac = new ClassPathXmlApplicationContext (
    "classpath:ks-programInfo-dictionary-context.xml");
  testLoadDictionary (MajorDisciplineInfo.class, ac);
 }

 private void dump (ObjectStructureDefinition os, PrintStream out)
 {
  out.println (new DictionaryFormatter (os, "|").format ());
 }

 public void testLoadDictionary (Class<?> clazz, ApplicationContext ac)
 {
  ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
    clazz.getName ());
  os.getAttributes ();
  dump (os, System.out);
  validate (clazz, os);
 }

 private void validate (Class<?> clazz, ObjectStructureDefinition os)
 {
  Dictionary2BeanComparer validator = new Dictionary2BeanComparer (clazz, os);
  List<String> errors = validator.validate ();
  if (errors.size () > 0)
  {
   System.out.println (toString (clazz.getSimpleName (), errors));
//   fail (toString (clazz.getSimpleName (), errors));
  }
 }

 private String toString (String objectName, List<String> errors)
 {
  int i = 0;
  StringBuilder builder = new StringBuilder ();
  builder.append (errors.size () + " errors found in " + objectName + ":\n");
  for (String error : errors)
  {
   i ++;
   builder.append (i + ". " + error + "\n");
  }
  return builder.toString ();
 }
}
