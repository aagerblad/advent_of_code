import copy
import re
import numpy as np

filename = "day22/input.txt"
f = open(filename)

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
    # if too_high:
    #     continue

    if re.match("on", line):
        input.append([1] + num_input)
    elif re.match("off", line):
        input.append([0] + num_input)


input = np.array(input)

mapping_table = []
for i in range(3):
    a = input[:, i * 2 + 1 : i * 2 + 2]
    b = np.append(a, input[:, i * 2 + 2 : i * 2 + 3] + 1, axis=0)
    c = list(np.unique(b))

    mapping_table.append(c)

size_table = copy.deepcopy(mapping_table)
for i in range(3):
    for l in range(len(mapping_table[i]) - 1):
        size_table[i][l] = mapping_table[i][l + 1] - mapping_table[i][l]
    size_table[i][-1] = 0

for i in range(3):
    for j in range(len(input)):
        input[j, i * 2 + 1] = mapping_table[i].index(input[j, i * 2 + 1])
        input[j, i * 2 + 2] = mapping_table[i].index(input[j, i * 2 + 2] + 1)

max = [0, 0, 0]
for inp in input:
    high = [inp[2], inp[4], inp[6]]
    for i in range(3):
        if high[i] > max[i]:
            max[i] = high[i]

print(max)

grid = np.zeros((max[0] + 1, max[1] + 1, max[2] + 1), dtype=np.int)

for i in input:
    # turn on
    if i[0] == 1:
        for x in range(i[1], i[2]):
            for y in range(i[3], i[4]):
                for z in range(i[5], i[6]):
                    grid[x, y, z] = 1

    # turn off
    elif i[0] == 0:
        for x in range(i[1], i[2]):
            for y in range(i[3], i[4]):
                for z in range(i[5], i[6]):
                    grid[x, y, z] = 0


print("done with grid")

grid_sum = 0
for x in range(max[0] + 1):
    for y in range(max[1] + 1):
        for z in range(max[2] + 1):
            grid_sum = (
                grid_sum
                + size_table[0][x] * size_table[1][y] * size_table[2][z] * grid[x, y, z]
            )


# print(grid)
print("sum:", grid_sum)
