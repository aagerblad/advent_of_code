import numpy as np

from struct import pack


filename = "day16/input.txt"

f = open(filename).readline().strip()

scale = 16  ## equals to hexadecimal

num_of_bits = (len(f)) * 4

bin_string = bin(int(f, scale))[2:].zfill(num_of_bits)


def read_packet(bin_string):
    i = 0
    if len(bin_string) == 0:
        return ([], [], i)

    # Read version
    versions = []

    version_bin = bin_string[i : i + 3]
    i += 3
    versions.append(int(version_bin, 2))

    # Read packet type
    type_bin = bin_string[i : i + 3]
    i += 3
    packet_type = int(type_bin, 2)

    if packet_type == 4:
        packet_value = ""
        last_packet_bit = "1"
        while last_packet_bit == "1":
            last_packet_bit = bin_string[i : i + 1]
            i += 1

            packet_bin = bin_string[i : i + 4]
            i += 4
            packet_value += packet_bin

        return (int(packet_value, 2), versions, i)

    else:
        length_type = bin_string[i : i + 1]
        i += 1

        packet_values = []

        if length_type == "0":
            # Read bits
            length_in_bits_bin = bin_string[i : i + 15]
            i += 15
            length_in_bits = int(length_in_bits_bin, 2)
            end = i + length_in_bits

            test_counter = 0
            while i < end and test_counter < 10:
                test_counter += 1
                output = read_packet(bin_string[i:end])
                packet_values.append(output[0])
                versions += output[1]
                i += output[2]
            i = end
        else:
            # Read packages
            length_in_packets_bin = bin_string[i : i + 11]
            i += 11
            length_in_packets = int(length_in_packets_bin, 2)

            for x in range(length_in_packets):
                output = read_packet(bin_string[i:])
                i += output[2]

                packet_values.append(output[0])
                versions += output[1]

        if packet_type == 0:
            packet_value = sum(packet_values)
        elif packet_type == 1:
            packet_value = np.prod(packet_values)
        elif packet_type == 2:
            packet_value = min(packet_values)
        elif packet_type == 3:
            packet_value = max(packet_values)
        elif packet_type == 5:
            packet_value = int(packet_values[0] > packet_values[1])
        elif packet_type == 6:
            packet_value = int(packet_values[0] < packet_values[1])
        elif packet_type == 7:
            packet_value = int(packet_values[0] == packet_values[1])

        return (packet_value, versions, i)


output = read_packet(bin_string)
print(output[0])
