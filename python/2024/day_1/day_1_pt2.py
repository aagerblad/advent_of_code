f = open("python/2024/day_1/input.txt")


l_list = []
r_list = []

for val in f:
    split = val.split("   ")
    l_list.append(int(split[0]))
    r_list.append(int(split[1]))

sorted_l = sorted(l_list)
sorted_r = sorted(r_list)

s = 0

r_i = 0
for i in range(len(sorted_l)):
    o = 0

    while sorted_r[r_i] <= sorted_l[i]:
        if sorted_r[r_i] == sorted_l[i]:
            o += 1

        r_i += 1
        if r_i == len(sorted_r):
            break

    s += sorted_l[i] * o

print(s)
