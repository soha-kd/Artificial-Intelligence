package chess;
import chess.pieces.*;
import java.util.ArrayList;

public class Board {
	public static final int a=0, b=1, c=2, d=3, e=4, f=5, g=6, h=7;
	 Tile[][] tiles;
	/*
	 * 	 8	r n b q k b n r 
	 *	 7	p p p p p p p p 
	 *	 6	. . . . . . . . 
	 *	 5	. . . . . . . . 
	 *	 4	. . . . . . . . 
	 *	 3	. . . . . . . . 
	 *	 2	P P P P P P P P 
	 *	 1  R N B Q K B N R
	 *    	
	 *    	a b c d e f g h
	 *    
	 * P=Pawn, K=King, Q=Queen, R=Rook, N=Knight, B=Bishop
	 */
	
	public Board(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public Board() {
		boolean co = Piece.WHITE;
		tiles = new Tile[8][8];
		tiles[a][0] = new Tile(new Rook(co));
		tiles[b][0] = new Tile(new Knight(co));
		tiles[c][0] = new Tile(new Bishop(co));
		tiles[d][0] = new Tile(new Queen(co));
		tiles[e][0] = new Tile(new King(co));
		tiles[f][0] = new Tile(new Bishop(co));
		tiles[g][0] = new Tile(new Knight(co));
		tiles[h][0] = new Tile(new Rook(co));
		
		for(int i = 0; i < 8; i++) {
			tiles[i][1] = new Tile(new Pawn(co));
		}
		
		for(int i = 2; i < 7; i++) {
			for(int j = 0; j < 8; j++) {
				tiles[j][i] = new Tile();
			}
		}
		
		co = Piece.BLACK;
		tiles[a][7] = new Tile(new Rook(co));
		tiles[b][7] = new Tile(new Knight(co));
		tiles[c][7] = new Tile(new Bishop(co));
		tiles[d][7] = new Tile(new Queen(co));
		tiles[e][7] = new Tile(new King(co));
		tiles[f][7] = new Tile(new Bishop(co));
		tiles[g][7] = new Tile(new Knight(co));
		tiles[h][7] = new Tile(new Rook(co));
		
		for(int i = 0; i < 8; i++) {
			tiles[i][6] = new Tile(new Pawn(co));
		}
	}
//////////////////////////////////////////////////////////////////////////////////////////
	public String toString() {
		String str = "";
		for(int i = 7; i >= 0; i--) {
			str += (i+1) + "  ";
			for(int j = 0; j < 8; j++) {
				str += tiles[j][i] + " ";
			}
			str += "\n";
		}
		str += "\n   a b c d e f g h";
		return str;
	}
///////////////////////////////////////////////////////////////////////////////////////////////
	public boolean isCheck(boolean color) {
		int x = -1, y = -1;
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				if(tiles[i][j].isOccupied() && tiles[i][j].getPiece().getColor() == color &&
						tiles[i][j].getPiece().toString().equalsIgnoreCase("K")) {
					x = i; y = j;
				}
			}
		// check a move if after making this move the king can be killed (moving into check)
		ArrayList<Move> opponentMoves = getMoves(!color, false);

		// check all opponent moves if they kill king (opponent moves in next round)
		for (Move opponentMove : opponentMoves) {
			if (opponentMove.getX2() == x && opponentMove.getY2() == y)
				return true;
		}
		return false;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	//Checks if player color is under check
	public boolean isCheckAfter(boolean color, ArrayList<Move> moves) {

		Tile[][] newTiles = getTilesAfter(moves);
		int x = -1, y = -1;
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				if(newTiles[i][j].isOccupied() &&
						newTiles[i][j].getPiece().getColor() == color &&
						newTiles[i][j].getPiece().toString().equalsIgnoreCase("K")) {
					x = i; y = j;
				}
			}

		// check a move if after making this move the king can be killed (moving into check)
		ArrayList<Move> opponentMoves = getMovesAfter(!color, moves, false);

		// check all opponent moves if they kill king (opponent moves in next round)
		for (Move opponentMove : opponentMoves) {
			if (opponentMove.getX2() == x && opponentMove.getY2() == y)
				return true;
		}
		return false;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Move> getMoves(boolean color) {
		return getMoves(color, true);
	}
////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Move> getMoves(boolean color, boolean checkCheck) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				if(tiles[i][j].isOccupied() && tiles[i][j].getPiece().getColor() == color) {
					moves.addAll(tiles[i][j].getPiece().getMoves(this, i, j));
				}
			}
		
		// check if move is valid (must not be check after move) and throw away erroneous moves
		if(checkCheck) {
			// find king
			int x = -1, y = -1;
			for(int i = 0; i < 8; i++)
				for(int j = 0; j < 8; j++) {
					if(tiles[i][j].isOccupied() &&tiles[i][j].getPiece().getColor() == color &&
							tiles[i][j].getPiece().toString().equalsIgnoreCase("K")) {
						x = i; y = j;
					}
				}
			ArrayList<Move> removeThese = new ArrayList<Move>();
			for(int i = 0; i < moves.size(); i++) {
				// check a move if after making this move the king can be killed (moving into check)
				ArrayList<Move> checkThis = new ArrayList<Move>(moves.subList(i, i+1));
				ArrayList<Move> opponentMoves = getMovesAfter(!color, checkThis, false);
				
				int xUpdated = x, yUpdated = y;
				if(checkThis.get(0).getX1() == x && checkThis.get(0).getY1() == y) {
					xUpdated = checkThis.get(0).getX2();
					yUpdated = checkThis.get(0).getY2();
				}
				
				// check all opponent moves if they kill king (opponent moves in next round)
				for(int j = 0; j < opponentMoves.size(); j++) {
					if(opponentMoves.get(j).getX2() == xUpdated && opponentMoves.get(j).getY2() == yUpdated)
						removeThese.add(checkThis.get(0));
				}
			}
			moves.removeAll(removeThese); // remove invalid moves
		}
		return moves;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Move> getMovesAfter(boolean color, ArrayList<Move> moves) {
		return getMovesAfter(color, moves, true);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Move> getMovesAfter(boolean color, ArrayList<Move> moves, boolean checkCheck) {

		Tile[][] temp = new Tile[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				temp[x][y] = new Tile(this.tiles[x][y]);
			}
		}
		Board b = new Board(temp);

		for (Move move : moves) b.makeMove(move);

		return b.getMoves(color, checkCheck);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	public Tile[][] getTilesAfter(ArrayList<Move> moves) {

		Tile[][] temp = new Tile[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				temp[x][y] = new Tile(this.tiles[x][y]);
			}
		}
		Board b = new Board(temp);
		for (Move move : moves) b.makeMove(move);

		Tile[][] temp2 = new Tile[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				temp2[x][y] = new Tile(b.getTile(x, y));
			}
		}
		return temp2;
	}
/////////////////////////////////////////////////////////////////////////////////////////////
	public void makeMove(Move m) {
		Tile oldTile = tiles[m.getX1()][m.getY1()];
		tiles[m.getX2()][m.getY2()] = tiles[m.getX1()][m.getY1()];
		tiles[m.getX1()][m.getY1()] = new Tile();
		
		if(m.isCastling()) {
			if(m.getX2() == g && m.getY2() == 0) {
				tiles[f][0] = tiles[h][0];
				tiles[h][0] = new Tile();
			}
			if(m.getX2() == c && m.getY2() == 0) {
				tiles[d][0] = tiles[a][0];
				tiles[a][0] = new Tile();
			}
			if(m.getX2() == g && m.getY2() == 7) {
				tiles[f][7] = tiles[h][7];
				tiles[h][7] = new Tile();
			}
			if(m.getX2() == c && m.getY2() == 7) {
				tiles[d][7] = tiles[a][7];
				tiles[a][7] = new Tile();
			}
		}

		if(oldTile.getPiece().toString().equals("P") && m.getY2() == 7)
			tiles[m.getX2()][m.getY2()] = new Tile(new Queen(Piece.WHITE));
		
		if(oldTile.getPiece().toString().equals("p") && m.getY2() == 0)
			tiles[m.getX2()][m.getY2()] = new Tile(new Queen(Piece.BLACK));
	}
























	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
}