import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Topic } from '../models/rss/topic.model';
import { Observable } from 'rxjs';
import { Article } from '../models/rss/article.model';

@Injectable({
	providedIn: 'root'
})
export class RssService {
	private readonly baseUrl = '/topics';

	constructor(private readonly http: HttpClient) {}

	getAllTopics(): Observable<Topic[]> {
		return this.http.get<Topic[]>(`${this.baseUrl}`);
	}

	getArticlesByTopic(topicName: string): Observable<Article[]> {
		return this.http.get<Article[]>(`${this.baseUrl}/${topicName}/articles`);
	}
}
