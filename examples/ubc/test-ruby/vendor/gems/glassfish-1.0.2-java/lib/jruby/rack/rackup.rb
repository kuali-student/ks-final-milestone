require 'rubygems'

begin
  gem 'rack'
  require 'rack'
rescue LoadError
  error = <<EOF
###
  ERROR : Is rack installed? Install using 'gem install rack'
###
EOF
    puts "\n"+error
end


def get_rackup_app(config, env={})
  config ||= "config.ru"
  #Load all the files inside this directory
  $: << "#{File.dirname(config)}"

  #Read contents of rackup script
  config_data = File.read(config)

  #create an app based on whats given by the rackup script
  inner_app = eval "Rack::Builder.new {( " + config_data + "\n )}.to_app",nil, config

  case env
  when "development", "test"
    app = Rack::Builder.new {
      use Rack::CommonLogger, $stderr
      use Rack::ShowExceptions
      use Rack::Lint
      run inner_app
    }.to_app

  when "deployment", "production"
    app = Rack::Builder.new {
      use Rack::CommonLogger, $stderr
      run inner_app
    }.to_app

  when "none"
    app = inner_app  
  end

end
