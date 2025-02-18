package at.ac.fhsalzburg.swd.spring;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
	private String licensePlates;
    private boolean blocked;
	private String type;
											// NO TICKETS IMPLEMENTED AS REFERRED TO IN CLASS DIAGRAM
	// FUNCTIONS
	boolean block(boolean newState){
		this.blocked = newState;
		return true;
	};

	// CONSTRUCTORS
    protected Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    // GETTERS
	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLicensePlates() {
		return licensePlates;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public String getType() { return type; };


    // SETTERS
	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLicensePlates(String licensePlates) {
		this.licensePlates = licensePlates;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public void setType(String type) {
		this.type = type;
	}




}
