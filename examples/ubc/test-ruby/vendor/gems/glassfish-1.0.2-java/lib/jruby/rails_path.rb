module Rails
  class Configuration    
    def threadsafe?
      if(Rails::Configuration.instance_methods.include?'threadsafe!')
        preload_frameworks && cache_classes && !dependency_loading && action_controller.allow_concurrency
      else
        false
      end
    end
  end
end