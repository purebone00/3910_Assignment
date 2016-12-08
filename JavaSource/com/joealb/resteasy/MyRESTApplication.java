package com.joealb.resteasy;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;




@ApplicationPath("/*")
public class MyRESTApplication  extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public MyRESTApplication() {
		singletons.add(new TestService());
		singletons.add(new AdminResource());
		singletons.add(new EmployeeResource());
		singletons.add(new TimesheetResource());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}