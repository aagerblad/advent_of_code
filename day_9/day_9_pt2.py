import numpy as np

filename = "day_9input.txt"

bottom_map = np.genfromtxt(filename, dtype="int", delimiter=1)

rows = len(bottom_map[:, 0])
cols = len(bottom_map[0, :])
low_points = []
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
            low_points.append((row_i, col_i, col))

sizes = []
for (lp_r, lp_c, lp_val) in low_points:
    bfs = [
        (lp_r - 1, lp_c, lp_val),
        (lp_r + 1, lp_c, lp_val),
        (lp_r, lp_c - 1, lp_val),
        (lp_r, lp_c + 1, lp_val),
    ]
    used_bfs = set([(lp_r, lp_c)])

    while bfs:
        step = bfs.pop()

        if step[0] < 0 or step[1] < 0 or step[0] >= rows or step[1] >= cols:
            continue

        val = bottom_map[step[0], step[1]]

        if val != 9 and val >= step[2]:
            used_bfs.add((step[0], step[1]))

            next_steps = [
                (step[0] - 1, step[1], step[2]),
                (step[0] + 1, step[1], step[2]),
                (step[0], step[1] - 1, step[2]),
                (step[0], step[1] + 1, step[2]),
            ]

            for ns in next_steps:
                if (ns[0], ns[1]) not in used_bfs:
                    bfs.append(ns)
    sizes.append(len(used_bfs))

print(np.prod(sorted(sizes)[-3:]))
