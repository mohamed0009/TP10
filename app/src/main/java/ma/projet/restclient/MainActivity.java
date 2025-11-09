package ma.projet.restclient;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ma.projet.restclient.adapter.CompteAdapter;
import ma.projet.restclient.entities.Compte;
import ma.projet.restclient.repository.CompteRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MainActivity - Main entry point for the Account Management application.
 * 
 * <p>
 * This activity provides a comprehensive interface for managing bank accounts
 * with support for
 * both JSON and XML data formats. It implements CRUD operations (Create, Read,
 * Update, Delete)
 * through a RESTful API integration using Retrofit.
 * </p>
 * 
 * <p>
 * Key Features:
 * </p>
 * <ul>
 * <li>Display accounts in a RecyclerView with real-time updates</li>
 * <li>Toggle between JSON and XML data formats</li>
 * <li>Add new accounts with validation</li>
 * <li>Update existing account details</li>
 * <li>Delete accounts with confirmation dialogs</li>
 * </ul>
 * 
 * @author Mohamed
 * @version 1.0
 * @since 2025-11-09
 */
public class MainActivity extends AppCompatActivity
        implements CompteAdapter.OnDeleteClickListener, CompteAdapter.OnUpdateClickListener {
    /** RecyclerView component for displaying the list of accounts */
    private RecyclerView recyclerView;

    /** Adapter for managing account data in the RecyclerView */
    private CompteAdapter adapter;

    /** RadioGroup for selecting data format (JSON or XML) */
    private RadioGroup formatGroup;

    /** Floating action button for adding new accounts */
    private FloatingActionButton addbtn;

    /**
     * Called when the activity is first created.
     * Initializes the UI components, sets up listeners, and loads initial data.
     * 
     * @param savedInstanceState Bundle containing the activity's previously saved
     *                           state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupFormatSelection();
        setupAddButton();

        loadData("JSON");
    }

    /**
     * Initializes all view components by finding them in the layout.
     * This method should be called during onCreate to bind UI elements.
     */
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        formatGroup = findViewById(R.id.formatGroup);
        addbtn = findViewById(R.id.fabAdd);
    }

    /**
     * Configures the RecyclerView with a LinearLayoutManager and CompteAdapter.
     * Sets up the adapter with callback listeners for update and delete operations.
     */
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CompteAdapter(this, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Sets up the RadioGroup listener for format selection.
     * Reloads data when the user switches between JSON and XML formats.
     */
    private void setupFormatSelection() {
        formatGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String format = checkedId == R.id.radioJson ? "JSON" : "XML";
            loadData(format);
        });
    }

    /**
     * Configures the floating action button click listener.
     * Shows the add account dialog when the button is clicked.
     */
    private void setupAddButton() {
        addbtn.setOnClickListener(v -> showAddCompteDialog());
    }

    /**
     * Displays a dialog for adding a new account.
     * The dialog includes input fields for account balance and type
     * (COURANT/EPARGNE).
     * On confirmation, creates a new Compte object and submits it to the API.
     */
    private void showAddCompteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_compte, null);

        EditText etSolde = dialogView.findViewById(R.id.etSolde);
        RadioGroup typeGroup = dialogView.findViewById(R.id.typeGroup);

        builder.setView(dialogView)
                .setTitle("Ajouter un compte")
                .setPositiveButton("Ajouter", (dialog, which) -> {
                    String solde = etSolde.getText().toString();
                    String type = typeGroup.getCheckedRadioButtonId() == R.id.radioCourant
                            ? "COURANT"
                            : "EPARGNE";

                    String formattedDate = getCurrentDateFormatted();
                    Compte compte = new Compte(null, Double.parseDouble(solde), type, formattedDate);
                    addCompte(compte);
                })
                .setNegativeButton("Annuler", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Gets the current date formatted as yyyy-MM-dd.
     * Used for setting the creation date when adding new accounts.
     * 
     * @return String representation of the current date in yyyy-MM-dd format
     */
    private String getCurrentDateFormatted() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(calendar.getTime());
    }

    /**
     * Adds a new account to the backend via API call.
     * Displays success or error messages based on the response.
     * Reloads the account list on successful addition.
     * 
     * @param compte The Compte object to be added
     */
    private void addCompte(Compte compte) {
        CompteRepository compteRepository = new CompteRepository("JSON");
        compteRepository.addCompte(compte, new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                if (response.isSuccessful()) {
                    showToast("Compte ajouté");
                    loadData("JSON");
                }
            }

            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                showToast("Erreur lors de l'ajout");
            }
        });
    }

    /**
     * Loads account data from the backend API in the specified format.
     * Updates the RecyclerView adapter with the fetched data.
     * 
     * @param format The data format to use ("JSON" or "XML")
     */
    private void loadData(String format) {
        CompteRepository compteRepository = new CompteRepository(format);
        compteRepository.getAllCompte(new Callback<List<Compte>>() {
            @Override
            public void onResponse(Call<List<Compte>> call, Response<List<Compte>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Compte> comptes = response.body();
                    runOnUiThread(() -> adapter.updateData(comptes));
                }
            }

            @Override
            public void onFailure(Call<List<Compte>> call, Throwable t) {
                showToast("Erreur: " + t.getMessage());
            }
        });
    }

    /**
     * Callback method triggered when the update button is clicked on an account
     * item.
     * Opens the update dialog pre-populated with the account's current data.
     * 
     * @param compte The Compte object to be updated
     */
    @Override
    public void onUpdateClick(Compte compte) {
        showUpdateCompteDialog(compte);
    }

    /**
     * Displays a dialog for updating an existing account.
     * Pre-fills the form with the current account data.
     * On confirmation, updates the account via the API.
     * 
     * @param compte The Compte object containing current data to be modified
     */
    private void showUpdateCompteDialog(Compte compte) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_compte, null);

        EditText etSolde = dialogView.findViewById(R.id.etSolde);
        RadioGroup typeGroup = dialogView.findViewById(R.id.typeGroup);
        etSolde.setText(String.valueOf(compte.getSolde()));
        if (compte.getType().equalsIgnoreCase("COURANT")) {
            typeGroup.check(R.id.radioCourant);
        } else if (compte.getType().equalsIgnoreCase("EPARGNE")) {
            typeGroup.check(R.id.radioEpargne);
        }

        builder.setView(dialogView)
                .setTitle("Modifier un compte")
                .setPositiveButton("Modifier", (dialog, which) -> {
                    String solde = etSolde.getText().toString();
                    String type = typeGroup.getCheckedRadioButtonId() == R.id.radioCourant
                            ? "COURANT"
                            : "EPARGNE";
                    compte.setSolde(Double.parseDouble(solde));
                    compte.setType(type);
                    updateCompte(compte);
                })
                .setNegativeButton("Annuler", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Sends an update request to the backend API for the specified account.
     * Displays success or error messages based on the response.
     * Reloads the account list on successful update.
     * 
     * @param compte The Compte object with updated data
     */
    private void updateCompte(Compte compte) {
        CompteRepository compteRepository = new CompteRepository("JSON");
        compteRepository.updateCompte(compte.getId(), compte, new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                if (response.isSuccessful()) {
                    showToast("Compte modifié");
                    loadData("JSON");
                }
            }

            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                showToast("Erreur lors de la modification");
            }
        });
    }

    /**
     * Callback method triggered when the delete button is clicked on an account
     * item.
     * Shows a confirmation dialog before proceeding with deletion.
     * 
     * @param compte The Compte object to be deleted
     */
    @Override
    public void onDeleteClick(Compte compte) {
        showDeleteConfirmationDialog(compte);
    }

    /**
     * Displays a confirmation dialog before deleting an account.
     * Proceeds with deletion only if the user confirms.
     * 
     * @param compte The Compte object to be deleted
     */
    private void showDeleteConfirmationDialog(Compte compte) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Voulez-vous vraiment supprimer ce compte ?")
                .setPositiveButton("Oui", (dialog, which) -> deleteCompte(compte))
                .setNegativeButton("Non", null)
                .show();
    }

    /**
     * Sends a delete request to the backend API for the specified account.
     * Displays success or error messages based on the response.
     * Reloads the account list on successful deletion.
     * 
     * @param compte The Compte object to be deleted
     */
    private void deleteCompte(Compte compte) {
        CompteRepository compteRepository = new CompteRepository("JSON");
        compteRepository.deleteCompte(compte.getId(), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showToast("Compte supprimé");
                    loadData("JSON");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast("Erreur lors de la suppression");
            }
        });
    }

    /**
     * Displays a toast message to the user on the UI thread.
     * Ensures thread-safety by running on the main thread.
     * 
     * @param message The message text to display in the toast
     */
    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show());
    }
}