import { IPurchase } from 'app/shared/model/purchase.model';
import { ISales } from 'app/shared/model/sales.model';

export interface IWarehouse {
  id?: number;
  name?: string;
  purchases?: IPurchase[];
  sales?: ISales[];
}

export class Warehouse implements IWarehouse {
  constructor(public id?: number, public name?: string, public purchases?: IPurchase[], public sales?: ISales[]) {}
}
