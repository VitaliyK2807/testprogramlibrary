package testprogramlibrary.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertBookRequest {

    @NotNull(message = "Author ID must be specified")
    @Positive(message = "Author ID must be greater than 0")
    private Long authorId;

    @NotBlank(message = "Book title must be filled in!")
    private String title;

    private Date publishedDate;


}
