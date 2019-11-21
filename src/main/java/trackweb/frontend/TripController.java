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

@Named
@ViewScoped
public class TripController implements Serializable{

	private static final long serialVersionUID = 1804468353914632729L;

	@Inject
	private TripSessionBean tripSessionBean;
	@Getter
	@Setter
	private Date from;
	@Getter
	@Setter
	private Date to;
    
    public void retrieveTrips() {
       	tripSessionBean.retrieveTrips(from, to);
    }
    
    public void getAllPoints() {
       	tripSessionBean.getAllPoints(from, to);
    }
    
	public void onTripRowSelect(SelectEvent event) {
		tripSessionBean.setSelectedTrip((TripDTO) event.getObject());
	}
}
