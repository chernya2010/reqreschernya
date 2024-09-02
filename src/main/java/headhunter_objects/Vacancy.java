package headhunter_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Vacancy {
    @Expose
    String name;
    @Expose
    @SerializedName("alternate_url")
    String alternativeUrl;
    @Expose
    Salary salary;
    String additionalInfo;
}
