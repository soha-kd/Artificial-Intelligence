package com.company;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
// todo no empty start set to start
public class Game {
    private static Game game = null;

    private final char[][] board;
    private final Player player2 = new Player();
    private final Player player1 = new Player();
    private final Set<int[]> obstaclesPositions = new HashSet<>();
    private final Set<int[]> starsPositions = new HashSet<>();
    private Player playerToMove;
    private Player opponent;

    private static class Player {
        private int y;
        private int x;
        private int score = 0;
    }

    public static Game getInstance(int numOfRows, int numOfColumns) {
        if (game == null) {
            game = new Game(numOfRows, numOfColumns);
        }
        return game;
    }

    public static Game getInstance() {
        if (game == null) {
            try {
                throw new IllegalAccessException();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return game;
    }

    private Game(int numOfRows, int numOfColumns) {
//        this.numOfRows = numOfRows;
//        this.numOfColumns = numOfColumns;
        board = new char[numOfRows][numOfColumns];
    }

    public char[][] getBoard() {
        for (char[] chars : board) Arrays.fill(chars, '-');
        for (int[] coordinates : obstaclesPositions) board[coordinates[0]][coordinates[1]] = 'O';
        for (int[] coordinates : starsPositions) board[coordinates[0]][coordinates[1]] = 'S';
        board[player1.y][player1.x] = '1';
        board[player2.y][player2.x] = '2';
        return board;
    }

    public void setP1Position(int y, int x) {
        player1.y = y;
        player1.x = x;
    }

    public void setP2Position(int y, int x) {
        player2.y = y;
        player2.x = x;
    }

    public int getPlayer1Score() {
        return player1.score;
    }

    public int getPlayer2Score() {
        return player2.score;
    }

    public void addObstacle(int y, int x) { //todo can have duplicated arrays
        obstaclesPositions.add(new int[]{y, x});
    }

    public void addStar(int y, int x) {
        for (int[] coordinates : starsPositions)
            if (Arrays.equals(coordinates, new int[]{y, x})) return;
        starsPositions.add(new int[]{y, x});
    }

    public void moveItem(int destinationY, int destinationX) throws IllegalStateException {
        if (isGameFinished()) return;
        switchPlayers();
        if (isLegalMove(playerToMove, opponent, destinationY, destinationX)) {
            playerToMove.score += getMovementScore(playerToMove, destinationY, destinationX);
            playerToMove.y = destinationY;
            playerToMove.x = destinationX;
        } else {
            switchPlayers();
            throw new IllegalStateException();
        }
    }

    private boolean isLegalMove(Player player, Player opponent, int destinationY, int destinationX) {
        if (destinationY >= board.length || destinationX >= board[0].length) return false;
        if (destinationY == player.y && destinationX == player.x) return false;
        if (destinationY == opponent.y && destinationX == opponent.x) return false;
        if (destinationY != player.y && destinationX != player.x) return false;
        if (isThereObstacleInWay(player.y, player.x, destinationY, destinationX)) return false;
        return true;
    }

    private int getMovementScore(Player player, int destinationY, int destinationX) {
        var movementScore = 0;
        if (destinationY == player.y) {
            var direction = destinationX > player.x ? 1 : -1;
            for (int x = player.x; x != destinationX + direction; x += direction)
                movementScore += getMovementPartialScore(destinationY, x);
        } else if (destinationX == player.x) {
            var direction = destinationY > player.y ? 1 : -1;
            for (int y = player.y; y != destinationY + direction; y += direction)
                movementScore += getMovementPartialScore(y, destinationX);
        }
        return movementScore;
    }

    private int getMovementPartialScore(int y, int x) {
        var movementPartialScore = 0;
        var toRemove = new HashSet<int[]>();
        for (int[] coordinates : starsPositions)
            if (Arrays.equals(coordinates, new int[]{y, x})) {
                toRemove.add(coordinates);
                movementPartialScore ++;
            }
        starsPositions.removeAll(toRemove);
        return movementPartialScore;
    }

    private void switchPlayers() {
        if (playerToMove == null || playerToMove.equals(player2)) {
            playerToMove = player1;
            opponent = player2;
        } else {
            playerToMove = player2;
            opponent = player1;
        }
    }

    private boolean isThereObstacleInWay(int startY, int startX, int destinationY, int destinationX) {
        if (destinationY == startY) {
            var direction = destinationX > startX ? 1 : -1;
            for (int x = startX; x != destinationX + direction; x += direction)
                for (int[] coordinates : obstaclesPositions)
                    if (Arrays.equals(coordinates, new int[]{destinationY, x})) return true;
        } else {
            var direction = destinationY > startY ? 1 : -1;
            for (int y = startY; y != destinationY + direction; y += direction)
                for (int[] coordinates : obstaclesPositions)
                    if (Arrays.equals(coordinates, new int[]{y, destinationX})) return true;
        }
        return false;
    }

    public int getTurn() {
        return playerToMove == null || playerToMove.equals(player1) ? 2 : 1;
    }

    public boolean isGameFinished() {
        return starsPositions.isEmpty();
    }
}
