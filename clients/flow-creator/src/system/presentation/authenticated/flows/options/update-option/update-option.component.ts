import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { OptionRepository } from 'system/domain/repository/option.repository';
import { MessageService } from 'system/domain/services/message.service';
import {AuthenticatedViewComponent} from '../../../authenticated-view.component';

// @ts-ignore
@Component({
  selector: 'update-option',
  templateUrl: 'update-option.component.html',
  styleUrls: ['../options.component.scss']
})
export class UpdateOptionComponent implements OnInit {


  /**
   *
   */
  entity: any = {};

  /**
   *
   * @param router
   * @param homeView
   * @param activatedRoute
   * @param messageService
   * @param optionRepository OptionRepository
   */
  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private messageService: MessageService,
              private homeView: AuthenticatedViewComponent,
              private optionRepository: OptionRepository) {
    homeView.toolbar.subhead = 'Opção / Editar';
  }

  /**
   *
   */
  back() {
    if (this.activatedRoute.snapshot.routeConfig.path === 'edit/:id')
      this.router.navigate(['flows/options']);
    else
      this.router.navigate(['flows/options/' + (+this.activatedRoute.snapshot.params.id)]);
  }

  /**
   *
   */
  ngOnInit() {
    this.findById()
  }

  /**
   *
   */
  public findById() {
    this.optionRepository.findById(+this.activatedRoute.snapshot.params.id)
      .subscribe((result) =>
        this.entity = result
      )
  }

  /**
   *
   * @param form
   */
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
