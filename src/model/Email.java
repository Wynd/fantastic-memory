package model;

import java.time.LocalDateTime;

public class Email {
	private int id;
	private int id_note;
	private LocalDateTime date_scheduled;
	private boolean is_sent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_note() {
		return id_note;
	}
	public void setId_note(int id_note) {
		this.id_note = id_note;
	}
	public LocalDateTime getDate_scheduled() {
		return date_scheduled;
	}
	public void setDate_scheduled(LocalDateTime date_scheduled) {
		this.date_scheduled = date_scheduled;
	}
	public boolean isIs_sent() {
		return is_sent;
	}
	public void setIs_sent(boolean is_sent) {
		this.is_sent = is_sent;
	}
	
	
	

}
