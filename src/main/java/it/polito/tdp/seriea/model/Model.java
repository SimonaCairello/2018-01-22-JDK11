package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SerieADAO dao;
	private Graph<Integer, DefaultWeightedEdge> graph;
	private List<StagionePunti> puntiStagione;
	
	private List<StagionePunti> camminoVirtuoso;
	
	public Model() {
		this.dao = new SerieADAO();
		this.puntiStagione = new ArrayList<>();
	}

	public List<Team> getTeams() {
		return this.dao.getTeams();
	}
	
	public List<StagionePunti> getSeasonsResult(Team team) {
		List<StagionePunti> win = new ArrayList<>(this.dao.getWin(team));
		List<StagionePunti> draw = new ArrayList<>(this.dao.getDraw(team));
		
		for(StagionePunti s : win) {
			for(StagionePunti sp : draw) {
				if(s.getStagione().equals(sp.getStagione())) {
					Integer tot = s.getPunti()+sp.getPunti();
					StagionePunti stagPun = new StagionePunti(s.getStagione(), tot);
					this.puntiStagione.add(stagPun);
				}
			}
		}
		return this.puntiStagione;
	}
	
	public void generateGraph(Team team) {
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		for(StagionePunti sp : this.puntiStagione) {
			this.graph.addVertex(sp.getStagione());
		}
		
		for(StagionePunti s : this.puntiStagione) {
			for(StagionePunti sp : this.puntiStagione) {
				if(!s.equals(sp)) {
					if(s.getPunti()>=sp.getPunti())
						Graphs.addEdge(this.graph, s.getStagione(), sp.getStagione(), s.getPunti()-sp.getPunti());
					if(s.getPunti()<sp.getPunti())
						Graphs.addEdge(this.graph, sp.getStagione(), s.getStagione(), sp.getPunti()-s.getPunti());
				}
			}
		}		
	}
	
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	
	public StagionePunti getAnnataDoro() {
		StagionePunti annataOro = null;
		Integer max = 0;
		
		for(Integer i : this.graph.vertexSet()) {
			Integer entranti = 0;
			Integer uscenti = 0;
			for(DefaultWeightedEdge di : this.graph.incomingEdgesOf(i)) {
				entranti += (int) this.graph.getEdgeWeight(di);
			}
			for(DefaultWeightedEdge d : this.graph.outgoingEdgesOf(i)) {
				uscenti += (int) this.graph.getEdgeWeight(d);
			}
			Integer somma = entranti-uscenti;
			if(somma>max) {
				annataOro = new StagionePunti(i, somma);
				max = somma;
			}
		}
		
		return annataOro;
	}
	
	public List<StagionePunti> getCamminoVirtuoso() {
		this.camminoVirtuoso = new ArrayList<>();
		List<StagionePunti> parziale = new ArrayList<>();
		Collections.sort(puntiStagione);
		
		for(StagionePunti sp : this.puntiStagione) {
			parziale.add(sp);
			ricorsiva(parziale);
			parziale.remove(parziale.size()-1);
		}
		return this.camminoVirtuoso;
	}
	
	public void ricorsiva(List<StagionePunti> parziale) {
		if(parziale.size()>this.camminoVirtuoso.size()) {
			this.camminoVirtuoso = new ArrayList<>(parziale);
			return;
		}
		
		StagionePunti ultimaAggiunta = parziale.get(parziale.size()-1);
		
		for(Integer in : Graphs.successorListOf(this.graph, ultimaAggiunta.getStagione())) {
			Integer indice = this.puntiStagione.indexOf(ultimaAggiunta)+1;
			if(this.puntiStagione.size()>indice) {
				if(in==this.puntiStagione.get(indice).getStagione()) {
					parziale.add(this.puntiStagione.get(indice));
					ricorsiva(parziale);
					
					parziale.remove(parziale.size()-1);
				}
			}
		}		
	}
}
