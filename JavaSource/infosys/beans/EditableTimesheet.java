package infosys.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import ca.bcit.infosys.timesheet.Timesheet;

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
	
	
}
