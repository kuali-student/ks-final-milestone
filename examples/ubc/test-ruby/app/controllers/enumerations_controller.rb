class EnumerationsController < ApplicationController
  active_scaffold do |config|
    config.label = "Enumerations - KSEM_ENUM_VAL_ENT"
    config.columns.add :enumerationid
  end
end
