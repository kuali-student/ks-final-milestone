class EnumerationContextsController < ApplicationController
  active_scaffold do |config|
    config.columns.add :enumerationcontextid
  end

#  # GET /enumeration_contexts
#  # GET /enumeration_contexts.xml
#  def index
#    @enumeration_contexts = EnumerationContext.all
#
#    respond_to do |format|
#      format.html # index.html.erb
#      format.xml  { render :xml => @enumeration_contexts }
#    end
#  end
#
#  # GET /enumeration_contexts/1
#  # GET /enumeration_contexts/1.xml
#  def show
#    @enumeration_context = EnumerationContext.find(params[:id])
#
#    respond_to do |format|
#      format.html # show.html.erb
#      format.xml  { render :xml => @enumeration_context }
#    end
#  end
#
#  # GET /enumeration_contexts/new
#  # GET /enumeration_contexts/new.xml
#  def new
#    @enumeration_context = EnumerationContext.new
#
#    respond_to do |format|
#      format.html # new.html.erb
#      format.xml  { render :xml => @enumeration_context }
#    end
#  end
#
#  # GET /enumeration_contexts/1/edit
#  def edit
#    @enumeration_context = EnumerationContext.find(params[:id])
#  end
#
#  # POST /enumeration_contexts
#  # POST /enumeration_contexts.xml
#  def create
#    @enumeration_context = EnumerationContext.new(params[:enumeration_context])
#
#    respond_to do |format|
#      if @enumeration_context.save
#        flash[:notice] = 'EnumerationContext was successfully created.'
#        format.html { redirect_to(@enumeration_context) }
#        format.xml  { render :xml => @enumeration_context, :status => :created, :location => @enumeration_context }
#      else
#        format.html { render :action => "new" }
#        format.xml  { render :xml => @enumeration_context.errors, :status => :unprocessable_entity }
#      end
#    end
#  end
#
#  # PUT /enumeration_contexts/1
#  # PUT /enumeration_contexts/1.xml
#  def update
#    @enumeration_context = EnumerationContext.find(params[:id])
#
#    respond_to do |format|
#      if @enumeration_context.update_attributes(params[:enumeration_context])
#        flash[:notice] = 'EnumerationContext was successfully updated.'
#        format.html { redirect_to(@enumeration_context) }
#        format.xml  { head :ok }
#      else
#        format.html { render :action => "edit" }
#        format.xml  { render :xml => @enumeration_context.errors, :status => :unprocessable_entity }
#      end
#    end
#  end
#
#  # DELETE /enumeration_contexts/1
#  # DELETE /enumeration_contexts/1.xml
#  def destroy
#    @enumeration_context = EnumerationContext.find(params[:id])
#    @enumeration_context.destroy
#
#    respond_to do |format|
#      format.html { redirect_to(enumeration_contexts_url) }
#      format.xml  { head :ok }
#    end
#  end
end
