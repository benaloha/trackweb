package trackweb.frontend;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

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
	private TripSessionBean tripSessionBean;
	@Getter
	@Setter
	private Date from;
	@Getter
	@Setter
	private Date to;
    
    public void retrieveTrips() {
    	tripSessionBean.setTrips(tripService.getTrips(from, to));
    }
    
	public void onTripRowSelect(SelectEvent event) throws IOException {
		tripSessionBean.setSelectedTrip((TripDTO) event.getObject());
		tripSessionBean.setPoints(tripService.getRoute(
				tripSessionBean.getSelectedTrip().getStartTime(),
				tripSessionBean.getSelectedTrip().getEndTime()));
	}
}
