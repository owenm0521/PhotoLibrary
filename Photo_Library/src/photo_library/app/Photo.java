package photo_library.app;
import java.util.Date;
import java.util.HashMap;
import java.time.*;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Photo {
	String location;
	String name;
	String caption; 
	Date date;
	
	HashMap<String, ArrayList<String>> tags;
	
	public Photo(String location, String name) {
		this.location = location;
		File image = new File(location);
		date = convertTime(image.lastModified());
		this.name = image.getName();
	}
	
	public Date convertTime(long time){
	    Date date = new Date(time);
	    return date;
	}
	
	public void addCaption(String caption) {
		this.caption = caption;
	}
	
	public void addtags(String tag, String value) {
		if(tags.containsKey(tag)) {
			ArrayList<String> temp = tags.get(tag);
			temp.add(value);
			tags.put(tag, temp);
		}
		else {
			ArrayList temp = new ArrayList<String>();
			temp.add(value);
			tags.put(tag,temp);
		}
		
	}
	
	public String getCaption() {
		return caption;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public HashMap<String, ArrayList<String>> getTags() {
		return tags;
	}
	
	public Date getDate() {
		return date;
	}
}
