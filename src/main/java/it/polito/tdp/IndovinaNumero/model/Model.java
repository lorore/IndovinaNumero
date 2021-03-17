package it.polito.tdp.IndovinaNumero.model;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

public class Model {

	private final int NMAX = 100;
	private final int TMAX = 8;
	private int segreto;
	private int tentativiFatti;
	private boolean inGioco = false;
	
	private Set<Integer> tentativi;
	
	public void nuovaPartita() {
		//gestione inizio nuova partita
    	this.segreto = (int) (Math.random() * NMAX) +1;
    	this.tentativiFatti = 0;
    	this.inGioco = true;
    	
    	this.tentativi=new HashSet<Integer>();
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
					+" e non puoi inserire due volte lo stesso numero");
		}
		
		//il tentativo è valido
		this.tentativiFatti++;
		this.tentativi.add(tentativo);
		if(this.tentativiFatti==(TMAX-1))
			this.inGioco=false; //la partita è finita
		
		if(tentativo==this.segreto) {
			this.inGioco=false; //la partita è finita
			return 0;
		}
		else if(tentativo<this.segreto)
			return -1;
		else
			return 1;
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
	
	public int getTMAX() {
		return TMAX;
	}

	public int getSegreto() {
		return segreto;
	}

	

	public int getTentativiFatti() {
		return tentativiFatti;
	}

	

	public int getNMAX() {
		return NMAX;
	}
	
	
}
