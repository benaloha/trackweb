package trackweb.model;

import java.util.Date;

import org.traccar.api.model.Position;

import lombok.Data;

@Data
public class PositionDTO implements Comparable<PositionDTO>{

    private long id; 
    private Date deviceTime;
    private double latitude;
    private double longitude;
    private double altitude; // value in meters
    private String address;
    private double speed; // conversion from knots to km/h
    private double distance; // meters
    private double totalDistance; // meters to km's.
    private double batteryLevel; //percentage.
    private boolean motion;
    
    public static PositionDTO positionConverter(Position pos) {
    	PositionDTO dto = new PositionDTO();
    	
    	dto.setId(pos.getId());
    	dto.setDeviceTime(pos.getDeviceTime());
    	dto.setLatitude(pos.getLatitude());
    	dto.setLongitude(pos.getLongitude());
    	dto.setAltitude(pos.getAltitude());
    	dto.setAddress(pos.getAddress());
    	dto.setSpeed(1.852 * pos.getSpeed());
    	dto.setDistance(pos.getDouble(Position.KEY_DISTANCE));
    	dto.setTotalDistance(pos.getDouble(Position.KEY_TOTAL_DISTANCE)/1000);
    	dto.setBatteryLevel(pos.getDouble(Position.KEY_BATTERY_LEVEL));
    	dto.setMotion(pos.getBoolean(Position.KEY_MOTION));
    	
    	return dto;
    }

	@Override
	public int compareTo(PositionDTO other) {
		return deviceTime.compareTo(other.getDeviceTime());
	}
    
}
