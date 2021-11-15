package photo_library.app;

import java.util.ArrayList;

public class Album {
	ArrayList<Photo> photos;
	String name;
	
	public Album(String name) {
		this.name = name;
	}
	
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	
	public void removePhoto(String name) {
		for(Photo p:photos) {
			if(p.getName().equals(name)) {
				photos.remove(p);
				break;
			}
		}
	}

	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	
	public String getName() {
		return name;
	}
}
