package progSD;


import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id",scope=Colonna.class)
@JsonIgnoreProperties("colonna")
@Entity
public class Colonna implements Serializable {	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(unique=true)
    private String titolo;
	
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL, mappedBy ="colonna")
	@JsonIgnoreProperties(value="colonna", allowSetters=true)
	@JsonManagedReference
	private Collection<Tile> tiles=new ArrayList<>();
	
    private String stato;

	public Colonna() {
		
	}
	
	public Colonna(String titolo) {
		super();
		this.titolo = titolo;
		this.stato = "in_corso";
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getStato() {
		return stato;
	}

	public void setStato() {
		if(this.getStato().equals("archiviata"))
			this.stato = "in_corso";
		else if(this.getStato().equals("in_corso"))
			this.stato = "archiviata";
	}
	public int getId() {
		return id;
	}
}