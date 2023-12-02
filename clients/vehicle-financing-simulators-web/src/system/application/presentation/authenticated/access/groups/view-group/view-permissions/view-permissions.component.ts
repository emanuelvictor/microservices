import {SelectionModel} from '@angular/cdk/collections';
import {FlatTreeControl} from '@angular/cdk/tree';
import {MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material/tree';
import {BehaviorSubject} from 'rxjs';
import {Component, Input, OnInit} from '@angular/core';
import {Permission} from "../../../../../../../domain/entity/permission.model";

/**
 * Node for to-do item
 */
export class TodoItemNode {
  id: number;
  item: string;
  selected: boolean;
  children: TodoItemNode[];
}

/** Flat to-do item node with expandable and level information */
export class TodoItemFlatNode {
  id: number;
  item: string;
  level: number;
  selected: boolean;
  expandable: boolean;
}

// @ts-ignore
@Component({
  selector: 'view-permissions',
  templateUrl: 'view-permissions.component.html',
  styleUrls: ['view-permissions.component.scss']
})
export class ViewPermissionsComponent implements OnInit {

  /**
   *
   */
  dataChange = new BehaviorSubject<TodoItemNode[]>([]);

  @Input()
  permissions: Permission[];

  /** Map from flat node to nested node. This helps us finding the nested node to be modified */
  flatNodeMap = new Map<TodoItemFlatNode, TodoItemNode>();

  /** Map from nested node to flattened node. This helps us to keep the same object for selection */
  nestedNodeMap = new Map<TodoItemNode, TodoItemFlatNode>();

  treeControl: FlatTreeControl<TodoItemFlatNode>;

  treeFlattener: MatTreeFlattener<TodoItemNode, TodoItemFlatNode>;

  dataSource: MatTreeFlatDataSource<TodoItemNode, TodoItemFlatNode>;

  /** The selection for checklist */
  checklistSelection = new SelectionModel<TodoItemFlatNode>(true /* multiple */);

  ngOnInit(): void {
    this.treeFlattener = new MatTreeFlattener(this.transformer, this.getLevel, this.isExpandable, this.getChildren);
    this.treeControl = new FlatTreeControl<TodoItemFlatNode>(this.getLevel, this.isExpandable);
    this.dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

    this.dataChange.subscribe(data => {
      this.dataSource.data = data;
      // this.treeControl.expandAll();
      this.treeControl.expand(this.treeControl.dataNodes[0])
    });

    if (this.permissions && this.permissions.length) {
      const data = this.buildFileTree(this.reduce(this.permissions), 0, false);
      this.dataChange.next(data);
    }
  }

  /**
   * @param array
   */
  reduce(array): any {
    return array.reduce((json, value, key) => {
      key = value.name;
      if (value.lowerPermissions)
        if (value.lowerPermissions.length !== 0)
          json[key] = {key: this.reduce(value.lowerPermissions), id: value.id, selected: value.selected};
        else
          json[key] = {key: key, id: value.id, selected: value.selected};
      return json;
    }, {});
  }

  /**
   * Build the file structure tree. The `value` is the Json object, or a sub-tree of a Json object.
   * The return value is the list of `TodoItemNode`.
   */
  buildFileTree(obj: { [key: string]: any }, level: number, selected: boolean): TodoItemNode[] {
    return Object.keys(obj).reduce<TodoItemNode[]>((accumulator, key) => {

      const value = obj[key];
      const node = new TodoItemNode();
      node.item = key;

      if (value != null) {
        if (typeof value === 'object' && typeof value.key !== 'string') {
          node.children = this.buildFileTree(value.key, level + 1, selected ? selected : value.selected);
        } else {
          if (typeof value === 'object')
            node.item = value.key;
          else
            node.item = value;
        }
        node.id = value.id;
        node.selected = (selected || value.selected);
      }

      return accumulator.concat(node);
    }, []);
  }

  getLevel = (node: TodoItemFlatNode) => node.level;

  isExpandable = (node: TodoItemFlatNode) => node.expandable;

  getChildren = (node: TodoItemNode): TodoItemNode[] => node.children;

  hasChild = (_: number, _nodeData: TodoItemFlatNode) => _nodeData.expandable;

  /**
   * Transformer to convert nested node to flat node. Record the nodes in maps for later use.
   */
  transformer = (node: TodoItemNode, level: number) => {
    const existingNode = this.nestedNodeMap.get(node);
    const flatNode = existingNode && existingNode.item === node.item ? existingNode : new TodoItemFlatNode();
    flatNode.item = node.item;
    flatNode.level = level;
    flatNode.id = node.id;
    flatNode.expandable = !!node.children;
    this.flatNodeMap.set(flatNode, node);
    this.nestedNodeMap.set(node, flatNode);

    if (node.selected) {
      this.initTodoLeafItemSelectionToggle(flatNode);
    }

    return flatNode
  };

  /** Toggle a leaf to-do item selection. Check all the parents to see if they changed */
  initTodoLeafItemSelectionToggle(node: TodoItemFlatNode): void {
    this.checklistSelection.toggle(node);
    this.checkAllParentsSelection(node);
  }

  /* Checks all the parents when a leaf node is selected/unselected */
  checkAllParentsSelection(node: TodoItemFlatNode): void {
    let parent: TodoItemFlatNode | null = this.getParentNode(node);
    while (parent) {
      this.checkRootNodeSelection(parent);
      parent = this.getParentNode(parent);
    }
  }

  /** Check root node checked state and change it accordingly */
  checkRootNodeSelection(node: TodoItemFlatNode): void {
    const nodeSelected = this.checklistSelection.isSelected(node);
    const descendants = this.treeControl.getDescendants(node);
    const descAllSelected = descendants.every(child =>
      this.checklistSelection.isSelected(child)
    );
    if (nodeSelected && !descAllSelected) {
      this.checklistSelection.deselect(node);
    } else if (!nodeSelected && descAllSelected) {
      this.checklistSelection.select(node);
    }
  }

  /* Get the parent node of a node */
  getParentNode(node: TodoItemFlatNode): TodoItemFlatNode | null {

    const currentLevel = this.getLevel(node);

    if (currentLevel < 1) {
      return null;
    }

    const startIndex = this.treeControl.dataNodes.indexOf(node) - 1;

    for (let i = startIndex; i >= 0; i--) {
      const currentNode = this.treeControl.dataNodes[i];

      if (this.getLevel(currentNode) < currentLevel) {
        return currentNode
      }
    }

    return null
  }

}
