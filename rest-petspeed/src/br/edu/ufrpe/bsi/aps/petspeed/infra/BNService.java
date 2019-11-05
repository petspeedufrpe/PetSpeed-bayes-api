package br.edu.ufrpe.bsi.aps.petspeed.infra;

import java.util.List;

import br.edu.ufrpe.bsi.aps.petspeed.models.BNetworkDBModel;
import br.edu.ufrpe.bsi.aps.petspeed.persistencia.BayesNetDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import smile.Network;

public class BNService {

	public List<DiseaseProb> getProbData(Symptoms symptomsList) {
		final String EVIDENCE = "Possui";
		
		Network net = loadNetwork();
		
		System.out.println(symptomsList);

		for (int i = 0; i < symptomsList.getSymptomsList().size(); i++) {
			changeEvidenceAndUpdate(net, symptomsList.getSymptomsList().get(i), EVIDENCE);
		}
		return makeInferenceToGetProbs(net);
	}

	private Network loadNetwork() {
		try {
		setLicense();
		}catch(Exception e) {
			System.out.println("CAPTUROU EXCESSÂO SMILE License");
		}
		Network net = new Network();

		net.readString(getNetData());
		net.updateBeliefs();
		return net;
	}
	
	public String getNetData() {
		BayesNetDAO netDAO = new BayesNetDAO();
		BNetworkDBModel netDataModel;
		try {
			netDataModel = netDAO.getNetworkModel();
			String network = netDataModel.getNetwork();
			return network;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void setLicense() {
		new smile.License(
				"SMILE LICENSE 1f4555cf 45159b76 171b4f23 " + "THIS IS AN ACADEMIC LICENSE AND CAN BE USED "
						+ "SOLELY FOR ACADEMIC RESEARCH AND TEACHING, " + "AS DEFINED IN THE BAYESFUSION ACADEMIC "
						+ "SOFTWARE LICENSING AGREEMENT. " + "Serial #: 3u1zz49guolbwahlw778tsxp8 "
						+ "Issued for: Arthur Queiroz (arthurclaizoni@gmail.com) "
						+ "Academic institution: Federal Rural University of Pernambuco - Recife- PE, Brazil "
						+ "Valid until: 2019-12-23 " + "Issued by BayesFusion activation server",
				new byte[] { 66, 40, -2, -56, 103, 88, -86, -76, -107, -92, 104, 95, 17, -88, -66, -44, -117, 78, 54,
						118, 123, 1, 53, -51, -24, -76, 71, -81, 14, -19, 112, -46, -117, 112, -9, 23, 5, -15, 9, 116,
						-26, 69, -53, -126, 102, 90, 68, 23, -108, -22, 127, 74, 12, 40, 73, 17, 9, 9, -113, 12, -34,
						28, -18, -66 });
	}

	private List<DiseaseProb> makeInferenceToGetProbs(Network net) {
		List<DiseaseProb> orderedDiseases = new ArrayList<>();
		final String EVIDENCE_POSSUI = "T";

		setNodeType(net, orderedDiseases, EVIDENCE_POSSUI);
		sortDiseaseProbs(orderedDiseases);
		return orderedDiseases;
	}


	private void setNodeType(Network net, List<DiseaseProb> orderedDiseases, final String EVIDENCE_POSSUI) {
		for (int nodeID = net.getFirstNode(); nodeID >= 0; nodeID = net.getNextNode(nodeID)) {
			if (net.getOutcomeId(nodeID, 0).equals(EVIDENCE_POSSUI) ^ net.getOutcomeId(nodeID, 0).equals("Doente")) {
				setDiseaseeProb(net, orderedDiseases, nodeID);
			}
		}
	}

	private void sortDiseaseProbs(List<DiseaseProb> orderedDiseases) {
		Collections.sort(orderedDiseases, new Comparator<DiseaseProb>() {
			public int compare(DiseaseProb prob1, DiseaseProb prob2) {
				if (prob1.getProb() < prob2.getProb()) {
					return 1;
				} else if (prob1.getProb() > prob2.getProb()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
	}

	private void setDiseaseeProb(Network net, List<DiseaseProb> orderedDiseases, int nodeID) {
		DiseaseProb disease = new DiseaseProb();
		disease.setProb(net.getNodeValue(nodeID)[0]);
		disease.setNome(net.getNodeName(nodeID));
		orderedDiseases.add(disease);
	}

	private void changeEvidenceAndUpdate(Network net, String nodeId, String outcomeId) {
		if (outcomeId != null) {
			net.setEvidence(nodeId, outcomeId);
		} else {
			net.clearEvidence(nodeId);
		}
		net.updateBeliefs();

	}
}
