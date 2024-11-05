package testprogramlibrary.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertAuthorRequest {

    @NotBlank(message = "Author name must be filled in!")
    @Size(min = 3, max = 50, message = "Author name cannot be less than {min} or greater than {max}!")
    private String name;
}
