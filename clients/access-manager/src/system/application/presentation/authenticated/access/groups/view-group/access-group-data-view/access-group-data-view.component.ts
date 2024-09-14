import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AuthenticatedViewComponent} from '../../../../authenticated-view.component';
import {Group} from "../../../../../../../domain/entity/group.model";
import {PermissionRepository} from "../../../../../../../domain/repository/permission.repository";
import {Permission} from "../../../../../../../domain/entity/permission.model";
import {AccessGroupPermissionRepository} from "../../../../../../../domain/repository/accessGroupPermission.repository";

// @ts-ignore
@Component({
  selector: 'access-group-data-view',
  templateUrl: 'access-group-data-view.component.html',
  styleUrls: ['../../groups.component.scss']
})
export class AccessGroupDataViewComponent implements OnInit {

  @Input()
  group: Group;

  rootPermission: Permission;

  permissionsOfGroup: Permission[];

  constructor(homeView: AuthenticatedViewComponent,
              public activatedRoute: ActivatedRoute,
              public permissionRepository: PermissionRepository,
              private accessGroupPermissionRepository: AccessGroupPermissionRepository) {
    homeView.toolbar.subhead = 'Grupo de Acesso / Detalhes';
  }

  ngOnInit() {
    this.permissionRepository.findById(1).subscribe(permission => {
      this.rootPermission = permission;
      this.findPermissionsFromAccessGroupId(this.group.id).then(resultFromGroupPermissionRequest => {
        this.permissionsOfGroup = resultFromGroupPermissionRequest;
      });
    });
  }

  async findPermissionsFromAccessGroupId(groupId: number): Promise<any> {
    const pageable = {
      'groupId': groupId
    }
    return (await this.accessGroupPermissionRepository.listByFilters(pageable).toPromise())
      .content.map((accessGroupPermission: { permission: any; }) => accessGroupPermission.permission);
  }
}
