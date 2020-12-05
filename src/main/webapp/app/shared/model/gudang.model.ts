import { IPurchase } from 'app/shared/model/purchase.model';
import { ISales } from 'app/shared/model/sales.model';

export interface IGudang {
  id?: number;
  name?: string;
  purchases?: IPurchase[];
  sales?: ISales[];
}

export class Gudang implements IGudang {
  constructor(public id?: number, public name?: string, public purchases?: IPurchase[], public sales?: ISales[]) {}
}
