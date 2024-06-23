package spw4.connectfour;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConnectFourImplTest {
    @Test
    void testCreateGame() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        assertArrayEquals(new Player[][] {
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
        }, sut.getBoard());
    }

    @Test
    void getPlayerAtWithEmptyBoard() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        Player p = sut.getPlayerAt(0, 0);
        assertEquals(Player.none, p);
    }

    @Test
    void yellowStartsFirstThrowsInvalidStartingPlayerException() {
        // expect that starting with yellow is not allowed per game rules.
        assertThrows(InvalidStartingPlayer.class, () -> new ConnectFourImpl(Player.yellow));
    }

    @Test
    void noneStartsFirstThrowsInvalidStartingPlayerException() {
        assertThrows(InvalidStartingPlayer.class, () -> new ConnectFourImpl(Player.none));
    }

    @Test
    void getPlayerAtOutOfBoundsThrowsException() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);

        // Perform the action
        assertThrows(IllegalArgumentException.class, () -> sut.getPlayerAt(100, 100));
    }

    @Test
    void getPlayerOnTurnAtStartIsRed() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        assertEquals(Player.red, sut.getPlayerOnTurn());
    }

    @Test
    void getPlayerOnTurnAlternatesAfterDrop() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(0);
        assertEquals(Player.yellow, sut.getPlayerOnTurn());
    }

    @Test
    void getPlayerAtRowOutsideOfBounds() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        assertThrows(IllegalArgumentException.class, () -> sut.getPlayerAt(-1, 1));
    }

    @Test
    void getPlayerAtColOutsideOfBounds() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        assertThrows(IllegalArgumentException.class, () -> sut.getPlayerAt(1, -1));
    }

    @Test
    void getPlayerAtColOutsideOfBoundsMax() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        assertThrows(IllegalArgumentException.class, () -> sut.getPlayerAt(1, 100));
    }

    @Test
    void toStringOnEmptyBoardReturnsCleanBoard() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        assertEquals("Player: RED\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n", sut.toString());
    }

    @Test
    void toStringOnBoard() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(0);
        sut.drop(2);
        sut.drop(3);
        sut.drop(2);
        assertEquals("Player: RED\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  Y  .  .  .  . |\n" +
                "| R  .  Y  R  .  .  . |\n", sut.toString());
    }

    @Test
    void droppingPiecesInEachColumn() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(0);
        sut.drop(1);
        sut.drop(2);
        sut.drop(3);
        sut.drop(4);
        sut.drop(5);
        sut.drop(6);

        assertArrayEquals(new Player[][] {
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.red, Player.yellow, Player.red, Player.yellow, Player.red, Player.yellow, Player.red},
        }, sut.getBoard());
    }

    @Test
    void dropOnTopOfPiece() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(1);
        sut.drop(1);

        assertArrayEquals(new Player[][] {
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.yellow, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.red, Player.none, Player.none, Player.none, Player.none, Player.none},
        }, sut.getBoard());
    }

    @Test
    void dropLastPieceOnColumn() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(0);
        sut.drop(0);
        sut.drop(0);
        sut.drop(0);
        sut.drop(0);
        sut.drop(0);

        assertArrayEquals(new Player[][] {
                {Player.yellow, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.red, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.yellow, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.red, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.yellow, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.red, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
        }, sut.getBoard());
    }

    @Test
    void dropOnFullColumn() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(0);
        sut.drop(0);
        sut.drop(0);
        sut.drop(0);
        sut.drop(0);
        sut.drop(0);
        sut.drop(0);

        assertArrayEquals(new Player[][] {
                {Player.yellow, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.red, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.yellow, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.red, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.yellow, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.red, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
        }, sut.getBoard());
    }

    @Test
    void verticalWin() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(1);
        sut.drop(2);
        sut.drop(1);
        sut.drop(2);
        sut.drop(1);
        sut.drop(2);
        sut.drop(1);
        assertTrue(sut.isGameOver());
    }

    @Test
    void verticalStackMissing2() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(1);
        sut.drop(2);
        sut.drop(1);
        sut.drop(2);
        sut.drop(3);
        sut.drop(2);
        sut.drop(3);
        assertFalse(sut.isGameOver());
    }

    @Test
    void verticalStackMissing1() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(1);
        sut.drop(2);
        sut.drop(1);
        sut.drop(2);
        sut.drop(1);
        sut.drop(2);
        sut.drop(3);
        assertFalse(sut.isGameOver());
    }

    @Test
    void horizontalWin() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(1);
        sut.drop(1);
        sut.drop(2);
        sut.drop(2);
        sut.drop(3);
        sut.drop(3);
        sut.drop(4);
        assertTrue(sut.isGameOver());
    }

    @Test
    void diagonalLeftWin() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(5); //r
        sut.drop(4); //y
        sut.drop(4); //r
        sut.drop(3); //y
        sut.drop(5); //r
        sut.drop(3); //y
        sut.drop(3); //r
        sut.drop(2); //y
        sut.drop(6); //r
        sut.drop(2); //y
        sut.drop(5); //r
        sut.drop(2); //y
        sut.drop(2); //r
        System.out.println("left diag: " + sut.toString());

        assertTrue(sut.isGameOver());
    }

    @Test
    void diagonalRightWin() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(1);
        sut.drop(2);
        sut.drop(2);
        sut.drop(4);
        sut.drop(4);
        sut.drop(4);
        sut.drop(4);
        sut.drop(3);
        sut.drop(3);
        sut.drop(5);
        sut.drop(3);
        System.out.println(sut.toString());
        assertTrue(sut.isGameOver());
    }

    @Test
    void intersectNotWin() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(1);
        sut.drop(2);
        sut.drop(2);
        sut.drop(3);
        sut.drop(4);
        sut.drop(4);
        assertFalse(sut.isGameOver());
    }

    @Test
    void acceptanceTest() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(3);
        sut.drop(2);
        sut.drop(4);
        sut.drop(3);
        sut.drop(5);
        sut.drop(6);
        sut.drop(4);
        sut.drop(5);
        sut.drop(3);
        sut.drop(4);
        sut.drop(2);
        sut.drop(3);
        assertTrue(sut.isGameOver());
    }

    @Test
    void dropTillFull() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 7; c++) {
                sut.drop(c);
            }
        }
        sut.drop(1);
        sut.drop(2);
        sut.drop(3);
        sut.drop(4);
        sut.drop(5);
        sut.drop(6);
        sut.drop(0);

        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 7; c++) {
                sut.drop(c);
            }
        }
        sut.drop(0);
        sut.drop(1);
        sut.drop(2);
        sut.drop(4);
        sut.drop(3);
        sut.drop(5);
        sut.drop(6);

        assertTrue(sut.isGameOver());
    }

    @Test
    void resetDuringGameWithYellowThrowsInvalidStartingPlayerException() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(0);
        sut.drop(1);
        sut.drop(2);
        sut.drop(3);
        assertThrows(InvalidStartingPlayer.class, () -> sut.reset(Player.yellow));
    }

    @Test
    void resetDuringGameWithRed() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        sut.drop(0);
        sut.drop(1);
        sut.drop(2);
        sut.drop(3);
        sut.reset(Player.red);
        assertArrayEquals(new Player[][] {
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
                {Player.none, Player.none, Player.none, Player.none, Player.none, Player.none, Player.none},
        }, sut.getBoard());
    }

    @Test
    void invalidColumnDrop() {
        ConnectFourImpl sut = new ConnectFourImpl(Player.red);
        assertDoesNotThrow(() -> sut.drop(100));
    }
}
