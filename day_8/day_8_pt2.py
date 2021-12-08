import re
from itertools import groupby

filename = "day_8/input.txt"

f = open(filename)

#  ----    0000    0000    ----    0000    0000    0000    0000    0000    0000
# -    2  -    2  -    2  1    2  1    -  1    -  -    2  1    2  1    2  1    2
# -    2  -    2  -    2  1    2  1    -  1    -  -    2  1    2  1    2  1    2
#  ----    3333    3333    3333    3333    3333    ----    3333    3333    ----
# -    5  4    -  -    5  -    5  -    5  4    5  -    5  4    5  -    5  4    5
# -    5  4    -  -    5  -    5  -    5  4    5  -    5  4    5  -    5  4    5
#  ----    6666    6666    ----    6666    6666    ----    6666    6666    6666
# 2 .     5 .     5 .     4 .     5 .     6 .     3 .     7 .     6       6

# 2 3 4 5 5 5 6 6 6 7
# 1 7 4 - - - - - - 8
#       2 3 5 - - -
# .           6 9 0

# 0 8 --
# 1 6 --
# 2 8 --
# 3 7
# 4 4 --
# 5 9 --
# 6 7

def map_number(mapping_table, output_digit):
    length = len(output_digit)
    if length == 2: return 1
    elif length == 3: return 7
    elif length == 4: return 4
    elif length == 7: return 8
    elif length == 5:
        if mapping_table[1] in output_digit: return 5
        elif mapping_table[4] in output_digit: return 2
        else: return 3
    else:
        if mapping_table[4] not in output_digit: return 9
        elif mapping_table[3] not in output_digit: return 0
        else: return 6

sum = 0
for line in f:
    mapping_table = [""] * 7
    val = list(filter(None, re.split("\W+", line)))

    signal = sorted(val[:10], key=len)
    output = val[-4:]
    mapping_table[0] = (set(signal[1]) - set(signal[0])).pop()

    foo = sorted("".join(signal))
    bar = [(char, len(list(group))) for char, group in groupby(foo)]

    for (letter, occurance) in bar:
        if occurance == 4:
            mapping_table[4] = letter
        elif occurance == 6:
            mapping_table[1] = letter
        elif occurance == 9:
            mapping_table[5] = letter
        elif occurance == 8 and letter != mapping_table[0]:
            mapping_table[2] = letter

    mapping_table[3] = (
        set(signal[2]) - set([mapping_table[1], mapping_table[2], mapping_table[5]])
    ).pop()

    for (letter, occurance) in bar:
        if occurance == 7 and letter != mapping_table[3]:
            mapping_table[6] = letter
    
    output_string = ""
    for digit in output:
        output_string = output_string + str(map_number(mapping_table, digit))
    
    sum = sum + int(output_string)

print(sum)