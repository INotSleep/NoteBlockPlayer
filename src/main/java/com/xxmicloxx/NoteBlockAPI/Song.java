package com.xxmicloxx.NoteBlockAPI;

import java.io.File;
import java.util.HashMap;

/**
 * Represents a Note Block Studio project
 *
 */
public class Song {

	private HashMap<Integer, Layer> layerHashMap = new HashMap<Integer, Layer>();
	private short songHeight;
	private short length;
	private String title;
	private File path;
	private String author;
	private String originalAuthor;
	private String description;
	private float speed;
	private float delay;


	public Song(float speed, HashMap<Integer, Layer> layerHashMap,
		short songHeight, final short length, String title, String author, String originalAuthor,
				File path) {
		this.speed = speed;
		delay = 20 / speed;
		this.layerHashMap = layerHashMap;
		this.songHeight = songHeight;
		this.length = length;
		this.title = title;
		this.author = author;
		this.originalAuthor = originalAuthor;
		this.description = description;
		this.path = path;
	}

	/**
	 * Gets all Layers in this Song and their index
	 * @return HashMap of Layers and their index
	 */
	public HashMap<Integer, Layer> getLayerHashMap() {
		return layerHashMap;
	}

	/**
	 * Gets the Song's height
	 * @return Song height
	 */
	public short getSongHeight() {
		return songHeight;
	}

	/**
	 * Gets the length in ticks of this Song
	 * @return length of this Song
	 */
	public short getLength() {
		return length;
	}

	/**
	 * Gets the title / name of this Song
	 * @return title of the Song
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the author of the Song
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Gets the original author of the Song
	 * @return author
	 */
	public String getOriginalAuthor() {
		return originalAuthor;
	}

	/**
	 * Returns the File from which this Song is sourced
	 * @return file of this Song
	 */
	public File getPath() {
		return path;
	}

	/**
	 * Gets the description of this Song
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the speed (ticks per second) of this Song
	 * @return
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Gets the delay of this Song
	 * @return delay
	 */
	public float getDelay() {
		return delay;
	}

}
