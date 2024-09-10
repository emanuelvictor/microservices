import {Component, Input, OnInit} from '@angular/core';
import {PermissionRepository} from "../../../../../domain/repository/permission.repository";
import {Permission} from "../../../../../domain/entity/permission.model";
import {Group} from "../../../../../domain/entity/group.model";
import {GroupRepository} from "../../../../../domain/repository/group.repository";
import {GroupPermission} from "../../../../../domain/entity/group-permission.model";
import {AccessGroupPermissionRepository} from "../../../../../domain/repository/accessGroupPermission.repository";

// @ts-ignore
@Component({
  selector: 'tree-permissions-view',
  templateUrl: './tree-permissions-view.component.html',
  styleUrls: ['./tree-permissions-view.component.scss']
})
export class TreePermissionsViewComponent implements OnInit {

  permissionRepository: PermissionRepository;
  accessGroupPermissionRepository: AccessGroupPermissionRepository;

  expanded: boolean = false;

  @Input() group: Group = new Group();

  @Input() permissionsOfGroup: Permission[];

  @Input() upperPermission: Permission = new Permission();

  constructor(permissionRepository: PermissionRepository,
              accessGroupPermissionRepository: AccessGroupPermissionRepository) {
    this.permissionRepository = permissionRepository;
    this.accessGroupPermissionRepository = accessGroupPermissionRepository;
  }

  ngOnInit(): void {
    if (this.upperPermission.id == null)
      this.permissionRepository.findById(1).subscribe(result => {
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

  setChecked(permission: Permission, checked: boolean, saveOnBackend: boolean = false) {
    if (saveOnBackend) {
      if (checked) {
        console.log('Link: ' + permission.authority);
        const group: Group = new Group(this.group.id);
        const permissionToSave: Permission = new Permission(permission.authority);
        const accessGroupPermission: GroupPermission = new GroupPermission(permissionToSave, group);
        this.accessGroupPermissionRepository.save(accessGroupPermission).then(value => {
        })
      } else {
        console.log('Unlink: ' + permission.authority);
        this.accessGroupPermissionRepository.removeAccessGroupPermission(this.group.id, permission.authority).then(value => {
        })
      }
    }
    permission.checked = checked
    if (permission.lowerPermissions != null)
      permission.lowerPermissions.forEach(p => (this.setChecked(p, checked, false)))
    this.areTheLowersPermissionsChecked(permission.upperPermission)
  }
}
