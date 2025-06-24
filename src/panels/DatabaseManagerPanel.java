package panels;

import model.DatabaseManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class DatabaseManagerPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton deleteButton, backButton;
    private JLabel statusLabel;
    private JScrollPane tableScrollPane;
    private boolean hasUnsavedChanges = false;
    private String userEmail;
    private String userStudID;
    private JComboBox<String> tableSelector;
    private JButton refreshButton, saveButton;

    public DatabaseManagerPanel(CardLayout cardLayout, JPanel mainPanel, String userEmail, String userStudID) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.userEmail = userEmail;
        this.userStudID = userStudID;
        initComponents();
        layoutComponents();
        loadTableNames();

        // Add component listener to refresh data when panel becomes visible
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                System.out.println("[DEBUG] DatabaseManagerPanel componentShown event fired");
                // Only refresh if there are no unsaved changes
                if (!hasUnsavedChanges) {
                    System.out.println("[DEBUG] No unsaved changes, refreshing data...");
                    refreshData();
                } else {
                    System.out.println("[DEBUG] Has unsaved changes, skipping refresh");
                }
            }
        });
    }

    private void initComponents() {
        searchField = new JTextField(10);
        searchField.setPreferredSize(new Dimension(100, 30));
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells editable except the first column (primary key) and Email column
                String colName = getColumnName(column);
                if (column == 0) return false;
                if (colName.equalsIgnoreCase("Email")) return false;
                return true;
            }
        };
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowSorter(new TableRowSorter<>(tableModel));
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        table.setRowHeight(24);
        tableScrollPane = new JScrollPane(table);

        // Add table model listener to detect changes
        tableModel.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                hasUnsavedChanges = true;
                statusLabel.setText("Unsaved changes detected. Click 'Save Changes' to persist.");
            }
        });

        // Add selection listener to update delete button state
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedCount = table.getSelectedRows().length;
                System.out.println("Selection changed: " + selectedCount + " rows selected");
                deleteButton.setEnabled(selectedCount > 0);
                deleteButton.setVisible(true); // Ensure button is visible
                if (selectedCount > 0) {
                    statusLabel.setText(selectedCount + " row(s) selected for deletion");
                    System.out.println("Delete button should be enabled and visible");
                } else {
                    statusLabel.setText("Ready");
                }
                // Force repaint
                deleteButton.repaint();
                deleteButton.getParent().repaint();
            }
        });

        // Create buttons with text labels
        deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete selected rows");
        deleteButton.setPreferredSize(new Dimension(80, 35));
        deleteButton.setBackground(new Color(220, 20, 60)); // Crimson
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorder(BorderFactory.createRaisedBevelBorder());
        deleteButton.setFocusPainted(false);
        deleteButton.setEnabled(false); // Start disabled until rows are selected
        backButton = new JButton("Back to Home");
        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Century Gothic", Font.PLAIN, 12));

        deleteButton.addActionListener(e -> {
            System.out.println("Delete button clicked!");
            System.out.println("Selected rows: " + table.getSelectedRows().length);
            deleteSelectedRows();
        });
        backButton.addActionListener(e -> {
            if (hasUnsavedChanges) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "You have unsaved changes. Do you want to save them before going back?",
                        "Unsaved Changes",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    saveChanges();
                } else if (choice == JOptionPane.CANCEL_OPTION) {
                    return; // Don't go back
                }
            }
            cardLayout.show(mainPanel, "Home");
        });
        searchField.addActionListener(e -> searchTable());

        tableSelector = new JComboBox<>();
        refreshButton = new JButton("Refresh");
        refreshButton.setToolTipText("Refresh data");
        refreshButton.setPreferredSize(new Dimension(80, 35));
        refreshButton.setBackground(new Color(70, 130, 180)); // Steel blue
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBorder(BorderFactory.createRaisedBevelBorder());
        refreshButton.setFocusPainted(false);
        saveButton = new JButton("Save");
        saveButton.setToolTipText("Save changes");
        saveButton.setPreferredSize(new Dimension(80, 35));
        saveButton.setBackground(new Color(34, 139, 34)); // Forest green
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createRaisedBevelBorder());
        saveButton.setFocusPainted(false);

        tableSelector.addActionListener(e -> {
            if (hasUnsavedChanges) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "You have unsaved changes. Do you want to save them before switching tables?",
                        "Unsaved Changes",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    saveChanges();
                } else if (choice == JOptionPane.CANCEL_OPTION) {
                    return; // Don't switch tables
                }
            }
            loadTableData();
        });
        refreshButton.addActionListener(e -> {
            if (hasUnsavedChanges) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "You have unsaved changes. Do you want to save them before refreshing?",
                        "Unsaved Changes",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    saveChanges();
                } else if (choice == JOptionPane.CANCEL_OPTION) {
                    return; // Don't refresh
                }
            }
            loadTableData();
        });
        saveButton.addActionListener(e -> saveChanges());
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Top control panel with better layout
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel leftControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftControls.add(new JLabel("Table:"));
        leftControls.add(tableSelector);
        leftControls.add(Box.createHorizontalStrut(20));
        leftControls.add(new JLabel("Search:"));
        leftControls.add(searchField);
        leftControls.add(refreshButton);
        topPanel.add(leftControls, BorderLayout.WEST);
        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightControls.setBackground(new Color(240, 240, 240));
        saveButton.setPreferredSize(new Dimension(120, 30));
        deleteButton.setPreferredSize(new Dimension(120, 30));
        rightControls.add(saveButton);
        rightControls.add(deleteButton);
        topPanel.add(rightControls, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        add(tableScrollPane, BorderLayout.CENTER);

        // Bottom panel with status and back button
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(250, 250, 250));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(backButton, BorderLayout.EAST);

        add(statusPanel, BorderLayout.SOUTH);
    }

    private void loadTableNames() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(conn.getCatalog(), null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableSelector.addItem(tableName);
            }
            tables.close();
            if (tableSelector.getItemCount() > 0) {
                tableSelector.setSelectedIndex(0);
                loadTableData();
            }
        } catch (SQLException e) {
            statusLabel.setText("Error loading tables: " + e.getMessage());
        }
    }

    private void loadTableData() {
        String tableName = (String) tableSelector.getSelectedItem();
        if (tableName == null) return;

        // Dynamically retrieve the current userStudID from database
        String currentUserStudID = DatabaseManager.getInstance().getStudIDByEmail(userEmail);

        System.out.println("[DEBUG] loadTableData() called for table: " + tableName);
        System.out.println("[DEBUG] userEmail: " + userEmail + ", currentUserStudID: " + currentUserStudID);

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            Vector<String> colNames = new Vector<>();
            // List of possible email column names
            String[] emailColumns = {"Email", "StudEmail", "ParentEmail", "ApplicantEmail"};
            java.util.List<Integer> emailColIndices = new java.util.ArrayList<>();
            int studIdColIndex = -1;
            for (int i = 1; i <= colCount; i++) {
                String colName = meta.getColumnName(i);
                colNames.add(colName);
                for (String possible : emailColumns) {
                    if (colName.equalsIgnoreCase(possible)) {
                        emailColIndices.add(i - 1);
                    }
                }
                if (colName.equalsIgnoreCase("StudID")) {
                    studIdColIndex = i - 1;
                }
            }
            System.out.println("[DEBUG] Email columns for filtering: " + emailColIndices.stream().map(colNames::get).toList());
            System.out.println("[DEBUG] StudID column index: " + studIdColIndex);
            Vector<Vector<Object>> data = new Vector<>();
            int matchEmailCount = 0;
            int matchStudIdCount = 0;
            int totalRows = 0;
            while (rs.next()) {
                totalRows++;
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= colCount; i++) {
                    row.add(rs.getObject(i));
                }
                // If any email column matches userEmail, or StudID matches currentUserStudID, add the row
                boolean matchesEmail = false;
                for (int idx : emailColIndices) {
                    Object val = row.get(idx);
                    if (val != null && val.toString().equalsIgnoreCase(userEmail)) {
                        matchesEmail = true;
                        break;
                    }
                }
                boolean matchesStudId = false;
                if (studIdColIndex != -1 && currentUserStudID != null) {
                    Object val = row.get(studIdColIndex);
                    if (val != null && val.toString().equalsIgnoreCase(currentUserStudID)) {
                        matchesStudId = true;
                    }
                }
                if (emailColIndices.isEmpty() && studIdColIndex == -1) {
                    data.add(row); // No filtering columns, show all
                } else if (matchesEmail || matchesStudId) {
                    data.add(row);
                    if (matchesEmail) matchEmailCount++;
                    if (matchesStudId) matchStudIdCount++;
                }
            }
            System.out.println("[DEBUG] Total rows in table: " + totalRows);
            System.out.println("[DEBUG] Rows matching userEmail: " + matchEmailCount + ", matching currentUserStudID: " + matchStudIdCount + ", total displayed: " + data.size());
            tableModel.setDataVector(data, colNames);
            hasUnsavedChanges = false;
            statusLabel.setText("Loaded table: " + tableName + " (" + data.size() + " rows)");
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("[DEBUG] SQL Error in loadTableData: " + e.getMessage());
            statusLabel.setText("Error loading data: " + e.getMessage());
        }
    }

    private void saveChanges() {
        String tableName = (String) tableSelector.getSelectedItem();
        if (tableName == null) return;
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            conn.setAutoCommit(false);
            int updated = 0;
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                // Build update statement for each row (skipping new/empty rows)
                Vector<Object> rowData = ((Vector<Object>) tableModel.getDataVector().get(row));
                StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
                Vector<Object> params = new Vector<>();
                for (int col = 1; col < tableModel.getColumnCount(); col++) {
                    sql.append(tableModel.getColumnName(col)).append(" = ?");
                    if (col < tableModel.getColumnCount() - 1) sql.append(", ");
                    params.add(rowData.get(col));
                }
                sql.append(" WHERE ").append(tableModel.getColumnName(0)).append(" = ?");
                params.add(rowData.get(0));
                PreparedStatement pstmt = conn.prepareStatement(sql.toString());
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(i + 1, params.get(i));
                }
                updated += pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
            hasUnsavedChanges = false;
            statusLabel.setText("Saved " + updated + " changes to " + tableName);
            JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Save Complete", JOptionPane.INFORMATION_MESSAGE);

            // Refresh the data to show the updated state
            loadTableData();
        } catch (SQLException e) {
            statusLabel.setText("Error saving changes: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error saving changes: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedRows() {
        String tableName = (String) tableSelector.getSelectedItem();
        if (tableName == null) return;

        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select rows to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Show confirmation dialog with more details
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + selectedRows.length + " row(s) from table '" + tableName + "'?\n\n" +
                        "This action cannot be undone!",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            conn.setAutoCommit(false);

            int deleted = 0;
            int failed = 0;
            StringBuilder errorMessages = new StringBuilder();

            // First, let's try a simple test to see if we can delete at all
            try {
                // Test if we can even execute a DELETE statement
                String testSql = "SELECT COUNT(*) FROM " + tableName;
                PreparedStatement testStmt = conn.prepareStatement(testSql);
                testStmt.executeQuery();
                testStmt.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Cannot access table '" + tableName + "': " + e.getMessage(),
                        "Access Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Delete from highest index to lowest to avoid shifting issues
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                try {
                    int modelRow = table.convertRowIndexToModel(selectedRows[i]);
                    Object pkValue = tableModel.getValueAt(modelRow, 0);

                    if (pkValue == null) {
                        failed++;
                        errorMessages.append("Row ").append(modelRow + 1).append(": Primary key is null\n");
                        continue;
                    }

                    // Build the DELETE statement
                    String sql = "DELETE FROM " + tableName + " WHERE " + tableModel.getColumnName(0) + " = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setObject(1, pkValue);

                    // Log what we're trying to delete
                    System.out.println("Attempting to delete: " + sql + " with value: " + pkValue);

                    int result = pstmt.executeUpdate();
                    System.out.println("Delete result: " + result + " rows affected");

                    if (result > 0) {
                        deleted++;
                        tableModel.removeRow(modelRow);
                        System.out.println("Successfully deleted row with PK: " + pkValue);
                    } else {
                        failed++;
                        errorMessages.append("Row ").append(modelRow + 1).append(": No rows affected (PK: ").append(pkValue).append(")\n");
                        System.out.println("No rows affected for PK: " + pkValue);
                    }
                    pstmt.close();

                } catch (SQLException e) {
                    failed++;
                    String errorMsg = e.getMessage();
                    errorMessages.append("Row ").append(selectedRows[i] + 1).append(": ").append(errorMsg).append("\n");
                    System.out.println("SQL Error deleting row: " + errorMsg);

                    // Check for specific constraint violations
                    if (errorMsg.contains("foreign key constraint")) {
                        errorMessages.append("  → This row is referenced by other tables\n");
                    } else if (errorMsg.contains("cannot delete")) {
                        errorMessages.append("  → Database prevents deletion of this row\n");
                    }
                }
            }

            if (deleted > 0) {
                conn.commit();
                statusLabel.setText("Successfully deleted " + deleted + " row(s) from " + tableName);

                if (failed > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Deleted " + deleted + " row(s) successfully.\n" +
                                    "Failed to delete " + failed + " row(s).\n\n" +
                                    "Errors:\n" + errorMessages.toString(),
                            "Partial Delete Complete",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Successfully deleted " + deleted + " row(s) from " + tableName,
                            "Delete Complete",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                conn.rollback();
                statusLabel.setText("Failed to delete any rows from " + tableName);
                JOptionPane.showMessageDialog(this,
                        "Failed to delete any rows.\n\n" +
                                "Possible reasons:\n" +
                                "• Foreign key constraints (rows referenced by other tables)\n" +
                                "• Database permissions\n" +
                                "• Primary key values don't exist\n\n" +
                                "Errors:\n" + errorMessages.toString(),
                        "Delete Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            statusLabel.setText("Error deleting rows: " + e.getMessage());
            System.out.println("Database error: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Database error while deleting rows:\n" + e.getMessage() + "\n\n" +
                            "This might be due to:\n" +
                            "• Foreign key constraints\n" +
                            "• Database permissions\n" +
                            "• Network connectivity issues",
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchTable() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadTableData();
            return;
        }
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        statusLabel.setText("Filtered by: '" + searchText + "'");
    }

    /**
     * Public method to refresh data from database
     * This ensures that the latest data is always displayed
     */
    public void refreshData() {
        System.out.println("[DEBUG] refreshData() method called");
        if (tableSelector.getItemCount() > 0) {
            System.out.println("[DEBUG] Table selector has items, calling loadTableData()");
            loadTableData();
            System.out.println("[DEBUG] Data refreshed for table: " + tableSelector.getSelectedItem());
        } else {
            System.out.println("[DEBUG] Table selector has no items, skipping refresh");
        }
    }
} 
