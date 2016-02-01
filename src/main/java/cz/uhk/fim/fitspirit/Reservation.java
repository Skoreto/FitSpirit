package cz.uhk.fim.fitspirit;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="RESERVATIONS")
public class Reservation extends BaseEntity {

	private Timestamp reservationTime;
	
	private Lesson lesson;
	
	// Klient jako vlastn�k dan� rezervace.
	private User client;
	
	// Pomocn� vlastnost na ozna�en� rezervac�, kter� ji� nelze zru�it z d�vod� vnit�n� politiky fitness centra.
	// Nap�. 6 hodin p�ed zah�jen�m lekce klient ji� nem��e zru�it rezervaci lekce.
	private boolean isCancellable;

	@Column(name="RESERVATION_TIME")
	public Timestamp getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(Timestamp reservationTime) {
		this.reservationTime = reservationTime;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	// Atribut by nebylo potreba vest v databazi, ale dochazi ke kolizi s Hibernate.
	@Column(name="IS_CANCELLABLE")
	public boolean isCancellable() {
		return isCancellable;
	}

	public void setCancellable(boolean isCancellable) {
		this.isCancellable = isCancellable;
	}
	
}
