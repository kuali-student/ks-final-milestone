package org.apache.torque.mojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Drops a database
 * 
 * @goal drop
 */
public class DropMojo extends AbstractSQLExecutorMojo {

	/**
	 * The schema to drop
	 * 
	 * @parameter expression="${schema}"
	 * @required
	 */
	String schema;

	@Override
	public void execute() throws MojoExecutionException {
		Properties properties = new Properties();
		Map<String, String> environment = System.getenv();
		for (String key : environment.keySet()) {
			// properties.put("env." + key, environment.get(key));
		}
		// properties.putAll(project.getProperties());
		// properties.putAll(System.getProperties());
		try {
			Map<?, ?> props = BeanUtils.describe(project);
			for (Object key : props.keySet()) {
				getLog().info(key + "=" + props.get(key));
			}
		} catch (Exception e) {
			throw new MojoExecutionException("Error copying properties", e);
		}
		List<String> list = new ArrayList<String>();
		list.addAll(properties.stringPropertyNames());
		Collections.sort(list);
		for (String s : list) {
			getLog().info(s + "=" + properties.getProperty(s));
		}
	}

}
