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

/**
 * controller to initialize a user's initial page of albums 
 * 
 * @author Owen Morris
 * @author Ali Khan
 */
public class UserController extends PhotoLibController {
	User currentUser = null;
	ArrayList<Album> albums;
	
	@FXML ListView<String> albumList;
	@FXML Button createNewAlbum;
	@FXML Button deleteCurrentAlbum;
	@FXML Button openCurrentAlbum;
	@FXML Button renameCurrentAlbum;
	@FXML TextField newAlbumName;
	@FXML TextField currentAlbumName;
	@FXML TextField numPhotos;
	@FXML TextField dateRange;
	@FXML Button search;
	
	/**
	 * sets up current window
	 * @param primaryStage current window
	 */
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
	
	/**
	 * gets and displays album information for selected album
	 */
	public void updateAlbumInfo() {
		int index = albumList.getSelectionModel().getSelectedIndex();
		Album album  = albums.get(index);
		currentAlbumName.setText(album.getName());
		int numphoto = 0;
		for(Photo p: album.getPhotos()) {
			numphoto++;
		}
		numPhotos.setText(numphoto+"");
		if(album.getMinDate() == null) {
			dateRange.clear();
		}
		else {
			dateRange.setText(album.getMinDate() + " TO " + album.getMaxDate());
		}
	}
	
	/**
	 * creates new album
	 * @param e create new album button
	 */
	public void createAlbum(ActionEvent e) {
		if((Button)e.getSource() == createNewAlbum) {
			if(!newAlbumName.getText().isEmpty()) {
				String new_album = newAlbumName.getText();
				if(currentUser.searchAlbums(new_album) != null) {
					incorrectInfoError("Duplicate Album", "There is another album with this name. Please choose a different name");
					return;
				}
				Album temp = new Album(new_album);
				albums.add(temp);
				currentUser.setAlbums(albums);
				albumList.getItems().add(temp.getName());
			}
		}
	}
	
	/**
	 * renames selected album 
	 * @param e rename button
	 */
	public void renameAlbum(ActionEvent e) {
		if((Button)e.getSource() == renameCurrentAlbum) {
			if(currentAlbumName.getText().isEmpty()) {
				incorrectInfoError("Empty Name","You did not provide a name for your album");
				return;
			}
			String current = albumList.getSelectionModel().getSelectedItem();
			String new_name = currentAlbumName.getText();
			if(!current.equals(new_name)) {
				if(currentUser.searchAlbums(new_name)!=null) {
					incorrectInfoError("Duplicate Album","There is another album with this name. Please choose a different name");
					return;
				}
				Album currentAlbum = null;
				currentAlbum = currentUser.searchAlbums(current);
				currentAlbum.rename(new_name);
				int index = albumList.getItems().indexOf(current);
				albumList.getItems().remove(current);
				albumList.getItems().add(index, new_name);
				albumList.getSelectionModel().select(new_name);
			}
		}
	}
	
	/**
	 * deletes selected album
	 * @param e delete button
	 */
	public void deleteAlbum(ActionEvent e) {
		if((Button)e.getSource()==deleteCurrentAlbum) {
		int index = albumList.getSelectionModel().getSelectedIndex();
		if(index == -1) {
			return;
		}
		for(int i = 0;i<currentUser.getAlbums().size();i++) {
			if(currentUser.getAlbums().get(i).getName().equals(albums.get(index)))
			{
				currentUser.getAlbums().remove(i);
				break;
			}
		}
		albums.remove(index);
		albumList.getItems().remove(index);
		if(index == 0) {
			index = 1;
		}
		albumList.getSelectionModel().select(index-1);
		if(albums.size() <= 0) {
			currentAlbumName.clear();
			dateRange.clear();
			numPhotos.clear();
		}
	}
	}
	
	
	/**
	 * opens selected album
	 * @param e open button
	 * @throws Exception
	 */
	public void openAlbum(ActionEvent e) throws Exception {
		if((Button)e.getSource() == openCurrentAlbum) {
			String name = albumList.getSelectionModel().getSelectedItem();
			if(name == null || name == "") {
				return;
			}
			Album currentAlbum = currentUser.searchAlbums(name);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/photo_library/view/albumPage.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			AlbumPageController albumController = loader.getController();
			
			albumController.start(mainStage, currentAlbum, currentUser);

			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.setTitle(currentAlbum.getName());
			mainStage.setResizable(false);
			mainStage.show();
			
		}
	}
	
	/**
	 * takes user to photo search page 
	 * @param e search all button
	 * @throws Exception
	 */
	public void search(ActionEvent e) throws Exception {
		if((Button)e.getSource() == search) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/photo_library/view/photoSearch.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		PhotoSearchController searchController = loader.getController();
		
		searchController.start(mainStage);

		Scene scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.setTitle("Search");
		mainStage.setResizable(false);
		mainStage.show();
	}
	}
}
