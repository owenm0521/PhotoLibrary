package photo_library.app;


import java.util.ArrayList;
import java.io.Serializable;
import java.io.File;
import java.util.Scanner;

/**
 * User object stores albums and user wide tags
 * 
 * @author Ali Khan
 * @author Owen Morris
 *
 */
public class User implements Serializable{
	private String username;
	private ArrayList<Album> albums;
	private ArrayList<String> tags;
	private static final long serialVersionUID = 1L;
	
	/**
	 * initializes User object
	 * @param username username to represent User
	 */
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
		tags = new ArrayList<String>();
	}
	/**
	 * changes the username
	 * @param name new username
	 */
	public void setUser(String name) {
		this.username = name;
	}
	/**
	 * gets the username
	 * @return username
	 */
	public String getUser(){
		return username;
	}
	/**
	 * sets the albums stored in user
	 * @param albums albums
	 */
	public void setAlbums(ArrayList<Album> albums){
		this.albums = albums;
	}
	/**
	 * returns the albums in User
	 * @return albums of user
	 */
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	/**
	 * finds the album based on the name
	 * @param name name of album
	 * @return album or null if not in User
	 */
	public Album searchAlbums(String name) {
		for(Album a:albums) {
			if(a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}
	/**
	 * sets the tags of the user
	 * @param tags list of tags
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	/**
	 * returns tags
	 * @return list of tags
	 */
	public ArrayList<String> getTags(){
		return tags;
	}
}
