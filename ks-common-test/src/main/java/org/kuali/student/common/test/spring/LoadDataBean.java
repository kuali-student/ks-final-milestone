package org.kuali.student.common.test.spring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LoadDataBean implements ApplicationContextAware{
	private static final Log LOG = LogFactory.getLog(LoadDataBean.class);



	@PersistenceContext
	EntityManager em;
	private boolean loaded = false;
	private String daoAnnotations;

	private ApplicationContext applicationContext;

	public void loadData(){
		if (daoAnnotations == null || loaded || daoAnnotations.trim().isEmpty()) {
			return;
		}

		// Load all the beans
		String[] classes = daoAnnotations.split(",");
		for (String line : classes) {
			try {
				String[] split = line.split("\\|");

				// Invoke the data loader for this dao
                invokeDataLoader(split[0]);

                // Load data bean file for this dao
                if (split.length > 1&& !split[1].isEmpty()) {
				    String testDataFile = split[1];

					ApplicationContext ac = new FileSystemXmlApplicationContext(
							testDataFile);
					for (Object bean : (List<?>) ac.getBean("persistList")) {
						if(!em.contains(bean)){
							em.persist(bean);
						}
					}
				}
                // Load sql file for this dao
                if (split.length > 2&& !split[2].isEmpty()) {

					String testDataFile = split[2];
				    File sqlFile;
				    if(testDataFile.startsWith("classpath:")){
				 	   sqlFile = new ClassPathResource(testDataFile.substring("classpath:".length())).getFile();
				    }else{
				    	sqlFile = new File(testDataFile);
				    }
					BufferedReader in
					   = new BufferedReader(new FileReader(sqlFile));
					String ln;

					while((ln=in.readLine())!=null){
						if(!ln.startsWith("/")&&!ln.startsWith("--")&&StringUtils.isNotBlank(ln)){
							ln=ln.replaceFirst("[;/]\\s*$","");
							em.createNativeQuery(ln).executeUpdate();
						}
					}
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		loaded = true;

	}

	protected void invokeDataLoader(String dao){
	    try {
            //Check if there is a loader class for this dao
	        Class<?> daoType = Class.forName(dao).getInterfaces()[0];

	        Class<?> clazz = Class.forName(daoType.getName() + "Loader");
            DaoLoader daoLoader = (DaoLoader)clazz.newInstance();

            //Get spring bean for the dao
            Map<?,?> daoBeans = applicationContext.getBeansOfType(daoType);

            //Invoke the loader for this doa bean (there shouldn't be more than one)
            if (daoBeans.size() == 1){
                daoLoader.setDao(daoBeans.values().iterator().next());
                daoLoader.run();
            }

	    } catch (ClassNotFoundException cnfe) {
	    	LOG.info(cnfe);
	    } catch (Exception e) {
            LOG.error(e);
        }
	}

	/**
	 * @return the daoAnnotations
	 */
	public String getDaoAnnotations() {
		return daoAnnotations;
	}

	/**
	 * @param daoAnnotations
	 *            the daoAnnotations to set
	 */
	public void setDaoAnnotations(String daoAnnotations) {
		this.daoAnnotations = daoAnnotations;
	}

	public void setApplicationContext(ApplicationContext applicationContext){
	    this.applicationContext = applicationContext;
	}
}
