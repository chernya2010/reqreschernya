package reqres_objects;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class SingleUser {
    @Expose
    UserForUsersList data;
    @Expose
    Support support;
}