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

import java.sql.Time;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


/**
* Esse arquivo implementa serviços relacionados a Locais/Instituições.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/places")
public class PlacesView 
{
	@PersistenceContext(unitName = "comunePersistenceUnit")
	private EntityManager entityManager;
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(PlacesView.class);

	
	/**
   * Serviço que cadastra um novo estabelecimento público.
   * @param newPlace Objeto JSON com os dados do novo estabelecimento.
   * @return response Texto com informações sobre o sucesso ou falha da operação.
   */
	@RequestMapping(value="/registerNewPlace", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<String> registerNewPlace(@RequestBody PublicInstitution newPlace)
	{
		String response = "";
		if (newPlace != null)
		{		
			try
			{
				Subcategory subcategory = entityManager.find(Subcategory.class, newPlace.getSubcategory());

				if (subcategory != null)
				{
					Geometry geom = Functions.wktToGeometry("POINT(" + newPlace.getLongitude() + " " + newPlace.getLatitude() + ")"); //POINT(lng lat)
			        Place place = new Place();
			        place.setName(newPlace.getName());
			        place.setAbbrevName(newPlace.getAbbrevName());
			        place.setAddress(newPlace.getAddress());
			        place.setStatus(newPlace.getStatus());
			        place.setRating(Double.valueOf(0.0));
			        //place.setRating(newPlace.getRating());
			        //place.setOpeningTime(newPlace.getOpeningTime());
			        //place.setClosingTime(newPlace.getClosingTime());
			        place.setLocation((Point) geom);
			        place.setSubcategory(subcategory);

			        entityManager.persist(place);
			        entityManager.flush();
			        
			        List<WorkingDay> workingDays = newPlace.getWorkingDays();
			        
			        PlaceWorkingDays placeWorkingDay;
			        if ((workingDays != null) && (workingDays.size() > 0))
			        {
			        	for (WorkingDay workingDay : workingDays)
			        	{
			        		placeWorkingDay = new PlaceWorkingDays();
			        		placeWorkingDay.setPlace(place);
			        		placeWorkingDay.setDayOfTheWeek(workingDay.getDayOfTheWeek());
			        		Time opening = new Time(workingDay.getOpeningTime());
			        		placeWorkingDay.setOpeningTime(opening);
			        		Time closing = new Time(workingDay.getClosingTime());
			        		placeWorkingDay.setClosingTime(closing);
					        entityManager.persist(placeWorkingDay);
			        	}
			        }
				    
			        logger.debug("registerNewPlace : {}", "HttpStatus.OK");
					response = "Estabelecimento cadastrado com sucesso.";
					return new ResponseEntity<String>(response, HttpStatus.OK);
				}
			}
			catch (Exception e)
			{
				logger.debug("registerNewPlace : {}", "HttpStatus.INTERNAL_SERVER_ERROR: " + e.getLocalizedMessage());
				response = "Ocorreu um erro.";
				return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.debug("registerNewPlace : {}", "HttpStatus.UNPROCESSABLE_ENTITY");
		response = "JSON recebido está fora do padrão esperado.";
		return new ResponseEntity<String>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	/**
   * Serviço que retorna informações de um(a) local/instituição.
   * @param id Parâmetro enviado via URL contendo o id do local a ser buscado no Banco de Dados.
   * @return institution Objeto JSON com as informações da instituição.
   */
	@RequestMapping("/getPlaceById")
	@Transactional
	public ResponseEntity<PublicInstitution> getPlaceById(@PathParam("id") int id) 
	{
		PublicInstitution institution = new PublicInstitution();
		
		try
		{
			Place place = entityManager.find(Place.class, id);
						
			if ((place != null) && (place.getStatus() == Constants.STATUS_ATIVO))
			{
				institution.setId(place.getId());
				institution.setName(place.getName());	
				institution.setNickname(place.getNickname());
				institution.setAbbrevName(place.getAbbrevName());
				institution.setStatus(place.getStatus());
				institution.setLongitude(place.getLocation().getX());
				institution.setLatitude(place.getLocation().getY());
				institution.setAddress(place.getAddress());
				//institution.setRating(place.getRating());
				//institution.setOpeningTime(place.getOpeningTime());
				//institution.setClosingTime(place.getClosingTime());
				institution.setCategory(place.getSubcategory().getCategory().getId());
				institution.setSubcategory(place.getSubcategory().getId());
				
				/* Cálcula nota média ***********/
		    	Double rating = 0.0;

				Session session = entityManager.unwrap(Session.class);
			    Criteria criteria = session.createCriteria(PlaceSurveys.class);
			    criteria.add(Restrictions.eq("place", place));
			    List<PlaceSurveys> placeSurveys = criteria.list();
			    
			    if ((placeSurveys != null) && (placeSurveys.size() > 0))
			    {
					session = entityManager.unwrap(Session.class);
				    criteria = session.createCriteria(CompletedSurvey.class);
				    criteria.add(Restrictions.in("placeSurvey", placeSurveys));
				    List<CompletedSurvey> completedSurveys = criteria.list();
					
				    if ((completedSurveys != null) && (completedSurveys.size() > 0))
				    {
						session = entityManager.unwrap(Session.class);
					    criteria = session.createCriteria(RatingQuestionAnswer.class);
					    criteria.add(Restrictions.in("completedSurvey", completedSurveys));
					    criteria.setProjection(Projections.avg("answer"));
					    rating = (Double) criteria.uniqueResult();
				    }
			    }
			    
			    place.setRating(rating);
			    entityManager.merge(place);
			    
			    institution.setRating(rating);
				/**************************************/
			    
			    institution.setWorkingDays(getPlaceWorkingDays(place));
			    
				return new ResponseEntity<PublicInstitution>(institution, HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<PublicInstitution>(institution, HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e)
		{
			return new ResponseEntity<PublicInstitution>(institution, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private List<WorkingDay> getPlaceWorkingDays(Place place)
	{
		WorkingDay workingDay;
		List<WorkingDay> workingDays = new ArrayList();
		
		Session session = entityManager.unwrap(Session.class);
	    Criteria criteria = session.createCriteria(PlaceWorkingDays.class);
	    criteria.add(Restrictions.eq("place", place));
	    List<PlaceWorkingDays> placeWorkingDays = criteria.list();
	    
	    if ((placeWorkingDays != null) && (placeWorkingDays.size() > 0))
	    {
	    	for (PlaceWorkingDays placeWorkingDay : placeWorkingDays)
	    	{
	    		workingDay = new WorkingDay();
	    		workingDay.setDayOfTheWeek(placeWorkingDay.getDayOfTheWeek());
	    		workingDay.setOpeningTime(placeWorkingDay.getOpeningTime());
	    		workingDay.setClosingTime(placeWorkingDay.getClosingTime());	 
	    		
	    		workingDays.add(workingDay);
	    	}
	    }
	    
	    return workingDays;
	}

	
	/**
   * Serviço que retorna uma lista de locais/instituições dentro de um raio ao redor de um ponto informado.
   * @param lat Parâmetro enviado via URL contendo a latitude do ponto referencial para a busca.
   * @param lng Parâmetro enviado via URL contendo a longitude do ponto referencial para a busca.
   * @param radius Parâmetro enviado via URL contendo o tamanho (em Km) do raio ao redor do ponto referencial para a busca.
   * @return institutions Array JSON com as informações das instituições encontradas.
   */
	@RequestMapping("/getNearbyPlaces")
	@Transactional
	public ResponseEntity<List<PublicInstitution>> getNearbyPlaces(@PathParam("lat") double lat, @PathParam("lng") double lng, @PathParam("radius") int radius) 
	{
		List<Place> places;
		List<PublicInstitution> institutions = new ArrayList<>();
		
		try
		{
	    	if (radius < 1) radius = 5;
			radius = radius*1000; //from meters to km
			Geometry circularArea = Functions.getCircle(lat, lng, radius);
			//logger.debug("polygon circle : {}", circularArea.toString());
	
			//CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		    Session session = entityManager.unwrap(Session.class);
		    Criteria criteria = session.createCriteria(Place.class);
		    criteria.add(Restrictions.eq("status", Constants.STATUS_ATIVO));
		    criteria.add(SpatialRestrictions.within("location", circularArea));
		    //criteria.setMaxResults(10);
		    places = criteria.list();
		    
		    PublicInstitution institution;
		    
		    for (Place place : places)
		    {
		    	institution = new PublicInstitution();
		    	
		    	institution.setId(place.getId());
				institution.setName(place.getName());	
				institution.setNickname(place.getNickname());
				institution.setAbbrevName(place.getAbbrevName());
				institution.setStatus(place.getStatus());
				institution.setLongitude(place.getLocation().getX());
				institution.setLatitude(place.getLocation().getY());
				institution.setAddress(place.getAddress());
				//institution.setRating(place.getRating());
				//institution.setOpeningTime(place.getOpeningTime());
				//institution.setClosingTime(place.getClosingTime());
				institution.setCategory(place.getSubcategory().getCategory().getId());
				institution.setSubcategory(place.getSubcategory().getId());				
				
				/* Cálcula nota média ***********/
		    	Double rating = 0.0;

				session = entityManager.unwrap(Session.class);
			    criteria = session.createCriteria(PlaceSurveys.class);
			    criteria.add(Restrictions.eq("place", place));
			    List<PlaceSurveys> placeSurveys = criteria.list();
			    
			    if ((placeSurveys != null) && (placeSurveys.size() > 0))
			    {
					session = entityManager.unwrap(Session.class);
				    criteria = session.createCriteria(CompletedSurvey.class);
				    criteria.add(Restrictions.in("placeSurvey", placeSurveys));
				    List<CompletedSurvey> completedSurveys = criteria.list();
					
				    if ((completedSurveys != null) && (completedSurveys.size() > 0))
				    {
						session = entityManager.unwrap(Session.class);
					    criteria = session.createCriteria(RatingQuestionAnswer.class);
					    criteria.add(Restrictions.in("completedSurvey", completedSurveys));
					    criteria.setProjection(Projections.avg("answer"));
					    rating = (Double) criteria.uniqueResult();  
				    }
			    }
			    place.setRating(rating);
			    entityManager.merge(place);
			    
			    institution.setRating(rating);
				/**************************************/
				
			    institution.setWorkingDays(getPlaceWorkingDays(place));
				
		    	institutions.add(institution);
		    }
			
			return new ResponseEntity<List<PublicInstitution>>(institutions, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<List<PublicInstitution>>(institutions, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
   * Serviço que retorna uma lista de locais/instituições de uma categroria específica, 
   * com maiores ou menores médias, dentro de um raio ao redor de um ponto informado.
   * @param lat Parâmetro enviado via URL contendo a latitude do ponto referencial para a busca.
   * @param lng Parâmetro enviado via URL contendo a longitude do ponto referencial para a busca.
   * @param radius Parâmetro enviado via URL contendo o tamanho (em Km) do raio ao redor do ponto referencial para a busca.
   * @param category Parâmetro enviado via URL contendo a  categoria dos locais sendo buscados.
   * @param highRatings Parâmetro enviado via URL que define se serão buscados locais com médias altas ou baixas.
   * @return institutions Array JSON com as informações das instituições encontradas.
   */
	@RequestMapping("/findNearbyPlaces")
	@Transactional
	public ResponseEntity<List<PublicInstitution>> findNearbyPlaces(@PathParam("lat") double lat, @PathParam("lng") double lng,
			@PathParam("radius") int radius, @PathParam("category") int category, @PathParam("highRatings") boolean highRatings) 
	{
		List<Place> places;
		List<PublicInstitution> institutions = null;
		
		try
		{
	    	if (radius == 0) radius = 5;
			radius = radius*1000; //from meters to km
			Geometry circularArea = Functions.getCircle(lat, lng, radius);
	
		    Session session = entityManager.unwrap(Session.class);
		    Criteria criteria = session.createCriteria(Place.class, "place");
		    criteria.createAlias("place.subcategory", "subcategory");
		    criteria.createAlias("subcategory.category", "category");
		    criteria.add(Restrictions.eq("place.status", Constants.STATUS_ATIVO));
		    criteria.add(Restrictions.eq("category.id", category));
		    criteria.add(SpatialRestrictions.within("place.location", circularArea));
		    criteria.addOrder(Order.asc("place.rating"));
		    if (!highRatings)
		    {
		    	criteria.addOrder(Order.desc("place.rating"));
		    }
		    criteria.setMaxResults(10);
		    places = criteria.list();
		    
		    PublicInstitution institution;
		    institutions = new ArrayList<>();
		    
		    for (Place place : places)
		    {
		    	institution = new PublicInstitution();
		    	
		    	institution.setId(place.getId());
				institution.setName(place.getName());	
				institution.setNickname(place.getNickname());
				institution.setAbbrevName(place.getAbbrevName());
				institution.setStatus(place.getStatus());
				institution.setLongitude(place.getLocation().getX());
				institution.setLatitude(place.getLocation().getY());
				institution.setAddress(place.getAddress());
				institution.setRating(place.getRating());
				//institution.setOpeningTime(place.getOpeningTime());
				//institution.setClosingTime(place.getClosingTime());
				institution.setCategory(place.getSubcategory().getCategory().getId());
				institution.setSubcategory(place.getSubcategory().getId());
				//TODO: add surveys available
				//novaPesquisa = new Survey(102, "Essa pesquisa 2 tem o objetivo de ...");
			    institution.setWorkingDays(getPlaceWorkingDays(place));

		    	institutions.add(institution);
		    }
			
			return new ResponseEntity<List<PublicInstitution>>(institutions, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<List<PublicInstitution>>(institutions, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
   * Serviço que retorna uma lista de locais/instituições que já receberam algum tipo de relato 
   * (sugestão, reclamação, etc) de um usuário específico.
   * @param userIdInfo  Objeto JSON recebido no corpo da requisição HTTP, contendo os ID do usuário.
   * @return institutionsReported Array JSON com as informações das instituições encontradas.
   */
	@RequestMapping("/getPlacesReportedByUser")
	@Transactional
	public ResponseEntity<List<PublicInstitutionReported>> getPlacesReportedByUser(@RequestBody UserIdInfo userIdInfo)
	{
		Place place;
		List<Report> reports;
		PublicInstitution institution;
		PublicInstitutionReported institutionReported;
		List<PublicInstitutionReported> institutionsReported = null;
		HashMap<Integer, PublicInstitutionReported> institutionsHashMap;

		if (userIdInfo != null)
		{
			try
			{
				Query query = entityManager.createQuery("SELECT r FROM " + Report.class.getName() + " r WHERE r.user.id = :user_id");
				query.setParameter("user_id", userIdInfo.getId());
			    reports = query.getResultList();
			    logger.debug("reports.size : {}", reports.size());
			    
			    institutionsHashMap = new HashMap<>();
			    for (Report report : reports)
			    {
			    	logger.debug("report.id : {}", report.getId());
		
			    	place = report.getPlace();
			    	
			    	institutionReported = institutionsHashMap.get(place.getId());
			    	
			    	if (institutionReported != null)
			    	{
			    		institutionReported.setNumberReportsMade(institutionReported.getNumberReportsMade() + 1);
			    	}
			    	else
				    {
				    	institution = new PublicInstitution();
				    	institution.setId(place.getId());
						institution.setName(place.getName());	
						institution.setNickname(place.getNickname());
						institution.setAbbrevName(place.getAbbrevName());
						institution.setStatus(place.getStatus());
						institution.setLongitude(place.getLocation().getX());
						institution.setLatitude(place.getLocation().getY());
						institution.setAddress(place.getAddress());
						institution.setRating(place.getRating());
						//institution.setOpeningTime(place.getOpeningTime());
						//institution.setClosingTime(place.getClosingTime());
						institution.setCategory(place.getSubcategory().getCategory().getId());
						institution.setSubcategory(place.getSubcategory().getId());	
						
						institutionReported = new PublicInstitutionReported();
						institutionReported.setInstitution(institution);
						institutionReported.setNumberReportsMade(1);				
			    	}
			    	
					institutionsHashMap.put(new Integer(place.getId()), institutionReported);
			    }
			    
			    institutionsReported = new ArrayList<>();
			    for (int key : institutionsHashMap.keySet())
			    {
			    	institutionsReported.add(institutionsHashMap.get(key));
			    }
			    
			    return new ResponseEntity<List<PublicInstitutionReported>>(institutionsReported, HttpStatus.OK);
			}
			catch (Exception e)
			{
			    return new ResponseEntity<List<PublicInstitutionReported>>(institutionsReported, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
	    return new ResponseEntity<List<PublicInstitutionReported>>(institutionsReported, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	/*@RequestMapping("/getCircularArea")
	@Transactional
	public String getCircularArea(@PathParam("lat") double lat, @PathParam("lng") double lng, @PathParam("radius") int radius) 
	{		
    	if (radius < 1) radius = 5;
		radius = radius*1000; //from meters to km
		Geometry circularArea = Functions.getCircle(lat, lng, radius);
		//logger.debug("polygon circle : {}", circularArea.toString());
		
		return circularArea.toString();
	}*/
}
