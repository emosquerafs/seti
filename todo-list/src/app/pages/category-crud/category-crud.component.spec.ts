import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CategoryCrudComponent } from './category-crud.component';

describe('CategoryCrudComponent', () => {
  let component: CategoryCrudComponent;
  let fixture: ComponentFixture<CategoryCrudComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [CategoryCrudComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CategoryCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
