package org.kuali.student.rules.ruleexecution.runtime.drools.logging;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DroolsExecutionStatistics {
	private final static DroolsExecutionStatistics INSTANCE = new DroolsExecutionStatistics();
	private Date startTime;
	private Map<String,EventLogger> eventMap = new HashMap<String,EventLogger>();
	
	private StatisticsLogger counter = new StatisticsLogger();
	
	private DroolsExecutionStatistics() {}
	
	public static synchronized DroolsExecutionStatistics getInstance() {
		return INSTANCE;
	}
	
    public void start() {
        this.startTime = new Date();
    }

	public Date getStartTime() {
    	return this.startTime;
    }

    public StatisticsLogger getStatistics() {
    	return counter;
    }
    
    public Collection<EventLogger> getRuleActivationStatistics() {
    	return this.eventMap.values();
    }
    
    public void addRuleActivation(String ruleBaseType, String packageName, String ruleName) {
    	String key = ruleBaseType + "." + packageName + "." + ruleName;
    	if(!eventMap.containsKey(key)) {
    		EventLogger ec = new EventLogger(ruleBaseType, packageName, ruleName);
    		eventMap.put(key, ec);
    	}
    	eventMap.get(key).addActivation();
    }
    
	public static class EventLogger {
		private String ruleBaseType;
		private String packageName;
		private String ruleName;
		private int activationCount = 0;
	
		public EventLogger(String ruleBaseType, String packageName, String ruleName) {
			this.ruleBaseType = ruleBaseType;
			this.packageName = packageName;
			this.ruleName = ruleName;
		}

		public String getRuleBaseType() {
			return this.ruleBaseType;
		}

		public String getPackageName() {
			return this.packageName;
		}

		public String getRuleName() {
			return this.ruleName;
		}
		
		public void addActivation() {
			this.activationCount++;
		}
		
		public int getActivationCount() {
			return this.activationCount;
		}
	}

	public static class StatisticsLogger {

		private int totalLogEventCount = 0;
		private int totalActivationsFiredCount = 0;
		private int totalPackageAddCount = 0;
		private int totalPackageRemoveCount = 0;
		private int totalObjectInsertCount = 0;
		private int totalObjectRetractCount = 0;
		private int totalObjectUpdateCount = 0;
		
		public StatisticsLogger() {
		}

		public void addLogEvent() {
			this.totalLogEventCount++;
		}
		
		public void addActivationsFiredEvent() {
			this.totalActivationsFiredCount++;
		}
		
		public void addPackageAddEvent() {
			this.totalPackageAddCount++;
		}
		
		public void addPackageRemoveEvent() {
			this.totalPackageRemoveCount++;
		}
		
		public void addObjectInsertEvent() {
			this.totalObjectInsertCount++;
		}
		
		public void addObjectRetractEvent() {
			this.totalObjectRetractCount++;
		}
		
		public void addObjectUpdatesEvent() {
			this.totalObjectUpdateCount++;
		}

		public int getTotalLogEventCount() {
			return totalLogEventCount;
		}

		public int getTotalActivationsFiredCount() {
			return totalActivationsFiredCount;
		}

		public int getTotalPackageAddCount() {
			return totalPackageAddCount;
		}

		public int getTotalPackageRemoveCount() {
			return totalPackageRemoveCount;
		}

		public int getTotalObjectInsertCount() {
			return totalObjectInsertCount;
		}

		public int getTotalObjectRetractCount() {
			return totalObjectRetractCount;
		}

		public int getTotalObjectUpdateCount() {
			return totalObjectUpdateCount;
		}
	}
}
