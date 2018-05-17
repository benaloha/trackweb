package trackweb.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import trackweb.model.Position;

@Service
@Log4j2
public class LocationService {

	public Collection<Position> getRoute(Date from, Date to){
		//TODO
		return null;
	}
	
}
