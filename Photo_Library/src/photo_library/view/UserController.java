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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import java.io.BufferedReader;  
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import photo_library.app.*;

public class UserController extends PhotoLibController {
	User currentUser;
	ArrayList<Album> albums;
	
	@FXML ListView<String> albumList;
	@FXML Button createNewAlbum;
	@FXML Button deleteCurrentAlbum;
	@FXML Button openCurrentAlbum;
	@FXML Button renameCurrentAlbum;
	@FXML TextField enterNewALbum;
	@FXML TextField CurrentAlbumName;
	@FXML TextField numPhotos;
	@FXML TextField dateRange;
	
	public void start(Stage primaryStage) {
		mainStage = primaryStage;
		currentUser = PhotoLibrary.currentUser;
		
		albums = new ArrayList<Album>();
		albums = currentUser.getAlbums();
		
		ObservableList<String> albumNames = FXCollections.observableArrayList();
		for(Album a: albums) {
			albumNames.add(a.getName());
		}
		
		albumList.setItems(albumNames);
		
		albumList.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> updateAlbumInfo());
		
		albumList.getSelectionModel().select(0);
	}
	
	public void updateAlbumInfo() {
		int index = albumList.getSelectionModel().getSelectedIndex();
		Album album  = albums.get(index);
		CurrentAlbumName.setText(album.getName());
		int numphoto = 0;
		for(Photo p: album.getPhotos()) {
			numphoto++;
		}
		numPhotos.setText(numphoto+"");
		dateRange.setText(album.getMinDate() + "to" + album.getMaxDate());
		
	}
}
