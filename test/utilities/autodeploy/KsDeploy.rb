require 'TomcatUtils'
require 'KsDeployConfig'
require 'DatabaseUtils'

def runDeploy(ksConfig)
	# initialize configurations
	tu = TomcatUtilsLinux.new(ksConfig)
	dbu = DatabaseUtilsLinux.new(ksConfig)
	
	# download the latest build
	ksConfig.log '= Downloading latest build war from ' + ksConfig.discoverWarUrl + ' to ' + ksConfig.warfilePath
	ksConfig.getRemoteFile(ksConfig.discoverWarUrl, ksConfig.warfilePath, true)
	ksConfig.log '= Download complete'
	
	# stop tomcat
	ksConfig.log '= Stopping tomcat...'
	shutdown = tu.stopTomcat
	if shutdown
		ksConfig.log '= tomcat stopped successfully'
	else
		ksConfig.log '= ERROR unable to stop tomcat.  Deploy process ending abnormally.'
		return false
	end
	
	# back up previous build, and deploy the current war
	ksConfig.log '= Backing up previous build and deploying current war...'
	tu.backupAndDeployWar
	ksConfig.log '= Backup and deploy complete'
	
	# build the database checkout url
	ksConfig.discoverDbConfigUrl
	
	# remove the previous ks-cfg-dbs directory and checkout ks-cfg-dbs from the tag
	ksConfig.log '= Checking out current database config...'
	dbu.retrieveCurrentDatabaseConfig
	ksConfig.log '= Database config checkout complete'
	
	# reset the database instance
	ksConfig.log '= Performing database initialization...'
	dbu.mavenDatabaseBuild
	ksConfig.log '= Database initialization complate'
	
	# start tomcat
	ksConfig.log '= Starting tomcat...'
	startup = tu.startupTomcat
	if startup
		ksConfig.log '= tomcat started successfully'
	else
		ksConfig.log '= A problem occurred while starting tomcat'
		return false
	end
	
	true
end

ksConfig = KsDeployConfig.new('ksDeployConfig.properties')

success = runDeploy(ksConfig)

if !ksConfig.properties[:logToConsole]
	puts ksConfig.logString
end

puts 'Deploy successful? ' + success.to_s

if success
	puts 'Running post-deploy scripts'
	
	ksConfig.runPostDeployScripts
end

#dbCleanup = ksConfig.properties[:dbCleanupScript]

#if dbCleanup != nil
#	puts 'Performing database cleanup:  running ' + dbCleanup + '...'
#	ksConfig.runCommand dbCleanup
#	puts 'Database cleanup complete'
#end
