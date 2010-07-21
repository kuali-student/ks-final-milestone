require 'spec_helper'

describe EnumerationContextsController do
  describe "routing" do
    it "recognizes and generates #index" do
      { :get => "/enumeration_contexts" }.should route_to(:controller => "enumeration_contexts", :action => "index")
    end

    it "recognizes and generates #new" do
      { :get => "/enumeration_contexts/new" }.should route_to(:controller => "enumeration_contexts", :action => "new")
    end

    it "recognizes and generates #show" do
      { :get => "/enumeration_contexts/1" }.should route_to(:controller => "enumeration_contexts", :action => "show", :id => "1")
    end

    it "recognizes and generates #edit" do
      { :get => "/enumeration_contexts/1/edit" }.should route_to(:controller => "enumeration_contexts", :action => "edit", :id => "1")
    end

    it "recognizes and generates #create" do
      { :post => "/enumeration_contexts" }.should route_to(:controller => "enumeration_contexts", :action => "create") 
    end

    it "recognizes and generates #update" do
      { :put => "/enumeration_contexts/1" }.should route_to(:controller => "enumeration_contexts", :action => "update", :id => "1") 
    end

    it "recognizes and generates #destroy" do
      { :delete => "/enumeration_contexts/1" }.should route_to(:controller => "enumeration_contexts", :action => "destroy", :id => "1") 
    end
  end
end
