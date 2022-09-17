import {Component, ViewChild} from "@angular/core";
import {MatTableDataSource} from "@angular/material";
import { ListPageComponent } from "system/application/controls/crud/list/list-page.component";
import { OptionRepository } from "system/domain/repository/option.repository";
import { DialogService } from "system/domain/services/dialog.service";
import { MessageService } from "system/domain/services/message.service";
import { PaginationService } from "system/domain/services/pagination.service";
import { handlePageable } from "system/infrastructure/utils/handle-data-table";

// @ts-ignore
@Component({
  selector: 'consult-options',
  templateUrl: 'consult-options.component.html',
  styleUrls: ['../options.component.scss']
})
export class ConsultOptionsComponent /*implements OnInit */ {

  // Bind com o component ListPageComponent
  @ViewChild(ListPageComponent, {static : true})
  private option: any = {};

  public filters: any = {defaultFilter: ''}; // Estado inicial dos filtros

  public pageable: any = {
    size: 20,
    page: 0,
    sort: null,
    defaultFilter: []
  };

  public totalElements: any;
  public pageIndex: any;
  public pageSize: any;

  public columns: any[] = [
    {name: 'name', label: 'Nome'}
  ];

  public displayedColumns: string[] = this.columns.map(cell => cell.name);

  public dataSource = new MatTableDataSource();

  /**
   * @param dialogService {DialogService}
   * @param paginationService {PaginationService}
   * @param messageService {MessageService}
   * @param optionRepository {OptionRepository}
   */
  constructor(private dialogService: DialogService,
              paginationService: PaginationService,
              private messageService: MessageService,
              private optionRepository: OptionRepository) {

    this.displayedColumns.push('acoes');
    this.pageable = paginationService.pageable('name');

  }

  /**
   *
   */
  ngOnInit() {
    // Seta o size do pageable no size do paginator
    (this.option as any).paginator.pageSize = this.pageable.size;

    // Sobrescreve o sortChange do sort bindado
    this.sortChange();
  }

  /**
   *
   */
  public sortChange() {
    (this.option as any).sort.sortChange.subscribe(() => {
      const {active, direction} = (this.option as any).sort;
      this.pageable.sort = {'properties': active, 'direction': direction};
      this.listByFilters();
    });
  }

  /**
   *
   * @param hasAnyFilter Verifica se há algum filtro,
   * caso exista, então será redirecionado para a primeira página
   */
  public listByFilters(hasAnyFilter: boolean = false) {

    const pageable = handlePageable(hasAnyFilter, (this.option as any).paginator, this.pageable);
    pageable.defaultFilter = (this.option as any).filters.defaultFilter;

    this.optionRepository.listByFilters(pageable)
      .subscribe(result => {
        this.dataSource = new MatTableDataSource(result)
      });
  }

  /**
   * Função para confirmar a exclusão de um registro permanentemente
   * @param option
   */
  public openDeleteDialog(option) {

    this.dialogService.confirmDelete(option, 'OPÇÃO')
      .then((accept: boolean) => {

        if (accept) {
          this.optionRepository.delete(option.id)
            .then(() => {
              this.listByFilters();
              this.messageService.toastSuccess('Opção excluída com sucesso')
            });
        }
      });
  }
}
