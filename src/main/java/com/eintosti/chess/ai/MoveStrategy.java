package com.eintosti.chess.ai;

import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;

public interface MoveStrategy {

    Move execute(Board board);
}