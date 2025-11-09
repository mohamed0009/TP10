package ma.projet.restclient.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import javax.xml.bind.annotation.XmlElement;

/**
 * Compte - Entity class representing a bank account.
 * 
 * <p>
 * This class serves as a data model for bank account information with support
 * for both JSON (via Gson) and XML (via SimpleXML)
 * serialization/deserialization.
 * It is used throughout the application for transferring account data between
 * the Android client and the REST API backend.
 * </p>
 * 
 * <p>
 * Account Types:
 * </p>
 * <ul>
 * <li>COURANT - Current/Checking account</li>
 * <li>EPARGNE - Savings account</li>
 * </ul>
 * 
 * <p>
 * Annotations:
 * </p>
 * <ul>
 * <li>@Root - Marks the class as an XML root element</li>
 * <li>@Element - Maps fields to XML elements</li>
 * <li>@XmlElement - Additional XML binding for compatibility</li>
 * </ul>
 * 
 * @author Mohamed
 * @version 1.0
 * @since 2025-11-09
 */
@Root(name = "item", strict = false)
public class Compte {
    /** Unique identifier for the account */
    @Element(name = "id")
    private Long id;

    /** Account balance amount */
    @Element(name = "solde")
    private double solde;

    /** Account type (COURANT or EPARGNE) */
    @Element(name = "type")
    private String type;

    /** Date when the account was created (format: yyyy-MM-dd) */
    @Element(name = "dateCreation")
    private String dateCreation;

    /**
     * Default no-argument constructor.
     * Required for XML/JSON deserialization.
     */
    public Compte() {
    }

    /**
     * Parameterized constructor for creating a Compte instance.
     * 
     * @param id           The unique account identifier
     * @param solde        The account balance
     * @param type         The account type (COURANT or EPARGNE)
     * @param dateCreation The account creation date
     */
    public Compte(Long id, double solde, String type, String dateCreation) {
        this.id = id;
        this.solde = solde;
        this.type = type;
        this.dateCreation = dateCreation;
    }

    // ==================== Setters ====================

    /**
     * Sets the account identifier.
     * 
     * @param id The unique account identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the account balance.
     * 
     * @param solde The account balance amount
     */
    public void setSolde(double solde) {
        this.solde = solde;
    }

    /**
     * Sets the account type.
     * 
     * @param type The account type (COURANT or EPARGNE)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the account creation date.
     * 
     * @param dateCreation The creation date in yyyy-MM-dd format
     */
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    // ==================== Getters ====================

    /**
     * Gets the account identifier.
     * 
     * @return The unique account identifier
     */
    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Gets the account balance.
     * 
     * @return The current account balance
     */
    @XmlElement(name = "solde")
    public double getSolde() {
        return solde;
    }

    /**
     * Gets the account type.
     * 
     * @return The account type (COURANT or EPARGNE)
     */
    @XmlElement(name = "type")
    public String getType() {
        return type;
    }

    /**
     * Gets the account creation date.
     * 
     * @return The creation date in yyyy-MM-dd format
     */
    @XmlElement(name = "dateCreation")
    public String getDateCreation() {
        return dateCreation;
    }

    /**
     * Returns a string representation of the account for debugging purposes.
     * Includes all account fields in a readable format.
     * 
     * @return A formatted string containing account details
     */
    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", solde=" + solde +
                ", type='" + type + "'" +
                ", dateCreation='" + dateCreation + "'" +
                '}';
    }
}
