package com.hunghing.littletrain.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import com.hunghing.littletrain.model.Data;

public class SaveFile implements Runnable{
	private String ip;
	private Data data;
	private String sessionId;
	
	public SaveFile(String ip, Data data, String sessionId){
		this.ip = ip;
		this.data = data;
		this.sessionId = sessionId;
	}

	@Override
	public void run() {
		while(true){
            Timestamp le = data.getLastEdit();
            Timestamp nowTime = new Timestamp(new Date().getTime());
            System.out.println("lastEdit: " + le + " nowTime: " + nowTime);   //不加这一句会BUG
            long timeDifference = (nowTime.getTime()-le.getTime())/1000;
            if(timeDifference>6){
                break;
            }
        }
        String resultTime = data.getTimes();
        String result;
        String path = "test.txt";
        File file = new File(path);
        if(!file.exists()){                  //如果文件不存在
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        result = ip + " " + data.getIdentifier() +" " + resultTime + " " +String.valueOf(data.getFrequency()) + "\r\n";

        synchronized(WriterLock.writeLock){
        	try{
                FileWriter fileWritter = new FileWriter(file.getName(),true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write(result);
                bufferWritter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        BaseData.datas.remove(sessionId);
        System.out.println("done");
	}

}
