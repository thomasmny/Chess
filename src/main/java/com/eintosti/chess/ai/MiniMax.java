package com.eintosti.chess.ai;

import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.board.Move;
import com.eintosti.chess.game.board.MoveTransition;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Color;
import org.bukkit.Bukkit;

public class MiniMax implements MoveStrategy {

    private final Game game;
    private final BoardEvaluator evaluator;
    private final int searchDepth;

    public MiniMax(Game game, int searchDepth) {
        this.game = game;
        this.evaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public Move execute(Board board) {
        long startTime = System.currentTimeMillis();

        Move bestMove = null;
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        Bukkit.broadcastMessage("§7§oThinking with depth=" + searchDepth);

        Participant currentTurn = game.getCurrentTurn();
        boolean isWhite = currentTurn.getColor() == Color.WHITE;

        for (Move move : currentTurn.getLegalMoves(board)) {
            MoveTransition moveTransition = board.transitionMove(move, currentTurn);
            if (moveTransition.getOutcome().isDone()) {
                currentValue = isWhite ?
                        min(moveTransition.getToBoard(), currentTurn, searchDepth - 1) :
                        max(moveTransition.getToBoard(), currentTurn, searchDepth - 1);

                if (isWhite && currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                } else if (!isWhite && currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            }
        }

        long executionTime = System.currentTimeMillis() - startTime;
        Bukkit.broadcastMessage("§7Found move after §e" + executionTime + "ms§7: §b" + bestMove.toString());

        return bestMove;
    }

    public int min(Board board, Participant participant, int depth) {
        if (depth == 0 || isEndGameScenario(participant)) {
            return this.evaluator.evaluate(game, board, depth);
        }

        int lowestSeenValue = Integer.MAX_VALUE;
        for (Move move : participant.getLegalMoves(board)) {
            MoveTransition transition = board.transitionMove(move, participant);
            if (transition.getOutcome().isDone()) {
                int currentValue = max(transition.getToBoard(), participant, depth - 1);
                if (currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;
    }

    public int max(Board board, Participant participant, int depth) {
        if (depth == 0 || isEndGameScenario(participant)) {
            return this.evaluator.evaluate(game, board, depth);
        }

        int highestSeenValue = Integer.MIN_VALUE;
        for (Move move : participant.getLegalMoves(board)) {
            MoveTransition transition = board.transitionMove(move, participant);
            if (transition.getOutcome().isDone()) {
                int currentValue = min(transition.getToBoard(), participant, depth - 1);
                if (currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }

    private boolean isEndGameScenario(Participant participant) {
        return participant.isCheckMate() || participant.isStaleMate();
    }

    @Override
    public String toString() {
        return "MiniMax";
    }
}