package infosys.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetRow;

public class EditableTimesheet extends Timesheet {
	
	boolean isEditable;

	boolean editing;
	
	public EditableTimesheet() {
		super();
		isEditable = true;
		editing = false;
		this.addRow();
		this.addRow();
		this.addRow();
		this.addRow();
		this.addRow();
	}
	
	public EditableTimesheet(final Employee user, final Date end,
            final List<TimesheetRow> charges) {
		
		super(user, end, charges);

		isEditable = true;
		editing = false;
	}
	
		
	public boolean isEditing() {
		return editing;
	}


	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	public void editPage() {
		if (isEditable()) {
			setEditing(!isEditing());
		}
	}
	
	public String amIEditing() {
		if (editing) {
			return "Save";
		}
		return "Edit";
	}
	
}
