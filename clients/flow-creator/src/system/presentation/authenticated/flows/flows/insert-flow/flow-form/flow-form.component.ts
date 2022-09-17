import { Component, ElementRef, Inject, Input, OnInit, Renderer } from '@angular/core';
import { FormBuilder, Validators } from "@angular/forms";
import { MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldDefaultOptions, MatSnackBar } from "@angular/material";
import { ActivatedRoute } from "@angular/router";
import { CrudViewComponent } from 'system/application/controls/crud/crud-view.component';

const appearance: MatFormFieldDefaultOptions = {
  appearance: 'outline'
};

// @ts-ignore
@Component({
  selector: 'flow-form',
  templateUrl: 'flow-form.component.html',
  styleUrls: ['../../flows.component.scss'],
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: appearance
    }
  ]
})
export class FlowFormComponent extends CrudViewComponent implements OnInit {

  /**
   *
   */
  @Input()
  entity: any = { };

  /**
   *
   * @param snackBar
   * @param activatedRoute
   * @param element
   * @param permissionRepository
   * @param fb
   * @param renderer
   */
  constructor(public snackBar: MatSnackBar,
    public activatedRoute: ActivatedRoute,
    @Inject(ElementRef) public element: ElementRef,
    public fb: FormBuilder, public renderer: Renderer) {
    super(snackBar, element, fb, renderer, activatedRoute);
  }

  /**
   *
   */
  ngOnInit() {
    this.form = this.fb.group({
      name: ['name', [Validators.required]]
    });
  }

}