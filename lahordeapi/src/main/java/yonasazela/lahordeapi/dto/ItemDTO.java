package yonasazela.lahordeapi.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Item.
 * Used for transferring item data between different layers of the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

	/** Unique identifier of the item. */
	private int id;

	/** Name of the item. */
	@NotBlank
	@Size(max = 255)
	private String name;

	/** Price of the item. */
	@NotNull
	@Digits(integer = 6, fraction = 3)
	@PositiveOrZero
	@Builder.Default
	private BigDecimal price = BigDecimal.ZERO;

	/** Weight of the item. */
	@NotNull
	@Digits(integer = 2, fraction = 2)
	@PositiveOrZero
	@Builder.Default
	private BigDecimal weight = BigDecimal.ZERO;

	/** Size of the item. */
	@Positive
	@Builder.Default
	private int size = 1;

	/** Calculated price per slot (read-only). */
	private BigDecimal priceForOneSlot;

	/** Detailed description of the item. */
	@NotBlank
	@Size(max = 2500)
	private String description;
}
