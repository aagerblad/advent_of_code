filename = "day10/input.txt"
f = open(filename)

line_sums = []
for line in f:
    corrupted = False
    line = line.strip()
    cur_open = []
    for char in line:
        if char in ("(", "{", "[", "<"):
            cur_open.append(char)
        else:
            a = cur_open.pop()
            if not (
                (a == "(" and char == ")")
                or (a == "{" and char == "}")
                or (a == "[" and char == "]")
                or (a == "<" and char == ">")
            ):
                corrupted = True
                break
    if corrupted:
        continue
    line_sum = 0
    while cur_open:
        a = cur_open.pop()
        if a == "(":
            line_sum = (line_sum * 5) + 1
        if a == "{":
            line_sum = (line_sum * 5) + 3
        if a == "[":
            line_sum = (line_sum * 5) + 2
        if a == "<":
            line_sum = (line_sum * 5) + 4
    line_sums.append(line_sum)
print(sorted(line_sums)[int(len(line_sums) / 2)])
