import {Injectable} from '@angular/core';
import {BaseRepository} from "../../infrastructure/repository/base/base.repository";
import {HttpClient} from "@angular/common/http";
import {Group} from "../entity/group.model";
import {GroupPermission} from "../entity/group-permission.model";

@Injectable()
export class AccessGroupPermissionRepository extends BaseRepository<GroupPermission> {

  constructor(httpClient: HttpClient) {
    super(httpClient, 'access-manager/v1/access-group-permissions');
  }

  // findAccessGroupPermissionsByGroupId(id: number): any {
  //   return this.httpClient.get<any>(this.collectionName + '?id=' + id )
  // }

  save(accessGroupPermission: GroupPermission) {
    return this.httpClient.post<any>(this.collectionName, accessGroupPermission).toPromise();
  }

  saveAccessGroupPermission(groupId: number, authority: string) {
    const accessGroupPermission = {
      group: {id: groupId},
      permission: {authority: authority}
    }
    return this.httpClient.post<any>(this.collectionName, accessGroupPermission).toPromise();
  }

  removeAccessGroupPermission(groupId: number, authority: string) {
    return this.httpClient.delete(this.collectionName + '/' + groupId + '/unlink/' + authority).toPromise();
  }
}
