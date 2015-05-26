package com.manager.sys.action;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.google.gson.Gson;

public class AbstractAction {

	protected final SimpleDateFormat FORMAT = new SimpleDateFormat("yy-MM-dd hh:mm:ss");

	public Timestamp formate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long time = cal.getTimeInMillis();
		return new Timestamp(time);
	}

	public Timestamp getNow() {
		Calendar cal = Calendar.getInstance();
		long date = cal.getTimeInMillis();
		return new Timestamp(date);
	}

	protected String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	// protected void sendMsg(HttpServletResponse response, String jsonResult)
	// throws IOException {
	// // HttpServletResponse response = ServletActionContext.getResponse();
	// response.setContentType("application/json");
	// response.setCharacterEncoding("UTF-8");
	// PrintWriter out = response.getWriter();
	// out.write(jsonResult);
	// out.close();
	// }
}
