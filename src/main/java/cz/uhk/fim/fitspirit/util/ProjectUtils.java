package cz.uhk.fim.fitspirit.util;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import cz.uhk.fim.fitspirit.FitnessCentre;
import cz.uhk.fim.fitspirit.Lesson;
import cz.uhk.fim.fitspirit.Lessons;

/**
 * Pomocna trida shromazdujici atributy nastaveni projektu.
 */
public final class ProjectUtils {

	private final FitnessCentre fitnessCentre;
	
	@Autowired
	public ProjectUtils(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	/**
	 * Nutné pøenastavit cestu ke složce "uploads" v projektu.
	 */
	private static String myProjectPath = "C:\\Users\\Tomas\\Documents\\workspace-sts-3.7.2.RELEASE\\fitspirit\\src\\main\\webapp\\static\\uploads";
	
	public static String getMyProjectPath() {
		return myProjectPath;
	}	
	
	/**
	 * Metoda nastavi lekce, jejichz zahajeni zacalo pred aktualnim casem, jako neaktivni.
	 * Zmenu updatne v databazi.
	 */
	public void setExpiredLessons() {
		Lessons lessons = new Lessons();
		lessons.getLessonList().addAll(this.fitnessCentre.getLessons());
		
		for (Lesson lesson : lessons.getLessonList()) {		
			Timestamp actualTime = new Timestamp(new Date().getTime());	
			Timestamp lessonStartTime = lesson.getStartTime();
			
			// Porovnani compareTo vraci hodnoty -1, 0, 1. 
			if (actualTime.compareTo(lessonStartTime) > 0) {
				lesson.setActive(false);
				this.fitnessCentre.storeLesson(lesson);
			}			
		}
	}
	
	
}
