package yonasazela.lahordeapi.kenshi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity representing an item in the database. Mapped to the "items" table.
 */
@Entity
@Table(name = "item", uniqueConstraints = {@UniqueConstraint(name = "Name", columnNames = "Name")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEntity {

	/** Unique identifier of the item. Mapped to column "ID". */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "INT")
	private int id;

	/** Name of the item. Mapped to column "Name". must be unique. */
	@Column(name = "Name", nullable = false, columnDefinition = "VARCHAR(255) COLLATE latin1_bin")
	private String name;

	/** Price of the item. Mapped to column "Price". */
	@Column(name = "Price", nullable = false, precision = 9, scale = 3, columnDefinition = "DECIMAL(9,3) UNSIGNED DEFAULT 0.000")
	@Builder.Default
	private BigDecimal price = BigDecimal.ZERO;

	/** Weight of the item. Mapped to column "Weight". */
	@Column(name = "Weight", nullable = false, precision = 4, scale = 2, columnDefinition = "DECIMAL(4,2) UNSIGNED DEFAULT 0.00")
	@Builder.Default
	private BigDecimal weight = BigDecimal.ZERO;

	/** Size of the item. Mapped to column "Size". */
	@Column(name = "Size", nullable = false, columnDefinition = "SMALLINT UNSIGNED DEFAULT 1")
	@Builder.Default
	private int size = 1;

	/** Column calculated by the database representing the price for one slot. */
	@Column(name = "PriceForOneSlot", insertable = false, updatable = false, columnDefinition = "DECIMAL(6,3) GENERATED ALWAYS AS (ROUND(Price / NULLIF(Size,0),3)) STORED")
	private BigDecimal priceForOneSlot;

	/** Detailed description of the item. Mapped to column "Description". */
	@Column(name = "Description", nullable = false, length = 2500, columnDefinition = "VARCHAR(2500) CHARACTER SET latin1 COLLATE latin1_bin")
	private String description;

}
