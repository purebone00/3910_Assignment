package infosys.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import ca.bcit.infosys.timesheet.TimesheetRow;

public class EditableRow extends TimesheetRow implements Serializable {
	
	public BigDecimal getSaturday() {
		return super.getHour(0);
	}
	
	public void setSaturday(BigDecimal hours) {
		super.setHour(0, hours);
	}
	
	public BigDecimal getSunday() {
		return super.getHour(1);
	}
	
	public void setSunday(BigDecimal hours) {
		super.setHour(1, hours);
	}
	
	public BigDecimal getMonday() {
		return super.getHour(2);
	}

	public void setMonday(BigDecimal hours) {
		super.setHour(2, hours);
	}
	
	public BigDecimal getTuesday() {
		return super.getHour(3);
	}

	public void setTuesday(BigDecimal hours) {
		super.setHour(3, hours);
	}
	
	public BigDecimal getWednesday() {
		return super.getHour(4);
	}

	public void setWednesday(BigDecimal hours) {
		super.setHour(4, hours);
	}
	
	public BigDecimal getThursday() {
		return super.getHour(5);
	}

	public void setThursday(BigDecimal hours) {
		super.setHour(5, hours);
	}
	
	public BigDecimal getFriday() {
		return super.getHour(6);
	}

	public void setFriday(BigDecimal hours) {
		super.setHour(6, hours);
	}
}
	
