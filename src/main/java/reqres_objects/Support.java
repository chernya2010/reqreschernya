package reqres_objects;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Support {
    @Expose
    String url;
    @Expose
    String text;
}
