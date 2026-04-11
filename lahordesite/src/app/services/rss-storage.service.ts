import { Injectable } from '@angular/core';
import { Article } from '../models/rss/article.model';
import { RssStorage } from '../models/rss/rss-storage.model';

@Injectable({
	providedIn: 'root'
})
export class RssStorageService {
	private readonly key = 'rss_state';

	private defaultState(): RssStorage {
		return { topics: {} };
	}

	private read(): RssStorage {
		const raw = localStorage.getItem(this.key);

		if (!raw) {
			return this.defaultState();
		}

		try {
			const parsed = JSON.parse(raw);

			return {
				topics: parsed?.topics ?? {}
			};
		} catch {
			return this.defaultState();
		}
	}

	private write(data: RssStorage) {
		localStorage.setItem(this.key, JSON.stringify(data));
	}

	get(topicName: string): Article[] {
		const data = this.read();
		return data.topics?.[topicName] ?? [];
	}

	set(topicName: string, articles: Article[]) {
		const data = this.read();

		data.topics ??= {};
		data.topics[topicName] = articles;

		this.write(data);
	}
}
