package at.ac.fhsalzburg.swd.spring;



public class Status {

    public String currentUserFirstName;
    public String currentUserLastName;
    public String loginStatus;
    public String ticketStatus;
    public String ticketCustomer;
    public boolean firstStart;


    Status(){
        currentUserFirstName = "";
        currentUserLastName = "";
        loginStatus = "not logged in";
        ticketStatus = "";
        ticketCustomer = "";
    }

}
