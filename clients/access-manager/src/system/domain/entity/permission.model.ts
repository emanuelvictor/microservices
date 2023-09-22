import {Abstract} from "./abstract/abstract.model";

export class Permission extends Abstract {

    /**
     *
     */
    public name: string;

    /**
     *
     */
    private identifier: string;

    /**
     *
     */
    public authority: string;

    /**
     *
     */
    public upperPermission: number;

    /**
     *
     */
    public lowerPermissions: Permission[]

}
