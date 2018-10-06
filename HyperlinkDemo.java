import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class HyperlinkDemo extends Application
{

	@Override
    public void start(Stage stage) {
 
        /*Hyperlink hyperlink = new Hyperlink("Go to Eclipse home page");
 
        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                getHostServices().showDocument("https://eclipse.org");
            }
        });*/
		FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
		for(String s : library_management.result)
		{
			Hyperlink hyperlink = new Hyperlink(s);
			 
	        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	                getHostServices().showDocument(s);
	            }
	        });
			root.getChildren().addAll(hyperlink);
		}
 
        Scene scene = new Scene(root);

        stage.setTitle("Top Articles");
 
        stage.setWidth(400);
        stage.setHeight(200);
 
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void launchStage(String [] args)
    {
    	Application.launch(args);	
    }
   
}
