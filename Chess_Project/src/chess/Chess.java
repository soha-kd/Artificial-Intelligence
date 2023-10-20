package chess;
import chess.player.*;
import chess.pieces.*;

public class Chess {
	
	public static void main(String[] args){
		Board b = new Board();
		System.out.println(b);
		int iteration = 100;
		float player1Score =0, player2Score = 0;

		for(int i = 0; i < iteration; i++) {
			Board board = new Board();
			Player player1 = new AlphaBetaPlayer(Piece.WHITE,4);
			Player player2 = new AlphaBetaPlayer(Piece.BLACK,4);

			int game = play(player1, player2, board);
			if(game == 1)
				player1Score++;
			if(game == -1)
				player2Score++;
			if(game == 0) {
				player1Score += 0.5f;
				player2Score += 0.5f;
			}
		}
		System.out.println(player1Score);
		System.out.println(player2Score);
		if (player1Score > player2Score)
			System.out.println("Winner is player 1");
		else if(player1Score < player2Score)
			System.out.println("Winner is player 2");
		else
			System.out.println("Draw");
	}

//	Returns 1 if player1 wins
//	Returns 0 if draw
//	Returns -1 if player2 wins
	public static int play(Player player1, Player player2, Board b) {
		Move move;
		while(true) {
			move = player1.getNextMove(b);
			if(move == null && b.isCheck(player1.getColor()))
				return -1;
			if(move == null)
				return 0;
			b.makeMove(move);
			System.out.println(b);
			
			move = player2.getNextMove(b);
			if(move == null && b.isCheck(player2.getColor()))
				return 1;
			if(move == null)
				return 0;
			b.makeMove(move);
			System.out.println(b);
		} 
	}
}