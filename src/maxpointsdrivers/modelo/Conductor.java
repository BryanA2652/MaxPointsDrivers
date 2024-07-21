/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maxpointsdrivers.modelo;

/**
 *
 * @author ROCIO
 */
public class Conductor {
     private String forename;
    private String surname;
    private double totalPoints;

    public Conductor(String forename, String surname, double totalPoints) {
        this.forename = forename;
        this.surname = surname;
        this.totalPoints = totalPoints;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public double getTotalPoints() {
        return totalPoints;
    }
    
    
}
