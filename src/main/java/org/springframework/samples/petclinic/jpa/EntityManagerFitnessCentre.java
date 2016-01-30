package org.springframework.samples.petclinic.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.samples.petclinic.ActivityType;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Lesson;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;
import org.springframework.samples.petclinic.PetType;
import org.springframework.samples.petclinic.Reservation;
import org.springframework.samples.petclinic.Room;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.UserRole;
import org.springframework.samples.petclinic.Vet;
import org.springframework.samples.petclinic.Visit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

/**
 * JPA implementation of the Clinic interface using EntityManager.
 *
 * <p>The mappings are defined in "orm.xml" located in the META-INF directory.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @since 22.4.2006
 */
@Repository
@Transactional
public class EntityManagerFitnessCentre implements FitnessCentre {

	@PersistenceContext
	private EntityManager em;


	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Vet> getVets() {
		return this.em.createQuery("SELECT vet FROM Vet vet ORDER BY vet.lastName, vet.firstName").getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Room> getRooms() {
		return this.em.createQuery("SELECT room FROM Room room ORDER BY room.id").getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<ActivityType> getActivityTypes() throws DataAccessException {
		return this.em.createQuery("SELECT activityType FROM ActivityType activityType ORDER BY activityType.id").getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<User> getUsers() throws DataAccessException {
		return this.em.createQuery("SELECT user FROM User user ORDER BY user.id").getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Lesson> getLessons() throws DataAccessException {
		return this.em.createQuery("SELECT lesson FROM Lesson lesson ORDER BY lesson.id").getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Reservation> getReservations() throws DataAccessException {
		return this.em.createQuery("SELECT reservation FROM Reservation reservation ORDER BY reservation.id").getResultList();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<PetType> getPetTypes() {
		return this.em.createQuery("SELECT ptype FROM PetType ptype ORDER BY ptype.name").getResultList();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Owner> findOwners(String lastName) {
		Query query = this.em.createQuery("SELECT owner FROM Owner owner WHERE owner.lastName LIKE :lastName");
		query.setParameter("lastName", lastName + "%");
		return query.getResultList();
	}

	@Transactional(readOnly = true)
	public Owner loadOwner(int id) {
		return this.em.find(Owner.class, id);
	}
	
	@Transactional(readOnly = true)
	public Room loadRoom(int id) {
		return this.em.find(Room.class, id);
	}
	
	@Transactional(readOnly = true)
	public ActivityType loadActivityType(int id) throws DataAccessException {
		return this.em.find(ActivityType.class, id);
	}
	
	@Transactional(readOnly = true)
	public UserRole loadUserRole(int id) throws DataAccessException {
		return this.em.find(UserRole.class, id);
	}
	
	@Transactional(readOnly = true)
	public User loadUser(int id) throws DataAccessException {
		return this.em.find(User.class, id);
	}

	@Transactional(readOnly = true)
	public Pet loadPet(int id) {
		return this.em.find(Pet.class, id);
	}

	public void storeOwner(Owner owner) {
		// Consider returning the persistent object here, for exposing
		// a newly assigned id using any persistence provider...
		Owner merged = this.em.merge(owner);
		this.em.flush();
		owner.setId(merged.getId());
	}
	
	public void storeRoom(Room room) {
		Room merged = this.em.merge(room);
		this.em.flush();
		room.setId(merged.getId());
	}
	
	public void storeActivityType(ActivityType activityType) throws DataAccessException {
		ActivityType merged = this.em.merge(activityType);
		this.em.flush();
		activityType.setId(merged.getId());
	}
	
	public void storeUser(User user) throws DataAccessException {
		User merged = this.em.merge(user);
		this.em.flush();
		user.setId(merged.getId());
	}
	
	public void storeLesson(Lesson lesson) throws DataAccessException {
		Lesson merged = this.em.merge(lesson);
		this.em.flush();
		lesson.setId(merged.getId());
	}

	public void storePet(Pet pet) {
		// Consider returning the persistent object here, for exposing
		// a newly assigned id using any persistence provider...
		Pet merged = this.em.merge(pet);
		this.em.flush();
		pet.setId(merged.getId());
	}

	public void storeVisit(Visit visit) {
		// Consider returning the persistent object here, for exposing
		// a newly assigned id using any persistence provider...
		Visit merged = this.em.merge(visit);
		this.em.flush();
		visit.setId(merged.getId());
	}

	public void deletePet(int id) throws DataAccessException {
		Pet pet = loadPet(id);
		this.em.remove(pet);
	}

	public void deleteRoom(int id) throws DataAccessException {
		Room room = loadRoom(id);
		this.em.remove(room);
	}

	public void deleteActivityType(int id) throws DataAccessException {
		ActivityType activityType = loadActivityType(id);
		this.em.remove(activityType);
	}
	
	public void deleteUser(int id) throws DataAccessException {
		User user = loadUser(id);
		this.em.remove(user);
	}

}