package com.comune.view;


import java.sql.Timestamp;

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

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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


/**
* Esse arquivo implementa serviços relacionados a Pesquisas.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/surveys")
public class SurveysView 
{
	@PersistenceContext(unitName = "comunePersistenceUnit")
	private EntityManager entityManager;
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(SurveysView.class);
		
		
	/**
   * Serviço que cadastra uma pesquisa completa (com descrição, todas as questões, etc).
   * @param entireSurvey Objeto JSON com todo o conteúdo da pesquisa.
   * @return response Texto com informações sobre o sucesso ou falha da operação.
   */
	@RequestMapping(value = "/registerNewSurveyWithQuestions", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<String> registerNewSurveyWithQuestions(@RequestBody EntireSurvey newSurvey) 
	{
		String response = "";
		Survey survey = null;
		Question question = null;
		QuestionType questionType = null;
		SurveyQuestion surveyQuestion = null;
		
		AnswerOption newAnswerOption = null;
		QuestionAnswerOption questionAnswerOption = null;
		
		if (newSurvey != null)
		{		
			try
			{	
				survey = new Survey();
				survey.setDescription(newSurvey.getDescription());
				Date date = new Date();
				survey.setCreatedAt(new Timestamp(date.getTime()));
				entityManager.persist(survey);
				
				List<EntireQuestion> entireQuestions = newSurvey.getQuestions();
				for (EntireQuestion entireQuestion : entireQuestions)
				{
					question = new Question();
					question.setDescr(entireQuestion.getDescr());
					question.setMandatory(entireQuestion.isMandatory());
					
					questionType = entityManager.find(QuestionType.class, entireQuestion.getType());
					
					if (questionType != null)
					{
						question.setQuestionType(questionType);
						entityManager.persist(question);
						
						if (Constants.TIPO_QUESTAO_MULTIPLA_ESCOLHA == entireQuestion.getType())
						{
							MultipleChoiceQuestion checkboxesQuestion = (MultipleChoiceQuestion) entireQuestion;
							List<AnswerOption> answerOptions = checkboxesQuestion.getAnswerOptions();
							
							for (AnswerOption answerOption : answerOptions)
							{
								newAnswerOption = new AnswerOption();
								newAnswerOption.setHeader(answerOption.getHeader());
								entityManager.persist(newAnswerOption);

								questionAnswerOption = new QuestionAnswerOption();
								questionAnswerOption.setQuestion(question);
								questionAnswerOption.setAnswerOption(newAnswerOption);
								entityManager.persist(questionAnswerOption);
							}
						}
						else if (Constants.TIPO_QUESTAO_CHECKBOXES == entireQuestion.getType())
						{
							CheckboxesQuestion checkboxesQuestion = (CheckboxesQuestion) entireQuestion;
							List<AnswerOptionStatus> answerOptionsStatus = checkboxesQuestion.getAnswerOptions();
							
							for (AnswerOptionStatus answerOptionStatus : answerOptionsStatus)
							{
								newAnswerOption = new AnswerOption();
								newAnswerOption.setHeader(answerOptionStatus.getHeader());
								entityManager.persist(newAnswerOption);

								questionAnswerOption = new QuestionAnswerOption();
								questionAnswerOption.setQuestion(question);
								questionAnswerOption.setAnswerOption(newAnswerOption);
								entityManager.persist(questionAnswerOption);
							}
						}
												
						surveyQuestion = new SurveyQuestion();
						surveyQuestion.setSurvey(survey);
						surveyQuestion.setQuestion(question);
						entityManager.persist(surveyQuestion);
					}
				}				    
			        
			    logger.debug("registerNewSurveyWithQuestions : {}", "HttpStatus.OK");
				response = "Questionário cadastrado com sucesso.";
				return new ResponseEntity<String>(response, HttpStatus.OK);
			
			}
			catch (Exception e)
			{
				logger.debug("registerNewSurveyWithQuestions : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.getLocalizedMessage());
				response = "Ocorreu um erro.";
				return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.debug("registerNewSurveyWithQuestions : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		response = "JSON recebido está fora do padrão esperado.";
		return new ResponseEntity<String>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	
	/**
   * Serviço que retorna uma pesquisa completa (com descrição, todas as questões, etc).
   * @param userSurveyInfo Objeto JSON recebido no corpo da requisição HTTP, 
   * contendo o id do usuário interessado na pesquisa e o id da pesquisa a ser retornada.
   * @return entireSurvey Objeto JSON com todo o conteúdo da pesquisa.
   */
	@RequestMapping(value = "/getSurveyById", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<EntireSurvey> getSurveyById(@RequestBody UserSurveyInfo userSurveyInfo) 
	{
		User user;
		Survey survey;
		EntireSurvey entireSurvey = null;
		Question question;
		EntireQuestion entireQuestion;
		List<EntireQuestion> entireQuestions;
		List<SurveyQuestion> surveyQuestions;
		List<QuestionAnswerOption> questionAnswerOptions;
		AnswerOption answerOption;
		
		if (userSurveyInfo != null)
		{
			try
	    	{
				user = entityManager.find(User.class, userSurveyInfo.getUserId());
		    	survey = entityManager.find(Survey.class, userSurveyInfo.getSurveyId());
				
			    if ((user != null) && (survey != null))
			    {
		    		entireSurvey = new EntireSurvey();
		    		entireSurvey.setSurveyId(survey.getId());
		    		entireSurvey.setDescription(survey.getDescription());
		    		entireSurvey.setAvailableSince(survey.getCreatedAt().getTime());
		    		
		    		Session session = entityManager.unwrap(Session.class);
		    	    Criteria criteria = session.createCriteria(SurveyQuestion.class);
		    	    criteria.add(Restrictions.eq("survey.id", survey.getId()));
		    	    surveyQuestions = criteria.list();
		    	    
		    	    entireQuestions = new ArrayList<>();
		    	    
		    	    for (SurveyQuestion surveyQuestion : surveyQuestions)
		    	    {
		    	    	question = surveyQuestion.getQuestion();

		    	    	int questionType = question.getQuestionType().getId();
		    	    	
		    	    	entireQuestion = new EntireQuestion();
		    	    	
		    	    	if (questionType == Constants.TIPO_QUESTAO_SIM_NAO)
		    	    	{
		    	    		entireQuestion = new YesOrNoQuestion();
		    	    	}
		    	    	else if (questionType == Constants.TIPO_QUESTAO_ESCALA) 
		    	    	{
		    	    		entireQuestion = new RatingQuestion();
		    	    	}
		    	    	else if (questionType == Constants.TIPO_QUESTAO_MULTIPLA_ESCOLHA) 
		    	    	{
		    	    		entireQuestion = new MultipleChoiceQuestion();
		    	    	}
		    	    	else if (questionType == Constants.TIPO_QUESTAO_CHECKBOXES) 
		    	    	{
		    	    		entireQuestion = new CheckboxesQuestion();
		    	    	}			    	    	
		    	    	entireQuestion.setId(question.getId());
		    	    	entireQuestion.setType(question.getQuestionType().getId());
		    	    	entireQuestion.setDescr(question.getDescr());
		    	    	entireQuestion.setMandatory(question.isMandatory());
		    	    	
		    	    	if ((questionType == Constants.TIPO_QUESTAO_MULTIPLA_ESCOLHA) 
		    	    			|| (questionType == Constants.TIPO_QUESTAO_CHECKBOXES))
		    	    	{
			    	    	session = entityManager.unwrap(Session.class);
				    	    criteria = session.createCriteria(QuestionAnswerOption.class);
				    	    criteria.add(Restrictions.eq("question.id", question.getId()));
				    	    questionAnswerOptions = criteria.list();
				    	    					    	    
				    	    if (questionType == Constants.TIPO_QUESTAO_MULTIPLA_ESCOLHA) 
			    	    	{
				    			List<AnswerOption> answerOptions = new ArrayList<>();
					    	    for (QuestionAnswerOption questionAnswerOption : questionAnswerOptions)
					    	    {
					    	    	answerOption = questionAnswerOption.getAnswerOption();					    	    	
					    	    	answerOptions.add(answerOption);
					    	    }
					    	    
					    	    ((MultipleChoiceQuestion) entireQuestion).setAnswerOptions(answerOptions);
			    	    	}
			    	    	else if (questionType == Constants.TIPO_QUESTAO_CHECKBOXES) 
			    	    	{
			    	    		List<AnswerOptionStatus> answerOptions = new ArrayList<>();
					    	    for (QuestionAnswerOption questionAnswerOption : questionAnswerOptions)
					    	    {
					    	    	answerOption = questionAnswerOption.getAnswerOption();
					    	    	
					    	    	AnswerOptionStatus answerOptionStatus = new AnswerOptionStatus();
					    	    	answerOptionStatus.setId(questionAnswerOption.getAnswerOption().getId());
					    	    	answerOptionStatus.setHeader(questionAnswerOption.getAnswerOption().getHeader());
					    	    	answerOptions.add(answerOptionStatus);
					    	    }
					    	    
			    	    		((CheckboxesQuestion) entireQuestion).setAnswerOptions(answerOptions);
			    	    	}
		    	    	}
		    	    	
		    	    	entireQuestions.add(entireQuestion);
		    	    }
		    	    
		    	    entireSurvey.setQuestions(entireQuestions);
		    	    
		    	    logger.debug("getSurveyById : {}", "HttpStatus.OK");
					return new ResponseEntity<EntireSurvey>(entireSurvey, HttpStatus.OK);
		    	}
	    	}
			catch (Exception e)
			{
				logger.debug("getSurveyById : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.toString());
				return new ResponseEntity<EntireSurvey>(entireSurvey, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.debug("getSurveyById : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<EntireSurvey>(entireSurvey, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	/**
   * Serviço que recebe as respostas de uma pesquisa completada por um usuário e as armazena no Banco de Dados.
   * @param entireSurvey Objeto JSON recebido no corpo da requisição HTTP, 
   * contendo as respostas de um usuário para uma pesquisa.
   * @return answersStored Objeto JSON que retorna o id do usuário que enviou as respostas,
   *  o id da pesquisa respondida e o id do local ao qual a pesquisa se refere.
   */
	@RequestMapping(value = "/saveSurveyAnswers", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<SurveyAnswersStored> saveSurveyAnswers(@RequestBody EntireSurvey entireSurvey) 
	{
		User user;
		Place place;
		Survey survey;
		SurveyAnswersStored answersStored = new SurveyAnswersStored();
		
		if (entireSurvey != null)
		{
			try
			{
				user = entityManager.find(User.class, entireSurvey.getUserId());
				place = entityManager.find(Place.class, entireSurvey.getPlaceId());
				survey = entityManager.find(Survey.class, entireSurvey.getSurveyId());
				
			    //Verifica se a pesquisa está liberada para o local
				PlaceSurveys placeSurvey;
				List<PlaceSurveys> placeSurveys; 
				Session session = entityManager.unwrap(Session.class);
				Criteria criteria = session.createCriteria(PlaceSurveys.class);
			    criteria.add(Restrictions.eq("place.id", place.getId()));
			    criteria.add(Restrictions.eq("survey.id", survey.getId()));
			    criteria.setMaxResults(1);
			    placeSurveys = criteria.list();

				if ((placeSurveys != null) || (placeSurveys.size() == 0))
				{
					placeSurvey = placeSurveys.get(0);
					
					//Verifica se a pesquisa já foi respondida pelo usuário
					List<CompletedSurvey> completedSurveys; 
					session = entityManager.unwrap(Session.class);
				    criteria = session.createCriteria(CompletedSurvey.class);
				    criteria.add(Restrictions.eq("user.id", user.getId()));
				    criteria.add(Restrictions.eq("placeSurvey.id", placeSurvey.getId()));
				    completedSurveys = criteria.list();
								    
					if ((completedSurveys == null) || (completedSurveys.size() == 0))
					{
						QuestionAnswerOption questionAnswerOption;
						List<QuestionAnswerOption> questionAnswerOptions;
						
						CompletedSurvey completedSurvey = new CompletedSurvey();
						completedSurvey.setUser(user);
						completedSurvey.setPlaceSurvey(placeSurvey);
						completedSurvey.setStartedAt(new Timestamp(entireSurvey.getStartedAt()));
						completedSurvey.setCompletedAt(new Timestamp(entireSurvey.getCompletedAt()));
						entityManager.persist(completedSurvey);
						entityManager.flush();
						
						List<EntireQuestion> entireQuestions = entireSurvey.getQuestions();
						for (EntireQuestion entireQuestion : entireQuestions)
						{
							/*logger.debug("entireQuestion.getType(): {}", entireQuestion.getType());	
							logger.debug("entireQuestion.getDescr(): {}", entireQuestion.getDescr());	
							logger.debug("entireQuestion.getId(): {}", entireQuestion.getId());*/	

							Question question = entityManager.find(Question.class, entireQuestion.getId());
							if (question != null)
							{
								int type = question.getQuestionType().getId();
								//logger.debug("question.getQuestionType().getId() 2: {}", type);	

								if (Constants.TIPO_QUESTAO_SIM_NAO == type)
								{
									//logger.debug("TIPO_QUESTAO_SIM_NAO: {}", entireQuestion.getType());	

									YesNoQuestionAnswer yesNoQuestionAnswer = new YesNoQuestionAnswer();
									yesNoQuestionAnswer.setCompletedSurvey(completedSurvey);
									yesNoQuestionAnswer.setQuestion(question);
									yesNoQuestionAnswer.setAnswer(((YesOrNoQuestion)entireQuestion).getAnswer());
									entityManager.persist(yesNoQuestionAnswer);
									//logger.debug("yesNoQuestionAnswer: {}", yesNoQuestionAnswer);	

								}
								else if (Constants.TIPO_QUESTAO_ESCALA == type)
								{
									//logger.debug("TIPO_QUESTAO_ESCALA: ()", entireQuestion.getType());	

									RatingQuestionAnswer ratingQuestionAnswer = new RatingQuestionAnswer();
									ratingQuestionAnswer.setCompletedSurvey(completedSurvey);
									ratingQuestionAnswer.setQuestion(question);
									ratingQuestionAnswer.setAnswer(((RatingQuestion)entireQuestion).getAnswer());
									entityManager.persist(ratingQuestionAnswer);
								}
								else if (Constants.TIPO_QUESTAO_MULTIPLA_ESCOLHA == type)
								{
									//logger.debug("TIPO_QUESTAO_MULTIPLA_ESCOLHA: ()", entireQuestion.getType());	

									//AnswerOption answerOption = entityManager.find(AnswerOption.class, ((MultipleChoiceQuestion)entireQuestion).getAnswerId());
										
									int answerOptionId = ((MultipleChoiceQuestion)entireQuestion).getAnswerId();
									
									/*if (answerOption != null)
									{*/
									session = entityManager.unwrap(Session.class);
								    criteria = session.createCriteria(QuestionAnswerOption.class);
								    criteria.add(Restrictions.eq("question.id", question.getId()));
								    criteria.add(Restrictions.eq("answerOption.id", answerOptionId));
								    criteria.setMaxResults(1);
								    questionAnswerOptions = criteria.list();

									if (questionAnswerOptions != null)
									{
										questionAnswerOption = questionAnswerOptions.get(0);
										
										MultipleChoiceQuestionAnswer multipleChoiceQuestionAnswer = new MultipleChoiceQuestionAnswer();
										multipleChoiceQuestionAnswer.setCompletedSurvey(completedSurvey);
										//multipleChoiceQuestionAnswer.setQuestionAnswerOption(questionAnswerOption);
										multipleChoiceQuestionAnswer.setQuestion(questionAnswerOption.getQuestion());
										multipleChoiceQuestionAnswer.setAnswer(questionAnswerOption.getAnswerOption());
										entityManager.persist(multipleChoiceQuestionAnswer);
									}
									//}
								}
								else if (Constants.TIPO_QUESTAO_CHECKBOXES == type)
								{
									//logger.debug("TIPO_QUESTAO_CHECKBOXES: ()", entireQuestion.getType());	

									CheckboxesQuestion checkboxesQuestion = (CheckboxesQuestion) entireQuestion;
									List<AnswerOptionStatus> answerOptionsStatus = checkboxesQuestion.getAnswerOptions();
									
									for (AnswerOptionStatus answerOptionStatus : answerOptionsStatus)
									{
										AnswerOption answerOption = entityManager.find(AnswerOption.class, answerOptionStatus.getId());
										
										if (answerOption != null)
										{
											session = entityManager.unwrap(Session.class);
										    criteria = session.createCriteria(QuestionAnswerOption.class);
										    criteria.add(Restrictions.eq("question.id", question.getId()));
										    criteria.add(Restrictions.eq("answerOption.id", answerOption.getId()));
										    criteria.setMaxResults(1);
										    questionAnswerOptions = criteria.list();

											if (questionAnswerOptions != null)
											{
												questionAnswerOption = questionAnswerOptions.get(0);
												
												CheckboxesQuestionAnswer checkboxesQuestionAnswer = new CheckboxesQuestionAnswer();
												checkboxesQuestionAnswer.setCompletedSurvey(completedSurvey);
												checkboxesQuestionAnswer.setQuestionAnswerOption(questionAnswerOption);
												checkboxesQuestionAnswer.setChecked(answerOptionStatus.isChecked());
												entityManager.persist(checkboxesQuestionAnswer);
											}
										}
									}
								}
							}
						}
											
						answersStored.setUserId(user.getId());
						answersStored.setPlaceId(place.getId());
						answersStored.setSurveyId(survey.getId());
								
						return new ResponseEntity<SurveyAnswersStored>(answersStored, HttpStatus.OK);
					}
					else
					{
						logger.debug("saveSurveyAnswers : {}", "HttpStatus.CONFLICT");
						return new ResponseEntity<SurveyAnswersStored>(answersStored, HttpStatus.CONFLICT);
					}
				}
			}
			catch (Exception e)
			{
				logger.debug("saveSurveyAnswers : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.toString());
				return new ResponseEntity<SurveyAnswersStored>(answersStored, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.debug("saveSurveyAnswers : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<SurveyAnswersStored>(answersStored, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	
	/**
   * Serviço que retorna os ID's das pesquisas disponíveis para um usuário e um(a) determinado(a) local/instituição.
   * @param userPlaceInfo Objeto JSON recebido no corpo da requisição HTTP, 
   * contendo os ID's do usuário e do local.
   * @return availableSurveys Objeto JSON que retorna o ID do usuário que fez a solicitação, 
   * o ID do local em que o usuário está localizado e um array de ID's de pesquivas disponíveis.
   */
	@RequestMapping(value = "/getAvailableSurveys", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<AvailableSurveys> getAvailableSurveys(@RequestBody UserPlaceInfo userPlaceInfo) 
	{
		AvailableSurveys availableSurveys = null;
		
		if (userPlaceInfo != null)
		{
			try
			{
				User user = entityManager.find(User.class, userPlaceInfo.getUserId());
				Place place = entityManager.find(Place.class, userPlaceInfo.getPlaceId());	
				
				List<CompletedSurvey> completedSurveys; 
				Session session = entityManager.unwrap(Session.class);
			    Criteria criteria = session.createCriteria(CompletedSurvey.class, "completedSurvey");
	    	    criteria.createAlias("completedSurvey.placeSurvey", "placeSurvey");
			    criteria.add(Restrictions.eq("completedSurvey.user.id", user.getId()));
			    criteria.add(Restrictions.eq("placeSurvey.place.id", place.getId()));
			    completedSurveys = criteria.list();
			    
			    ArrayList<Integer> completedSurveysIds = null;
			    if ((completedSurveys != null) && (completedSurveys.size() > 0))
			    {
				    completedSurveysIds = new ArrayList<>();
				    for (CompletedSurvey completedSurvey: completedSurveys)
				    {
				    	completedSurveysIds.add(completedSurvey.getPlaceSurvey().getSurvey().getId());
				    }
			    }
			    
				List<PlaceSurveys> placeSurveys; 
			    session = entityManager.unwrap(Session.class);
			    criteria = session.createCriteria(PlaceSurveys.class);
			    criteria.add(Restrictions.eq("place.id", userPlaceInfo.getPlaceId()));
			    if (completedSurveysIds != null)
			    {
				    criteria.add(Restrictions.not(
				    	    Restrictions.in("survey.id", completedSurveysIds)
				    	  ));
			    }
				placeSurveys = criteria.list();
			    			    
			    ArrayList<Survey> surveys = new ArrayList<>();
			    for (PlaceSurveys placeSurvey: placeSurveys)
			    {
			    	surveys.add(placeSurvey.getSurvey());
			    }
			    availableSurveys = new AvailableSurveys();
			    availableSurveys.setUserId(userPlaceInfo.getUserId());
			    availableSurveys.setPlaceId(userPlaceInfo.getPlaceId());
			    availableSurveys.setSurveys(surveys);
				
				logger.debug("getAvailableSurvey : {}", "HttpStatus.OK");
				return new ResponseEntity<AvailableSurveys>(availableSurveys, HttpStatus.OK);
			}
			catch (Exception e)
			{
				logger.debug("getAvailableSurvey : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.toString());
				return new ResponseEntity<AvailableSurveys>(availableSurveys, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.debug("getAvailableSurvey : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		return new ResponseEntity<AvailableSurveys>(availableSurveys, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
}
