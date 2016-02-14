package com.comune.view;


import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import java.util.Locale;
import java.util.UUID;

import com.vividsolutions.jts.geom.Coordinate;

import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Hibernate;
import org.hibernate.spatial.criterion.SpatialRestrictions;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.*;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.sql.rowset.serial.SerialBlob;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.vividsolutions.jts.geom.*;

import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.comune.model.*;
import com.comune.model.android.*;
import com.comune.util.*;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;


/**
* Esse arquivo implementa serviços relacionados a Usuários.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/users")
public class UsersView 
{
	@PersistenceContext(unitName = "comunePersistenceUnit")
	private EntityManager entityManager;
		
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(UsersView.class);
	
	
	/**
	* Serviço que reseta a senha do usuário e envia uma nova combinação temporária para o seu email.
	* @param userEmail Parâmetro enviado via URL contendo o email do usuário que deseja ter sua senha resetada.
	* @return response Texto com o resultado da operação.
	*/
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<String> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) 
	{
		String response;
		
		Session session = entityManager.unwrap(Session.class);
	    Criteria criteria = session.createCriteria(User.class);
	    criteria.add(Restrictions.eq("email", userEmail));
	    criteria.setMaxResults(1);
	    User user = (User) criteria.uniqueResult();
	    
		logger.debug("resetPassword : {}", "user id: " + user.getId());
		logger.debug("resetPassword : {}", "user email: " + user.getEmail());

	    
	    if (user != null)
	    {
	    	try
	    	{
		    	String token = UUID.randomUUID().toString().replace("-", "");
		    	token = token.substring(0, 6);
		        //createPasswordResetTokenForUser(user, token);
				logger.debug("resetPassword : {}", "token " + token);

				//Create the application context
		        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:application-context.xml");
		         
		        //Get the mailer instance
		        ApplicationMailer mailer = (ApplicationMailer) context.getBean("mailService");
		        
		        mailer.sendResetTokenEmail(token, user);
		        
				logger.debug("resetPassword : {}", "email");

		        /* Salva a senha temporária **/
		        user.setHashedTemporaryPassword(Functions.cryptWithMD5(token));
		        entityManager.merge(user);
		        /* **/
		    	
		        logger.debug("resetPassword : {}", "HttpStatus.OK");
				response = "Email enviado.";
				return new ResponseEntity<String>(response, HttpStatus.OK);
	    	}
	    	catch (Exception e)
	    	{
	    		logger.debug("resetPassword : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.toString());
				response = "Ocorreu um erro.";
				return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    	}
	    }
	    
	    logger.debug("resetPassword : {}", "HttpStatus.NOT_FOUND");
		response = "Usuário não encontrado.";
		return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
	}
	
	
	/**
   * Serviço que autentica um usuário.
   * @param loginInfo Parâmetro enviado via form contendo os dados para login de um usuário.
   * @return userInfo Objeto JSON contendo dados do usuário.
   */
	@RequestMapping(value = "/authenticateUser", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<UserInfo> authenticateUser(@RequestBody LoginInfo loginInfo) 
	{
		User user;
		List<User> users;
		boolean loginInfoOK = true;
		UserInfo userInfo = null;
		UserProfilePhoto userProfilePhoto;
		List<UserProfilePhoto> userProfilePhotos;
		
		logger.debug("loginInfo : {}", loginInfo);
		
		if (loginInfo != null)
		{
			try
			{
				Query query = entityManager.createQuery("SELECT u FROM " + User.class.getName() + " u WHERE u.email = :email AND u.hashedPassword = :hashpass");
				query.setParameter("email", loginInfo.getEmail());
				query.setParameter("hashpass", Functions.cryptWithMD5(loginInfo.getPassword()));
				query.setMaxResults(1);
				users = query.getResultList();
				
				if ((users == null) || (users.size() < 1))
				{
					loginInfoOK = false;
					
					query = entityManager.createQuery("SELECT u FROM " + User.class.getName() + " u WHERE u.email = :email AND u.hashedTemporaryPassword = :hashpass");
					query.setParameter("email", loginInfo.getEmail());
					query.setParameter("hashpass", Functions.cryptWithMD5(loginInfo.getPassword()));
					query.setMaxResults(1);
					users = query.getResultList();
					
					if ((users != null) && (users.size() > 0))
					{
						loginInfoOK = true;
					}
				}
				else
				{
					/*Se fez o login com a senha original, apaga a temporária*/
					user = users.get(0);
					user.setHashedTemporaryPassword(null);
					entityManager.merge(user);
				}
				
				if (loginInfoOK)
				{					
					user = users.get(0);
					
					userInfo = new UserInfo();
					userInfo.setId(user.getId());
					userInfo.setFirstName(user.getFirstName());
					userInfo.setLastName(user.getLastName());
					userInfo.setDateOfBirth(user.getDateOfBirth().getTime());	
					userInfo.setEmail(user.getEmail());
					userInfo.setMobileNumber(user.getMobileNumber());
					userInfo.setPhoneNumber(user.getPhoneNumber());
					
					Session session = entityManager.unwrap(Session.class);
				    Criteria criteria = session.createCriteria(UserProfilePhoto.class);
				    criteria.add(Restrictions.eq("user.id", user.getId()));
				    userProfilePhotos = criteria.list();
	
				    if ((userProfilePhotos != null) && (userProfilePhotos.size() > 0))
				    {
				    	userProfilePhoto = userProfilePhotos.get(0);
				    	userInfo.setIdPhoto(userProfilePhoto.getPicture().getId());
				    }
					
			    	java.util.Date date = new java.util.Date();
					user.setLastLoggedInAt(new Timestamp(date.getTime()));
					entityManager.merge(user);
	
					logger.debug("authenticateUser : {}", "HttpStatus.OK");
					return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
				} 
				else
				{
					logger.debug("authenticateUser : {}", "HttpStatus.UNAUTHORIZED");
					return new ResponseEntity<UserInfo>(userInfo, HttpStatus.UNAUTHORIZED);
				}
				
			}
			catch (Exception e)
			{
				logger.debug("authenticateUser : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.getLocalizedMessage());
				return new ResponseEntity<UserInfo>(userInfo, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity<UserInfo>(userInfo, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	/**
   * Serviço que atualiza os dados de um usuário cadastrado.
   * @param updatedUserInfo Parâmetro enviado via form contendo os novos dados do usuário.
   * @return response Texto com o resultado da operação.
   */
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<String> updateUserInfo(@RequestBody UpdatedUserInfo updatedUserInfo) 
	{
		String response = null;
		List<User> users;
		User user;
				
		if (updatedUserInfo != null)
		{
			try
			{
				Session session = entityManager.unwrap(Session.class);
			    Criteria criteria = session.createCriteria(User.class);
			    criteria.add(Restrictions.eq("email", updatedUserInfo.getEmail()));
			    criteria.add(Restrictions.eq("hashedPassword", Functions.cryptWithMD5(updatedUserInfo.getPassword())));
			    criteria.setMaxResults(1);
			    users = criteria.list();
			
			    if (users.size() == 1)
			    {	
			    	user = users.get(0);
			    	
			    	if (updatedUserInfo.getFirstName() != null) user.setFirstName(updatedUserInfo.getFirstName());
			    	if (updatedUserInfo.getLastName() != null) user.setLastName(updatedUserInfo.getLastName());
			    	if (updatedUserInfo.getDateOfBirth() != 0) user.setDateOfBirth(new java.sql.Date(updatedUserInfo.getDateOfBirth()));
			    	if (updatedUserInfo.getEmail() != null) user.setEmail(updatedUserInfo.getEmail());
			    	if (updatedUserInfo.getNewPassword() != null) user.setHashedPassword(Functions.cryptWithMD5(updatedUserInfo.getNewPassword()));
			    	if (updatedUserInfo.getMobileNumber() != null) user.setMobileNumber(updatedUserInfo.getMobileNumber());
			    	if (updatedUserInfo.getEmail() != null) user.setEmail(updatedUserInfo.getEmail());

			    	entityManager.merge(user);
			    	
			    	logger.debug("updateUserInfo : {}", "HttpStatus.OK");
					response = "Dados atualizados.";
					return new ResponseEntity<String>(response, HttpStatus.OK);
			    }

			    logger.debug("updateUserInfo : {}", "HttpStatus.NOT_FOUND");
				response = "Usuário inexistente.";
				return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
			}
			catch (Exception e)
			{
				logger.debug("updateUserInfo : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.getLocalizedMessage());
				response = "Ocorreu um erro.";
				return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.debug("updateUserInfo : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		response = "Objeto enviado com erros";
		return new ResponseEntity<String>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	
	
	/**
   * Serviço que cadastra um novo usuário.
   * @param userInfo Parâmetro enviado via form contendo os dados do usuário.
   * @return newUserInfo Objeto JSON contendo informações do usuário recém cadastrado.
   */
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<UserInfo> registerUser(@RequestBody UserInfo userInfo) 
	{
		UserInfo newUserInfo = new UserInfo();
		List<User> users;
		User newUser;
		
		//logger.debug("userInfo : {}", userInfo);
		
		if (userInfo != null)
		{
			//logger.debug("userInfo.email : {}", userInfo.getEmail());
			
			try
			{
				Session session = entityManager.unwrap(Session.class);
			    Criteria criteria = session.createCriteria(User.class);
			    criteria.add(Restrictions.eq("email", userInfo.getEmail()));
			    criteria.setMaxResults(1);
			    users = criteria.list();
				/*Query query = entityManager.createQuery("SELECT u FROM " + User.class.getName() + " u WHERE u.email = :user_email");
				query.setParameter("user_email", userInfo.getEmail());
			    users = query.getResultList();*/
			    //logger.debug("users.size: {}", users.size());

			    if (users.size() == 0)
			    {			    	
			    	newUser = new User();
			    	newUser.setFirstName(userInfo.getFirstName());
			    	newUser.setLastName(userInfo.getLastName());
			    	newUser.setDateOfBirth(new java.sql.Date(userInfo.getDateOfBirth()));
			    	newUser.setEmail(userInfo.getEmail());
			    	newUser.setHashedPassword(Functions.cryptWithMD5(userInfo.getPassword()));
			    	newUser.setMobileNumber(userInfo.getMobileNumber());
			    	/*newUser.setPhoneNumber();*/
			    	java.util.Date date = new java.util.Date();
			    	newUser.setRegisteredAt(new Timestamp(date.getTime()));
			    	
			    	entityManager.persist(newUser);
			    	entityManager.flush();
			    	
			    	newUserInfo.setId(newUser.getId());
			    	newUserInfo.setEmail(newUser.getEmail());
			    	newUserInfo.setFirstName(newUser.getFirstName());
			    	newUserInfo.setLastName(newUser.getLastName());
			    	newUserInfo.setMobileNumber(newUser.getMobileNumber());
			    	newUserInfo.setDateOfBirth(newUser.getDateOfBirth().getTime());

			    	logger.debug("registerUser : {}", "HttpStatus.OK");
			    	return new ResponseEntity<UserInfo>(newUserInfo, HttpStatus.OK);
			    }
			    else
			    {
					logger.debug("registerUser : {}", "HttpStatus.CONFLICT");
			    	return new ResponseEntity<UserInfo>(newUserInfo, HttpStatus.CONFLICT);
			    }			    
			}
			catch (Exception e)
			{
				logger.debug("registerUser : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.getLocalizedMessage());
				return new ResponseEntity<UserInfo>(newUserInfo, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		logger.debug("registerUser : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<UserInfo>(newUserInfo, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	private void setUploadedFileAttributesFromMultipartFile(UploadedFiles uploadedFile, MultipartFile file)
	{
		try
		{
			if ((uploadedFile != null) && (file != null))
			{
				uploadedFile.setName(file.getOriginalFilename());
				uploadedFile.setContentType(file.getContentType());
				uploadedFile.setContentLength(file.getBytes().length);
				uploadedFile.setContent(file.getBytes());
				Date date = new Date();
				uploadedFile.setReceivedAt(new Timestamp(date.getTime()));
			}
		}
		catch (Exception e)
		{}
	}
	
	
	/**
   * Serviço que recebe e salva no Banco de Dados uma imagem para o perfil de um usuário.
   * @param userId Parâmetro enviado via form contendo o ID do usuário.
   * @param file Parâmetro enviado via form contendo o arquivo da imagem.
   * @return docInfo Objeto JSON confirmando o recebimento da imagem.
   */
	@RequestMapping(value = "/uploadUserPhoto", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<UploadedDocumentInfo> uploadUserPhoto(@RequestParam("userId") int userId, @RequestParam("file") MultipartFile file) 
	{
		UploadedDocumentInfo docInfo = null;
		UploadedFiles pictureFile;
		UserProfilePhoto userProfilePhoto;
		List<UserProfilePhoto> userProfilePhotos;
				
		if (!file.isEmpty() && 
				((file.getContentType().equals("image/jpeg")) || (file.getContentType().equals("image/png"))))
		{			
			try
			{
				User user = entityManager.find(User.class, userId);
				
				if (user != null)
				{
	                Session session = entityManager.unwrap(Session.class);
				    Criteria criteria = session.createCriteria(UserProfilePhoto.class);
				    criteria.add(Restrictions.eq("user.id", userId));
				    userProfilePhotos = criteria.list();
	                
				    if ((userProfilePhotos != null) && (userProfilePhotos.size() > 0))
				    {
				    	userProfilePhoto = userProfilePhotos.get(0);
				    	
				    	pictureFile = userProfilePhoto.getPicture();
				    	setUploadedFileAttributesFromMultipartFile(pictureFile, file);
		                entityManager.merge(pictureFile);
				    }
				    else
				    {
				    	pictureFile = new UploadedFiles();
				    	setUploadedFileAttributesFromMultipartFile(pictureFile, file);
		                entityManager.persist(pictureFile);
		                entityManager.flush();
				    	
		                userProfilePhoto = new UserProfilePhoto();
		                userProfilePhoto.setUser(user);
		                userProfilePhoto.setPicture(pictureFile);
		                entityManager.persist(userProfilePhoto);
				    }
				    entityManager.flush();

	                docInfo = new UploadedDocumentInfo();
					docInfo.setOwnerId(userId);
					docInfo.setFileId(pictureFile.getId());
					docInfo.setFileName(pictureFile.getName());
					
					logger.debug("uploadUserPhoto : {}", "HttpStatus.OK");
					return new ResponseEntity<UploadedDocumentInfo>(docInfo, HttpStatus.OK);
				}
			}
			catch (Exception e)
			{
				logger.debug("uploadUserPhoto : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.getLocalizedMessage());
				return new ResponseEntity<UploadedDocumentInfo>(docInfo, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.debug("uploadUserPhoto : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<UploadedDocumentInfo>(docInfo, HttpStatus.UNPROCESSABLE_ENTITY);
	}


	/**
   * Serviço que recupera do Banco de Dados e retorna um arquivo carregado.
   * @param id Parâmetro enviado via URL contendo o ID do arquivo.
   * @param response Conexão HTTP para envio do arquivo como resposta à requisição.
   */
	@RequestMapping("/downloadFile")
	@Transactional
	//public void downloadUserPhoto(@PathParam("id") int id, HttpServletResponse response) 
	//public void downloadUserPhoto(@RequestBody UserIdInfo userIdInfo, HttpServletResponse response) 
	//public void downloadUserPhoto(HttpServletRequest request, HttpServletResponse response) 
	public void downloadFile(@RequestParam("id") int id, HttpServletResponse response) 
	{
		UploadedFiles file;
		
		try
		{
			file = entityManager.find(UploadedFiles.class, id);

	    	InputStream is = new ByteArrayInputStream(file.getContent());
	    	
	    	response.setContentType(file.getContentType());
	    	response.setContentLength(file.getContentLength());
	    	response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
	    	
	    	OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			os.flush();
			os.close();
			is.close();
	    	
			logger.debug("downloadUserPhoto : {}", file.getName() + ", HttpStatus.OK");
		}
		catch (Exception e)
		{
			logger.debug("downloadUserPhoto : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.toString());
			//return null;
		}
	}
	
	
}
