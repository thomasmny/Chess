package com.eintosti.chess.ai;

import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Board;

public interface BoardEvaluator {

    int evaluate(Game game, Board board, int depth);
}