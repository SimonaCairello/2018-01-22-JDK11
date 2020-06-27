package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.StagionePunti;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listAllSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> getTeams() {
		String sql = "SELECT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<StagionePunti> getWin(Team team) {
		String sql = "SELECT m.Season, COUNT(m.FTR)*3 AS win " + 
				"FROM matches AS m " + 
				"WHERE (m.HomeTeam = ? AND FTR = 'H') OR (m.AwayTeam = ? AND FTR = 'A') " + 
				"GROUP BY m.Season";
		List<StagionePunti> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, team.getTeam());
			st.setString(2, team.getTeam());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new StagionePunti(res.getInt("Season"), res.getInt("win")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<StagionePunti> getDraw(Team team) {
		String sql = "SELECT m.Season, COUNT(m.FTR) AS draw " + 
				"FROM matches AS m " + 
				"WHERE (m.HomeTeam = ? OR m.AwayTeam = ?) AND FTR = 'D' " + 
				"GROUP BY m.Season";
		List<StagionePunti> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, team.getTeam());
			st.setString(2, team.getTeam());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new StagionePunti(res.getInt("Season"), res.getInt("draw")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
