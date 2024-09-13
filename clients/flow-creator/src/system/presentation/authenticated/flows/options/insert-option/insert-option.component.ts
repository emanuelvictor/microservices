import { Component } from '@angular/core';
import { OptionRepository } from 'system/domain/repository/option.repository';
import { MessageService } from 'system/domain/services/message.service';
import { AuthenticatedViewComponent } from '../../../authenticated-view.component';
import { ActivatedRoute, Router } from '@angular/router';

// @ts-ignore
@Component({
  selector: 'insert-option',
  templateUrl: 'insert-option.component.html',
  styleUrls: ['../options.component.scss']
})
export class InsertOptionComponent {

  /**
   *
   */
  entity: any = {};

  /**
   *
   * @param router
   * @param homeView AuthenticatedViewComponent
   * @param messageService MessageService
   * @param optionRepository OptionRepository
   */
  constructor(private router: Router,
              private messageService: MessageService,
              private optionRepository: OptionRepository,
              private homeView: AuthenticatedViewComponent) {

    homeView.toolbar.subhead = 'Opção / Adicionar'

  }

  public save(form) {

    if (form.invalid) {
      this.messageService.toastWarning();
      return;
    }

    this.optionRepository.save(this.entity)
      .then(() => {
        this.router.navigate(['flows/options']);
        this.messageService.toastSuccess()
      })
  }

}
