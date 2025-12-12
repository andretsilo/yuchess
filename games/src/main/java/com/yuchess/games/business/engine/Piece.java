package com.yuchess.games.business.engine;

import com.yuchess.games.business.enums.PieceColor;
import com.yuchess.games.business.enums.PieceType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Piece {
    private PieceType type;
    private PieceColor color;
}
