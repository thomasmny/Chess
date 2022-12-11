package com.eintosti.chess.game.board;

public final class MoveTransition {

    private final Board fromBoard;
    private final Board toBoard;
    private final Move transitionMove;
    private final Move.Outcome outcome;

    public MoveTransition(Board fromBoard, Board toBoard, Move transitionMove, Move.Outcome outcome) {
        this.fromBoard = fromBoard;
        this.toBoard = toBoard;
        this.transitionMove = transitionMove;
        this.outcome = outcome;
    }

    public Board getFromBoard() {
        return fromBoard;
    }

    public Board getToBoard() {
        return toBoard;
    }

    public Move getTransitionMove() {
        return transitionMove;
    }

    public Move.Outcome getOutcome() {
        return outcome;
    }
}