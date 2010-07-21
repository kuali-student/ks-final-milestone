require 'spec_helper'

describe EnumerationContextsController do

  def mock_enumeration_context(stubs={})
    @mock_enumeration_context ||= mock_model(EnumerationContext, stubs)
  end

  describe "GET index" do
    it "assigns all enumeration_contexts as @enumeration_contexts" do
      EnumerationContext.stub(:find).with(:all).and_return([mock_enumeration_context])
      get :index
      assigns[:enumeration_contexts].should == [mock_enumeration_context]
    end
  end

  describe "GET show" do
    it "assigns the requested enumeration_context as @enumeration_context" do
      EnumerationContext.stub(:find).with("37").and_return(mock_enumeration_context)
      get :show, :id => "37"
      assigns[:enumeration_context].should equal(mock_enumeration_context)
    end
  end

  describe "GET new" do
    it "assigns a new enumeration_context as @enumeration_context" do
      EnumerationContext.stub(:new).and_return(mock_enumeration_context)
      get :new
      assigns[:enumeration_context].should equal(mock_enumeration_context)
    end
  end

  describe "GET edit" do
    it "assigns the requested enumeration_context as @enumeration_context" do
      EnumerationContext.stub(:find).with("37").and_return(mock_enumeration_context)
      get :edit, :id => "37"
      assigns[:enumeration_context].should equal(mock_enumeration_context)
    end
  end

  describe "POST create" do

    describe "with valid params" do
      it "assigns a newly created enumeration_context as @enumeration_context" do
        EnumerationContext.stub(:new).with({'these' => 'params'}).and_return(mock_enumeration_context(:save => true))
        post :create, :enumeration_context => {:these => 'params'}
        assigns[:enumeration_context].should equal(mock_enumeration_context)
      end

      it "redirects to the created enumeration_context" do
        EnumerationContext.stub(:new).and_return(mock_enumeration_context(:save => true))
        post :create, :enumeration_context => {}
        response.should redirect_to(enumeration_context_url(mock_enumeration_context))
      end
    end

    describe "with invalid params" do
      it "assigns a newly created but unsaved enumeration_context as @enumeration_context" do
        EnumerationContext.stub(:new).with({'these' => 'params'}).and_return(mock_enumeration_context(:save => false))
        post :create, :enumeration_context => {:these => 'params'}
        assigns[:enumeration_context].should equal(mock_enumeration_context)
      end

      it "re-renders the 'new' template" do
        EnumerationContext.stub(:new).and_return(mock_enumeration_context(:save => false))
        post :create, :enumeration_context => {}
        response.should render_template('new')
      end
    end

  end

  describe "PUT update" do

    describe "with valid params" do
      it "updates the requested enumeration_context" do
        EnumerationContext.should_receive(:find).with("37").and_return(mock_enumeration_context)
        mock_enumeration_context.should_receive(:update_attributes).with({'these' => 'params'})
        put :update, :id => "37", :enumeration_context => {:these => 'params'}
      end

      it "assigns the requested enumeration_context as @enumeration_context" do
        EnumerationContext.stub(:find).and_return(mock_enumeration_context(:update_attributes => true))
        put :update, :id => "1"
        assigns[:enumeration_context].should equal(mock_enumeration_context)
      end

      it "redirects to the enumeration_context" do
        EnumerationContext.stub(:find).and_return(mock_enumeration_context(:update_attributes => true))
        put :update, :id => "1"
        response.should redirect_to(enumeration_context_url(mock_enumeration_context))
      end
    end

    describe "with invalid params" do
      it "updates the requested enumeration_context" do
        EnumerationContext.should_receive(:find).with("37").and_return(mock_enumeration_context)
        mock_enumeration_context.should_receive(:update_attributes).with({'these' => 'params'})
        put :update, :id => "37", :enumeration_context => {:these => 'params'}
      end

      it "assigns the enumeration_context as @enumeration_context" do
        EnumerationContext.stub(:find).and_return(mock_enumeration_context(:update_attributes => false))
        put :update, :id => "1"
        assigns[:enumeration_context].should equal(mock_enumeration_context)
      end

      it "re-renders the 'edit' template" do
        EnumerationContext.stub(:find).and_return(mock_enumeration_context(:update_attributes => false))
        put :update, :id => "1"
        response.should render_template('edit')
      end
    end

  end

  describe "DELETE destroy" do
    it "destroys the requested enumeration_context" do
      EnumerationContext.should_receive(:find).with("37").and_return(mock_enumeration_context)
      mock_enumeration_context.should_receive(:destroy)
      delete :destroy, :id => "37"
    end

    it "redirects to the enumeration_contexts list" do
      EnumerationContext.stub(:find).and_return(mock_enumeration_context(:destroy => true))
      delete :destroy, :id => "1"
      response.should redirect_to(enumeration_contexts_url)
    end
  end

end
