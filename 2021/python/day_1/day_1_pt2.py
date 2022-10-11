import numpy as np

f = np.fromfile("day_1/input.txt", dtype="int", sep=" ")

increases = 0

for idx, val in enumerate(f):
    if idx < 3:
        continue

    cur_val = f[idx] + f[idx - 1] + f[idx - 2]
    prev_val = f[idx - 1] + f[idx - 2] + f[idx - 3]

    # print(idx, cur_val, prev_val)

    if cur_val > prev_val:
        increases = increases + 1

print("increases: " + str(increases))
