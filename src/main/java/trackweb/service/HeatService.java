package trackweb.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import trackweb.repository.HeatRepository;

@Service
@Log4j2
public class HeatService {

	/*
	 * Q = m * c * deltaT
	 * 
	 * kg/s * kJ/(kg*K) * K = kJ/s = kW.
	 */

	private static final double FLOW_HOT_WATER = 0.07; // 250 l/h
	private static final double FLOW_HEATING = 0.1; // 360 l/h
	private static final double SPECIFIC_HEAT = 4.18; // kJ/(kg*K)
	private static final double TRESHOLD_TEMP_HEATING_ON = 55; // degrees Celsius
	private static final double TRESHOLD_TEMP_HOT_WATER_ON = 40; // degrees Celsius
	private static final double TRESHOLD_DELTA_TEMP_EXCHANGER_ON = 15; // K

	private double deltaT;

	@Inject
	private HeatRepository heatRepo;

	// in kW.
	public double getHeatingPower() {
		heatRepo.readValues();
		return isHeatingOn() ? FLOW_HEATING * SPECIFIC_HEAT * deltaT : 0;
	}

	// in kW.
	public double getHotWaterPower() {
		heatRepo.readValues();		
		return isHotWaterOn() ? FLOW_HOT_WATER * SPECIFIC_HEAT * deltaT : 0;
	}

	private boolean isExchangerOn() {
		boolean value = getDeltaT() > TRESHOLD_DELTA_TEMP_EXCHANGER_ON;
		log.error("isExchangeOn : {}", value);
		return value;
	}

	private boolean isHeatingOn() {
		boolean value = heatRepo.getTempHeatingIn() > TRESHOLD_TEMP_HEATING_ON && isExchangerOn();
		log.error("isHeatingOn : {}", value);
		return value;

	}

	private boolean isHotWaterOn() {
		boolean value = heatRepo.getTempHotWater() > TRESHOLD_TEMP_HOT_WATER_ON && isExchangerOn();
		log.error("isHotWaterOn : {}", value);
		return value;
	}

	private double getDeltaT() {
		deltaT = heatRepo.getTempHeatExchangeIn() - heatRepo.getTempHeatExchangeOut() + 5.0;
		// 5 graden compensatie omdat ExchIn eigenlijk 70 gr. is, ik meet maar 60 gr. C.
		log.error("===================================");
		log.error("deltaT={}K.", deltaT);
		return deltaT;
	}
}
