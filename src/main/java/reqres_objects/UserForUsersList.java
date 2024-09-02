package reqres_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserForUsersList {
    @Expose
    int id;
    @Expose
    String email;
    @Expose
    @SerializedName("first_name")
    String firstName;
    @Expose
    @SerializedName("last_name")
    String lastName;
    @Expose
    String avatar;
}