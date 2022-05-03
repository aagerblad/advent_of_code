filename = "day10/input.txt"
f = open(filename)

d = {
    ")": 0,
    "}": 0,
    "]": 0,
    ">": 0,
}
for line in f:
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
                d[char] = d[char] + 1
                break
sum = d[")"] * 3 + d["}"] * 57 + d["]"] * 1197 + d[">"] * 25137

print(sum)
