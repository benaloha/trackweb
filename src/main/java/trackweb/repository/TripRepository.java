package trackweb.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.traccar.api.TraccarApiClient;
import org.traccar.api.model.Position;
import org.traccar.api.model.TripReport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TripRepository {

	private final static FastDateFormat DATE_FORMATTER = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT;
	@Value("${org.traccar.api.host}")
	private String host;
	@Value("${org.traccar.api.port}")
	private String port;
	@Value("${org.traccar.api.user}")
	private String user;
	@Value("${org.traccar.api.password}")
	private String password;
	@Value("${org.traccar.api.route.url}")
	private String routePartUrl;
	@Value("${org.traccar.api.trips.url}")
	private String tripsPartUrl;
	private TraccarApiClient apiClient;
	private String contextRoot;

	@PostConstruct
	private void init() {
		apiClient = new TraccarApiClient(user, password);
		contextRoot = String.format("http://%s:%s", host, port);
	}
	
	public List<TripReport> getTrips(Date from, Date to) {
		List<TripReport> list = new ArrayList<>();

		String url = String.format(contextRoot + tripsPartUrl, DATE_FORMATTER.format(from), DATE_FORMATTER.format(to));

		Optional<String> response = apiClient.get(url);

		if (!response.isPresent()) {
			return list;
		}

		try {
			list = new ObjectMapper().readValue(response.get(), new TypeReference<List<TripReport>>(){});
		} catch (IOException e) {
			log.error("Json response not mapped to List<Position>.");
			log.error(e.getMessage());
		}
		return list;
	}

	public List<Position> getRoute(Date from, Date to) {
		List<Position> list = new ArrayList<>();
	
		String url = String.format(contextRoot + routePartUrl, DATE_FORMATTER.format(from), DATE_FORMATTER.format(to));
	
		Optional<String> response = apiClient.get(url);
	
		if (!response.isPresent()) {
			return list;
		}
	
		try {
			list = new ObjectMapper().readValue(response.get(), new TypeReference<List<Position>>(){});
		} catch (IOException e) {
			log.error("Json response not mapped to List<Position>.");
			log.error(e.getMessage());
		}
		return list;
	}

}
