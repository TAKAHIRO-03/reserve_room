package mrs.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class ReservableRoomId implements Serializable {
	private Integer roomId;
	private LocalDate reservedDate;

	public ReservableRoomId(Integer roomId, LocalDate reservedDate) {
		this.roomId = roomId;
		this.reservedDate = reservedDate;
	}

	public ReservableRoomId() {
	}

}