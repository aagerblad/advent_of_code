import numpy as np
import re

filename = "day13/input.txt"
f = open(filename)

input_lines = f.read().split("\n\n")

input_rows = np.array(list(map(int, re.split("\n|,", input_lines[0]))))
input_matrix = input_rows.reshape((int(len(input_rows) / 2), 2))

max_values = np.max(input_matrix, axis=0)
print(max_values)
dots_coordinates = np.zeros((max_values[0] + 1, max_values[1] + 1), dtype=int)

for inputs in input_matrix:
    dots_coordinates[inputs[0], inputs[1]] = True

instructions = input_lines[1].split("\n")
for i in instructions:

    if i == "":
        break
    shape = dots_coordinates.shape
    instruction = i.split("fold along ")[1].split("=")

    if instruction[0] == "y":
        instruction_val = int(instruction[1])
        for y in range(instruction_val + 1, shape[1]):
            delta = abs((instruction_val - y) * 2)
            dots_coordinates[:, y - delta] = np.logical_or(
                dots_coordinates[:, y - delta], dots_coordinates[:, y]
            )

        dots_coordinates = dots_coordinates[:, 0:instruction_val]

    elif instruction[0] == "x":
        instruction_val = int(instruction[1])
        for x in range(instruction_val + 1, shape[0]):
            delta = abs((instruction_val - x) * 2)
            dots_coordinates[x - delta, :] = np.logical_or(
                dots_coordinates[x - delta, :], dots_coordinates[x, :]
            )

        dots_coordinates = dots_coordinates[0:instruction_val, :]

transposed = dots_coordinates.transpose()
str_ = ""
for t in transposed:
    str_ = str_ + "".join(list(map(lambda x: " " if x == 0 else "#", t))) + "\n"
print(str_)
