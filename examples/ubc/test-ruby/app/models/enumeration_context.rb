class EnumerationContext < ActiveRecord::Base
  set_table_name "KSEM_CTX_T"
  alias enumerationcontextid id
  alias enumerationcontextid= id=

  def to_s
    ctx_key
  end

end
