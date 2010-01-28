#!/usr/bin/env ruby

require 'tsung-api.rb'

sesh = Session.new('test.xml', 'test')
puts "Sesh: #{sesh.to_s}"
puts sesh.inspect
txn = sesh.add_transaction("transaction_name")
puts txn.inspect
puts "#{sesh.transactions.count}"
