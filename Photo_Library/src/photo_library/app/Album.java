package photo_library.app;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
/**
 * 
 * Album stores photos in an organized way
 * 
 * 	@author Ali Khan
 *  @author Owen Morris
 *  
 *
 */
public class Album implements Serializable{
	private ArrayList<Photo> photos;
	private String name;
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Constructs album class
	 * 
	 * @param name name of album
	 */
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
	}
	
	/**
	 * stores photo in album
	 * 
	 * @param photo photo to be stored in album
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	/**
	 * remove photo from file
	 * 
	 * @param filePath path of photo
	 */
	public void removePhoto(String filePath) {
		for(Photo p:photos) {
			if(p.getPath().equals(filePath)) {
				photos.remove(p);
				break;
			}
		}
	}
	
	/**
	 * finds the photo in the Album or returns null
	 * 
	 * @param filePath path of photo
	 * @return photo with matching path
	 */
	public Photo findPhoto(String filePath) {
		for(Photo p:photos) {
			if(p.getPath().equals(filePath)) {
				return p;
			}
		}
		return null;
	}
	/**
	 * gets lowest date in album
	 * 
	 * @return lowest date of photo
	 */
	public Date getMinDate() {
		if(photos.size()==0) return null;
		Date minDate = photos.get(0).getDate();
		for(Photo p:photos) {
			if(minDate.compareTo(p.getDate())>0) {
				minDate = p.getDate();
			}
		}
		return minDate;
	}
	/**
	 * Returns the maximum date
	 * @return date of photo
	 */
	public Date getMaxDate() {
		if(photos.size()==0) return null;
		Date maxDate = photos.get(0).getDate();
		for(Photo p:photos) {
			if(maxDate.compareTo(p.getDate())<0) {
				maxDate = p.getDate();
			}
		}
		return maxDate;
	}
	
	
	/**
	 * gets all photos in Album
	 * @return all photos
	 */
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	/**
	 * set the photos in album
	 * @param photos group of photos
	 */
	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}
	/**
	 * retrieves name of album
	 * @return name of album
	 */
	public String getName() {
		return name;
	}
	/**
	 * changes name of album
	 * @param name new name
	 */
	public void rename(String name) {
		this.name = name;
	}
}
