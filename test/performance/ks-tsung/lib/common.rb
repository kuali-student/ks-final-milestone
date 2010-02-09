# 
# == Synopsis
#
# Place for common routines and utilities
#
# Author:: Kyle Campos (mailto:kcampos@rsmart.com)
#

module Common
  
  def Common.dir_simplify(path)
    # Remove complex paths: /lib/../config -> /config
    path.sub(/(\w+\/\.\.\/)/, '')
  end

end