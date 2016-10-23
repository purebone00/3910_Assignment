package infosys.beans;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.TimesheetRow;
/**
 * The pseudo-database that holds information on all currently stores timesheets.
 * Will iteract with an actual database in the future, and will act as in 
 * intermediary between the database and the front-end JSF page.
 * @author Albert
 *
 */
@ApplicationScoped
public class TimeSheetCollector {
    
	/** A list that holds all timesheets that have been made. */
    List<EditableTimesheet> timesheets = new ArrayList<EditableTimesheet>();
    
    /** the timesheet that is being displayed. */
    EditableTimesheet currentTimesheet;
              
    /**
     * Gets all currently stores timesheets.
     * @return timesheets
     */
    public List<EditableTimesheet> getTimesheets() {
        return timesheets;
    }

    /**
     * Adds a new timesheet that is linked to a specific Employee.
     * @param employee
     * @return null to refresh the page.
     */
    public String addTimesheet(Employee employee) {
    	EditableTimesheet temp = new EditableTimesheet(employee, getNextFriday(), createNewRows());    	
        timesheets.add(temp);
        setCurrentTimesheet(temp);
        return null;
    }
    
    /**
     * Creates a list of 5 new rows.
     * @return ArrayList of timesheetRows
     */
    public List<TimesheetRow> createNewRows() {
    	List<TimesheetRow> newRows = new ArrayList<TimesheetRow>();
    	newRows.add(new EditableRow());
    	newRows.add(new EditableRow());
    	newRows.add(new EditableRow());
    	newRows.add(new EditableRow());
    	newRows.add(new EditableRow());
    	return newRows;
    }
    
    /**
     * Adds a blank timesheet.
     * @return null to refresh the page.
     */
    public String addTimesheet() {
    	EditableTimesheet temp = new EditableTimesheet();    
		timesheets.add(temp);
        setCurrentTimesheet(temp);
		return null;
	}
    
	/**
	 * Gets currentTimesheet
	 * @return currentTimesheet
	 */
	public EditableTimesheet getCurrentTimesheet() {
		if (currentTimesheet == null) {
			addTimesheet();
		}
		return currentTimesheet;
	}
	
	/**
	 * Gets currentTimesheet that belongs to employee
	 * @param employee that is currently on the page
	 * @return Timesheet associated with that employee
	 */
	public EditableTimesheet getCurrentTimesheet(Employee employee) {
		boolean hasTimesheet = false;
		for (EditableTimesheet x : timesheets) {
			if (x.getEmployee().getEmpNumber() == employee.getEmpNumber()) {
				currentTimesheet = x;
				hasTimesheet = true;
			}
		}
		if (currentTimesheet == null || !hasTimesheet) {
			addTimesheet(employee);
		}
		return currentTimesheet;
	}

	/**
	 * Updates the currentTimesheet to the appropriate one.
	 * @param currentTimesheet
	 */
	public void setCurrentTimesheet(EditableTimesheet currentTimesheet) {
		this.currentTimesheet = currentTimesheet;
	}	
	
	/**
	 * Calculates the date for the NextFriday
	 * @return Date of next friday
	 */
	public static Date getNextFriday() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
    	Date now = new Date();
    	cal.setTime(now);
    	int week = cal.get(Calendar.DAY_OF_WEEK);
    	return new Date(now.getTime() - 24 * 60 * 60 * 1000 * (week - 6));
    }

    

}
