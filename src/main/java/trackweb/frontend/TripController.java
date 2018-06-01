package trackweb.frontend;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
public class TripController implements Serializable{

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
		
	public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    public void submit() {
    	tripSession.setPoints(tripService.getRoute(from, to));
    }
    
    public List<PositionDTO> getPoints(){
    	return tripSession.getPoints();
    }
}
