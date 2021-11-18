package photos.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
public class PhotoSearchController {
	private Stage currentStage;
	private ObservableList<String> matchingPhotosInList;
	private ObservableList<String> selectedPhotoTagsList;
	
	@FXML
	ListView<String> matchingPhotosListView;
	@FXML
	DatePicker fromDatePicker;
	@FXML
	DatePicker toDatePicker;
	@FXML
	TextField singlePairTagType;
	@FXML
	TextField singlePairTagValue;
	@FXML
	TextField doublePairFirstTagType;
	@FXML 
	TextField doublePairFirstTagValue;
	@FXML
	TextField doublePairSecondTagType;
	@FXML
	TextField doublePairSecondTagValue;
	@FXML
	ImageView selectedPhotoImageView;
	@FXML
	TextField displayedSelectedPhotoCaption;
	@FXML
	TextField displayedSelectedPhotoDate;
	@FXML
	ListView<String> selectedPhotoTags;
	@FXML
	TextField inputedNewAlbumName;
	
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
	private void searchPhotosInDateRange() {
		// extracts the dates selected by user
		LocalDate localDateFrom = fromDatePicker.getValue();
		LocalDate localDateTo = toDatePicker.getValue();
		if(localDateFrom==null || localDateTo==null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Dates Not Entered");
			alert.setHeaderText("The search dates were not entered properly. Please try again.");
			alert.showAndWait();
			return;
		}
		// clears previous results
		matchingPhotosInList.clear();
		
		//converts dates from datepicker to comparable Date objects
		Instant instantFrom = Instant.from(localDateFrom.atStartOfDay(ZoneId.systemDefault()));
		Instant instantTo = Instant.from(localDateTo.atStartOfDay(ZoneId.systemDefault()));
		Date dateFrom = Date.from(instantFrom);
		Date dateTo = Date.from(instantTo);
		
		// searches all of user's images for a match
		for(int j = 0; j<Photos.activeUser.getUserAlbums().size(); j++) {
			Album currentAlbum = Photos.activeUser.getUserAlbums().get(j);
			for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
				Photo currentPhoto = currentAlbum.getImagesInAlbum().get(i);
				if(currentPhoto.getDateTaken().compareTo(dateFrom)>=0 && 
						currentPhoto.getDateTaken().compareTo(dateTo)<=0 
						&& !matchingPhotosInList.contains(currentPhoto.getFilepath())) {
					matchingPhotosInList.add(currentPhoto.getFilepath());
				}
			}
		}
		
		updateListImages();
		matchingPhotosListView.getSelectionModel().select(0);
	}
	
	@FXML
	private void searchPhotosSingleTagValuePair() {
		// throws alert if either of the fields are left blank
		if(singlePairTagType.getText().equals("")||singlePairTagValue.getText().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Tags Not Entered");
			alert.setHeaderText("The tag type and value were not entered completely. Please try again.");
			alert.showAndWait();
			return;
		}
		// clears previous results
		matchingPhotosInList.clear();
		
		//searches all of user's images for a match
		for(int j = 0; j<Photos.activeUser.getUserAlbums().size(); j++) {
			Album currentAlbum = Photos.activeUser.getUserAlbums().get(j);
			for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
				Photo currentPhoto = currentAlbum.getImagesInAlbum().get(i);
				if(currentPhoto.getTags().containsKey(singlePairTagType.getText()) && currentPhoto.getTags().get(singlePairTagType.getText()).contains(singlePairTagValue.getText())
						&& !matchingPhotosInList.contains(currentPhoto.getFilepath())) {
					matchingPhotosInList.add(currentPhoto.getFilepath());
				}
			}
		}
		
		updateListImages();
		matchingPhotosListView.getSelectionModel().select(0);
	}
	
	@FXML
	private void conjunctiveSearch() {
		// throws alert if any of the fields are left blank
		if(doublePairFirstTagType.getText().equals("")||doublePairFirstTagValue.getText().equals("") ||
				doublePairSecondTagType.getText().equals("") || doublePairSecondTagValue.getText().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Tags Not Entered");
			alert.setHeaderText("The tag types and values were not entered completely. Please try again.");
			alert.showAndWait();
			return;
		}
		
		// clears previous results
		matchingPhotosInList.clear();

		//searches all of user's images for a match with both tag combinations entered
		for(int j = 0; j<Photos.activeUser.getUserAlbums().size(); j++) {
			Album currentAlbum = Photos.activeUser.getUserAlbums().get(j);
			for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
				Photo currentPhoto = currentAlbum.getImagesInAlbum().get(i);
				if(currentPhoto.getTags().containsKey(doublePairFirstTagType.getText()) && currentPhoto.getTags().get(doublePairFirstTagType.getText()).contains(doublePairFirstTagValue.getText())
						&& currentPhoto.getTags().containsKey(doublePairSecondTagType.getText()) && currentPhoto.getTags().get(doublePairSecondTagType.getText()).contains(doublePairSecondTagValue.getText())
						&& !matchingPhotosInList.contains(currentPhoto.getFilepath())) {
					matchingPhotosInList.add(currentPhoto.getFilepath());
				}
			}
		}
		
		updateListImages();
		matchingPhotosListView.getSelectionModel().select(0);
	}
	
	@FXML
	private void disjunctiveSearch() {
		// throws alert if any of the fields are left blank
		if(doublePairFirstTagType.getText().equals("")||doublePairFirstTagValue.getText().equals("") ||
				doublePairSecondTagType.getText().equals("") || doublePairSecondTagValue.getText().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Tags Not Entered");
			alert.setHeaderText("The tag types and values were not entered completely. Please try again.");
			alert.showAndWait();
			return;
		}
		
		// clears previous results
		matchingPhotosInList.clear();

		//searches all of user's images for a match with first tag combination entered
		for(int j = 0; j<Photos.activeUser.getUserAlbums().size(); j++) {
			Album currentAlbum = Photos.activeUser.getUserAlbums().get(j);
			for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
				Photo currentPhoto = currentAlbum.getImagesInAlbum().get(i);
				if(currentPhoto.getTags().containsKey(doublePairFirstTagType.getText()) && currentPhoto.getTags().get(doublePairFirstTagType.getText()).contains(doublePairFirstTagValue.getText())
						&& !matchingPhotosInList.contains(currentPhoto.getFilepath())) {
					matchingPhotosInList.add(currentPhoto.getFilepath());
				}
			}
		}
		
		//searches all of user's images for a match with second tag combination entered
		for(int j = 0; j<Photos.activeUser.getUserAlbums().size(); j++) {
			Album currentAlbum = Photos.activeUser.getUserAlbums().get(j);
			for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
				Photo currentPhoto = currentAlbum.getImagesInAlbum().get(i);
				if(currentPhoto.getTags().containsKey(doublePairSecondTagType.getText()) && currentPhoto.getTags().get(doublePairSecondTagType.getText()).contains(doublePairSecondTagValue.getText())
						&& !matchingPhotosInList.contains(currentPhoto.getFilepath())) {
					matchingPhotosInList.add(currentPhoto.getFilepath());
				}
			}
		}
		
		updateListImages();
		matchingPhotosListView.getSelectionModel().select(0);
	}
	
	@FXML
	private void createNewAlbumWithResults() {
		// check if album name already exists or isn't entered. if so, throw error
		if(inputedNewAlbumName.getText().equals("") || Photos.activeUser.searchUserAlbums(inputedNewAlbumName.getText())!=null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Invalid Album Name");
			alert.setHeaderText("The album name you have chosen is already taken or is invalid. Please try again.");
			alert.showAndWait();
			return;
		}
		// create new album with the inputed album name
		Album newAlbum = new Album(inputedNewAlbumName.getText());
		// add photos from the search results to the new album
		for(int k = 0; k<matchingPhotosInList.size(); k++) {
			String filePathMatching = matchingPhotosInList.get(k);
			for(int i = 0; i<Photos.activeUser.getUserAlbums().size(); i++) {
				Album currentAlbum = Photos.activeUser.getUserAlbums().get(i);
				for(int j = 0; j<currentAlbum.getImagesInAlbum().size(); j++) {
					if(currentAlbum.getImagesInAlbum().get(j).getFilepath().equals(filePathMatching) && !newAlbum.getImagesInAlbum().contains(currentAlbum.getImagesInAlbum().get(j))) {
						newAlbum.addImageToAlbum(currentAlbum.getImagesInAlbum().get(j));
					}
				}
			}
		}
		// add new album as one of user's albums
		Photos.activeUser.getUserAlbums().add(newAlbum);
	}
	
	/**
	 * Default constructor for this class, not used in implementation.
	 */
	public PhotoSearchController() {
		
	}
	
	/**
	 * Main entry point for JavaFX application. Called when application is ready to begin running.
	 * @param primaryStage The primary stage for the application where the application scene is set.
	 */
	public void start(Stage primaryStage) {
		currentStage = primaryStage;
		
		// populate ListView with captions and images
		matchingPhotosInList = FXCollections.observableArrayList();
		matchingPhotosListView.setItems(matchingPhotosInList);
		updateListImages();
		
		// set listener for listview
		matchingPhotosListView.getSelectionModel().selectedIndexProperty()
		.addListener((obs, oldVal, newVal) -> {
			try {
				populateCurrentPhoto();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	/**
	 * Populates the current photo field on the page
	 * @throws FileNotFoundException Exception thrown if the photo cannot be located on user's directory.
	 */
	public void populateCurrentPhoto() throws FileNotFoundException {
		// imageview showing selected image
		
		int index = matchingPhotosListView.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		Photo selectedPhoto = null;
		// finds and designates selected photo
		for(int j = 0; j<Photos.activeUser.getUserAlbums().size(); j++) {
			Album currentAlbum = Photos.activeUser.getUserAlbums().get(j);
			for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
				if(currentAlbum.getImagesInAlbum().get(i).getFilepath().equals(matchingPhotosInList.get(index))) {
					selectedPhoto = currentAlbum.getImagesInAlbum().get(i);
				}
			}
		}
		
		InputStream stream = new FileInputStream(selectedPhoto.getFilepath());
		Image image = new Image(stream);
		selectedPhotoImageView.setImage(image);
		selectedPhotoImageView.setFitHeight(150);
        selectedPhotoImageView.setFitWidth(200);
        selectedPhotoImageView.setPreserveRatio(true);
        displayedSelectedPhotoDate.setText(selectedPhoto.getDateTaken().toString());
        displayedSelectedPhotoCaption.setText(selectedPhoto.getCaption());
        
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
	/**
	 * Updates the list of images
	 */
	public void updateListImages() {
		matchingPhotosListView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Photo desiredPhoto = null;
                    for(int j = 0; j<Photos.activeUser.getUserAlbums().size(); j++) {
                    	Album currentAlbum = Photos.activeUser.getUserAlbums().get(j);
                    	for(int i = 0; i<currentAlbum.getImagesInAlbum().size(); i++) {
                        	if(currentAlbum.getImagesInAlbum().get(i).getFilepath().equals(name)) {
                        		desiredPhoto = currentAlbum.getImagesInAlbum().get(i);
                        	}
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
}
