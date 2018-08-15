package application.service;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import application.dao.DateDao;
import application.dao.EmailDao;
import application.dao.NoteDao;
import application.model.Note;

public class NotesService {

	private Connection con;

	public NotesService() {
		try {
			DBConnectionService dbcs = new DBConnectionService();
			con = dbcs.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static final class SingletonHolder {
		private static final NotesService INSTANCE = new NotesService();
	}

	public static NotesService getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void addNote(int id_user, String title, String message, int id_type, boolean checked) {
		DateDao dateDao = new DateDao(con);
		dateDao.insertDate(LocalDateTime.now(), LocalDateTime.now());
		int id_date = dateDao.returnIdDate();
		NoteDao noteDao = new NoteDao(con);
		noteDao.addNotes(id_user, title, message, id_type, checked, id_date);

	}

	public void deleteNote(int id) {

		NoteDao noteDao = new NoteDao(con);
		Optional<Note> note = noteDao.getSelectedNote(id);
		if (note.isPresent()) {
			int id_date = note.get().getId_date();
			noteDao.deleteNotes(id);

			DateDao dateDao = new DateDao(con);
			dateDao.deleteDate(id_date);
		}

	}

	public List<Note> getListOfNotes(int id_user, int id_type) {

		NoteDao noteDao = new NoteDao(con);
		return noteDao.returnList(id_user, id_type);
	}

	public void changeNote(Note note) {

		NoteDao noteDao = new NoteDao(con);
		noteDao.updateDate(note.getTitle(), note.getMessage(), note.isChecked(), note.getId());

		int id_date = note.getId_date();

		DateDao dateDao = new DateDao(con);
		dateDao.updateDate(LocalDateTime.now(), id_date);
	}

	public void createReminder(int id_note, LocalDateTime date_scheduled) {

		EmailDao emailDao = new EmailDao(con);
		emailDao.addEmail(id_note, date_scheduled, false);

	}

}
