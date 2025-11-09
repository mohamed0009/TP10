package ma.projet.restclient.api;

import ma.projet.restclient.entities.Compte;
import ma.projet.restclient.entities.CompteList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * CompteService - Retrofit service interface for account API endpoints.
 * 
 * <p>
 * This interface defines all RESTful API endpoints for account management
 * operations.
 * It supports both JSON and XML data formats through custom headers and
 * provides
 * complete CRUD functionality.
 * </p>
 * 
 * <p>
 * Base URL is configured in RetrofitClient. All endpoints are relative to:
 * http://10.0.2.2:8082/api/comptes
 * </p>
 * 
 * <p>
 * Supported Operations:
 * </p>
 * <ul>
 * <li>GET - Retrieve all accounts or a specific account by ID</li>
 * <li>POST - Create a new account</li>
 * <li>PUT - Update an existing account</li>
 * <li>DELETE - Remove an account</li>
 * </ul>
 * 
 * @author Mohamed
 * @version 1.0
 * @since 2025-11-09
 */
public interface CompteService {

    /**
     * Retrieves all accounts in JSON format.
     * 
     * @return A Retrofit Call object containing a list of Compte objects
     */
    @GET("api/comptes")
    @Headers("Accept: application/json")
    Call<List<Compte>> getAllCompteJson();

    /**
     * Retrieves all accounts in XML format.
     * 
     * @return A Retrofit Call object containing a CompteList wrapper
     */
    @GET("api/comptes")
    @Headers("Accept: application/xml")
    Call<CompteList> getAllCompteXml();

    /**
     * Retrieves a specific account by its unique identifier.
     * 
     * @param id The unique identifier of the account
     * @return A Retrofit Call object containing the requested Compte
     */
    @GET("api/comptes/{id}")
    Call<Compte> getCompteById(@Path("id") Long id);

    /**
     * Creates a new account in the system.
     * 
     * @param compte The Compte object to be created
     * @return A Retrofit Call object containing the created Compte with its
     *         assigned ID
     */
    @POST("api/comptes")
    Call<Compte> addCompte(@Body Compte compte);

    /**
     * Updates an existing account with new data.
     * 
     * @param id     The unique identifier of the account to update
     * @param compte The Compte object containing updated data
     * @return A Retrofit Call object containing the updated Compte
     */
    @PUT("api/comptes/{id}")
    Call<Compte> updateCompte(@Path("id") Long id, @Body Compte compte);

    /**
     * Deletes an account from the system.
     * 
     * @param id The unique identifier of the account to delete
     * @return A Retrofit Call object with Void response on successful deletion
     */
    @DELETE("api/comptes/{id}")
    Call<Void> deleteCompte(@Path("id") Long id);
}