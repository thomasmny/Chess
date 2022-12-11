package com.eintosti.chess.game.board;

import com.eintosti.chess.game.participant.Participant;
import com.eintosti.chess.game.piece.Color;
import com.eintosti.chess.game.piece.Piece;
import com.eintosti.chess.game.piece.pieces.Bishop;
import com.eintosti.chess.game.piece.pieces.King;
import com.eintosti.chess.game.piece.pieces.Knight;
import com.eintosti.chess.game.piece.pieces.Pawn;
import com.eintosti.chess.game.piece.pieces.Queen;
import com.eintosti.chess.game.piece.pieces.Rook;

public class Board {

    public static final int MAX_TILES = 8;

    protected static final int WHITE_MAIN = 0;
    protected static final int WHITE_PAWNS = 1;
    protected static final int BLACK_MAIN = 7;
    protected static final int BLACK_PAWNS = 6;

    protected final Tile[][] tiles;

    public Board(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Board() {
        this.tiles = new Tile[MAX_TILES][MAX_TILES];

        // White
        addMainWhitePiece(Tile.Name.A, new Rook(Color.WHITE));
        addMainWhitePiece(Tile.Name.B, new Knight(Color.WHITE));
        addMainWhitePiece(Tile.Name.C, new Bishop(Color.WHITE));
        addMainWhitePiece(Tile.Name.D, new Queen(Color.WHITE));
        addMainWhitePiece(Tile.Name.E, new King(Color.WHITE));
        addMainWhitePiece(Tile.Name.F, new Bishop(Color.WHITE));
        addMainWhitePiece(Tile.Name.G, new Knight(Color.WHITE));
        addMainWhitePiece(Tile.Name.H, new Rook(Color.WHITE));

        for (int x = 0; x < MAX_TILES; x++) {
            tiles[x][WHITE_PAWNS] = new Tile(x, WHITE_PAWNS, new Pawn(Color.WHITE));
        }

        // Empty
        for (int x = 0; x < MAX_TILES; x++) {
            for (int z = 2; z < BLACK_PAWNS; z++) {
                tiles[x][z] = new Tile(x, z);
            }
        }

        // Black
        addMainBlackPiece(Tile.Name.A, new Rook(Color.BLACK));
        addMainBlackPiece(Tile.Name.B, new Knight(Color.BLACK));
        addMainBlackPiece(Tile.Name.C, new Bishop(Color.BLACK));
        addMainBlackPiece(Tile.Name.D, new Queen(Color.BLACK));
        addMainBlackPiece(Tile.Name.E, new King(Color.BLACK));
        addMainBlackPiece(Tile.Name.F, new Bishop(Color.BLACK));
        addMainBlackPiece(Tile.Name.G, new Knight(Color.BLACK));
        addMainBlackPiece(Tile.Name.H, new Rook(Color.BLACK));

        for (int x = 0; x < MAX_TILES; x++) {
            tiles[x][BLACK_PAWNS] = new Tile(x, BLACK_PAWNS, new Pawn(Color.BLACK));
        }
    }

    private void addPiece(Tile.Name letter, int num, Piece piece) {
        tiles[letter.getNum()][num] = new Tile(letter.getNum(), num, piece);
    }

    private void addMainWhitePiece(Tile.Name letter, Piece piece) {
        addPiece(letter, WHITE_MAIN, piece);
    }

    private void addMainBlackPiece(Tile.Name letter, Piece piece) {
        addPiece(letter, BLACK_MAIN, piece);
    }

    /**
     * Gets the tile from local coordinates.
     *
     * @param x The local x coordinate (from 0-7)
     * @param z The local z coordinate (from 0-7)
     * @return The tile located at the specified coordinates
     */
    public Tile getTile(int x, int z) {
        return tiles[x][z];
    }

    /**
     * Gets the tile which is located at a given format (e.g. C6).
     *
     * @param name The letter of the row
     * @param z    The local z coordinate (from 1-8)
     * @return The tile located at the specified coordinates
     */
    public Tile getTile(Tile.Name name, int z) {
        return tiles[name.getNum()][z - 1];
    }

    /**
     * Checks if a move is valid.
     *
     * @param previousTile The tile the piece is currently standing on
     * @param tileToMoveTo The tile the piece wants to move to
     * @param participant  The participant making the move
     * @return {@code true} if the move is valid, otherwise {@code false}
     */
    public boolean canMove(Tile previousTile, Tile tileToMoveTo, Participant participant) {
        if (previousTile == null) {
            return false;
        }

        Piece selectedPiece = previousTile.getPiece();
        if (!selectedPiece.isValidMove(this, previousTile.getX(), previousTile.getZ(), tileToMoveTo.getX(), tileToMoveTo.getZ())) {
            return false;
        }

        Piece pieceOnTileToMoveTo = tileToMoveTo.getPiece();
        if (pieceOnTileToMoveTo == null) {
            return true;
        }

        return participant != null && pieceOnTileToMoveTo.getColor() != participant.getColor();
    }

    /**
     * Make a {@link Piece} move on the board.
     *
     * @param move The move the piece should make
     * @return The outcome of the move
     */
    public Move.Outcome move(Move move) {
        final Tile from = move.getFrom();
        final Tile to = move.getTo();

        int startX = from.getX(), startZ = from.getZ();
        int endX = to.getX(), endZ = to.getZ();

        tiles[endX][endZ] = new Tile(endX, endZ, from.getPiece());
        tiles[startX][startZ] = new Tile(startX, startZ);

        if (move.isCastling()) {
            //TODO: Implement correctly
            if (endX == 6 && endZ == Tile.Name.A.getNum()) {
                tiles[Tile.Name.F.getNum()][0] = tiles[Tile.Name.H.getNum()][0];
                tiles[Tile.Name.H.getNum()][0] = new Tile(Tile.Name.H.getNum(), 0);
            }
            if (endX == 2 && endZ == Tile.Name.A.getNum()) {
                tiles[Tile.Name.D.getNum()][0] = tiles[Tile.Name.A.getNum()][0];
                tiles[Tile.Name.A.getNum()][0] = new Tile(Tile.Name.A.getNum(), 0);
            }
            if (endX == 6 && endZ == Tile.Name.H.getNum()) {
                tiles[Tile.Name.F.getNum()][7] = tiles[Tile.Name.H.getNum()][7];
                tiles[Tile.Name.H.getNum()][7] = new Tile(Tile.Name.H.getNum(), 7);
            }
            if (endX == 2 && endZ == Tile.Name.H.getNum()) {
                tiles[Tile.Name.D.getNum()][7] = tiles[Tile.Name.A.getNum()][7];
                tiles[Tile.Name.A.getNum()][7] = new Tile(Tile.Name.A.getNum(), 7);
            }
            return Move.Outcome.CASTLE;
        }

        if (from.getPiece() instanceof Pawn) {
            Color color = from.getPiece().getColor();
            if ((color == Color.WHITE && endZ == BLACK_MAIN) || (color == Color.BLACK && endZ == WHITE_MAIN)) {
                tiles[endX][endZ] = new Tile(endX, endZ, new Queen(color));
                return Move.Outcome.PROMOTION;
            }
        }

        return to.isOccupied() ? Move.Outcome.TAKE : Move.Outcome.MOVE;
    }

    public MoveTransition transitionMove(Move move, Participant participant) {
        Board copy = copyBoard();
        if (!canMove(move.getFrom(), move.getTo(), participant)) {
            return new MoveTransition(copy, copy, move, Move.Outcome.ILLEGAL_MOVE);
        }

        Board afterMove = copy.copyBoard();
        Move.Outcome outcome = afterMove.move(move);
        return new MoveTransition(copy, afterMove, move, outcome);
    }

    public Board copyBoard() {
        Tile[][] copy = new Tile[MAX_TILES][MAX_TILES];
        for (int x = 0; x < MAX_TILES; x++) {
            System.arraycopy(tiles[x], 0, copy[x], 0, MAX_TILES);
        }
        return new Board(copy);
    }
}