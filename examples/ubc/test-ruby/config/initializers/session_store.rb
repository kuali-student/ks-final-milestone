# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_test-kscdm_session',
  :secret      => '99908fb0f64f54955fa14b6b5e682124b32320d89cddbaf983b8736e7abca6f26646c7512a50fd25765ee493f853d9ba3f06aa1dd44278c155a1cbd14cfcba58'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
