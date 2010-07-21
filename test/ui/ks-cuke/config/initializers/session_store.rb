# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_kuali_test_suite_session',
  :secret      => 'a980bff8763e2d0617da3c2c26173f47b679d9635044c7f5ba8a926752cd6f8436aa2d5312bfddbbf623c22d9cf1d808a03cb30889da88cf5171da225ceecac0'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
