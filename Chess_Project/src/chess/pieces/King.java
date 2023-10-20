package chess.pieces;
import java.util.ArrayList;
import chess.Board;
import chess.Move;

public class King extends Piece {
	boolean hasMoved = false;

	public King(boolean color) {
		super(color);
		value = 0;
	}
	
	public King(boolean color, boolean hasMoved) {
		super(color);
		this.hasMoved = hasMoved;
		value = 0;
	}

	
	public String toString() {
		if(color == Piece.WHITE)
			return "K";
		else
			return "k";
	}

	public ArrayList<Move> getMoves(Board b, int x, int y) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		// North
		if(valid(x, y+1) && (!b.getTile(x, y+1).isOccupied() || (b.getTile(x, y+1).isOccupied()
				&& b.getTile(x, y+1).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x,y+1));
		
		// NorthEast
		if(valid(x+1, y+1) && 
				(!b.getTile(x+1, y+1).isOccupied() || 
						(b.getTile(x+1, y+1).isOccupied() && b.getTile(x+1, y+1).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x+1,y+1));
		
		// East
		if(valid(x+1,y) && 
				(!b.getTile(x+1,y).isOccupied() || 
						(b.getTile(x+1,y).isOccupied() && b.getTile(x+1,y).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x+1,y));
		
		
		// SouthEast
		if(valid(x+1,y-1) && 
				(!b.getTile(x+1,y-1).isOccupied() || 
						(b.getTile(x+1,y-1).isOccupied() && b.getTile(x+1,y-1).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x+1,y-1));
		
		// South
		if(valid(x,y-1) && 
				(!b.getTile(x,y-1).isOccupied() || 
						(b.getTile(x,y-1).isOccupied() && b.getTile(x,y-1).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x,y-1));
		
		// SouthWest
		if(valid(x-1,y-1) && 
				(!b.getTile(x-1,y-1).isOccupied() || 
						(b.getTile(x-1,y-1).isOccupied() && b.getTile(x-1,y-1).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x-1,y-1));
		
		// West
		if(valid(x-1,y) && 
				(!b.getTile(x-1,y).isOccupied() || 
						(b.getTile(x-1,y).isOccupied() && b.getTile(x-1,y).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x-1,y));
		
		// NorthWest
		if(valid(x-1,y+1) && 
				(!b.getTile(x-1,y+1).isOccupied() || 
						(b.getTile(x-1,y+1).isOccupied() && b.getTile(x-1,y+1).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x-1,y+1));

		// Castling
		if(color == Piece.WHITE) {
			if(!hasMoved && x == Board.e && y == 0) {
				if(!b.getTile(Board.f, 0).isOccupied() && !b.getTile(Board.g, 0).isOccupied() &&
				b.getTile(Board.h, 0).isOccupied() && b.getTile(Board.h, 0).getPiece().toString().equals("R"))
					moves.add(new Move(x,y,x+2,y));
			}
			else 
				hasMoved = true;
		}
		return moves;
	}














	public King clone() {
		return new King(color, hasMoved);
	}
}