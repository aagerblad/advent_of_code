def run_game(player_pos, player_score, cur_player, depth, history):

    global memo
    mem = (
        ",".join(map(str, player_pos))
        + ",".join(map(str, player_score))
        + str(cur_player)
    )

    if mem in memo.keys():
        return memo[mem]

    dice_rolls = gen_outcomes()
    wins = [0, 0]
    for d in dice_rolls:
        new_player_pos = player_pos.copy()
        new_player_score = player_score.copy()

        new_player_pos[cur_player] = (player_pos[cur_player] + sum(d[0:3]) - 1) % 10 + 1
        new_player_score[cur_player] += new_player_pos[cur_player]

        if new_player_score[cur_player] >= 21:
            wins[cur_player] += 1
        else:
            outcome = run_game(
                new_player_pos,
                new_player_score,
                (cur_player + 1) % 2,
                depth + 1,
                history + [d],
            )

            wins[0] += outcome[0]
            wins[1] += outcome[1]

    memo[mem] = wins
    return wins


def gen_outcomes():
    output = []
    for a in range(1, 4):
        for b in range(1, 4):
            for c in range(1, 4):
                output.append([a, b, c])
    return output


memo = {}

player = [7, 8]
player_score = [0, 0]
cur_player = 0

wins = run_game(player, player_score, cur_player, 0, [])

print(wins)
print(max(wins))
