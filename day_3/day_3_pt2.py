import numpy as np
import math

filename = "day_3/input.txt"

width = len(open(filename).readline()) - 1
f = np.genfromtxt(filename,  dtype ="int", usecols=range(width), delimiter=1)
length = len(f)

new_f = f.copy()

for w in range(width):
	if len(new_f) == 1: break
	col_value = math.floor(new_f[:,w].sum()/(len(new_f)/2))
	new_f = np.delete(new_f, new_f[:,w] != col_value, axis=0)

oxygen = int("".join(str(x) for x in new_f[0]), 2)

new_f = f.copy()

for w in range(width):
	if len(new_f) == 1: break
	col_value = 1 - math.floor(new_f[:,w].sum()/(len(new_f)/2))
	new_f = np.delete(new_f, new_f[:,w] != col_value, axis=0)

co2 = int("".join(str(x) for x in new_f[0]), 2)

print("Life support: ", oxygen*co2)
