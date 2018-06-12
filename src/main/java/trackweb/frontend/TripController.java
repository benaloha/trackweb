package trackweb.frontend;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import lombok.Getter;
import lombok.Setter;
import trackweb.model.TripDTO;
import trackweb.service.TripService;

@Named
@ViewScoped
public class TripController implements Serializable{

	private static final long serialVersionUID = 1804468353914632729L;
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
	private List<TripDTO> trips;
	@Getter
	@Setter
	private TripDTO selectedTrip;
    
    public void retrieveTrips() {
    	trips = tripService.getTrips(from, to);
    }
    
	public void onRowSelect(SelectEvent event) throws IOException {
		selectedTrip = (TripDTO) event.getObject();
		tripSession.setPoints(tripService.getRoute(selectedTrip.getStartTime(), selectedTrip.getEndTime()));
	}
}
