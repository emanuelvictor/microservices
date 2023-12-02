import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticatedViewComponent} from '../../../../authenticated-view.component';
import {MessageService} from '../../../../../../../domain/services/message.service';
import {GroupRepository} from "../../../../../../../domain/repository/group.repository";
import {DialogService} from "../../../../../../../domain/services/dialog.service";
import {Group} from "../../../../../../../domain/entity/group.model";
import {PermissionRepository} from "../../../../../../../domain/repository/permission.repository";
import {Permission} from "../../../../../../../domain/entity/permission.model";

// @ts-ignore
@Component({
  selector: 'access-group-data-view',
  templateUrl: 'access-group-data-view.component.html',
  styleUrls: ['../../groups.component.scss']
})
export class AccessGroupDataViewComponent implements OnInit {

  permissions: Permission[];

  @Input()
  group: Group;

  permissionsOfGroup: Permission[];

  constructor(private router: Router,
              private dialogService: DialogService,
              public activatedRoute: ActivatedRoute,
              private messageService: MessageService,
              private homeView: AuthenticatedViewComponent,
              private groupRepository: GroupRepository) {
    homeView.toolbar.subhead = 'Grupo de Acesso / Detalhes';
  }

  ngOnInit() {
    this.findPermissionsFromAccessGroupId(this.group.id).then(resultFromGroupPermissionRequest => {
      this.permissionsOfGroup = resultFromGroupPermissionRequest;
    });
  }

  async findPermissionsFromAccessGroupId(groupId: number): Promise<any> {
    return (await this.groupRepository.findAccessGroupPermissionsByUserId(groupId).toPromise()).content.map(accessGroupPermission => accessGroupPermission.permission);
  }

  /**
   * Função para confirmar a exclusão de um registro permanentemente
   * @param group
   */
  public openDeleteDialog(group) {

    this.dialogService.confirmDelete(group, 'Grupo de Acesso')
      .then((accept: boolean) => {

        if (accept) {
          this.groupRepository.delete(group.id)
            .then(() => {
              this.router.navigate(['access/groups']);
              this.messageService.toastSuccess('Registro excluído com sucesso')
            })
        }
      })
  }
}
