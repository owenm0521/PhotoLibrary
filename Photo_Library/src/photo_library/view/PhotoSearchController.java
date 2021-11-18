package photo_library.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photo_library.app.Album;
import photo_library.app.Photo;
import photo_library.app.PhotoLibrary;
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
	@FXML ListView<String> resultsList; 
	@FXML TextField enterAlbumName; 
	@FXML Button createNewAlbum; 
	
	private ObservableList<String> results;
	private User currUser; 
	private Stage primaryStage;
	
	
	public void back(ActionEvent e) throws IOException {
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
	
	public void searchByDate(ActionEvent e) {
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
			
			if(results.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(":(");
				alert.setHeaderText("Your search yielded no results.");
				alert.showAndWait();
				return;
			}else {
				populateList();
			} 
		}		
	}
	
	public void searchByTagSingle(ActionEvent e) {
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
					if( photo.getTags() != null
							&& photo.getTags().containsKey(tagTypeSingle.getText()) 
							&& photo.getTags().get(tagTypeSingle.getText()).contains(tagValueSingle.getText())
							&& !results.contains(photo.getPath())) {
						results.add(photo.getPath()); 
					}
				}
			}
			
			if(results.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(":(");
				alert.setHeaderText("Your search yielded no results.");
				alert.showAndWait();
				return;
			}else {
				populateList();
			} 

		}		
	}
	
	
	public void searchByTagDouble(ActionEvent e) {
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
						if( photo.getTags() != null
								&& photo.getTags().containsKey(tagTypeDouble1.getText()) 
								&& photo.getTags().get(tagTypeDouble1.getText()).contains(tagValueDouble1.getText())
								&& photo.getTags().containsKey(tagTypeDouble2.getText()) 
								&& photo.getTags().get(tagTypeDouble2.getText()).contains(tagValueDouble2.getText())
								&& !results.contains(photo.getPath())) {
							results.add(photo.getPath()); 
						}
					}
					if(or) {
						if( photo.getTags() != null
								&& ((photo.getTags().containsKey(tagTypeDouble1.getText()) 
								&& photo.getTags().get(tagTypeDouble1.getText()).contains(tagValueDouble1.getText()))
								|| (photo.getTags().containsKey(tagTypeDouble2.getText()) 
								&& photo.getTags().get(tagTypeDouble2.getText()).contains(tagValueDouble2.getText())))
								&& !results.contains(photo.getPath())) {
							results.add(photo.getPath()); 
						}
					}
					
				}
			}
			
			if(results.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(":(");
				alert.setHeaderText("Your search yielded no results.");
				alert.showAndWait();
				return;
			}else {
				populateList();
			} 
		}		
	}
	
	public void createNewAlbum(ActionEvent e) {
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
			if(results.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Cannot Create Album");
				alert.setHeaderText("Album cannot be created because your search yielded no results. Please enter valid search criteria and try again.");
				alert.showAndWait();
				return;
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
	
	
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage; 
		this.currUser = PhotoLibrary.currentUser; 
		this.results = FXCollections.observableArrayList(); 
		
		ObservableList<String> tagDropDownValues = FXCollections.observableArrayList("AND", "OR");
		tagDropDown.setItems(tagDropDownValues); 
		
	}
	
	public void populateList() {
		resultsList.setCellFactory(param -> new ListCell<String>(){
			private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty){
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Photo temp = null;
                    for (Album album : currUser.getAlbums()) {
                    	for(Photo p: album.getPhotos()) {
                        	if(p.getPath().equals(name)) {
                        		temp = p;
                        		break;
                        	}
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
		
		resultsList.setItems(results);
		
		
	}

	
	
	
	
}
