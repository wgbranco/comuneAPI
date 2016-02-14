package com.comune.util;

import com.comune.view.PlacesView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import org.slf4j.LoggerFactory;


public class Functions
{
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(Functions.class);

	public static Geometry wktToGeometry(String wktGeometry) 
    {
        WKTReader fromText = new WKTReader(new GeometryFactory(new PrecisionModel(), 4326));
        Geometry geom = null;
        
        try {
            geom = fromText.read(wktGeometry);
            logger.debug("wktToGeometry result : {}", geom.toString());
        } catch (ParseException e) {
            throw new RuntimeException("Not a WKT string:" + wktGeometry);
        }
                
        return geom;
    }
	
	/**
     * Utility method to assemble all arguments save the first into a String
     */
    public static String assembleArgs(String[] args) 
    {
        StringBuilder builder = new StringBuilder();
        
        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        return builder.toString();
    }
    
    private static int EARTH_RADIUS = 6371000; //in meters

    private static String getPointLatLng(double latitude, double longitude, int radius, double angle) {
        // Get the coordinates of a circle point at the given angle
        double east = radius * Math.cos(angle);
        double north = radius * Math.sin(angle);

        double cLat = latitude;
        double cLng = longitude;
        double latRadius = EARTH_RADIUS * Math.cos(cLat / 180 * Math.PI);

        double newLat = cLat + (north / EARTH_RADIUS / Math.PI * 180);
        double newLng = cLng + (east / latRadius / Math.PI * 180);

        String pointLatLng = Double.toString(newLng) + " " + Double.toString(newLat);
        return pointLatLng;
    }

    public static String drawWktCircle(double lat, double lng, int radius) {
        // Generate the points
        String wktPolygon = new String("POLYGON((");
        String firstPointLatLng = null;
        
        int totalPonts = 30; // number of corners of the pseudo-circle
        
        for (int i = 0; i < totalPonts; i++) 
        {
        	String point = getPointLatLng(lat, lng, radius, i*2*Math.PI/totalPonts);
        	
        	wktPolygon = wktPolygon + point;
        	
        	if (i == 0)
        		firstPointLatLng = point;
        	
        	if (i < totalPonts)
        		wktPolygon = wktPolygon + ", ";
        }
        
        wktPolygon = wktPolygon + firstPointLatLng + "))";
        
        // Create and return the polygon
        return wktPolygon;
    }
    
    public static Geometry getCircle(double lat, double lng, int radius)
    {    	
    	String wktPolygon = drawWktCircle(lat, lng, radius);
    	
        Geometry geom = wktToGeometry(wktPolygon);

        return geom;
    }
    
    public static String cryptWithMD5(String pass)
    {
    	MessageDigest md;
    	
	    try 
	    {
	        md = MessageDigest.getInstance("MD5");
	        byte[] passBytes = pass.getBytes();
	        md.reset();
	        byte[] digested = md.digest(passBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
	    } 
	    catch (NoSuchAlgorithmException ex) 
	    {}
	    
	    return null;
	}
}