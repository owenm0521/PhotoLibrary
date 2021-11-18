package photos.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Creates a User object of the application
 * 
 * @author Jawad Mamun
 * @author Arnav Reddy
 * @version 1.0
 * @since 2021-04-14
 */
public class User implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Album> userAlbums;
	private ArrayList<String> tagTypes;
	/**
	 * Initializes User.
	 * @param userName The unique identifier of a user.
	 */
	public User(String userName) {
		name = userName;
		userAlbums = new ArrayList<Album>();
		tagTypes = new ArrayList<String>();
		tagTypes.add("Location");
		tagTypes.add("People");
		tagTypes.add("Color");

	}
	/**
	 * Searches through the albums of a user
	 * @param albumName The unique identifier of a user.
	 * @return Album The album in the user that matches the inputed albumName
	 */
	public Album searchUserAlbums(String albumName) {
		for(int i = 0; i<userAlbums.size(); i++) {
			if(userAlbums.get(i).getName().equals(albumName)) {
				return userAlbums.get(i);
			}
		}
		return null;
	}
	/**
	 * Provides String representation of user
	 * @return String Name of the user
	 */
	@Override
	public String toString() {
		return name;
	}
	/**
	 * Get name of User
	 * @return String Name of the user
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set name of user
	 * @param name Name of the user
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets list of albums of user
	 * @return ArrayList List of albums
	 */
	public ArrayList<Album> getUserAlbums() {
		return userAlbums;
	}
	/**
	 * Sets list of albums of user
	 * @param userAlbums List of Albums belonging to user.
	 */
	public void setUserAlbums(ArrayList<Album> userAlbums) {
		this.userAlbums = userAlbums;
	}
	/**
	 * Gets List of tags
	 * @return ArrayList List of tags
	 */
	public ArrayList<String> getTagTypes() {
		return tagTypes;
	}
	/**
	 * Sets List of tags
	 * @param tagTypes List of tags
	 */
	public void setTagTypes(ArrayList<String> tagTypes) {
		this.tagTypes = tagTypes;
	}
}
