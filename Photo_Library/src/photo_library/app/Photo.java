package photo_library.app;
import java.util.Date;
import java.io.Serializable;
import java.util.HashMap;
import java.time.*;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/**
 * 
 * @author Ali Khan
 * @author Owen Morris
 *
 */
public class Photo implements Serializable{
	private String path;
	private String name;
	private String caption; 
	private Date date;
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, ArrayList<String>> tags;
	/**
	 * initializes photo object
	 * @param location file path of photo
	 */
	public Photo(String location) {
		this.path = location;
		File image = new File(location);
		date = convertTime(image.lastModified());
		caption = "";
		this.name = image.getName();
		tags = new HashMap<String, ArrayList<String>>();
	}
	
	/**
	 * converts long to datetime
	 * @param time long int 
	 * @return date time object
	 */
	public Date convertTime(long time){
	    Date date = new Date(time);
	    return date;
	}
	
	/**
	 * sets the tags of a photo
	 * @param tags new set of tags
	 */
	public void settags(HashMap<String, ArrayList<String>> tags) {
		this.tags = tags;
		
	}
	/**
	 * sets caption for photo
	 * @param caption string describing photo
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * retrieves captions
	 * @return String
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * retrieves name of photo
	 * @return name of photo
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets file path
	 * @return path of photo
	 */
	public String getPath() {
		return path;
	}
	/**
	 * gets tags
	 * @return tags for photo
	 */
	public HashMap<String, ArrayList<String>> getTags() {
		return tags;
	}
	/**
	 * changes date of photo
	 * @param date new datetime object
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * retrieves date of object
	 * @return datetime object
	 */
	public Date getDate() {
		return date;
	}
}
