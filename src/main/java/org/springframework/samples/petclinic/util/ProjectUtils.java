package org.springframework.samples.petclinic.util;

/**
 * Pomocna trida shromazdujici atributy nastaveni projektu.
 */
public final class ProjectUtils {

	/**
	 * Nutné pøenastavit cestu ke složce "uploads" v projektu.
	 */
	private static String myProjectPath = "C:\\Users\\Tomas\\Documents\\workspace-sts-3.7.2.RELEASE\\petclinic\\src\\main\\webapp\\static\\uploads";

	public static String getMyProjectPath() {
		return myProjectPath;
	}	
	
}
