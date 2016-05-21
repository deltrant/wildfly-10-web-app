package cz.muni.fi.pv243.jms;

import dao.DemoDAO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import model.Comment;
import model.Demo;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static model.Demo.Status.UPLOADED;

@Slf4j
@Getter
@Setter
@ApplicationScoped
@Singleton
@Startup
public class ServiceBean {

	private byte[] mp3 = null;

	@Inject
	private DemoDAO demoDAO;

	private static Demo testDemo = new Demo();

	@Transactional(Transactional.TxType.REQUIRED)
	public void run() {
		testDemo.setEmail("test@email.cz");
		testDemo.setArtist("Interpreeteurer");
		testDemo.setStatus(UPLOADED);
		testDemo.setTitle("titelel");
		List<Comment> comments = new ArrayList<>();
		comments.add(new Comment("Jožo Ráž", "Môže byť."));
		comments.add(new Comment("Palo Habera", "Nepáči sa mi to."));
		testDemo.setComments(comments);
		testDemo.setTrack(this.getMp3());
		this.setMp3(null); //free memory

		log.warn("All: {}", demoDAO.findAll());
		demoDAO.createDemo(testDemo);
		log.warn("Saved: {}", testDemo);
		log.warn("All: {}", demoDAO.findAll());
	}

}
