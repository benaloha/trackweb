package trackweb.repository;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

	private static DateFormat DATE_FORMATTER;
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
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
		DATE_FORMATTER.setTimeZone(tz);
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
