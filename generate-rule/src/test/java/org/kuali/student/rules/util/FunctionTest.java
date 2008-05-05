package org.kuali.student.rules.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FunctionTest {

    private String theFunction = "A0*B4+ (C*D )";
    private Function f;
    private List<String> funcVars;
    private List<String> funcSymbols;
    private String var;
    private String[] arrVar = {"A0","B4","C","D"};
    private String[] arrSymbols = {"A0","*","B4","+","(","C","*","D",")"};
    
    @Before
    public void setUp() throws Exception {
        f = new Function(theFunction);
        funcVars = f.getVariables();
        funcSymbols = f.getSymbols();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetVariables() {
        
        for(int i=0; i < funcVars.size(); i++ ){
            var = funcVars.get(i);
            assertEquals(var, arrVar[i]);
        }
    }
    
    @Test
    public void testGetSymbols() {
        
        for(int i=0; i < funcSymbols.size(); i++ ){
            var = funcSymbols.get(i);
            System.out.println("var : " + var);
            assertEquals(var, arrSymbols[i]);
        }
    }

}
