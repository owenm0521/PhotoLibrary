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
	ArrayList<User> users = new ArrayList<User>();
	
	@FXML Button enter;
	@FXML Button exit;
	@FXML TextField enterUsername;
	Stage mainStage;
	
	public void start(Stage PrimaryStage) throws Exception {
		Admin admin = new Admin();
		users = admin.getUsers();
		mainStage = PrimaryStage;
		
	}
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
			for(User u: users) {
				System.out.println(u.getUser());
				if(u.getUser().equals(new_user)) {
					FXMLLoader loader = new FXMLLoader();
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
			}
			if(!dne) {
				incorrectInfoError("Incorrect Username", "No username in database");
			}
		}
		
	}
}
