package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application 
{
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
		primaryStage.setTitle("START");
		primaryStage.getIcons().add(new Image("file:files/icon.png"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	@FXML private javafx.scene.control.Button closeButton;
	@FXML public void closeWindow(ActionEvent e) throws Exception {
		new space.MainSpace().start(new Stage());
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
	public static void main(String[] args) {
		launch(args);
	}	
}