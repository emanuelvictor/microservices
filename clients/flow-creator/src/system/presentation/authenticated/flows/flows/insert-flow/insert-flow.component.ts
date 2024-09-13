import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FlowRepository } from 'system/domain/repository/flow.repository';
import { OptionRepository } from 'system/domain/repository/option.repository';
import { MessageService } from 'system/domain/services/message.service';
import { AuthenticatedViewComponent } from 'system/presentation/authenticated/authenticated-view.component';

// @ts-ignore
@Component({
  selector: 'insert-flow',
  templateUrl: 'insert-flow.component.html',
  styleUrls: ['../flows.component.scss']
})
export class InsertFlowComponent {

  /**
   *
   */
  entity: any = {};

  /**
   *
   * @param router
   * @param homeView AuthenticatedViewComponent
   * @param messageService MessageService
   * @param flowRepository FlowRepository
   */
  constructor(private router: Router,
              private messageService: MessageService,
              private flowRepository: FlowRepository,
              private homeView: AuthenticatedViewComponent) {

    homeView.toolbar.subhead = 'Fluxo / Adicionar'

  }

  public save(form) {

    if (form.invalid) {
      this.messageService.toastWarning();
      return;
    }

    this.flowRepository.save(this.entity)
      .then(() => {
        this.router.navigate(['flows/flows']);
        this.messageService.toastSuccess()
      })
  }

}
