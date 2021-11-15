package photo_library.app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photo_library.view.*;

public class photoLibrary extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/photo_library/view/login.fxml"));
		System.out.println("starting to load");
		AnchorPane root = (AnchorPane)loader.load();
		System.out.println("loaded pane");
		loginController photoController = 
				loader.getController();
		
		
		photoController.start(primaryStage);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}