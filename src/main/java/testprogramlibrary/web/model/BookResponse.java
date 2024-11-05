package testprogramlibrary.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private Long id;

    private String title;

    private Date publishedDate;
}
