package infosys.beans;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetCollection;
import ca.bcit.infosys.timesheet.TimesheetRow;

@ApplicationScoped
public class TimeSheetCollector implements TimesheetCollection {
    
    List<Timesheet> timesheets = new ArrayList<Timesheet>();
    EditableTimesheet currentTimesheet;
    
    TimeSheetCollector() {
    	
    }
        
    public List<Timesheet> getTimesheets() {
        return timesheets;
    }

    public List<Timesheet> getTimesheets(Employee e) {
    	List<Timesheet> temp = new ArrayList<Timesheet>();
    	for (Timesheet x : timesheets) {
    		
    	}
    	return temp;
    }

    public String addTimesheet(Employee e) {
    	List<TimesheetRow> newRows = new ArrayList<TimesheetRow>(5);
    	EditableTimesheet temp = new EditableTimesheet(e, getNextFriday(), newRows);    	
        timesheets.add(temp);
        setCurrentTimesheet(temp);
        return null;
    }
    
    public static Date getNextFriday() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
    	Date now = new Date();
    	cal.setTime(now);
    	int week = cal.get(Calendar.DAY_OF_WEEK);
    	return new Date(now.getTime() - 24 * 60 * 60 * 1000 * (week - 6));
    }

	public Timesheet getCurrentTimesheet(Employee e) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addTimesheet() {
    	EditableTimesheet temp = new EditableTimesheet();    
		timesheets.add(temp);
        setCurrentTimesheet(temp);
		return null;
	}
	
	public void setCurrentTimesheet(Timesheet timesheet) {
		currentTimesheet = (EditableTimesheet) timesheet;
	}

    

}
