package chess.minimax;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import chess.Board;
import chess.Move;
import chess.Tile;
import chess.pieces.Piece;

public class MinimaxAlphaBeta {

	boolean color;
	int maxDepth;
	Random rand;

	public MinimaxAlphaBeta(boolean color, int maxDepth) {
		this.color = color;
		this.maxDepth = maxDepth;
		rand = new Random();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////
	private float maxValue(Board b, ArrayList<Move> state, float alpha, float beta, int depth) {
		if (depth > maxDepth) {
			return eval1(b, state, color);
		}

		ArrayList<Move> moves = b.getMovesAfter(color, state);
		if (moves.size() == 0)
			return Float.NEGATIVE_INFINITY;

		for (int i = 0; i < moves.size(); i++) {
			state.add(moves.get(i));
			float tmp = minValue(b, state, alpha, beta, depth + 1);
			state.remove(state.lastIndexOf(moves.get(i)));
			if (tmp > alpha)
				alpha = tmp;
			if (beta <= alpha)
				break;
		}
		return alpha;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private float minValue(Board b, ArrayList<Move> state, float alpha, float beta, int depth) {
		if (depth > maxDepth)
			return eval1(b, state, !color);

		ArrayList<Move> moves = b.getMovesAfter(!color, state);
		if (moves.size() == 0)
			return Float.POSITIVE_INFINITY;

		for (int i = 0; i < moves.size(); i++) {
			state.add(moves.get(i));
			float tmp = maxValue(b, state, alpha, beta, depth + 1);
			state.remove(state.lastIndexOf(moves.get(i)));
			if (tmp < beta)
				beta = tmp;
			if (beta <= alpha)
				break;
		}
		return beta;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////
	private float eval1(Board b, ArrayList<Move> moves, boolean currentColor) {
		Tile[][] tiles = b.getTilesAfter(moves);

		if (b.getMoves(currentColor).size() == 0) {
			if (b.isCheckAfter(currentColor, moves))
				return (currentColor == this.color) ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
			else
				return Float.NEGATIVE_INFINITY;
		}
		int whiteScore = 0;
		int blackScore = 0;

		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				if (tiles[i][j].isOccupied())
					if (tiles[i][j].getPiece().getColor() == Piece.WHITE)
						whiteScore += tiles[i][j].getPiece().getValue();
					else
						blackScore += tiles[i][j].getPiece().getValue();
			}
		if (color == Piece.WHITE)
			return whiteScore - blackScore;
		else
			return blackScore - whiteScore;
	}










////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Move decision(final Board b) {

		final ArrayList<Move> moves = b.getMoves(color);
		if (moves.size() == 0)
			return null;

		Vector<Future<Float>> costs = new Vector<Future<Float>>(moves.size());
		costs.setSize(moves.size());
		ExecutorService exec = Executors.newFixedThreadPool(moves.size());

		try {
			for (int i = 0; i < moves.size(); i++) {
				final Move move = moves.get(i);
				Future<Float> result = exec.submit(new Callable<Float>() {
					@Override
					public Float call() {
						ArrayList<Move> state = new ArrayList<Move>();
						state.add(move);
						return minValue(b, state, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, 1);
					}
				});
				costs.set(i, result);
			}
		} finally {
			exec.shutdown();
		}

		int maxi = -1;
		float max = Float.NEGATIVE_INFINITY;
		for (int i = 0; i < moves.size(); i++) {
			float cost;
			try {
				cost = costs.get(i).get();
			} catch (Exception e) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
				}
				continue;
			}
			if (cost >= max) {
				if (Math.abs(cost - max) < 0.1)
					if (rand.nextBoolean())
						continue;
				max = cost;
				maxi = i;
			}
		}
		return moves.get(maxi);
	}
}