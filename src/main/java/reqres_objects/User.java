package reqres_objects;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    @Expose
    String name;
    @Expose
    String job;
    @Expose
    String id;
    @Expose
    String createdAt;
}