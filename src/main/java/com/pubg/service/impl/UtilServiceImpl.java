package com.pubg.service.impl;

import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pubg.dto.EmailDTO;
import com.pubg.entity.RegistrationEntity;
import com.pubg.service.BaseService;
import com.pubg.service.UtilService;

/**
 * UtilServiceImpl : Implementation class of UtilService.
 * 
 * @author Prolifics
 *
 */
@Service
public class UtilServiceImpl extends BaseService implements UtilService {
	
	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@Override
	@Async
	public void sendEmailWithAttachment(EmailDTO emailRequest,String appType){
		logger.info("Entering UtilServiceImpl.sendEmailWithAttachment()");
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        boolean mailSent=false;
		try {
			helper = new MimeMessageHelper(msg, true);
			helper.setTo(emailRequest.getSendTo());
	        helper.setSubject(emailRequest.getSubject());
	        helper.setText(emailRequest.getMessage(), true);
	        //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
	        javaMailSender.send(msg);
	        mailSent=true;
		} catch (javax.mail.MessagingException e) {
			e.printStackTrace();
		}
		
		if(mailSent) {
			String updateVerificationLinkStatus = "FROM RegistrationEntity where userId = '"+emailRequest.getUserId()+"'";
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction entityTransaction = entityManager.getTransaction();
			try {
				RegistrationEntity userEntity = (RegistrationEntity) entityManager.createQuery(updateVerificationLinkStatus).getResultList().get(0);
				userEntity.setVerificationLink("Sent");
				//Start of transaction
				entityTransaction.begin();
				//merge method is used to update entities into their DB table.
				entityManager.merge(userEntity);
				entityTransaction.commit();
			}catch(RuntimeException e) {
				e.printStackTrace();
			}finally {
				entityManager.clear();
				entityManager.close();
			}
		}
		logger.info("Exiting UtilServiceImpl.sendEmailWithAttachment()");
    }

	
}
