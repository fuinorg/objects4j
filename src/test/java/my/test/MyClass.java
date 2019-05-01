// CHECKSTYLE:OFF
package my.test;

import java.util.Date;

import org.fuin.objects4j.ui.Examples;
import org.fuin.objects4j.ui.FontSizeUnit;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.Mappings;
import org.fuin.objects4j.ui.ShortLabel;
import org.fuin.objects4j.ui.TableColumn;
import org.fuin.objects4j.ui.TextField;

/**
 * Test class with fields annotated with label and table column.
 */
@ShortLabel("DEFAULT shortLabel")
@Label(value = "DEFAULT label", key = "MyClass.Label")
public class MyClass {

    private int id;

    @Label("First name")
    @TableColumn(pos = 0, width = 80)
    private String firstName;

    @Label("Last name")
    @TextField(width = 50)
    @TableColumn(pos = 1, width = 100, unit = FontSizeUnit.POINT)
    private String lastName;

    @ShortLabel(value = "BIRTH", bundle = "my/MyBundle", key = "birthday.short")
    @Label(value = "BIRTHDAY", bundle = "my/MyBundle", key = "birthday")
    @TableColumn(pos = 2, width = 40)
    private Date birthday;

    @Label(value = "Permanent employee")
    @TableColumn(pos = 3, width = 20, getter = "isPermanentEmployee")
    private boolean permanentEmployee;

    @Examples(value = { "3e626f61-bf48-49ee-b174-16634eb0e05b", "87cf0401-d83f-4021-91c5-7b490026bb24"})
    private String uuid;
    
    @Mappings({"0=Zero", "1=One", "2=Two"})
    private String code;

}
// CHECKSTYLE:ON
