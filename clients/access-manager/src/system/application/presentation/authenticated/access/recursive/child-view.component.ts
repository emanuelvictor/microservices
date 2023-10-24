import {Component, Input, OnInit} from '@angular/core';
import {PermissionRepository} from "../../../../../domain/repository/permission.repository";
import {Permission} from "../../../../../domain/entity/permission.model";

// @ts-ignore
@Component({
  selector: 'child-view',
  templateUrl: './child-view.component.html',
  styleUrls: ['./child-view.component.scss']
})
export class ChildViewComponent implements OnInit {

  expanded: boolean = false;

  permissionRepository: PermissionRepository;

  @Input()
  upperPermission: Permission = new Permission();

  constructor(permissionRepository: PermissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  ngOnInit(): void {
    if (this.upperPermission.id == null)
      this.permissionRepository.findById(1)
        .subscribe(result => {
          this.upperPermission = result;
        })
  }

  areUpperPermissionsChecked(permission: Permission): boolean {
    return permission.upperPermission && permission.upperPermission.checked && this.areUpperPermissionsChecked(permission.upperPermission)
  }

  areTheLowersPermissionsChecked(permission: Permission): boolean {
    if (permission.lowerPermissions && permission.lowerPermissions.filter(value => value.checked).length === permission.lowerPermissions.length)
      permission.checked = true
    else if (permission.lowerPermissions && permission.lowerPermissions.filter(value => value.checked).length !== permission.lowerPermissions.length)
      permission.checked = false;
    return permission.checked
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
  }
}
