package connect4.ai;

import connect4.enums.Disc;
import connect4.models.BoardState;
import connect4.models.PlayerMove;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomPlayer implements Player {

    @Override
    public PlayerMove nextMove(BoardState boardState) {
        List<Integer> possibleColumns = IntStream
                .range(0,boardState.getBoardSize().getHorizontalSize())
                .filter(i -> Arrays
                        .stream(boardState.getDiscs()[i])
                        .anyMatch(disc -> disc == Disc.EMPTY))
                .boxed().collect(Collectors.toList());
        Random random = new Random();
        int chosenColumn = random.nextInt(possibleColumns.size());
        Disc disc = boardState.getLastPlayer() == Disc.PLAYER_ONE ? Disc.PLAYER_TWO : Disc.PLAYER_ONE;
        return new PlayerMove(disc, chosenColumn);
    }
}
