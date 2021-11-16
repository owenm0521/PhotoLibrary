package photo_library.app;


import java.util.ArrayList;
import java.io.Serializable;
import java.io.File;
import java.util.Scanner;


public class User implements Serializable{
	private String username;
	private ArrayList<Album> albums;
	private ArrayList<String> tags;
	private static final long serialVersionUID = 1L;
	
	public User(String username) throws Exception {
		this.username = username;
		albums = new ArrayList<Album>();
		tags = new ArrayList<String>();
	}
	
	public void setUser(String name) {
		this.username = name;
	}
	
	public String getUser(){
		return username;
	}
	
	public void setAlbums(ArrayList<Album> albums){
		this.albums = albums;
	}
	
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	public Album searchAlbums(String name) {
		for(Album a:albums) {
			if(a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}
	
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	
	public ArrayList<String> getTags(){
		return tags;
	}
}
