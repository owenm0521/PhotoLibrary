package photos.app;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * Creates a Photo object of the application
 * 
 * @author Jawad Mamun
 * @author Arnav Reddy
 * @version 1.0
 * @since 2021-04-14
 */
public class Photo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String filepath;
	private Date dateTaken;
	private String caption;
	private HashMap<String, ArrayList<String>> tags;
	/**
	 * Initializes Photo.
	 * @param photoFilepath The location of the photo
	 */
	public Photo(String photoFilepath) {
		filepath = photoFilepath;
		File file = new File(filepath);
		long lastModified = file.lastModified();
		dateTaken = new Date(lastModified);
		caption = "";
		tags = new HashMap<String, ArrayList<String>>();
	}
	/**
	 * Get file path of photo
	 * @return String file path of photo
	 */
	public String getFilepath() {
		return filepath;
	}
	/**
	 * Set file path of photo
	 * @param filepath file path of photo
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	/**
	 * Get date of photo
	 * @return Date Date of photo
	 */
	public Date getDateTaken() {
		return dateTaken;
	}
	/**
	 * Set date of photo
	 * @param dateTaken Date of photo
	 */
	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}
	/**
	 * Get caption of photo
	 * @return String Caption of photo
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * Set date of photo
	 * @param caption Caption of photo
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * Get map of tag and value
	 * @return HashMap Map of tag and values of photo
	 */
	public HashMap<String, ArrayList<String>> getTags() {
		return tags;
	}
	/**
	 * Set map of tag and value
	 * @param tags Map of tag and values of photo
	 */
	public void setTags(HashMap<String, ArrayList<String>> tags) {
		this.tags = tags;
	}
}
