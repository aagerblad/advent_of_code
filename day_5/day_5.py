import re
import numpy as np

filename = "day_5/input.txt"

f = open(filename)

lines = np.array([], dtype="int")

for line in f:
    val = list(map(int, filter(None, re.split("\W+", line))))
    lines = np.append(lines, val)

lines = np.reshape(lines, (int(len(lines) / 4), 4))

max_x = np.amax(lines[:, [0, 2]])
max_y = np.amax(lines[:, [1, 3]])

bottom_map = np.zeros((max_x + 1, max_y + 1))

for line in lines:
    start = line[[0, 1]]
    end = line[[2, 3]]

    x_step = end[0] - start[0]
    y_step = end[1] - start[1]

    if x_step < 0:
        x_step = -1
    elif x_step > 0:
        x_step = 1
    else:
        x_step = 0

    if y_step < 0:
        y_step = -1
    elif y_step > 0:
        y_step = 1
    else:
        y_step = 0

    if x_step != 0 and y_step != 0:
        continue

    bottom_map[start[0], start[1]] = bottom_map[start[0], start[1]] + 1

    while start[0] != end[0] or start[1] != end[1]:

        start[0] = start[0] + x_step
        start[1] = start[1] + y_step

        bottom_map[start[0], start[1]] = bottom_map[start[0], start[1]] + 1

print(bottom_map)
print(np.count_nonzero(bottom_map >= 2))
