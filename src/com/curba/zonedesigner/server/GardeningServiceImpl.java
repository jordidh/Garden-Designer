package com.curba.zonedesigner.server;

import com.curba.zonedesigner.client.GardeningService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

enum JSONReadingStates
{
	ExpectLeftSquareBracket, ExpectCurlyBrace, ExpectKey, ExpectValue, ExpectRightSquareBracket  
};
	
/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GardeningServiceImpl extends RemoteServiceServlet implements
		GardeningService {
	
	//String url = "jdbc:mysql://localhost:3306/Hort";
	String url = "jdbc:mysql://localhost:3306/hort";
	String user = "root";
	String pass = "1qaz2wsx";

	public String getGarden(int gardenId, String culture) throws IllegalArgumentException {
		Connection conn = null;
		//String url = "jdbc:mysql://localhost:3306/Hort";
		//String user = "root";
		//String pass = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			return "E1.1:" + e.toString();
		}
			
		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			return "E1.2:" + e.toString();
		}

		String jsonGarden = "";
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Garden WHERE id=" + gardenId);
			ResultSet result = ps.executeQuery();
			jsonGarden = "[ ";
			while (result.next()) {
				jsonGarden += "{ ";
				jsonGarden += "id: " + result.getInt("id") + ", ";
				jsonGarden += "region_id: " + result.getInt("region_id") + ", ";
				jsonGarden += "name: \"" + result.getString("name") + "\", ";
				jsonGarden += "description: \"" + result.getString("description") + "\", ";
				jsonGarden += "latitude: " + result.getInt("latitude") + ", ";
				jsonGarden += "longitude: " + result.getInt("longitude") + ", ";
				jsonGarden += "width: " + result.getInt("width") + ", ";
				jsonGarden += "height: " + result.getInt("height"); // + ", ";
				jsonGarden += "}, ";
			}
			jsonGarden += " ]";
			result.close();
			ps.close();
		} catch (SQLException sqle) {
			return "E2:" + sqle.toString();
		}
		
		return jsonGarden;	
	}

	/**
	 * Tested Ok
	 */
	public String getZone(int gardenId, String culture) throws IllegalArgumentException {
		Connection conn = null;
		//String url = "jdbc:mysql://localhost:3306/Hort";
		//String user = "root";
		//String pass = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			return "E1.1:" + e.toString();
		}
			
		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			return "E1.2:" + e.toString();
		}

		String jsonZones = "";

		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT z.*, zt.name as zone_type_name, zt.description as zone_type_description " +
					"FROM Zone z " +
				    "INNER JOIN ZoneType zt ON zt.id=z.zone_type_id " +
					"WHERE z.garden_id=" + gardenId + " AND z.deleted_at is NULL");
			
			ResultSet result = ps.executeQuery();
			jsonZones = "[ ";
			while (result.next()) {
				jsonZones += "{ ";
				jsonZones += "id: " + result.getInt("id") + ", ";
				jsonZones += "garden_id: " + result.getInt("id") + ", ";
				jsonZones += "zone_type_id: " + result.getInt("zone_type_id") + ", ";
				jsonZones += "zone_type_name: \"" + result.getString("zone_type_name") + "\", ";
				jsonZones += "zone_type_description: \"" + result.getString("zone_type_description") + "\", ";
				jsonZones += "name: \"" + result.getString("name") + "\", ";
				jsonZones += "description: \"" + result.getString("description") + "\", ";
				jsonZones += "initial_point_x: " + result.getInt("initial_point_x") + ", ";
				jsonZones += "initial_point_y: " + result.getInt("initial_point_y") + ", ";
				jsonZones += "final_point_x: " + result.getInt("final_point_x") + ", ";
				jsonZones += "final_point_y: " + result.getInt("final_point_y") + ", ";
				jsonZones += "depth: " + result.getInt("depth") + ", ";
				jsonZones += "deleted: 0"; // + ", ";
				jsonZones += "}, ";
			}
			jsonZones += " ]";
			result.close();
			ps.close();
		} catch (SQLException sqle) {
			return "E2:" + sqle.toString();
		}

		return jsonZones;
	}
	public String setZone(String zoneJsonized) throws IllegalArgumentException {
		Connection conn = null;
		//String url = "jdbc:mysql://localhost:3306/Hort";
		//String user = "root";
		//String pass = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			return "E1.1:" + e.toString();
		}
			
		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			return "E1.2:" + e.toString();
		}

		//TODO
		/*
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM zona");
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				n++;
			}
			result.close();
			ps.close();
		} catch (SQLException sqle) {
			return "E2:" + sqle.toString();
		}
		*/
		return "OK";
	}

	
    public String getCrop(int cropId, String culture) throws IllegalArgumentException {
		Connection conn = null;
		//String url = "jdbc:mysql://localhost:3306/Hort";
		//String user = "root";
		//String pass = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			return "E1.1:" + e.toString();
		}
			
		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			return "E1.2:" + e.toString();
		}

		String jsonCrops = "";
		
		try {
			PreparedStatement ps2 = conn.prepareStatement(
					"SELECT c.*, p.*, p.name as plant_name, p.description as plant_description " +
					"FROM Crop c " + 
					"INNER JOIN Plant p ON p.id = c.plant_id " +
					"WHERE c.zone_id = " + cropId + " AND c.final_real_date IS NULL");
			ResultSet result2 = ps2.executeQuery();
			jsonCrops = "[ ";
			while (result2.next()) {
				jsonCrops += "{ ";
				jsonCrops += "id: " + result2.getInt("id") + ", ";
				jsonCrops += "zone_id: " + result2.getInt("zone_id") + ", ";
				jsonCrops += "crop_period_id: " + result2.getInt("crop_period_id") + ", ";
				jsonCrops += "plant_id: " + result2.getInt("plant_id") + ", ";
				jsonCrops += "plant_name: \"" + result2.getString("plant_name") + "\", ";
				jsonCrops += "plant_description: \"" + result2.getString("plant_description") + "\", ";
				jsonCrops += "initial_real_date: \"" + result2.getString("initial_real_date") + "\", ";
				jsonCrops += "initial_planned_date: \"" + result2.getString("initial_planned_date") + "\", ";
				jsonCrops += "final_real_date: \"" + result2.getString("final_real_date") + "\", ";
				jsonCrops += "final_planned_date: \"" + result2.getString("final_planned_date") + "\", ";
				jsonCrops += "point_x: " + result2.getInt("point_x") + ", ";
				jsonCrops += "point_y: " + result2.getInt("point_y") + ", ";
				jsonCrops += "num_plants: " + result2.getInt("num_plants") + ",";
				jsonCrops += "productivity: " + result2.getInt("productivity") + ", ";
				jsonCrops += "width_spacing: " + result2.getInt("width_spacing") + ", ";
				jsonCrops += "height_spacing: " + result2.getInt("height_spacing") + ", ";
				jsonCrops += "soil_volume: " + result2.getInt("soil_volume") + ", ";
				jsonCrops += "deleted: 0"; // + ", ";
				jsonCrops += "}, ";
			}
			jsonCrops += " ]";
			result2.close();
			ps2.close();
		} catch (SQLException sqle) {
			return "E3:" + sqle.toString();
		}
	
		return jsonCrops;
	}
	public String setCrop(String cropJsonized) throws IllegalArgumentException {
		Connection conn = null;
		//String url = "jdbc:mysql://localhost:3306/Hort";
		//String user = "root";
		//String pass = "";
		String sql = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			return "E1.1:" + e.toString();
		}
			
		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			return "E1.2:" + e.toString();
		}
/*
		try {
	        //TODO: make all the changes in a transaction
			
		} catch (SQLException sqle) {
			return "E2:" + sql + ". " + sqle.toString();
		} catch(Exception ex) {
			return "E3" + ex.toString();
		}
*/
		return "Plants updated successfully";
	}

	
	/**
	 * Tested Ok
	 * (non-Javadoc)
	 * @see com.curba.zonedesigner.client.GardeningService#getPlants(java.lang.String)
	 */
	public String getPlants(String culture) throws IllegalArgumentException {
		Connection conn = null;
		//String url = "jdbc:mysql://localhost:3306/hort";
		//String user = "root";
		//String pass = "1qaz2wsx";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			return "E1.1:" + e.toString();
		}
			
		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			return "E1.2:" + e.toString();
		}

		String jsonPlantations = "";
		try {
			PreparedStatement ps2 = conn.prepareStatement(
					"SELECT p.* " +
					"FROM Plant p ");
			ResultSet result2 = ps2.executeQuery();
			jsonPlantations = "[ ";
			while (result2.next()) {
				jsonPlantations += "{ ";
				jsonPlantations += "id: " + result2.getInt("id") + ", ";
				jsonPlantations += "name: \"" + result2.getString("name") + "\", ";
				jsonPlantations += "description: \"" + result2.getString("description") + "\", ";
				jsonPlantations += "scientific_name: \"" + result2.getString("scientific_name") + "\", ";
				jsonPlantations += "productivity: " + result2.getInt("productivity") + ", ";
				jsonPlantations += "width_spacing: " + result2.getInt("witdh_spacing") + ", ";
				jsonPlantations += "height_spacing: " + result2.getInt("height_spacing") + ", ";
				jsonPlantations += "soil_volume: " + result2.getInt("soil_volume") + ", ";
				jsonPlantations += "soil_type_id: " + result2.getInt("soil_type_id") + ", ";
				jsonPlantations += "climate_type_id: " + result2.getInt("climate_type_id") + ", ";
				jsonPlantations += "irrigation_type_id: " + result2.getInt("irrigation_type_id") + ", ";
				jsonPlantations += "fertilizer_type_id: " + result2.getInt("fertilizer_type_id") + ", ";
				jsonPlantations += "life_cicle_type_id: " + result2.getInt("life_cicle_type_id") + ", ";
				jsonPlantations += "root_depth: " + result2.getInt("root_depth") + ", ";
				jsonPlantations += "plant_height: " + result2.getInt("plant_height") + ", ";
				jsonPlantations += "seed_depth: " + result2.getInt("seed_depth") + ", ";
				jsonPlantations += "plant_care_id: " + result2.getInt("plant_care_id") + ", ";
				jsonPlantations += "action_type_id: " + result2.getInt("action_type_id") + ", ";
				jsonPlantations += "plant_family_id: " + result2.getInt("plant_family_id") + ", ";
				jsonPlantations += "ph_minimum: " + result2.getInt("ph_minimum") + ", ";
				jsonPlantations += "ph_maximum: " + result2.getInt("ph_maximum") + ", ";
				jsonPlantations += "temperature_minimum: " + result2.getInt("temperature_minimum") + ", ";
				jsonPlantations += "temperature_maximum: " + result2.getInt("temperature_maximum") + ", ";
				jsonPlantations += "frost_resistance_type_id: " + result2.getInt("frost_resistance_type_id") + ", ";
				jsonPlantations += "salinity_tolerance_type_id: " + result2.getInt("salinity_tolerance_type_id");// + ", ";
				jsonPlantations += "}, ";
			}
			jsonPlantations += " ]";
			result2.close();
			ps2.close();
		} catch (SQLException sqle) {
			return "E3:" + sqle.toString();
		}
	
		return jsonPlantations;
	}
}
