package yonasazela.lahordeapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Entity
@Table(name = "items", uniqueConstraints = {@UniqueConstraint(name = "Name", columnNames = "Name")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "SMALLINT")
	private int id;

	@Column(name = "Name", nullable = false, columnDefinition = "VARCHAR(255) COLLATE latin1_bin")
	private String name;

	@Column(name = "Price", nullable = false, precision = 9, scale = 3, columnDefinition = "DECIMAL(9,3) UNSIGNED DEFAULT 0.000")
	@Builder.Default
	private BigDecimal price = BigDecimal.ZERO;

	@Column(name = "Weight", nullable = false, precision = 4, scale = 2, columnDefinition = "DECIMAL(4,2) UNSIGNED DEFAULT 0.00")
	@Builder.Default
	private BigDecimal weight = BigDecimal.ZERO;

	@Column(name = "Size", nullable = false, columnDefinition = "SMALLINT UNSIGNED DEFAULT 1")
	@Builder.Default
	private int size = 1;

	// colonne calculée par MySQL
	@Column(name = "PriceForOneSlot", insertable = false, updatable = false, columnDefinition = "DECIMAL(6,3) GENERATED ALWAYS AS (ROUND(Price / NULLIF(Size,0),3)) STORED")
	private BigDecimal priceForOneSlot;

	@Column(name = "Description", nullable = false, length = 2500, columnDefinition = "VARCHAR(2500) CHARACTER SET latin1 COLLATE latin1_bin")
	private String description;
}
