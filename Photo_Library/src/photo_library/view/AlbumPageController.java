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
import javafx.scene.control.ComboBox;
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
	@FXML Button moveOrCopyPhoto;
	@FXML Button createNewTag;
	@FXML Button deleteTag;
	@FXML Button addPhoto;
	@FXML Button saveCaption;
	@FXML ListView<String> photoTagsView;
	@FXML ListView<String> photoList;
	@FXML TextField value;
	@FXML ComboBox<String> tag;
	@FXML TextField dateView;
	@FXML TextField captionView;
	@FXML ImageView selectedPhotoView;
	
	
	public void start(Stage primaryStage, Album current, User currentUser) throws FileNotFoundException {
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
			try {
				updatePhoto();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		photoList.getSelectionModel().select(0);
	}
	
	public void updatePhoto() throws Exception {
		int index = photoList.getSelectionModel().getSelectedIndex();
		if (index == -1) {
			System.out.println("failed out");
			return;
		}
		Photo photo = null;
		for(Photo p: album.getPhotos()) {
			if(p.getPath().equals(photos.get(index))){
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
        
        photoTags = FXCollections.observableArrayList();
        for(String key:photo.getTags().keySet()) {
			photoTags.add(key +" | " + photo.getTags().get(key));
		}
		photoTagsView.setItems(photoTags);
		tag.getItems().clear();
		tag.getItems().addAll(currentUser.getTags());
        
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
                    InputStream stream = null;
					try {
						stream = new FileInputStream(temp.getPath());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
		
	
	public void back(ActionEvent e) throws Exception {
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
			mainStage.setTitle(currentUser.getUser());
			mainStage.setResizable(false);
			mainStage.show();
		}
	}
	
	public void moveOrCopyPhoto() throws Exception {
		int index = photoList.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		Photo photo = album.findPhoto(photos.get(index));
		// goes to Move or Copy Page, passing current album and selected photo as parameters
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/photo_library/view/albumsList.fxml"));
		AnchorPane root = loader.load();
		AlbumsListController moveController = loader.getController();
		
		moveController.start(mainStage, album, photo);

		Scene scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.setResizable(false);
		mainStage.show();
	}
	
	
	public void addPhoto() {
		
	}

	public void deletePhoto() {
		int index = photoList.getSelectionModel().getSelectedIndex();
		if(index==-1) {
			return;
		}
		for(Photo p: album.getPhotos()) {
			if(p.getPath().equals(photos.get(index))){
				album.getPhotos().remove(p);
				break;
			}
		}
		photos.remove(index);
		photoList.getSelectionModel().select(index);
		if(photos.size() <= 0) {
			dateView.clear();
			captionView.clear();
			selectedPhotoView.setImage(null);
			photoTagsView.setItems(null);
			value.clear();
		}
	}
	
	public void createNewTag() {
		if(tag.getValue() == null || value.getText().isEmpty()) {
			return;
		}
		int index = photoList.getSelectionModel().getSelectedIndex();
		if(index == -1) {
			return;
		}
		Photo photo = album.findPhoto(photos.get(index));
		if(tag.getValue().equals("Location") && photo.getTags().containsKey("Location")) {
			incorrectInfoError("Location Error","Location can only have one value.");
			return;
		}
		if(photo.getTags().containsKey(tag.getValue()) && photo.getTags().get(tag).contains(value.getText())){
			incorrectInfoError("Duplicate Value", "This tag already has that value");
			return;
		}
		if(!photo.getTags().containsKey(tag.getValue())) {
			photo.getTags().put(tag.getValue().toString(), new ArrayList<String>());
			if(!currentUser.getTags().contains(tag.getValue())) {
				currentUser.getTags().add(tag.getValue().toString());
				
			}
		}
		photo.getTags().get(tag.getValue().toString()).add(value.getText());
		photoTags = FXCollections.observableArrayList();
		for(String key:photo.getTags().keySet()) {
			photoTags.add(key +" | " + photo.getTags().get(key));
		}
		photoTagsView.setItems(photoTags);
		
		
	}
	
	public void deleteTag() {
		
	}
	
	public void saveCaption(ActionEvent e) {
		if((Button)e.getSource() == saveCaption) {
			int index = photoList.getSelectionModel().getSelectedIndex();
			if(index==-1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Select a photo fucker.");
				alert.showAndWait();
				return;
			}
			if(captionView.getText() == null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid Caption");
				alert.setHeaderText("Please enter a valid caption.");
				alert.showAndWait();
				return;
			}
			for (Photo photo : album.getPhotos()) {
				if(photo.getPath().equals(photos.get(index))) {
					photo.setCaption(captionView.getText());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Great success!");
					alert.setHeaderText("Caption successfully saved.");
					alert.showAndWait();
					return;
				}
			}
			
		}
		
	}
}
