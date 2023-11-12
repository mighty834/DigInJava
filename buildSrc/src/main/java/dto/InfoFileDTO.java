package dto;
import java.util.Date;
import java.util.List;

public class InfoFileDTO {
    public Date date;
    public List<String> keyWords;
    public String description;

    public InfoFileDTO() {}

    public InfoFileDTO(
        Date date,
        List<String> keyWords,
        String description
    ) {
        this.date = date;
        this.keyWords = keyWords;
        this.description = description;
    }
}
