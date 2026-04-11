import { Component } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
	selector: 'app-generic-dialog',
	imports: [MatDialogModule],
	standalone: true,
	templateUrl: './generic-dialog.dialog.html',
	styleUrl: './generic-dialog.dialog.css'
})
export class GenericDialog {}
