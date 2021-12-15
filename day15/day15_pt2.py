import numpy as np

filename = "day15/input.txt"

f = np.genfromtxt(filename, dtype="int", delimiter=1)


cur_pos = (0, 0)
size = np.shape(f)
increase = 5
new_f = np.zeros((size[0] * increase, size[1] * increase), dtype="int")


for x in range(increase):
    for y in range(increase):
        new_f[size[0] * x : (x + 1) * size[0], size[1] * y : (y + 1) * size[1]] = (
            (f.copy() + (x + y) - 1) % 9
        ) + 1

f = new_f.copy()
size = np.shape(f)

paths = [[cur_pos]]
completed_paths = []
shortest_distance = np.sum(f)

shortest_paths = np.zeros(size, dtype="int")

while paths:
    cur_path = paths.pop(0)
    cur_step = cur_path[-1]

    if cur_step == (0, 0):
        sum = f[cur_step]
    else:
        sum = shortest_paths[cur_path[-2]] + f[cur_step]

    if shortest_paths[cur_step] == 0 or sum < shortest_paths[cur_step]:
        shortest_paths[cur_step] = sum

        steps = [(-1, 0), (1, 0), (0, -1), (0, 1)]
        for s in steps:
            next_step = (cur_step[0] + s[0], cur_step[1] + s[1])
            if (
                0 <= next_step[0] < size[0]
                and 0 <= next_step[1] < size[1]
                and next_step not in cur_path
                and (
                    shortest_paths[next_step] == 0
                    or sum + f[next_step] < shortest_paths[next_step]
                )
            ):
                paths.append(cur_path + [next_step])

            if next_step == (size[0] - 1, size[1] - 1):
                shortest_distance = min(sum + f[next_step], shortest_distance)


shortest_distance = shortest_distance - f[0, 0]
print(shortest_distance)
