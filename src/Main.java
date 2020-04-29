import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayDeque;

public class Main {

    private static Robot robot;
    private static final int[] columns = {470,550,630,710,790,870};
    private static final int[] columnGutters = {509,590,669,749,828};
    private static final int[] rows = {215,291,358,449,525,615};
    private static final int[] rowGutters = {250,330,409,488,570};
    //colours
    private static final int BACKGROUND_RED = 51;
    private static final int LIGHT_BLOCK_RED = 200;
    private static final int RED_BLOCK_GREEN = 100;

    public static void main(String[] args) {
        try {robot = new Robot();} catch (AWTException ignore) {}
        robot.setAutoDelay(50);
        robot.setAutoWaitForIdle(true);

        //wait 'till game is full screen
        while(robot.getPixelColor(5,5).getRed() != BACKGROUND_RED) {
            //poll
        }

        //for i number of levels
        for (int i = 0; i < 8; i++) {
            Board board = new Board();
            discoverBlocks(board, columns, rowGutters, true);
            discoverBlocks(board, rows, columnGutters, false);

            makeMoves(StateSearch.AStar(board));
            clickStartNextLevel();
        }
    }

    private static void discoverBlocks(Board board, int[] listA, int[] listB, boolean verticalSearch) {
        Block block = null;
        for (int i = 0; i < listA.length; i++) { //for each column/row
            for (int j = 0; j < listB.length; j++) { //for each rowGutter/columnGutter
                int pixelX = verticalSearch ? listA[i] : listB[j];
                int pixelY = verticalSearch ? listB[j] : listA[i];
                //if pixel at x,y is the colour of a wooden block
                if (robot.getPixelColor(pixelX, pixelY).getRed() > LIGHT_BLOCK_RED) {
                    if (block == null) {
                        if (verticalSearch)
                            block = new VerticalBlock(board.blocks.size(), i, j);
                        else
                            block = new HorizontalBlock(board.blocks.size(), j, i);
                        board.blocks.put(block.id, block);
                        //if the color
                        if (robot.getPixelColor(pixelX, pixelY).getGreen() < RED_BLOCK_GREEN) {
                            board.redBlockKey = block.id;
                        }
                    } else {
                        block.length++;
                    }
                } else {
                    block = null;
                }
            }
            block = null;
        }
    }

    private static void makeMoves(ArrayDeque<int[][]> moves) {
        while (!moves.isEmpty()) {
            int[][] move = moves.removeFirst();
            robot.mouseMove(columns[move[0][0]], rows[move[0][1]]);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseMove(columns[move[1][0]], rows[move[1][1]]);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
    }

    public static void clickStartNextLevel() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) {
        }
        robot.mouseMove(826, 559);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) {
        }
    }
}
