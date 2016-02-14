package com.comune.view;


import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;
import java.sql.Time;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Controller;
import org.slf4j.LoggerFactory;

import com.comune.model.*;
import com.comune.util.*;


/**
* Esse arquivo implementa fun��es relacionadas a inicializa��o do Banco de Dados.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@RestController
public class InitializeDB 
{
	@PersistenceContext//(unitName = "comunePersistenceUnit")
	private EntityManager entityManager;
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(PlacesView.class);
	
	
	@RequestMapping("/initialize")
	@Transactional
	public void initialize() //Model model 
	{
		/* EDUCACAO *************************************/

		Category category = new Category(Constants.CATEGORIA_EDUCACAO, "Educa��o");
        entityManager.persist(category);
        
        Subcategory subcategory = new Subcategory(Constants.EDUCACAO_SUBCATEGORIA_ESCOLA, "Escola", category);
        entityManager.persist(subcategory);
        
        subcategory = new Subcategory(Constants.EDUCACAO_SUBCATEGORIA_UNIVERSIDADE, "Universidade", category);
        entityManager.persist(subcategory);
        
        
		/* SAUDE *************************************/

        category = new Category(Constants.CATEGORIA_SAUDE, "Sa�de");
        entityManager.persist(category);
        
        subcategory = new Subcategory(Constants.SAUDE_SUBCATEGORIA_HOSPITAL, "Hospital", category);
        entityManager.persist(subcategory);
        
        subcategory = new Subcategory(Constants.SAUDE_SUBCATEGORIA_UPA, "Unidade de Pronto Atendimento", category);
        entityManager.persist(subcategory);
        
        
		/* SEGURANCA *************************************/

        category = new Category(Constants.CATEGORIA_SEGURANCA, "Seguran�a");
        entityManager.persist(category);
        
        subcategory = new Subcategory(Constants.SEGURANCA_SUBCATEGORIA_DELEGACIA, "Delegacia", category);
        entityManager.persist(subcategory);
        
        subcategory = new Subcategory(Constants.SEGURANCA_SUBCATEGORIA_PCS, "Posto Comunit�rio de Seguran�a", category);
        entityManager.persist(subcategory);
        
        
		/* TIPOS DE PERGUNTAS *************************************/

        QuestionType type = new QuestionType(Constants.TIPO_QUESTAO_SIM_NAO, "Sim ou Nao");
        entityManager.persist(type);
        
        type = new QuestionType(Constants.TIPO_QUESTAO_ESCALA, "Escala");
        entityManager.persist(type);
        
        type = new QuestionType(Constants.TIPO_QUESTAO_MULTIPLA_ESCOLHA, "Multipla Escolha");
        entityManager.persist(type);
        
        type = new QuestionType(Constants.TIPO_QUESTAO_CHECKBOXES, "Checkboxes");
        entityManager.persist(type);
		
	}
	
	private void setWorkingDays(Place place, int auxDays, int auxTime)
	{
		PlaceWorkingDays placeWorkingDay = null;
		int firstDay = 1;
		int lastDay = 7;
		int openingTime = 0;
		int closingTime = 0;
		
		if (auxDays == 1) {
			firstDay = 2;
			lastDay = 6;
		}
		
		if (auxTime == 1)
		{
			openingTime = 6;
			closingTime = 18;
		}
			
        for (int weekDay=firstDay; weekDay<=lastDay; weekDay++)
		{
        	placeWorkingDay = new PlaceWorkingDays();
        	placeWorkingDay.setPlace(place);
        	placeWorkingDay.setDayOfTheWeek(weekDay);
        	placeWorkingDay.setOpeningTime(new java.sql.Time(openingTime, 0, 0));
        	placeWorkingDay.setClosingTime(new java.sql.Time(closingTime, 0, 0));
        	entityManager.persist(placeWorkingDay);
        	/*hour - 0 to 23
			minute - 0 to 59
			second - 0 to 59*/
		}
	}
	
	@RequestMapping("/populate")
	@Transactional
	public void populate()
	{
		Category category = entityManager.find(Category.class, Constants.CATEGORIA_SAUDE);
		Subcategory subcategory = entityManager.find(Subcategory.class, Constants.SAUDE_SUBCATEGORIA_HOSPITAL);
		
		/* USERS *************************************/
        
        User user = new User();
		user.setFirstName("Administrador");
		user.setLastName("Administrador");
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 1-1);
		calendar.set(Calendar.YEAR, 1980);
		user.setDateOfBirth(new java.sql.Date(calendar.getTimeInMillis()));
		user.setEmail("admin@comune.com");
		user.setHashedPassword(Functions.cryptWithMD5("admin"));
		user.setMobileNumber("+556199999999");
		java.util.Date date = new java.util.Date();
		user.setRegisteredAt(new Timestamp(date.getTime()));
		
		entityManager.persist(user);
		
		
        /* PLACES *************************************/
		
		/* SAUDE *********/
		
		//subcategory = entityManager.find(Subcategory.class, Constants.SAUDE_SUBCATEGORIA_HOSPITAL);
		
		Geometry geom = Functions.wktToGeometry("POINT(-47.874361127614975 -15.772089718877316)"); //POINT(lng lat)
        Place place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Hospital Universit�rio de Bras�lia");
        place.setAbbrevName("HUB");
        place.setAddress("SGAN, Quadra 604, Avenida L2 Norte, s/n - Asa Norte, Bras�lia - DF");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(3.1);
        //place.setOpeningTime(0);
        //place.setClosingTime(0);
        place.setLocation((Point) geom);
        logger.debug("Point  : {}", geom.toString());
        entityManager.persist(place);
        setWorkingDays(place, 0, 0);
		
		/**************************************/
		
        subcategory = entityManager.find(Subcategory.class, Constants.SAUDE_SUBCATEGORIA_UPA);
		
		geom = Functions.wktToGeometry("POINT(-47.874191142618656 -15.77121306836615)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Centro de Sa�de de Bras�lia N� 12");
        place.setAbbrevName("UPA 407 Norte");
        place.setAddress("EQN 208/408, s/n - Asa Norte, Bras�lia - DF, 70853-450");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(2.9);
        //place.setOpeningTime(5);
        //place.setClosingTime(22);
        place.setLocation((Point) geom);
        logger.debug("Point  : {}", geom.toString());
        entityManager.persist(place);  
        setWorkingDays(place, 0, 1);

        /**************************************/
		
        subcategory = entityManager.find(Subcategory.class, Constants.SAUDE_SUBCATEGORIA_UPA);
		
		geom = Functions.wktToGeometry("POINT(-48.121142126619816 -15.825339527734451)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("UPA de Ceil�ndia");
        place.setAbbrevName(null);
        place.setAddress("St. P St. N QNP 1FEIRA/QNN 17 AE - Ceil�ndia Norte, Bras�lia - DF, 72225-270");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(3.2);
        //place.setOpeningTime(0);
        //place.setClosingTime(0);
        place.setLocation((Point) geom);
        logger.debug("Point  : {}", geom.toString());
        entityManager.persist(place);
        setWorkingDays(place, 0, 0);

        /**************************************/
		
        subcategory = entityManager.find(Subcategory.class, Constants.SAUDE_SUBCATEGORIA_HOSPITAL);
		
		geom = Functions.wktToGeometry("POINT(-47.88867641240358 -15.800896404196164)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Hospital de Base do Distrito Federal");
        place.setAbbrevName("HBDF");
        place.setAddress("SHS, Quadra 101, �rea Especial, s/n - Asa Sul, Bras�lia - DF, 70335-900");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(3.5);
        //place.setOpeningTime(0);
        //place.setClosingTime(0);
        place.setLocation((Point) geom);
        logger.debug("Point  : {}", geom.toString());
        entityManager.persist(place);
        setWorkingDays(place, 0, 0);

        /**************************************/
		
        subcategory = entityManager.find(Subcategory.class, Constants.SAUDE_SUBCATEGORIA_HOSPITAL);
		
		geom = Functions.wktToGeometry("POINT(-47.89683133363724 -15.823807322420993)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Hospital Materno Infantil de Bras�lia");
        place.setAbbrevName("HMIB");
        place.setAddress("SGAS Av. L2 Sul Quadra 608 M�dulo A, s/n - Asa Sul, Bras�lia - DF, 70203-900");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(4.8);
        //place.setOpeningTime(0);
        //place.setClosingTime(0);
        place.setLocation((Point) geom);
        logger.debug("Point  : {}", geom.toString());
        entityManager.persist(place);
        setWorkingDays(place, 0, 0);

        /* EDUCACAO *************************************/
        
        subcategory = entityManager.find(Subcategory.class, Constants.EDUCACAO_SUBCATEGORIA_ESCOLA);
        
        geom = Functions.wktToGeometry("POINT(-47.87519596517086 -15.76617088310072)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Centro de Ensino M�dio da Asa Norte");
        place.setAbbrevName("CEAN");
        place.setAddress("Modulo G/H - - SGAN 606 - Asa Norte, Brasilia - DF, 70840-060");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(3.5);
        //place.setOpeningTime(7);
        //place.setClosingTime(18);
        place.setLocation((Point) geom);
        entityManager.persist(place);
        setWorkingDays(place, 1, 1);

        geom = Functions.wktToGeometry("POINT(-47.90950108319521 -15.807580394370175)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Centro de Ensino M�dio Elefante Branco");
        place.setAbbrevName("CEM Elefante Branco");
        place.setAddress("SGAS, 908 - �rea Especial - Asa Sul, Bras�lia - DF, 70390-080");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(4.0);
        //place.setOpeningTime(7);
        //place.setClosingTime(18);
        place.setLocation((Point) geom);
        entityManager.persist(place);
        setWorkingDays(place, 1, 1);

        geom = Functions.wktToGeometry("POINT(-47.879256159067154 -15.751662487724124)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Centro de Ensino M�dio Paulo Freire");
        place.setAbbrevName("CEM Paulo Freire");
        place.setAddress("Via L2 Norte, 611 - Asa Norte, Bras�lia - DF, 70830-450");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(3.9);
        //place.setOpeningTime(7);
        //place.setClosingTime(18);
        place.setLocation((Point) geom);
        entityManager.persist(place);
        setWorkingDays(place, 1, 1);
        
        geom = Functions.wktToGeometry("POINT(-48.12508899718523 -15.82303121567544)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Centro de Ensino Fundamental 11 de Ceil�ndia");
        place.setAbbrevName("CEF 11");
        place.setAddress("Area Especial, St. N Eqnn 24/26 - Ceilandia, Brasilia - DF, 72220-580");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(2.75);
        //place.setOpeningTime(6);
        //place.setClosingTime(22);
        place.setLocation((Point) geom);
        entityManager.persist(place);
        setWorkingDays(place, 0, 1);
        
        subcategory = entityManager.find(Subcategory.class, Constants.EDUCACAO_SUBCATEGORIA_UNIVERSIDADE);
        
        geom = Functions.wktToGeometry("POINT(-47.86987077444792 -15.763442107438548)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Universidade de Bras�lia");
        place.setAbbrevName("UnB");
        place.setAddress("Campus Universit�rio Darcy Ribeiro, Bras�lia, DF, 70910-900");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(4.4);
        //place.setOpeningTime(0);
        //place.setClosingTime(0);
        place.setLocation((Point) geom);
        entityManager.persist(place);
        setWorkingDays(place, 0, 0);

        
        /* SEGURANCA *************************************/
        
        subcategory = entityManager.find(Subcategory.class, Constants.SEGURANCA_SUBCATEGORIA_DELEGACIA);
        
        geom = Functions.wktToGeometry("POINT(-47.879705764353275 -15.773760732674479)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Delegacia da Crian�a e do Adolescente");
        place.setAbbrevName("DCA I");
        place.setAddress("EQN, 204-205, Asa Norte, SHCN Superquadra Norte 205 - Brasilia, DF, 70842-400");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating((double)4.3);
        //place.setOpeningTime(0);
        //place.setClosingTime(0);
        place.setLocation((Point) geom);
        entityManager.persist(place);
        setWorkingDays(place, 0, 0);

        geom = Functions.wktToGeometry("POINT(-47.89223235100508 -15.787272239937685)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("5� Delegacia de Pol�cia");
        place.setAbbrevName("5� DP");
        place.setAddress("Lote A, SGAN Q 901 - Asa Norte, Bras�lia - DF, 70040-000");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating((double)2.7);
        //place.setOpeningTime(0);
        //place.setClosingTime(0);
        place.setLocation((Point) geom);
        entityManager.persist(place);
        setWorkingDays(place, 1, 1);

        geom = Functions.wktToGeometry("POINT(-47.8921414911747 -15.81090375768057)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Delegacia Especial de Atendimento a Mulher");
        place.setAbbrevName("DEAM");
        place.setAddress("EQS 204/205 - Asa Sul - Bras�lia - DF, 70234-400");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating((double)4.5);
        //place.setOpeningTime(0);
        //place.setClosingTime(0);
        place.setLocation((Point) geom);
        entityManager.persist(place);
        setWorkingDays(place, 0, 0);
        
        geom = Functions.wktToGeometry("POINT(-47.92469512671232 -15.872417723708432)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Delegacia de Imigra��o de Bras�lia");
        place.setAbbrevName("DELEMIG");
        place.setAddress("Aeroporto Internacional de Bras�lia, Lago Sul, Bras�lia - DF, 71608-900");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating((double) 4.1);
        //place.setOpeningTime(0);
        //place.setClosingTime(0);
        place.setLocation((Point) geom);
        entityManager.persist(place);
        setWorkingDays(place, 0, 0);

        subcategory = entityManager.find(Subcategory.class, Constants.SEGURANCA_SUBCATEGORIA_PCS);
        
        geom = Functions.wktToGeometry("POINT(-48.11194617301226 -15.822276718360525)"); //POINT(lng lat)
        place = new Place();
        place.setSubcategory(subcategory);
        place.setName("Posto Comunit�rio de Seguran�a 105");
        place.setAbbrevName("PCS 105");
        place.setAddress("St. N CNN 2 Conjunto H - Ceil�ndia - DF");
        place.setStatus(Constants.STATUS_ATIVO);
        place.setRating(2.4);
        //place.setOpeningTime(0);
        //place.setClosingTime(0);
        place.setLocation((Point) geom);
        entityManager.persist(place);
        setWorkingDays(place, 0, 1);

	}
}


/*
 * {"userId":0,"placeId":0,"surveyId":0,
"description":"Esta pesquisa tem o objetivo de identificar o n�vel de satisfa��o dos estudantes matriculados nessa institui��o. As respostas obtidas ser�o encaminhadas ao conselho de professores para a elabora��o do Plano de Metas do pr�ximo ano.",
"startedAt":0,"completedAt":0,
"questions":[
{"id":0,"type":3, "mandatory":true,
"descr":"Qual ano voc� est� cursando atualmente?","answerId":0,
"answerOptions":[{"id":0,"header":"1� Ano"},
             {"id":0,"header":"2� Ano"},
             {"id":0,"header":"3� Ano"}]
},
{"id":0,"type":3, "mandatory":true,
"descr":"Em quantas mat�rias voc� est� matriculado este ano?","answerId":0,
"answerOptions":[{"answerOptions":[{"id":0,"header":"Menos de 8 mat�rias"},
             {"id":0,"header":"8 mat�rias"},
             {"id":0,"header":"9 mat�rias"},
             {"id":0,"header":"10 mat�rias"},
             {"id":0,"header":"11 mat�rias"},
             {"id":0,"header":"12 mat�rias"},
             {"id":0,"header":"Mais de 12 mat�rias"}]
},
{"id":0,"type":1, "mandatory":true,
"descr":"Em geral, voc� se sente satisfeito com seu desempenho escolar este ano?","answer":false},
{"id":0,"type":2, "mandatory":true,
"descr":"Que nota voc� daria para o seu desempenho?","answer":0.0},
{"id":0,"type":1, "mandatory":true,
"descr":"Em geral, voc� se sente satisfeito com o n�vel de preparo dos professores em sala de aula?","answer":false},
{"id":0,"type":2, "mandatory":true,
"descr":"Que nota voc� daria para a qualidade das aulas, em geral?","answer":0.0},
{"id":0,"type":4, "mandatory":true,
"descr":"Selecione dentre as �reas do conhecimento abaixo aquelas que mais despertam o seu interesse:",
"answerOptions":[{"id":0,"header":"Ci�ncias Exatas e da Terra","checked":false},
             {"id":0,"header":"Engenharias","checked":false},
             {"id":0,"header":"Ci�ncias Biol�gicas","checked":false},
             {"id":0,"header":"Ci�ncias Humanas","checked":false},
             {"id":0,"header":"Ci�ncias da Sa�de","checked":false},
             {"id":0,"header":"Ci�ncias Agr�rias","checked":false},
             {"id":0,"header":"Artes","checked":false},
             {"id":30,"header":"Lingu�stica","checked":false}]
},
{"id":0,"type":1, "mandatory":true,
"descr":"Voc� acha que a escola oferece suporte adequado ao aprofundamento de seus estudos nas �reas de seu interesse?","answer":false},
{"id":0,"type":4, "mandatory":true,
"descr":"Selecione os aspectos que voc� considera como sendo os melhores em sua escola:",
"answerOptions":[{"id":0,"header":"Estrutura f�sica","checked":false},
             {"id":0,"header":"Corpo docente","checked":false},
             {"id":0,"header":"Acervo da biblioteca","checked":false},
             {"id":0,"header":"Organiza��o","checked":false},
             {"id":0,"header":"Rela��o entre professores e alunos","checked":false},
             {"id":0,"header":"Rela��o entre a escola e a comunidade","checked":false}]
}

]}*/





/*
{"userId":0,"placeId":0,"surveyId":0,
 "description":"Esta pesquisa tem o objetivo de medir a receptividade do atendimento nesta institui��o. As respostas obtidas ser�o utilizadas na elabora��o do plano de melhorias.",
 "startedAt":0,"completedAt":0,
 "questions":[
   {"id":0,"type":3, "mandatory":true,
    "descr":"Com que frequ�ncia voc� utiliza de algum do servi�os prestados por este estabelecimento?","answerId":0,
    "answerOptions":[{"id":0,"header":"Todos os dias"},
                     {"id":0,"header":"Toda semana"},
                     {"id":0,"header":"A cada 15 dias"},
                     {"id":0,"header":"1 vez por m�s"},
                     {"id":0,"header":"A cada 3 meses"},
                     {"id":0,"header":"Raramente"},
                     {"id":0,"header":"Esta � minha primeira visita"}]
   },
   {"id":0,"type":1, "mandatory":true,
    "descr":"Voc� considera como aceit�vel o tempo de espera at� pelo atendimento?","answer":false},
   {"id":0,"type":1, "mandatory":true,
    "descr":"Os funcion�rios souberam sanar suas d�vidas ou atender ao seu pedido?","answer":false},
   {"id":0,"type":2, "mandatory":true,
    "descr":"Que nota voc� daria para o atendimento que recebeu?","answer":0.0},
   {"id":0,"type":2, "mandatory":true,
    "descr":"O qu�o confort�vel voc� se sentiu estando neste estabelecimento?","answer":0.0},
   {"id":0,"type":4, "mandatory":true,
    "descr":"Selecione os aspectos que voc� considera como sendo os que necessitam de mais aten��o no local:",
    "answerOptions":[{"id":0,"header":"Estrutura f�sica","checked":false},
                     {"id":0,"header":"Limpeza do pr�dio","checked":false},
                     {"id":0,"header":"Qualidade do atendimento","checked":false},
                     {"id":0,"header":"N�mero de funcion�rios","checked":false},
                     {"id":0,"header":"Organiza��o","checked":false}]
   },
   {"id":0,"type":1, "mandatory":true,
    "descr":"Voc� recomendaria este estabelecimento a um amigo?","answer":false} 
 ]}
 */
