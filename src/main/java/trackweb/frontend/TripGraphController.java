package trackweb.frontend;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

import lombok.Getter;
import trackweb.model.PositionDTO;

@Named
@ViewScoped
public class TripGraphController implements Serializable{

	private static final long serialVersionUID = 6307292393448463495L;
	@Inject
	private TripSessionBean tripSessionBean;
	@Getter
	private LineChartModel dateModel;
    private LineChartSeries altitudeLine, speedLine;
    private DateAxis xAxis;

	@PostConstruct
    public void init() {
        initDateModel();
        createLines();
    }
     
    private void createLines() {
    	List<PositionDTO> points = tripSessionBean.getPoints();
		if (points!=null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); 
			for (PositionDTO point : points) {
				String time = dateFormat.format(point.getDeviceTime());
				speedLine.set(time, Double.valueOf(point.getSpeed()));
				altitudeLine.set(time, Double.valueOf(point.getAltitude()));
			}
			xAxis.setMax(dateFormat.format(points.get(points.size()-1).getDeviceTime()));
		}
	}

	private void initDateModel() {
        dateModel = new LineChartModel();
        dateModel.setLegendPosition("ne");
        
        speedLine = new LineChartSeries();
        speedLine.setLabel("Speed");
	    dateModel.addSeries(speedLine);
 
        altitudeLine = new LineChartSeries();
        altitudeLine.setXaxis(AxisType.X2);
        altitudeLine.setYaxis(AxisType.Y2);
        altitudeLine.setLabel("Altitude");
        dateModel.addSeries(altitudeLine);
        
        dateModel.setTitle("Speed versus Altitude");
        dateModel.setZoom(true);
         
        Axis yAxis = dateModel.getAxis(AxisType.Y);
        yAxis.setLabel("Speed [km/h]");
                 
        Axis y2Axis = new LinearAxis("Altitude [m]");
        dateModel.getAxes().put(AxisType.Y2, y2Axis);
        
        xAxis = new DateAxis("Time");
        xAxis.setTickAngle(-50);
        xAxis.setTickFormat("%H:%#M:%S");
        dateModel.getAxes().put(AxisType.X, xAxis);
        dateModel.getAxes().put(AxisType.X2, xAxis);
	}
    
}
