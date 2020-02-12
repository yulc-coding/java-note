local bloomName = KEYS[1]
local value = KEYS[2]
local result = redis.call('BF.EXISTS', bloomName, value)
return result