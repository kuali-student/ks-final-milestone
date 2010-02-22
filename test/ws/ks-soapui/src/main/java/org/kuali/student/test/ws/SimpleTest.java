package org.kuali.student.test.ws;

import java.util.List;

import com.eviware.soapui.impl.support.AbstractHttpRequestInterface;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.model.iface.Response;

public class SimpleTest {
	public static void main(String[] args) {
		new SimpleTest().run();
	}

	public void run() {
		try {
			// create new project
			WsdlProject project = new WsdlProject();

			// import wsdl
			WsdlInterface iface = project.importWsdl("http://localhost//services/LuService?wsdl", true)[0];

			// Cycle through the available operations
			List<Operation> operations = iface.getOperationList();

			for (Operation operation : operations) {
				WsdlOperation op = (WsdlOperation) operation;
				System.out.println(getString(op));
			}
			// assertNotNull( content );
			// assertTrue( content.indexOf( "404 Not Found" ) > 0 );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	protected String getString(WsdlOperation op) {
		StringBuffer sb = new StringBuffer();
		// sb.append(op.getAction() + " ");
		// sb.append(op.getAnonymous() + " ");
		sb.append(op.getBindingOperationName() + " ");
		// sb.append(op.getInputName() + " ");
		// sb.append(op.getOutputName() + " ");
		// sb.append(op.getStyle() + " ");
		// sb.append(op.getType() + " ");
		return sb.toString();
	}
}
