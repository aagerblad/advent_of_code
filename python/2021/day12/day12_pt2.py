import collections

filename = "day12/input.txt"
f = open(filename)

map_dict = {"start": [], "end": []}

for line in f:
    split_line = line.split("-")
    start = split_line[0]
    end = split_line[1].strip()

    if start in map_dict.keys():
        map_dict[start].append(end)
    else:
        map_dict[start] = [end]

    if end in map_dict.keys():
        map_dict[end].append(start)
    else:
        map_dict[end] = [start]

paths = [["start"]]
completed_paths = []
while paths:
    p = paths.pop(0)
    cur_pos = p[-1]

    next_pos = map_dict[cur_pos]
    occurances = collections.Counter(p)
    second_try = False

    for key in occurances:
        if key.lower() == key and key not in ["start", "end"] and occurances[key] >= 2:
            second_try = True

    for np in next_pos:
        new_p = p.copy()
        if (
            np not in p
            or np.upper() == np
            or (not second_try and np not in ["start", "end"])
        ):
            new_p.append(np)

            if np != "end":
                paths.append(new_p)
            else:
                completed_paths.append(new_p)

print(len(completed_paths))
