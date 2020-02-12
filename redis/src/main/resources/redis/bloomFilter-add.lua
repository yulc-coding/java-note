local values = KEYS
local bloomName = ARGV[1]
local result
for k,v in ipairs(values) do
 result = redis.call('BF.ADD',bloomName,v)
end
return result