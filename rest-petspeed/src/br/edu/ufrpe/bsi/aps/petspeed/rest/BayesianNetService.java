package br.edu.ufrpe.bsi.aps.petspeed.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.edu.ufrpe.bsi.aps.petspeed.infra.BNService;
import br.edu.ufrpe.bsi.aps.petspeed.infra.DiseaseProb;
import br.edu.ufrpe.bsi.aps.petspeed.infra.Symptoms;

@Path("/bayesnetwork")
public class BayesianNetService {

	private static final String CHARSET_UTF8 = ";charset=utf-8";


	@GET
	@Path("/getprobdata")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<DiseaseProb> getProbData(Symptoms symptomsList) {
		
		List<DiseaseProb> probabilityData = new BNService().getProbData(symptomsList);
		
		return probabilityData;
	}

	@GET
	@Path("/helloworld")
	@Produces(MediaType.TEXT_PLAIN + CHARSET_UTF8)
	public String helloWorld() {
		return "HelloPetSpeedWorld";
	}
}
