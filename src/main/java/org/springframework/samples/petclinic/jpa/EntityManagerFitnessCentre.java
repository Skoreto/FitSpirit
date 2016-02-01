package org.springframework.samples.petclinic.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.samples.petclinic.ActivityType;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Lesson;
import org.springframework.samples.petclinic.Reservation;
import org.springframework.samples.petclinic.Room;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

/**
 * JPA implementation of the FitnessCentre interface using EntityManager.
 *
 * <p>The mappings are defined in "orm.xml" located in the META-INF directory.
 */
@Repository
@Transactional
public class EntityManagerFitnessCentre implements FitnessCentre {

	@PersistenceContext
	private EntityManager em;
	
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
	public Collection<User> getInstructors() throws DataAccessException {
		return this.em.createQuery("SELECT user FROM User user WHERE user.userRole.id=2 ORDER BY user.id").getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Lesson> getLessons() throws DataAccessException {
		return this.em.createQuery("SELECT lesson FROM Lesson lesson ORDER BY lesson.id").getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Lesson> getActiveLessons() throws DataAccessException {
		return this.em.createQuery("SELECT lesson FROM Lesson lesson WHERE lesson.active=1 ORDER BY lesson.id").getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Reservation> getReservations() throws DataAccessException {
		return this.em.createQuery("SELECT reservation FROM Reservation reservation ORDER BY reservation.id").getResultList();
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
	public Lesson loadLesson(int id) throws DataAccessException {
		return this.em.find(Lesson.class, id);
	}
	
	@Transactional(readOnly = true)
	public Reservation loadReservation(int id) throws DataAccessException {
		return this.em.find(Reservation.class, id);
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
	
	public void storeReservation(Reservation reservation) throws DataAccessException {
		Reservation merged = this.em.merge(reservation);
		this.em.flush();
		reservation.setId(merged.getId());
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
	
	public void deleteReservation(int id) throws DataAccessException {
		Reservation reservation = loadReservation(id);
		this.em.remove(reservation);
	}

	public void deleteLesson(int id) throws DataAccessException {
		Lesson lesson = loadLesson(id);
		this.em.remove(lesson);
	}

}