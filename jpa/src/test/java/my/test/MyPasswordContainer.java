// CHECKSTYLE:OFF
package my.test;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.TextField;

public class MyPasswordContainer {

    @NotNull
    @Label("User name")
    @TextField
    private String user;

    @NotNull
    @TextField
    private String password;

    @TextField
    @Min(0)
    @Max(200)
    private String comment;

}
// CHECKSTYLE:ON
