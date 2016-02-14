package com.comune.view;


import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import com.vividsolutions.jts.geom.Coordinate;

import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.spatial.criterion.SpatialRestrictions;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vividsolutions.jts.geom.*;

import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.comune.model.*;
import com.comune.model.android.*;
import com.comune.util.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


/**
* Esse arquivo implementa serviços relacionados a Relatos.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/reports")
public class ReportsView 
{
	@PersistenceContext(unitName = "comunePersistenceUnit")
	private EntityManager entityManager;
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportsView.class);
	
	
	/**
   * Serviço que retorna informações de um relato feito por um usuário.
   * @param reportUserInfo Objeto JSON recebido no corpo da requisição HTTP,
   * contendo os ID's do usuário e do relato.
   * @return userReport Objeto JSON com as informações do relato.
   */
	@RequestMapping(value = "/getReportById", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<UserReport> getReportById(@RequestBody ReportUserInfo reportUserInfo) 
	{
		User user;
		Report report;
		UserReport userReport = new UserReport();
		ReportResponse reportResponse;
		List<ReportResponse> reportResponses;
		ReportPicture reportPicture;
		List<ReportPicture> reportPictures;
		ReportVideo reportVideo;
		List<ReportVideo> reportVideos;
		Report response;
 		
		if (reportUserInfo != null)
		{
	    	try
	    	{
	    		user = entityManager.find(User.class, reportUserInfo.getUserId());
		    	report = entityManager.find(Report.class, reportUserInfo.getReportId());

			    if ((user != null) && (report != null)) //(report.getUser().getId() == reportUserInfo.getUserId())
			    {
		    		userReport.setReportId(report.getId());
		    		userReport.setUserId(report.getUser().getId());
		    		userReport.setPlaceId(report.getPlace().getId());
		    		userReport.setComment(report.getComment());
		    		userReport.setMadeAt(report.getMadeAt().getTime());
		    		userReport.setResponseId(-1);
    				userReport.setResponseVisualized(false);
		    		
		    		/********************************************************************************/

		    		Session session = entityManager.unwrap(Session.class);
		    	    Criteria criteria = session.createCriteria(ReportResponse.class);
		    	    criteria.add(Restrictions.eq("report.id", reportUserInfo.getReportId()));
		    	    criteria.setMaxResults(1);
		    	    reportResponses = criteria.list();
		    	    
		    		if ((reportResponses != null) && (reportResponses.size() > 0))
		    		{
		    			reportResponse = reportResponses.get(0);
		    			response = reportResponse.getResponse();
		    			
		    			if (response != null)
		    			{
		    				userReport.setResponseId(response.getId());
		    				userReport.setResponseVisualized(reportResponse.isVisualized());
		    			}
		    		}
		    		
		    		/********************************************************************************/
		    		
		    		session = entityManager.unwrap(Session.class);
		    	    criteria = session.createCriteria(ReportPicture.class);
		    	    criteria.add(Restrictions.eq("report.id", reportUserInfo.getReportId()));
		    	    criteria.setMaxResults(1);
		    	    reportPictures = criteria.list();
		    	    
		    		if ((reportPictures != null) && (reportPictures.size() > 0))
		    		{
		    			reportPicture = reportPictures.get(0);
		    			userReport.setIdPicture(reportPicture.getPicture().getId());
		    		}
		    		
		    		/********************************************************************************/

		    		session = entityManager.unwrap(Session.class);
		    	    criteria = session.createCriteria(ReportVideo.class);
		    	    criteria.add(Restrictions.eq("report.id", reportUserInfo.getReportId()));
		    	    criteria.setMaxResults(1);
		    	    reportVideos = criteria.list();
		    	    
		    		if ((reportVideos != null) && (reportVideos.size() > 0))
		    		{
		    			reportVideo = reportVideos.get(0);
		    			userReport.setIdFootage(reportVideo.getVideo().getId());
		    		}
		    		
		    		logger.debug("getByIdForUser : {}", "HttpStatus.OK");
					return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
		    	}
	    	}
	    	catch (Exception e)
			{
		    	logger.debug("getByIdForUser : {}", "HttpStatus.INTERNAL_SERVER_ERROR");
				return new ResponseEntity<UserReport>(userReport, HttpStatus.INTERNAL_SERVER_ERROR);
			}		    	
		}
		
		logger.debug("getByIdForUser : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<UserReport>(userReport, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	/**
   * Serviço que retorna a resposta para um relato feito por um usuário.
   * @param reportUserInfo Objeto JSON recebido no corpo da requisição HTTP,
   * contendo os ID's do usuário e da resposta.
   * @return userReport Objeto JSON com as informações da resposta.
   */
	@RequestMapping(value = "/getReportResponseById", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<UserReport> getReportResponseById(@RequestBody ReportUserInfo reportUserInfo)
	{
		User user;
		Report response;
		UserReport userReport = new UserReport();
		
		if (reportUserInfo != null)
		{		    	
	    	try
	    	{
	    		user = entityManager.find(User.class, reportUserInfo.getUserId());
	    		response = entityManager.find(Report.class, reportUserInfo.getReportId());
	    		
	    		if ((user != null) && (response != null))
			    {
		    		userReport.setReportId(response.getId());
		    		userReport.setUserId(response.getUser().getId());
		    		userReport.setPlaceId(response.getPlace().getId());
		    		userReport.setComment(response.getComment());
		    		userReport.setMadeAt(response.getMadeAt().getTime());
		    		userReport.setResponseId(-1);
					userReport.setResponseVisualized(false);
					
					logger.debug("getReportResponseById : {}", "HttpStatus.OK");
					return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
			    }
	    	}
		    catch (Exception e)
		    {
		    	logger.debug("getReportResponseById : {}", "HttpStatus.INTERNAL_SERVER_ERROR");
				return new ResponseEntity<UserReport>(userReport, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
		
		logger.debug("getReportResponseById : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<UserReport>(userReport, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	/**
   * Serviço que retorna a resposta para um relato feito por um usuário.
   * @param reportUserInfo Objeto JSON recebido no corpo da requisição HTTP,
   * contendo os ID's do usuário e da resposta.
   * @return userReport Objeto JSON com as informações da resposta.
   */
	@RequestMapping(value = "/markReportResponseAsVisualized", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<String> markReportResponseAsVisualized(@RequestBody ReportResponseVisualized reportVisualized)
	{
		String response = "";
		List<ReportResponse> reportResponses;
		ReportResponse reportResponse;
		
		if (reportVisualized != null)
		{
			Session session = entityManager.unwrap(Session.class);
		    Criteria criteria = session.createCriteria(ReportResponse.class);
		    criteria.add(Restrictions.eq("report.id", reportVisualized.getReportId()));
		    criteria.add(Restrictions.eq("response.id", reportVisualized.getResponseId()));
    	    criteria.setMaxResults(1);
		    reportResponses = criteria.list();
		    
		    if ((reportResponses != null) && (reportResponses.size() > 0))
		    {
		    	reportResponse = reportResponses.get(0);
		    	reportResponse.setVisualized(true);
		    	entityManager.merge(reportResponse);
		    	
		    	logger.debug("markReportResponseAsVisualized : {}", "HttpStatus.OK");
				response = "Resposta marcada como lida com sucesso.";
				return new ResponseEntity<String>(response, HttpStatus.OK);
		    }
		    else
		    {
		    	logger.debug("markReportResponseAsVisualized : {}", "HttpStatus.NOT_FOUND");
				response = "Relato não encontrado.";
				return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		    }
		}
		
		logger.debug("markReportResponseAsVisualized : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		response = "JSON recebido está fora do padrão esperado.";
		return new ResponseEntity<String>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	/**
   * Serviço que retorna uma lista de relatos feito por um usuário sobre um(a) local/instituição específico(a).
   * @param userPlaceInfo Objeto JSON recebido no corpo da requisição HTTP,
   * contendo os ID's do usuário e do local.
   * @return userReports Array JSON com os relatos encontrados.
   */
	@RequestMapping(value = "/getPlaceReportsSubmittedByUser", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<List<UserReport>> getPlaceReportsSubmittedByUser(@RequestBody UserPlaceInfo userPlaceInfo) 
	{
		Report response;
		List<Report> reports;
		UserReport userReport;
		List<UserReport> userReports = new ArrayList<>();
		ReportResponse reportResponse;
		List<ReportResponse> reportResponses;
		ReportPicture reportPicture;
		List<ReportPicture> reportPictures;
		ReportVideo reportVideo;
		List<ReportVideo> reportVideos;
				
		if (userPlaceInfo != null)
		{
			try
			{
		    	Session session = entityManager.unwrap(Session.class);
			    Criteria criteria = session.createCriteria(Report.class);
			    criteria.add(Restrictions.eq("user.id", userPlaceInfo.getUserId()));
			    criteria.add(Restrictions.eq("place.id", userPlaceInfo.getPlaceId()));
			    reports = criteria.list();
			    
			    if ((reports != null) && (reports.size() > 0))
			    {
				    for (Report report : reports)
				    {
				    	userReport = new UserReport();
						userReport.setReportId(report.getId());
						userReport.setUserId(report.getUser().getId());
						userReport.setPlaceId(report.getPlace().getId());
						String comment = report.getComment();
						String commentPreview = comment.substring(0, Math.min(comment.length(), 25));
						userReport.setComment(commentPreview); //just a preview
						userReport.setMadeAt(report.getMadeAt().getTime());
						userReport.setResponseId(-1);
						userReport.setResponseVisualized(false);
						
			    		/********************************************************************************/

						session = entityManager.unwrap(Session.class);
			    	    criteria = session.createCriteria(ReportResponse.class);
			    	    criteria.add(Restrictions.eq("report.id", report.getId()));
			    	    criteria.setMaxResults(1);
			    	    reportResponses = criteria.list();
			    	    
			    		if ((reportResponses != null) && (reportResponses.size() > 0))
			    		{
			    			reportResponse = reportResponses.get(0);
			    			response = reportResponse.getResponse();
			    			
			    			if (response != null)
			    			{
			    				userReport.setResponseId(response.getId());
			    				userReport.setResponseVisualized(reportResponse.isVisualized());
			    			}
			    		}
			    		
			    		/********************************************************************************/
			    		
			    		session = entityManager.unwrap(Session.class);
			    	    criteria = session.createCriteria(ReportPicture.class);
			    	    criteria.add(Restrictions.eq("report.id", report.getId()));
			    	    criteria.setMaxResults(1);
			    	    reportPictures = criteria.list();
			    	    
			    		if ((reportPictures != null) && (reportPictures.size() > 0))
			    		{
			    			reportPicture = reportPictures.get(0);
			    			userReport.setIdPicture(reportPicture.getPicture().getId());
			    		}
			    		
			    		/********************************************************************************/

			    		session = entityManager.unwrap(Session.class);
			    	    criteria = session.createCriteria(ReportVideo.class);
			    	    criteria.add(Restrictions.eq("report.id", report.getId()));
			    	    criteria.setMaxResults(1);
			    	    reportVideos = criteria.list();
			    	    
			    		if ((reportVideos != null) && (reportVideos.size() > 0))
			    		{
			    			reportVideo = reportVideos.get(0);
			    			userReport.setIdFootage(reportVideo.getVideo().getId());
			    		}
			    		
			    		/********************************************************************************/
						
						userReports.add(userReport);
				    }
			    }
		    }
			catch (Exception e)
		    {
		    	logger.debug("getPlaceReportsSubmittedByUser : {}", "HttpStatus.INTERNAL_SERVER_ERROR");
				return new ResponseEntity<List<UserReport>>(userReports, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		    
	    	logger.debug("getPlaceReportsSubmittedByUser : {}", "HttpStatus.OK");
			return new ResponseEntity<List<UserReport>>(userReports, HttpStatus.OK);
		}
		
    	logger.debug("getPlaceReportsSubmittedByUser : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<List<UserReport>>(userReports, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	/**
   * Serviço que retorna uma lista de respostas não-visualizadas para relatos feitos por um usuário.
   * @param userIdInfo Objeto JSON recebido no corpo da requisição HTTP, contendo os ID do usuário.
   * @return newResponses Array JSON com a lista de novas respostas.
   */
	@RequestMapping(value = "/getNewResponsesForUserReports", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<List<NewReportResponseInfo>> getNewResponsesForUserReports(@RequestBody UserIdInfo userIdInfo)
	{
		User user;
		List<ReportResponse> reportResponses;
		NewReportResponseInfo newResponse;
		List<NewReportResponseInfo> newResponses = new ArrayList<>();
		
		if (userIdInfo != null)
		{
			try
			{
				user = entityManager.find(User.class, userIdInfo.getId());
				
				if (user != null)
				{
					Session session = entityManager.unwrap(Session.class);
		    	    Criteria criteria = session.createCriteria(ReportResponse.class, "reportResponse");
		    	    criteria.createAlias("reportResponse.report", "report");
		    	    criteria.add(Restrictions.eq("reportResponse.visualized", false));
		    	    criteria.add(Restrictions.eq("report.user.id", userIdInfo.getId()));
		    	    reportResponses = criteria.list();

		    		if ((reportResponses != null) && (reportResponses.size() > 0))
		    		{
		    			for (ReportResponse reportResponse: reportResponses)
		    			{
		    				newResponse = new NewReportResponseInfo();
		    				newResponse.setUserId(reportResponse.getReport().getUser().getId());
		    				newResponse.setPlaceId(reportResponse.getReport().getPlace().getId());
		    				newResponse.setReportId(reportResponse.getReport().getId());
		    				newResponse.setResponseId(reportResponse.getResponse().getId());
		    				
		    				newResponses.add(newResponse);
		    			}
		    		}
				}
			}
			catch (Exception e)
			{
				logger.debug("getNewResponsesForUserReports : {}", "HttpStatus.INTERNAL_SERVER_ERROR");
				return new ResponseEntity<List<NewReportResponseInfo>>(newResponses, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			logger.debug("getNewResponsesForUserReports : {}", "HttpStatus.OK");
			return new ResponseEntity<List<NewReportResponseInfo>>(newResponses, HttpStatus.OK);
		}
		
		logger.debug("getNewResponsesForUserReports : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<List<NewReportResponseInfo>>(newResponses, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	
	/**
   * Serviço que recebe e salva no Banco de Dados um novo relato feito por um usuário.
   * @param userReport Objeto JSON recebido no corpo da requisição HTTP, contendo as informações do relato.
   * @return reportUserInfo Objeto JSON confirmando o recebimento do relato.
   */
	@RequestMapping(value = "/saveNewReport", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<ReportUserInfo> saveNewReport(@RequestBody UserReport userReport) 
	{
		User user;
		Place place;
		Report newReport;
		Report report;
		List<Report> reports;
		ReportUserInfo reportUserInfo = new ReportUserInfo();
		
		if (userReport != null)
		{
			try
			{
				user = entityManager.find(User.class, userReport.getUserId());
				place = entityManager.find(Place.class, userReport.getPlaceId());
				
				if ((user != null) && (place != null))
				{
					Session session = entityManager.unwrap(Session.class);
				    Criteria criteria = session.createCriteria(Report.class);
				    criteria.add(Restrictions.eq("user.id", user.getId()));
				    criteria.add(Restrictions.eq("place.id", place.getId()));
				    criteria.add(Restrictions.eq("madeAt", new Timestamp(userReport.getMadeAt())));
				    criteria.setMaxResults(1);
				    reports = criteria.list();
					
					if ((reports != null) && (reports.size() > 0))
					{
						report = reports.get(0);
						
						reportUserInfo.setUserId(report.getUser().getId());
						reportUserInfo.setPlaceId(report.getPlace().getId());
						reportUserInfo.setReportId(report.getId());
						
						logger.debug("saveNewReport : {}", "HttpStatus.OK");
						return new ResponseEntity<ReportUserInfo>(reportUserInfo, HttpStatus.OK);
					}
					else
					{
						newReport = new Report();
						newReport.setUser(user);
						newReport.setPlace(place);
						newReport.setComment(userReport.getComment());
						newReport.setMadeAt(new Timestamp(userReport.getMadeAt()));
						Date date = new Date();
						newReport.setReceivedAt(new Timestamp(date.getTime()));
						entityManager.persist(newReport);
						entityManager.flush();
						
						reportUserInfo.setUserId(newReport.getUser().getId());
						reportUserInfo.setPlaceId(newReport.getPlace().getId());
						reportUserInfo.setReportId(newReport.getId());
								
						logger.debug("saveNewReport : {}", "HttpStatus.OK");
						return new ResponseEntity<ReportUserInfo>(reportUserInfo, HttpStatus.OK);
					}
				}
			}
			catch (Exception e)
			{
				logger.debug("saveNewReport : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.toString());
				return new ResponseEntity<ReportUserInfo>(reportUserInfo, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.debug("saveNewReport : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<ReportUserInfo>(reportUserInfo, HttpStatus.UNPROCESSABLE_ENTITY);
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
   * Serviço que recebe e salva no Banco de Dados uma imagem relacionada a um novo relato feito por um usuário.
   * @param reportId Parâmetro enviado via form contendo o ID do relato.
   * @param file Parâmetro enviado via form contendo o arquivo da imagem.
   * @return docInfo Objeto JSON confirmando o recebimento da imagem.
   */
	@RequestMapping(value = "/uploadReportPicture", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<UploadedDocumentInfo> uploadReportPicture(@RequestParam("reportId") int reportId, @RequestParam("file") MultipartFile file) 
	{
		UploadedDocumentInfo docInfo = null;
		UploadedFiles pictureFile;
		ReportPicture reportPicture;
		List<ReportPicture> reportPictures;
		/*&& 
		((file.getContentType().equals("image/jpeg")) || (file.getContentType().equals("image/png"))))*/
		if (!file.isEmpty() &&
				((file.getContentType().equals("image/jpeg")) || (file.getContentType().equals("image/png")))) 
		{			
			try
			{
				Report report = entityManager.find(Report.class, reportId);
				
				if (report != null)
				{
	                Session session = entityManager.unwrap(Session.class);
				    Criteria criteria = session.createCriteria(ReportPicture.class);
				    criteria.add(Restrictions.eq("report.id", reportId));
				    reportPictures = criteria.list();
	                
				    if ((reportPictures != null) && (reportPictures.size() > 0))
				    {
				    	reportPicture = reportPictures.get(0);
				    	
				    	pictureFile = reportPicture.getPicture();
				    	setUploadedFileAttributesFromMultipartFile(pictureFile, file);
		                entityManager.merge(pictureFile);
				    }
				    else
				    {
				    	pictureFile = new UploadedFiles();
				    	setUploadedFileAttributesFromMultipartFile(pictureFile, file);
		                entityManager.persist(pictureFile);
		                entityManager.flush();
				    	
				    	reportPicture = new ReportPicture();
				    	reportPicture.setReport(report);
				    	reportPicture.setPicture(pictureFile);
		                entityManager.persist(reportPicture);
				    }
				    entityManager.flush();

	                docInfo = new UploadedDocumentInfo();
					docInfo.setOwnerId(reportId);
					docInfo.setFileId(pictureFile.getId());
					docInfo.setFileName(pictureFile.getName());
					
					logger.debug("uploadReportPicture : {}", "HttpStatus.OK");
					return new ResponseEntity<UploadedDocumentInfo>(docInfo, HttpStatus.OK);
				}
			}
			catch (Exception e)
			{
				logger.debug("uploadReportPicture : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.getLocalizedMessage());
				return new ResponseEntity<UploadedDocumentInfo>(docInfo, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.debug("uploadReportPicture : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<UploadedDocumentInfo>(docInfo, HttpStatus.UNPROCESSABLE_ENTITY);
	}


	/**
   * Serviço que recupera do Banco de Dados e retorna uma imagem relacionada a um relato feito por um usuário.
   * @param id Parâmetro enviado via URL contendo o ID do arquivo de imagem.
   * @param response Conexão HTTP para envio do arquivo como resposta à requisição.
   */
	
	
	/**
   * Serviço que recebe e salva no Banco de Dados um vídeo relacionado a um novo relato feito por um usuário.
   * @param reportId Parâmetro enviado via form contendo o ID do relato.
   * @param file Parâmetro enviado via form contendo o arquivo da filmagem.
   * @return docInfo Objeto JSON confirmando o recebimento da filmagem.
   */
	@RequestMapping(value = "/uploadReportFootage", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<UploadedDocumentInfo> uploadReportFootage(@RequestParam("reportId") int reportId, @RequestParam("file") MultipartFile file) 
	{
		UploadedDocumentInfo docInfo = null;
		UploadedFiles videoFile;
		ReportVideo reportVideo;
		List<ReportVideo> reportVideos;
				
		if (!file.isEmpty() /*&& 
				((file.getContentType().equals("video/mp4")) || (file.getContentType().equals("video/avi")))*/)
		{			
			try
			{
				Report report = entityManager.find(Report.class, reportId);
				
				if (report != null)
				{
	                Session session = entityManager.unwrap(Session.class);
				    Criteria criteria = session.createCriteria(ReportVideo.class);
				    criteria.add(Restrictions.eq("report.id", reportId));
				    reportVideos = criteria.list();
	                
				    if ((reportVideos != null) && (reportVideos.size() > 0))
				    {
				    	reportVideo = reportVideos.get(0);
				    	
				    	videoFile = reportVideo.getVideo();
				    	setUploadedFileAttributesFromMultipartFile(videoFile, file);
		                entityManager.merge(videoFile);
				    }
				    else
				    {
				    	videoFile = new UploadedFiles();
				    	setUploadedFileAttributesFromMultipartFile(videoFile, file);
		                entityManager.persist(videoFile);
		                entityManager.flush();
				    	
				    	reportVideo = new ReportVideo();
				    	reportVideo.setReport(report);
				    	reportVideo.setVideo(videoFile);
		                entityManager.persist(reportVideo);
				    }
				    entityManager.flush();

	                docInfo = new UploadedDocumentInfo();
					docInfo.setOwnerId(reportId);
					docInfo.setFileId(videoFile.getId());
					docInfo.setFileName(videoFile.getName());
					
					logger.debug("uploadReportFootage : {}", "HttpStatus.OK");
					return new ResponseEntity<UploadedDocumentInfo>(docInfo, HttpStatus.OK);
				}
			}
			catch (Exception e)
			{
				logger.debug("uploadReportFootage : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.toString());
				return new ResponseEntity<UploadedDocumentInfo>(docInfo, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.debug("uploadReportFootage : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<UploadedDocumentInfo>(docInfo, HttpStatus.UNPROCESSABLE_ENTITY);
	}


	/**
   * Serviço que recupera do Banco de Dados e retorna um vídeo relacionada a um relato feito por um usuário.
   * @param id Parâmetro enviado via URL contendo o ID do arquivo de vídeo.
   * @param response Conexão HTTP para envio do arquivo como resposta à requisição.
   */
	
}
