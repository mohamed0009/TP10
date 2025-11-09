package ma.projet.restclient.repository;

import ma.projet.restclient.api.CompteService;
import ma.projet.restclient.entities.Compte;
import ma.projet.restclient.entities.CompteList;
import ma.projet.restclient.config.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * CompteRepository - Repository pattern implementation for account data
 * operations.
 * 
 * <p>
 * This class serves as an abstraction layer between the UI (MainActivity) and
 * the
 * REST API service (CompteService). It handles all data operations including
 * fetching,
 * creating, updating, and deleting accounts, with support for both JSON and XML
 * formats.
 * </p>
 * 
 * <p>
 * Design Pattern: Repository Pattern
 * </p>
 * <ul>
 * <li>Encapsulates data access logic</li>
 * <li>Provides a clean API for data operations</li>
 * <li>Handles format conversion (JSON/XML)</li>
 * <li>Manages Retrofit service initialization</li>
 * </ul>
 * 
 * <p>
 * Supported Formats:
 * </p>
 * <ul>
 * <li>JSON - Direct deserialization to List&lt;Compte&gt;</li>
 * <li>XML - Deserialization via CompteList wrapper</li>
 * </ul>
 * 
 * @author Mohamed
 * @version 1.0
 * @since 2025-11-09
 */
public class CompteRepository {
    /** Retrofit service interface for API calls */
    private CompteService compteService;

    /** Data format being used (JSON or XML) */
    private String format;

    /**
     * Constructs a repository with the specified data format.
     * Initializes the Retrofit service with the appropriate converter.
     * 
     * @param converterType The data format to use ("JSON" or "XML")
     */
    public CompteRepository(String converterType) {
        compteService = RetrofitClient.getClient(converterType).create(CompteService.class);
        this.format = converterType;
    }

    /**
     * Retrieves all accounts from the backend.
     * Automatically handles format conversion based on the repository's configured
     * format.
     * 
     * <p>
     * For JSON: Directly deserializes to List&lt;Compte&gt;
     * </p>
     * <p>
     * For XML: Deserializes to CompteList wrapper, then extracts the list
     * </p>
     * 
     * @param callback Retrofit callback to handle the response
     */
    public void getAllCompte(Callback<List<Compte>> callback) {
        if ("JSON".equals(format)) {
            Call<List<Compte>> call = compteService.getAllCompteJson();
            call.enqueue(callback);
        } else {
            Call<CompteList> call = compteService.getAllCompteXml();
            call.enqueue(new Callback<CompteList>() {
                @Override
                public void onResponse(Call<CompteList> call, Response<CompteList> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Convert CompteList wrapper to List<Compte>
                        List<Compte> comptes = response.body().getComptes();
                        callback.onResponse(null, Response.success(comptes));
                    }
                }

                @Override
                public void onFailure(Call<CompteList> call, Throwable t) {
                    // Propagate error to the callback
                }
            });
        }
    }

    /**
     * Retrieves a specific account by its unique identifier.
     * 
     * @param id       The unique identifier of the account to retrieve
     * @param callback Retrofit callback to handle the response
     */
    public void getCompteById(Long id, Callback<Compte> callback) {
        Call<Compte> call = compteService.getCompteById(id);
        call.enqueue(callback);
    }

    /**
     * Adds a new account to the backend.
     * 
     * @param compte   The Compte object to be created
     * @param callback Retrofit callback to handle the response
     */
    public void addCompte(Compte compte, Callback<Compte> callback) {
        Call<Compte> call = compteService.addCompte(compte);
        call.enqueue(callback);
    }

    /**
     * Updates an existing account with new data.
     * 
     * @param id       The unique identifier of the account to update
     * @param compte   The Compte object containing updated data
     * @param callback Retrofit callback to handle the response
     */
    public void updateCompte(Long id, Compte compte, Callback<Compte> callback) {
        Call<Compte> call = compteService.updateCompte(id, compte);
        call.enqueue(callback);
    }

    /**
     * Deletes an account from the backend.
     * 
     * @param id       The unique identifier of the account to delete
     * @param callback Retrofit callback to handle the response
     */
    public void deleteCompte(Long id, Callback<Void> callback) {
        Call<Void> call = compteService.deleteCompte(id);
        call.enqueue(callback);
    }
}