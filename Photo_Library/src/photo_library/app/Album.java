package photo_library.app;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class Album implements Serializable{
	private ArrayList<Photo> photos;
	private String name;
	private static final long serialVersionUID = 1L;
	
	public Album(String name) {
		this.name = name;
	}
	
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	
	public void removePhoto(String filePath) {
		for(Photo p:photos) {
			if(p.getPath().equals(filePath)) {
				photos.remove(p);
				break;
			}
		}
	}
	
	public Photo findPhoto(String filePath) {
		for(Photo p:photos) {
			if(p.getPath().equals(filePath)) {
				return p;
			}
		}
		return null;
	}
	
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
	 * @return Date
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
	
	

	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	
	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}
	
	public String getName() {
		return name;
	}
	
	public void rename(String name) {
		this.name = name;
	}
}
