package org.kuali.student.common.ui.client.widgets;

public class KSZIndexStack {

    private static int current = 10;
    public static int pop() {
     return current++;
    }
    public static void push() {
     current--;
    }
   }