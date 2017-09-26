package itg.makerspace.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import itg.makerspace.MainFrame;

public class Logger {
	
	public MainFrame main;
	
	public String logFolderPath;
	
	public File logFile = null;
	
	public Logger(MainFrame m) {
		main = m;
		
		logFolderPath = MainFrame.HOME_DIRECTORY + File.separator + "logs";
		
		File file = new File(logFolderPath);
		System.out.println(file.getAbsolutePath());
		if(!file.exists()) {
			file.mkdirs();
		}
	}
	
	public void log(String msg) {
		if(logFile == null) {
			String logFilePath = logFolderPath;
			File logFolder = new File(logFolderPath);
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String date = dateFormat.format(new Date());
		
			if (logFolder.list() == null) {
				logFolder.mkdirs();
				logFilePath += File.separator + "TerminalLog-" + date + "_1.log";
			}else if(logFolder.list().length > 0) {
				boolean equalDates = false;
				
				for(int i = 0; i < logFolder.list().length; i++) {
					String file = logFolder.list()[i].trim();
					String fileDate = file.substring(file.indexOf("-") + 1, file.indexOf("_"));
					if(fileDate.equals(date)) {
						equalDates = true;
						break;
					}
				}
				
				if(equalDates) {
					ArrayList<String> fileList = new ArrayList<String>();
					for(int i = 0; i < logFolder.list().length; i++) {
						String file = logFolder.list()[i].trim();
						String fileDate = file.substring(file.indexOf("-") + 1, file.indexOf("_"));
						if(fileDate.equals(date)) {
							fileList.add(file);
						}
					}
					int highestInt = 0;
					
					for(int i = 0; i < fileList.size(); i++) {
						String file = fileList.get(i);
						int logNumber = Integer.parseInt(file.substring(file.indexOf("_") + 1, file.indexOf(".")).trim());
						if(logNumber > highestInt) {
							highestInt = logNumber;
						}
					}
					highestInt++;
					
					logFilePath += File.separator + "TerminalLog-" + date + "_" + highestInt +".log";
				} else {
					logFilePath += File.separator + "TerminalLog-" + date + "_1.log";
				}
			} else {
				logFilePath += File.separator + "TerminalLog-" + date + "_1.log";
			}
			
			logFile = new File(logFilePath);
		}
		
		try {
			if(!logFile.exists()) {
				logFile.createNewFile();
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(logFile));
			ArrayList<String> dataLines = new ArrayList<String>();
			Calendar cal = Calendar.getInstance();
	    	DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
			String date = "[" + sdf.format(cal.getTime()) + "] ";
			
			while (true) {
				String line = reader.readLine();
				if(line == null || line == "") {
					break;
				} else {
					dataLines.add(line);
				}
			}
			reader.close();
			
			dataLines.add(date + msg);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
			for(int i = 0; i < dataLines.size(); i++) {
				writer.write(dataLines.get(i));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
