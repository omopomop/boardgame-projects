/**
 * @author Eric, Hansae, Dimitri, Jed 
 * @version 060216
 */

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class WinBox
{
    static boolean answer;
    
    /**
     * gives the appropriate win message and asks the user if they would like to play again
     */
    public static boolean display(PieceType type)
    {
        Stage window = new Stage();

        //prevents the user from clicking on the board while the win message is active
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("WINNER!");
        window.setMinWidth(300);
        
        //displays the win message to the user along with the buttons
        Label label = new Label();
        label.setText("The Winner is " + type + "!\nWould you like to play another round?");
        Button yesButton =  new Button("Yes");
        yesButton.setOnAction(e -> 
        {
            answer=true;
            window.close();
        });
        Button closeButton = new Button("No");
        closeButton.setOnAction(e -> 
        {
            answer = false;
            window.close();
        });
        
        //creates the box with the buttons on it
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,yesButton,closeButton);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        return answer;
    }
}