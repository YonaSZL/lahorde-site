package yonasazela.lahordeapi.rss.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing an RSS feed.
 */
@Entity
@Table(name = "feed", uniqueConstraints = {
		@UniqueConstraint(name = "Name", columnNames = "Name"),
		@UniqueConstraint(name = "Feed", columnNames = "Feed")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedEntity {

	/** Unique identifier of the feed. Mapped to column "ID". */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", columnDefinition = "INT")
	private int id;

	/** Name of the feed. Mapped to column "Name". */
	@Column(name = "Name", nullable = false, columnDefinition = "VARCHAR(255) COLLATE latin1_bin")
	private String name;

	/** RSS URL of the feed. Mapped to column "Feed". */
	@Column(name = "Feed", nullable = false, columnDefinition = "VARCHAR(255) COLLATE latin1_bin")
	private String feed;

	/** Topic this feed belongs to. */
	@ManyToOne
	@JoinColumn(name = "topic_id", nullable = false)
	private TopicEntity topic;
}
