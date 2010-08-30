# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_sis_cbm_migration_session',
  :secret      => '3696b08063256c68e050a23ef063c71f640f0c4d24b79d6276b5a9a74fe1626e92ff86c95d5246ed7838dc2d8724794a9ce61046de2ec2d946198f63fba8d52d'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
