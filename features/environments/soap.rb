require 'savon'

Savon::Request.log = false
$input_hash = {}

Before('@timer') do
  @time = Time.now
end

After('@timer') do
  puts "Time to complete scenario: #{Time.now-@time}s"
end