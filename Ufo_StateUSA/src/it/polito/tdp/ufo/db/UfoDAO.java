package it.polito.tdp.ufo.db;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.ufo.db.DBConnect;
import it.polito.tdp.ufo.bean.CoppiaInt;
import it.polito.tdp.ufo.bean.Sighting;

public class UfoDAO {
	
	public List<CoppiaInt> getAllCoppiaIntAnni() {

		final String sql = "SELECT  DISTINCT Extract(year FROM s1.`datetime`) anno ,count(*) Navvistamenti FROM sighting s1 where country='us' GROUP BY anno ";
		List<CoppiaInt> result = new ArrayList<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {				
				result.add(new CoppiaInt (rs.getInt("anno"),rs.getInt("Navvistamenti")));
			}	conn.close();
			return result;
		} catch (SQLException e) {e.printStackTrace();
			return null;
			}
	 }
	
	public List<Integer> getAllAnni() {

		final String sql = "SELECT  DISTINCT Extract(year FROM s1.`datetime`) anno ,count(*) Navvistamenti FROM sighting s1 where country='us 'GROUP BY anno ";
		List<Integer> result = new ArrayList<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {				
				result.add(new Integer (rs.getInt("anno")));
			}	conn.close();
			return result;
		} catch (SQLException e) {e.printStackTrace();
			return null;
			}
	 }


	public List<Sighting> getAllStati(int anno) {

		final String sql = " SELECT Distinct * FROM sighting  WHERE Extract(YEAR FROM `datetime`)=? AND country='us' GROUP BY state ";
		List<Sighting> o = new ArrayList<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {	
//				Sighting s= new Sighting (rs.getString("state"));
				Sighting s= new Sighting (rs.getInt("id"), rs.getDate("datetime").toLocalDate(),rs.getString("state"),new LatLng(rs.getDouble("latitude"), rs.getDouble("longitude")));
				o.add(s);	}	
			st.close();
			conn.close();
			return o;
		} catch (SQLException e) {e.printStackTrace();
			return null;
			}
	 }
	

	public static void main(String[] args) {
		UfoDAO dao = new UfoDAO() ;
		
		List<Sighting> years = dao.getAllStati(2014);
		System.out.println(years);
		
		List<CoppiaInt> getCoppiaIntAnni=dao.getAllCoppiaIntAnni();
		System.out.println(getCoppiaIntAnni);

		
	}

	
}


