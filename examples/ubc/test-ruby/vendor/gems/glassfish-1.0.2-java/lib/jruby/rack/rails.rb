require 'jruby/rack/grizzly_helper'
require 'rack/adapter/rails'

module JRuby
  module Rack
    class RailsGrizzlyHelper < GrizzlyHelper
      attr_accessor :rails_env, :rails_root, :context_root

      def initialize(glassfish_config = nil)
        super
        @rails_root = @glassfish_config.app_root
        @rails_env = @glassfish_config.environment
        @context_root = @glassfish_config.context_root 
      end

      def options
        {:public => public_root, :root => rails_root, :environment => rails_env, :prefix=>context_root}
      end

    end

    class RailsFactory
      def self.new
        helper = RailsGrizzlyHelper.instance
        ::Rack::Adapter::Rails.new(helper)
      end
    end
  end
end
