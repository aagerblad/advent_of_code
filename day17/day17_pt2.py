import re

filename = "day17/input.txt"

f = open(filename).readline().strip()

coords = re.split("target area\: x\=|\, y\=", f)
(x_start, x_end) = list(map(int, coords[1].split("..")))
(y_end, y_start) = list(map(int, coords[2].split("..")))


initial_velocity = [0, 0]
initial_pos = [0, 0]
successful_velocities = []
while -initial_velocity[1] >= y_end:
    while initial_velocity[0] <= x_end:
        pos = initial_pos.copy()
        velocity = initial_velocity.copy()
        while pos[1] + velocity[1] >= y_end and pos[0] + velocity[0] <= x_end:
            pos = [pos[0] + velocity[0], pos[1] + velocity[1]]
            if velocity[0] > 0:
                velocity[0] -= 1
            velocity[1] -= 1

        if y_start >= pos[1] >= y_end and x_start <= pos[0] <= x_end:
            successful_velocities.append(initial_velocity.copy())

        initial_velocity[0] += 1

    initial_velocity[0] = 0
    if initial_velocity[1] > 0:
        initial_velocity[1] = -initial_velocity[1]
    else:
        initial_velocity[1] = -initial_velocity[1] + 1

print(len(successful_velocities))
