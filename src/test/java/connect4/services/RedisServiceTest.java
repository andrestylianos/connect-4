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

        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_ONE, GameState.PLAYER_ONE_WIN);

        String key = UUID.randomUUID().toString();
        redisService.upsertValue(key, boardState);

        assertEquals(redisService.getValue(key, BoardState.class), boardState);
    }

    @Test
    public void shouldAllowUpdatingAValue() throws Exception {

        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_ONE, GameState.ACTIVE);
        BoardState updatedState = new BoardState(boardSize, discs, Disc.PLAYER_ONE, GameState.PLAYER_ONE_WIN);

        String key = UUID.randomUUID().toString();
        redisService.upsertValue(key, boardState);
        redisService.upsertValue(key, updatedState);

        assertEquals(redisService.getValue(key, BoardState.class), updatedState);
    }

}