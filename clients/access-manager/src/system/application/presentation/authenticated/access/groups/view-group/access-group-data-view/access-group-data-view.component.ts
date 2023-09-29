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

  /**
   *
   */
  permissions: Permission[];

  /**
   *
   */
  @Input()
  group: Group;

  /**
   *
   * @param router
   * @param homeView
   * @param dialogService
   * @param activatedRoute
   * @param messageService
   * @param groupRepository
   * @param permissionRepository
   */
  constructor(private router: Router,
              private dialogService: DialogService,
              public activatedRoute: ActivatedRoute,
              private messageService: MessageService,
              private homeView: AuthenticatedViewComponent,
              private groupRepository: GroupRepository,
              private permissionRepository: PermissionRepository) {

    homeView.toolbar.subhead = 'Grupo de Acesso / Detalhes';

  }

  /**
   *
   */
  ngOnInit() {
    this.findPermissions();
  }

  /**
   *
   */
  public findPermissions() {

    this.permissionRepository.listByFilters({branch: true})
      .subscribe(result => {
        this.permissions = result.content;

        let permissions = this.group.groupPermissions.map(a => a.permission);
        permissions = this.organize(permissions);
        this.organizeTheSelecteds(permissions, this.permissions);

        for (let i = 0; i < this.permissions.length; i++)
          this.markToDelete(this.permissions[i]);

        this.deletePermissionsMarked(this.permissions);
      })
  }

  /**
   *
   * @param permissions
   */
  deletePermissionsMarked(permissions) {
    for (let i = 0; i < permissions.length; i++) {
      if (permissions[i].mustBeDeleted) {
        permissions.splice(i, 1);
        i--;
      } else
        this.deletePermissionsMarked(permissions[i].lowerPermissions)
    }
  }

  /**
   *
   * @param permission
   */
  markToDelete(permission) {
    if (!permission.selected && permission.lowerPermissions.length) {
      for (let i = 0; i < permission.lowerPermissions.length; i++)
        this.markToDelete(permission.lowerPermissions[i]);
      if (!this.hasSomeChildSelected(permission))
        permission.mustBeDeleted = true;
    } else if (!permission.selected && !permission.lowerPermissions.length) {
      permission.mustBeDeleted = true
    }
  }

  /**
   *
   * @param permission
   */
  hasSomeChildSelected(permission) {
    return permission.lowerPermissions.filter(
      lowerPermission => lowerPermission.selected ||
        this.hasSomeChildSelected(lowerPermission)
    ).length > 0
  }

  /**
   *
   * @param permissions
   */
  organize(permissions: Permission[]): Permission[] {

    for (let i = 0; i < permissions.length; i++) {

      if (permissions[i].upperPermission && (permissions[i].upperPermission as any).id)
        permissions[i].upperPermission = (permissions[i].upperPermission as any).id;

      if (!permissions[i].id)
        permissions[i] = this.findPermission(this.permissions, (permissions[i] as any));
      else if (permissions[i].lowerPermissions)
        permissions[i].lowerPermissions = this.organize(permissions[i].lowerPermissions);
    }

    return permissions
  }

  /**
   * Pesqusia a permissão pelo ID
   * @param ownPermissoes
   * @param allPermissoes
   */
  public organizeTheSelecteds(ownPermissoes: Permission[], allPermissoes: Permission[]): void {
    for (let i = 0; i < allPermissoes.length; i++) {
      const permission: Permission = this.findPermission(ownPermissoes, allPermissoes[i].id);

      if (permission) {
        (permission as any).selected = true;
        allPermissoes[i] = permission;
      } else if (allPermissoes[i].lowerPermissions && allPermissoes[i].lowerPermissions.length)
        this.organizeTheSelecteds(ownPermissoes, allPermissoes[i].lowerPermissions)
    }
  }

  /**
   * Pesqusia a permissão pelo ID
   * @param permissions
   * @param id
   */
  public findPermission(permissions: Permission[], id: number): Permission {
    for (let i = 0; i < permissions.length; i++) {
      if (permissions[i]) {
        if (permissions[i].id === id)
          return permissions[i];
        else if (permissions[i].lowerPermissions && permissions[i].lowerPermissions.length) {
          const permission: Permission = this.findPermission(permissions[i].lowerPermissions, id);
          if (permission)
            return permission;
        }
      }
    }
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
