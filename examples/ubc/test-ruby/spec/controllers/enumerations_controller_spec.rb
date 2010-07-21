require 'spec_helper'

describe EnumerationsController do

  def mock_enumeration(stubs={})
    @mock_enumeration ||= mock_model(Enumeration, stubs)
  end

  describe "GET index" do
    it "assigns all enumerations as @enumerations" do
      Enumeration.stub(:find).with(:all).and_return([mock_enumeration])
      get :index
      assigns[:enumerations].should == [mock_enumeration]
    end
  end

  describe "GET show" do
    it "assigns the requested enumeration as @enumeration" do
      Enumeration.stub(:find).with("37").and_return(mock_enumeration)
      get :show, :id => "37"
      assigns[:enumeration].should equal(mock_enumeration)
    end
  end

  describe "GET new" do
    it "assigns a new enumeration as @enumeration" do
      Enumeration.stub(:new).and_return(mock_enumeration)
      get :new
      assigns[:enumeration].should equal(mock_enumeration)
    end
  end

  describe "GET edit" do
    it "assigns the requested enumeration as @enumeration" do
      Enumeration.stub(:find).with("37").and_return(mock_enumeration)
      get :edit, :id => "37"
      assigns[:enumeration].should equal(mock_enumeration)
    end
  end

  describe "POST create" do

    describe "with valid params" do
      it "assigns a newly created enumeration as @enumeration" do
        Enumeration.stub(:new).with({'these' => 'params'}).and_return(mock_enumeration(:save => true))
        post :create, :enumeration => {:these => 'params'}
        assigns[:enumeration].should equal(mock_enumeration)
      end

      it "redirects to the created enumeration" do
        Enumeration.stub(:new).and_return(mock_enumeration(:save => true))
        post :create, :enumeration => {}
        response.should redirect_to(enumeration_url(mock_enumeration))
      end
    end

    describe "with invalid params" do
      it "assigns a newly created but unsaved enumeration as @enumeration" do
        Enumeration.stub(:new).with({'these' => 'params'}).and_return(mock_enumeration(:save => false))
        post :create, :enumeration => {:these => 'params'}
        assigns[:enumeration].should equal(mock_enumeration)
      end

      it "re-renders the 'new' template" do
        Enumeration.stub(:new).and_return(mock_enumeration(:save => false))
        post :create, :enumeration => {}
        response.should render_template('new')
      end
    end

  end

  describe "PUT update" do

    describe "with valid params" do
      it "updates the requested enumeration" do
        Enumeration.should_receive(:find).with("37").and_return(mock_enumeration)
        mock_enumeration.should_receive(:update_attributes).with({'these' => 'params'})
        put :update, :id => "37", :enumeration => {:these => 'params'}
      end

      it "assigns the requested enumeration as @enumeration" do
        Enumeration.stub(:find).and_return(mock_enumeration(:update_attributes => true))
        put :update, :id => "1"
        assigns[:enumeration].should equal(mock_enumeration)
      end

      it "redirects to the enumeration" do
        Enumeration.stub(:find).and_return(mock_enumeration(:update_attributes => true))
        put :update, :id => "1"
        response.should redirect_to(enumeration_url(mock_enumeration))
      end
    end

    describe "with invalid params" do
      it "updates the requested enumeration" do
        Enumeration.should_receive(:find).with("37").and_return(mock_enumeration)
        mock_enumeration.should_receive(:update_attributes).with({'these' => 'params'})
        put :update, :id => "37", :enumeration => {:these => 'params'}
      end

      it "assigns the enumeration as @enumeration" do
        Enumeration.stub(:find).and_return(mock_enumeration(:update_attributes => false))
        put :update, :id => "1"
        assigns[:enumeration].should equal(mock_enumeration)
      end

      it "re-renders the 'edit' template" do
        Enumeration.stub(:find).and_return(mock_enumeration(:update_attributes => false))
        put :update, :id => "1"
        response.should render_template('edit')
      end
    end

  end

  describe "DELETE destroy" do
    it "destroys the requested enumeration" do
      Enumeration.should_receive(:find).with("37").and_return(mock_enumeration)
      mock_enumeration.should_receive(:destroy)
      delete :destroy, :id => "37"
    end

    it "redirects to the enumerations list" do
      Enumeration.stub(:find).and_return(mock_enumeration(:destroy => true))
      delete :destroy, :id => "1"
      response.should redirect_to(enumerations_url)
    end
  end

end
