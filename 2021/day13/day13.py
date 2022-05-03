import numpy as np
import re


filename = "day13/input.txt"

f = open(filename)


input_lines = f.read().split("\n\n")

input_rows = np.array(list(map(int, re.split("\n|,", input_lines[0]))))
input_matrix = input_rows.reshape((int(len(input_rows) / 2), 2))

max_values = np.max(input_matrix, axis=0)

dots_coordinates = np.zeros((max_values[0] + 1, max_values[1] + 1), dtype=int)

for inputs in input_matrix:
    dots_coordinates[inputs[0], inputs[1]] = True

instruction = input_lines[1].split("\n")[0].split("fold along ")[1].split("=")

if instruction[0] == "y":
    instruction_val = int(instruction[1])
    for y in range(instruction_val + 1, max_values[1] + 1):
        delta = abs((instruction_val - y) * 2)
        dots_coordinates[:, y - delta] = np.logical_or(
            dots_coordinates[:, y - delta], dots_coordinates[:, y]
        )

    dots_coordinates = dots_coordinates[:, 0:instruction_val]

elif instruction[0] == "x":
    instruction_val = int(instruction[1])
    for x in range(instruction_val + 1, max_values[0] + 1):
        delta = abs((instruction_val - x) * 2)
        dots_coordinates[x - delta, :] = np.logical_or(
            dots_coordinates[x - delta, :], dots_coordinates[x, :]
        )

    dots_coordinates = dots_coordinates[0:instruction_val, :]

print(np.count_nonzero(dots_coordinates))
# print(dots_coordinates.transpose())
