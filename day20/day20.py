import numpy as np


def image_print(image):
    print("")
    for line in image:
        print("".join(line))
        # print(np.char.replace(line, " ", " "))


def pad(image, padding_cell):
    if padding_cell == ".":
        other_cell = "#"
    else:
        other_cell = "."
    height = len(image)
    width = len(image[0])

    if (
        other_cell in image[0, :]
        or other_cell in image[1, :]
        or other_cell in image[2, :]
        or other_cell in image[3, :]
        or other_cell in image[4, :]
    ):
        image = np.append(image, [image[-1, :]], axis=0)
        for y in range(height - 1, -1, -1):
            image[y + 1] = image[y]
        image[0, :] = np.char.replace(image[0, :], other_cell, padding_cell)
        return pad(image, padding_cell=padding_cell)  # 1

    elif (
        other_cell in image[-1, :]
        or other_cell in image[-2, :]
        or other_cell in image[-3, :]
        or other_cell in image[-4, :]
        or other_cell in image[-5, :]
    ):
        image = np.append(image, [image[-1, :]], axis=0)
        image[-1, :] = np.char.replace(image[-1, :], other_cell, padding_cell)
        return pad(image, padding_cell=padding_cell)  # 2

    elif (
        other_cell in image[:, 0]
        or other_cell in image[:, 1]
        or other_cell in image[:, 2]
        or other_cell in image[:, 3]
        or other_cell in image[:, 4]
    ):
        image = np.append(image, image[:, -1:], axis=1)
        for x in range(width - 1, -1, -1):
            image[:, x + 1] = image[:, x]
        image[:, 0] = np.char.replace(image[:, 0], other_cell, padding_cell)
        return pad(image, padding_cell=padding_cell)  # 3

    elif (
        other_cell in image[:, -1]
        or other_cell in image[:, -2]
        or other_cell in image[:, -3]
        or other_cell in image[:, -4]
        or other_cell in image[:, -5]
    ):
        image = np.append(image, image[:, -1:], axis=1)
        image[:, -1] = np.char.replace(image[:, -1], other_cell, padding_cell)
        return pad(image, padding_cell=padding_cell)  # 4
    else:
        return image


filename = "day20/input.txt"

f = open(filename)

algo_string = f.readline().strip()

f.readline()

image_string = []
for line in f:
    line = line.strip()
    if line == "":
        continue
    else:
        image_string.append([char for char in line])


image_string = np.array(image_string)

surrounding_cells = [
    [-1, -1],
    [-1, 0],
    [-1, 1],
    [0, -1],
    [0, 0],
    [0, 1],
    [1, -1],
    [1, 0],
    [1, 1],
]

image_string = pad(image_string, ".")

image_print(image_string)

for it in range(2):
    image_string_copy = image_string.copy()
    if it % 2 == 0:
        padding_cell = "."
    else:
        padding_cell = "#"

    height = len(image_string)
    width = len(image_string[0])
    for y in range(height):
        for x in range(width):

            cell_bin = ""
            for surrounding_cell in surrounding_cells:
                if (
                    x + surrounding_cell[1] < 0
                    or x + surrounding_cell[1] >= width
                    or y + surrounding_cell[0] < 0
                    or y + surrounding_cell[0] >= height
                ):
                    cell = padding_cell
                else:
                    cell = image_string[y + surrounding_cell[0]][
                        x + surrounding_cell[1]
                    ]

                cell_bin += cell

            cell_bin = cell_bin.replace(".", "0").replace("#", "1")

            cell_int = int(cell_bin, 2)
            image_string_copy[y, x] = algo_string[cell_int]

    if it % 2 == 0:
        padding_cell = "#"
    else:
        padding_cell = "."

    image_string = pad(image_string_copy, padding_cell)
    # image_string = image_string_copy
    # image_print(image_string)

print(np.count_nonzero(image_string == "#"))
