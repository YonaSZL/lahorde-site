package yonasazela.lahordeapi.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

	private int id;

	@NotBlank
	@Size(max = 255)
	private String name;

	@NotNull
	@Digits(integer = 6, fraction = 3)
	@PositiveOrZero
	@Builder.Default
	private BigDecimal price = BigDecimal.ZERO;

	@NotNull
	@Digits(integer = 2, fraction = 2)
	@PositiveOrZero
	@Builder.Default
	private BigDecimal weight = BigDecimal.ZERO;

	@Positive
	@Builder.Default
	private int size = 1;

	// champ calculé par la DB (read-only)
	private BigDecimal priceForOneSlot;

	@NotBlank
	@Size(max = 2500)
	private String description;
}
