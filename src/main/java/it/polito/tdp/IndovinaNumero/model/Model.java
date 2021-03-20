package it.polito.tdp.IndovinaNumero.model;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

public class Model {

	private int NMAX;
	private int TMAX;
	private int segreto;
	private int tentativiFatti;
	private boolean inGioco = false;
	private int tA;
	private int tB;
	private boolean assistita=false;
	
	private Set<Integer> tentativi;
	
	public void nuovaPartita() {
		//gestione inizio nuova partita
    	this.segreto = (int) (Math.random() * NMAX) +1;
    	this.tentativiFatti = 0;
    	this.inGioco = true;
    	
    	this.tentativi=new HashSet<Integer>();
	}
	
	
	
	public boolean isAssistita() {
		return assistita;
	}



	public void setAssistita(boolean assistita) {
		this.assistita = assistita;
	}



	public void setNMAX(int nMAX) {
		NMAX = nMAX;
		this.settA(nMAX);
		this.settB(1);
	}



	public void settA(int tA) {
		this.tA = tA;
	}



	public void settB(int tB) {
		this.tB = tB;
	}



	public void setTMAX(int tMAX) {
		TMAX = tMAX;
	}



	public int tentativo(int tentativo) {
		//0 tentativo corretto
		//-1 tentativo troppo basso
		//+1 tentativo troppo alto
		
		//controllare se la partita è in corso
		if(!inGioco) {
			throw new IllegalStateException("La partita è già terminata");
		}
		
		//controllo dell'input 
		if(!this.tentativoValido(tentativo)) {
			throw new InvalidParameterException("Devi inserire un numero tra 1 e "+NMAX
					+"\n"+"e non puoi inserire due volte lo stesso numero");
		}
		
		//il tentativo è valido
		this.tentativiFatti++;
		this.tentativi.add(tentativo);
		if(this.tentativiFatti==TMAX)
			this.inGioco=false; //la partita è finita
		
		if(tentativo==this.segreto) {
			this.inGioco=false; //la partita è finita
			return 0;
		}
		else if(tentativo<this.segreto) {
			if(tentativo>=tB)
				tB=tentativo+1;
			return -1;
		}
		else {
			if(tentativo<=tA)
				tA=tentativo-1;
			return 1;
		}
	}
	
	// perchè se voglio aggiungere un controllo domani, non
	// voglio avere un if infinito sopra
	private boolean tentativoValido(int tentativo) {
		if(tentativo<1 || tentativo>NMAX)
			return false;
		if(tentativi.contains(tentativo))
			return false;
		
			return true;
	}
	
	public void abbandona() {
		this.inGioco=false;
	}
	
	public int getTMAX() {
		return TMAX;
	}

	public int getSegreto() {
		return segreto;
	}

	
	
	

	public int gettA() {
	/*if(tA!=this.NMAX)
		return tA-1;
		else*/
			return tA;
	}



	public int gettB() {
	/*	if(tB!=1)
		return tB+1;
		else*/
			return tB;
	}



	public int getTentativiFatti() {
		return tentativiFatti;
	}

	public boolean maxTentativiRaggiunti() {
		if(this.tentativiFatti==this.TMAX)
			return true;
		else
			return false;
	}

	public int getNMAX() {
		return NMAX;
	}
	
	
}
