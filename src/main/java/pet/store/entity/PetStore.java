package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//JPA and Hibernate create customer table and this entity's relationship to the pet stores
@Entity //Tells JPA that this class is an entity and maps to a table
@Data  //This generates getters, setters, toString, equals and hashCode methods
public class PetStore {
	@Id //Tells JPA that this is PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Indicates that Db will assign PK
	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip;
	private String petStorePhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//This creates the many-to-many relationship between stores and customers
	@ManyToMany(cascade = CascadeType.PERSIST)
	
	//This gives the join table name and columns on which to join
	@JoinTable(name = "pet_store_customer", joinColumns = @JoinColumn(name = "pet_store_id"), 
	inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//This creates the one-to-many relationship between store and employees
	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();
	
}
