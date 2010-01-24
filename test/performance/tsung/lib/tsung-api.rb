#!/usr/bin/env ruby
require 'rubygems'
require 'actions.rb'
require 'builder'

# Main Sessions class
class TsungSessions

  attr_accessor :config
  
  def initialize(config_path)
    @config = config_path
  end
  
  def to_s
    "TsungSessions-> Config: #{@config}"
  end
end


# Individual session
class Session < TsungSessions
  
  attr_accessor :name, :probability, :type
  
  def initialize(config, session_name, probability='100%', type='ts_http')
    super(config)
    @name = session_name
    @probability = probability
    @type = type
  end
  
  # Add a transaction object to this session object
  def add_transaction(txn_name)
    Transaction.new(txn_name, @config, @name, @probability, @type)
  end
  
  # Return all transaction object associated with this session object
  def transactions
    results = []
    ObjectSpace.each_object(Transaction) do |transact|
      results << transact if(transact.name == self.name)
    end
    
    return results
  end
  
  def to_s
    "Session-> #{super.to_s} Name: #{@name} Probability: #{@probability} Type: #{@type}"
  end
end

# Individual transaction with many actions
class Transaction < Session
  
  attr_accessor :txn_name
  
  def initialize(txn_name, config, session_name, probability='100%', type='ts_http')
    super(config, session_name, probability='100%', type='ts_http')
    @txn_name = txn_name
    
    # Action object will hold all requests
    @action_obj = Actions.new(config)
  end  
  
  def actions
    @action_obj.list
  end
  
end


# Class meant to handle all writing of XML
class Writer

  attr_accessor :file_path
  
  def initialize(file_path)
    @file_path = file_path
  end
  
end

# XML Writer
class XmlWriter < Writer
  
  attr_reader :xml
  
  def initialize(file_path)
    super(file_path)
    @xml = Builder::XmlMarkup.new(:target => file_path, :indent => 2)
  end
  
end

# Log writer
class LogWriter < Writer
  
  attr_reader :file
  
  def initialize(file_path)
    super(file_path)
    @file = File.open(file_path, 'w')
  end
  
  def info(msg="")
    puts(msg)
    @file.puts(msg)
  end
  
  def debug(msg="")
    
  end
  
end
