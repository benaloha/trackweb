package trackweb.model;

import java.time.Duration;
import java.util.Date;

import org.traccar.api.model.TripReport;

import lombok.Data;

@Data
public class TripDTO implements Comparable<TripDTO> {

	private long id;
	private Date startTime;
	private String startAddress;
	private Date endTime;
	private String endAddress;
	private double averageSpeed;
	private double maxSpeed;
	private double distance;
	private Duration duration;
	
	@Override
	public int compareTo(TripDTO o) {
		return getStartTime().compareTo(o.getStartTime());
	}
	
	public static TripDTO tripConverter(TripReport report) {
		TripDTO dto = new TripDTO();

		dto.setId(report.getStartPositionId());
		dto.setStartTime(report.getStartTime());
		dto.setStartAddress(report.getStartAddress());
		dto.setEndTime(report.getEndTime());
		dto.setEndAddress(report.getEndAddress());
		dto.setAverageSpeed(1.852 * report.getAverageSpeed()); //knots to km/h
		dto.setMaxSpeed(1.852 * report.getMaxSpeed()); //idem
		dto.setDistance(report.getDistance()/1000);
		dto.setDuration(Duration.ofMillis(report.getDuration()));
		
		return dto;
	}
}
