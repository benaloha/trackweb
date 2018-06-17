package trackweb.frontend;

import java.io.Serializable;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import trackweb.model.PositionDTO;

@Named
@ViewScoped
public class TripMapController implements Serializable{

	private static final long serialVersionUID = 6307292393448463495L;
	@Inject
	private TripSessionBean tripSessionBean;
	@Getter
	private String route;
    
	@PostConstruct
    public void init(){
		if (tripSessionBean.getPoints()!=null) {
			
			StringBuilder sb = new StringBuilder("[");
			
	    	for(PositionDTO point: tripSessionBean.getPoints().stream().sorted().collect(Collectors.toList())) {
	    		sb.append("['")
	    		.append(point.getDeviceTime().toString()).append("', ")
	    		.append(point.getLatitude()).append(", ")
	    		.append(point.getLongitude()).append(", ")    		
	    		.append(point.getSpeed())
	    		.append("],");
	    	}
	    	sb.deleteCharAt(sb.length()-1).append("]");
	    	route = sb.toString();
		}
    }
    
}
