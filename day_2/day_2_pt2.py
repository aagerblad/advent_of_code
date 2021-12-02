from turtle import forward


f = open("day_2/input.txt")

horizontal_pos = 0
depth = 0
aim = 0

for line in f:
    val = line.split()
    direction = val[0]
    distance = int(val[1])

    if direction == "forward":
        horizontal_pos = horizontal_pos + distance
        depth = depth + (distance * aim)
    elif direction == "down":
        aim = aim + distance
    elif direction == "up":
        aim = aim - distance
    else:
        print("Error")
        break

print("Output: ", horizontal_pos * depth)
