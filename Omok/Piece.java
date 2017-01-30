/**
 * @author Eric, Hansae, Dimitri, Jed 
 * @version 060216
 */

import javafx.scene.shape.Circle;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
public class Piece extends StackPane
{
   //contains the type of the piece, either BLACK or WHITE
   private PieceType type;
   
   /**
    * constructor for the Piece class that takes the Piece's type,
    * and where the piece is located
    */
   public Piece(PieceType type, int row, int column)
   {
        this.type = type;
       
        relocate(row*Omok.TILE_SIZE, column*Omok.TILE_SIZE);
        
        Circle circle = new Circle(Omok.TILE_SIZE/2);
        circle.setFill(type ==PieceType.BLACK?Color.BLACK : Color.WHITE);
        circle.setStroke(Color.BLACK);
            
        circle.setTranslateX(Omok.TILE_SIZE);
        circle.setTranslateY(Omok.TILE_SIZE);
        getChildren().addAll(circle);
   }
   
   /**
    * accessor that returns the type of the piece, BLACK or WHITE
    */
   public PieceType getType()
   {
       return type;
   }
   
}