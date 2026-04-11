import { Component, inject } from '@angular/core';
import { MatCard, MatCardImage } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { Kenshi } from '../kenshi/kenshi';
import { DragonBall } from '../dragon-ball/dragon-ball';
import { AboutMe } from '../about-me/about-me';
import { DIALOG_CONFIG, PROTON_MAIL, TRANSLATION_URL } from '../constants';
import { Rss } from '../rss/rss';

@Component({
	selector: 'app-homepage',
	imports: [MatCard, MatCardImage],
	templateUrl: './homepage.html',
	styleUrl: './homepage.css'
})
export class Homepage {
	private readonly dialog = inject(MatDialog);

	openKenshi() {
		this.dialog.open(Kenshi, DIALOG_CONFIG);
	}

	openDbxv2() {
		this.dialog.open(DragonBall, DIALOG_CONFIG);
	}

	openRss() {
		this.dialog.open(Rss, DIALOG_CONFIG);
	}

	openGithub() {
		this.dialog.open(AboutMe, DIALOG_CONFIG);
	}

	openTranslation() {
		globalThis.open(TRANSLATION_URL, '_blank', 'noopener');
	}

	sendEmail() {
		globalThis.location.href = `mailto:${PROTON_MAIL}`;
	}
}
