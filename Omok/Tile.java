/**
 * @author Eric, Hansae, Dimitri, Jed 
 * @version 060216
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle
{
    private Piece piece;
    /**
     * constructor for the tile class that takes a boolean, light, which determines the color of the tile,
     * and two integers, x and y, which determine the location of the tile
     */
    public Tile(boolean light, int x, int y)
    {
        //creates boxes of TILE_SIZE width and height and colors them
        setWidth(Omok.TILE_SIZE);
        setHeight(Omok.TILE_SIZE);
        relocate(x*Omok.TILE_SIZE,y*Omok.TILE_SIZE);
        
        //determines what color current tile should be to make the board checkered
        setFill(light ? Color.valueOf("#317873") : Color.valueOf("#a0d6b4"));
    }

    /**
     * returns the object piece on the current tile
     */
    public Piece getPiece()
    {
        return piece;
    }

    /**
     * modifier that sets the piece object of the current tile
     */
    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }
}
