package trackweb.frontend;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import trackweb.model.PositionDTO;

@Named
@SessionScoped
public class TripSessionBean implements Serializable{

	private static final long serialVersionUID = 6307292393448463495L;
	@Getter
	@Setter
	private List<PositionDTO> points;
}
