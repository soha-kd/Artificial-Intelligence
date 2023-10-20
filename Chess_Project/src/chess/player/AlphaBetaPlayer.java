package chess.player;
import chess.Move;
import chess.Board;
import chess.minimax.*;

public class AlphaBetaPlayer extends Player {

	MinimaxAlphaBeta minimax;
	public AlphaBetaPlayer(boolean color, int maxDepth) {
		super(color);
		minimax = new MinimaxAlphaBeta(color, maxDepth);
	}

	public Move getNextMove(Board b) {
		return minimax.decision(b);
	}
}