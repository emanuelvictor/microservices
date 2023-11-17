import {Component, Input, OnInit} from '@angular/core';
import {PermissionRepository} from "../../../../../domain/repository/permission.repository";
import {Permission} from "../../../../../domain/entity/permission.model";
import {Group} from "../../../../../domain/entity/group.model";
import {GroupRepository} from "../../../../../domain/repository/group.repository";

// @ts-ignore
@Component({
  selector: 'tree-permissions-view',
  templateUrl: './tree-permissions-view.component.html',
  styleUrls: ['./tree-permissions-view.component.scss']
})
export class TreePermissionsViewComponent implements OnInit {

  permissionRepository: PermissionRepository;

  expanded: boolean = false;

  @Input() group: Group = new Group();

  @Input() permissionsOfGroup: Permission[];

  @Input() upperPermission: Permission = new Permission();

  constructor(permissionRepository: PermissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  ngOnInit(): void {
    if (this.upperPermission.id == null)
      this.permissionRepository.findById(1)
        .subscribe(result => {
          this.upperPermission = result;
          this.verifyIfThePermissionIsCheckedInGroup(this.upperPermission, this.permissionsOfGroup)
        })
  }

  verifyIfThePermissionIsCheckedInGroup(permission: Permission, permissionsOfGroup: Permission[]) {
    permissionsOfGroup.forEach(permissionOfGroup => {
      if (permission.id === permissionOfGroup.id)
        this.setChecked(permission, true);
    })
    if (permission.upperPermission)
      this.verifyIfThePermissionIsCheckedInGroup(permission.upperPermission, permissionsOfGroup);
  }

  areTheLowersPermissionsChecked(permission: Permission) {
    if (permission) {
      const contOfLowerPermissionChecked = permission.lowerPermissions.filter(value => value.checked).length;
      if (permission.lowerPermissions && contOfLowerPermissionChecked === permission.lowerPermissions.length) {
        permission.checked = true;
        permission.indeterminate = false;
      } else if (permission.lowerPermissions && contOfLowerPermissionChecked !== permission.lowerPermissions.length) {
        permission.checked = false;
        if (contOfLowerPermissionChecked > 0)
          permission.indeterminate = true;
        else permission.indeterminate = permission.lowerPermissions.filter(value => value.indeterminate).length > 0;
      }
      this.areTheLowersPermissionsChecked(permission.upperPermission)
    }
  }

  clickAtPermissionName(permission) {
    if (this.expanded)
      this.expanded = false;
    else {
      this.loadLowerPermissions(permission)
      this.expanded = true;
    }
  }

  loadLowerPermissions(upperPermission: Permission) {
    if (!upperPermission.lowerPermissions)
      this.permissionRepository.listByFilters({upperPermissionId: upperPermission.id})
        .subscribe(result => {
          this.upperPermission.lowerPermissions = result.content;
          this.upperPermission.lowerPermissions.forEach(permission => permission.upperPermission = this.upperPermission);
          this.setChecked(upperPermission, upperPermission.checked);
        })
  }

  setChecked(permission: Permission, checked: boolean) {
    permission.checked = checked
    if (permission.lowerPermissions != null)
      permission.lowerPermissions.forEach(p => (this.setChecked(p, checked)))
    this.areTheLowersPermissionsChecked(permission.upperPermission)
  }
}
