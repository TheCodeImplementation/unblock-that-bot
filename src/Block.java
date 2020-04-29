import java.util.ArrayList;

public abstract class Block {

    int id;
    int x, y;
    int length = 2;

    Block(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Board createNeighbour(Board board, int x, int y) {
        Board neighbour = new Board(board);
        neighbour.move = new int[][]{{this.x, this.y}, {x, y}};
        for (Block block : neighbour.blocks.values()) {
            if (block.id == id) {
                block.x = x;
                block.y = y;
            }
        }
        return neighbour;
    }

    public abstract ArrayList<Board> getNeighbours(Board board);

    public abstract boolean covers(int x, int y);

    @Override
    public abstract Block clone();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Block) {
            Block other = (Block) obj;
            if (getClass() == other.getClass() && id == other.id && x == other.x && y == other.y && length == other.length) {
                return true;
            }
        }
        return false;
    }
}
