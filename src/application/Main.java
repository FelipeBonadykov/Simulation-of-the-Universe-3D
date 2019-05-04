package application;

import java.io.IOException;
import java.time.LocalTime;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application 
{
	static Stage primaryStage, spaceStage = new Stage();
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
		this.primaryStage=primaryStage;
		primaryStage.setTitle("START");
		primaryStage.getIcons().add(new Image("file:files/icon.png"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		new space.MainSpace().start(spaceStage);// call another window
	}
	public void handleAction(ActionEvent e) throws Exception {
		primaryStage.close();//closing window
		spaceStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}	
}