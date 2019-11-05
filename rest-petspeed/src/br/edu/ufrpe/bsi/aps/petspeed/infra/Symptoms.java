package br.edu.ufrpe.bsi.aps.petspeed.infra;

import java.util.ArrayList;
import java.util.List;

public class Symptoms {
	List<String> symptomsList = new ArrayList<String>();

	public List<String> getSymptomsList() {
		return symptomsList;
	}

	public void setSymptomsList(List<String> symptomList) {
		this.symptomsList = symptomList;
	}
	@Override
	public String toString() {
		
		return symptomsList.toString();
	}

}
