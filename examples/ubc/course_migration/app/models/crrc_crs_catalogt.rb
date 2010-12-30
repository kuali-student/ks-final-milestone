require 'rubygems'
require 'composite_primary_keys'
class CrrcCrsCatalogt < ActiveRecord::Base
  set_table_name "SIS.CRRC_CRS_CATALOGT"
  set_primary_keys :crs_code, :crs_seq_no, :crs_ver_seq
end
