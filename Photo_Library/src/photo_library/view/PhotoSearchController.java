package photo_library.view;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photo_library.app.Album;
import photo_library.app.Photo;
import photo_library.app.User;

public class PhotoSearchController extends PhotoLibController {
	
	@FXML Button back; 
	@FXML DatePicker fromDateSelect; 
	@FXML DatePicker toDateSelect; 
	@FXML Button searchByDate; 
	@FXML TextField tagTypeSingle; 
	@FXML TextField tagValueSingle; 
	@FXML Button tagSearchSingle; 
	@FXML TextField tagTypeDouble1; 
	@FXML TextField tagValueDouble1; 
	@FXML ComboBox<String> tagDropDown; 
	@FXML TextField tagTypeDouble2; 
	@FXML TextField tagValueDouble2; 
	@FXML Button tagSearchDouble; 
	@FXML ListView resultsList; 
	@FXML TextField enterAlbumName; 
	@FXML Button createNewAlbum; 
	
	private ObservableList<String> results;
	private User currUser; 
	private Stage primaryStage;
	
	
	private void back(ActionEvent e) throws IOException {
		if((Button)e.getSource() == back) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/photo_library/view/user.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			UserController controller = loader.getController();
			controller.start(primaryStage);
	
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		}
	}
	
	private void searchByDate(ActionEvent e) {
		if((Button)e.getSource() == searchByDate) {
			LocalDate localDateFrom = fromDateSelect.getValue();
			LocalDate localDateTo = toDateSelect.getValue();
			if(localDateFrom == null || localDateTo == null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid Date(s)");
				alert.setHeaderText("Please enter a valid date range.");
				alert.showAndWait();
				return;
			}
			results.clear(); 
			
			Instant from = Instant.from(localDateFrom.atStartOfDay(ZoneId.systemDefault()));
			Instant to = Instant.from(localDateTo.atStartOfDay(ZoneId.systemDefault()));
			Date fromDate = Date.from(from);
			Date toDate = Date.from(to);
			
			for(Album album : currUser.getAlbums()) {
				for(Photo photo : album.getPhotos()) {
					if( photo.getDate().after(fromDate) 
							&& photo.getDate().before(toDate) 
							&& !results.contains(photo.getPath())) {
						results.add(photo.getPath()); 
					}
				}
			}
		}		
	}
	
	private void searchByTagSingle(ActionEvent e) {
		if((Button)e.getSource() == tagSearchSingle) {
			if(tagTypeSingle.getText().equals("")||tagValueSingle.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid Tag");
				alert.setHeaderText("Please enter a valid tag type and value.");
				alert.showAndWait();
				return;
			}
			results.clear();
			
			for(Album album : currUser.getAlbums()) {
				for(Photo photo : album.getPhotos()) {
					if( photo.getTags().containsKey(tagTypeSingle.getText()) 
							&& photo.getTags().get(tagTypeSingle.getText()).contains(tagValueSingle.getText())
							&& !results.contains(photo.getPath())) {
						results.add(photo.getPath()); 
					}
				}
			}
		}		
	}
	
	
	private void searchByTagDouble(ActionEvent e) {
		if((Button)e.getSource() == tagSearchDouble) {
			if(tagTypeDouble1.getText().equals("")||tagValueDouble1.getText().equals("")
					|| tagTypeDouble2.getText().equals("")||tagValueDouble2.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid Tag(s)");
				alert.setHeaderText("Please enter valid tag types and values.");
				alert.showAndWait();
				return;
			}
			
			if(tagDropDown.getValue() == null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid Selection");
				alert.setHeaderText("Please select an option from the drop down or use single tag search.");
				alert.showAndWait();
				return;
			}
			
			results.clear();
			
			boolean and = false;
			boolean or = false; 
			if(tagDropDown.getValue().equals("AND")) {
				and = true; 
			}else {
				or = true; 
			}
			
			for(Album album : currUser.getAlbums()) {
				for(Photo photo : album.getPhotos()) {
					if(and) {
						if( photo.getTags().containsKey(tagTypeDouble1.getText()) 
								&& photo.getTags().get(tagTypeDouble1.getText()).contains(tagValueDouble1.getText())
								&& photo.getTags().containsKey(tagTypeDouble2.getText()) 
								&& photo.getTags().get(tagTypeDouble2.getText()).contains(tagValueDouble2.getText())
								&& !results.contains(photo.getPath())) {
							results.add(photo.getPath()); 
						}
					}
					if(or) {
						if( ((photo.getTags().containsKey(tagTypeDouble1.getText()) 
								&& photo.getTags().get(tagTypeDouble1.getText()).contains(tagValueDouble1.getText()))
								|| (photo.getTags().containsKey(tagTypeDouble2.getText()) 
								&& photo.getTags().get(tagTypeDouble2.getText()).contains(tagValueDouble2.getText())))
								&& !results.contains(photo.getPath())) {
							results.add(photo.getPath()); 
						}
					}
					
				}
			}
		}		
	}
	
	private void createNewAlbum(ActionEvent e) {
		if((Button)e.getSource() == createNewAlbum) {
			if(enterAlbumName.getText().equals("") ){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid Album Name");
				alert.setHeaderText("No name entered for new album. Please enter a valid album name.");
				alert.showAndWait();
				return;
			}
			for(Album album : currUser.getAlbums()) {
				if(album.getName().equals(enterAlbumName.getText())) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Invalid Album Name");
					alert.setHeaderText("The album name entered already exists. Please enter a different album name.");
					alert.showAndWait();
					return;
				}
			}
			
			Album newAlbum = new Album(enterAlbumName.getText());
			for (Album album : currUser.getAlbums()) {
				for(Photo photo : album.getPhotos()) {
					if(results.contains(photo.getPath()) && !newAlbum.getPhotos().contains(photo)) {
						newAlbum.addPhoto(photo);
					}
				}
			}
			
			ArrayList<Album> updatedAlbums = currUser.getAlbums(); 
			updatedAlbums.add(newAlbum); 
			currUser.setAlbums(updatedAlbums); 
		}
	}
	
	
	public void start(Stage primaryStage, User currUser) {
		this.primaryStage = primaryStage; 
		this.currUser = currUser; 
		
		ObservableList<String> tagDropDownValues = FXCollections.observableArrayList("AND", "OR");
		tagDropDown.setItems(tagDropDownValues); 
	}
	

	
	
	
	
}
