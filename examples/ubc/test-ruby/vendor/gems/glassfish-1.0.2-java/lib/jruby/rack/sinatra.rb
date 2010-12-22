require 'rack/handler/grizzly'
$: << "#{File.dirname($glassfish_appFile)}"
load $glassfish_appFile
disable :run

set :server, 'grizzly'
set :environment, $rackEnv.to_sym
set :root, $glassfish_appRoot
set :port, 0000
set :views, $glassfish_appRoot + '/views'
set :public,  $glassfish_appRoot + '/public'
set :sessions, false
set :logging, true
set :app_file, $glassfish_appFile
set :adapter, 'grizzly'


$grizzly_rack_app
