package photo_library.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import photo_library.app.*;

public class AlbumPageController extends PhotoLibController {
	User currentUser;
	Album album;
	ObservableList<String> photos;
	ObservableList<String> photoTags;
	
	@FXML Button back;
	@FXML Button movePhoto;
	@FXML Button copyPhoto;
	@FXML Button createNewTag;
	@FXML Button deleteTag;
	@FXML Button addPhoto;
	@FXML Button saveCaption;
	@FXML ListView<String> photoTagsView;
	@FXML ListView<String> photoList;
	@FXML TextField value;
	@FXML TextField dateView;
	@FXML TextField captionView;
	@FXML ImageView selectedPhotoView;
	
	
	public void start(Stage primaryStage, Album current, User currentUser) {
		this.currentUser = currentUser;
		album = current;
		mainStage = primaryStage;
		
		photos = FXCollections.observableArrayList();
		for(Photo p: current.getPhotos()) {
			photos.add(p.getPath());
		}
		photoList.setItems(photos);
		
		addImages();
		
		photoList.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
			updatePhoto();
		});

		// select the first item the first time the window opens
		photoList.getSelectionModel().select(0);
	}
	
	public void updatePhoto() throws Exception {
		int index = photoList.getSelectionModel().getSelectedIndex();
		if (index == -1) {
			return;
		}
		Photo photo = null;
		for(Photo p: album.getPhotos()) {
			if(p.getPath().equals(p.getPath())){
				photo = p;
				break;
			}
		}
		InputStream input = new FileInputStream(photo.getPath());
		Image temp = new Image(input);
		selectedPhotoView.setImage(temp);
		selectedPhotoView.setFitHeight(174);
        selectedPhotoView.setFitWidth(297);
        selectedPhotoView.setPreserveRatio(true);
        dateView.setText(photo.getDate().toString());
        captionView.setText(photo.getCaption());
	}
		
	public void addImages() throws FileNotFoundException{
		photoList.setCellFactory(param -> new ListCell<String>(){
			private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty){
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Photo temp = null;
                    for(Photo p: album.getPhotos()) {
                    	if(p.getPath().equals(name)) {
                    		temp = p;
                    		break;
                    	}
                    }
                    Image temp1 = null;
                    InputStream stream = new FileInputStream(temp.getPath());
					temp1 = new Image(stream);
					imageView.setImage(temp1);
					imageView.setFitHeight(100);
					imageView.setFitWidth(100);
					imageView.setPreserveRatio(true);
					setText(temp.getCaption());
					setGraphic(imageView);
                }
		}
	});
	}
		
	
	public void back(ActionEvent e) {
		if((Button)e.getSource() == back) {
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
	
	public void movePhoto() {
		
	}
	
	public void copyPhoto() {
		
	}
	
	public void addPhoto() {
		
	}

	public void deletePhoto() {
		
	}
	
	public void createNewTag() {
		
	}
	
	public void deleteTag() {
		
	}
	
	public void saveCaption() {
		
	}
}
