import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Rss } from './rss';

describe('Rss', () => {
	let component: Rss;
	let fixture: ComponentFixture<Rss>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [Rss]
		}).compileComponents();

		fixture = TestBed.createComponent(Rss);
		component = fixture.componentInstance;
		await fixture.whenStable();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
