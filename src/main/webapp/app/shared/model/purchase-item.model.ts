import { Moment } from 'moment';

export interface IPurchaseItem {
  id?: number;
  typeId?: number;
  qty?: number;
  price?: number;
  disc?: number;
  isDiscPercent?: boolean;
  tax?: number;
  note?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  recordStatusId?: number;
  catalogId?: number;
  unitId?: number;
  taxId?: number;
  purchaseId?: number;
}

export class PurchaseItem implements IPurchaseItem {
  constructor(
    public id?: number,
    public typeId?: number,
    public qty?: number,
    public price?: number,
    public disc?: number,
    public isDiscPercent?: boolean,
    public tax?: number,
    public note?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public recordStatusId?: number,
    public catalogId?: number,
    public unitId?: number,
    public taxId?: number,
    public purchaseId?: number
  ) {
    this.isDiscPercent = this.isDiscPercent || false;
  }
}
