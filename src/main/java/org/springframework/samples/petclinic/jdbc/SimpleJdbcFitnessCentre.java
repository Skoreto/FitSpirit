package org.springframework.samples.petclinic.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.ActivityType;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Lesson;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;
import org.springframework.samples.petclinic.PetType;
import org.springframework.samples.petclinic.Reservation;
import org.springframework.samples.petclinic.Room;
import org.springframework.samples.petclinic.Specialty;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.UserRole;
import org.springframework.samples.petclinic.Vet;
import org.springframework.samples.petclinic.Visit;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A simple JDBC-based implementation of the {@link FitnessCentre} interface.
 *
 * <p>This class uses Java 5 language features and the {@link SimpleJdbcTemplate}
 * plus {@link SimpleJdbcInsert}. It also takes advantage of classes like
 * {@link BeanPropertySqlParameterSource} and
 * {@link ParameterizedBeanPropertyRowMapper} which provide automatic mapping
 * between JavaBean properties and JDBC parameters or query results.
 *
 * <p>SimpleJdbcClinic is a rewrite of the AbstractJdbcClinic which was the base
 * class for JDBC implementations of the Clinic interface for Spring 2.0.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Sam Brannen
 * @author Thomas Risberg
 * @author Mark Fisher
 */
@Service
public class SimpleJdbcFitnessCentre implements FitnessCentre {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private SimpleJdbcTemplate simpleJdbcTemplate;

	private SimpleJdbcInsert insertOwner;
	private SimpleJdbcInsert insertRoom;
	private SimpleJdbcInsert insertActivityType;
	private SimpleJdbcInsert insertPet;
	private SimpleJdbcInsert insertVisit;
	private SimpleJdbcInsert insertUser;
	private SimpleJdbcInsert insertLesson;
	private SimpleJdbcInsert insertReservation;

	private final List<Vet> vets = new ArrayList<Vet>();
	private final List<Room> rooms = new ArrayList<Room>();
	private final List<ActivityType> activityTypes = new ArrayList<ActivityType>();
	private final List<User> users = new ArrayList<User>();
	private final List<Lesson> lessons = new ArrayList<Lesson>();
	private final List<Reservation> reservations = new ArrayList<Reservation>();

	@Autowired
	public void init(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);

		this.insertOwner = new SimpleJdbcInsert(dataSource)
			.withTableName("owners")
			.usingGeneratedKeyColumns("id");
		this.insertRoom = new SimpleJdbcInsert(dataSource)
				.withTableName("rooms")
				.usingGeneratedKeyColumns("id");
		this.insertActivityType = new SimpleJdbcInsert(dataSource)
				.withTableName("activity_types")
				.usingGeneratedKeyColumns("id");
		this.insertPet = new SimpleJdbcInsert(dataSource)
			.withTableName("pets")
			.usingGeneratedKeyColumns("id");
		this.insertVisit = new SimpleJdbcInsert(dataSource)
			.withTableName("visits")
			.usingGeneratedKeyColumns("id");
		this.insertUser = new SimpleJdbcInsert(dataSource)
				.withTableName("users")
				.usingGeneratedKeyColumns("id");
		this.insertLesson = new SimpleJdbcInsert(dataSource)
				.withTableName("lessons")
				.usingGeneratedKeyColumns("id");
		this.insertReservation = new SimpleJdbcInsert(dataSource)
				.withTableName("reservations")
				.usingGeneratedKeyColumns("id");
	}


	/**
	 * Refresh the cache of Vets that the Clinic is holding.
	 * @see org.springframework.samples.petclinic.FitnessCentre#getVets()
	 */
	@ManagedOperation
	@Transactional(readOnly = true)
	public void refreshVetsCache() throws DataAccessException {
		synchronized (this.vets) {
			this.logger.info("Refreshing vets cache");

			// Retrieve the list of all vets.
			this.vets.clear();
			this.vets.addAll(this.simpleJdbcTemplate.query(
					"SELECT id, first_name, last_name FROM vets ORDER BY last_name,first_name",
					ParameterizedBeanPropertyRowMapper.newInstance(Vet.class)));

			// Retrieve the list of all possible specialties.
			final List<Specialty> specialties = this.simpleJdbcTemplate.query(
					"SELECT id, name FROM specialties",
					ParameterizedBeanPropertyRowMapper.newInstance(Specialty.class));

			// Build each vet's list of specialties.
			for (Vet vet : this.vets) {
				final List<Integer> vetSpecialtiesIds = this.simpleJdbcTemplate.query(
						"SELECT specialty_id FROM vet_specialties WHERE vet_id=?",
						new ParameterizedRowMapper<Integer>() {
							public Integer mapRow(ResultSet rs, int row) throws SQLException {
								return Integer.valueOf(rs.getInt(1));
							}},
						vet.getId().intValue());
				for (int specialtyId : vetSpecialtiesIds) {
					Specialty specialty = EntityUtils.getById(specialties, Specialty.class, specialtyId);
					vet.addSpecialty(specialty);
				}
			}
		}
	}

	
	/**
	 * Refreshne cache Místností, kterou udržuje rozhraní FitnessCentre.
	 * @throws DataAccessException
	 */
	@ManagedOperation
	@Transactional(readOnly = true)
	public void refreshRoomsCache() throws DataAccessException {
		synchronized (this.rooms) {
			this.logger.info("Refreshuji cache mistnosti");
			
			// Vrátí list všech Místností
			this.rooms.clear();
			this.rooms.addAll(this.simpleJdbcTemplate.query("SELECT id, name, illustration_image_name FROM rooms ORDER BY id",
					ParameterizedBeanPropertyRowMapper.newInstance(Room.class)));			
		}
	}
	
	/**
	 * Refreshne cache Aktivit, ktetou udržuje rozhraní FitnessCentre.
	 * @throws DataAccessException
	 */
	@ManagedOperation
	@Transactional(readOnly = true)
	private void refreshActivityTypesCache() throws DataAccessException {
		synchronized (this.activityTypes) {
			this.logger.info("Refreshuji cache aktivit");
			
			// Vrátí list všech Aktivit
			this.activityTypes.clear();
			this.activityTypes.addAll(this.simpleJdbcTemplate.query("SELECT id, name, price, illustration_image_name, short_description, description FROM activity_types ORDER BY id",
					ParameterizedBeanPropertyRowMapper.newInstance(ActivityType.class)));			
		}
	}
	
	/**
	 * Refreshne cache Lekci, ktetou udržuje rozhraní FitnessCentre.
	 * @throws DataAccessException
	 */
	@ManagedOperation
	@Transactional(readOnly = true)
	private void refreshLessonsCache() throws DataAccessException {
		synchronized (this.lessons) {
			this.logger.info("Refreshuji cache lekci");
			
			// Vrátí list všech Lekci
			this.lessons.clear();
			this.lessons.addAll(this.simpleJdbcTemplate.query("SELECT id, start_time, end_time, activityType_id, room_id, original_capacity, actual_capacity, description, instructor_id, is_active FROM lessons ORDER BY id",
					ParameterizedBeanPropertyRowMapper.newInstance(Lesson.class)));			
		}
	}
	
	/**
	 * Refreshne cache aktivnich Lekci, ktetou udržuje rozhraní FitnessCentre.
	 * @throws DataAccessException
	 */
	@ManagedOperation
	@Transactional(readOnly = true)
	private void refreshActiveLessonsCache() throws DataAccessException {
		synchronized (this.lessons) {
			this.logger.info("Refreshuji cache aktivnich lekci");
			
			// Vrátí list všech aktivnich Lekci
			this.lessons.clear();
			this.lessons.addAll(this.simpleJdbcTemplate.query("SELECT id, start_time, end_time, activityType_id, room_id, original_capacity, actual_capacity, description, instructor_id, is_active FROM lessons WHERE is_active=1 ORDER BY id",
					ParameterizedBeanPropertyRowMapper.newInstance(Lesson.class)));			
		}
	}
	
	/**
	 * Refreshne cache Rezervaci, ktetou udržuje rozhraní FitnessCentre.
	 * @throws DataAccessException
	 */
	@ManagedOperation
	@Transactional(readOnly = true)
	private void refreshReservationsCache() throws DataAccessException {
		synchronized (this.reservations) {
			this.logger.info("Refreshuji cache rezervaci");
			
			// Vrátí list všech Rezervaci
			this.reservations.clear();
			this.reservations.addAll(this.simpleJdbcTemplate.query("SELECT id, reservation_time, lesson_id, client_id, is_cancellable FROM reservations ORDER BY id",
					ParameterizedBeanPropertyRowMapper.newInstance(Reservation.class)));			
		}
	}
	
	/**
	 * Refreshne cache Uzivatelu, ktetou udrzuje rozhrani FitnessCentre.
	 * @throws DataAccessException
	 */
	@ManagedOperation
	@Transactional(readOnly = true)
	private void refreshUsersCache() throws DataAccessException {
		synchronized (this.users) {
			this.logger.info("Refreshuji cache uzivatelu");
			
			// Vrati list vsech Uzivatelu
			this.users.clear();
			this.users.addAll(this.simpleJdbcTemplate.query("SELECT id, first_name, last_name, street, city, postcode, mail, telephone, credit, description, profile_photo_name, login, password, userRole_id, is_active FROM users ORDER BY id",
					ParameterizedBeanPropertyRowMapper.newInstance(User.class)));
			
			// Vrati list vsech moznych Uzivatelskych roli
//			final List<UserRole> userRoles = this.simpleJdbcTemplate.query(
//					"SELECT id, identificator, role_description FROM user_roles", 
//					ParameterizedBeanPropertyRowMapper.newInstance(UserRole.class));
			
			
			
			
		}
	}
	
	
	// ==== START of FitnessCentre implementation section ====

	@Transactional(readOnly = true)
	public Collection<Vet> getVets() throws DataAccessException {
		synchronized (this.vets) {
			if (this.vets.isEmpty()) {
				refreshVetsCache();
			}
			return this.vets;
		}
	}
	
	@Transactional(readOnly = true)
	public Collection<Room> getRooms() throws DataAccessException {
		synchronized (this.rooms) {
			refreshRoomsCache();
		}
		return this.rooms;
	}
	
	@Transactional(readOnly = true)
	public Collection<ActivityType> getActivityTypes() throws DataAccessException {
		synchronized (this.activityTypes) {
			refreshActivityTypesCache();
		}
		return this.activityTypes;
	}
	
	@Transactional(readOnly = true)
	public Collection<User> getUsers() throws DataAccessException {
		synchronized (this.users) {
			refreshUsersCache();
		}
		return this.users;
	}
	
	@Transactional(readOnly = true)
	public Collection<Lesson> getLessons() throws DataAccessException {
		synchronized (this.lessons) {
			refreshLessonsCache();
		}
		return this.lessons;
	}
	
	@Transactional(readOnly = true)
	public Collection<Lesson> getActiveLessons() throws DataAccessException {
		synchronized (this.lessons) {
			refreshActiveLessonsCache();
		}
		return this.lessons;
	}
	
	@Transactional(readOnly = true)
	public Collection<Reservation> getReservations() throws DataAccessException {
		synchronized (this.reservations) {
			refreshReservationsCache();
		}
		return this.reservations;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> getPetTypes() throws DataAccessException {
		return this.simpleJdbcTemplate.query(
				"SELECT id, name FROM types ORDER BY name",
				ParameterizedBeanPropertyRowMapper.newInstance(PetType.class));
	}

	/**
	 * Loads {@link Owner Owners} from the data store by last name, returning
	 * all owners whose last name <i>starts</i> with the given name; also loads
	 * the {@link Pet Pets} and {@link Visit Visits} for the corresponding
	 * owners, if not already loaded.
	 */
	@Transactional(readOnly = true)
	public Collection<Owner> findOwners(String lastName) throws DataAccessException {
		List<Owner> owners = this.simpleJdbcTemplate.query(
				"SELECT id, first_name, last_name, address, city, telephone FROM owners WHERE last_name like ?",
				ParameterizedBeanPropertyRowMapper.newInstance(Owner.class),
				lastName + "%");
		loadOwnersPetsAndVisits(owners);
		return owners;
	}

	/**
	 * Loads the {@link Owner} with the supplied <code>id</code>; also loads
	 * the {@link Pet Pets} and {@link Visit Visits} for the corresponding
	 * owner, if not already loaded.
	 */
	@Transactional(readOnly = true)
	public Owner loadOwner(int id) throws DataAccessException {
		Owner owner;
		try {
			owner = this.simpleJdbcTemplate.queryForObject(
					"SELECT id, first_name, last_name, address, city, telephone FROM owners WHERE id=?",
					ParameterizedBeanPropertyRowMapper.newInstance(Owner.class),
					id);
		}
		catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Owner.class, new Integer(id));
		}
		loadPetsAndVisits(owner);
		return owner;
	}
	
	/**
	 * Naète Mistnost.
	 */
	@Transactional(readOnly = true)
	public Room loadRoom(int id) throws DataAccessException {
		Room room;
		try {
			room = this.simpleJdbcTemplate.queryForObject(
					"SELECT id, name, illustration_image_name FROM rooms WHERE id=?",
					ParameterizedBeanPropertyRowMapper.newInstance(Room.class),
					id);
		}
		catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Room.class, new Integer(id));
		}		
		return room;
	}
	
	/**
	 * Nacte Aktivitu.
	 */
	@Transactional(readOnly = true)
	public ActivityType loadActivityType(int id) throws DataAccessException {
		ActivityType activityType;
		try {
			activityType = this.simpleJdbcTemplate.queryForObject(
					"SELECT id, name, price, illustration_image_name, short_description, description FROM activity_types WHERE id=?",
					ParameterizedBeanPropertyRowMapper.newInstance(ActivityType.class),
					id);
		}
		catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(ActivityType.class, new Integer(id));
		}		
		return activityType;
	}
	
	/**
	 * Nacte Uzivatelskou roli.
	 */
	@Transactional(readOnly = true)
	public UserRole loadUserRole(int id) throws DataAccessException {
		UserRole userRole;
		try {
			userRole = this.simpleJdbcTemplate.queryForObject(
					"SELECT id, identificator, role_description FROM user_roles WHERE id=?",
					ParameterizedBeanPropertyRowMapper.newInstance(UserRole.class),
					id);
		}
		catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(UserRole.class, new Integer(id));
		}		
		return userRole;
	}
	
	/**
	 * Nacte Uzivatele dle id.
	 */
	@Transactional(readOnly = true)
	public User loadUser(int id) throws DataAccessException {
		User user;
		try {
			user = this.simpleJdbcTemplate.queryForObject(
					"SELECT id, first_name, last_name, street, city, postcode, mail, telephone, credit, description, profile_photo_name, login, password, userRole_id, is_active FROM users WHERE id=?",
					ParameterizedBeanPropertyRowMapper.newInstance(User.class),
					id);
		}
		catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(User.class, new Integer(id));
		}		
		return user;
	}
	
	/**
	 * Nacte Lekci dle id.
	 */
	@Transactional(readOnly = true)
	public Lesson loadLesson(int id) throws DataAccessException {
		Lesson lesson;
		try {
			lesson = this.simpleJdbcTemplate.queryForObject(
					"SELECT id, start_time, end_time, activityType_id, room_id, original_capacity, actual_capacity, description, instructor_id, is_active, is_reserved FROM lessons WHERE id=?",
					ParameterizedBeanPropertyRowMapper.newInstance(Lesson.class),
					id);
		}
		catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Lesson.class, new Integer(id));
		}		
		return lesson;
	}
	
	/**
	 * Nacte Rezervaci dle id.
	 */
	@Transactional(readOnly = true)
	public Reservation loadReservation(int id) throws DataAccessException {
		Reservation reservation;
		try {
			reservation = this.simpleJdbcTemplate.queryForObject(
					"SELECT id, reservation_time, lesson_id, client_id, is_cancellable FROM reservations WHERE id=?",
					ParameterizedBeanPropertyRowMapper.newInstance(Reservation.class),
					id);
		}
		catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Reservation.class, new Integer(id));
		}		
		return reservation;
	}

	@Transactional(readOnly = true)
	public Pet loadPet(int id) throws DataAccessException {
		JdbcPet pet;
		try {
			pet = this.simpleJdbcTemplate.queryForObject(
					"SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE id=?",
					new JdbcPetRowMapper(),
					id);
		}
		catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Pet.class, new Integer(id));
		}
		Owner owner = loadOwner(pet.getOwnerId());
		owner.addPet(pet);
		pet.setType(EntityUtils.getById(getPetTypes(), PetType.class, pet.getTypeId()));
		loadVisits(pet);
		return pet;
	}

	@Transactional
	public void storeOwner(Owner owner) throws DataAccessException {
		if (owner.isNew()) {
			Number newKey = this.insertOwner.executeAndReturnKey(
					new BeanPropertySqlParameterSource(owner));
			owner.setId(newKey.intValue());
		}
		else {
			this.simpleJdbcTemplate.update(
					"UPDATE owners SET first_name=:firstName, last_name=:lastName, address=:address, " +
					"city=:city, telephone=:telephone WHERE id=:id",
					new BeanPropertySqlParameterSource(owner));
		}
	}
	
	/**
	 * Pøidá místnost když je nová nebo updatne.
	 * @param room
	 * @throws DataAccessException
	 */
	@Transactional
	public void storeRoom(Room room) throws DataAccessException {
		if (room.isNew()) {
			Number newKey = this.insertRoom.executeAndReturnKey(
					new BeanPropertySqlParameterSource(room));
			room.setId(newKey.intValue());
		}
		else {
			this.simpleJdbcTemplate.update(
					"UPDATE rooms SET name=:name, illustration_image_name=:illustrationImageName WHERE id=:id",
					new BeanPropertySqlParameterSource(room));		
		}
	}

	/**
	 * Prida novou mistnost nebo updatne stavajici.
	 */
	@Transactional
	public void storeActivityType(ActivityType activityType) throws DataAccessException {
		if (activityType.isNew()) {
			Number newKey = this.insertActivityType.executeAndReturnKey(
					new BeanPropertySqlParameterSource(activityType));
			activityType.setId(newKey.intValue());
		}
		else {
			this.simpleJdbcTemplate.update(
					"UPDATE activity_types SET name=:name, price=:price, illustration_image_name=:illustrationImageName, " + 
					"short_description=:shortDescription, description=:description WHERE id=:id",
					new BeanPropertySqlParameterSource(activityType));	
		}
	}
	
	/**
	 * Prida noveho uzivatele nebo updatne stavajiciho.
	 */
	@Transactional
	public void storeUser(User user) throws DataAccessException {
		if (user.isNew()) {
			Number newKey = this.insertUser.executeAndReturnKey(createUserParameterSource(user));
			user.setId(newKey.intValue());
		}
		else {
			this.simpleJdbcTemplate.update(
					"UPDATE activity_types SET first_name=:firstName, last_name=:lastName, " +
					"street=:street, city=:city, postcode=:postcode, mail=:mail, telephone=:telephone, " +
					"credit=:credit, description=:description, profile_photo_name=:profilePhotoName, " + 
					"login=:login, password=:password, userRole_id=:userRole_id, is_active=:isActive WHERE id=:id",
					new BeanPropertySqlParameterSource(user));	
		}
	}
	
	/**
	 * Prida novou Lekci nebo updatne stavajici.
	 */
	@Transactional
	public void storeLesson(Lesson lesson) throws DataAccessException {
		if (lesson.isNew()) {
			Number newKey = this.insertLesson.executeAndReturnKey(
					new BeanPropertySqlParameterSource(lesson));
			lesson.setId(newKey.intValue());
		}
		else {
			this.simpleJdbcTemplate.update(
					"UPDATE lessons SET start_time=:startTime, end_time=:endTime, " +
					"activityType_id=:activityType_id, room_id=:room_id, original_capacity=:originalCapacity, " +
					"actual_capacity=:actualCapacity, description=:description, instructor_id=:instructor_id, " +
					"is_active=:isActive, is_reserved=:isReserved WHERE id=:id",
					new BeanPropertySqlParameterSource(lesson));	
		}
	}
	
	/**
	 * Prida novou Rezervaci nebo updatne stavajici.
	 */
	@Transactional
	public void storeReservation(Reservation reservation) throws DataAccessException {
		if (reservation.isNew()) {
			Number newKey = this.insertReservation.executeAndReturnKey(
					new BeanPropertySqlParameterSource(reservation));
			reservation.setId(newKey.intValue());
		}
		else {
			this.simpleJdbcTemplate.update(
					"UPDATE reservations SET reservation_time=:reservationTime, lesson_id=:lesson_id, client_id=:client_id, is_cancellable=:isCancellable WHERE id=:id",
					new BeanPropertySqlParameterSource(reservation));	
		}
	}
	
	@Transactional
	public void storePet(Pet pet) throws DataAccessException {
		if (pet.isNew()) {
			Number newKey = this.insertPet.executeAndReturnKey(createPetParameterSource(pet));
			pet.setId(newKey.intValue());
		}
		else {
			this.simpleJdbcTemplate.update(
					"UPDATE pets SET name=:name, birth_date=:birth_date, type_id=:type_id, " +
					"owner_id=:owner_id WHERE id=:id",
					createPetParameterSource(pet));
		}
	}

	@Transactional
	public void storeVisit(Visit visit) throws DataAccessException {
		if (visit.isNew()) {
			Number newKey = this.insertVisit.executeAndReturnKey(
					createVisitParameterSource(visit));
			visit.setId(newKey.intValue());
		}
		else {
			throw new UnsupportedOperationException("Visit update not supported");
		}
	}

	public void deletePet(int id) throws DataAccessException {
		this.simpleJdbcTemplate.update("DELETE FROM pets WHERE id=?", id);
	}
	
	public void deleteRoom(int id) throws DataAccessException {
		this.simpleJdbcTemplate.update("DELETE FROM rooms WHERE id=?", id);	
	}
	
	public void deleteActivityType(int id) throws DataAccessException {
		this.simpleJdbcTemplate.update("DELETE FROM activity_types WHERE id=?", id);
	}
	
	public void deleteUser(int id) throws DataAccessException {
		this.simpleJdbcTemplate.update("DELETE FROM users WHERE id=?", id);
	}
	
	public void deleteReservation(int id) throws DataAccessException {
		this.simpleJdbcTemplate.update("DELETE FROM reservations WHERE id=?", id);
	}

	// END of Clinic implementation section ************************************


	/**
	 * Creates a {@link MapSqlParameterSource} based on data values from the
	 * supplied {@link Pet} instance.
	 */
	private MapSqlParameterSource createPetParameterSource(Pet pet) {
		return new MapSqlParameterSource()
			.addValue("id", pet.getId())
			.addValue("name", pet.getName())
			.addValue("birth_date", pet.getBirthDate())
			.addValue("type_id", pet.getType().getId())
			.addValue("owner_id", pet.getOwner().getId());
	}
	
	/**
	 * Creates a {@link MapSqlParameterSource} based on data values from the
	 * supplied {@link User} instance.
	 */
	private MapSqlParameterSource createUserParameterSource(User user) {
		return new MapSqlParameterSource()
			.addValue("id", user.getId())
			.addValue("first_name", user.getFirstName())
			.addValue("last_name", user.getLastName())
			.addValue("street", user.getStreet())
			.addValue("city", user.getCity())
			.addValue("postcode", user.getPostcode())
			.addValue("mail", user.getMail())
			.addValue("telephone", user.getTelephone())
			.addValue("credit", user.getCredit())
			.addValue("description", user.getDescription())
			.addValue("profile_photo_name", user.getProfilePhotoName())
			.addValue("login", user.getLogin())
			.addValue("password", user.getPassword())
			.addValue("userRole_id", user.getUserRole().getId());
	}

	/**
	 * Creates a {@link MapSqlParameterSource} based on data values from the
	 * supplied {@link Visit} instance.
	 */
	private MapSqlParameterSource createVisitParameterSource(Visit visit) {
		return new MapSqlParameterSource()
			.addValue("id", visit.getId())
			.addValue("visit_date", visit.getDate())
			.addValue("description", visit.getDescription())
			.addValue("pet_id", visit.getPet().getId());
	}

	/**
	 * Loads the {@link Visit} data for the supplied {@link Pet}.
	 */
	private void loadVisits(JdbcPet pet) {
		final List<Visit> visits = this.simpleJdbcTemplate.query(
				"SELECT id, visit_date, description FROM visits WHERE pet_id=?",
				new ParameterizedRowMapper<Visit>() {
					public Visit mapRow(ResultSet rs, int row) throws SQLException {
						Visit visit = new Visit();
						visit.setId(rs.getInt("id"));
						visit.setDate(rs.getTimestamp("visit_date"));
						visit.setDescription(rs.getString("description"));
						return visit;
					}
				},
				pet.getId().intValue());
		for (Visit visit : visits) {
			pet.addVisit(visit);
		}
	}

	/**
	 * Loads the {@link Pet} and {@link Visit} data for the supplied
	 * {@link Owner}.
	 */
	private void loadPetsAndVisits(final Owner owner) {
		final List<JdbcPet> pets = this.simpleJdbcTemplate.query(
				"SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE owner_id=?",
				new JdbcPetRowMapper(),
				owner.getId().intValue());
		for (JdbcPet pet : pets) {
			owner.addPet(pet);
			pet.setType(EntityUtils.getById(getPetTypes(), PetType.class, pet.getTypeId()));
			loadVisits(pet);
		}
	}

	/**
	 * Loads the {@link Pet} and {@link Visit} data for the supplied
	 * {@link List} of {@link Owner Owners}.
	 *
	 * @param owners the list of owners for whom the pet and visit data should be loaded
	 * @see #loadPetsAndVisits(Owner)
	 */
	private void loadOwnersPetsAndVisits(List<Owner> owners) {
		for (Owner owner : owners) {
			loadPetsAndVisits(owner);
		}
	}

	/**
	 * {@link ParameterizedRowMapper} implementation mapping data from a
	 * {@link ResultSet} to the corresponding properties of the {@link JdbcPet} class.
	 */
	private class JdbcPetRowMapper implements ParameterizedRowMapper<JdbcPet> {

		public JdbcPet mapRow(ResultSet rs, int rownum) throws SQLException {
			JdbcPet pet = new JdbcPet();
			pet.setId(rs.getInt("id"));
			pet.setName(rs.getString("name"));
			pet.setBirthDate(rs.getDate("birth_date"));
			pet.setTypeId(rs.getInt("type_id"));
			pet.setOwnerId(rs.getInt("owner_id"));
			return pet;
		}
	}

}