package photo_library.view;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photo_library.app.*;

public class loginController extends PhotoLibController {
	
	@FXML Button enter;
	@FXML Button exit;
	@FXML TextField enterUsername;
	Stage mainStage;
	
	/**
	 * sets up current window
	 * @param PrimaryStage current window
	 * @throws Exception
	 */
	public void start(Stage PrimaryStage) throws Exception {
		mainStage = PrimaryStage;
		
	}
	
	/**
	 * opens account of inputted username 
	 * @param e login button
	 * @throws Exception
	 */
	public void login(ActionEvent e) throws Exception {
		if((Button)e.getSource() == enter) {
			boolean dne = false;
			String new_user = enterUsername.getText().trim();
			if(new_user.equals("admin")) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/photo_library/view/admin.fxml"));
				System.out.println("starting to load");
				AnchorPane root = (AnchorPane)loader.load();
				System.out.println("loaded pane");
				AdminController photoController = loader.getController();
				
				photoController.start(mainStage);

				Scene scene = new Scene(root);
				mainStage.setScene(scene);
				mainStage.setTitle("Admin Console");
				mainStage.setResizable(false);
				mainStage.show();
				dne = true;
			}
			if(PhotoLibrary.getUser(new_user) != null) {FXMLLoader loader = new FXMLLoader();
					PhotoLibrary.currentUser = PhotoLibrary.getUser(new_user);
					loader.setLocation(getClass().getResource("/photo_library/view/user.fxml"));
					System.out.println("starting to load");
					AnchorPane root = (AnchorPane)loader.load();
					System.out.println("loaded pane");
					UserController photoController = loader.getController();
					
					photoController.start(mainStage);

					Scene scene = new Scene(root);
					mainStage.setScene(scene);
					mainStage.setTitle(new_user);
					mainStage.setResizable(false);
					mainStage.show();
					dne=true;
			}
			if(!dne) {
				incorrectInfoError("Incorrect Username", "No username in database");
			}
		}
		
	}
}
