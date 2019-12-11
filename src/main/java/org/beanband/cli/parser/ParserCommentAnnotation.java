package org.beanband.cli.parser;

import org.beanband.model.music.MusicAnnotation;
import org.beanband.model.song.SongElement;

/**
 * {@code MusicAnnotation} to attach comments read by the {@code LightweightFileParser} from
 * the song file to the Music Model.
 * 
 * @author Michael Niemeck
 * @see LightweightFileParser
 *
 */
public class ParserCommentAnnotation extends MusicAnnotation<SongElement> {
	
	private String comment;
	
	/**
	 * Returns the comment stored in this {@code MusicAnnotation}.
	 * 
	 * @return The comment {@code String}.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment to store in this {@code ParserCommentAnnotsation}. Any existing comment is overwritten.
	 * 
	 * @param comment The comment {@code String} to store.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return super.toString() + comment;
	}

}
