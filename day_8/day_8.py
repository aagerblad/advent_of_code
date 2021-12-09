import re

filename = "day_8/input.txt"

f = open(filename)

digits = 0
for line in f:
    val = list(filter(None, re.split("\W+", line)))[-4:]

    for v in val:
        if len(v) in (2, 3, 4, 7):
            digits = digits + 1

print(digits)
