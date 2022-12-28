import math
import re

filename = "day18/input.txt"

f = open(filename)


def split_input(input):
    input = input.strip()
    if re.match("-?\d+", input):
        return int(input)
    output = []

    brackets = 0
    for i, char in enumerate(input):
        if char == "[":
            brackets += 1
        elif char == "]":
            brackets -= 1
        elif char == "," and brackets == 1:
            output.append(split_input(input[1:i]))
            output.append(split_input(input[i + 1 : -1]))
    return output


def add_numbers(x, y):
    output = [x, y]

    exploded = True
    is_split = True
    while exploded and is_split:

        while exploded:
            output, exploded = explode(output)

        output, is_split = split(output)
        if is_split:
            exploded = True

    return output


def split(x):
    # print("split", x)
    if type(x) == int and x >= 10:
        return [int(math.floor(x / 2)), int(math.ceil(x / 2))], True

    if type(x) == int:
        return x, False

    (x[0], is_split) = split(x[0])
    if is_split:
        return x, is_split
    (x[1], is_split) = split(x[1])

    return x, is_split


def explode(x):
    # print("explode", x)
    output, exploded, path, pair = exploder_helper(0, x, [])

    if exploded:
        if 1 in path:
            left_path = reduce_path(path)
            output = add_left(output, left_path, pair[0])
        if 0 in path:
            right_path = increase_path(path)
            output = add_right(output, right_path, pair[1])
    return output, exploded


def increase_path(orig_path):
    path = orig_path.copy()
    last_zero = len(path) - 1 - path[::-1].index(0)
    path[last_zero] = 1
    return path[: last_zero + 1]


def add_right(x, path, value):
    if type(x) == int:
        return x + value

    if len(path) == 0:
        x[0] = add_right(x[0], path, value)
        return x

    step = path.pop(0)
    x[step] = add_right(x[step], path, value)
    return x


def reduce_path(orig_path):
    path = orig_path.copy()
    last_one = len(path) - 1 - path[::-1].index(1)
    path[last_one] = 0
    return path[: last_one + 1]


def add_left(x, path, value):
    if type(x) == int:
        return x + value

    if len(path) == 0:
        x[1] = add_left(x[1], path, value)
        return x

    step = path.pop(0)
    x[step] = add_left(x[step], path, value)
    return x


def exploder_helper(depth, x, path):
    if type(x) == int:
        return x, False, [], []

    if depth >= 4 and type(x[0]) == int and type(x[1]) == int:
        return 0, True, path, x

    output = exploder_helper(depth + 1, x[0], path + [0])
    if output[1]:
        x[0] = output[0]
        return x, output[1], output[2], output[3]

    output = exploder_helper(depth + 1, x[1], path + [1])
    x[1] = output[0]
    return x, output[1], output[2], output[3]


def magnitude(x):
    if type(x) == int:
        return x

    return 3 * magnitude(x[0]) + 2 * magnitude(x[1])


# foo = [[[[4, 3], 4], 4], [7, [[8, 4], 9]]]
# bar = [1, 1]

# print(add_numbers(foo, bar))


number = split_input(f.readline())
for line in f:
    next_number = split_input(line)
    number = add_numbers(number, next_number)
    # print(number)

print(number)
print(magnitude(number))
