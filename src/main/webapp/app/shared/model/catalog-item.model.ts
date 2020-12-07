export interface ICatalogItem {
  id?: number;
  name?: string;
}

export class CatalogItem implements ICatalogItem {
  constructor(public id?: number, public name?: string) {}
}
