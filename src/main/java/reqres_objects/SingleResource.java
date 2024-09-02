package reqres_objects;

import com.google.gson.annotations.Expose;
import lombok.Data;
@Data
public class SingleResource {
    @Expose
    ResourceForResourcesList data;
    @Expose
    Support support;
}