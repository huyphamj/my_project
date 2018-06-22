package com.crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.Debugger;
import com.main.Main;

public class CrawlerVietlott extends Thread {
	private String url = "http://vietlott.ketqua.net/ket-qua-so-xo-vietlott-mega-6-45/";
	private String input_date;
	private ArrayList<String> jackpot_list = new ArrayList<String>();

	public CrawlerVietlott(String date) {
		url += date;
		this.input_date = date;
	}

	public void run() {
		try {
			if (new SimpleDateFormat("dd-MM-yyyy").parse(input_date.replace("ngay=", ""))
					.getTime() > System.currentTimeMillis() + 86400000)
				return;
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Debugger.get().log(url);
		String html = "";
		while (html.equals("")) {
			try {
				html = new DefaultHttpConnection().get(url.replace("ngay=", ""), "");
			} catch (Exception e) {
			}
		}
		Document doc = Jsoup.parse(html);
		if (html.endsWith("not available!")) {
			new CrawlerVietlott(Main.getNextDayToString(input_date)).start();
			return;
		}
		try {
			// NẾU NHƯ KHÔNG CÓ KẾT QUẢ CỦA NGÀY TRUY VẤN
			try {
				if (doc.select("p.text-danger").first().text().startsWith("Không có kết quả")) {
					new CrawlerVietlott(Main.getNextDayToString(input_date)).start();
					return;
				}
			} catch (Exception e) {
			}
			// END NẾU

			// DATE
			String date = doc.select("div.box-result-detail").first().select("p.time-result").text();
			date = date.substring(date.indexOf("/") - 2, date.indexOf("/") + 8).replaceAll("/", "-");
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date parsedDate = df.parse(date);
			date = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
			date += " 18:10:00";

			// GET VALUE
			Elements numbers = doc.select("ul.result-number").first().select("li");
			for (Element e : numbers) {
				String val = e.text();
				jackpot_list.add(val);
			}

			// GET NUMBER OF PRIZE
			Elements prises = doc.select("table.table").get(1).select("tbody").first().select("tr");
			int giai_jackpot = Integer.parseInt(prises.get(0).select("td").get(1).text());
			long value = Long.parseLong(prises.get(0).select("td").get(2).text().replaceAll("\\.", ""));
			int giai_nhat = Integer.parseInt(prises.get(1).select("td").get(1).text());
			int giai_nhi = Integer.parseInt(prises.get(2).select("td").get(1).text());
			int giai_ba = Integer.parseInt(prises.get(3).select("td").get(1).text());

			// ========== TO DATABASE ===========//
			for (int i = 0; i < jackpot_list.size(); i++) {
				DBEngine.get().lotteryDao.insertLottery(40, date, 1, jackpot_list.get(i));
			}
			DBEngine.get().jackpotDao.insert(date, value, giai_jackpot, giai_nhat, giai_nhi, giai_ba);

			// CRAWL NGAY HOM SAU
			new CrawlerVietlott(Main.getNextDayToString(input_date).replaceAll("ngay=", "")).start();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Jsoup exception" + url);
			new CrawlerVietlott(Main.getNextDayToString(input_date)).start();
		}
	}
	
	public static void main(String[] args) throws ParseException {
		String date = "20-02-2013";
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date parsedDate = df.parse(date);
		System.out.println(parsedDate);
		date = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
		date += " 18:10:00";
		System.out.println(date);
	}
}
