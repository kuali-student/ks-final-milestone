require 'open-uri'
require 'xmlsimple'
require 'progressbar'

class KsDeployConfig
	
	attr_reader :properties, :downloadUrl, :moduleName, :version, :buildsRootUrl, :warUrl, :tomcatHome, :dbConfigUrl, :tomcatDeployPath, :buildType, :warfilePath, :logger, :logString
	
	@@VERSION_SUFFIX = "-SNAPSHOT"
	@@WARFILE_EXTENSION = ".war"
	
	@@MANIFEST_PATH_SUFFIX = 'META-INF' + File::SEPARATOR + 'MANIFEST.MF'
	@@MANIFEST_BUILD_ID_PROPERTY = 'Bundle-BuildNumber: '
	
	@@PROPERTY_LIST_DELIMITER='<>'
	
	# create a new KSDeployConfig instance using the given file
	# the properties file is expected to include the following named parameters:
	#
	# downloadUrl, module, version, metadataFilename, tomcatHome
	
	def initialize(configPath) 
		@logString = ''
		@logger = StringIO.new(@logString)
		
		@properties = {}
		
		# create a mapping from each property name (as a symbol) to it's string value
		File.open(configPath, 'r') do |configFile|
			configFile.read.each_line do |line|
				line.strip!
				if (line[0] != ?# and line[0] != ?= and !line.empty?)
					i = line.index('=')
					if (i)
						@properties[line[0..i - 1].strip.to_sym] = line[i + 1..-1].strip
					else
						@properties[line.to_sym] = ''
					end
				end
			end 
		end
		
		@logToConsole = false
		if @properties[:logToConsole] != nil
			@logToConsole = true if @properties[:logToConsole] == 'true'
		end
		
		@downloadUrl = @properties[:downloadUrl]
		@moduleName = @properties[:module]
		@version = @properties[:version]
		@tomcatHome = @properties[:tomcatHome]
		
		@warRootUrl = @downloadUrl + '/' + @moduleName + '/' + version + @@VERSION_SUFFIX
		
		@buildType = @properties[:buildType].to_sym
		
		# build the tomcat deploy path
		@tomcatDeployPath = @properties[:warDeployPath] + File::SEPARATOR + @buildType.to_s
		
		# build the war file path
		@warfilePath = @properties[:downloadPath] + File::SEPARATOR + @moduleName + '.war'
		
		# format today's date to check that the build has been completed for today
		t = Time.now
		@dateString = t.strftime("%Y%m%d")
	end
	
	# return the ks war file url discovered through the maven metadata file
	def discoverWarUrl
		
		# download the xml data from the built url for the metadata
		xmlBody = URI.parse(@warRootUrl + '/' + @properties[:metadataFilename]).read
		
		@metadata = XmlSimple.xml_in(xmlBody)
		
		# extract specific info from the metadata xml structure
		timeStamp = @metadata["versioning"][0]["snapshot"][0]["timestamp"][0]
		buildNumber = @metadata["versioning"][0]["snapshot"][0]["buildNumber"][0]
		
		raise 'Latest nightly build is not from today!  Check Hudson for build failures.' if !timeStamp.include? @dateString
		
		@warUrl = @warRootUrl + '/' + @moduleName + '-'  + @version + '-' + timeStamp + '-' + buildNumber + @@WARFILE_EXTENSION
	end
	
	# this method looks for the MANIFEST.MF file within the expanded war file
	# if the file is not found, it will raise a runtime exceptions
	def discoverDbConfigUrl
		
		manifestPath = @tomcatDeployPath + File::SEPARATOR + @@MANIFEST_PATH_SUFFIX
		
		raise "No manifest file found at " + manifestPath if !File.exist? manifestPath
			
		
		@buildId = ''
		File.open(manifestPath, 'r') do |configFile|
			configFile.read.each_line do |line|
				line.strip!
				if line.include? @@MANIFEST_BUILD_ID_PROPERTY
					@buildId = line.sub(@@MANIFEST_BUILD_ID_PROPERTY, '')
					break
				end
			end
		end
		
		@dbConfigUrl = @properties[:baseBuildsUrl] + @dateString + '-build-' + @buildId + '/' + @properties[:dbConfigModuleName]
	end
	
	def getRemoteFile(url, localFilePath, showProgressBar)
		pbar = nil
		lengthOption=nil
		progressOption = nil
		
		pBarIO = @logger
		if @logToConsole
			pBarIO = $>
		end
		
		if showProgressBar
			lengthOption = lambda {|t|
				if t && 0 < t
					pbar = ProgressBar.new("...", t, pBarIO)
					pbar.file_transfer_mode
				end
			}

			progressOption = lambda {|s|
				pbar.set s if pbar
			}
		end
		
		localFile = File.open(localFilePath, 'wb')
		
		# read remote file and write bytes to local file
		open(url, :content_length_proc => lengthOption, :progress_proc => progressOption) do |remoteFile| 
			while incoming = remoteFile.read(512)
				localFile.write(incoming)
			end
		end
		
		localFile.close
	end
	
	# logs to the defined logger IO object, and optionally to the console
	def log(logString)
		@logger.puts logString
		if @logToConsole
			puts logString
		end
	end
	
	def runPostDeployScripts
		propName = (@buildType.to_s + 'PostDeployScripts')
		scriptsProperty = @properties[propName.to_sym]
		
		if scriptsProperty == nil or scriptsProperty.empty?
			log 'No post deploy scripts defined in property ' + propName
			return
		end
		
		scriptsList = scriptsProperty.split(@@PROPERTY_LIST_DELIMITER)
		
		scriptsList.each do |script|
			log 'About to run the following script: ' + script
			runCommand script
		end
		
	end
	
	def runCommand(commandLine)
		IO.popen(commandLine) { |f|
			log f.readlines
		}
	end
	
end