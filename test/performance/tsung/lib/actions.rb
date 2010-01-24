#!/usr/bin/env ruby

class Actions
  # Shell class for now...not sure what will go here
  
  attr_accessor :config
  attr_reader :list
  
  def initialize(config)
    @config = config
    @list = [] # request list
  end
  
  def write
    # Write the XML
  end
  
  def list
    @list
  end
  
end