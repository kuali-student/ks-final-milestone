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

module JRuby
  module Rack
    class Response
      include Java::com.sun.grizzly.jruby.rack.RackResponse
      def initialize(arr)
        @status, @headers, @body = *arr
      end

      def getStatus
        @status
      end

      def getHeaders
        @headers
      end

      def getBody
        b = ""
        begin
        @body.each {|part| b << part }
        ensure
          @body.close  if @body.respond_to? :close
        end
        b
      end

      def respond(response)
        if fwd = @headers["Forward"]
          fwd.call(response)
        else
            write_status(response)
            write_headers(response)
            write_body(response)
        end
      end

      def write_status(response)
        response.setStatus(@status.to_i)
      end

      def write_headers(response)
        mime_headers = response.getResponse().getMimeHeaders()

        @headers.each do |k,v|
          case k
          when /^Content-Type$/i
            response.content_type = v.to_s
          when /^Content-Length$/i
            response.content_length = v.to_i
          else
              if v.respond_to?(:each)                              
                v.each do |val|
                  len = rstrip_newline(val)
                  mime_headers.addValue(k.to_java_bytes,0, k.length).setBytes(val.to_java_bytes, 0, len)
                end
              else
                len = rstrip_newline(v)
                mime_headers.addValue(k.to_java_bytes,0, k.length).setBytes(val.to_java_bytes, 0, val.length)
              end
          end
        end
      end

      # Strips off any \r or \n from the end of the
      # v MUST not be null
      def rstrip_newline(v)
        len = v.size
        (v.size-1).downto(0) do |i|
          if(v[i] == 10 || v[i] == 13)
            len -= 1
          end
        end
        len
      end
      

      def write_body(response)
        stream = response.output_stream
        begin
          @body.each do |el|
            stream.write(el.to_java_bytes)
          end
        ensure
          @body.close  if @body.respond_to? :close
        end          
      end
    end


    class GrizzlyLog
      def initialize(logger = $logger)
        @logger = logger
      end
      def puts(msg)
        write msg.to_s
      end

      def write(msg)
        case $gf_ruby_log_level
          when 3,4,5 then @logger.severe("#{msg}")
          when 2 then @logger.warning("#{msg}")
          when 0,1 then @logger.info("#{msg}")
          else @logger.info("#{msg}") 
        end
      end

      def flush; end
      def close; end
    end

    class GrizzlyHelper
      attr_reader :public_root, :gem_path

      def initialize(glassfish_config = nil)
        @glassfish_config = glassfish_config || $glassfish_config
        @public_root = File.join(@glassfish_config.app_root, "public")
        @gem_path = @glassfish_config.gem_path
        setup_gems
      end

      def logdev
        @logdev ||= GrizzlyLog.new
      end

      def logger
        @logger ||= begin; require 'logger'; Logger.new(logdev); end
      end

      def setup_gems
        ENV['GEM_PATH'] = @gem_path
      end

      def self.instance
        @instance ||= self.new
      end
    end

    class Errors
      def initialize(file_server)
        @file_server = file_server
      end

      def call(env)
        [code = response_code(env), *response_content(env, code)]
      end

      def response_code(env)
        exc = env['rack.exception']
        if exc
          env['rack.showstatus.detail'] = exc.getMessage
          if exc.getCause.kind_of?(Java::JavaLang::InterruptedException)
            503
          else
            500
          end
        else
          500
        end
      end

      def response_content(env, code)
        @responses ||= Hash.new do |h,k|
          env["PATH_INFO"] = "/#{code}.html"
          response = @file_server.call(env)
          body = response[2]
          unless Array === body
            newbody = ""
            body.each do |chunk|
              newbody << chunk
            end
            response[2] = [newbody]
          end
          h[k] = response
        end
        response = @responses[code]
        if response[0] != 404
          env["rack.showstatus.detail"] = nil
          response[1..2]
        else
          [{}, []]
        end
      end
    end

    class ErrorsApp
      def self.new
        ::Rack::Builder.new {
          use ::Rack::ShowStatus
          run Errors.new(::Rack::File.new(GrizzlyHelper.instance.public_root))
        }.to_app
      end
    end
  end
end