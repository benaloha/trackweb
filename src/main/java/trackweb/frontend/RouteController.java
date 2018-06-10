package trackweb.frontend;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import lombok.Getter;
import lombok.Setter;
import trackweb.model.PositionDTO;
import trackweb.service.TripService;

@Named
@ViewScoped
public class RouteController implements Serializable{

	private static final long serialVersionUID = 6307292393448463495L;
	@Inject
	private TripService tripService;
	@Inject
	private TripSessionBean tripSession;
	@Getter
	@Setter
	private Date from;
	@Getter
	@Setter
	private Date to;
	@Getter
	private String totalTime;
	@Getter
	private String avgSpeed;
	@Getter
	private String totalDistance;
	@Getter
	private String avgAccuPercentage;
	@Getter
	private String avgAltitude;

				
	public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    public void submit() {
    	List<PositionDTO> list = tripService.getRoute(from, to);
    	if (list != null && !list.isEmpty()) {
        	tripSession.setPoints(list);
        	calculateSummary();
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

		
		totalTime = duration.toString();
		avgSpeed = String.format("%.1f", avgSpeedDouble);
		totalDistance = String.format("%.1f", totalDistanceDouble);
		avgAccuPercentage = String.format("%.0f", avgAccuPercentageDouble);
		avgAltitude = String.format("%.5f", avgAltitudeDouble);
	}

	public List<PositionDTO> getPoints(){
    	return tripSession.getPoints();
    }
}
