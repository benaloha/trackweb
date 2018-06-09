package trackweb.model;

import org.traccar.api.model.TripReport;

public class TripDTO extends TripReport implements Comparable<TripDTO> {

	@Override
	public int compareTo(TripDTO o) {
		return super.getStartTime().compareTo(o.getStartTime());
	}
	
	public static TripDTO tripConverter(TripReport report) {
		return (TripDTO) report;
	}
}
