import { Component } from '@angular/core';
import { GenericDialog } from '../generic/generic-dialog/generic-dialog.dialog';
import { MatIconModule } from '@angular/material/icon';
import { GITHUB_URL, ITCHIO_URL } from '../constants';

@Component({
	selector: 'app-about-me',
	imports: [GenericDialog, MatIconModule],
	templateUrl: './about-me.html',
	styleUrl: './about-me.css'
})
export class AboutMe {
	protected readonly GITHUB_URL = GITHUB_URL;
	protected readonly ITCHIO_URL = ITCHIO_URL;
}
