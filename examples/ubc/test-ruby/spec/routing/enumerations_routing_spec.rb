require 'spec_helper'

describe EnumerationsController do
  describe "routing" do
    it "recognizes and generates #index" do
      { :get => "/enumerations" }.should route_to(:controller => "enumerations", :action => "index")
    end

    it "recognizes and generates #new" do
      { :get => "/enumerations/new" }.should route_to(:controller => "enumerations", :action => "new")
    end

    it "recognizes and generates #show" do
      { :get => "/enumerations/1" }.should route_to(:controller => "enumerations", :action => "show", :id => "1")
    end

    it "recognizes and generates #edit" do
      { :get => "/enumerations/1/edit" }.should route_to(:controller => "enumerations", :action => "edit", :id => "1")
    end

    it "recognizes and generates #create" do
      { :post => "/enumerations" }.should route_to(:controller => "enumerations", :action => "create") 
    end

    it "recognizes and generates #update" do
      { :put => "/enumerations/1" }.should route_to(:controller => "enumerations", :action => "update", :id => "1") 
    end

    it "recognizes and generates #destroy" do
      { :delete => "/enumerations/1" }.should route_to(:controller => "enumerations", :action => "destroy", :id => "1") 
    end
  end
end
