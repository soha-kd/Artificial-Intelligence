package chess;
import chess.pieces.Piece;

public class Tile {
	 boolean occupied;
	 Piece piece;

	public Tile() {
		occupied = false;
	}
	
	public Tile(Tile tile) {
		this.occupied = tile.isOccupied();
		this.piece = tile.isOccupied() ? tile.getPiece().clone() : null;
	}
	
	public Tile(Piece piece) {
		occupied = true;
		this.piece = piece;
	}
	
	public String toString() {
		if(occupied)
			return piece.toString();
		else
			return ".";
	}

	public Piece getPiece() {
		return piece;
	}
	public boolean isOccupied() {
		return occupied;
	}
}