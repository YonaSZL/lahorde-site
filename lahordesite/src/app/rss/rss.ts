import { ChangeDetectorRef, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { GenericDialog } from '../generic/generic-dialog/generic-dialog.dialog';

import { RssService } from '../services/rss.service';
import { RssStorageService } from '../services/rss-storage.service';

import { Topic } from '../models/rss/topic.model';
import { Article } from '../models/rss/article.model';

type ViewMode = 'topics' | 'articles' | 'article';

@Component({
	selector: 'app-rss',
	standalone: true,
	imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule, GenericDialog],
	templateUrl: './rss.html',
	styleUrl: './rss.css'
})
export class Rss {
	view: ViewMode = 'topics';

	topics: Topic[] = [];
	articles: Article[] = [];

	selectedTopic?: Topic;
	selectedArticle?: Article;

	constructor(
		private readonly rssService: RssService,
		private readonly storage: RssStorageService,
		private readonly cdr: ChangeDetectorRef
	) {
		this.init();
	}

	private init() {
		this.rssService.getAllTopics().subscribe({
			next: (topics) => {
				this.topics = topics;
				this.cdr.detectChanges();
			}
		});
	}

	selectTopic(topic: Topic) {
		this.selectedTopic = topic;
		this.view = 'articles';

		const cached = this.storage.get(topic.name);
		if (cached.length) {
			this.articles = cached;
		}

		this.rssService.getArticlesByTopic(topic.name).subscribe({
			next: (articles) => {
				console.log('ARTICLES OK:', articles);
				this.articles = articles;
				this.cdr.detectChanges();
			},
			error: (err) => {
				console.error('HTTP ERROR:', err);
			}
		});
	}

	backToTopics() {
		this.selectedTopic = undefined;
		this.view = 'topics';
	}

	selectArticle(article: Article) {
		this.selectedArticle = article;
		this.view = 'article';
	}

	backToArticles() {
		this.selectedArticle = undefined;
		this.view = 'articles';
	}

	stripHtml(html: string): string {
		return html.replace(/<[^>]*>/g, '');
	}
}
