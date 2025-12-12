package com.yuchess.games.business.engine;

import com.yuchess.games.business.enums.PieceColor;
import com.yuchess.games.business.enums.PieceType;

import lombok.Getter;

public class Board {

    @Getter
    private Piece[][] board;

    public Board() {
	board = new Piece[8][8];
    }

    private void init() {
	// Pawns
	for (int i = 0; i < 8; i++) {
	    board[1][i] = new Piece(PieceType.PAWN, PieceColor.BLACK);
	    board[6][i] = new Piece(PieceType.PAWN, PieceColor.WHITE);
	}

	// Rooks
	board[0][0] = board[0][7] = new Piece(PieceType.ROOK, PieceColor.BLACK);
	board[7][0] = board[7][7] = new Piece(PieceType.ROOK, PieceColor.WHITE);

	// Knights
	board[0][1] = board[0][6] = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
	board[7][1] = board[7][6] = new Piece(PieceType.KNIGHT, PieceColor.WHITE);

	// Bishops
	board[0][2] = board[0][5] = new Piece(PieceType.BISHOP, PieceColor.BLACK);
	board[7][2] = board[7][5] = new Piece(PieceType.BISHOP, PieceColor.WHITE);

	// Queens
	board[0][3] = new Piece(PieceType.QUEEN, PieceColor.BLACK);
	board[7][3] = new Piece(PieceType.QUEEN, PieceColor.WHITE);

	// Kings
	board[0][4] = new Piece(PieceType.KING, PieceColor.BLACK);
	board[7][4] = new Piece(PieceType.KING, PieceColor.WHITE);
    }

    public Piece getPieceAt(int row, int col) {
	return board[row][col];
    }

    public void setPieceAt(int row, int col, Piece piece) {
	board[row][col] = piece;
    }
}
