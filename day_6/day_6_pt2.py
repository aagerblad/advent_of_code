import re
import numpy as np

filename = "day_6/input.txt"
initial_fishes = np.loadtxt(filename, dtype="int", delimiter=",", max_rows=1)

fishes = np.zeros(9)
end_day = 256 + 1
new_fish_life = 8
fish_refresh_life = 6

for fish in initial_fishes:
    fishes[fish] = fishes[fish] + 1

for day in range(1, end_day):
    new_fishes = fishes[0]
    for n in range(8):
        fishes[n] = fishes[n + 1]
    fishes[6] = fishes[6] + new_fishes
    fishes[8] = new_fishes

print(int(sum(fishes)))
