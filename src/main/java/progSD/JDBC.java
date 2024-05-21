package progSD;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class JDBC implements CommandLineRunner {
	
	  public static void main(String args[]) {
		  SpringApplication.run(JDBC.class, args);
	  }
	
	  @Autowired
	  JdbcTemplate jdbcTemplate;
	  @Autowired
	  private ColumnsRepository repoC;
	  @Autowired
	  private TilesRepository repoT;
	  
	  @Bean
		CommandLineRunner init(StorageService storageService) {
			return (args) -> {
				storageService.init();
			};
		}
	
	  @Override
	  public void run(String... strings) throws Exception {
		//    log.info("Creating tables");
		//    repoC.save(new Colonna("the bue"));
		//  Colonna colonna1 = new Colonna("colonna1");
		//	  Colonna colonna3 = new Colonna("colonna3");
		//	  Colonna colonna4 = new Colonna("colonna4");
		//	  repoC.save(colonna1);
		//	  repoC.save(colonna3);
		//	  repoC.save(colonna4);
		//	  repoT.save(new Tile(colonna1, "PastaAlSugo", "Gaia", "testuale", "nnaahh", "organizzativo"));
		//	  repoT.save(new Tile(colonna3, "PastaAlPesto", "Gaia", "multimediale", "-foto-", "informativo"));
		//	  repoT.save(new Tile(colonna3, "Casera", "Andrea", "testuale", ":)", "informativo"));
		//	  repoT.save(new Tile(colonna4, "Panino", "Fede", "multimediale", "con dentro il prosciuttino", "organizzativo"));
			  
		//    Iterable<Colonna> colonneIterable = repoC.findAll();
		//    for (Colonna colonna : colonneIterable) {
		//		System.out.println(colonna.getTitolo());
		//	}
		//    Iterable<Tile> tilesIterable = repoT.findAll();
		//    for (Tile tile : tilesIterable) {
		//		System.out.println(tile.toString());
		//	}
		    
		
		//    jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		//    jdbcTemplate.execute("CREATE TABLE customers(" +
		//        "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
		//
		//    // Split up the array of whole names into an array of first/last names
		//    List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
		//        .map(name -> name.split(" "))
		//        .collect(Collectors.toList());
		//
		//    // Use a Java 8 stream to print out each tuple of the list
		//    splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));
		
		    // Uses JdbcTemplate's batchUpdate operation to bulk load data
		//    jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
		//
		//    log.info("Querying for customer records where first_name = 'Josh':");
		//    jdbcTemplate.query(
		//        "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" },
		//        (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
		//    ).forEach(customer -> log.info(customer.toString()));
	  }
}