/** 
 * @author Eric, Hansae, Dimitri, Jed 
 * @version 060216
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.application.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.embed.swing.JFXPanel;

public class Omok extends Application
{
    //constants for tile, column, and row size that can be changed to alter the board's dimensions
    protected static final int TILE_SIZE = 40;
    protected static final int COLUMNS = 15;
    protected static final int ROWS = 15;

    private boolean blackMove = true;
    private boolean gameOver = false;
    private Piece[][] grid = new Piece[ROWS][COLUMNS];

    private Tile[][] board = new Tile[ROWS][COLUMNS];

    private Pane pieceRoot = new Pane();

    //stores groups of tiles and pieces
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    /**
     * method that creates the rows, columns, tiles, and colors
     */
    private Parent createContent() 
    {
        //creates the tiles
        Pane root = new Pane();
        root.getChildren().add(pieceRoot);
        root.setPrefSize(COLUMNS * TILE_SIZE, ROWS * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);
        root.getChildren().addAll(makeColumns());

        //adds the tiles to the group
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);
            }
        }
        
        return root;
    }

    /**
     * places the pieces and highlights the tile that is being hovered over
     */
    private List<Rectangle> makeColumns()
    {
        List<Rectangle> list = new ArrayList<>();
        for(int x = 0; x < ROWS;x++)
        {
            for(int y = 0; y< COLUMNS; y++)
            {
                //highlights the tile that the user is hovering over
                Rectangle rect = new Rectangle(TILE_SIZE,TILE_SIZE);
                rect.setTranslateX(x*(TILE_SIZE ) );
                rect.setTranslateY(y*(TILE_SIZE));
                rect.setFill(Color.TRANSPARENT);
                rect.setOnMouseEntered(e-> rect.setFill(Color.rgb(200,200,50,0.3)));
                rect.setOnMouseExited(e->rect.setFill(Color.TRANSPARENT));

                final int column = x;
                final int row = y;

                //when the user clicks, puts an appropriate piece down
                rect.setOnMouseClicked(e-> {
                        Piece piece = null;

                        //checks which color piece to put down when the mouse is clicked
                        if(blackMove)                                   
                            piece = placePiece(PieceType.BLACK,column-1, row-1);                
                        else
                            piece = placePiece(PieceType.WHITE,column-1, row-1);

                        //checks if either player has won once they play a piece    
                        if(piece!=null)
                        {

                            pieceGroup.getChildren().add(piece);

                            if(blackMove)
                            {
                                if(testRight(grid,row,PieceType.BLACK)==true)
                                {
                                    gameOver=true;
                                    if(WinBox.display(PieceType.BLACK)==true)
                                        resetBoard();

                                }
                                else if(testDown(grid,column,PieceType.BLACK)==true)
                                {
                                    gameOver=true;
                                    if(WinBox.display(PieceType.BLACK)==true)
                                        resetBoard();

                                }
                                else if(testDiagonalLeft(grid,row,column,PieceType.BLACK))
                                {
                                    gameOver=true;
                                    if(WinBox.display(PieceType.BLACK)==true)
                                        resetBoard();

                                }
                                else if(testDiagonalRight(grid,row,column,PieceType.BLACK))
                                {
                                    gameOver=true;
                                    if(WinBox.display(PieceType.BLACK)==true)
                                        resetBoard();

                                }

                            }
                            else
                            {
                                if(testRight(grid,row,PieceType.WHITE)==true)
                                {
                                    gameOver=true;
                                    if(WinBox.display(PieceType.WHITE)==true)
                                        resetBoard();

                                }
                                else if(testDown(grid,column,PieceType.WHITE)==true)
                                {
                                    gameOver=true;
                                    if(WinBox.display(PieceType.WHITE)==true)
                                        resetBoard();

                                }
                                else if(testDiagonalLeft(grid,row,column,PieceType.WHITE))
                                {
                                    gameOver=true;
                                    if(WinBox.display(PieceType.WHITE)==true)
                                        resetBoard();

                                }
                                else if(testDiagonalRight(grid,row,column,PieceType.WHITE))
                                {
                                    gameOver=true;
                                    if(WinBox.display(PieceType.WHITE)==true)
                                        resetBoard();

                                }

                            }
                            //switches color
                            blackMove=!blackMove;
                        }

                    });

                list.add(rect);
            }
        }

        return list;
    }

    /**
     * helper method that aids in performing the action of placing the piece
     */
    private Piece placePiece(PieceType type, int x, int y)
    {
        Piece piece = null;
        
        //does not let the user place a piece if the game has ended
        if(gameOver == true)
            piece = null;
        else if(grid[y+1][x+1]!=null)
            piece = null;
        else
        {
            piece = new Piece(type, x, y);
            grid[y+1][x+1]=piece;
        }
        return piece;
    }

    /**
     * clears the board and resets the game state
     */
    public void resetBoard() 
    {
        for(int i = 0; i < 14; i++)
        {
            for(int j = 0; j < 14; j++)
            {
                pieceGroup.getChildren().remove(grid[i][j]);
                grid[i][j]=null;
                board[i][j].setPiece(null);
            }
        }

        gameOver = false;
        blackMove = false;
    }

    /**
     * tests at the current spot to see if the user has won by way of 5 in a row to the right
     */
    private boolean testRight(Piece[][] grid,int x,PieceType type)
    {
        for(int a = 0; a < 11; a++)
        {
            if(grid[x][a] != null)
                if(grid[x][a].getType() == type)
                    if(grid[x][a+1] != null)
                        if(grid[x][a+1].getType() == type)
                            if(grid[x][a+2] != null)
                                if(grid[x][a+2].getType() == type)
                                    if(grid[x][a+3] != null)
                                        if(grid[x][a+3].getType() == type)
                                            if(grid[x][a+4] != null)
                                                if(grid[x][a+4].getType() == type)
                                                    return true;
        }
        return false;
    }

    /**
     * tests at the current spot to see if the user has won by way of 5 in a row downward
     */
    private boolean testDown(Piece[][] grid,int y,PieceType type)
    {
        for(int a = 0; a<11;a++)
        {
            if(grid[a][y] != null)
                if(grid[a][y].getType() == type)
                    if(grid[a+1][y] != null)
                        if(grid[a+1][y].getType() == type)
                            if(grid[a+2][y] != null)
                                if(grid[a+2][y].getType() == type)
                                    if(grid[a+3][y] != null)
                                        if(grid[a+3][y].getType() == type)
                                            if(grid[a+4][y] != null)
                                                if(grid[a+4][y].getType() == type)
                                                    return true;
        }
        return false;
    }

    /**
     * tests at the current spot to see if the user has won by way of 5 in a row to the diagonal left
     */
    private boolean testDiagonalLeft(Piece[][] grid,int x, int y,PieceType type)
    {
        //if the row position is greater than the column position
        if(x >= y)
        {
            for(int a = 0; a< 11-(x-y); a++)
            {
                if(grid[a+x-y][a]!=null)
                    if(grid[a+x-y][a].getType()==type)
                        if(grid[a+1+x-y][a+1]!=null)
                            if(grid[a+1+x-y][a+1].getType()==type)
                                if(grid[a+x+2-y][a+2]!=null)
                                    if(grid[a+2+x-y][a+2].getType()==type)
                                        if(grid[a+x+3-y][a+3]!=null)
                                            if(grid[a+3+x-y][a+3].getType()==type)
                                                if(grid[a+x+4-y][a+4]!=null)
                                                    if(grid[a+4+x-y][a+4].getType()==type)
                                                        return true;
            }
        }
        else
        {
            for(int a = 0; a< 11-(y-x); a++)
            {
                if(grid[a][a+y-x]!=null)
                    if(grid[a][a+y-x].getType()==type)
                        if(grid[a+1][a+1+y-x]!=null)
                            if(grid[a+1][a+1+y-x].getType()==type)
                                if(grid[a+2][a+2+y-x]!=null)
                                    if(grid[a+2][a+2+y-x].getType()==type)
                                        if(grid[a+3][a+3+y-x]!=null)
                                            if(grid[a+3][a+3+y-x].getType()==type)
                                                if(grid[a+4][a+4+y-x]!=null)
                                                    if(grid[a+4][a+4+y-x].getType()==type)
                                                        return true;
            }
        }
        return false;
    }

    /**
     * tests at the current spot to see if the user has won by way of 5 in a row to the diagonal right
     */
    private boolean testDiagonalRight(Piece[][] grid,int x, int y,PieceType type)
    {
        if(x+y<=14&&x+y>5)
        {
            for(int a = 0; a< x+y-3; a++)
            {
                if(grid[a][x+y-a]!=null)
                    if(grid[a][x+y-a].getType()==type)
                        if(grid[a+1][x+y-1-a]!=null)
                            if(grid[a+1][x+y-1-a].getType()==type)
                                if(grid[a+2][x+y-2-a]!=null)
                                    if(grid[a+2][x+y-a-2].getType()==type)
                                        if(grid[a+3][x+y-a-3]!=null)
                                            if(grid[a+3][x+y-3-a].getType()==type)
                                                if(grid[a+4][x+y-a-4]!=null)
                                                    if(grid[a+4][x+y-4-a].getType()==type)
                                                        return true;
            }
        }
        else if(x + y > 14)
        {
            for(int a = 0; a < 11 - (y + x - 14); a++)
            {
                if(grid[x+y-14+a][14-a]!=null)
                    if(grid[x+y-14+a][14-a].getType()==type)
                        if(grid[x+y-14+a+1][14-a-1]!=null)
                            if(grid[x+y-14+a+1][14-a-1].getType()==type)
                                if(grid[x+y-14+a+2][14-a-2]!=null)
                                    if(grid[x+y-14+a+2][14-a-2].getType()==type)
                                        if(grid[x+y-14+a+3][14-a-3]!=null)
                                            if(grid[x+y-14+a+3][14-a-3].getType()==type)
                                                if(grid[x+y-14+a+4][14-a-4]!=null)
                                                    if(grid[x+y-14+a+4][14-a-4].getType()==type)
                                                        return true;
            }
        }
        return false;
    }

    /**
     * creates and displays the board
     */
    @Override
    public void start(Stage primaryStage)throws Exception
    {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("OMOK");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * launches the program
     */
    public void runGame(String args[])
    {
        launch(args);
    }
}