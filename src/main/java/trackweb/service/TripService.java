package trackweb.service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import trackweb.model.PositionDTO;
import trackweb.repository.TripRepository;

@Service
public class TripService {

@Inject
private TripRepository tripRepos;
	
	public List<PositionDTO> getRoute(Date from, Date to) {
		return tripRepos.getRoute(from, to)
				.stream()
				.map(p -> PositionDTO.positionConverter(p))
				.collect(Collectors.toList());
	}

}
