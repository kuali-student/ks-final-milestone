require 'spec_helper'

describe Enumeration do
  before(:each) do
    @valid_attributes = {
      :id => "value for id",
      :abbrev_val => "value for abbrev_val",
      :cd => "value for cd",
      :enum_key => "value for enum_key",
      :sort_key => 1,
      :val => "value for val"
    }
  end

  it "should create a new instance given valid attributes" do
    Enumeration.create!(@valid_attributes)
  end
end
