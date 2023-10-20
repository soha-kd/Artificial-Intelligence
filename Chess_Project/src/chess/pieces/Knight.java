package chess.pieces;
import java.util.ArrayList;
import chess.Board;
import chess.Move;

public class Knight extends Piece {

	public Knight(boolean color) {
		super(color);
		value = 3;
	}

	public String toString() {
		if(color == Piece.WHITE)
			return "N";
		else
			return "n";
	}
	
	public ArrayList<Move> getMoves(Board b, int x, int y) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		// North North East
		if(valid(x+1, y+2) && 
				(!b.getTile(x+1, y+2).isOccupied() || 
						(b.getTile(x+1, y+2).isOccupied() && b.getTile(x+1, y+2).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x+1, y+2));
		
		// East North East
		if(valid(x+2, y+1) && 
				(!b.getTile(x+2, y+1).isOccupied() || 
						(b.getTile(x+2, y+1).isOccupied() && b.getTile(x+2, y+1).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x+2, y+1));
		
		// East South East
		if(valid(x+2,y-1) && 
				(!b.getTile(x+2,y-1).isOccupied() || 
						(b.getTile(x+2,y-1).isOccupied() && b.getTile(x+2,y-1).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x+2,y-1));

		// South South East
		if(valid(x+1,y-2) && 
				(!b.getTile(x+1,y-2).isOccupied() || 
						(b.getTile(x+1,y-2).isOccupied() && b.getTile(x+1,y-2).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x+1,y-2));
		
		// South South West
		if(valid(x-1,y-2) && 
				(!b.getTile(x-1,y-2).isOccupied() || 
						(b.getTile(x-1,y-2).isOccupied() && b.getTile(x-1,y-2).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x-1,y-2));
		
		// West South West
		if(valid(x-2,y-1) && 
				(!b.getTile(x-2,y-1).isOccupied() || 
						(b.getTile(x-2,y-1).isOccupied() && b.getTile(x-2,y-1).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x-2,y-1));
		
		// West North West
		if(valid(x-2,y+1) && 
				(!b.getTile(x-2,y+1).isOccupied() || 
						(b.getTile(x-2,y+1).isOccupied() && b.getTile(x-2,y+1).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x-2,y+1));
		
		// North North West
		if(valid(x-1,y+2) && 
				(!b.getTile(x-1,y+2).isOccupied() || 
						(b.getTile(x-1,y+2).isOccupied() && b.getTile(x-1,y+2).getPiece().getColor() != color)))
			moves.add(new Move(x,y,x-1,y+2));
		
		return moves;
	}















	public Knight clone() {
		return new Knight(color);
	}
}