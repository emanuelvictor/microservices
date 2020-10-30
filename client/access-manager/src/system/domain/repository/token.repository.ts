import {Injectable} from '@angular/core';
import {BaseRepository} from "../../infrastructure/repository/base/base.repository";
import {HttpClient, HttpParams} from "@angular/common/http";
import {PageSerialize} from "../../infrastructure/page-serialize/page-serialize";
import {Observable} from "rxjs";
import {User} from "../entity/user.model";
import {Token} from "../entity/token.model";

@Injectable()
export class TokenRepository extends BaseRepository<Token> {

  /**
   *
   * @param httpClient
   */
  constructor(httpClient: HttpClient) {
    super(httpClient, 'access-manager/tokens');
  }

  /**
   *
   * @param id
   */
  listTokensById(id: any): any {
    return this.httpClient.get<any>(this.collectionName + '/' + id)
  }
}
