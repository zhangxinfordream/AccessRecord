package com.hunghing.littletrain.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunghing.littletrain.model.Data;
import com.hunghing.littletrain.util.BaseData;
import com.hunghing.littletrain.util.SaveFile;

public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RecordServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        
        String token = request.getParameter("token");
        System.out.println(token);
        Data data = BaseData.datas.get(token);
        if(data==null){
        	//记录错误日志信息
        	System.out.println("data is empty!");
        	return;
        }
        
        data.setFrequency(data.getFrequency()+1);
        //获取当前时间
        Timestamp timestamp = new Timestamp(new Date().getTime());
        String times = data.getTimes();
        if(times==null){
            times = timestamp.toString();
        }else{
            times += " " + timestamp.toString();
        }
        data.setTimes(times);
        data.setLastEdit(timestamp);
        
        boolean isFirst = data.getIsFirst();    //该用户是不是第一次访问
        if(isFirst){
            data.setIsFirst();   //设置用户已经访问过后台
            
            BaseData.datas.put(token, data);          
            //获取用户的ip
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = (String) ips[index];
                    if (!("unknown".equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
            
            Thread thread = new Thread(new SaveFile(ip, data, token));
            thread.start();
        }
        
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
