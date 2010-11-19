require "KsDeployConfig"
require 'fileutils'
require 'zip/zip'


class TomcatUtils
	
	attr_reader :deployPath
	
	# Requires a KsDeployConfig instance
	def initialize(config)
		@deployConfig = config
		@logPath = @deployConfig.tomcatHome + File::SEPARATOR + 'logs'
		@workPath = @deployConfig.tomcatHome + File::SEPARATOR + 'work'
		@binPath = @deployConfig.tomcatHome + File::SEPARATOR + 'bin'
		
		@deployPath = @deployConfig.tomcatDeployPath
		@warfilePath = @deployConfig.warfilePath
	end
	
	# number of seconds to wait for tomcat to stop
	# (not exact, since some time is spent every second checking if the process has stopped)
	@@SHUTDOWN_TIMEOUT = 300  # 5 minutes
	
	# number of seconds to wait for tomcat to start
	# (not exact, since some time is spent every second checking if the server has fully started)
	@@STARTUP_TIMEOUT = 600  # 10 minutes
	
	# pattern to watch for in log file indicating a successful tomcat server start
	@@STARTUP_PATTERN = 'INFO: Server startup in '
	
	def  backupAndDeployWar
		backupPath = @deployPath + '.bak'
		
		# if an old backup exists, remove it
		if File.exist?(backupPath)
			FileUtils.remove_dir(backupPath)
			@deployConfig.log 'Removed old backup at ' + backupPath
		end
		
		# move the current deploy to the backup location
		FileUtils.move(@deployPath, backupPath)
		@deployConfig.log 'Moved current deploy to backup path'
		
		# create the extraction directory
		FileUtils.mkdir @deployPath
		
		# unzip the war file to the deploy path
		counter = 0
		zipper = Zip::ZipFile.foreach(@warfilePath) { |zipEntry|
			zipEntryPath = @deployPath + File::SEPARATOR + zipEntry.name
			
			# ensure that the directory for the extracted file already exists
			dirName = File.dirname(zipEntryPath)
			FileUtils.mkdir_p(dirName)
			
			# extract the entry
			zipEntry.extract(zipEntryPath)
			counter = counter.next
		}
		
		@deployConfig.log 'Extracted ' + counter.to_s + ' entries to ' + @deployPath
	end
	
	# clean up tomcat log files and working directory
	def startupCleanup
		FileUtils.rm_f Dir.glob(@logPath + File::SEPARATOR + '*.out')
		FileUtils.rm_f Dir.glob(@logPath + File::SEPARATOR + '*.log')
		
		FileUtils.remove_dir(@workPath, true)
	end
	
	# utility method to tail a file, looking for a particular pattern, until the timeout has been exceeded
	def watch_for(file, pattern, timeout=30)
	
		while !File.exists? file
			sleep 1
			timeout = timeout - 1
		end
		
		f = File.open(file, 'r')
		f.seek(0, IO::SEEK_END)
		
		found = false
		
		s = f.gets
		timeout.times do
			s = f.gets
			if s != nil 
				while s != nil
					if s.include? pattern
						found = true
					end
					s = f.gets
				end
				break if found
			else 
				sleep 1
			end
		end
		
		found
	
	end
	
	
end


class TomcatUtilsLinux < TomcatUtils
	
	# Requires a KsDeployConfig instance
	def initialize(config)
		super(config)
		
		@tomcatUser = @deployConfig.properties[:tomcatUser]
		
		if @tomcatUser == nil or @tomcatUser.empty?
			@tomcatCommandPrefix = @binPath
			@tomcatCommandSuffix = ''
		else
			@tomcatCommandPrefix = 'su - ' + @tomcatUser + ' -c "' + @binPath
			@tomcatCommandSuffix = '"'
		end
		
	end

	def stopTomcat
		pid = nil
		
		# if a pid file is defined, use it to determine the running tomcat process
		
		processCheck = ''
		pidFileName = ''
		if !@deployConfig.properties.key? :catalinaPidFile
			pidFileName = @deployConfig.properties[:catalinaPidFile]
			File.open(pidFileName) do |f|
				f.each_line { |s| pid = s }
			end
			
			processCheck = 'ps --no-headers -p ' + pid
		else
			processCheck = 'ps --no-headers | grep java | grep -v grep'
		end
		
		shutdownCommand = @tomcatCommandPrefix + '/shutdown.sh' + @tomcatCommandSuffix
		
		@deployConfig.log 'About to run shutdown command: ' + shutdownCommand
		
		IO.popen(shutdownCommand)  { |f|
			@deployConfig.log f.readlines
		}
		
		psOutput = ""
		
		if !psOutput.empty?
			@@SHUTDOWN_TIMEOUT.times do
				psOutput = ""
				IO.popen(processCheck) {|output| output.each_line { |line| psOutput << line } }
				if psOutput.empty?
					break
				end
				
				sleep 1
			end
		end
		
		# if shutdown was successful, remove pid file if it still exists
		if psOutput.empty?
			if !pidFileName.empty? and File.exists? pidFilename
				@deployConfig.log 'PID file ' + pidFileName + ' still exists after tomcat shutdown, removing file...'
				FileUtils.rm pidFileName
				@deployConfig.log 'PID file removed'
			end
		end
		
		# return true if the tomcat process was actually removed from the running list 
		# (i.e. sucessfully stopped), false otherwise
		psOutput.empty?
	end
	
	# returns true if the method was able to determine that the server started successfully by watching the log file, false otherwise
	def startupTomcat
		
		startupCleanup()
		
		startupCommand = @tomcatCommandPrefix + '/startup.sh' + @tomcatCommandSuffix
		
		@deployConfig.log 'About to run shutdown command: ' + startupCommand
		
		IO.popen(startupCommand) { |f|
			@deployConfig.log f.readlines
		}
		
		# give tomcat enough time to at least create the log file
		sleep(5)
		
		# watch the output file
		@deployConfig.log 'startup.sh script run sucessfully, waiting for tomcat instance to start...'
		result = watch_for(@logPath + '/catalina.out', @@STARTUP_PATTERN, @@STARTUP_TIMEOUT)
		
		if result
			@deployConfig.log 'Instance started successfully'
		end
		
		result
		
	end
	
end