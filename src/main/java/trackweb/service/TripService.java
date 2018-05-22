package trackweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Service;
import org.traccar.api.TraccarApiClient;
import org.traccar.api.model.Position;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TripService {

	private final static FastDateFormat DATE_FORMATTER = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT;

	public List<Position> getRoute(Date from, Date to) {
		List<Position> list = new ArrayList<>();

		String url = String.format("http://192.168.2.12:8082/api/reports/route?deviceId=1&from=%s&to=%s",
				DATE_FORMATTER.format(from), DATE_FORMATTER.format(to));

		Optional<String> response = new TraccarApiClient().get(url);

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
