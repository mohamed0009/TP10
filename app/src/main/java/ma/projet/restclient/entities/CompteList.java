package ma.projet.restclient.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.List;

/**
 * CompteList - Wrapper class for XML deserialization of account lists.
 * 
 * <p>
 * This class is specifically designed to handle XML responses from the REST API
 * when multiple accounts are returned. SimpleXML requires a wrapper class to
 * properly
 * deserialize XML lists, as it cannot directly deserialize to
 * List&lt;Compte&gt;.
 * </p>
 * 
 * <p>
 * XML Structure Example:
 * </p>
 * 
 * <pre>
 * &lt;List&gt;
 *   &lt;item&gt;
 *     &lt;id&gt;1&lt;/id&gt;
 *     &lt;solde&gt;1000.0&lt;/solde&gt;
 *     &lt;type&gt;COURANT&lt;/type&gt;
 *     &lt;dateCreation&gt;2025-11-09&lt;/dateCreation&gt;
 *   &lt;/item&gt;
 *   &lt;item&gt;...&lt;/item&gt;
 * &lt;/List&gt;
 * </pre>
 * 
 * @author Mohamed
 * @version 1.0
 * @since 2025-11-09
 */
@Root(name = "List", strict = false)
public class CompteList {
    /** List of account items from XML response */
    @ElementList(inline = true, entry = "item")
    private List<Compte> comptes;

    /**
     * Gets the list of accounts.
     * 
     * @return List of Compte objects deserialized from XML
     */
    public List<Compte> getComptes() {
        return comptes;
    }

    /**
     * Sets the list of accounts.
     * 
     * @param comptes List of Compte objects to set
     */
    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}