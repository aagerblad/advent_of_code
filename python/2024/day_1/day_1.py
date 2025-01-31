f = open("input.txt")


l_list = []
r_list = []

for val in f:
    split = val.split("   ")
    l_list.append(int(split[0]))
    r_list.append(int(split[1]))

sorted_l = sorted(l_list)
sorted_r = sorted(r_list)

s = 0
for i in range(len(sorted_l)):
    s += abs(sorted_r[i] - sorted_l[i])

print(s)
