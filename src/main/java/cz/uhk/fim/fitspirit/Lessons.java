package cz.uhk.fim.fitspirit;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean domain object representing a list of lessons. Mostly here to be used for the 'lessons'
 * @author Tomas Skorepa
 */
public class Lessons {

	private List<Lesson> lessons;
	
	public List<Lesson> getLessonList() {
		if (lessons == null) {
			lessons = new ArrayList<Lesson>();
		}
		return lessons;
	}
}
