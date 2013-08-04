// CHECKSTYLE:OFF
package my.test;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.fuin.objects4j.Label;
import org.fuin.objects4j.TextField;
import org.fuin.objects4j.validation.PasswordStr;
import org.fuin.objects4j.validation.UserNameStr;

public class MyPasswordContainer {

    @NotNull
    @UserNameStr
    @Label("User name")
    @TextField
    private String user;

    @NotNull
    @PasswordStr
    @TextField
    private String password;

    @TextField
    @Min(0)
    @Max(200)
    private String comment;

}
// CHECKSTYLE:ON
