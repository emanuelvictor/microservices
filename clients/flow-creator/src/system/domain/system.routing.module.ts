import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthenticatedViewComponent} from "../application/presentation/authenticated/authenticated-view.component";
import {AuthenticationService} from "./services/authentication.service";
import {ConsultFlowsComponent} from "../application/presentation/authenticated/access/flows/consult-flows/consult-flows.component";
import {InsertFlowComponent} from "../application/presentation/authenticated/access/flows/insert-flow/insert-flow.component";
import {UpdateFlowComponent} from "../application/presentation/authenticated/access/flows/update-flow/update-flow.component";
import {ViewFlowComponent} from "../application/presentation/authenticated/access/flows/view-flow/view-flow.component";
import {FlowsViewComponent} from "../application/presentation/authenticated/access/flows/flows-view.component";
import {AccessViewComponent} from "../application/presentation/authenticated/access/access-view.component";

const routes: Routes = [
  {
    path: '', component: AuthenticatedViewComponent, canActivate: [AuthenticationService],
    children: [
      {
        path: '', redirectTo: 'access', pathMatch: 'full',
      },
      {
        path: 'access',
        component: AccessViewComponent,
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
