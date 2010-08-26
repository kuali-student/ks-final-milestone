/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.writer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class JavaClassFileNameBuilderTest
{

 public JavaClassFileNameBuilderTest ()
 {
 }

 @BeforeClass
 public static void setUpClass ()
  throws Exception
 {
 }

 @AfterClass
 public static void tearDownClass ()
  throws Exception
 {
 }

 @Before
 public void setUp ()
 {
 }

 @After
 public void tearDown ()
 {
 }

 /**
  * Test of build method, of class JavaClassFileNameBuilder.
  */
 @Test
 public void testBuild ()
 {
  System.out.println ("build");
  JavaClassFileNameBuilder instance =
   new JavaClassFileNameBuilder ("my/dir", "my.package", "MyClass");
  String expResult = "my/dir/my/package/MyClass.java";
  String result = instance.build ();
  assertEquals (expResult, result);
 }

}
