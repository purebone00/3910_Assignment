package infosys.beans;

import ca.bcit.infosys.access.TimesheetManager;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.TimesheetRow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


/**
 * The pseudo-database that holds information on all currently stores
 * timesheets. Will iteract with an actual database in the future,
 * and will act as in.
 * intermediary between the database and the front-end JSF page.
 * @author Albert
 *
 */

@ApplicationScoped
public class TimeSheetCollector {

    @Inject TimesheetManager timesheetManager;

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
     * @param employee adding a timesheet.
     * @return null to refresh the page.
     */
    public String addTimesheet(Employee employee) {
        EditableTimesheet temp = new EditableTimesheet(employee, getNextFriday(), createNewRows(0));
        timesheetManager.create(temp);
        setCurrentTimesheet(temp);
        temp.setDetails(createNewRows());
        timesheets.add(temp);
        return null;
    }

    /**
     * Adds a blank timesheet.
     * @return null to refresh the page.
     */
    public String addTimesheet() {
        EditableTimesheet temp = new EditableTimesheet(); 
        timesheetManager.create(temp);
        timesheets.add(temp);
        setCurrentTimesheet(temp);
        temp.setDetails(createNewRows());
        return null;
    }

    /**
     * Creates a list of 5 new rows.
     * @return ArrayList of timesheetRows
     */
    public ArrayList<TimesheetRow> createNewRows() {
        ArrayList<TimesheetRow> newRows = new ArrayList<TimesheetRow>();
        EditableRow newRow = new EditableRow();
        newRows.add(newRow);
        timesheetManager.createRow(newRow, currentTimesheet.getTimesheetId());
        newRow = new EditableRow();
        newRows.add(newRow);
        timesheetManager.createRow(newRow, currentTimesheet.getTimesheetId());
        newRow = new EditableRow();
        newRows.add(newRow);
        timesheetManager.createRow(newRow, currentTimesheet.getTimesheetId());
        newRow = new EditableRow();
        newRows.add(newRow);
        timesheetManager.createRow(newRow, currentTimesheet.getTimesheetId());
        newRow = new EditableRow();
        newRows.add(newRow);
        timesheetManager.createRow(newRow, currentTimesheet.getTimesheetId());
        return newRows;
    }
    
    /**
     * Creates a list of 5 blank rows.
     * @return ArrayList of timesheetRows
     */
    public ArrayList<TimesheetRow> createNewRows(int num) {
        ArrayList<TimesheetRow> newRows = new ArrayList<TimesheetRow>();
        EditableRow newRow = new EditableRow();
        newRows.add(newRow);
        timesheetManager.createRow(newRow, num);
        newRow = new EditableRow();
        newRows.add(newRow);
        timesheetManager.createRow(newRow, num);
        newRow = new EditableRow();
        newRows.add(newRow);
        timesheetManager.createRow(newRow, num);
        newRow = new EditableRow();
        newRows.add(newRow);
        timesheetManager.createRow(newRow, num);
        newRow = new EditableRow();
        newRows.add(newRow);
        timesheetManager.createRow(newRow, num);
        return newRows;
    }

    /**
     * Gets the currentTimesheet.
     * @return currentTimesheet
     */
    public EditableTimesheet getCurrentTimesheet() {
        if (currentTimesheet == null) {
            addTimesheet();
        }
        return currentTimesheet;
    }

    /**
     * Gets currentTimesheet that belongs to employee.
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
     * @param currentTimesheet selected.
     */
    public void setCurrentTimesheet(EditableTimesheet currentTimesheet) {
        this.currentTimesheet = currentTimesheet;
    }

    /**
     * Calculates the date for the NextFriday.
     * @return Date of next Friday
     */
    public static Date getNextFriday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
        Date now = new Date();
        cal.setTime(now);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        return new Date(now.getTime() - 24 * 60 * 60 * 1000 * (week - 6));
    }

    public void setTimesheets() {
        timesheets = timesheetManager.getAll();
    }

    /**
     * updates the database with the new details.
     */
    public void update() {
        for (TimesheetRow row : currentTimesheet.getDetails()) {
            timesheetManager.update((EditableRow) row);
        }
    }

    /**
     * Adds a new row to the timesheet.
     */
    public void addRow() {
        EditableRow newRow = new EditableRow();
        timesheetManager.createRow(newRow, currentTimesheet.getTimesheetId());
        currentTimesheet.getDetails().add(newRow);
    }

}
