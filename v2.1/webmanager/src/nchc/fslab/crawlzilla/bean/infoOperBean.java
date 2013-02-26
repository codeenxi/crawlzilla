package nchc.fslab.crawlzilla.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class infoOperBean {

	public String getSpendTime(String dbName) throws IOException {
		String spendTime = "0";
		String startTime, finishTime;
		startTime = getMessage(dbName, "start_time");
		finishTime = new String(getMessage(dbName, "finish_time"));

		if (!startTime.equals("null")) {
			int sHr, sMin, sSec, start, finish;
			if (finishTime.equals("null")) {
				finishTime = System.currentTimeMillis() / 1000 + "";
				System.out.println("F: " + finishTime);
				System.out.println("S: " + startTime);
			}

			start = Integer.parseInt(startTime);
			finish = Integer.parseInt(finishTime);
			System.out.println("" + (finish - start));
			sHr = ((finish - start) / 3600);
			sMin = ((finish - start - 3600 * sHr) / 60);
			sSec = ((finish - start) % 60);
			// System.out.println("H: "+ sHr + ", M: " + sMin + ", S: " + sSec);
			spendTime = (sHr >= 10 ? sHr : "0" + sHr) + ":"
					+ (sMin >= 10 ? sMin : "0" + sMin) + ":"
					+ (sSec >= 10 ? sSec : "0" + sSec);
		}
		return spendTime;
	}

	public boolean _checkDB(String dbName, String filePath) {
		File file = new File("/opt/crawlzilla/crawlDB/" + dbName + "/.meta/"
				+ filePath);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public String getMessage(String DBName, String fileName) throws IOException {
		String filePath = "/opt/crawlzilla/crawlDB/" + DBName + "/.meta/"
				+ fileName;
		String retMes = "null";
		if (_checkDB(DBName, fileName)) {
			FileReader fileNameReader = new FileReader(filePath);
			String strTemp = new String(
					new BufferedReader(fileNameReader).readLine());
			retMes = strTemp;
			fileNameReader.close();
		}
		return retMes;
	}

	public void changeHideInfoFlag(String dbName, boolean infoSwitch)
			throws IOException {
		if (_checkDB(dbName, "show_status_flag")) {
			String fPath = "/opt/crawlzilla/crawlDB/" + dbName
					+ "/.meta/show_status_flag";
			FileReader fr = new FileReader(fPath);
			BufferedReader br = new BufferedReader(fr);
			String newContent = "";

			while (br.readLine() != null) {
				newContent = infoSwitch + "";
			}
			FileWriter fw = new FileWriter(fPath);
			fw.write(newContent);
			br.close();
			fr.close();
			fw.close();
		}
	}

	public String getIPAddr() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ipAddr = addr.getHostAddress().toString();
		return ipAddr;
	}

	public static void main(String args[]) throws IOException {
		infoOperBean iOB = new infoOperBean();
		System.out.println(iOB.getMessage("nchc-test1", "status"));
		System.out.println(iOB.getSpendTime("nchc-test1"));
		// iOB.changeHideInfoFlag("NCHC_20130131-2", true);
	}
}
