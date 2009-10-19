/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author nwright
 */
public class TestSwing
{

 public void run ()
 {
  JFrame frame = new JFrame ();
  frame.setTitle ("TEst 1");
  frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
  frame.getContentPane ().add (new JLabel ("first label"));
  frame.getContentPane ().add (new JLabel ("2nd label"));
  frame.pack ();
  frame.setVisible (true);

  frame = new JFrame ();
  frame.setTitle ("TEst 2");
  frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
  Box content = new Box (BoxLayout.Y_AXIS);
  frame.setContentPane (content);
  content.add (new JLabel ("first label"));
  content.add (new JLabel ("2nd label"));
  frame.pack ();
  frame.setVisible (true);

  frame = new JFrame ();
  frame.setTitle ("TEst 3");
  frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
  content = new Box (BoxLayout.Y_AXIS);

  content.add (new JLabel ("first label"));
  content.add (new JLabel ("2nd label"));
  frame.setContentPane (content);
  frame.pack ();
  frame.setVisible (true);
 }

 public static void main (String[] args)
 {
  TestSwing menu = new TestSwing ();
  menu.run ();
 }

}
