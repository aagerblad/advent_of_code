import numpy as np
import math

filename = "day_3/input.txt"

width = len(open(filename).readline()) - 1
f = np.genfromtxt(filename, usecols=range(width), delimiter=1)
length = len(f)

sum_f = f.sum(axis=0)

binary_gamma = np.array([math.floor(xi / (length / 2)) for xi in sum_f])
binary_epsilon = np.array([1 - xi for xi in binary_gamma])

gamma = int("".join(str(x) for x in binary_gamma), 2)
epsilon = int("".join(str(x) for x in binary_epsilon), 2)

print("Power consumption: ", gamma * epsilon)
