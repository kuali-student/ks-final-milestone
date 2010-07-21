class Enumeration < ActiveRecord::Base
  set_table_name "KSEM_ENUM_VAL_T"
  alias enumerationid id
  alias enumerationid= id=

  def to_s
    val
  end
end
