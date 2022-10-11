import re

filename = "day17/input.txt"

f = open(filename).readline().strip()

coords = re.split("target area\: x\=|\, y\=", f)
(x_start, x_end) = list(map(int, coords[1].split("..")))
(y_end, y_start) = list(map(int, coords[2].split("..")))


initial_velocity = [0, 0]
initial_pos = [0, 0]
highest_initial_velocity = [1, 1]
while -initial_velocity[1] > y_end:

    pos = initial_pos.copy()
    velocity = initial_velocity.copy()
    while pos[1] + velocity[1] >= y_end:
        pos = [pos[0] + velocity[0], pos[1] + velocity[1]]
        if velocity[0] > 0:
            velocity[0] -= 1
        velocity[1] -= 1

    if y_start >= pos[1] >= y_end:
        highest_initial_velocity = initial_velocity.copy()

    initial_velocity[1] += 1

print(highest_initial_velocity)
print(int(highest_initial_velocity[1] * (highest_initial_velocity[1] + 1) / 2))
