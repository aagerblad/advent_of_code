import re
from itertools import groupby

filename = "day14/input.txt"

f = open(filename)

polymer_string = f.readline().strip()
f.readline()

instructions = []
for line in f:
    line_instr = list(map(str.strip, line.split(" -> ")))
    instructions.append(line_instr)


total_steps = 10
for step in range(total_steps):

    inserts = []
    for instr in instructions:
        re_str = "(?=" + instr[0] + ")"

        insert_indexes = [m.start() for m in re.finditer(re_str, polymer_string)]

        for index in insert_indexes:
            inserts.append([index, instr[1]])

    inserts.sort(key=lambda x: x[0])
    inserted = 0
    for insert in inserts:
        index = insert[0] + inserted + 1
        polymer_string = polymer_string[:index] + insert[1] + polymer_string[index:]
        inserted = inserted + 1

foo = sorted(polymer_string)
bar = [(char, len(list(group))) for char, group in groupby(foo)]

bar.sort(key=lambda x: x[1])
print(bar[-1][1] - bar[0][1])
