from itertools import count
import numpy as np
import math

filename = "day_4/input.txt"

f = np.genfromtxt(filename, dtype="int", skip_header=2, usecols=range(5), delimiter=3)
num_of_boards = int(len(f) / 5)
boards = np.split(f, num_of_boards)
result_boards = np.split(f < 0, num_of_boards)

numbers = np.loadtxt(filename, dtype="int", delimiter=",", max_rows=1)


def test_win(result_boards, completed_boards):
    last_completed = -1
    for b_i, b in enumerate(result_boards):
        if completed_boards[b_i] == 1:
            continue

        for r in range(5):
            if np.count_nonzero(b[r, :]) == 5 or np.count_nonzero(b[:, r]) == 5:
                completed_boards[b_i] = 1
                last_completed = b_i

    return (completed_boards, last_completed)


def get_score(result_board, board):
    remaining_sum = 0
    for row_i in range(5):
        for col_i in range(5):
            if not result_board[row_i, col_i]:
                remaining_sum = remaining_sum + board[row_i, col_i]
    return remaining_sum


completed_boards = np.zeros(num_of_boards)

for n in numbers:
    for b_i, b in enumerate(boards):
        for row_i, row in enumerate(b):
            for col_i, col in enumerate(row):
                if n == col:
                    result_boards[b_i][row_i][col_i] = True

    (completed_boards, last_completed) = test_win(result_boards, completed_boards)
    if sum(completed_boards) == num_of_boards:
        score = get_score(result_boards[last_completed], boards[last_completed])
        print(n, " done - score: ", n * score, " board:", last_completed)
        break
