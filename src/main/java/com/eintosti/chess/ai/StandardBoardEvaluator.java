package com.eintosti.chess.ai;

import com.eintosti.chess.game.Game;
import com.eintosti.chess.game.board.Board;
import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.game.piece.PieceType;

public final class StandardBoardEvaluator implements BoardEvaluator {

    private final static int CHECK_MATE_BONUS = 10000;
    private final static int CHECK_BONUS = 45;
    private final static int DEPTH_BONUS = 100;
    private final static int MOBILITY_MULTIPLIER = 5;
    private final static int TWO_BISHOPS_BONUS = 25;

    @Override
    public int evaluate(Game game, Board board, int depth) {
        return score(board, game.getWhite(), depth) - score(board, game.getBlack(), depth);
    }

    private int score(Board board, Participant participant, int depth) {
        return mobility(board, participant)
                + pieceValue(board, participant)
                + kingThreats(participant, depth);
    }

    /**
     * Evaluates score based on how many moves can be made.
     *
     * @param board       The board
     * @param participant The participant
     * @return The evaluated score
     */
    private int mobility(Board board, Participant participant) {
        return (int) (MOBILITY_MULTIPLIER * ((participant.getLegalMoves(board).size() * 10.0f) / participant.getOpponent().getLegalMoves(board).size()));
    }

    /**
     * Evaluates score based on how valuable a participants {@link Piece}s are.
     * <p>
     * Having two {@link PieceType#BISHOP}s gives an extra bonus.
     *
     * @param board       The board
     * @param participant The participant
     * @return The evaluated score
     */
    private int pieceValue(Board board, Participant participant) {
        int pieceValuationScore = 0;
        int numBishops = 0;
        for (Piece piece : participant.getActivePieces(board)) {
            pieceValuationScore += piece.getValue();
            if (piece.getType() == PieceType.BISHOP) {
                numBishops++;
            }
        }
        return pieceValuationScore + (numBishops == 2 ? TWO_BISHOPS_BONUS : 0);
    }

    /**
     * Evaluates score based on threats against the opponents king.
     * <p>
     * Gives a bonus for a higher depth.
     *
     * @param participant The participant
     * @param depth       The current depth
     * @return The evaluated score
     */
    private int kingThreats(Participant participant, int depth) {
        return participant.getOpponent().isCheckMate() ?
                CHECK_MATE_BONUS * (depth == 0 ? 1 : DEPTH_BONUS * depth) :
                participant.getOpponent().isCheck() ? CHECK_BONUS : 0;
    }
}