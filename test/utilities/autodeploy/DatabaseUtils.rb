require 'fileutils'

class DatabaseUtils

	# Requires a KsDeployConfig instance
	def initialize(deployConfig)
		@config = deployConfig
		@moduleName = @config.properties[:dbConfigModuleName]
		@configPath = @config.properties[:downloadPath] + File::SEPARATOR + @moduleName
	end
	
	def retrieveCurrentDatabaseConfig(svnCommand = 'svn', svnCommandSuffix = '')
		# first clean up old config
		if File.exist? @configPath
			FileUtils.remove_dir @configPath
		end
		
		# build the svn command to call
		commandLine = svnCommand + ' co ' + @config.dbConfigUrl + ' ' + @configPath + svnCommandSuffix
		
		# call svn to download current config
		@config.log 'Retrieving current database configuration via this command: ' + commandLine
		IO.popen(commandLine) { |f|
			@config.log f.readlines
		}
	end
	
end

class DatabaseUtilsLinux < DatabaseUtils
	
	# Requires a KsDeployConfig instance
	def initialize(deployConfig)
		super(deployConfig)
		
		@sudoCommandPrefix = 'su - ' + @config.properties[:dbSystemUsername] + ' -c "'
		@sudoCommandSuffix = '"'
		
		@mavenCommand = ''
		
		if @config.buildType == :dev
			@mavenCommand = @config.properties[:devDbMavenCommand]
		else
			@mavenCommand = @config.properties[:stagingDbMavenCommand]
		end
		
	end
	
	def retrieveCurrentDatabaseConfig
		super(@sudoCommandPrefix + 'svn', '"')
	end
	
	def mavenDatabaseBuild
		mavenWorkingPath = @configPath + '/' +  @config.moduleName + '-db'
		
		databaseBuildCommand = @sudoCommandPrefix + @mavenCommand + @sudoCommandSuffix;
		
		@config.log 'Updating database via this command: ' + databaseBuildCommand
		
		FileUtils.cd(mavenWorkingPath) {
			IO.popen(databaseBuildCommand) { |f|
				@config.log f.readlines
			}
		}
		
	end
	
end