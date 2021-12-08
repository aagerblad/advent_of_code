from audioop import avg
import re
import numpy as np

filename = "day_7/input.txt"
crabs = np.loadtxt(filename, dtype="int", delimiter=",", max_rows=1)

max_value = np.max(crabs)

lowest_fuel = max_value * len(crabs) * max_value
last_fuel = lowest_fuel
for pos in range(max_value):
    movement = np.abs(crabs - pos)

    fuel = int(np.sum(((movement * movement) + movement) / 2))
    if fuel > last_fuel:
        break
    if fuel < lowest_fuel:
        lowest_fuel = fuel
    last_fuel = fuel
    print(pos, fuel, lowest_fuel, last_fuel)


print(lowest_fuel)
