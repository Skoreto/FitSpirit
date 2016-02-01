package org.springframework.samples.petclinic;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

/**
 * The high-level FitSpirit business interface.
 *
 * <p>This is basically a data access object.
 * FitSpirit doesn't have a dedicated business facade.
 */
public interface FitnessCentre {

	/**
	 * Retrieve all <code>Room</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Room</code>s
	 */
	Collection<Room> getRooms() throws DataAccessException;
	
	/**
	 * Retrieve all <code>ActivityType</code>s from the data store.
	 * @return a <code>Collection</code> of <code>ActivityType</code>s
	 */
	Collection<ActivityType> getActivityTypes() throws DataAccessException;
	
	/**
	 * Retrieve all <code>User</code>s from the data store.
	 * @return a <code>Collection</code> of <code>User</code>s
	 */
	Collection<User> getUsers() throws DataAccessException;
	
	/**
	 * Retrieve all <code>instructor</code>s from the data store.
	 * @return a <code>Collection</code> of <code>instructor</code>s
	 */
	Collection<User> getInstructors() throws DataAccessException;
	
	/**
	 * Retrieve all <code>Lesson</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Lesson</code>s
	 */
	Collection<Lesson> getLessons() throws DataAccessException;
	
	/**
	 * Retrieve all active <code>Lesson</code>s from the data store.
	 * @return a <code>Collection</code> of active <code>Lesson</code>s
	 */
	Collection<Lesson> getActiveLessons() throws DataAccessException;
	
	/**
	 * Retrieve all <code>Reservation</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Reservation</code>s
	 */
	Collection<Reservation> getReservations() throws DataAccessException;
	
	/**
	 * Vr·tÌ MÌstnost z data store podle id.
	 * @param id id mÌstnosti, kterou hled·m
	 * @return poûadovan· MÌstnost, pokud byla nalezena
	 * @throws DataAccessException
	 */
	Room loadRoom(int id) throws DataAccessException;
	
	/**
	 * Vrati Aktivitu z data store podle id.
	 */
	ActivityType loadActivityType(int id) throws DataAccessException;
	
	/**
	 * Vrati Uzivatelskou roli z data store dle zadaneho id.
	 */
	UserRole loadUserRole(int id) throws DataAccessException;
	
	/**
	 * Vrati Uzivatele z data store dle zadaneho id.
	 */
	User loadUser(int id) throws DataAccessException;
	
	/**
	 * Vrati Lekci z data store podle id.
	 */
	Lesson loadLesson(int id) throws DataAccessException;
	
	/**
	 * Vrati Rezervaci z data store podle id.
	 */
	Reservation loadReservation(int id) throws DataAccessException;

	/**
	 * UloûÌ mÌstnost do data store, aù uû insertovanou nebo updatovanou.
	 * @param room mÌstnost, kterou chci uloûit
	 * @throws DataAccessException
	 */
	void storeRoom(Room room) throws DataAccessException;
	
	/**
	 * Ulozi druh aktivity do data store, at uz insertovanou nebo updatovanou.
	 * @param activityType
	 * @throws DataAccessException
	 */
	void storeActivityType(ActivityType activityType) throws DataAccessException;
	
	/**
	 * Ulozi uzivatele do data store, at uz insertovaneho nebo updatovaneho.
	 * @param user
	 * @throws DataAccessException
	 */
	void storeUser(User user) throws DataAccessException;
	
	/**
	 * Ulozi Lekci do data store, at uz insertovaneho nebo updatovaneho.
	 * @param lesson
	 * @throws DataAccessException
	 */
	void storeLesson(Lesson lesson) throws DataAccessException;
	
	/**
	 * Ulozi Rezervaci do data store, at uz insertovanou nebo updatovanou.
	 * @param reservation
	 * @throws DataAccessException
	 */
	void storeReservation(Reservation reservation) throws DataAccessException;

	/**
	 * Deletes a <code>Room</code> from the data store.
	 */
	void deleteRoom(int id) throws DataAccessException;
		
	/**
	 * Deletes a <code>ActivityType</code> from the data store.
	 */
	void deleteActivityType(int id) throws DataAccessException;
	
	/**
	 * Deletes a <code>User</code> from the data store.
	 */
	void deleteUser(int id) throws DataAccessException;
	
	/**
	 * Deletes a <code>Reservation</code> from the data store.
	 */
	void deleteReservation(int id) throws DataAccessException;
	
	/**
	 * Deletes a <code>Lesson</code> from the data store.
	 */
	void deleteLesson(int id) throws DataAccessException;

}
