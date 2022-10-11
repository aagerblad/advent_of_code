f = open("day_2/input.txt")

horizontal_pos = 0
depth = 0

for line in f:
    val = line.split()

    if val[0] == "forward":
        horizontal_pos = horizontal_pos + int(val[1])
    elif val[0] == "down":
        depth = depth + int(val[1])
    elif val[0] == "up":
        depth = depth - int(val[1])
    else:
        print("Error")
        break

print("Output: ", horizontal_pos * depth)
