#
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
#
# Copyright 2007-2008 Sun Microsystems, Inc. All rights reserved.
#
# The contents of this file are subject to the terms of either the GNU
# General Public License Version 2 only ("GPL") or the Common Development
# and Distribution License("CDDL") (collectively, the "License").  You
# may not use this file except in compliance with the License. You can obtain
# a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
# or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
# language governing permissions and limitations under the License.
#
# When distributing the software, include this License Header Notice in each
# file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
# Sun designates this particular file as subject to the "Classpath" exception
# as provided by Sun in the GPL Version 2 section of the License file that
# accompanied this code.  If applicable, add the following below the License
# Header, with the fields enclosed by brackets [] replaced by your own
# identifying information: "Portions Copyrighted [year]
# [name of copyright owner]"
#
# Contributor(s):
#
# If you wish your version of this file to be governed by only the CDDL or
# only the GPL Version 2, indicate your decision by adding "[Contributor]
# elects to include this software in this distribution under the [CDDL or GPL
# Version 2] license."  If you don't indicate a single choice of license, a
# recipient has the option to distribute your version of this file under
# either the CDDL, the GPL Version 2 or to extend the choice of license to
# its licensees as provided above.  However, if you add GPL Version 2 code
# and therefore, elected the GPL Version 2 license, then the option applies
# only if the new code is made subject to such option by the copyright
# holder.
#
require 'jruby/rack/rails'

require 'rubygems'

begin
  gem 'rack'
  require 'rack'
rescue LoadError 
  error = <<EOF
###
  ERROR : Is rack installed? Install Rack using 'gem install rack'
###
EOF
    puts "\n"+error
    raise
end


# Based on http://github.com/macournoyer/thin/tree/master/lib/rack/adapter/rails.rb
# Adapter to run a Rails app with any supported Rack handler.
# By default it will try to load the Rails application in the
# current directory in the development environment.
# Options:
#  root: Root directory of the Rails app
#  env: Rails environment to run in (development, production or test)
# Based on http://fuzed.rubyforge.org/ Rails adapter
module Rack
  module Adapter
    class Rails
      FILE_METHODS = %w(GET HEAD).freeze

      def initialize(helper)
        options = helper.options        
        @rails_grizzly_helper = helper
        @root = options[:root] || Dir.pwd
        @env = options[:environment] || 'development'
        @prefix = options[:prefix]
        @public = options[:public]

        load_application

        @rails_app = if rack_based?
          ActionController::Dispatcher.new
        else
          CgiApp.new
        end

        @file_app = Rack::File.new(::File.join(@root, "public"))

      end

      def rack_based?
        ActionController.const_defined?(:Dispatcher) &&
          (ActionController::Dispatcher.instance_methods.include?(:call) ||
           ActionController::Dispatcher.instance_methods.include?("call"))
      end

      def load_application
        ENV['RAILS_ROOT'] = @root        
        ENV['RAILS_ENV'] = @env


        require "#{@root}/config/environment"        
        require 'dispatcher'

        setup_logger

        if !@prefix.nil? && @prefix != "/"
          if ActionController::Base.respond_to?('relative_url_root=') #for rails < 2.2
            ActionController::Base.relative_url_root = @prefix
          else
            ActionController::AbstractRequest.relative_url_root = @prefix
          end
        end
        
      end

      def file_exist?(path)
          full_path = ::File.join(@file_app.root, Utils.unescape(path))
        ::File.file?(full_path) && ::File.readable_real?(full_path)
      end

      def call(env)
        path        = env['PATH_INFO'].chomp('/')
        method      = env['REQUEST_METHOD']
        cached_path = (path.empty? ? 'index' : path) + ActionController::Base.page_cache_extension

        if FILE_METHODS.include?(method)
          if file_exist?(path)              # Serve the file if it's there
            return @file_app.call(env)
          elsif file_exist?(cached_path)    # Serve the page cache if it's there
            env['PATH_INFO'] = cached_path
            return @file_app.call(env)
          end
        end

        # No static file, let Rails handle it
        @rails_app.call(env)
      end

      protected

        # For Rails pre Rack (2.3)
        class CgiApp
          require 'cgi'
          def call(env)
            request         = Request.new(env)
            response        = Response.new
            session_options = ActionController::CgiRequest::DEFAULT_SESSION_OPTIONS
            cgi             = CGIWrapper.new(request, response)

            Dispatcher.dispatch(cgi, session_options, response)

            response.finish
          end
        end

        class CGIWrapper < ::CGI
          def initialize(request, response, *args)
            @request = request
            @response = response
            @args = *args
            @input = request.body

            super *args
          end

          def header(options = "text/html")
            if options.is_a?(String)
              @response['Content-Type'] = options unless @response['Content-Type']
            else
              @response['Content-Length'] = options.delete('Content-Length').to_s if options['Content-Length']

              @response['Content-Type'] = options.delete('type') || "text/html"
              @response['Content-Type'] += "; charset=" + options.delete('charset') if options['charset']

              @response['Content-Language'] = options.delete('language') if options['language']
              @response['Expires'] = options.delete('expires') if options['expires']

              @response.status = options.delete('Status') if options['Status']

              # Convert 'cookie' header to 'Set-Cookie' headers.
              # Because Set-Cookie header can appear more the once in the response body,
              # we store it in a line break seperated string that will be translated to
              # multiple Set-Cookie header by the handler.
              if cookie = options.delete('cookie')
              cookies = []

              case cookie
              when Array then cookie.each { |c| cookies << c.to_s }
              when Hash  then cookie.each { |_, c| cookies << c.to_s }
              else            cookies << cookie.to_s
              end

              @output_cookies.each { |c| cookies << c.to_s } if @output_cookies

              @response['Set-Cookie'] = cookies unless cookies.empty?
            end

              options.each { |k,v| @response[k] = v }
            end

            ""
          end

          def params
            @params ||= @request.params
          end

          def cookies
            @request.cookies
          end

          def query_string
            @request.query_string
          end

          # Used to wrap the normal args variable used inside CGI.
          def args
            @args
          end

          # Used to wrap the normal env_table variable used inside CGI.
          def env_table
            @request.env
          end

          # Used to wrap the normal stdinput variable used inside CGI.
          def stdinput
            @input
          end

          def stdoutput
            STDERR.puts "stdoutput should not be used."
            @response.body
          end
      end

      private

        def setup_logger
          #Based on the server log level adjust rails log level as well

          if $glassfish_log_level
            begin
              log_level = ActiveSupport::BufferedLogger.const_get($glassfish_log_level)
            rescue
              # We do nothing. This just means that user has not explicitly asked to change the log level
            end
          end

          if defined?(::RAILS_DEFAULT_LOGGER)
            class << ::RAILS_DEFAULT_LOGGER # Make these accessible to wire in the log device
              public :instance_variable_get, :instance_variable_set
            end
            if defined?(ActiveSupport::BufferedLogger) # Rails 2.x
              old_device = ::RAILS_DEFAULT_LOGGER.instance_variable_get "@log"
              old_device.close rescue nil
              ::RAILS_DEFAULT_LOGGER.instance_variable_set "@log", @rails_grizzly_helper.logdev
            else # Rails 1.x
              old_device = ::RAILS_DEFAULT_LOGGER.instance_variable_get "@logdev"
              old_device.close rescue nil
              ::RAILS_DEFAULT_LOGGER.instance_variable_set "@logdev", Logger::LogDevice.new(@rails_grizzly_helper.logdev)
            end
            ::RAILS_DEFAULT_LOGGER.level = log_level if log_level
            $gf_ruby_log_level = ::RAILS_DEFAULT_LOGGER.level 
          end
        end

    end
  end
end

