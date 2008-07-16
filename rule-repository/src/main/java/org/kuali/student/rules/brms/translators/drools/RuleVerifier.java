package org.kuali.student.rules.brms.translators.drools;

import java.io.Reader;
import java.util.Collections;
import java.util.List;

import org.drools.compiler.DrlParser;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.ParserError;
import org.drools.lang.descr.PackageDescr;
import org.kuali.student.rules.brms.translators.exceptions.GenerateRuleSetException;

enum Severity {ERROR, WARN, INFO, NONE}

public class RuleVerifier {

    //private Verifier verifier = new Verifier();
    private List<String> errorMessages = Collections.emptyList();

    /**
     * Constructs a new rule verifier.
     */
    public RuleVerifier() {
    }
    
    /**
     * Verify that a rule package is valid.
     * 
     * @param source
     * @return
     */
    public boolean verify(Reader source) {
        DrlParser p = new DrlParser();
        PackageDescr pkg;
        
        try {
            pkg = p.parse(source);
        } catch (DroolsParserException e) {
            throw new GenerateRuleSetException("Parsing rule failed", e);
        }

        if (p.hasErrors()) {
            List<ParserError> errors = p.getErrors();
            for(ParserError error : errors) {
                errorMessages.add(error.toString());
            }
        }

        //verifier.addPackageDescr(pkg);
        //verifier.fireAnalysis();
        //VerifierResult result = anal.getResult();
        
        return true;
    }

    /**
     * Gets rule verification severity.
     * 
     * @return Severity
     */
    public Severity getSeverity() {
        return Severity.NONE;
    }

    /**
     * Gets any verification messages.
     * 
     * @return
     */
    public List<String> getMessage() {
        return errorMessages;
    }
}
