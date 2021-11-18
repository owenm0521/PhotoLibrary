package photos.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import photos.app.*;
/**
 * Controller to initialize when admin logs in
 * 
 * @author Jawad Mamun
 * @author Arnav Reddy
 * @version 1.0
 * @since 2021-04-14
 */
public class PhotoViewController {
	private Stage currentStage = null;
	private Album currentAlbum;
	private ObservableList<String> photosInList;
	private ObservableList<String> selectedPhotoTagsList;
	@FXML
	ListView<String> photosListView;
	@FXML
	ImageView selectedPhotoImageView;
	@FXML
	TextField displayedPhotoDate;
	@FXML
	TextField displayedPhotoCaption;
	@FXML
	ListView<String> selectedPhotoTags;
	@FXML
	ComboBox<String> inputedTagName;
	@FXML
	TextField inputedTagValue;
	@FXML
	Label albumName;
	
	
	@FXML
	private void returnToPreviousPage() throws IOException {
		// goes back to albumView page
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/photos/view/albumView.fxml"));
		VBox root = (VBox)loader.load();
		AlbumViewController albumViewPage = loader.getController();
		albumViewPage.start(currentStage);

		Scene scene = new Scene(root);
		currentStage.setScene(scene);
		currentStage.setResizable(false);
		currentStage.show();
	}
	
	@FXML
	private void deleteTag() {
		// makes sure both text fields are filled
		if(inputedTagName.getValue()==null || inputedTagName.getValue()==null ||
				inputedTagName.getValue().equals("") || inputedTagValue.getText().equals("")) return;
		// finds selectedPhoto from ListView
		int index = photosListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		Photo selectedPhoto = currentAlbum.findPhoto(photosInList.get(index));
		if(selectedPhoto.getTags().containsKey(inputedTagName.getValue())) {
			selectedPhoto.getTags().get(inputedTagName.getValue()).remove(inputedTagValue.getText());
			// gets rid of the tag name if there are no more values attached to it
			if(selectedPhoto.getTags().get(inputedTagName.getValue()).size()==0) {
				selectedPhoto.getTags().remove(inputedTagName.getValue());
			}
			// refreshes the listview with tags
			selectedPhotoTagsList = FXCollections.observableArrayList();
	        Iterator it = selectedPhoto.getTags().entrySet().iterator();
	        while(it.hasNext()) {
	        	Map.Entry<String, ArrayList<String>> pair = (Map.Entry<String, ArrayList<String>>)it.next();
	        	if(pair.getValue().size()==0) {
	        		selectedPhotoTagsList.add("Tag Name: " + pair.getKey() + " || Tag Values: N/A");
	        	}
	        	else{
	        		selectedPhotoTagsList.add("Tag Name: " + pair.getKey() + " || Tag Values: " + pair.getValue().toString());
	        	}
	        }
	        selectedPhotoTags.setItems(selectedPhotoTagsList);
		}
	}
	
	@FXML
	private void addTag() throws FileNotFoundException {
		// makes sure both text fields are filled
		if(inputedTagName.getValue()==null|| inputedTagValue.getText()==null ||
				inputedTagName.getValue().equals("") || inputedTagValue.getText().equals("")) return;
		// finds selectedPhoto from ListView
		int index = photosListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		Photo selectedPhoto = currentAlbum.findPhoto(photosInList.get(index));
		//restrict location to only one
		if(inputedTagName.getValue().equals("Location") && selectedPhoto.getTags().containsKey("Location")) {
			return;
		}
		// adds the tag to the photo's tag hashmap
		if(!selectedPhoto.getTags().containsKey(inputedTagName.getValue())) {
			if(!Photos.activeUser.getTagTypes().contains(inputedTagName.getValue())) {
				Photos.activeUser.getTagTypes().add(inputedTagName.getValue());
				populateCurrentPhoto();
			}
			selectedPhoto.getTags().put(inputedTagName.getValue(), new ArrayList<String>());
		}
		// prevents repetitive tag name-value pair from being entered
		if(selectedPhoto.getTags().get(inputedTagName.getValue()).contains(inputedTagValue.getText())) return;
		selectedPhoto.getTags().get(inputedTagName.getValue()).add(inputedTagValue.getText());
		// refreshes the listview with tags
		selectedPhotoTagsList = FXCollections.observableArrayList();
		Iterator it = selectedPhoto.getTags().entrySet().iterator();
		while(it.hasNext()) {
        	Map.Entry<String, ArrayList<String>> pair = (Map.Entry<String, ArrayList<String>>)it.next();
        	for(int i = 0; i<pair.getValue().size(); i++) {
        		selectedPhotoTagsList.add("Tag Name: " + pair.getKey() + " | Tag Value: " + pair.getValue().get(i));
        	}
        }
		selectedPhotoTags.setItems(selectedPhotoTagsList);
	}
	
	@FXML
	private void confirmCaption() {
		// finds selectedPhoto from ListView
		int index = photosListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		Photo selectedPhoto = currentAlbum.findPhoto(photosInList.get(index));
		// sets selected photo's caption to whatever is in the box
		selectedPhoto.setCaption(displayedPhotoCaption.getText());
		updateListImages();
	}
	
	@FXML
	private void moveOrCopyPicture() throws IOException {
		// finds selectedPhoto from ListView
		int index = photosListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		Photo selectedPhoto = currentAlbum.findPhoto(photosInList.get(index));
		// goes to Move or Copy Page, passing current album and selected photo as parameters
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/photos/view/movePhoto.fxml"));
		VBox root = (VBox)loader.load();
		MovePhotoController moveOrCopyPage = loader.getController();
		
		moveOrCopyPage.start(currentStage, currentAlbum, selectedPhoto);

		Scene scene = new Scene(root);
		currentStage.setScene(scene);
		currentStage.setResizable(false);
		currentStage.show();
	}
	
	@FXML
	private void deletePicture() {
		// finds and deletes selectedPhoto from ListView
		int index = photosListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
			if(photosInList.get(index).equals(currentAlbum.getImagesInAlbum().get(i).getFilepath())) {
				currentAlbum.getImagesInAlbum().remove(i);
				break;
			}
		}
		photosInList.remove(index);
		if (index == 0) {
			photosListView.getSelectionModel().select(index-1);
		}
		photosListView.getSelectionModel().select(index);
		if (photosInList.size() <= 0) {
			displayedPhotoDate.clear();
			displayedPhotoCaption.clear();
			//inputedTagName.clear();
			inputedTagValue.clear();
			selectedPhotoImageView.setImage(null);
			selectedPhotoTags.getItems().clear();
		}
	}
	
	@FXML
	private void addPicture() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(currentStage);
		// stops the method if the user didn't choose a file
		if(selectedFile==null) return;
		// checks if this image is already in the album, in which case an error is thrown
		if(photosInList.contains(selectedFile.getAbsolutePath())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Invalid Photo");
			alert.setHeaderText("The photo you have chosen is already in the album. Please try again.");
			alert.showAndWait();
			return;
		}
		for(int i = 0; i < Photos.activeUser.getUserAlbums().size(); i++) {
			Album outerLoopAlbum = Photos.activeUser.getUserAlbums().get(i);
			for(int j = 0; j < outerLoopAlbum.getImagesInAlbum().size(); j++) {
				Photo innerLoopPhoto = outerLoopAlbum.getImagesInAlbum().get(j);
				if(innerLoopPhoto.getFilepath().equals(selectedFile.getAbsolutePath())) {
					currentAlbum.addImageToAlbum(innerLoopPhoto);
					photosInList.add(innerLoopPhoto.getFilepath());
					updateListImages();
					return;
				}
			}
		}
		Photo addedPhoto = new Photo(selectedFile.getAbsolutePath());
		currentAlbum.addImageToAlbum(addedPhoto);
		photosInList.add(addedPhoto.getFilepath());
		updateListImages();
	}
	
	/**
	 * Default constructor for this class, not used in implementation.
	 */
	public PhotoViewController() {
		
	}
	
	/**
	 * Updates the ListView containing all the images with their thumbnail image and caption.
	 */
	public void updateListImages() {
		photosListView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Photo desiredPhoto = null;
                    for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
                    	if(currentAlbum.getImagesInAlbum().get(i).getFilepath().equals(name)) {
                    		desiredPhoto = currentAlbum.getImagesInAlbum().get(i);
                    	}
                    }
                    	Image image = null;
                    try {
						InputStream stream = new FileInputStream(desiredPhoto.getFilepath());
						image = new Image(stream);
						imageView.setImage(image);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);
                    setText(desiredPhoto.getCaption());
                    setGraphic(imageView);
                }
            }
        });
	}
	/**
	 * Main entry point for JavaFX application. Called when application is ready to begin running.
	 * @param primaryStage The primary stage for the application where the application scene is set.
	 * @param passedAlbum The album from which photos are pulled to display in the Photo View page.
	 */
	public void start(Stage primaryStage, Album passedAlbum) {
		currentStage = primaryStage;
		currentAlbum = passedAlbum;
		
		// set name of album at top
		albumName.setText("Album Name: " + currentAlbum.getName());
		
		// populate ListView with captions and images
		photosInList = FXCollections.observableArrayList();
		for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
			photosInList.add(currentAlbum.getImagesInAlbum().get(i).getFilepath());
			//photosInList.add(currentAlbum.getImagesInAlbum().get(i).getCaption());
		}
		photosListView.setItems(photosInList);
		updateListImages();
		
		// set listener for listview
		photosListView.getSelectionModel().selectedIndexProperty()
		.addListener((obs, oldVal, newVal) -> {
			try {
				populateCurrentPhoto();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// select the first item the first time the window opens
		photosListView.getSelectionModel().select(0);
	}
	/**
	 * Populates the current photo field on the page
	 * @throws FileNotFoundException Throws this exception if the photo cannot be found at the approrpriate directory.
	 */
	public void populateCurrentPhoto() throws FileNotFoundException {
		int index = photosListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		Photo selectedPhoto = null;
		// finds and designates selected photo
		for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
			if(currentAlbum.getImagesInAlbum().get(i).getFilepath().equals(photosInList.get(index))) {
				selectedPhoto = currentAlbum.getImagesInAlbum().get(i);
			}
		}
		InputStream stream = new FileInputStream(selectedPhoto.getFilepath());
		Image image = new Image(stream);
		selectedPhotoImageView.setImage(image);
		selectedPhotoImageView.setFitHeight(150);
        selectedPhotoImageView.setFitWidth(265);
        selectedPhotoImageView.setPreserveRatio(true);
        displayedPhotoDate.setText(selectedPhoto.getDateTaken().toString());
        displayedPhotoCaption.setText(selectedPhoto.getCaption());
        
        selectedPhotoTagsList = FXCollections.observableArrayList();
        Iterator it = selectedPhoto.getTags().entrySet().iterator();
        while(it.hasNext()) {
        	Map.Entry<String, ArrayList<String>> pair = (Map.Entry<String, ArrayList<String>>)it.next();
        	for(int i = 0; i<pair.getValue().size(); i++) {
        		selectedPhotoTagsList.add("Tag Name: " + pair.getKey() + " | Tag Value: " + pair.getValue().get(i));
        	}
        }
        selectedPhotoTags.setItems(selectedPhotoTagsList);
        inputedTagName.getItems().clear();
        inputedTagName.getItems().addAll(Photos.activeUser.getTagTypes());
	}
}
