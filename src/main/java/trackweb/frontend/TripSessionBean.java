package trackweb.frontend;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import trackweb.model.PositionDTO;
import trackweb.model.TripDTO;
import trackweb.service.TripService;

@Named
@SessionScoped
@Getter
public class TripSessionBean implements Serializable{

	private static final long serialVersionUID = 6307292393448463495L;
	@Inject
	private TripService tripService;
	private List<TripDTO> trips;
	private TripDTO selectedTrip;
	private List<PositionDTO> points;
	private String totalTime;
	private String avgSpeed;
	private String totalDistance;
	private String avgAccuPercentage;
	private String avgAltitude;
    
	public void setSelectedTrip(TripDTO selectedTrip) {
		if (selectedTrip != null) {
			this.selectedTrip = selectedTrip;
			points = tripService.getRoute(getSelectedTrip().getStartTime(),
					getSelectedTrip().getEndTime());
			calculateSummary();
		}
	}
	
	public void retrieveTrips(Date from, Date to) {
		if (from != null && to != null) {
	    	trips = tripService.getTrips(from, to);			
		}
		else {
			trips = null;
		}
	}
	
    private void calculateSummary() {
    	int size = getPoints().size();
    	if (size == 0) return;

    	PositionDTO firstP = getPoints().get(0);
    	PositionDTO lastP = getPoints().get(size-1);
    	Duration duration = Duration.ofMillis(lastP.getDeviceTime().getTime()-firstP.getDeviceTime().getTime());
		double totalDistanceDouble = lastP.getTotalDistance() - firstP.getTotalDistance();
		double avgSpeedDouble = totalDistanceDouble / duration.getSeconds() * 3600;
		double avgAccuPercentageDouble = getPoints().stream().mapToDouble(PositionDTO::getBatteryLevel).average().orElse(0);
		double avgAltitudeDouble = getPoints().stream().mapToDouble(PositionDTO::getAltitude).average().orElse(0);

		
		totalTime = duration.toString().replace("PT", "");
		avgSpeed = String.format("%.1f", avgSpeedDouble);
		totalDistance = String.format("%.1f", totalDistanceDouble);
		avgAccuPercentage = String.format("%.0f", avgAccuPercentageDouble);
		avgAltitude = String.format("%.5f", avgAltitudeDouble);
	}
}
