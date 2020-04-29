import java.util.ArrayList;

public class HorizontalBlock extends Block {

    HorizontalBlock(int id, int x, int y) {
        super(id, x, y);
    }

    public ArrayList<Board> getNeighbours(Board board) {
        ArrayList<Board> neighbours = new ArrayList<>();
        int x = this.x + 1;
        while (x + length - 1 < 6 && !board.isSquareOccupied(x + length - 1, y)) {
            neighbours.add(createNeighbour(board, x, y));
            x++;
        }
        x = this.x - 1;
        while (x >= 0 && !board.isSquareOccupied(x, y)) {
            neighbours.add(createNeighbour(board, x, y));
            x--;
        }
        return neighbours;
    }

    public boolean covers(int x, int y) {
        if (y == this.y && x >= this.x && x <= this.x + length - 1) {
            return true;
        }
        return false;
    }

    @Override
    public Block clone() {
        HorizontalBlock clone = new HorizontalBlock(id, x, y);
        clone.length = this.length;
        return clone;
    }
}
