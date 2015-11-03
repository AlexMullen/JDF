package mullen.alex.jdf.common;

import java.util.HashSet;
import java.util.Set;

import mullen.alex.jdf.common.Piece.MoveDirection;

public class RetrogradeLearning {
    public static void main(final String... args) {
        final Set<Board> boardConfigs = new HashSet<>();
        final Board board = new Board(8, 8);
        final BoardPattern pattern =
                new CheckeredBoardPattern(BoardPattern.WHITE_SQUARE, 8, 8);
        final Piece darkPiece =
                new Piece(Piece.DARK, MoveDirection.BOTH);
        boardConfigs.add(board);
        generateWonPositions(board, pattern, boardConfigs, darkPiece, 0, 2);
        System.out.println("Position count: " + boardConfigs.size());
//        for (final Board b : boardConfigs) {
//            System.out.println(b);
//        }
    }

    private static void generateWonPositions(final Board board,
            final BoardPattern pattern, final Set<Board> boardConfigs, 
            final Piece piece, final int position, final int pieceCount) {
        if (pieceCount == 0) {
            return;
        }
        for (int i = position; i < board.pieces.length; i++) {
            final int x = board.positions[i].x;
            final int y = board.positions[i].y;
            if (pattern.getColourAt(x, y) == BoardPattern.BLACK_SQUARE) {
                board.setPieceAt(x, y, piece);
                if (!boardConfigs.add(new Board(board))) {
                    System.out.println("Collision!");
                }
                generateWonPositions(board, pattern, boardConfigs, piece, i + 1, pieceCount - 1);
                board.setPieceAt(x, y, null);
            }
        }
    }
}
