package progSD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class RESTcontroller {

	@Autowired
	  private ColumnsRepository repoC;
	@Autowired
	  private TilesRepository repoT;

	@RequestMapping(method = RequestMethod.GET, value = "/repoC/", produces = MediaType.APPLICATION_JSON_VALUE)
	public String repoCGET() throws IOException, URISyntaxException {
		Iterable<Colonna> colonneIterable = repoC.findAll();
		ObjectMapper mapper = new ObjectMapper(); 
		String jsonResult = mapper.writeValueAsString(colonneIterable);
		return jsonResult;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String colonneGET() throws IOException, URISyntaxException {
		URL res = getClass().getClassLoader().getResource("index.html");
		File file = Paths.get(res.toURI()).toFile();
		String absolutePath = file.getAbsolutePath();
		BufferedReader reader = new BufferedReader(new FileReader (absolutePath));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");
	    
	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }
	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/aggiungiColonna/")
	public String aggiungiColonnaGET() throws URISyntaxException, IOException {
		URL res = getClass().getClassLoader().getResource("aggiungiColonna.html");
		File file = Paths.get(res.toURI()).toFile();
		String absolutePath = file.getAbsolutePath();
		BufferedReader reader = new BufferedReader(new FileReader (absolutePath));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");
	    
	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }
	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/archiviate/")
	public String archiviateGET() throws IOException, URISyntaxException {
		URL res = getClass().getClassLoader().getResource("archiviate.html");
		File file = Paths.get(res.toURI()).toFile();
		String absolutePath = file.getAbsolutePath();
		BufferedReader reader = new BufferedReader(new FileReader (absolutePath));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }
	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	
	// -.-
	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminaColonna/{idColonna}/")	
	public String eliminaColonnaDELETE(@PathVariable String idColonna) {
		try {
			int intId = Integer.parseInt(idColonna);			
			Iterable<Tile> tileIterable = repoT.findAll();
			for (Tile tileI : tileIterable) {
		    	if(tileI.getColonna().getId() == intId) {
		    		//stesso codice di cancella immagine
		    		File immagineVecchia = new File("./storage/" + tileI.getId() + ".jpg");
		            if (immagineVecchia.exists()) {
		            	immagineVecchia.delete();
		            }
		    		repoT.delete(tileI);
		    	}
		    }
			repoC.deleteById(intId);
			return ":)";
		} catch(Exception e) {
			throw e;
		}
	}
	
	//OK
	@RequestMapping(method = RequestMethod.POST, value = "/modificaTitoloColonna")
	public RedirectView modificaTitoloColonnaPUT(@RequestParam String id, @RequestParam String titoloNuovo) {
		try {
			if(!titoloNuovo.equals("")) {
				int intId = Integer.parseInt(id);
				Colonna c = repoC.findById(intId).get();
				c.setTitolo(titoloNuovo);
				repoC.save(c);
			}
			return new RedirectView("/");
		} catch(Exception e) {
			return new RedirectView("/");
		}
	}
	
	//OK
	@RequestMapping(method = RequestMethod.POST, value = "/cambiaStatoColonna/{idColonna}/")
		public String cambiaStatoColonnaPOST(@PathVariable String idColonna) {
		try {
			int intId = Integer.parseInt(idColonna);
			Colonna c = repoC.findById(intId).get();
			c.setStato();
			repoC.save(c);
			return ":)";
		} catch(Exception e) {
			throw e;
		}
	}
	
	//OK
	@RequestMapping(method = RequestMethod.POST, value = "/aggiungiTile")
	public int aggiungiTilePOST(@RequestParam String id, @RequestParam String titolo, @RequestParam String autore,
			@RequestParam String tipoContenuto, @RequestParam(required = false) String contenuto , @RequestParam String tipoMessaggio) throws IOException {
	    try {
	    	if(titolo.equals("")) {
	    		return 0;
	    	}
			int intId = Integer.parseInt(id);
			Colonna c = repoC.findById(intId).get();
			Tile t = new Tile(c, titolo, autore, tipoContenuto,  contenuto, tipoMessaggio);
			repoT.save(t);
			return t.getId();
		} catch(Exception e) {
			return 0;
		}
	}
	
	//OK
	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminaTile/{idTile}/")
	public String eliminaTileDELETE(@PathVariable String idTile) {
		try {
			int intId  = Integer.parseInt(idTile);
			Tile t = repoT.findById(intId).get();
			File immagineVecchia = new File("./storage/" + t.getId() + ".jpg");
            if (immagineVecchia.exists()) {
            	immagineVecchia.delete();
            }
			repoT.delete(t);
			return ":)";
		} catch(Exception e) {
			return e.toString();
		}
	}
	
	//OK
	@RequestMapping(method = RequestMethod.POST, value = "/modificaTile")
	public int modificaTilePOST(@RequestParam String id, /*@RequestParam String nuovaColonna*/ @RequestParam String idNuovaColonna, @RequestParam String titolo, @RequestParam String autore,
			@RequestParam String tipoContenuto, @RequestParam(required = false) String contenuto, @RequestParam String tipoMessaggio) {
		int intId  = Integer.parseInt(id);
		try {
			Tile t = repoT.findById(intId).get();
			if(!idNuovaColonna.equals("")) {
				int intIdNuovaColonna = Integer.parseInt(idNuovaColonna);
				Colonna c = repoC.findById(intIdNuovaColonna).get();
				t.setColonna(c);
			}
			if(!titolo.equals("")) {
    			t.setTitolo(titolo);
			}
			if(!autore.equals("")) {
    			t.setAutore(autore);
			}
			if(!contenuto.equals("")) {
    			t.setContenuto(contenuto);
			}
			t.setTipoContenuto(tipoContenuto);
			if(tipoContenuto.equals("multimediale")) {
				t.setContenuto("");
			}
			else {
				File immagineVecchia = new File("./storage/" + t.getId() + ".jpg");
	            if (immagineVecchia.exists()) {
	            	immagineVecchia.delete();
	            }
			}
			t.setTipoMessaggio(tipoMessaggio);
			repoT.save(t);
			return intId;
		} catch(Exception e){
			return intId;
		}
	}
	
	//
	@RequestMapping(method = RequestMethod.POST, value = "/aggiungiColonna")
	public RedirectView aggiungiColonnaPOST(@RequestParam String titolo) {
		try {
			//il titolo non dovrebbe mai essere null se mettiamo il validate
			if(titolo.equals("")) {
				//titolo vuoto
				return new RedirectView("/aggiungiColonna/");
			}
			repoC.save(new Colonna(titolo));
			return new RedirectView("/");
		}catch (Exception e) {
			//colonna che esiste già
			return new RedirectView("/aggiungiColonna/");
		}
	}
}