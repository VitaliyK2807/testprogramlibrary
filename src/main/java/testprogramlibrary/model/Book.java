package testprogramlibrary.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Book {

    private Long id;
    private String title;
    private Author author;
    private Date publishedDate;

}
