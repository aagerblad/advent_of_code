import numpy as np

filename = "day11/input.txt"

octo_map = np.genfromtxt(filename, dtype="int", delimiter=1)

num_of_flashes = 0
for step in range(1, 100 + 1):
    flashed = []

    for y in range(10):
        for x in range(10):
            octo_map[y, x] = octo_map[y, x] + 1

            if octo_map[y, x] > 9:
                flashed.append([y, x])

    for flashes in flashed:
        surround = [
            [-1, -1],
            [-1, 0],
            [-1, 1],
            [0, -1],
            [0, 1],
            [1, -1],
            [1, 0],
            [1, 1],
        ]
        for sur_step in surround:
            (new_y, new_x) = (flashes[0] + sur_step[0], flashes[1] + sur_step[1])

            if 0 <= new_y <= 9 and 0 <= new_x <= 9:
                octo_map[new_y, new_x] = octo_map[new_y, new_x] + 1

                if (octo_map[new_y, new_x] > 9) and [new_y, new_x] not in flashed:
                    flashed.append([new_y, new_x])

    for flashes in flashed:
        octo_map[flashes[0], flashes[1]] = 0
        num_of_flashes = num_of_flashes + 1


print(num_of_flashes)
