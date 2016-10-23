package infosys.beans;

import java.util.Date;
import java.util.List;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetRow;

/**
 * Expands on the existing timesheet to be able to hold if the timesheet
 * is being edited.
 * 
 * @author Albert
 *
 */
public class EditableTimesheet extends Timesheet {
	
	/**	Sees if a time sheet is editable. For now it is always true. */
	private boolean isEditable;

	/** Saves the state on if someone is currently editing this timesheet */
	private boolean editing;
	
	/**
	 * Constructor for a blank timesheet.
	 * Adds 5 new rows.
	 */
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
	
	/**
	 * Constructor for a Timesheet linked with an employee.
	 * @param user the employee
	 * @param end  the ending of the current week
	 * @param list the details in each of the rows.
	 */
	public EditableTimesheet(final Employee user, final Date end,
            final List<TimesheetRow> list) {
		
		super(user, end, list);

		isEditable = true;
		editing = false;
	}
	
	/**
	 * Returns current state of editing.
	 * @return state of editing
	 */
	public boolean isEditing() {
		return editing;
	}

	/**
	 * Sets current state of editing.
	 * @param editing
	 */
	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	/**
	 * Returns whether of not this page is editable.
	 * @return editable
	 */
	public boolean isEditable() {
		return isEditable;
	}

	/**
	 * Sets whether or not this page will be editable in the future.
	 * @param isEditable state
	 */
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	/**
	 * Flips the state of editing.
	 */
	public void editPage() {
		if (isEditable()) {
			setEditing(!isEditing());
		}
	}
	
	/**
	 * returns the appropriate string for the button.
	 * @return Save or Edit
	 */
	public String amIEditing() {
		if (editing) {
			return "Save";
		}
		return "Edit";
	}
	
	/**
	 * Adds a new row to the timesheet.
	 */
    public void addRow() {
        super.getDetails().add(new EditableRow());
    }
    
    
	
	
}
