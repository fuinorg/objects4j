# objects4j-ui
Annotations that can be placed on plain objects but may be used by a user interface to render that object in some way.

* [Label annotation](#label-annotation)
* [Prompt annotation](#prompt-annotation)
* [ShortLabel annotation](#shortlabel-annotation)
* [TableColumn annotation](#tablecolumn-annotation)
* [TextField annotation](#textfield-annotation)
* [Tooltip annotation](#tooltip-annotation)

## Description
The annotations may be used by UI elements to display an appropriate text for a type or a field.
You can also configure a bundle and key for internationalization.

### Label annotation
Use this annotation to assign a label to a class or an attribute.
```Java
@Label(value = "Birthday", bundle = "my/MyBundle", key = "birthday.label")
private Date birthday;
```

### Prompt annotation
Use this annotation to assign a prompt (example value) to a class or an attribute.
```Java
@Prompt(value = "12-31-1993", bundle = "my/MyBundle", key = "birthday.prompt")
private Date birthday;
```

### ShortLabel annotation
Use this annotation to assign an abbreviation to a class or an attribute.
```Java
@ShortLabel(value = "BD", bundle = "my/MyBundle", key = "birthday.short")
private Date birthday;
```

### TableColumn annotation
Use this annotation to assign preferred table column values to a field.
```Java
@TableColumn(pos = 3, width = 20, unit= FontSizeUnit.EM, getter = "isInternal")
private boolean permanentEmployee;
```

### TextField annotation
Use this annotation to express the annotated attribute should be rendered as a text field. 
User agents are expected to use this information as hint for the rendering process.
```Java
@TextField(width = 50)
private String lastName;
```

### Tooltip annotation
Use this annotation to assign a tooltip to a class or a field.
```Java
@Tooltip(value = "The person's birthday", bundle = "my/MyBundle", key = "birthday.tooltip")
private Date birthday;
```

### AnnotationAnalyzer
You can use [AnnotationAnalyzer](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/ui/AnnotationAnalyzer.java) to read the internationalized text from above annotations. 

Read a single ```@Label``` text:
```Java
AnnotationAnalyzer analyzer = new AnnotationAnalyzer();

Field field = MyClass.class.getDeclaredField("birthday");

FieldTextInfo labelInfoGermany = analyzer.createFieldInfo(field, Locale.GERMANY, Label.class);        
System.out.println(labelInfoGermany.getText());
// Prints "Geburtstag"

FieldTextInfo labelInfoUs = analyzer.createFieldInfo(field, Locale.US, Label.class);        
System.out.println(labelInfoUs.getText());
// Prints "Birthday"
```

Read all ```@Label``` texts from a class:
```Java
AnnotationAnalyzer analyzer = new AnnotationAnalyzer();
List<FieldTextInfo> infos = analyzer.createFieldInfos(MyClass.class, Locale.US, Label.class);
for (final FieldTextInfo info : infos) {
	// Text of the label or the name of the field if the text is null
	System.out.println(info.getTextOrField());
}
```

Create a list of [TableColumnInfo](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/ui/TableColumnInfo.java) 
that combines [@Label](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/ui/Label.java), 
[@ShortLabel](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/ui/ShortLabel.java) and 
[@TableColumn](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/ui/TableColumn.java) annotations. 
```Java
List<TableColumnInfo> columns = TableColumnInfo.create(MyClass.class, Locale.US);
for (TableColumnInfo column : columns) {
    System.out.println(column.getText());
}
```

This allows easy creation of a table model for example for JavaFX:
```Java
TableView<MyClass> tableView = new TableView<>();
List<TableColumnInfo> tableCols = TableColumnInfo.create(MyClass.class, Locale.getDefault());
Collections.sort(tableCols);
for (TableColumnInfo column : tableCols) {
    TableColumn<MyClass, String> tc = new TableColumn<>();
    tc.setStyle("-fx-alignment: CENTER;");
    Label label = new Label(column.getShortText());
    label.setTooltip(new Tooltip(column.getTooltip()));
    tc.setGraphic(label);
    tc.setCellValueFactory(new PropertyValueFactory<MyClass, String>(column.getField().getName()));
    tc.setPrefWidth(column.getWidth().getSize());
    tableView.getColumns().add(tc);
}
```

### Examples annotation
Use this annotation to assign some example values to a class or an attribute.
```Java
@Examples({"John", "Jane"})
private String firstName;
```

### Mappings annotation
Use this annotation to assign key/value mappings to a class or an attribute.
```Java
@Mappings({"1=One", "2=Two", "3=Three"})
private String code;
```
