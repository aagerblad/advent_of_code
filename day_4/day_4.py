import numpy as np

filename = "day_4/input.txt"

f = np.genfromtxt(filename, dtype="int", skip_header=2, usecols=range(5), delimiter=3)
num_of_boards = len(f) / 5
boards = np.split(f, num_of_boards)
result_boards = np.split(f < 0, num_of_boards)

numbers = np.loadtxt(filename, dtype="int", delimiter=",", max_rows=1)


def test_win(result_boards, boards):
    for b_i, b in enumerate(result_boards):
        for r in range(5):
            if np.count_nonzero(b[r, :]) == 5:
                print(b_i, r, " row")
                return (True, get_score(b, boards[b_i]))

        for r in range(5):
            if np.count_nonzero(b[:, r]) == 5:
                print(b_i, r, " col")
                return (True, get_score(b, boards[b_i]))

        # if(np.count_nonzero([b[0,0], b[1,1], b[2,2], b[3,3], b[4,4]]) == 5):
        # 	print(b_i, " diag_1")
        # 	return (True, get_score(b, boards[b_i]))

        # if(np.count_nonzero([b[0,4], b[1,3], b[2,2], b[3,1], b[4,0]]) == 5):
        # 	print(b_i, " diag_0")
        # 	return (True, get_score(b, boards[b_i]))

    return (False, 0)


def get_score(result_board, board):
    remaining_sum = 0
    for row_i in range(5):
        for col_i in range(5):
            if not result_board[row_i, col_i]:
                remaining_sum = remaining_sum + board[row_i, col_i]
    return remaining_sum


for n in numbers:
    for b_i, b in enumerate(boards):
        for row_i, row in enumerate(b):
            for col_i, col in enumerate(row):
                if n == col:
                    result_boards[b_i][row_i][col_i] = True

    (done, score) = test_win(result_boards, boards)
    if done:
        print(n, " done - score: ", n * score)
        break
