package infosys.beans;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import ca.bcit.infosys.access.Database;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.TimesheetRow;


/**
 * Our testing environment.
 * @author Joe Fong, Albert Chen
 *
 */
public class TestUnit {
    /**
     * Main.
     * @param args.
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException {
        
        
        
        
        /*
        Database db = new Database();
        
        ArrayList<Employee> e = new ArrayList<Employee>();
        
        e = db.getResultsEmployee();
        System.out.println(e.size());
        
        for(Employee x:e) {
            System.out.println(x.getName());
            System.out.println(x.getPassword());
            System.out.println();
        }
        
        ArrayList<TimesheetRow> t = new ArrayList<>();
        t = db.getResultsTimeSheet(1);
        
        for(TimesheetRow x: t) {
            System.out.println(x.getProjectID());
            System.out.println(x.getWorkPackage());
            BigDecimal[] bd = x.getHoursForWeek();
            for(BigDecimal p: bd) {
                System.out.println(p);
            }
            System.out.println(x.getNotes());
        }
        
        EditableTimesheet et = db.getTimesheet(2);
        
        System.out.println(et.getEmployee().getName());
        System.out.println(et.getWeekEnding());
        for(TimesheetRow x : et.getDetails()) {
            System.out.println(x.getProjectID());
            System.out.println(x.getWorkPackage());
            BigDecimal[] bd = x.getHoursForWeek();
            for(BigDecimal p: bd) {
                System.out.println(p);
            }
        }
        */
        
    }

}
