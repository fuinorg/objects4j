/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.ui;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import my.test.MyClass;

/**
 * Usage examples.
 */
public class UsageExamples {

    /**
     * Main programm.
     * 
     * @param args
     *            Not used.
     * 
     * @throws Exception
     *             Something went wrong.
     */
    public static void main(String[] args) throws Exception {

        AnnotationAnalyzer analyzer = new AnnotationAnalyzer();

        // --- READ SINGLE FIELD ---

        Field field = MyClass.class.getDeclaredField("birthday");

        FieldTextInfo labelInfoGermany = analyzer.createFieldInfo(field, Locale.GERMANY, Label.class);
        System.out.println(labelInfoGermany.getText());
        // Prints "MyBundle_de birthday"

        FieldTextInfo labelInfoUs = analyzer.createFieldInfo(field, Locale.US, Label.class);
        System.out.println(labelInfoUs.getText());
        // Prints "MyBundle_en birthday"

        // --- READ ALL FIELDS ---
        List<FieldTextInfo> infos = analyzer.createFieldInfos(MyClass.class, Locale.US, Label.class);
        for (final FieldTextInfo info : infos) {
            // Text of the label or the name of the field if the text is null
            System.out.println(info.getTextOrField());
        }

        // --- CREATE A LIST OF TABLE COLUMNS ---
        List<TableColumnInfo> columns = TableColumnInfo.create(MyClass.class, Locale.US);
        for (TableColumnInfo column : columns) {
            System.out.println(column.getText());
        }

        // --- JavaFX usage ---
        TableView<MyClass> tableView = new TableView<>();
        List<TableColumnInfo> tableCols = TableColumnInfo.create(MyClass.class, Locale.getDefault());
        Collections.sort(tableCols);
        for (TableColumnInfo column : tableCols) {
            TableColumn<MyClass, String> tc = new TableColumn<>();
            tc.setStyle("-fx-alignment: CENTER;");
            javafx.scene.control.Label label = new javafx.scene.control.Label(column.getShortText());
            label.setTooltip(new Tooltip(column.getTooltip()));
            tc.setGraphic(label);
            tc.setCellValueFactory(new PropertyValueFactory<MyClass, String>(column.getField().getName()));
            tc.setPrefWidth(column.getWidth().getSize());
            tableView.getColumns().add(tc);
        }

    }

}
