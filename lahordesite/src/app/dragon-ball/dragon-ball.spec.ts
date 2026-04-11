import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DragonBall } from './dragon-ball';

describe('DragonBall', () => {
	let component: DragonBall;
	let fixture: ComponentFixture<DragonBall>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [DragonBall]
		}).compileComponents();

		fixture = TestBed.createComponent(DragonBall);
		component = fixture.componentInstance;
		await fixture.whenStable();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
