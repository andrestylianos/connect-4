package connect4.services;

import connect4.conf.ConnectFourConfiguration;
import connect4.conf.RedisConfig;
import connect4.enums.Disc;
import connect4.enums.GameState;
import connect4.models.BoardSize;
import connect4.models.BoardState;
import connect4.models.PlayerMove;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.rmi.UnexpectedException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ConnectFourConfiguration.class, RedisConfig.class})
public class RedisServiceTest {

    private static BoardSize boardSize = BoardSize.SIZE_DEFAULT;

    @Autowired
    private RedisService redisService;

    @Test
    public void shouldAllowSettingAndGettingAValue() throws Exception {

        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for (Disc[] discRow : discs) {
            Arrays.fill(discRow, Disc.EMPTY);
        }

        BoardState boardState = new BoardState(UUID.randomUUID(), boardSize, discs, Disc.PLAYER_ONE, GameState.PLAYER_ONE_WIN);

        redisService.upsertValue(boardState.getId().toString(), boardState);

        assertEquals(redisService.getValue(boardState.getId().toString(), BoardState.class), Optional.of(boardState));
    }

    @Test
    public void shouldAllowUpdatingAValue() throws Exception {

        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for (Disc[] discRow : discs) {
            Arrays.fill(discRow, Disc.EMPTY);
        }

        BoardState boardState = new BoardState(UUID.randomUUID(), boardSize, discs, Disc.PLAYER_ONE, GameState.ACTIVE);
        BoardState updatedState = new BoardState(boardState.getId(), boardSize, discs, Disc.PLAYER_ONE, GameState.PLAYER_ONE_WIN);

        redisService.upsertValue(boardState.getId().toString(), boardState);
        redisService.upsertValue(boardState.getId().toString(), updatedState);

        assertEquals(redisService.getValue(boardState.getId().toString(), BoardState.class), Optional.of(updatedState));
    }

    @Test
    public void shouldAllowRemovingAValue() throws Exception {

        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for (Disc[] discRow : discs) {
            Arrays.fill(discRow, Disc.EMPTY);
        }

        BoardState boardState = new BoardState(UUID.randomUUID(), boardSize, discs, Disc.PLAYER_ONE, GameState.ACTIVE);

        redisService.upsertValue(boardState.getId().toString(), boardState);
        redisService.removeValue(boardState.getId().toString());

        assertEquals(redisService.getValue(boardState.getId().toString(), BoardState.class), Optional.empty());
    }

}