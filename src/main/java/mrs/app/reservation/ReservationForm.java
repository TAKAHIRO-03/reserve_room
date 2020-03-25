package mrs.app.reservation;

import java.io.Serializable;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import mrs.domain.model.validator.EndTimeMustBeAfterStartTime;
import mrs.domain.model.validator.ThirtyMinutesUnit;

@Data
@EndTimeMustBeAfterStartTime(message = "終了時刻は開始時間よりも後にして下さい")
public class ReservationForm implements Serializable{
	@NotNull(message = "必須です")
	@DateTimeFormat(pattern = "HH:mm")
	@ThirtyMinutesUnit(message = "30分単位で入力して下さい")
	private LocalTime startTime;

	@NotNull(message = "必須です")
	@DateTimeFormat(pattern = "HH:mm")
	@ThirtyMinutesUnit(message = "30分単位で入力して下さい")
	private LocalTime endTime;
}
