import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

public class Deploy {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.hhmmss");

	public static void main(final String[] args) {
		Deploy deploy = new Deploy();
		deploy.run();
	}

	public void run() {
		try {
			String url = getCurlUrl();
			System.out.println("\n" + url);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	protected String getCurlUrl() throws Exception {
	    return null;
		//String url = getUrl("maven-metadata.xml");
		//String xml = getContents(url);
		//BuildInfo bi = getBuildInfo(xml);
		//String filename = getFilename(bi);
		//return getUrl(filename);
	}

	protected String getFilename(final BuildInfo bi) {
		StringBuffer sb = new StringBuffer();
		sb.append(bi.getArtifactId());
		sb.append("-");
		String version = bi.getVersion();
		if (version.endsWith("-SNAPSHOT")) {
			version = StringUtils.replace(version, "-SNAPSHOT", "");
		}
		sb.append(version);
		sb.append("-");
		sb.append(sdf.format(bi.getTimestamp()));
		sb.append("-");
		sb.append(bi.getBuildNumber());
		sb.append(".war");
		return sb.toString();
	}

	protected BuildInfo getBuildInfo(final String contents) throws ParseException {
		String artifactId = StringUtils.substringBetween(contents, "<artifactId>", "</artifactId>");
		String version = StringUtils.substringBetween(contents, "<version>", "</version>");
		String buildNumber = StringUtils.substringBetween(contents, "<buildNumber>", "</buildNumber>");
		String timestamp = StringUtils.substringBetween(contents, "<timestamp>", "</timestamp>");

		BuildInfo bi = new BuildInfo();
		bi.setArtifactId(artifactId);
		bi.setVersion(version);
		bi.setBuildNumber(new Integer(buildNumber));
		bi.setTimestamp(sdf.parse(timestamp));
		return bi;
	}

	protected String getUrl(final String filename) {
		String base = "http://maven.kuali.org/snapshot/org/kuali/student/web/ks-embedded/1.1.0-M9-SNAPSHOT/";
		return base + filename;
	}

	protected String getContents(final String url) throws IOException { // HttpException {
	    return null;
		//HttpClient client = new HttpClient();
		//HttpMethod method = new GetMethod(url);
		//client.executeMethod(method);
		//return method.getResponseBodyAsString();
	}

}
