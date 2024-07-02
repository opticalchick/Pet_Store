package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


//JPA and Hibernate create customer table and this entity's relationship to the pet stores
@Entity //Tells JPA that this class is an entity and maps to a table
@Data  //This generates getters, setters, toString, equals and hashCode methods
public class Customer {	
	@Id //tells JPA that this is PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) //this will allow the Db to generate the PK
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//This creates many-to-many relationship with customers and stores.
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	private Set<PetStore> petStores = new HashSet<>();
}
