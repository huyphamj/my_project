package com.database.bean;

import java.util.Date;

public class JackpotBean {
	private long value;
	private int giai_nhat, giai_nhi, giai_ba, giai_jackpot;

	public int getGiai_jackpot() {
		return giai_jackpot;
	}

	public void setGiai_jackpot(int giai_jackpot) {
		this.giai_jackpot = giai_jackpot;
	}

	private Date gen_date;

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public int getGiai_nhat() {
		return giai_nhat;
	}

	public void setGiai_nhat(int giai_nhat) {
		this.giai_nhat = giai_nhat;
	}

	public int getGiai_nhi() {
		return giai_nhi;
	}

	public void setGiai_nhi(int giai_nhi) {
		this.giai_nhi = giai_nhi;
	}

	public int getGiai_ba() {
		return giai_ba;
	}

	public void setGiai_ba(int giai_ba) {
		this.giai_ba = giai_ba;
	}

	public Date getGen_date() {
		return gen_date;
	}

	public void setGen_date(Date gen_date) {
		this.gen_date = gen_date;
	}
}
