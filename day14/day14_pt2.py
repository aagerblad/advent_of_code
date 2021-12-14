import re
from itertools import groupby

filename = "day14/input.txt"

f = open(filename)

polymer_string = f.readline().strip()

polymer_dict = {}

for i in range(len(polymer_string) - 1):
    pair = polymer_string[i : i + 2]
    if pair in polymer_dict.keys():
        polymer_dict[pair] = polymer_dict[pair] + 1
    else:
        polymer_dict[pair] = 1


f.readline()

instructions = []
for line in f:
    line_instr = list(map(str.strip, line.split(" -> ")))
    instructions.append(line_instr)


total_steps = 40
for step in range(total_steps):
    new_polymer_dict = polymer_dict.copy()

    for instr in instructions:
        old_pair = instr[0]
        first_new_pair = instr[0][0] + instr[1]
        second_new_pair = instr[1] + instr[0][1]

        if old_pair in polymer_dict.keys():
            n_old_pair = polymer_dict[old_pair]

            if first_new_pair not in new_polymer_dict.keys():
                new_polymer_dict[first_new_pair] = 0
            if second_new_pair not in new_polymer_dict.keys():
                new_polymer_dict[second_new_pair] = 0

            new_polymer_dict[first_new_pair] = (
                new_polymer_dict[first_new_pair] + n_old_pair
            )
            new_polymer_dict[second_new_pair] = (
                new_polymer_dict[second_new_pair] + n_old_pair
            )

            new_polymer_dict[old_pair] = new_polymer_dict[old_pair] - n_old_pair

    polymer_dict = new_polymer_dict.copy()

single_val_dict = {}
for key, value in polymer_dict.items():
    first_val = key[0]
    second_val = key[1]
    if first_val in single_val_dict.keys():
        single_val_dict[first_val] = single_val_dict[first_val] + value
    else:
        single_val_dict[first_val] = value
    if second_val in single_val_dict.keys():
        single_val_dict[second_val] = single_val_dict[second_val] + value
    else:
        single_val_dict[second_val] = value

for key, value in single_val_dict.items():
    if key == polymer_string[-1] or key == polymer_string[0]:
        single_val_dict[key] = int(single_val_dict[key] / 2) + 1
    else:
        single_val_dict[key] = int(single_val_dict[key] / 2)

vals = single_val_dict.values()
print(max(vals) - min(vals))
