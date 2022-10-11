import re
import numpy as np

filename = "day22/input.txt"
f = open(filename)
# on x=-20..26,y=-36..17,z=-47..7

input = []
for line in f:
    line = line.strip()
    line_input = re.split("[a-z\.\=\s\,]*", line)

    num_input = list(map(int, line_input[1:]))
    too_high = False
    for n in num_input:
        if n > 1000 or n <= -1000:
            too_high = True
            break
    if too_high:
        continue

    if re.match("on", line):
        input.append([1] + num_input)
    elif re.match("off", line):
        input.append([0] + num_input)

min = [0, 0, 0]
max = [0, 0, 0]
for inp in input:
    low = [inp[1], inp[3], inp[5]]
    for i in range(3):
        if low[i] < min[i]:
            min[i] = low[i]
    high = [inp[2], inp[4], inp[6]]
    for i in range(3):
        if high[i] > max[i]:
            max[i] = high[i]

input = np.array(input)

offset = [min[0], min[1], min[2]]
offset_comb = [min[0], min[0], min[1], min[1], min[2], min[2]]

input[:, 1:] = input[:, 1:] - offset_comb
print(input)

grid = np.zeros((max[0] - min[0] + 1, max[1] - min[1] + 1, max[2] - min[2] + 1))

for i in input:
    # turn on
    if i[0] == 1:
        for x in range(i[1], i[2] + 1):
            for y in range(i[3], i[4] + 1):
                for z in range(i[5], i[6] + 1):
                    grid[x, y, z] = 1

    # turn off
    elif i[0] == 0:
        for x in range(i[1], i[2] + 1):
            for y in range(i[3], i[4] + 1):
                for z in range(i[5], i[6] + 1):
                    grid[x, y, z] = 0


# print(grid)
print(int(np.sum(grid)))
