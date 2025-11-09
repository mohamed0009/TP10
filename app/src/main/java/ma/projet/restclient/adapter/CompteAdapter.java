package ma.projet.restclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ma.projet.restclient.R;
import ma.projet.restclient.entities.Compte;

import java.util.ArrayList;
import java.util.List;

/**
 * CompteAdapter - RecyclerView adapter for displaying bank account data.
 * 
 * <p>
 * This adapter manages the display of Compte objects in a RecyclerView,
 * handling item binding, click events for update and delete operations,
 * and data updates with automatic UI refresh.
 * </p>
 * 
 * <p>
 * Features:
 * </p>
 * <ul>
 * <li>Efficient ViewHolder pattern implementation</li>
 * <li>Click listener interfaces for update and delete operations</li>
 * <li>Dynamic data updates with notifyDataSetChanged()</li>
 * <li>Null-safe data binding</li>
 * </ul>
 * 
 * @author Mohamed
 * @version 1.0
 * @since 2025-11-09
 */
public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.CompteViewHolder> {

    /**
     * Interface for handling delete button click events on account items.
     */
    /**
     * Interface for handling delete button click events on account items.
     */
    public interface OnDeleteClickListener {
        /**
         * Called when the delete button is clicked for an account.
         * 
         * @param compte The account to be deleted
         */
        void onDeleteClick(Compte compte);
    }

    /**
     * Interface for handling update button click events on account items.
     */
    public interface OnUpdateClickListener {
        /**
         * Called when the update button is clicked for an account.
         * 
         * @param compte The account to be updated
         */
        void onUpdateClick(Compte compte);
    }

    /** List of accounts to be displayed */
    private List<Compte> comptes;

    /** Listener for delete click events */
    private OnDeleteClickListener onDeleteClickListener;

    /** Listener for update click events */
    private OnUpdateClickListener onUpdateClickListener;

    /**
     * Constructs a new CompteAdapter with the specified listeners.
     * 
     * @param onDeleteClickListener Callback for delete operations
     * @param onUpdateClickListener Callback for update operations
     */
    public CompteAdapter(OnDeleteClickListener onDeleteClickListener, OnUpdateClickListener onUpdateClickListener) {
        this.comptes = new ArrayList<>();
        this.onDeleteClickListener = onDeleteClickListener;
        this.onUpdateClickListener = onUpdateClickListener;
    }

    /**
     * Creates a new ViewHolder when needed by the RecyclerView.
     * Inflates the item layout and wraps it in a ViewHolder.
     * 
     * @param parent   The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View
     * @return A new CompteViewHolder instance
     */
    @NonNull
    @Override
    public CompteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_compte, parent, false);
        return new CompteViewHolder(view);
    }

    /**
     * Binds data to a ViewHolder at the specified position.
     * Called by RecyclerView to display data at a specific position.
     * 
     * @param holder   The ViewHolder to bind data to
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull CompteViewHolder holder, int position) {
        Compte compte = comptes.get(position);
        holder.bind(compte);
    }

    /**
     * Returns the total number of items in the data set.
     * 
     * @return The number of accounts in the list
     */
    @Override
    public int getItemCount() {
        return comptes.size();
    }

    /**
     * Updates the adapter's data set with new account data.
     * Clears existing data and adds new data, then notifies the RecyclerView of
     * changes.
     * 
     * @param newComptes The new list of accounts to display
     */
    public void updateData(List<Compte> newComptes) {
        this.comptes.clear();
        this.comptes.addAll(newComptes);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for account items.
     * Holds references to all views within an item layout and handles data binding.
     */
    /**
     * ViewHolder class for account items.
     * Holds references to all views within an item layout and handles data binding.
     */
    class CompteViewHolder extends RecyclerView.ViewHolder {
        /** TextView for displaying account ID */
        private TextView tvId, tvSolde, tvType, tvDate;

        /** Buttons for delete and update operations */
        private View btnDelete, btnUpdate;

        /**
         * Constructs a ViewHolder and initializes view references.
         * 
         * @param itemView The root view of the item layout
         */
        public CompteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvSolde = itemView.findViewById(R.id.tvSolde);
            tvType = itemView.findViewById(R.id.tvType);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnEdit);
        }

        /**
         * Binds account data to the view components.
         * Handles null checks and formats data for display.
         * Sets up click listeners for update and delete buttons.
         * 
         * @param compte The Compte object to display
         */
        public void bind(Compte compte) {
            if (compte == null)
                return;

            tvId.setText("ID: " + (compte.getId() != null ? compte.getId() : "N/A"));
            tvSolde.setText(String.format("Solde: %.2f", compte.getSolde()));
            tvType.setText("Type: " + (compte.getType() != null ? compte.getType() : "N/A"));
            tvDate.setText("Date: " + (compte.getDateCreation() != null ? compte.getDateCreation() : "N/A"));

            btnDelete.setOnClickListener(v -> {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(compte);
                }
            });
            btnUpdate.setOnClickListener(v -> {
                if (onUpdateClickListener != null) {
                    onUpdateClickListener.onUpdateClick(compte);
                }
            });
        }
    }
}