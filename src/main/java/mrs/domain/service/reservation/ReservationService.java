package mrs.domain.service.reservation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import mrs.domain.model.Reservation;
import mrs.domain.repository.reservation.ReservationRepository;
import mrs.domain.repository.room.ReservableRoomRepository;

@Service
@Transactional
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final ReservableRoomRepository reservableRoomRepository;

	@Autowired
	public ReservationService(ReservationRepository reservationRepository,
							  ReservableRoomRepository reservableRoomRepository) {
		this.reservationRepository = reservationRepository;
		this.reservableRoomRepository = reservableRoomRepository;
	}

	public List<Reservation> findReservations(ReservableRoomId reservableRoomId){
		return reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
	}

	public Reservation reserve(Reservation reservation) {
		ReservableRoomId reservableRoomId = reservation.getReservableRoom().getReservableRoomId();
		ReservableRoom reservable = reservableRoomRepository.getOneForUpdateByReservableRoomId(reservableRoomId);
		if(reservable == null) {
			throw new UnavailableReservationException("入力の日付・部屋の組み合わせは予約出来ません。");
		}
		boolean overlap = reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId)
											   .stream()
											   .anyMatch(x -> x.overlap(reservation));
		if(overlap) {
			throw new AlreadyReservedException("入力の時間帯は既に予約済みです。");
		}

		return 	reservationRepository.save(reservation);
	}

	@SuppressWarnings("deprecation")
	@PreAuthorize("hasRole('ADMIN') or #reservation.user.userId == principal.user.userId")
	public void cancel(@P("reservation") Reservation reservation) {
		reservationRepository.delete(reservation);
	}

	public Reservation getOne(Integer reservationId) {
		return reservationRepository.getOne(reservationId);
	}

}
