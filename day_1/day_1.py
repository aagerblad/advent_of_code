f = open("day_1/input.txt")

prev_val = 0
increases = 0

for val in f:
    if prev_val == 0:
        prev_val = int(val)
        continue

    if int(val) >= prev_val:
        increases = increases + 1
    prev_val = int(val)

print("increases: " + str(increases))
