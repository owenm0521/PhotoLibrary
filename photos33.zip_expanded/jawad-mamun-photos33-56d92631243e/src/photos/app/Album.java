package photos.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Creates a Album object of the application
 * 
 * @author Jawad Mamun
 * @author Arnav Reddy
 * @version 1.0
 * @since 2021-04-14
 */
public class Album implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Photo> imagesInAlbum;
	/**
	 * Initializes Album
	 * @param albumName The unique identifier of a album.
	 */
	public Album(String albumName) {
		name = albumName;
		imagesInAlbum = new ArrayList<Photo>();
	}
	/**
	 * Search for a certain photo in Album
	 * @param filepath The file path of image
	 * @return Photo The photo that is being searched for
	 */
	public Photo findPhoto(String filepath) {
		for(int i = 0; i<imagesInAlbum.size(); i++) {
			if(imagesInAlbum.get(i).getFilepath().equals(filepath)) {
				return imagesInAlbum.get(i);
			}
		}
		return null;
	}
	/**
	 * Returns the minimum date
	 * @return Date
	 */
	public Date getMinDate() {
		if(imagesInAlbum.size()==0) return null;
		Date minDate = imagesInAlbum.get(0).getDateTaken();
		for(int i = 1; i<imagesInAlbum.size(); i++) {
			if(minDate.compareTo(imagesInAlbum.get(i).getDateTaken())>0) {
				minDate = imagesInAlbum.get(i).getDateTaken();
			}
		}
		return minDate;
	}
	/**
	 * Returns the maximum date
	 * @return Date
	 */
	public Date getMaxDate() {
		if(imagesInAlbum.size()==0) return null;
		Date maxDate = imagesInAlbum.get(0).getDateTaken();
		for(int i = 1; i<imagesInAlbum.size(); i++) {
			if(maxDate.compareTo(imagesInAlbum.get(i).getDateTaken())<0) {
				maxDate = imagesInAlbum.get(i).getDateTaken();
			}
		}
		return maxDate;
	}
	/**
	 * Provides String representation of album
	 * @return String Name of the album
	 */
	@Override
	public String toString() {
		return name;
	}
	/**
	 * Get name of Album
	 * @return String Name of the album
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set name of album
	 * @param name Name of the album
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets list of photos of user
	 * @return ArrayList List of photos
	 */
	public ArrayList<Photo> getImagesInAlbum() {
		return imagesInAlbum;
	}
	/**
	 * Sets list of photos of user
	 * @param imagesInAlbum List of photos
	 */
	public void setImagesInAlbum(ArrayList<Photo> imagesInAlbum) {
		this.imagesInAlbum = imagesInAlbum;
	}
	/**
	 * Add photo to list of photos of user
	 * @param newImage Photo to be added
	 */
	public void addImageToAlbum(Photo newImage) {
		imagesInAlbum.add(newImage);
	}
}
