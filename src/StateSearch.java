import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class StateSearch {

    public static ArrayDeque<int[][]> AStar(Board current) {
        PriorityQueue<Board> frontier = new PriorityQueue<>(Comparator.comparingInt(o -> o.fScore));
        ArrayList<Board> visited = new ArrayList<>();

        System.out.println(current);

        current.gScore = 0;
        current.fScore = current.gScore + getHScore(current);
        frontier.add(current);
        while (!frontier.isEmpty()) {
            current = frontier.remove();

            if (isGoalState(current)) {
                return finish(current);
            }
            visited.add(current);
            for (Board neighbour : current.getNeighbours()) {
                if (!visited.contains(neighbour)) {
                    if (!frontier.contains(neighbour)) {
                        neighbour.parent = current;
                        neighbour.gScore = current.gScore + 1;
                        neighbour.fScore = neighbour.gScore + getHScore(neighbour);
                        frontier.add(neighbour);
                    } else {
                        for (Board original : frontier) {
                            if (original.equals(neighbour)) {
                                if (original.gScore < neighbour.gScore) {
                                    System.out.println(original.gScore);
                                    System.out.println(neighbour.gScore);
                                    frontier.remove(original);
                                    neighbour.parent = current;
                                    neighbour.gScore = current.gScore + 1;
                                    neighbour.fScore = neighbour.gScore + getHScore(neighbour);
                                    frontier.add(neighbour);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static boolean isGoalState(Board current) {
        return current.blocks.get(Board.redBlockKey).x == 4;
    }

    public static int getHScore(Board board) {
        if (board.blocks.get(Board.redBlockKey).x == 4) {
            return 0;
        }
        int count = 0;
        for (int i = board.blocks.get(Board.redBlockKey).x + 2; i < 6; i++) {
            if (board.isSquareOccupied(i, 2)) {
                count++;
            }
        }
        return count + 1;
    }

    private static ArrayDeque<int[][]> finish(Board current) {
        ArrayDeque<int[][]> moves = new ArrayDeque<>();

        moves.addFirst(current.move);
        while (current.parent.move != null) {
            moves.addFirst(current.parent.move);
            current = current.parent;
        }

        return moves;
    }
}
