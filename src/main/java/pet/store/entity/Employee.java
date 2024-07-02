package pet.store.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//JPA and Hibernate create customer table and this entity's relationship to the pet stores
@Entity //Tells JPA that this class is an entity and maps to a table
@Data  //This generates getters, setters, toString, equals and hashCode methods
public class Employee {
	@Id  //Tells JPA that this is PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //Tells JPA that Db will assign PK
	
	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//Creates many-to-one relationship between employees and pet store
	@ManyToOne(cascade = CascadeType.ALL)
	//Identifies the FK in employee table
	@JoinColumn(name = "pet_store_id")
	private PetStore petStore;

}
