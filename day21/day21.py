def get_die_roll(die_roll, rolls):
    return (die_roll % 100) + 1, rolls + 1


die_roll = 0
rolls = 0
player = [7, 8]
player_score = [0, 0]

cur_player = 0

while max(player_score) < 1000:
    player_move = 0
    for x in range(3):
        die_roll, rolls = get_die_roll(die_roll, rolls)
        player_move += die_roll

    player[cur_player] = (player[cur_player] + player_move - 1) % 10 + 1
    player_score[cur_player] += player[cur_player]

    cur_player = (cur_player + 1) % 2

print(min(player_score[0] * rolls, player_score[1] * rolls))
