import {Injectable} from '@angular/core';
import {BaseRepository} from "../../infrastructure/repository/base/base.repository";
import {HttpClient} from "@angular/common/http";
import {Flow} from "../entity/flow.model";

@Injectable()
export class FlowRepository extends BaseRepository<Flow> {

  constructor(httpClient: HttpClient) {
    super(httpClient, 'flows/flows');
  }

}
