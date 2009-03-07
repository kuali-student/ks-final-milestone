package org.kuali.student.rules.internal.common.utils;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.tools.generic.DateTool;
import org.junit.Assert;
import org.junit.Test;

public class VelocityTemplateEngineTest {

	@Test
	public void testEvaluateString_ContextMap() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prop.1", "Kamal");
		map.put("prop.2", "Larry");

		String s = "List of Developers: $prop.1, $prop.2";
		String eval = templateEngine.evaluate(map, s);
		// Velocity does not support dot notation in context keys
		Assert.assertEquals("List of Developers: $prop.1, $prop.2", eval);
	}

	@Test
	public void testEvaluateString1() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("developers", "Kamal, Larry, Len, Sherman, Zdenek");

		String s = "List of Developers: $developers";
		String eval = templateEngine.evaluate(map, s);
		
		Assert.assertEquals("List of Developers: Kamal, Larry, Len, Sherman, Zdenek", eval);
	}

	@Test
	public void testEvaluateString2() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
		List<String> devList = Arrays.asList(new String[] {"Kamal", "Larry", "Len", "Sherman", "Zdenek"});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("developers", devList);

		String s = "List of Developers: $developers";
		String eval = templateEngine.evaluate(map, s);
		
		Assert.assertEquals("List of Developers: [Kamal, Larry, Len, Sherman, Zdenek]", eval);
	}

	@Test
	public void testEvaluateReader() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("developers", "Kamal, Larry, Len, Sherman, Zdenek");

		StringReader sr = new StringReader("List of Developers: $developers");
		String eval = templateEngine.evaluate(map, sr);
		
		Assert.assertEquals("List of Developers: Kamal, Larry, Len, Sherman, Zdenek", eval);
	}

	@Test
	public void testEvaluateNumber() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expectedValue", new BigDecimal("100"));
		map.put("resultValue", new BigDecimal("60"));

		String s = "#set( $difference = ($expectedValue - $resultValue) )expectedValue=$expectedValue, resultValue=$resultValue, difference=$difference";
		String eval = templateEngine.evaluate(map, s);
		
		Assert.assertEquals("expectedValue=100, resultValue=60, difference=40", eval);
	}

	@Test
	public void testEvaluate_MathTool() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expectedValue", "100");
		map.put("resultValue", "60");

		String s = "#set( $difference = ( $mathTool.toNumber($expectedValue) - $mathTool.toNumber($resultValue)) )expectedValue=$expectedValue, resultValue=$resultValue, difference=$difference";
		String eval = templateEngine.evaluate(map, s);
		
		Assert.assertEquals("expectedValue=100, resultValue=60, difference=40", eval);
	}

	@Test
	public void testEvaluate_DateTool() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expectedValue", "100");
		map.put("resultValue", "60");

		String s = "Date created $dateTool.get('yyyy-MM-dd HH:mm:ss z')";
		String eval = templateEngine.evaluate(map, s);
		
		Assert.assertEquals("Date created " + new DateTool().get("yyyy-MM-dd HH:mm:ss z"), eval);
	}

	@Test
	public void testEvaluate_DateComparisonTool() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
		Map<String, Object> map = new HashMap<String, Object>();

		String s = "$dateComparisonTool.whenIs($dateComparisonTool.calendar)";
		String eval = templateEngine.evaluate(map, s);
		
		Assert.assertEquals("now", eval);
	}

	@Test
	public void testEvaluate_DateComparisonTool_NullContextMap() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();

		String s = "$dateComparisonTool.whenIs($dateComparisonTool.calendar)";
		String eval = templateEngine.evaluate(null, s);
		
		Assert.assertEquals("now", eval);
	}

	@Test
	public void testEvaluate_NumberTool() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expectedValue", "100.12");

		String s = "$numberTool.currency($expectedValue)";
		String eval = templateEngine.evaluate(map, s);
		
		Assert.assertEquals("$100.12", eval);
	}

	@Test
	public void testEvaluate_SortTool() throws Exception {
		VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
		List<String> devList = Arrays.asList(new String[] {"Len", "Larry", "Kamal", "Zdenek", "Sherman"});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("developers", devList);

		String s = "$sortTool.sort($developers)";
		String eval = templateEngine.evaluate(map, s);

		Assert.assertEquals("[Kamal, Larry, Len, Sherman, Zdenek]", eval);
	}
}
