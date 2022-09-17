import {Injectable} from '@angular/core';
import {BaseRepository} from "../../infrastructure/repository/base/base.repository";
import {HttpClient} from "@angular/common/http";
import {Option} from "../entity/option.model";

@Injectable()
export class OptionRepository extends BaseRepository<Option> {

  constructor(httpClient: HttpClient) {
    super(httpClient, 'flows/options');
  }

}
