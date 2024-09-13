import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthenticatedViewComponent} from "../presentation/authenticated/authenticated-view.component";
import {ConsultFlowsComponent} from "../presentation/authenticated/flows/flows/consult-flows/consult-flows.component";
import {InsertFlowComponent} from "../presentation/authenticated/flows/flows/insert-flow/insert-flow.component";
import {UpdateFlowComponent} from "../presentation/authenticated/flows/flows/update-flow/update-flow.component";
import {ViewFlowComponent} from "../presentation/authenticated/flows/flows/view-flow/view-flow.component";
import {FlowsViewComponent} from "../presentation/authenticated/flows/flows/flows-view.component";
import {FlowMenuViewComponent} from "../presentation/authenticated/flows/flow-menu-view.component";
import { ConsultOptionsComponent } from 'system/presentation/authenticated/flows/options/consult-options/consult-options.component';
import { InsertOptionComponent } from 'system/presentation/authenticated/flows/options/insert-option/insert-option.component';
import { UpdateOptionComponent } from 'system/presentation/authenticated/flows/options/update-option/update-option.component';
import { OptionDetailComponent } from 'system/presentation/authenticated/flows/options/option-detail/option-detail.component';
import { OptionsViewComponent } from 'system/presentation/authenticated/flows/options/options-view.component';

const routes: Routes = [
  {
    path: '', component: AuthenticatedViewComponent,
    children: [
      {
        path: '', redirectTo: 'flows', pathMatch: 'full',
      },
      {
        path: 'flows',
        component: FlowMenuViewComponent,
        children: [
          {
            path: '', redirectTo: 'flows/insert', pathMatch: 'full',
          },
          {
            path: 'flows', component: FlowsViewComponent,
            children: [
              {path: 'get', redirectTo: '', pathMatch: 'full'},
              {path: '', component: ConsultFlowsComponent},
              {path: 'insert', component: InsertFlowComponent},
              {path: 'edit/:id', component: UpdateFlowComponent},
              {path: ':id/edit', component: UpdateFlowComponent},
              {path: ':id', component: ViewFlowComponent}
            ]
          },

          {
            path: 'options', component: OptionsViewComponent,
            children: [
              {path: 'get', redirectTo: '', pathMatch: 'full'},
              {path: '', component: ConsultOptionsComponent},
              {path: 'insert', component: InsertOptionComponent},
              {path: 'edit/:id', component: UpdateOptionComponent},
              {path: ':id/edit', component: UpdateOptionComponent},
              {path: ':id', component: OptionDetailComponent}
            ]
          }
        ]
      }
    ]
  }
];

/**
 *
 */
@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule],
  providers: []
})
export class SystemRoutingModule {
}
