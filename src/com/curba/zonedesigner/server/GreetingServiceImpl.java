package com.curba.zonedesigner.server;

import com.curba.zonedesigner.client.GreetingService;
import com.curba.zonedesigner.shared.ServerPlant;
import com.google.gwt.dev.json.JsonArray;
import com.google.gwt.dev.json.JsonObject;
import com.google.gwt.dev.json.JsonValue;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
enum JSONReadingStates
{
	ExpectLeftSquareBracket, ExpectCurlyBrace, ExpectKey, ExpectValue, ExpectRightSquareBracket  
};
*/
	
/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	/**
	 * Convert the string of JSON into JavaScript object.
	 */
	//private final native JsArray<PlantationData> asArrayOfPlantationData(String json) /*-{
	//	return eval(json);
	//}-*/;

/*
	public String greetServer(String input) throws IllegalArgumentException {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/Hort";
		String user = "root";
		String pass = "";
		
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

		try {
			//JsArray<PlantationData> plantations = asArrayOfPlantationData(input);
			for (int i = 0; i < plantations.length(); i++) {
				PlantationData plant = plantations.get(i);
				
				if (plant.getId() == -1)
				{
					//Create a new plantatiom
					PreparedStatement ps = conn.prepareStatement("SELECT * FROM hort WHERE usuari_id= 1");
					ResultSet result = ps.executeQuery();
					result.close();
					ps.close();
				}
				else
				{
					//Update the plantation
				}
			}		
		} catch (SQLException sqle) {
			return "E2:" + sqle.toString();
		}
		
		return "Data saved successfully";
	}
*/
	public String getZoneServer(int zoneId, String culture) throws IllegalArgumentException {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/Hort";
		String user = "root";
		String pass = "";
		
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
		//String jsonPlantations = "";
		//int zoneIdFound = -1;
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM zona WHERE id=" + zoneId);
			ResultSet result = ps.executeQuery();
			jsonZones = "[ ";
			while (result.next()) {
				jsonZones += "{ ";
				jsonZones += "id: " + result.getInt("id") + ", ";
				jsonZones += "nom: \"" + result.getString("nom") + "\", ";
				jsonZones += "descripcio: \"" + result.getString("descripcio") + "\", ";
				jsonZones += "tipus_zona_id: " + result.getInt("tipus_zona_id") + ", ";
				jsonZones += "punt_inicial_x: " + result.getInt("punt_inicial_x") + ", ";
				jsonZones += "punt_inicial_y: " + result.getInt("punt_inicial_y") + ", ";
				jsonZones += "punt_final_x: " + result.getInt("punt_final_x") + ", ";
				jsonZones += "punt_final_y: " + result.getInt("punt_final_y") + ", ";
				jsonZones += "profunditat: " + result.getInt("profunditat") + ", ";
				jsonZones += "hort_id: " + result.getInt("hort_id"); // + ", ";
				jsonZones += "}, ";
				
				//zoneIdFound = result.getInt("id");
			}
			jsonZones += " ]";
			result.close();
			ps.close();
		} catch (SQLException sqle) {
			return "E2:" + sqle.toString();
		}

		/*
		try {
			if (zoneIdFound != -1)
			{
				PreparedStatement ps2 = conn.prepareStatement(
						"SELECT p.*, v.*, vt.* " +
						"FROM plantacio p " + 
						"INNER JOIN vegetal v ON v.id = p.vegetal_id " +
						"INNER JOIN vegetal_translation vt ON vt.id = p.vegetal_id " +
						"WHERE p.zona_id = " + zoneId + " AND p.data_final_real IS NULL AND vt.lang = '" + culture + "'");
				ResultSet result2 = ps2.executeQuery();
				jsonPlantations = "[ ";
				while (result2.next()) {
					jsonPlantations += "{ ";
					jsonPlantations += "id: " + result2.getInt("id") + ", ";
					jsonPlantations += "punt_x: " + result2.getInt("punt_x") + ", ";
					jsonPlantations += "punt_y: " + result2.getInt("punt_y") + ", ";
					jsonPlantations += "num_plantes: " + result2.getInt("num_plantes"); // + ", ";
					jsonPlantations += "}, ";
				}
				jsonPlantations += " ]";
				result2.close();
				ps2.close();
			}
		} catch (SQLException sqle) {
			return "E3:" + sqle.toString();
		}
		*/

		//String jsonResult = "[ { Zones: " + jsonZones + " } , { Plantations: " + jsonPlantations + " } ]";
	
		//return jsonResult;
		
		return jsonZones;
	}

	public String getPlantationsServer(int zoneId, String culture) throws IllegalArgumentException {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/Hort";
		String user = "root";
		String pass = "";
		
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
					"SELECT p.*, v.*, vt.nom as vegetal_nom, vt.descripcio as vegetal_descripcio " +
					"FROM plantacio p " + 
					"INNER JOIN vegetal v ON v.id = p.vegetal_id " +
					"INNER JOIN vegetal_translation vt ON vt.id = p.vegetal_id " +
					"WHERE p.zona_id = " + zoneId + " AND p.data_final_real IS NULL AND vt.lang = '" + culture + "'");
			ResultSet result2 = ps2.executeQuery();
			jsonPlantations = "[ ";
			while (result2.next()) {
				jsonPlantations += "{ ";
				jsonPlantations += "id: " + result2.getInt("id") + ", ";
				jsonPlantations += "usuari_id: " + result2.getInt("usuari_id") + ", ";
				jsonPlantations += "vegetal_id: " + result2.getInt("vegetal_id") + ", ";
				jsonPlantations += "vegetal_nom: \"" + result2.getString("vegetal_nom") + "\", ";
				jsonPlantations += "vegetal_descripcio: \"" + result2.getString("vegetal_descripcio") + "\", ";
				jsonPlantations += "data_inicial_real: \"" + result2.getString("data_inicial_real") + "\", ";
				jsonPlantations += "data_inicial_prevista: \"" + result2.getString("data_inicial_prevista") + "\", ";
				jsonPlantations += "data_final_real: \"" + result2.getString("data_final_real") + "\", ";
				jsonPlantations += "data_final_prevista: \"" + result2.getString("data_final_prevista") + "\", ";
				jsonPlantations += "tipus_periode_cultiu_inicial: " + result2.getInt("tipus_periode_cultiu_inicial") + ", ";
				jsonPlantations += "zona_id: " + result2.getInt("zona_id") + ", ";
				jsonPlantations += "productivitat: " + result2.getInt("productivitat") + ", ";
				jsonPlantations += "marc_de_plantacio_x: " + result2.getInt("marc_de_plantacio_x") + ", ";
				jsonPlantations += "marc_de_plantacio_y: " + result2.getInt("marc_de_plantacio_y") + ", ";
				jsonPlantations += "volum_de_terra: " + result2.getInt("volum_de_terra") + ", ";
				jsonPlantations += "punt_x: " + result2.getInt("punt_x") + ", ";
				jsonPlantations += "punt_y: " + result2.getInt("punt_y") + ", ";
				jsonPlantations += "num_plantes: " + result2.getInt("num_plantes") + ","; 
				jsonPlantations += "deleted: 0"; // + ", ";
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

	public String setZoneServer(String input) throws IllegalArgumentException {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/Hort";
		String user = "root";
		String pass = "";
		
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

	public String setPlantationsServer(ServerPlant[] plants /*String input*/) throws IllegalArgumentException {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/Hort";
		String user = "root";
		String pass = "";
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

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        Date date = new Date();
	        
	        //TODO: make all the changes in a transaction
			for (int i = 0; i < plants.length; i++) {
				ServerPlant plant = plants[i];
				
				if (plant.getId() == -1)
				{
					if (plant.getDeleted() == 0)
					{ 			        				        
						//Create a new plantation
						sql = "INSERT INTO plantacio " + 
						"(`usuari_id`, `vegetal_id`, `data_inicial_real`, `data_inicial_prevista`, " +
						"`data_final_real`, `data_final_prevista`, `tipus_periode_cultiu_inicial`, " +
						"`zona_id`, `punt_x`, `punt_y`, `num_plantes`, `created_at`, `updated_at`) " +
						"VALUES (" + plant.getUserId() + ", " + plant.getVegetalId() +",";
						if (plant.getRealInitialDate().equals("null")) sql += plant.getRealInitialDate() + ",";
						else sql += "'" + plant.getRealInitialDate() + "',"; 
						if (plant.getPlannedInitialDate().equals("null")) sql += plant.getPlannedInitialDate() + ",";
						else sql += "'" + plant.getPlannedInitialDate() + "',";
						if (plant.getRealFinalDate().equals("null")) sql += plant.getRealFinalDate() + ",";
						else sql += "'" + plant.getRealFinalDate() + "',";
						if (plant.getPlannedFinalDate().equals("null")) sql += plant.getPlannedFinalDate() + ",";
						else sql += "'" + plant.getPlannedFinalDate() + "',";
						sql += plant.getInitialPeriodType() +"," + plant.getZoneId() +"," + 
						 plant.getPointX() + "," + plant.getPointY() + "," + plant.getPlantsNumber() +",'" + 
						 dateFormat.format(date) + "','" + dateFormat.format(date) + "')";
						PreparedStatement ps = conn.prepareStatement(sql);
						int result = ps.executeUpdate();
						ps.close();
						
					}
					//else
					//{
					//	A new plantation deleted never i created in the database
					//}
				}
				else
				{
					if (plant.getDeleted() == 0)
					{
						//Update the plantation
						sql = "UPDATE plantacio ";
						sql += "SET `data_inicial_real`=";
						if (plant.getRealInitialDate().equals("null")) sql += plant.getRealInitialDate();
						else sql += "'" + plant.getRealInitialDate() + "'";
						if (plant.getPlannedInitialDate().equals("null")) sql += ", `data_inicial_prevista`=" + plant.getPlannedInitialDate();
						else  sql += ", `data_inicial_prevista`=" + "'" + plant.getPlannedInitialDate() + "'";
						if (plant.getRealFinalDate().equals("null")) sql += ", `data_final_real`=" + plant.getRealFinalDate();
						else sql += ", `data_final_real`=" + "'" + plant.getRealFinalDate() + "'";
						if (plant.getPlannedFinalDate().equals("null")) sql += ", `data_final_prevista`=" + plant.getPlannedFinalDate();
						else sql += ", `data_final_prevista`=" + "'" + plant.getPlannedFinalDate() + "'";
						sql += ", `tipus_periode_cultiu_inicial`=" + plant.getInitialPeriodType(); 
						sql += ", `punt_x`=" + plant.getPointX(); 
						sql += ", `punt_y`=" + plant.getPointY();
						sql += ", `num_plantes`=" + plant.getPlantsNumber(); 
						sql += ", `updated_at`='" + dateFormat.format(date); 
						sql += "' WHERE id=" + plant.getId();
						PreparedStatement ps = conn.prepareStatement(sql);
						//ResultSet result = ps.executeQuery();
						int result = ps.executeUpdate();
						//result.close();
						ps.close();
					}
					else
					{
						//Delete the plantation
						sql = "DELETE FROM plantacio WHERE id=" + plant.getId();
						PreparedStatement ps = conn.prepareStatement(sql);
						int result = ps.executeUpdate();
						//result.close();
						ps.close();
					}
				}
			}
			
		} catch (SQLException sqle) {
			return "E2:" + sql + ". " + sqle.toString();
		} catch(Exception ex) {
			return "E3" + ex.toString();
		}
	
		return "Plants updated successfully";
	}

	public String getVegetalsServer(String culture) throws IllegalArgumentException {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/Hort";
		String user = "root";
		String pass = "";
		
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
					"SELECT v.*, vt.nom as nom, vt.descripcio as descripcio " +
					"FROM vegetal v " + 
					"INNER JOIN vegetal_translation vt ON vt.id = v.id " +
					"WHERE vt.lang = '" + culture + "'");
			ResultSet result2 = ps2.executeQuery();
			jsonPlantations = "[ ";
			while (result2.next()) {
				jsonPlantations += "{ ";
				jsonPlantations += "id: " + result2.getInt("id") + ", ";
				jsonPlantations += "nom: \"" + result2.getString("nom") + "\", ";
				jsonPlantations += "descripcio: \"" + result2.getString("descripcio") + "\", ";
				jsonPlantations += "nom_cientific: \"" + result2.getString("nom_cientific") + "\", ";
				jsonPlantations += "productivitat: " + result2.getInt("productivitat") + ", ";
				jsonPlantations += "marc_de_plantacio_x: " + result2.getInt("marc_de_plantacio_x") + ", ";
				jsonPlantations += "marc_de_plantacio_y: " + result2.getInt("marc_de_plantacio_y") + ", ";
				jsonPlantations += "volum_de_terra: " + result2.getInt("volum_de_terra") + ", ";
				jsonPlantations += "preferencia_de_sol_id: " + result2.getInt("preferencia_de_sol_id") + ", ";
				jsonPlantations += "preferencia_climatica_id: " + result2.getInt("preferencia_climatica_id") + ", ";
				jsonPlantations += "rec_id: " + result2.getInt("rec_id") + ", ";
				jsonPlantations += "adob_id: " + result2.getInt("adob_id") + ", ";
				jsonPlantations += "cicle_de_vida_id: " + result2.getInt("cicle_de_vida_id") + ", ";
				jsonPlantations += "profunditat_arrel: " + result2.getInt("profunditat_arrel") + ", ";
				jsonPlantations += "ph_minim: " + result2.getInt("ph_minim") + ", ";
				jsonPlantations += "ph_maxim: " + result2.getInt("ph_maxim") + ", ";
				jsonPlantations += "temperatura_minima: " + result2.getInt("temperatura_minima") + ", ";
				jsonPlantations += "temperatura_maxima: " + result2.getInt("temperatura_maxima") + ", ";
				jsonPlantations += "resistencia_gelades_id: " + result2.getInt("resistencia_gelades_id") + ", ";
				jsonPlantations += "tolerancia_salinitat_id: " + result2.getInt("tolerancia_salinitat_id");// + ", ";
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
