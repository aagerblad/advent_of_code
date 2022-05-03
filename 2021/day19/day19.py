import numpy as np
import re
import copy


def generate_rotations(scanners):
    sc = np.asarray(scanners)

    rotations = [
        [[0, 1, 2], [1, 1, 1]],
        [[0, 2, 1], [1, -1, 1]],
        [[0, 1, 2], [1, -1, -1]],
        [[0, 2, 1], [1, 1, -1]],
        [[1, 2, 0], [1, 1, 1]],
        [[1, 0, 2], [1, -1, 1]],
        [[1, 2, 0], [1, -1, -1]],
        [[1, 0, 2], [1, 1, -1]],
        [[2, 0, 1], [1, 1, 1]],
        [[2, 1, 0], [1, -1, 1]],
        [[2, 0, 1], [1, -1, -1]],
        [[2, 1, 0], [1, 1, -1]],
        [[0, 1, 2], [-1, 1, -1]],
        [[0, 2, 1], [-1, -1, -1]],
        [[0, 1, 2], [-1, -1, 1]],
        [[0, 2, 1], [-1, 1, 1]],
        [[1, 2, 0], [-1, 1, -1]],
        [[1, 0, 2], [-1, -1, -1]],
        [[1, 2, 0], [-1, -1, 1]],
        [[1, 0, 2], [-1, 1, 1]],
        [[2, 0, 1], [-1, 1, -1]],
        [[2, 1, 0], [-1, -1, -1]],
        [[2, 0, 1], [-1, -1, 1]],
        [[2, 1, 0], [-1, 1, 1]],
    ]
    output = []
    for r in rotations:
        output.append(rotate(sc, r))

    return output, rotations


def rotate(scanner, rotation):
    s = copy.deepcopy(scanner)
    for i in range(3):
        s[:, i] = scanner[:, rotation[0][i]] * rotation[1][i]

    return s


def test_scanners(scanners, s_1, s_2_unrotated):
    s_2_rotations, rotations = generate_rotations(scanners[s_2_unrotated])

    for s_2_i, s_2 in enumerate(s_2_rotations):

        for first_b_1_i in range(0, len(scanners[s_1])):
            first_b_1 = scanners[s_1][first_b_1_i]

            for first_b_2_i in range(0, len(s_2)):
                first_b_2 = s_2[first_b_2_i]
                offset = [
                    first_b_1[0] - first_b_2[0],
                    first_b_1[1] - first_b_2[1],
                    first_b_1[2] - first_b_2[2],
                ]

                matched_beacons = 0

                for b_1_i, b_1 in enumerate(scanners[s_1]):
                    calc_b_2 = [
                        b_1[0] - offset[0],
                        b_1[1] - offset[1],
                        b_1[2] - offset[2],
                    ]

                    for b_2 in s_2:
                        if (
                            calc_b_2[0] == b_2[0]
                            and calc_b_2[1] == b_2[1]
                            and calc_b_2[2] == b_2[2]
                        ):
                            matched_beacons += 1

                    if len(scanners[s_1]) - b_1_i + matched_beacons < 12:
                        break

                if matched_beacons >= 12:
                    print("more than 12 matched beacons")
                    return [rotations[s_2_i], offset]


filename = "day19/input.txt"

f = open(filename)

scanners = []

i = -1
for line in f:
    line = line.strip()
    if re.match("\-\-\-.*", line):
        i += 1
        scanners.append([])
    elif line == "":
        continue
    else:
        scanners[i].append(list(map(int, line.split(","))))

num_of_scanners = len(scanners)

scanner_list = []
for i in range(num_of_scanners):
    scanner_list.append(np.asfarray(scanners[i], dtype=int))

scanners = scanner_list

scanner_locations = {0: [0, 0, 0]}
completed_scanners = [0]
completed_scanner_queue = completed_scanners.copy()
while completed_scanner_queue:
    s_1 = completed_scanner_queue.pop(0)

    for s_2_unrotated in range(0, num_of_scanners):
        if s_2_unrotated in completed_scanners:
            continue
        print(s_1, s_2_unrotated)

        test_result = test_scanners(scanners, s_1, s_2_unrotated)
        if test_result:
            sc = np.asarray(scanners[s_2_unrotated])
            scanners[s_2_unrotated] = rotate(sc, test_result[0])
            scanners[s_2_unrotated] = scanners[s_2_unrotated] + test_result[1]
            scanner_locations[s_2_unrotated] = test_result[1]

            completed_scanners.append(s_2_unrotated)
            completed_scanner_queue.append(s_2_unrotated)


flat_scanners = scanners[0]
for i in range(1, num_of_scanners):
    flat_scanners = np.append(flat_scanners, scanners[i], axis=0)
print(len(np.unique(flat_scanners, axis=0)))

largest_distance = 0
for i in range(num_of_scanners):
    for j in range(num_of_scanners):
        if i == j:
            continue
        distance = (
            abs(scanner_locations[i][0] - scanner_locations[j][0])
            + abs(scanner_locations[i][1] - scanner_locations[j][1])
            + abs(scanner_locations[i][2] - scanner_locations[j][2])
        )
        if distance > largest_distance:
            largest_distance = distance
print(largest_distance)
