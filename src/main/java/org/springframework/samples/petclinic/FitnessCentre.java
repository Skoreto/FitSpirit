package org.springframework.samples.petclinic;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

/**
 * The high-level PetClinic business interface.
 *
 * <p>This is basically a data access object.
 * PetClinic doesn't have a dedicated business facade.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
public interface FitnessCentre {

	/**
	 * Retrieve all <code>Vet</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Vet</code>s
	 */
	Collection<Vet> getVets() throws DataAccessException;

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
	 * Retrieve all <code>Lesson</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Lesson</code>s
	 */
	Collection<Lesson> getLessons() throws DataAccessException;
	
	/**
	 * Retrieve all <code>Reservation</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Reservation</code>s
	 */
	Collection<Reservation> getReservations() throws DataAccessException;
	
	/**
	 * Retrieve all <code>PetType</code>s from the data store.
	 * @return a <code>Collection</code> of <code>PetType</code>s
	 */
	Collection<PetType> getPetTypes() throws DataAccessException;

	/**
	 * Retrieve <code>Owner</code>s from the data store by last name,
	 * returning all owners whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a <code>Collection</code> of matching <code>Owner</code>s
	 * (or an empty <code>Collection</code> if none found)
	 */
	Collection<Owner> findOwners(String lastName) throws DataAccessException;

	/**
	 * Retrieve an <code>Owner</code> from the data store by id.
	 * @param id the id to search for
	 * @return the <code>Owner</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */
	Owner loadOwner(int id) throws DataAccessException;
	
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
	 * Retrieve a <code>Pet</code> from the data store by id.
	 * @param id the id to search for
	 * @return the <code>Pet</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */
	Pet loadPet(int id) throws DataAccessException;

	/**
	 * Save an <code>Owner</code> to the data store, either inserting or updating it.
	 * @param owner the <code>Owner</code> to save
	 * @see BaseEntity#isNew
	 */
	void storeOwner(Owner owner) throws DataAccessException;

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
	 * @param user
	 * @throws DataAccessException
	 */
	void storeLesson(Lesson lesson) throws DataAccessException;
	
	/**
	 * Save a <code>Pet</code> to the data store, either inserting or updating it.
	 * @param pet the <code>Pet</code> to save
	 * @see BaseEntity#isNew
	 */
	void storePet(Pet pet) throws DataAccessException;

	/**
	 * Save a <code>Visit</code> to the data store, either inserting or updating it.
	 * @param visit the <code>Visit</code> to save
	 * @see BaseEntity#isNew
	 */
	void storeVisit(Visit visit) throws DataAccessException;

	/**
	 * Deletes a <code>Pet</code> from the data store.
	 */
	void deletePet(int id) throws DataAccessException;

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

}
