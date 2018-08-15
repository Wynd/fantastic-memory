package application.service;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import application.dao.EmailDao;
import application.dao.NoteDao;
import application.dao.UserDao;
import application.model.Email;
import application.model.Note;
import application.model.SendEmail;
import application.model.User;
import application.runnable.EmailRunnable;

public class EmailsService {

	private Connection con;

	public EmailsService() {
		try {
			DBConnectionService dbcs = new DBConnectionService();
			con = dbcs.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static final class SingletonHolder {
		private static final EmailsService INSTANCE = new EmailsService();
	}

	public static EmailsService getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void sendEmail(int id) {

		NoteDao noteDao = new NoteDao(con);
		Optional<Note> note = noteDao.getSelectedNote(id);
		if (note.isPresent()) {
			UserDao userDao = new UserDao(con);
			Optional<User> user = userDao.findUserAfterId(note.get().getId_user());
			if (user.isPresent()) {
				String email = user.get().getEmail();
				String title = "Reminder";
				String message = "Dear " + user.get().getUsername() + " " + "\nDo not forget about the "
						+ note.get().getTitle() + " from your list!";
				SendEmail sendEmail = new SendEmail(email, title, message);
			}
		}

	}

	public void deleteEmails() {

		EmailDao emailDao = new EmailDao(con);
		emailDao.deleteSentEmail();
	}

	public void updateEmails(LocalDateTime date_scheduled, boolean is_sent, int id_note) {
		EmailDao emailDao = new EmailDao(con);
		emailDao.updateEmail(date_scheduled, is_sent, id_note);

	}

	public List<Email> getUnsentEmails() {

		EmailDao emailDao = new EmailDao(con);
		return emailDao.returnEmail(LocalDateTime.now(), false);
	}

	public void emailAction(ScheduledExecutorService service) {
		service.schedule(new EmailRunnable(), 1, TimeUnit.MINUTES);
	}

	public void stopEmailAction(ScheduledExecutorService service) {
		service.shutdown();
	}
}
