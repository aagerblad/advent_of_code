import numpy as np

filename = "day_7/input.txt"
crabs = np.loadtxt(filename, dtype="int", delimiter=",", max_rows=1)

max_value = np.max(crabs)

lowest_fuel = max_value * len(crabs)
last_fuel = lowest_fuel
for pos in range(max_value):
    fuel = np.sum(np.abs(crabs - pos))
    if fuel > last_fuel:
        break
    if fuel < lowest_fuel:
        lowest_fuel = fuel
    last_fuel = fuel
    print(pos, fuel, lowest_fuel, last_fuel)


print(lowest_fuel)
