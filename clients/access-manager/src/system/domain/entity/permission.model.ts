import {Abstract} from "./abstract/abstract.model";

export class Permission extends Abstract {

  public name: string;
  public checked: boolean = false;
  public authority: string;
  private identifier: string;
  public upperPermission: any;
  public lowerPermissions: Permission[]

}
