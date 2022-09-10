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
import { ConsultFlowsComponent } from 'system/application/presentation/authenticated/access/flows/consult-flows/consult-flows.component';
import { FlowsViewComponent } from 'system/application/presentation/authenticated/access/flows/flows-view.component';
import { InsertFlowComponent } from 'system/application/presentation/authenticated/access/flows/insert-flow/insert-flow.component';
import { UpdateFlowComponent } from 'system/application/presentation/authenticated/access/flows/update-flow/update-flow.component';
import { ViewFlowComponent } from 'system/application/presentation/authenticated/access/flows/view-flow/view-flow.component';
import { SharedModule } from '../../shared/shared.module';
import { CrudViewComponent } from '../application/controls/crud/crud-view.component';
import { DetailPageComponent } from '../application/controls/crud/detail/detail-page.component';
import { FormPageComponent } from '../application/controls/crud/form/form-page.component';
import { ListPageComponent } from '../application/controls/crud/list/list-page.component';
import { DataComponent } from "../application/controls/data/data.component";
import { DocumentoPipe } from "../application/controls/documento-pipe/documento-pipe";
import { EvDatepicker } from "../application/controls/ev-datepicker/ev-datepicker";
import { NoSubmitDirective } from "../application/controls/no-sumbit/no-submit.directive";
import { EmConstrucaoComponent } from "../application/controls/not-found/em-construcao.component";
import { FilterPipe } from "../application/controls/pipes/filter.pipe";
import { UserInitialsPipe } from "../application/controls/pipes/user-initials.pipe";
import { CnpjValidator, CpfValidator } from "../application/controls/validators/validators";
import { Describer } from '../application/describer/describer';
import { HasPermissionDirective } from "../application/has-permission/has-permission";
import { Interceptor } from '../application/interceptor/interceptor';
import { AccessViewComponent } from "../application/presentation/authenticated/access/access-view.component";
import { AuthenticatedViewComponent } from '../application/presentation/authenticated/authenticated-view.component';
import { SystemComponent } from "../application/presentation/system.component";
import { FirstUppercasePipe } from "../application/utils/utils";
import { ApplicationRepository } from "./repository/application.repository";
import { GroupRepository } from "./repository/group.repository";
import { PermissionRepository } from "./repository/permission.repository";
import { TokenRepository } from "./repository/token.repository";
import { UserRepository } from "./repository/user.repository";
import { AuthenticationService } from './services/authentication.service';
import { DialogService } from './services/dialog.service';
import { MessageService } from './services/message.service';
import { PaginationService } from './services/pagination.service';
import { getPaginatorIntl } from './services/portuguese-paginator-intl';
import { WildcardService } from './services/wildcard.service';
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
    AccessViewComponent,

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

    AccessViewComponent,

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
