import java.util.ArrayList;

public class VerticalBlock extends Block {

    VerticalBlock(int id, int x, int y) {
        super(id, x, y);
    }

    public ArrayList<Board> getNeighbours(Board board) {
        ArrayList<Board> neighbours = new ArrayList<>();
        int y = this.y + 1;
        while (y + length - 1 < 6 && !board.isSquareOccupied(x, y + length - 1)) {
            neighbours.add(createNeighbour(board, x, y));
            y++;
        }
        y = this.y - 1;
        while (y >= 0 && !board.isSquareOccupied(x, y)) {
            neighbours.add(createNeighbour(board, x, y));
            y--;
        }
        return neighbours;
    }

    public boolean covers(int x, int y) {
        if (x == this.x && y >= this.y && y <= this.y + length - 1) {
            return true;
        }
        return false;
    }

    @Override
    public Block clone() {
        VerticalBlock clone = new VerticalBlock(id, x, y);
        clone.length = this.length;
        return clone;
    }
}
