import { CommonModule, registerLocaleData } from "@angular/common";
import { HttpClient, HttpClientJsonpModule, HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import localePt from '@angular/common/locales/pt';
import { CUSTOM_ELEMENTS_SCHEMA, LOCALE_ID, NgModule } from "@angular/core";
import {
  MatFormFieldDefaultOptions,
  MatPaginatorIntl,
  MatTreeModule, MAT_FORM_FIELD_DEFAULT_OPTIONS
} from "@angular/material";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CovalentSearchModule } from "@covalent/core";
import { TranslateLoader, TranslateModule } from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { PieChartModule } from "@swimlane/ngx-charts";
import { ButtonClearFiltersComponent } from 'system/application/controls/button-clear-filters/button-clear-filters.component';
import { ButtonToggleAdvancedFiltersComponent } from 'system/application/controls/button-toggle-advanced-filters/button-toggle-advanced-filters.component';
import { EntityFormComponent } from 'system/application/controls/crud/cadastros/entity-form/entity-form.component';
import { FormToolbarComponent } from 'system/application/controls/crud/cadastros/form-toolbar/form-toolbar.component';
import { ListTableComponent } from 'system/application/controls/crud/cadastros/list-table/list-table.component';
import { NoRecordsFoundComponent } from "system/application/controls/no-records-found/no-records-found.component";
import { ConsultFlowsComponent } from 'system/presentation/authenticated/flows/flows/consult-flows/consult-flows.component';
import { FlowsViewComponent } from 'system/presentation/authenticated/flows/flows/flows-view.component';
import { InsertFlowComponent } from 'system/presentation/authenticated/flows/flows/insert-flow/insert-flow.component';
import { UpdateFlowComponent } from 'system/presentation/authenticated/flows/flows/update-flow/update-flow.component';
import { ViewFlowComponent } from 'system/presentation/authenticated/flows/flows/view-flow/view-flow.component';
import { SharedModule } from '../../shared/shared.module';
import { CrudViewComponent } from './controls/crud/crud-view.component';
import { DetailPageComponent } from './controls/crud/detail/detail-page.component';
import { FormPageComponent } from './controls/crud/form/form-page.component';
import { ListPageComponent } from './controls/crud/list/list-page.component';
import { DataComponent } from "./controls/data/data.component";
import { DocumentoPipe } from "./controls/documento-pipe/documento-pipe";
import { EvDatepicker } from "./controls/ev-datepicker/ev-datepicker";
import { NoSubmitDirective } from "./controls/no-sumbit/no-submit.directive";
import { EmConstrucaoComponent } from "./controls/not-found/em-construcao.component";
import { FilterPipe } from "./controls/pipes/filter.pipe";
import { UserInitialsPipe } from "./controls/pipes/user-initials.pipe";
import { CnpjValidator, CpfValidator } from "./controls/validators/validators";
import { Describer } from '../infrastructure/describer/describer';
import { HasPermissionDirective } from "../infrastructure/has-permission/has-permission";
import { Interceptor } from './interceptor/interceptor';
import { FlowMenuViewComponent } from "../presentation/authenticated/flows/flow-menu-view.component";
import { AuthenticatedViewComponent } from '../presentation/authenticated/authenticated-view.component';
import { SystemComponent } from "../presentation/system.component";
import { FirstUppercasePipe } from "../infrastructure/utils/utils";
import { ApplicationRepository } from "../domain/repository/application.repository";
import { GroupRepository } from "../domain/repository/group.repository";
import { PermissionRepository } from "../domain/repository/permission.repository";
import { TokenRepository } from "../domain/repository/token.repository";
import { UserRepository } from "../domain/repository/user.repository";
import { AuthenticationService } from '../domain/services/authentication.service';
import { DialogService } from '../domain/services/dialog.service';
import { MessageService } from '../domain/services/message.service';
import { PaginationService } from '../domain/services/pagination.service';
import { getPaginatorIntl } from '../domain/services/portuguese-paginator-intl';
import { WildcardService } from '../domain/services/wildcard.service';
import { SystemRoutingModule } from "./system.routing.module";

const appearance: MatFormFieldDefaultOptions = {
  appearance: 'outline'
};

registerLocaleData(localePt, 'pt-BR');

// Custom TranslateLoader while using AoT compilation
export function customTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

/**
 *
 */
// @ts-ignore
@NgModule({
  declarations: [
    // Directives
    NoSubmitDirective,

    // PIPES
    FilterPipe,
    UserInitialsPipe,

    // COMPONENTS
    SystemComponent,
    AuthenticatedViewComponent,

    // CONTROLS
    CrudViewComponent,
    ListPageComponent,
    DetailPageComponent,
    FormPageComponent,
    EmConstrucaoComponent,
    EvDatepicker,
    CpfValidator,
    CnpjValidator,
    DocumentoPipe,
    ButtonToggleAdvancedFiltersComponent,
    ButtonClearFiltersComponent,
    NoRecordsFoundComponent,

    FirstUppercasePipe,

    //Cadastros
    FormToolbarComponent,
    ListTableComponent,
    EntityFormComponent,

    // Configuracoes
    FlowMenuViewComponent,

    // Flows
    FlowsViewComponent,
    ConsultFlowsComponent,
    UpdateFlowComponent,
    InsertFlowComponent,
    ViewFlowComponent,

    DataComponent,

    // Has Permission
    HasPermissionDirective,

  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    SystemRoutingModule,
    HttpClientModule,
    CovalentSearchModule,
    MatTreeModule,
    HttpClientJsonpModule,

    SharedModule,

    // Translate i18n
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (customTranslateLoader),
        deps: [HttpClient]
      }
    }),
    PieChartModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [NoSubmitDirective],
  providers: [

    // Repositories
    UserRepository,
    PermissionRepository,
    ApplicationRepository,
    GroupRepository,
    TokenRepository,

    // Services
    Describer,
    WildcardService,
    PaginationService,
    AuthenticationService,

    FlowMenuViewComponent,

    DialogService,
    MessageService,

    {
      useValue: appearance,
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS
    },

    {
      multi: true,
      useClass: Interceptor,
      provide: HTTP_INTERCEPTORS
    },

    // Internacionalizacao MatPaginator
    {provide: MatPaginatorIntl, useValue: getPaginatorIntl()},
    {provide: LOCALE_ID, useValue: 'pt-BR'}
  ],
  bootstrap: [SystemComponent]
})
export class SystemModule {
}
