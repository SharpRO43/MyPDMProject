package com.buspass.gui.app_gui.middle_panels;

import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;

public class MiddlePanel {
    public void setTableContents(JTable table, List<Map<String, Object>> results) {
        // Defensive handling for empty or null results
        if (results == null || results.isEmpty()) {
            table.setModel(new DefaultTableModel());
            return;
        }

        // Extract column names from first row (preserve order)
        Map<String, Object> firstRow = results.get(0);
        if (firstRow == null || firstRow.isEmpty()) {
            table.setModel(new DefaultTableModel());
            return;
        }
        String[] columns = firstRow.keySet().toArray(new String[0]);

        // Table model
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Fill rows using the column order
        for (Map<String, Object> row : results) {
            Object[] rowData = new Object[columns.length];
            for (int i = 0; i < columns.length; i++) {
                rowData[i] = row.get(columns[i]);
            }
            model.addRow(rowData);
        }

        table.setModel(model);
    }
}
