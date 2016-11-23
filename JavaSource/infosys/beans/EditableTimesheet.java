package infosys.beans;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetRow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
//import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


/**
 * Expands on the existing timesheet to be able to hold if the timesheet
 * is being edited.
 * 
 * @author Albert
 *
 */
public class EditableTimesheet extends Timesheet {

    /** Sees if a time sheet is editable. For now it is always true. */
    private boolean isEditable;
    
    /** Saves the state on if someone is currently editing this timesheet. */
    private boolean editing;
    
    private int timesheetID;
    
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
     * @param newEmployee the employee
     * @param end  the ending of the current week
     * @param list the details in each of the rows.
     */
    public EditableTimesheet(final Employee newEmployee, final Date end,
            final List<TimesheetRow> list) {
        super(newEmployee, end, list);
        
        isEditable = true;
        editing = false;
    }
    
    /**
     * Returns current state of editing.
     * @return state of editing.
     */
    public boolean isEditing() {
        return editing;
    }
    
    /**
     * Sets current state of editing.
     * @param editing.
     */
    public void setEditing(boolean editing) {
        this.editing = editing;
    }
    
    /**
     * Returns Whether or not this page is editable.
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
     * My attempt to check for row uniqueness.
     * @param context.
     * @param component.
     * @param value.
     */
    public void checkRow(FacesContext context, UIComponent component, Object value) {
    
        //UIInput dayInput = (UIInput) component.findComponent("day");
        //UIInput monthInput = (UIInput) component.findComponent("month");
    
        List<String> checkForUnique = new ArrayList<String>();
        for (TimesheetRow x : getDetails()) {
            checkForUnique.add(new String(x.getProjectID() + x.getWorkPackage()));
        }
        
        for (int i = 0 ; i < checkForUnique.size() ; i++) {
            for (int j = 1 ; j < checkForUnique.size() ; j++) {
                if (checkForUnique.get(i).equals(checkForUnique.get(j))) {
                    throw new ValidatorException(
                            new FacesMessage("ProjectID and WorkPackage have to be unique"));
                }
            }
        }

    }

	public int getTimesheetID() {
		return timesheetID;
	}

	public void setTimesheetID(int timesheetID) {
		this.timesheetID = timesheetID;
	}
        
    
}
