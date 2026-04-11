import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Kenshi } from './kenshi';

describe('Kenshi', () => {
	let component: Kenshi;
	let fixture: ComponentFixture<Kenshi>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [Kenshi]
		}).compileComponents();

		fixture = TestBed.createComponent(Kenshi);
		component = fixture.componentInstance;
		await fixture.whenStable();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
