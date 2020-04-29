import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    Board parent = null;
    int fScore = Integer.MAX_VALUE;
    int gScore = 0;
    int[][] move;
    static int redBlockKey;
    HashMap<Integer, Block> blocks = new HashMap<>();

    Board() {
    }

    Board(Board toCopy) {
        for (Block block : toCopy.blocks.values()) {
            blocks.put(block.id, block.clone());
        }
    }

    boolean isSquareOccupied(int x, int y) {
        for (Block block : blocks.values()) {
            if (block.covers(x, y)) {
                return true;
            }
        }
        return false;
    }

    ArrayList<Board> getNeighbours() {
        ArrayList<Board> neighbours = new ArrayList<>();
        for (Block b : blocks.values()) {
            neighbours.addAll(b.getNeighbours(this));
        }
        return neighbours;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board))
            return false;
        else {
            Board other = (Board) obj;
            for (int i = 0; i < blocks.size(); i++) {
                if (!blocks.get(i).equals(other.blocks.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append("|");
            for (int j = 0; j < 6; j++) {
                if (isSquareOccupied(j, i)) {
                    for (Block b : blocks.values()) {
                        if (b.covers(j, i)) {
                            sb.append(b.id + "|");
                            break;
                        }
                    }
                } else {
                    sb.append(" |");
                }
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
