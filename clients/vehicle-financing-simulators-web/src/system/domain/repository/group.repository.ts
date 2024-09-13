import {Injectable} from '@angular/core';
import {BaseRepository} from "../../infrastructure/repository/base/base.repository";
import {HttpClient} from "@angular/common/http";
import {Group} from "../entity/group.model";

@Injectable()
export class GroupRepository extends BaseRepository<Group> {

  constructor(httpClient: HttpClient) {
    super(httpClient, 'access-manager/v1/groups');
  }

  findAccessGroupPermissionsByUserId(id: number): any {
    return this.httpClient.get<any>(this.collectionName + '/' + id + '/access-group-permissions')
  }
}
