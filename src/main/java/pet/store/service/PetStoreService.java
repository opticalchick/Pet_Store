package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.dao.CustomerDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

//Service class is used to manage transaction performed in the Dao interface

//Denotes to Spring that this is a service class and creates the bean for PetStoreService
@Service
public class PetStoreService {

	//Injects PetStoreDao bean into field
	@Autowired
	private PetStoreDao petStoreDao;
	
	//Injects EmployeeDao bean into field
	@Autowired
	private EmployeeDao employeeDao;
	
	//Injects CustomerDao bean into field
	@Autowired
	private CustomerDao customerDao;

	//Saves created or updated store data in pet_store table using save() method in PetStoreDao
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId());
		copyPetStoreFields(petStore, petStoreData);

		PetStore dbPetStore = petStoreDao.save(petStore);
		return new PetStoreData(dbPetStore);
	}
	//sets values of PetStore fields using values in petStoreData fields
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());

	}
	//adds new pet store is Id is null, otherwise calls findPetStoreById method to retrieve from db
	private PetStore findOrCreatePetStore(Long petStoreId) {
		PetStore petStore;

		if (Objects.isNull(petStoreId)) {
			petStore = new PetStore();
		} else {
			petStore = findPetStoreById(petStoreId);
		}

		return petStore;
	}
	//retrieves pet store from db using Id.  If Id is not valid, it will throw an exception
	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet Store with ID=" + petStoreId + 
						" does not exist."));
	}
	//saves employee info in employee table using save() method from EmployeeDao interface
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Employee employee = findOrCreateEmployee(petStoreId, petStoreEmployee.getEmployeeId());
		copyEmployeeFields(employee, petStoreEmployee);
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new PetStoreEmployee(dbEmployee);
	}
	//sets values of Employee fields to values of petStoreEmployee fields
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
		
	}
	//creates new employee if Id is null, otherwise retrieves employee by Id
	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		Employee employee;
		
		if(Objects.isNull(employeeId)) {
			employee = new Employee();
		} else {
			employee = findEmployeeById(petStoreId, employeeId);
		}
		
		return employee;
	}
	//finds employee in db by Id and returns employee, otherwise throws and exception
	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException
						("Employee with ID=" + employeeId + " does not exist."));
		
		if(employee.getPetStore().getPetStoreId() == petStoreId) {
			return employee;
		} else {
			throw new IllegalArgumentException
			("Pet store with ID=" + petStoreId + " does not have an employee with ID=" + employeeId);
		}
	}

	//saves customer info in customer table using save() method from CustomerDao interface
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Customer customer = findOrCreateCustomer(petStoreId, petStoreCustomer.getCustomerId());
		copyCustomerFields(customer, petStoreCustomer);
		customer.getPetStores().add(petStore);
		petStore.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new PetStoreCustomer(dbCustomer);
	}
	
	//sets values of Customer fields to values of petStoreCustomer fields
	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
		
	}
	
	//finds customer by Id or creates a new customer if Id is null
	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		Customer customer;
		
		if(Objects.isNull(customerId)) {
			customer = new Customer();
		}  else {
			customer = findCustomerbyId(petStoreId, customerId);
		}
		
		return customer;
	}

	//finds customer in db by Id and returns customer, otherwise throws an exception.  Also will throw 
	//exception 
	private Customer findCustomerbyId(Long petStoreId, Long customerId) {
		boolean petStoreIdMatch = false;
		
		Customer customer = customerDao.findById(customerId).orElseThrow(() ->
		new NoSuchElementException("Customer with ID=" + customerId + " does not exist"));
		
		Set<PetStore> petStores = customer.getPetStores();
		for(PetStore petStore : petStores) {
			if(petStore.getPetStoreId() == petStoreId) {
				petStoreIdMatch = true;
			}
		}
		
		if(petStoreIdMatch) {
			return customer;
		} else {
			throw new IllegalArgumentException("Pet store with ID=" + petStoreId + 
					" does not have a customer with ID=" + customerId);
		}
	}
	
	//retrieves all pet stores only & removes employee and customer data before returning stores only
	@Transactional
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> results = new LinkedList<>();
		
		for(PetStore petStore : petStores) {
			PetStoreData petStoreData = new PetStoreData(petStore);
			
			petStoreData.getEmployees().clear();
			petStoreData.getCustomers().clear();
			
			results.add(petStoreData);
		}
		
		return results;
	}
	
	//retrieves pet store using Id
	public PetStoreData retrivePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		return new PetStoreData(petStore);
	}
	
	
	//deletes store using Id
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}
}	
