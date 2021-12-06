import re
import numpy as np

filename = "day_6/input.txt"
fishes = np.loadtxt(filename, dtype="int", delimiter=",", max_rows=1)

end_day = 80 + 1
new_fish_life = 8
fish_refresh_life = 6

for day in range(1, end_day):
    for fish_i, fish in enumerate(fishes):
        if fishes[fish_i] == 0:
            fishes[fish_i] = fish_refresh_life    
            fishes = np.append(fishes, new_fish_life)
        else: 
            fishes[fish_i] = fishes[fish_i] - 1

print(len(fishes))

