package yonasazela.lahordeapi.rss.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity representing a topic for RSS feeds.
 */
@Entity
@Table(name = "topic", uniqueConstraints = {@UniqueConstraint(name = "Name", columnNames = "Name")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicEntity {

	/** Unique identifier of the topic. Mapped to column "ID". */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "INT")
	private int id;

	/** Name of the topic. Mapped to column "Name". must be unique. */
	@Column(name = "Name", nullable = false, columnDefinition = "VARCHAR(255) COLLATE latin1_bin")
	private String name;

	/** List of feeds associated with this topic. */
	@OneToMany(mappedBy = "topic")
	private List<FeedEntity> feeds;
}
