import numpy as np

filename = "day_9/input.txt"

bottom_map = np.genfromtxt(filename, dtype="int", delimiter=1)

rows = len(bottom_map[:, 0])
cols = len(bottom_map[0, :])
output = 0
for (row_i, row) in enumerate(bottom_map):
    for (col_i, col) in enumerate(row):
        lowest_adjacent = 9
        if row_i - 1 >= 0:
            lowest_adjacent = min(bottom_map[row_i - 1, col_i], lowest_adjacent)
        if row_i + 1 < rows:
            lowest_adjacent = min(bottom_map[row_i + 1, col_i], lowest_adjacent)
        if col_i - 1 >= 0:
            lowest_adjacent = min(bottom_map[row_i, col_i - 1], lowest_adjacent)
        if col_i + 1 < cols:
            lowest_adjacent = min(bottom_map[row_i, col_i + 1], lowest_adjacent)

        if col < lowest_adjacent:
            output = output + col + 1


print(output)
