package org.beanband.band;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class Band {

	private List<Musician> musicians;
	private LeadMusician leadMusician;

	public abstract String getStyleName();

	public abstract double getBeatsPerBar();

	public abstract int[] getAllowedChanges();

	public final Collection<Musician> getMusicians() {
		if (musicians == null) {
			loadMusicians();
		}
		return Collections.unmodifiableList(musicians);
	}
	
	private void loadMusicians() {
		musicians = new ArrayList<>();
		createMusicians();
		if (musicians.isEmpty()) {
			Logger.getGlobal().warning("Band " + getClass().getName() + " did not register any musicians.");
		}
		int numMusicians = 0;
		for (Musician musician : musicians) {
			if (!(musician instanceof PercussionMusician)) {
				numMusicians++;
			}
		}
		if (numMusicians > 15) {
			throw new UnsupportedOperationException(
					"A band can not register more than 15 non-percussion musicians");
		}
	}
	
	public final LeadMusician getLeadMusician() {
		if (musicians == null) {
			loadMusicians();
		}
		return leadMusician;
	}

	protected abstract void createMusicians();

	protected final void addMusician(Musician musician) {
		musicians.add(musician);
	}
	
	protected final void setLeadMusician(LeadMusician musician) {
		this.leadMusician = musician;
	}
	
	protected final void addLeadMusician(Musician musician) {
		addMusician(musician);
		if (musician instanceof LeadMusician) {
			setLeadMusician((LeadMusician) musician);
		} else {
			Logger.getGlobal().warning(musician.getClass().getName() + " is not a LeadMusician. Not set.");
		}
	}
}
