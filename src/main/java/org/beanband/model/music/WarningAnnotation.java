package org.beanband.model.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@code MusicAnnotation} capturing general warnings created during the work of
 * the {@code Arranger} implementations. {@code WarningAnnotation} objects can
 * be attached to any {@code Annotatable} and can be used to collect and display
 * notifications about any problems detected during arranging after the process
 * has finished.
 * 
 * @author Michael Niemeck
 */
public class WarningAnnotation extends MusicAnnotation<Annotatable> {

	private final List<String> messages = new ArrayList<>();

	/**
	 * Adds a message to this {@code WarningAnnotatin}. Note that multiple calls to
	 * this method will accumulate massages rather than overwriting previous
	 * messages.
	 * 
	 * @param message The message {@code String} to add to this
	 *                {@code WarningAnnotation}.
	 */
	public void addMessage(String message) {
		messages.add(message);
	}

	/**
	 * Gets all messages added to this {@code WarningAnnotation} so far.
	 * 
	 * @return A {@code List} of message {@code String} objects.
	 */
	public List<String> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	@Override
	public String toString() {
		return super.toString() + messages.size() + " warning messages registered.";
	}

}
