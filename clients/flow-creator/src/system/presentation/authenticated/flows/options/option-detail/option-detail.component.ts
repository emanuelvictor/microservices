import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { OptionRepository } from 'system/domain/repository/option.repository';
import { DialogService } from 'system/domain/services/dialog.service';
import { MessageService } from 'system/domain/services/message.service';
import {AuthenticatedViewComponent} from '../../../authenticated-view.component';

// @ts-ignore
@Component({
  selector: 'option-detail',
  templateUrl: 'option-detail.component.html',
  styleUrls: ['../options.component.scss']
})
export class OptionDetailComponent implements OnInit {

  /**
   *
   */
  entity: any = {};

  /**
   *
   * @param router
   * @param homeView
   * @param dialogService
   * @param activatedRoute
   * @param messageService
   * @param optionRepository OptionRepository
   */
  constructor(private router: Router,
              private dialogService: DialogService,
              public activatedRoute: ActivatedRoute,
              private messageService: MessageService,
              private homeView: AuthenticatedViewComponent,
              private optionRepository: OptionRepository) {

    this.entity.id = +this.activatedRoute.snapshot.params.id || null;
    homeView.toolbar.subhead = 'Opção / Detalhes';

  }

  /**
   *
   */
  ngOnInit() {
    if (this.entity && this.entity.id) {
      this.findById();
    } else {
      this.router.navigate(['/flows'])
    }
  }

  /**
   *
   */
  public findById() {
    this.optionRepository.findById(this.entity.id)
      .subscribe((result) => this.entity = result)
  }

  /**
   * Função para confirmar a exclusão de um registro permanentemente
   * @param group
   */
  public openDeleteDialog(group) {

    this.dialogService.confirmDelete(group, 'Opção')
      .then((accept: boolean) => {

        if (accept) {
          this.optionRepository.delete(group.id)
            .then(() => {
              this.router.navigate(['flows/options']);
              this.messageService.toastSuccess('Registro excluído com sucesso')
            })
        }
      })
  }

}
