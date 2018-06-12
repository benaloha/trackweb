package trackweb.frontend;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import trackweb.model.PositionDTO;
import trackweb.model.TripDTO;

@Named
@SessionScoped
@Getter
@Setter
public class TripSessionBean implements Serializable{

	private static final long serialVersionUID = 6307292393448463495L;
	private List<TripDTO> trips;
	private TripDTO selectedTrip;
	private List<PositionDTO> points;
}
