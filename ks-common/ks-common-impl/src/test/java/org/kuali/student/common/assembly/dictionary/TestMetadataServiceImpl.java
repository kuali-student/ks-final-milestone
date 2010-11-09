package org.kuali.student.common.assembly.dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataFormatter;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.dictionary.service.impl.DictionaryServiceImpl;

public class TestMetadataServiceImpl {

	DictionaryServiceImpl dictionaryDelegate = new DictionaryServiceImpl("classpath:test-validator-context.xml");
	private static final String SIMPLE_STUDENT = "simpleStudent";
	private static final String ADDRESS_INFO = "addressInfo";
	private static final String US_ADDRESS_TYPE = "US";
	private static final String CANADIAN_ADDRESS_TYPE = "CAN";

	@Test
	public void testMetadataService() {
		MockDictionaryService mockDictionaryService = new MockDictionaryService();
		mockDictionaryService.setDictionaryServiceDelegate(dictionaryDelegate);

		MetadataServiceImpl metadataService = new MetadataServiceImpl(
				mockDictionaryService);
		metadataService.setUiLookupContext("classpath:test-lookup-context.xml");
		Metadata metadata = metadataService.getMetadata(SIMPLE_STUDENT);

		Map<String, Metadata> properties = metadata.getProperties();
		assertTrue(properties.containsKey("firstName"));
		assertTrue(properties.containsKey("dob"));
		assertTrue(properties.containsKey("gpa"));

		assertEquals(1, properties.get("firstName").getConstraints().size());
		assertEquals(1, properties.get("dob").getConstraints().size());
		assertEquals(1, properties.get("gpa").getConstraints().size());

		ConstraintMetadata nameConstraints = properties.get("firstName").getConstraints().get(0);
		assertTrue(nameConstraints.getMinLength() == 2);
		assertTrue(nameConstraints.getMaxLength() == 20);

		// Check requiredness for default state of draft
		ConstraintMetadata gpaConstraints = properties.get("gpa").getConstraints().get(0);
		assertTrue(gpaConstraints.isRequiredForNextState());
		assertEquals("SUBMITTED", gpaConstraints.getNextState());

		// Check requiredness for state of RETIRED (there should be no next state)
		metadata = metadataService.getMetadata(SIMPLE_STUDENT, "RETIRED");
		gpaConstraints = metadata.getProperties().get("gpa").getConstraints().get(0);
		assertFalse(gpaConstraints.isRequiredForNextState());
		assertNull(gpaConstraints.getNextState());

		// Check type and nested state
		metadata = metadataService.getMetadata(ADDRESS_INFO);
		ConstraintMetadata addrLineConstraint = metadata.getProperties().get("line1").getConstraints().get(0);
		assertEquals(2, addrLineConstraint.getMinLength().intValue());
		assertEquals(1, addrLineConstraint.getMinOccurs().intValue());
		assertEquals(30, addrLineConstraint.getMaxLength().intValue());

		metadata = metadataService.getMetadata(ADDRESS_INFO, US_ADDRESS_TYPE, "SUBMITTED");
		addrLineConstraint = metadata.getProperties().get("line1").getConstraints().get(0);
		assertEquals(2, addrLineConstraint.getMinLength().intValue());
		assertEquals(1, addrLineConstraint.getMinOccurs().intValue());
		assertEquals(30, addrLineConstraint.getMaxLength().intValue());

		metadata = metadataService.getMetadata(ADDRESS_INFO, US_ADDRESS_TYPE, "DRAFT");
		addrLineConstraint = metadata.getProperties().get("line1").getConstraints().get(0);
		assertEquals(2, addrLineConstraint.getMinLength().intValue());
		assertEquals(1, addrLineConstraint.getMinOccurs().intValue());
		assertEquals(30, addrLineConstraint.getMaxLength().intValue());

		metadata = metadataService.getMetadata(ADDRESS_INFO, CANADIAN_ADDRESS_TYPE, "DRAFT");
		addrLineConstraint = metadata.getProperties().get("line1").getConstraints().get(0);
		assertEquals(2, addrLineConstraint.getMinLength().intValue());
		assertEquals(1, addrLineConstraint.getMinOccurs().intValue());
		assertEquals(30, addrLineConstraint.getMaxLength().intValue());
	}

	@Test
	public void testMetadataFormatter() {

		Set<String> startingObjects = new LinkedHashSet<String>();
		Map<String, Set<String>> types = new LinkedHashMap<String, Set<String>>();

		startingObjects.add(SIMPLE_STUDENT);
		startingObjects.add(ADDRESS_INFO);

		MockDictionaryService mockDictionaryService = new MockDictionaryService();
		mockDictionaryService.setDictionaryServiceDelegate(dictionaryDelegate);

		MetadataServiceImpl metadataService = new MetadataServiceImpl(mockDictionaryService);
		metadataService.setUiLookupContext("classpath:test-lookup-context.xml");
		// startingClasses.add (StatementTreeViewInfo.class);

		Set<String> typesForClass = new LinkedHashSet<String>();
		types.put(ADDRESS_INFO, typesForClass);
		typesForClass.add(US_ADDRESS_TYPE);
		typesForClass.add(CANADIAN_ADDRESS_TYPE);

		String outFile = "target/test-metadata.txt";
		File file = new File(outFile);
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file, false);
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		PrintStream out = new PrintStream(outputStream);
		out.println("(!) This page was automatically generated on "	+ new Date());
		out.println("DO NOT UPDATE MANUALLY!");
		out.println("");
		out.println("This page represents a formatted view of the test dictionary");
		for (String objectKey : startingObjects) {
			out.println("# " + objectKey);
		}
		out.println("");
		out.println("----");
		out.println("{toc}");
		out.println("----");

		for (String objectKey : startingObjects) {
			// out.println ("getting meta data for " + clazz.getName ());
			Metadata metadata = metadataService.getMetadata(objectKey);
			assertNotNull(metadata);
			MetadataFormatter formatter = new MetadataFormatter(objectKey,
					metadata, null, null, new HashSet<Metadata>(), 1);
			out.println(formatter.formatForWiki());
			if (types.get(objectKey) == null) {
				continue;
			}
			for (String type : types.get(objectKey)) {
				System.out.println("*** Generating formatted version for " + type);
				metadata = metadataService.getMetadata(objectKey, type, (String) null);
				assertNotNull(metadata);
				formatter = new MetadataFormatter(objectKey, metadata, type, null, new HashSet<Metadata>(), 1);
				out.println(formatter.formatForWiki());
			}
		}
	}
}
