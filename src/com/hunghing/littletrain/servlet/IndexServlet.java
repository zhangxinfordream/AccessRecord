package com.hunghing.littletrain.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunghing.littletrain.model.Data;
import com.hunghing.littletrain.util.BaseData;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static ArrayList<String> test = new ArrayList<>();
	
    public IndexServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		String sessionId = request.getSession().getId();
	
		System.out.println("welcome: " + sessionId);
		
		Data data = new Data();
		data.setIdentifier(request.getParameter("key"));
		BaseData.datas.put(sessionId, data);
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(sessionId.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
