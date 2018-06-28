package org.traccar.api;

import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

@Log4j2
public class TraccarApiClient {

	private OkHttpClient client;
	private Builder requestBuilder;

	public TraccarApiClient(String authHeader) {
		client = new OkHttpClient();
		requestBuilder = new Request.Builder().addHeader("Accept", "application/json")
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", authHeader)
				.addHeader("Cache-Control", "no-cache");
	}

	public Optional<String> get(String url) {
		Optional<String> responseOptional = Optional.empty();
		try {
			Request request = requestBuilder.url(url).get().build();
			log.debug("Request to Traccar API: " + request.toString());

			Response response = client.newCall(request).execute();
			log.debug("HTTP response code from Traccar API: " + response.code());

			if (response.isSuccessful()) {
				String stringResponse = response.body().string();
				log.debug("Response from Traccar API: " + stringResponse);
				responseOptional = Optional.of(stringResponse);
			}
			return responseOptional;
		} catch (Exception e) {
			log.error("Exception during get to Traccar Api.");
			log.error(e.getMessage());
			return responseOptional;
		}
	}

}
