import { Article } from './article.model';

export interface RssStorage {
	topics: Record<string, Article[]>;
}
