# import numpy as np

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
    # if int(version_bin, 2) == 7:

    # Read packet type
    type_bin = bin_string[i : i + 3]
    i += 3

    packet_value = []
    if int(type_bin, 2) == 4:
        while True:
            last_packet_bit = bin_string[i : i + 1]
            i += 1

            packet_bin = bin_string[i : i + 4]
            i += 4
            packet_value += packet_bin

            if last_packet_bit == "0":
                return (packet_value, versions, i)

    else:
        length_type = bin_string[i : i + 1]
        i += 1

        if length_type == "0":
            # Read bits
            length_in_bits_bin = bin_string[i : i + 15]
            i += 15
            length_in_bits = int(length_in_bits_bin, 2)
            end = i + length_in_bits

            packet_values = []
            while i < end:
                output = read_packet(bin_string[i:end])
                packet_values += output[0]
                versions += output[1]
                i += output[2]

            return (packet_values, versions, i)
        else:
            # Read packages
            length_in_packets_bin = bin_string[i : i + 11]
            i += 11
            length_in_packets = int(length_in_packets_bin, 2)

            packet_values = []
            for x in range(length_in_packets):
                output = read_packet(bin_string[i:])
                i += output[2]

                packet_values += output[0]
                versions += output[1]

            return (packet_values, versions, i)


output = read_packet(bin_string)
print(sum(output[1]))
