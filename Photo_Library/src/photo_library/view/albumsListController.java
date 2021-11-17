package photo_library.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import photo_library.app.Album;
import photo_library.app.Photo;
import photo_library.app.User;

public class albumsListController extends PhotoLibController {
	@FXML Button back; 
	@FXML Button copyToAlbum; 
	@FXML Button moveToAlbum; 
	@FXML ListView<String> albumsList; 
	private ObservableList<String> albums;
	private Album prevAlbum; 
	private Photo selectedPhoto; 
	private User currUser; 
	private Stage primaryStage; 
	
	
	private void movePicture(ActionEvent e) {
		if((Button)e.getSource() == moveToAlbum) {
			int albumIndex = albumsList.getSelectionModel().getSelectedIndex();
			if (albumIndex == -1)
				return;
			Album dest = currUser.searchAlbums(albums.get(albumIndex)); 
			if(dest.getName().equals(prevAlbum.getName()) || dest.findPhoto(selectedPhoto.getPath()) != null ) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Selected photo is already in this album.");
				alert.showAndWait();
				return;
			}
			dest.addPhoto(selectedPhoto);
			prevAlbum.removePhoto(selectedPhoto.getPath());
			return; 
		}
	}
	
	private void copyPicture(ActionEvent e) {
		if((Button)e.getSource() == copyToAlbum) {
			int albumIndex = albumsList.getSelectionModel().getSelectedIndex();
			if (albumIndex == -1)
				return;
			Album dest = currUser.searchAlbums(albums.get(albumIndex)); 
			if(dest.getName().equals(prevAlbum.getName()) || dest.findPhoto(selectedPhoto.getPath()) != null ) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Selected photo is already in this album.");
				alert.showAndWait();
				return;
			}
			dest.addPhoto(selectedPhoto);
			return; 
		}
	}
	
	private void back(ActionEvent e) {
		if((Button)e.getSource() == back) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/photo_library/view/albumPage.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			albumPageController controller = loader.getController();
			controller.start(primaryStage);
	
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		}
	}
	
	
	public void start(Stage primaryStage, User currUser, Album prevAlbum, Photo selectedPhoto) {
		this.primaryStage = primaryStage; 
		this.currUser = currUser; 
		this.prevAlbum = prevAlbum;
		this.selectedPhoto = selectedPhoto; 
		
		this.albums = FXCollections.observableArrayList();
		for(int i = 0; i < currUser.getAlbums().size(); i++) {
			albums.add(currUser.getAlbums().get(i).getName());
		}
		albumsList.setItems(albums);
	}
	
	
}
