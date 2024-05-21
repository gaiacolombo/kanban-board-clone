package progSD;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id",scope = Tile.class)
@JsonIgnoreProperties("tiles")
@Entity
public class Tile implements Serializable  {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	

	@ManyToOne
    @JsonBackReference
    @JsonIgnoreProperties(value="tiles", allowSetters=true)
    @NotNull
    private Colonna colonna;
    private String titolo;
    private String autore;
    private String tipoContenuto;
    
    @Column(length = 100000)
    private String contenuto;
    private String tipoMessaggio;

    public Tile() {
    	
    }

    public Tile(Colonna colonna, String titolo, String autore, String tipoContenuto, String contenuto,
            String tipoMessaggio) {
        this.colonna = colonna;
        this.titolo = titolo;
        this.autore = autore;
        this.tipoContenuto = tipoContenuto;
        this.contenuto = contenuto;
        this.tipoMessaggio = tipoMessaggio;
    }
    
    public Colonna getColonna() {
		return colonna;
	}
    
	public void setColonna(Colonna colonna) {
		this.colonna = colonna;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getTipoContenuto() {
		return tipoContenuto;
	}

	public void setTipoContenuto(String tipoContenuto) {
		this.tipoContenuto = tipoContenuto;
	}

	public String getContenuto() {
		return contenuto;
	}


	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public String getTipoMessaggio() {
		return tipoMessaggio;
	}

	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}

	@Override
	public String toString() {
		return "Tile [id=" + id + ", titolo=" + titolo + ", autore=" + autore + ", tipoContenuto=" + tipoContenuto
				+ ", contenuto=" + contenuto + ", tipoMessaggio=" + tipoMessaggio + "]";
	}
	
	public int getId() {
		return id;
	}   
}