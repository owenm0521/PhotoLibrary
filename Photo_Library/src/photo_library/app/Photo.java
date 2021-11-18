package photo_library.app;
import java.util.Date;
import java.io.Serializable;
import java.util.HashMap;
import java.time.*;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Photo implements Serializable{
	private String path;
	private String name;
	private String caption; 
	private Date date;
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, ArrayList<String>> tags;
	
	public Photo(String location) {
		this.path = location;
		File image = new File(location);
		date = convertTime(image.lastModified());
		caption = "";
		this.name = image.getName();
		tags = new HashMap<String, ArrayList<String>>();
	}
	
	public Date convertTime(long time){
	    Date date = new Date(time);
	    return date;
	}
	
	
	public void settags(HashMap<String, ArrayList<String>> tags) {
		this.tags = tags;
		
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
	public HashMap<String, ArrayList<String>> getTags() {
		return tags;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
}
